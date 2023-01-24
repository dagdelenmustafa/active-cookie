package com.mdagdelen

object TestData {

  val validRowsForOneOccurence = List(
    "AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00",
    "AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00",
    "SAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00"
  )

  val validRowsForMultiOccurence = List(
    "AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00",
    "AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00",
    "SAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00",
    "SAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00",
    "FAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00"
  )

  val invalidTimestamp = List(
    "AtY0laUfhglK3lC7,2018-12-0914:19:00+00:00",
    "AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00",
    "SAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00"
  )

  val invalidCSVStructure = List(
    "AtY0laUfhglK3lC7,2018-12-0914:19:00+00:00,helloWorld",
    "AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00,helloWorld",
    "SAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00,helloWorld"
  )

}
