object Main{

   def main(args:Array[String]) = {
	println("Hello and welcome to the BlackJack table! Your default amount is $100\n")
	val blackJackGame = new BlackJack()
	var isPlaying = true
	while (isPlaying){
 		if (!blackJackGame.doesPlayerHaveSufficientFundsToPlay){
			println("Sorry! You do not have enough to play the game. Good bye!")
			isPlaying = false
		}
		println("Would you like to play a new game? (y or n)")
		var verdict = readChar()
		verdict match {
			case 'y' => blackJackGame.playGame
			case 'n' => isPlaying = false
			case _ => println("You pressed an incorrect key! Please try again")
		}
	}
   }
}
