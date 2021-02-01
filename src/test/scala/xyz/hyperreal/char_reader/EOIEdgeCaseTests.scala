package xyz.hyperreal.char_reader

import utest._

object EOIEdgeCaseTests extends TestSuite with Testing {
  val tests: Tests = Tests {

    test("single line indent, space, then eoi") {
      indent("1\n 2") ==>
        """|'1' (line 1, column 1):
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
           |'DEDENT' (line 2, column 3):
           | 2
           |  ^
           |
           |'EOI' (line 2, column 3):
           | 2
           |  ^
           |""".trim.replace("!\n", "\n").stripMargin
    }

    test("double indent, then eoi") {
      indent("1\n 2\n  3") ==>
        """|'1' (line 1, column 1):
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
           |'INDENT' (line 3, column 3):
           |  3
           |  ^
           |
           |'3' (line 3, column 3):
           |  3
           |  ^
           |
           |'DEDENT' (line 3, column 4):
           |  3
           |   ^
           |
           |'DEDENT' (line 3, column 4):
           |  3
           |   ^
           |
           |'EOI' (line 3, column 4):
           |  3
           |   ^
           |""".trim.replace("!\n", "\n").stripMargin
    }

    test("single line indent, space, then empty") {
      indent("1\n a \n  ") ==>
        """|'1' (line 1, column 1):
           |1
           |^
           |
           |'\n' (line 1, column 2):
           |1
           | ^
           |
           |'INDENT' (line 2, column 2):
           | a !
           | ^
           |
           |'a' (line 2, column 2):
           | a !
           | ^
           |
           |' ' (line 2, column 3):
           | a !
           |  ^
           |
           |'\n' (line 2, column 4):
           | a !
           |   ^
           |
           |'EOI' (line 3, column 3):
           |  !
           |  ^
           |""".trim.replace("!\n", "\n").stripMargin
    }

    test("single line indent, then empty") {
      indent("1\n a\n  ") ==>
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
           |'EOI' (line 3, column 3):
           |  !
           |  ^
           |""".trim.replace("!\n", "\n").stripMargin
    }

    test("single line indent, then empty with nl") {
      indent("1\n a\n  \n") ==>
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
           |'\n' (line 3, column 3):
           |  !
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

  }
}
