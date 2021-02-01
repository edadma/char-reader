package xyz.hyperreal.char_reader

import utest._

object BasicIndentationTests extends TestSuite with Testing {
  val tests: Tests = Tests {

    test("single line indent") {
      indent("1\n a\n2") ==>
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
          |'DEDENT' (line 3, column 1):
          |2
          |^
          |
          |'2' (line 3, column 1):
          |2
          |^
          |
          |'EOI' (line 3, column 2):
          |2
          | ^
          |""".trim.stripMargin
    }

    test("second level indent, then no indent") {
      indent("1\n a\n  b\n2") ==>
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
          |'INDENT' (line 3, column 3):
          |  b
          |  ^
          |
          |'b' (line 3, column 3):
          |  b
          |  ^
          |
          |'\n' (line 3, column 4):
          |  b
          |   ^
          |
          |'DEDENT' (line 4, column 1):
          |2
          |^
          |
          |'DEDENT' (line 4, column 1):
          |2
          |^
          |
          |'2' (line 4, column 1):
          |2
          |^
          |
          |'EOI' (line 4, column 2):
          |2
          | ^
          |""".trim.stripMargin
    }

    test("second level indent double") {
      indent("""
          |1
          | 2
          |  a
          |  b
          | 3
          |4
          |""".trim.stripMargin) ==>
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
          |'INDENT' (line 3, column 3):
          |  a
          |  ^
          |
          |'a' (line 3, column 3):
          |  a
          |  ^
          |
          |'\n' (line 3, column 4):
          |  a
          |   ^
          |
          |'b' (line 4, column 3):
          |  b
          |  ^
          |
          |'\n' (line 4, column 4):
          |  b
          |   ^
          |
          |'DEDENT' (line 5, column 2):
          | 3
          | ^
          |
          |'3' (line 5, column 2):
          | 3
          | ^
          |
          |'\n' (line 5, column 3):
          | 3
          |  ^
          |
          |'DEDENT' (line 6, column 1):
          |4
          |^
          |
          |'4' (line 6, column 1):
          |4
          |^
          |
          |'\n' (line 6, column 2):
          |4
          | ^
          |
          |'EOI' (line 7, column 1):
          |
          |^
          |""".trim.stripMargin
    }

    test("first level indent double") {
      indent("""
          |1
          | 2
          | 3
          |4
          |""".trim.stripMargin) ==>
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
          |'3' (line 3, column 2):
          | 3
          | ^
          |
          |'\n' (line 3, column 3):
          | 3
          |  ^
          |
          |'DEDENT' (line 4, column 1):
          |4
          |^
          |
          |'4' (line 4, column 1):
          |4
          |^
          |
          |'\n' (line 4, column 2):
          |4
          | ^
          |
          |'EOI' (line 5, column 1):
          |
          |^
          |""".trim.stripMargin
    }

    test("single line indent, then eoi") {
      indent(
        """|1
           | 2
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
           |'DEDENT' (line 3, column 1):
           |
           |^
           |
           |'EOI' (line 3, column 1):
           |
           |^
           |""".trim.stripMargin
    }

    test("double line indent, then eoi") {
      indent(
        """|1
           | 2
           | 3
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
           |'3' (line 3, column 2):
           | 3
           | ^
           |
           |'\n' (line 3, column 3):
           | 3
           |  ^
           |
           |'DEDENT' (line 4, column 1):
           |
           |^
           |
           |'EOI' (line 4, column 1):
           |
           |^
           |""".trim.stripMargin
    }

    test("double indent, then eoi") {
      indent(
        """|1
           |  2
           |    3
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
           |'INDENT' (line 2, column 3):
           |  2
           |  ^
           |
           |'2' (line 2, column 3):
           |  2
           |  ^
           |
           |'\n' (line 2, column 4):
           |  2
           |   ^
           |
           |'INDENT' (line 3, column 5):
           |    3
           |    ^
           |
           |'3' (line 3, column 5):
           |    3
           |    ^
           |
           |'\n' (line 3, column 6):
           |    3
           |     ^
           |
           |'DEDENT' (line 4, column 1):
           |
           |^
           |
           |'DEDENT' (line 4, column 1):
           |
           |^
           |
           |'EOI' (line 4, column 1):
           |
           |^
           |""".trim.stripMargin
    }

  }
}
