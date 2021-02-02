package xyz.hyperreal.char_reader

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class EOIEdgeCaseTests extends AnyFreeSpec with Matchers with Testing {

  "single line indent, space, then eoi" in {
    indent("1\n 2") shouldBe
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

  "double indent, then eoi" in {
    indent("1\n 2\n  3") shouldBe
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

  "single line indent, space, then empty" in {
    indent("1\n a \n  ") shouldBe
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

  "single line indent, then empty" in {
    indent("1\n a\n  ") shouldBe
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

  "single line indent, then empty with nl" in {
    indent("1\n a\n  \n") shouldBe
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
