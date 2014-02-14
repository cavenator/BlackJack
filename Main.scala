object Main{

   def main(args:Array[String]) = {
	println("Hello and welcome to the BlackJack table! Your default amount is $100\n")
	val blackJack = new BlackJack()
	var isPlaying = true
	blackJack.playGame
	while (isPlaying){
		println("Would you like to play a new game? (y or n)")
		var verdict = readChar()
		verdict match {
			case 'y' => blackJack.playGamesIfFundsAreSufficient
			case 'n' => isPlaying = false
			case _ => println("You pressed an incorrect key! Please try again")
		}
	}
   }
}
