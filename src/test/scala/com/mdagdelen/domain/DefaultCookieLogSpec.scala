package com.mdagdelen.domain

import cats.implicits._
import com.mdagdelen.TestData
import com.mdagdelen.exceptions.Exceptions.{CookieNotFound, TimestampCastEx, UnknownCSVStructureEx}
import munit.FunSuite

class DefaultCookieLogSpec extends FunSuite {
  test("successfully find the most active cookie of given logs") {
    (for {
      lines <- Either.right(TestData.validRowsForOneOccurence)
      service = DefaultCookieLog.make(lines)
      occurence <- service.getMostActiveForDate("2018-12-09")
    } yield occurence) match {
      case Left(value) => fail(value.getMessage)
      case Right(value) =>
        assert(value.length == 1 && value.head.occurence == 2 && value.head.cookie == "AtY0laUfhglK3lC7")
    }
  }

  test("successfully find the most active cookies(multiple) of given logs") {
    (for {
      lines <- Either.right(TestData.validRowsForMultiOccurence)
      service = DefaultCookieLog.make(lines)
      occurence <- service.getMostActiveForDate("2018-12-09")
    } yield occurence) match {
      case Left(value) => fail(value.getMessage)
      case Right(value) =>
        assert(
          value.length == 2 &&
            value.head.occurence == 2 &&
            value.head.cookie == "AtY0laUfhglK3lC7" &&
            value.tail.head.occurence == 2 &&
            value.tail.head.cookie == "SAZuXPGUrfbcn5UA"
        )
    }
  }

  test("fail when there is no cookie found for the given date") {
    (for {
      lines <- Either.right(TestData.validRowsForOneOccurence)
      service = DefaultCookieLog.make(lines)
      occurence <- service.getMostActiveForDate("2022-12-09")
    } yield occurence) match {
      case Left(value) => assert(value.isInstanceOf[CookieNotFound])
      case Right(_)    => fail("Must be failed with 'CookieNotFound' exception")
    }
  }

  test("fail when log file timestamp is not well formatted") {
    (for {
      lines <- Either.right(TestData.invalidTimestamp)
      service = DefaultCookieLog.make(lines)
      occurence <- service.getMostActiveForDate("2022-12-09")
    } yield occurence) match {
      case Left(value) => assert(value.isInstanceOf[TimestampCastEx])
      case Right(_)    => fail("Must be failed with 'TimestampCastEx' exception")
    }
  }

  test("fail when the csv structure is not proper") {
    (for {
      lines <- Either.right(TestData.invalidCSVStructure)
      service = DefaultCookieLog.make(lines)
      occurence <- service.getMostActiveForDate("2022-12-09")
    } yield occurence) match {
      case Left(value) => assert(value.isInstanceOf[UnknownCSVStructureEx])
      case Right(_)    => fail("Must be failed with 'UnknownCSVStructureEx' exception")
    }
  }
}
