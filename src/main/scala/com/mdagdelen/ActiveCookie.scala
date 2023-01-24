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

package com.mdagdelen

import com.mdagdelen.commands.Commands
import com.mdagdelen.domain.{CookieOccurence, DefaultCookieLog}
import com.monovore.decline.Command

object ActiveCookie {
  def main(args: Array[String]): Unit = {
    val command = Command(
      name = "active-cookie",
      header = "Find most active cookie at given time!",
      helpFlag = true
    )(Commands.appCmd)

    command.parse(args) match {
      case Left(help) if help.errors.isEmpty =>
        // help was requested by the user, i.e.: `--help`
        println(help)
        sys.exit(0)

      case Left(help) =>
        // user needs help due to bad/missing arguments
        println(help)
        sys.exit(1)

      case Right((filePath, date)) =>
        execute(filePath, date) match {
          case Left(value) =>
            println(value.getMessage)
            sys.exit(1)
          case Right(value) =>
            value.foreach(c => println(c.cookie))
            sys.exit(0)
        }
    }
  }

  private def execute(filePath: String, date: String): Either[Throwable, List[CookieOccurence]] = for {
    lines <- FileUtils.readFile(filePath)
    service = DefaultCookieLog.make(lines)
    activeCookies <- service.getMostActiveForDate(date)
  } yield activeCookies
}
