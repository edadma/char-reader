package xyz.hyperreal.ncr

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import xyz.hyperreal.ncr.Testing._

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

//  "second level indent, then no indent" in {
//    indent("1\n a\n  b\n2") shouldBe
//      """
//        |<1> (line 1, column 1):
//        |1
//        |^
//        |
//        |<\n> (line 1, column 2):
//        |1
//        | ^
//        |
//        |<2, 2, INDENT>
//        |<a> (line 2, column 2):
//        | a
//        | ^
//        |
//        |<\n> (line 2, column 3):
//        | a
//        |  ^
//        |
//        |<3, 3, INDENT>
//        |<b> (line 3, column 3):
//        |  b
//        |  ^
//        |
//        |<\n> (line 3, column 4):
//        |  b
//        |   ^
//        |
//        |<4, 1, DEDENT>
//        |<4, 1, DEDENT>
//        |<2> (line 4, column 1):
//        |2
//        |^
//        |""".trim.stripMargin
//  }
//
//  "second level indent double" in {
//    indent("""
//        |1
//        | 2
//        |  a
//        |  b
//        | 3
//        |4
//        |""".trim.stripMargin) shouldBe
//      """
//        |<1> (line 1, column 1):
//        |1
//        |^
//        |
//        |<\n> (line 1, column 2):
//        |1
//        | ^
//        |
//        |<2, 2, INDENT>
//        |<2> (line 2, column 2):
//        | 2
//        | ^
//        |
//        |<\n> (line 2, column 3):
//        | 2
//        |  ^
//        |
//        |<3, 3, INDENT>
//        |<a> (line 3, column 3):
//        |  a
//        |  ^
//        |
//        |<\n> (line 3, column 4):
//        |  a
//        |   ^
//        |
//        |<b> (line 4, column 3):
//        |  b
//        |  ^
//        |
//        |<\n> (line 4, column 4):
//        |  b
//        |   ^
//        |
//        |<5, 2, DEDENT>
//        |<3> (line 5, column 2):
//        | 3
//        | ^
//        |
//        |<\n> (line 5, column 3):
//        | 3
//        |  ^
//        |
//        |<6, 1, DEDENT>
//        |<4> (line 6, column 1):
//        |4
//        |^
//        |
//        |<\n> (line 6, column 2):
//        |4
//        | ^
//        |""".trim.stripMargin
//  }
//
//  "first level indent double" in {
//    indent("""
//             |1
//             | 2
//             | 3
//             |4
//             |""".trim.stripMargin) shouldBe
//      """
//        |<1> (line 1, column 1):
//        |1
//        |^
//        |
//        |<\n> (line 1, column 2):
//        |1
//        | ^
//        |
//        |<2, 2, INDENT>
//        |<2> (line 2, column 2):
//        | 2
//        | ^
//        |
//        |<\n> (line 2, column 3):
//        | 2
//        |  ^
//        |
//        |<3> (line 3, column 2):
//        | 3
//        | ^
//        |
//        |<\n> (line 3, column 3):
//        | 3
//        |  ^
//        |
//        |<4, 1, DEDENT>
//        |<4> (line 4, column 1):
//        |4
//        |^
//        |
//        |<\n> (line 4, column 2):
//        |4
//        | ^
//        |""".trim.stripMargin
//  }
//
//  "single line indent, then eoi" in {
//    indent(
//      """|1
//         | 2
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
//         |<3, 1, DEDENT>
//       """.trim.stripMargin
//  }
//
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
