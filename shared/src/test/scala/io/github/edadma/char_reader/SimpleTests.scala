package io.github.edadma.char_reader

import org.scalatest._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class SimpleTests extends AnyFreeSpec with Matchers with Testing {

  "empty" in {
    indent("") shouldBe
      """
        |'EOI' (line 1, column 1):
        |
        |^
        |""".trim.stripMargin
  }

  "single char" in {
    indent("1") shouldBe
      """
        |'1' (line 1, column 1):
        |1
        |^
        |
        |'EOI' (line 1, column 2):
        |1
        | ^
        |""".trim.stripMargin
  }

  "char, space" in {
    indent("1 ") shouldBe
      """
        |'1' (line 1, column 1):
        |1 !
        |^
        |
        |' ' (line 1, column 2):
        |1 !
        | ^
        |
        |'EOI' (line 1, column 3):
        |1 !
        |  ^
        |""".trim.replace("!\n", "\n").stripMargin
  }

  "char, nl" in {
    indent("1\n") shouldBe
      """
        |'1' (line 1, column 1):
        |1
        |^
        |
        |'\n' (line 1, column 2):
        |1
        | ^
        |
        |'EOI' (line 2, column 1):
        |
        |^
        |""".trim.replace("!\n", "\n").stripMargin
  }

  "char, space, nl" in {
    indent("1 \n") shouldBe
      """
        |'1' (line 1, column 1):
        |1 !
        |^
        |
        |' ' (line 1, column 2):
        |1 !
        | ^
        |
        |'\n' (line 1, column 3):
        |1 !
        |  ^
        |
        |'EOI' (line 2, column 1):
        |
        |^
        |""".trim.replace("!\n", "\n").stripMargin
  }

  "char, nl, space" in {
    indent("1\n ") shouldBe
      """
        |'1' (line 1, column 1):
        |1
        |^
        |
        |'\n' (line 1, column 2):
        |1
        | ^
        |
        |'EOI' (line 2, column 2):
        | !
        | ^
        |""".trim.replace("!\n", "\n").stripMargin
  }

  "char, nl, space (noindent)" in {
    noindent("1\n ") shouldBe
      """
        |'1' (line 1, column 1):
        |1
        |^
        |
        |'\n' (line 1, column 2):
        |1
        | ^
        |
        |' ' (line 2, column 1):
        | !
        |^
        |
        |'EOI' (line 2, column 2):
        | !
        | ^
        |""".trim.replace("!\n", "\n").stripMargin
  }

  "char, space, nl, space" in {
    indent("1 \n ") shouldBe
      """
        |'1' (line 1, column 1):
        |1 !
        |^
        |
        |' ' (line 1, column 2):
        |1 !
        | ^
        |
        |'\n' (line 1, column 3):
        |1 !
        |  ^
        |
        |'EOI' (line 2, column 2):
        | !
        | ^
        |""".trim.replace("!\n", "\n").stripMargin
  }

  "char, space, nl, space (noindent)" in {
    noindent("1 \n ") shouldBe
      """
        |'1' (line 1, column 1):
        |1 !
        |^
        |
        |' ' (line 1, column 2):
        |1 !
        | ^
        |
        |'\n' (line 1, column 3):
        |1 !
        |  ^
        |
        |' ' (line 2, column 1):
        | !
        |^
        |
        |'EOI' (line 2, column 2):
        | !
        | ^
        |""".trim.replace("!\n", "\n").stripMargin
  }

  "char, nl, space, nl, space" in {
    indent("1\n \n") shouldBe
      """
        |'1' (line 1, column 1):
        |1
        |^
        |
        |'\n' (line 1, column 2):
        |1
        | ^
        |
        |'\n' (line 2, column 2):
        | !
        | ^
        |
        |'EOI' (line 3, column 1):
        |
        |^
        |""".trim.replace("!\n", "\n").stripMargin
  }

  "char, nl, space, nl, space (noindent)" in {
    noindent("1\n \n") shouldBe
      """
        |'1' (line 1, column 1):
        |1
        |^
        |
        |'\n' (line 1, column 2):
        |1
        | ^
        |
        |' ' (line 2, column 1):
        | !
        |^
        |
        |'\n' (line 2, column 2):
        | !
        | ^
        |
        |'EOI' (line 3, column 1):
        |
        |^
        |""".trim.replace("!\n", "\n").stripMargin
  }

  "char, nl, char, nl" in {
    indent("1\n2\n") shouldBe
      """
        |'1' (line 1, column 1):
        |1
        |^
        |
        |'\n' (line 1, column 2):
        |1
        | ^
        |
        |'2' (line 2, column 1):
        |2
        |^
        |
        |'\n' (line 2, column 2):
        |2
        | ^
        |
        |'EOI' (line 3, column 1):
        |
        |^
        |""".trim.replace("!\n", "\n").stripMargin
  }

}
