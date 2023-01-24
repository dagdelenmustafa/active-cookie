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

package com.mdagdelen.exceptions

sealed trait BaseException extends Throwable

object Exceptions {
  case class UnsupportedFileFormatEx(fileFormat: String) extends BaseException {
    override def getMessage: String =
      s"Given file format($fileFormat) is not supported. Please use .csv format for the log file."
  }

  case object IsNotFileEx extends BaseException {
    override def getMessage: String = "No file found at the given path"
  }

  case class CookieNotFound(date: String) extends BaseException {
    override def getMessage: String = s"Could not found a cookie log for the given date($date)"
  }

  case class TimestampCastEx(message: String) extends BaseException {
    override def getMessage: String = s"Given log file contains unsupported date format. $message"
  }

  case class UnknownCSVStructureEx(message: String) extends BaseException {
    override def getMessage: String = message
  }
}
