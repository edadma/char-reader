package xyz.hyperreal.char_reader

import utest._

object SimpleTests extends TestSuite with Testing {
  val tests: Tests = Tests {

    test("empty") {
      indent("") ==>
        """
          |'EOI' (line 1, column 1):
          |
          |^
          |""".trim.stripMargin
    }

    test("single char") {
      indent("1") ==>
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

    test("char, space") {
      indent("1 ") ==>
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

    test("char, nl") {
      indent("1\n") ==>
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

    test("char, space, nl") {
      indent("1 \n") ==>
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

    test("char, nl, space") {
      indent("1\n ") ==>
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

    test("char, nl, space (noindent)") {
      noindent("1\n ") ==>
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

    test("char, space, nl, space") {
      indent("1 \n ") ==>
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

    test("char, space, nl, space (noindent)") {
      noindent("1 \n ") ==>
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

    test("char, nl, space, nl, space") {
      indent("1\n \n") ==>
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

    test("char, nl, space, nl, space (noindent)") {
      noindent("1\n \n") ==>
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

    test("char, nl, char, nl") {
      indent("1\n2\n") ==>
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
}
