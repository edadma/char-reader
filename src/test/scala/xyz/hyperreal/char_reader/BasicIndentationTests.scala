package xyz.hyperreal.char_reader

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import xyz.hyperreal.char_reader.Testing._

class BasicIndentationTests extends AnyFreeSpec with Matchers {

  "single line indent" in {
    indent("1\n a\n2") shouldBe
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

  "second level indent, then no indent" in {
    indent("1\n a\n  b\n2") shouldBe
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

  "second level indent double" in {
    indent("""
        |1
        | 2
        |  a
        |  b
        | 3
        |4
        |""".trim.stripMargin) shouldBe
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

  "first level indent double" in {
    indent("""
             |1
             | 2
             | 3
             |4
             |""".trim.stripMargin) shouldBe
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

  "single line indent, then eoi" in {
    indent(
      """|1
         | 2
         |""".stripMargin
    ) shouldBe
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

//  "double line indent, then eoi" in {
//    indent(
//      """|1
//         | 2
//         | 3
//         |""".stripMargin
//    ) shouldBe
//      """|<1> (line 1, column 1):
//         |1
//         |^
//         |
//         |<\n> (line 1, column 2):
//         |1
//         | ^
//         |
//         |<2, 2, INDENT>
//         |<2> (line 2, column 2):
//         | 2
//         | ^
//         |
//         |<\n> (line 2, column 3):
//         | 2
//         |  ^
//         |
//         |<3> (line 3, column 2):
//         | 3
//         | ^
//         |
//         |<\n> (line 3, column 3):
//         | 3
//         |  ^
//         |
//         |<4, 1, DEDENT>
//       """.trim.stripMargin
//  }

}
