package com.mdagdelen.commands

import com.monovore.decline.Command
import munit.FunSuite

class CommandsSpec extends FunSuite {
  val command: Command[(String, String)] = Command(
    name = "active-cookie",
    header = "Find most active cookie at given time!",
    helpFlag = true
  )(Commands.appCmd)

  test("parse and validate cli parameters successfully") {
    val args = List("-f", "src/test/resources/test.csv", "-d", "2018-12-01").toArray

    command.parse(args) match {
      case Left(_)  => fail("cli parser is not working properly")
      case Right(_) => assert(cond = true)
    }
  }

  test("fail when invalid date given") {
    val args = List("-f", "src/test/resources/test.csv", "-d", "invalidDate").toArray

    command.parse(args) match {
      case Left(_)  => assert(cond = true)
      case Right(_) => fail("cli parser is not working properly. Must be failed for invalid date")
    }
  }

  test("fail when invalid path given") {
    val args = List("-f", "/src/test/resources/test.csv", "-d", "2018-12-01").toArray

    command.parse(args) match {
      case Left(_)  => assert(cond = true)
      case Right(_) => fail("cli parser is not working properly. Must be failed for invalid path")
    }
  }
}
