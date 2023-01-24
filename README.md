# Active Cookie Cli App

This app is a simple but extendable cli application to find most active cookie in a given log file.

## Goal
Our goal is to write an application which finds the most active cookie in the given log file. Currently, we only support the csv files with the following structure.

Input: test.csv
```
cookie,timestamp
fbcn5UAVanZf6UtG,2018-12-08T09:30:00+00:00
4sMM2LxV07bPJzwf,2018-12-07T23:30:00+00:00
```

## Prerequisites
To run this app, you will need to have the following installed on your system:
- sbt 1.4
- Scala 2.13
- Scala-Native 0.4.9

### Helpful links for installations
#### Install Scala
Please visit official scala download [page](https://www.scala-lang.org/download/) and follow the related steps.

#### Install llvm and c++ to setup scala-native
Please visit official scala-native download [page](https://scala-native.org/en/stable/user/setup.html#installing-sbt) and follow the related steps.

## Getting Started
To get started with this app, clone the repository to your local machine:
```bash
git clone https://github.com/dagdelenmustafa/active-cookie.git
cd active-cookie
```

Then build the project using `sbt` and create executable file with `nativeLink` command.
```bash
sbt nativeLink 
```
This command will create the executable file under the `target` folder but this repository's root folder contains a `symlink` which name is `run` to the file and you can use it easily.

## Usage
`active-cookie` app takes two parameter. One for the cookie log file path and one for the date that we want to find the most active cookie.
Here is the `--help` output of the app.

```bash
./run --help
```
```
Usage: active-cookie --file <string> --date <string>

Find most active cookie at given time!

Options and flags:
    --help
        Display this help text.
    --file <string>, -f <string>
        Cookie log file path.
    --date <string>, -d <string>
        The date the most active cookie will be found.
```
Example usage with the test csv:

```bash
./run --file src/test/resources/test.csv --date 2018-12-08


AtY0laUfhglK3lC7
5UAVanZf6UtGyKVS
```

## Tests
Test files are located under `test` folder. To run the tests, simple run the following command:
```bash
sbt test
```

## Dependencies
We use following dependencies to build the application.

- [cats](https://typelevel.org/cats/): It offers functional data structures and syntax.
- [decline](https://ben.kirw.in/decline/): It enables us to handle command-line arguments in a functional way.
- [oslib](https://github.com/com-lihaoyi/os-lib): simple i/o utilities.

## Extend log file support
`active-cookie` is an extendable cli application and users can easily extend support for new files at any time. To make this
program more generic, we introduce the `CookieLog` trait and users can create a new active cookie extractor by extending this trait.

```scala
trait CookieLog {
  def getMostActiveForDate(date: String): Either[Throwable, List[CookieOccurence]]
  protected def convert(lines: List[String]): Either[Throwable, List[CookieRow]]
}
```

In this trait we have two different method. `convert` method gets a list of strings which is the representation of the log file rows.
Then it converts this rows into a structured form. We want to make this method extendable because each file has different type of structure.
And other method is `getMostActiveForDate`. This is the place that users can implement their activeness business logic.
Users can determine different kinds of activeness logic for different type of files.

## License
This project is licensed under the [Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0).
