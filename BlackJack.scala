//TODO:  Come up with a more clever way to determine when the deck needs to be reshuffled.
//	 Also, take the isPlayersTurn out and put it into the Player class. it belongs there.

class BlackJack {
   val player = new Player(100)
   val dealer = new Dealer()
   val deck = Deck.createDeckOfCards
   val threshold = deck.size - 7
   var counter = 50
   val MINIMUM_BET = 5
   val DEALER_SCORE_MINIMUM = 17
   var bet = 0
   var isPlayersTurn = true

   private def assignCards = {
        for (i <- 0 until 2){
           player.takeCard(deck(counter))
           incrementCounter
           dealer.takeCard(deck(counter))
           incrementCounter
        }
        dealer.timeToPlay = false
   }

   private def incrementCounter = {
        counter += 1
   }

   private def readBetFromUserInput = {
      try {
      	   readInt()
      } catch {
	   case t:Throwable => printf("Unable to determine amount! Applying minimum bet ($%d)\n", MINIMUM_BET); MINIMUM_BET
      }
   }

   private def placeBets = {
        printf("How much $$ do you want to bet?(MINIMUM BET = %d)\n", MINIMUM_BET)
	val money = readBetFromUserInput
	if (player.hasSufficientFunds(money))
           bet = player.bet(money)
        else {
           printf("Insufficient funds! You only have %d\nGiving you the minimum bet: %d\n",player.amount, MINIMUM_BET)
           bet = player.bet(MINIMUM_BET)
	}
   }

   private def shuffleIfNecessary = {
       if (counter >= threshold){
           Deck.shuffle(deck)
	   counter = 0
	}
   }

   def playGamesIfFundsAreSufficient = applyFunctionIfPlayerHasSufficientFunds(playGame)
   

   def playGame = {
	shuffleIfNecessary

        placeBets

        assignCards

        isPlayersTurn = true

	if (player.hasBlackJack){
	   isPlayersTurn = false
	   println("You have BLACKJACK! Woot!!")
	   if (dealer.hasBlackJack){
		println("But Dealer has BlackJack too! It's a wash")
		player.takeWinnings(bet)
	   } else {
                player.takeWinnings(2 * bet)
	   }
	} else {
        	println(dealer+"\n"+player)
        	while (!player.hasBusted && isPlayersTurn){
		   gameOptions
        	}

		if (!player.hasBusted){
		    playAgainstDealer
		} else { 
		    isPlayersTurn = false
		    println("Oh no!  You busted! You lose!")
		}
	}
	printf("Player now has $%d left\n", player.amount)
	applyFunctionIfPlayerHasSufficientFunds(cleanup)
   }

   private def applyFunctionIfPlayerHasSufficientFunds(carryOn: Unit ) = if (!player.hasSufficientFunds(MINIMUM_BET)) sayGoodByeAndExit else carryOn   

   private def sayGoodByeAndExit = {
	println("Sorry! You do not have enough to continue the game. Good bye!")
	System.exit(1);
   }

   private def cleanup = {
	bet = 0
        dealer.clearHand
        player.clearHand
   }

//TODO: Find a way to consolidate playThreeOptions and hitOrStay into one method. Code is duplicated and it is a smell.
//	Look into what can be done with partial functions.
   private def gameOptions = {
	   if (player.canDoubleDown(bet)){
		playThreeOptions
	   } else {
		hitOrStay
	   }
   }

   private def playThreeOptions = {
           println("What do you want to do? ('h' to Hit, 's' to Stay, or 'd' to Double Down)")
           val verdict = readChar()
           verdict match {
                case 'h' => {
                                player.takeCard(deck(counter))
                                incrementCounter
                            }
                case 's' => {
				isPlayersTurn = false
                                dealer.timeToPlay = true
                            }
                case 'd' => {
                                bet += player.bet(bet)
                                player.takeCard(deck(counter))
                                incrementCounter
				isPlayersTurn = false
                                dealer.timeToPlay = true
                            }
		case _ => println("You have pressed an invalid option! Please try again.")
           }
	println(player)
   }

   private def hitOrStay = {
           println("What do you want to do? ('h' to Hit or 's' to Stay)")
           val verdict = readChar()
           verdict match {
                case 'h' => {
                                player.takeCard(deck(counter))
                                incrementCounter
                            }
                case 's' => {
				isPlayersTurn = false
                                dealer.timeToPlay = true
                            }
		case _ => println("You have pressed an invalid option! Please try again.")
           }
	println(player)
   }

   private def playAgainstDealer = {
        println(dealer)
        while (dealer.score < DEALER_SCORE_MINIMUM){
                dealer.takeCard(deck(counter))
                incrementCounter
        	println(dealer)
        }

        if (!dealer.hasBusted && dealer.score > player.score){
                println("You lose!")
        } else if (dealer.score == player.score) {
		println("It's a push. You get your bet back")
		player.takeWinnings(bet)
	} else {
                println("You win!!")
                player.takeWinnings(2 * bet)
        }
   }

}
