package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(2)
    val s5 = singletonSet(3)
    val s6 = singletonSet(6)
    val s4singleton = singletonSet(4)
    val s5singleton = singletonSet(5)
    val negative1000singleton = singletonSet(-1000)
    val positive1000singleton = singletonSet(1000)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }
  
  test("intersect contains all elements in both sets") {
    new TestSets {
      val s2s4Intersect = intersect(s2, s4)
      val s3s5Intersect = intersect(s3, s5)
      val s1s2union = union(s1, s2)
      val s3s4union = union(s3, s4)
      val s1s2unionintersects3s4union = intersect(s1s2union, s3s4union)
      assert(!contains(s2s4Intersect, 1), "s2s4Intersect doesn't contain 1")
      assert(contains(s2s4Intersect, 2), "s2s4Intersect contains 2")
      assert(!contains(s2s4Intersect, 3), "s2s4Intersect doesn't contain 3")
      assert(contains(s3s5Intersect, 3), "s2s4Intersect contains 3")
      assert(!contains(s3s5Intersect, 2), "s2s4Intersect doesn't contain 2")
      assert(contains(s1s2unionintersects3s4union, 2), "s1s2unionintersects3s4union contains 2")
      assert(!contains(s1s2unionintersects3s4union, 3), "s1s2unionintersects3s4union doesn't contain 3")
    }
  }
  
  test("diff contains elements in one set that are not in the other") {
    new TestSets {
      val s1s2union = union(s1, s2)
      val s1s2uniondiffs4 = diff(s1s2union, s4)
      val s3s4union = union(s3, s4)
      val s3s4uniondiffs1s2union = diff(s3s4union, s1s2union)
      val s1s2uniondiffs3s4union = diff(s1s2union, s3s4union)
      assert(contains(s1s2uniondiffs4, 1), "s1s2uniondiffs4 contains elements in s1s2union not in s4 which is 1")
      assert(!contains(s1s2uniondiffs4, 2), "s1s2uniondiffs4 contains elements in s1s2union not in s4 which is not 2")
      assert(contains(s3s4uniondiffs1s2union, 3), "s3s4uniondiffs1s2union contains elements in s3s4union not in s1s2union which is 3")
      assert(!contains(s3s4uniondiffs1s2union, 2), "s3s4uniondiffs1s2union contains elements in s3s4union not in s1s2union which is not 2")
      assert(contains(s1s2uniondiffs3s4union, 1), "s1s2uniondiffs3s4union contains elements in s1s2union not in s3s4union which is 1")
      assert(!contains(s1s2uniondiffs3s4union, 2), "s1s2uniondiffs3s4union contains elements in s1s2union not in s3s4union which is not 2")
    }
  }
  
  test("filter contains subset of a set for which condition holds") {
    new TestSets {
      val s1s2s3union = union(union(s1,s2),s3)
      val s4s5union = union(s4,s5)
      val s1s2s3unionfilteredBys4s5union = filter(s1s2s3union, s4s5union)
      assert(contains(s1s2s3unionfilteredBys4s5union, 2) && contains(s1s2s3unionfilteredBys4s5union, 3), "s1s2s3unionfilteredBys4s5union contains 2 & 3 ")
      assert(!contains(s1s2s3unionfilteredBys4s5union, 1), "s1s2s3unionfilteredBys4s5union doesn't contain 1")
      val s1s2s3unionfilteredOfEverything = filter(s1s2s3union, x => false)
      assert(!contains(s1s2s3unionfilteredOfEverything, 1) && !contains(s1s2s3unionfilteredOfEverything, 2) && !contains(s1s2s3unionfilteredOfEverything, 3), 
          "s1s2s3unionfilteredOfEverything contains nothing in s1s2s3union : 1, 2 or 3")
      val s1s2s3unionfilteredOfNothing = filter(s1s2s3union, x => true)
      assert(contains(s1s2s3unionfilteredOfNothing, 1) && contains(s1s2s3unionfilteredOfNothing, 2) && contains(s1s2s3unionfilteredOfNothing, 3), 
          "s1s2s3unionfilteredOfNothing contains everything in s1s2s3union : 1, 2 & 3")
    }
  }
  
  test("Checks whether all integers in set satisfy condition") {
    new TestSets {
      val s1s2s3union = union(union(s1,s2),s3)
      val setOf1To6 = union(s1s2s3union, union(union(s4singleton, s5singleton), s6))
      val s1s2s3unionfilteredOfEverything = filter(s1s2s3union, x => false)
      val setOfMinus10001To6And1000 = union(union(negative1000singleton, setOf1To6), positive1000singleton)
      assert(forall(setOf1To6, x => x < 10), "All integers in set setOf1To6 are less than 10")
      assert(!forall(setOf1To6, x => x % 2 == 0), "Not all integers in set setOf1To6 is divisible by 2")
      assert(forall(setOf1To6, x => x <= 6), "All integers in set setOf1To6 are less than or equal to 6")
      assert(!forall(setOf1To6, x => x < 6), "Not all integers in set setOf1To6 are less than 6")
      assert(forall(setOf1To6, x => true), "Set setOf1To6 should satisfy the condition true for everything")
      assert(!forall(setOf1To6, x => false), "Set setOf1To6 shouldn't satisfy the condition false for everything")
      assert(forall(s1s2s3unionfilteredOfEverything, x => x < 10), "Empty set s1s2s3unionfilteredOfEverything should satisfy the condition contains integers less than 10")
      assert(forall(s1s2s3unionfilteredOfEverything, x => false), "Empty set s1s2s3unionfilteredOfEverything should satisfy the condition false for everything")
      assert(forall(s1s2s3unionfilteredOfEverything, x => true), "Empty set s1s2s3unionfilteredOfEverything should satisfy the condition true for everything")
      assert(forall(setOfMinus10001To6And1000, x => x <= 1000), "All integers in set setOfMinus10001To6And1000 less than or equal to 1000")
      assert(!forall(setOfMinus10001To6And1000, x => x <= 6), "Not all integers in set setOfMinus10001To6And1000 less than or equal to 6")
      assert(forall(setOfMinus10001To6And1000, x => x >= -1000 && x <= 1000 && (x == 1 || x % 2 == 0 || x % 3 == 0 || x % 5 == 0)), 
          "All integers in set setOfMinus10001To6And1000 are between -1000 & 1000 & divisble by 2, 3 or 5")
      assert(!forall(setOfMinus10001To6And1000, x => x >= -1000 && x <= 1000 && x % 2 == 0), 
          "Not all integers in set setOfMinus10001To6And1000 are between -1000 & 1000 and divisble by 2")
    }
  }
  
  test("Checks whether there exists an integer in set that satisfy condition") {
    new TestSets {
      val s1s2s3union = union(union(s1,s2),s3)
      val setOf1To6 = union(s1s2s3union, union(union(s4singleton, s5singleton), s6))
      val s1s2s3unionfilteredOfEverything = filter(s1s2s3union, x => false)
      val setOfMinus10001To6And1000 = union(union(negative1000singleton, setOf1To6), positive1000singleton)
      assert(exists(setOfMinus10001To6And1000, x => x >= -1000 && x <= 1000 && x % 2 == 0), 
          "Set setOfMinus10001To6And1000 has atleast one integer between -1000 & 1000 & divisble by 2")
      assert(exists(setOfMinus10001To6And1000, x => x == 1000), "Set setOfMinus10001To6And1000 has atleast one integer equal to 1000")
      assert(!exists(setOfMinus10001To6And1000, x => x == 1001), "Set setOfMinus10001To6And1000 has no integer equal to 1001")
      assert(!exists(setOfMinus10001To6And1000, x => x > 1000), "Set setOfMinus10001To6And1000 has no integer greater than 1000")
      assert(exists(setOf1To6, x => true), "Set setOf1To6 has atleast one integer that satisfy the condition true for everything")
      assert(!exists(setOf1To6, x => false), "Set setOf1To6 has no integer that can satisfy the condition false for everything")
      assert(!exists(s1s2s3unionfilteredOfEverything, x => x < 10),"Empty set s1s2s3unionfilteredOfEverything cannot have any integer less than 10")
      assert(!exists(s1s2s3unionfilteredOfEverything, x => false), "Empty set s1s2s3unionfilteredOfEverything cannot satisfy the condition false for everything")
      assert(!exists(s1s2s3unionfilteredOfEverything, x => true), "Empty set s1s2s3unionfilteredOfEverything cannot satisfy the condition true for everything")
    }
  }
  
  test("Mapped set contains elements transformed by function") {
    new TestSets {
      val s1s2s3union = union(union(s1,s2),s3)
      val setOf1To6 = union(s1s2s3union, union(union(s4singleton, s5singleton), s6))
      val s1s2s3unionfilteredOfEverything = filter(s1s2s3union, x => false)
      val setOfMinus10001To6And1000 = union(union(negative1000singleton, setOf1To6), positive1000singleton)
      assert( forall(map(setOf1To6, x => x + 10), x => x >= 11 && x <= 16), "Mapping of set containing 1 to 6 to add 10 is a set containing 11 to 16" )
      assert( forall(map(setOf1To6, x => x * 10), x => x >= 10 && x <= 60), "Mapping of set containing 1 to 6 to multiply 10 is a set containing 10 to 60" )
      assert( !exists(map(s1s2s3unionfilteredOfEverything, x => x - 1), x => x >= -1000 && x <= 1000), "Mapping of empty set is an empty set")
      assert( forall(map(setOfMinus10001To6And1000, x => x / 2), x => x == -500 || (x >= 0 && x <= 3) ||  x == 500), 
          "Mapping of set containing -1000, 1 to 6 & 1000 to divide by 2 is a set containing -500, 0, 1, 2, 3, 500" )
    }
  }


}
