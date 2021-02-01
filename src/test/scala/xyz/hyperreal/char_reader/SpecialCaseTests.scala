package xyz.hyperreal.char_reader

import utest._

object SpecialCaseTests extends TestSuite with Testing {
  val tests: Tests = Tests {

    test("indented text") {
      text(
        """|1
           | a
           |  b
           | c
           |2
           | d
           |  e
           |""".stripMargin
      ) ==>
        """|'1' (line 1, column 1):
           |1
           |^
           |
           |'\n' (line 1, column 2):
           |1
           | ^
           |
           |'INDENT' (line 2, column 2):
           | a
           | ^
           |
           |'a' (line 2, column 2):
           | a
           | ^
           |
           |'\n' (line 2, column 3):
           | a
           |  ^
           |
           |' ' (line 3, column 2):
           |  b
           | ^
           |
           |'b' (line 3, column 3):
           |  b
           |  ^
           |
           |'\n' (line 3, column 4):
           |  b
           |   ^
           |
           |'c' (line 4, column 2):
           | c
           | ^
           |
           |'\n' (line 4, column 3):
           | c
           |  ^
           |
           |'DEDENT' (line 5, column 1):
           |2
           |^
           |
           |'2' (line 5, column 1):
           |2
           |^
           |
           |'\n' (line 5, column 2):
           |2
           | ^
           |
           |'INDENT' (line 6, column 2):
           | d
           | ^
           |
           |'d' (line 6, column 2):
           | d
           | ^
           |
           |'\n' (line 6, column 3):
           | d
           |  ^
           |
           |'INDENT' (line 7, column 3):
           |  e
           |  ^
           |
           |'e' (line 7, column 3):
           |  e
           |  ^
           |
           |'\n' (line 7, column 4):
           |  e
           |   ^
           |
           |'DEDENT' (line 8, column 1):
           |
           |^
           |
           |'DEDENT' (line 8, column 1):
           |
           |^
           |
           |'EOI' (line 8, column 1):
           |
           |^
           |""".trim.replace("!\n", "\n").stripMargin
    }

  }
}
