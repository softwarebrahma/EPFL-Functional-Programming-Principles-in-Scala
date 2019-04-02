package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if (c==0 || c==r) 1
      else pascal( c-1, r-1 ) + pascal( c, r-1 )
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      def checkBalance(charList: List[Char], count: Int): Boolean = {
        if(charList.isEmpty) count == 0
          else if(count < 0) false
          else if(charList.head == '(') checkBalance(charList.tail, count+1)
          else if(charList.head == ')') checkBalance(charList.tail, count-1)
          else checkBalance(charList.tail, count)
      }
      checkBalance(chars, 0)
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      def countChangeRecursively(sum: Int, changeList: List[Int]): Int = {
        if(sum==0) 1
        else if(sum<0) 0
        else if(changeList.isEmpty && sum>0) 0
        else countChangeRecursively(sum, changeList.tail) + countChangeRecursively(sum-changeList.head, changeList)
      }
      countChangeRecursively(money, coins)
    }
  }
