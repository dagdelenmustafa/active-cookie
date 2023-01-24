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

package com.mdagdelen.commands

import cats.implicits._
import com.monovore.decline.Opts
import os._

import java.time.format.DateTimeFormatter
import scala.util.{Failure, Success, Try}

object Commands {
  val filePathOpt: Opts[String] =
    Opts
      .option[String]("file", short = "f", help = "Cookie log file path.")
      .validate("Given file path is not correct")(maybePath => {
        Try(os.pwd / os.RelPath(maybePath)) match {
          case Failure(_)    => false
          case Success(path) => os.isFile(path)
        }
      })

  val dateOpt: Opts[String] =
    Opts
      .option[String]("date", short = "d", help = "The date the most active cookie will be found.")
      .validate("Given date is not correctly formatted. It must be in yyyy-MM-dd format.") { maybeDate =>
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        Try(formatter.parse(maybeDate)).toOption.isDefined
      }

  val appCmd: Opts[(String, String)] = (filePathOpt, dateOpt).tupled
}
