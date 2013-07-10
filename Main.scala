object Main{

  val player = new Player(100)
  val dealer = new Dealer()
  val deck = Deck.createDeckOfCards
  val threshold = deck.size - 7
  var counter = 0
  val MINIMUM_BET = 5
  var bet = 0

   def main(args:Array[String]) = {
	println("Hello and welcome to the BlackJack table! Your default amount is $100\n")
	Deck.shuffle(deck)
	var isPlaying = true
	while (isPlaying){
		if (player.amount < MINIMUM_BET){
			println("Sorry! You do not have enough to play the game. Good bye!")
			isPlaying = false
		}
		println("Would you like to play a new game? (y or n)")
		var verdict = readChar()
		verdict match {
			case 'y' => if (player.amount >= MINIMUM_BET ) playGame
			case 'n' => isPlaying = false
			case _ => println("You pressed an incorrect key! Please try again")
		}
	}
   }

   def playGame = {
	if (counter >= threshold)
	    Deck.shuffle(deck)
	declareBets
	assignCards
	dealer.timeToPlay = false
	println(dealer)
	var playersTurn = true
	while (playersTurn){
	   println(player)
	   println("What do you want to do? ('h' to Hit, 's' to Stay, 'd' to Double Down or 'q' to quit)")
	   val verdict = readChar()
	   verdict match {
		case 'h' => {
				player.takeCard(deck(counter))
				incrementCounter
			    }
		case 's' => {
				dealer.timeToPlay = true
				 playersTurn = false
 			    }
		case 'd' => {
				bet += player.bet(bet)
				player.takeCard(deck(counter))
				incrementCounter
				dealer.timeToPlay = true
				playersTurn = false
			    }
	   }
	}

	println(dealer)
	while (dealer.score <= 21){
		dealer.takeCard(deck(counter))
		incrementCounter
	}

	println(dealer)

	if (dealer.score > player.score){
		println("You lose!")
	} else {
		println("You win!!")
		player.takeWinnings(bet)
	}

	dealer.clearHand
	player.clearHand
   }

   def assignCards = {
	for (i <- 0 until 2){
	   player.takeCard(deck(counter))
	   incrementCounter
	   dealer.takeCard(deck(counter))
	   incrementCounter
	}
   }

   def incrementCounter = {
	counter += 1
   }

   def declareBets = {
	printf("How much $$ do you want to bet?(MINIMUM BET = %d)\n", MINIMUM_BET)
	val money = readInt()
	if (player.hasSufficientFunds(money))
	   bet = player.bet(money)
	else {
	   printf("Insufficient funds! You only have %d\n",player.amount)
	   println("giving you minimum bet ....")
	   bet = player.bet(MINIMUM_BET)
	 }
   }
}
