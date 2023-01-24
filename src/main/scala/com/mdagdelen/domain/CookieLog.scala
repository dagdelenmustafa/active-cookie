/*
 * Copyright 2023 com.dagdelenmustafa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mdagdelen.domain

import cats.implicits._
import com.mdagdelen.exceptions.Exceptions._

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import scala.util.{Failure, Success, Try}

trait CookieLog {
  def getMostActiveForDate(date: String): Either[Throwable, List[CookieOccurence]]
  protected def convert(lines: List[String]): Either[Throwable, List[CookieRow]]
}

object DefaultCookieLog {
  def make(lines: List[String]): CookieLog = {
    new CookieLog {
      private def sortAndGetActive(cookies: List[CookieRow], date: String): Either[Throwable, List[CookieOccurence]] = {
        val sortedCookieOccurence = cookies
          .filter(_.dateStr == date)
          .groupBy(_.cookie)
          .map { case (cookie, cookies) => CookieOccurence(cookie, cookies.length) }
          .toList
          .sortBy(_.occurence)(Ordering[Int].reverse)

        sortedCookieOccurence.headOption.map(_.occurence) match {
          case None               => CookieNotFound(date).asLeft
          case Some(maxOccurence) => sortedCookieOccurence.filter(_.occurence == maxOccurence).asRight
        }
      }

      override protected def convert(lines: List[String]): Either[Throwable, List[CookieRow]] = lines.traverse(line =>
        line.split(",") match {
          case Array(cookie, timestamp) =>
            Try(OffsetDateTime.parse(timestamp)) match {
              case Failure(e) => TimestampCastEx(e.getMessage).asLeft[CookieRow]
              case Success(value) =>
                DefaultCookieRow(cookie, value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).asRight
            }
          case _ =>
            UnknownCSVStructureEx(
              "Unknown csv structure. Log file must be a csv with two row (cookie,timestamp)"
            ).asLeft[CookieRow]
        }
      )

      override def getMostActiveForDate(date: String): Either[Throwable, List[CookieOccurence]] = for {
        cookies         <- convert(lines)
        cookieOccurence <- sortAndGetActive(cookies, date)
      } yield cookieOccurence
    }
  }
}
