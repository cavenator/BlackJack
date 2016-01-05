object Main{

   def main(args:Array[String]) = {
	println("Hello and welcome to the BlackJack table! Your default amount is $100\n")
	val blackJack = new BlackJack(5)
	var isPlaying = true
        var validOption = false
	while (isPlaying){
                try {
			blackJack.playGame
                } catch {
			case x: RuntimeException => println(x.getMessage())
						    System.exit(1)
		}
		validOption = false
                while (!validOption){
			println("Would you like to play a new game? (y or n)")
			var verdict = readChar()
			verdict match {
				case 'y' => isPlaying = true; validOption = true
				case 'n' => isPlaying = false; validOption = true
				case _ => println("You pressed an incorrect key! Please try again")
			}
		}
	}
   }
}
