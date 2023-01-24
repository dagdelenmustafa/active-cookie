import com.mdagdelen.FileUtils
import com.mdagdelen.exceptions.Exceptions.{IsNotFileEx, UnsupportedFileFormatEx}
import munit.FunSuite

class FileUtilSpec extends FunSuite {
  test("read csv files successfully") {
    val testFilePath = "src/test/resources/test.csv"
    FileUtils.readFile(testFilePath) match {
      case Left(ex)    => fail("Should have successfully read", ex)
      case Right(rows) => assert(rows.length == 11)
    }
  }

  test("fail with 'UnsupportedFileFormatEx' when json file given.") {
    val testFilePath = "src/test/resources/test.json"
    FileUtils.readFile(testFilePath) match {
      case Left(ex) => assert(ex.isInstanceOf[UnsupportedFileFormatEx])
      case Right(_) => fail("Must be failed with `UnsupportedFileFormatEx`")
    }
  }

  test("fail with 'IsNotFileEx' when given path is not a file.") {
    val testFilePath = "src/test/resources/test.unknown"
    FileUtils.readFile(testFilePath) match {
      case Left(ex) => assert(ex == IsNotFileEx)
      case Right(_) => fail("Must be failed with `IsNotFileEx`")
    }
  }

  test("fail with 'IllegalArgumentException' when inappropriate path given.") {
    val testFilePath = "/src/test/resources/test.csv"
    FileUtils.readFile(testFilePath) match {
      case Left(ex) => assert(ex.isInstanceOf[IllegalArgumentException])
      case Right(_) => fail("Must be failed with `IsNotFileEx`")
    }
  }
}
