package xyz.hyperreal.char_reader

import utest._

object EmptyLinesTests extends TestSuite with Testing {
  val tests: Tests = Tests {

    test("single line indent preceeded by blank line") {
      indent(
        """|1
           |
           | 2
           |""".stripMargin
      ) ==>
        """
          |'1' (line 1, column 1):
          |1
          |^
          |
          |'\n' (line 1, column 2):
          |1
          | ^
          |
          |'\n' (line 2, column 1):
          |
          |^
          |
          |'INDENT' (line 3, column 2):
          | 2
          | ^
          |
          |'2' (line 3, column 2):
          | 2
          | ^
          |
          |'\n' (line 3, column 3):
          | 2
          |  ^
          |
          |'DEDENT' (line 4, column 1):
          |
          |^
          |
          |'EOI' (line 4, column 1):
          |
          |^
          |""".trim.replace("!\n", "\n").stripMargin
    }

    test("double line indent with blank line in between") {
      indent(
        """|1
           | 2
           |
           | 3
           |""".stripMargin
      ) ==>
        """
          |'1' (line 1, column 1):
          |1
          |^
          |
          |'\n' (line 1, column 2):
          |1
          | ^
          |
          |'INDENT' (line 2, column 2):
          | 2
          | ^
          |
          |'2' (line 2, column 2):
          | 2
          | ^
          |
          |'\n' (line 2, column 3):
          | 2
          |  ^
          |
          |'\n' (line 3, column 1):
          |
          |^
          |
          |'3' (line 4, column 2):
          | 3
          | ^
          |
          |'\n' (line 4, column 3):
          | 3
          |  ^
          |
          |'DEDENT' (line 5, column 1):
          |
          |^
          |
          |'EOI' (line 5, column 1):
          |
          |^
          |""".trim.replace("!\n", "\n").stripMargin
    }

    test("double line indent with comment line in between") {
      indent(
        """|1
           | 2
           | #asdf
           | 3
           |""".stripMargin
      ) ==>
        """
          |'1' (line 1, column 1):
          |1
          |^
          |
          |'\n' (line 1, column 2):
          |1
          | ^
          |
          |'INDENT' (line 2, column 2):
          | 2
          | ^
          |
          |'2' (line 2, column 2):
          | 2
          | ^
          |
          |'\n' (line 2, column 3):
          | 2
          |  ^
          |
          |'\n' (line 3, column 7):
          | #asdf
          |      ^
          |
          |'3' (line 4, column 2):
          | 3
          | ^
          |
          |'\n' (line 4, column 3):
          | 3
          |  ^
          |
          |'DEDENT' (line 5, column 1):
          |
          |^
          |
          |'EOI' (line 5, column 1):
          |
          |^
          |""".trim.replace("!\n", "\n").stripMargin
    }

  }
}
