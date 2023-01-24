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

import cats.implicits._
import com.mdagdelen.exceptions.Exceptions._
import os.Path

object FileUtils {
  def readFile(filePath: String): Either[Throwable, List[String]] = for {
    path <- Either.catchNonFatal[Path](os.pwd / os.RelPath(filePath))
    rows <-
      if (os.isFile(path)) {
        path.ext match {
          case "csv" =>
            Right(os.read.lines(path).drop(1).toList) // alternatively, you can use os.read.lines.stream(path)
          case unsupported => UnsupportedFileFormatEx(unsupported).asLeft
        }
      } else IsNotFileEx.asLeft
  } yield rows
}
