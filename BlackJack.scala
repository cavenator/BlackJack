class BlackJack {
   val player = new Player(100)
   val dealer = new Dealer()
   val deck = Deck.createDeckOfCards
   val REMAINING_CARDS_THRESHOLD_SCORE = 42  // the threshold score possible of remaining cards left before reshuffling is necessary
   var counter = 50
   val MINIMUM_BET = 5
   val DEALER_SCORE_MINIMUM = 17
   var bet = 0

   private def assignCards = {
        for (i <- 0 until 2){
           player.takeCard(deck(counter))
           incrementCounter
           dealer.takeCard(deck(counter))
           incrementCounter
        }
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
       val remainingDeckScore = Hand.calculateMinimumScorePossible(deck.takeRight(deck.size - counter))
       if (remainingDeckScore <= REMAINING_CARDS_THRESHOLD_SCORE){
          Deck.shuffle(deck)
	        counter = 0
      }
   }

	private def initializePlayerAndDealerState = {
		player.timeToPlay = true
		dealer.timeToPlay = false
	}

   def playGame = {
      shuffleIfNecessary

      placeBets

			initializePlayerAndDealerState

      assignCards

	if (player.hasBlackJack){
	   player.timeToPlay = false
	   println("You have BLACKJACK! Woot!!")
	   if (dealer.hasBlackJack){
		println("But Dealer has BlackJack too! It's a wash")
		player.takeWinnings(bet)
	   } else {
                player.takeWinnings(2 * bet)
	   }
	} else {
        	println(dealer+"\n"+player)
        	while (!player.hasBusted && player.timeToPlay){
		   gameOptions
        	}

		if (!player.hasBusted){
		    playAgainstDealer
		} else { 
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

   private def displayOptionsToUser = {
	val HITSTAY = "What do you want to do? ('h' to Hit or 's' to Stay)"
	val HITSTAYDOUBLEDOWN = "What do you want to do? ('h' to Hit, 's' to Stay, or 'd' to Double Down)"

	if (player.canDoubleDown(bet)) println(HITSTAYDOUBLEDOWN) else println(HITSTAY)

   }

   private def gameOptions = {
	displayOptionsToUser
        val verdict = readChar()
	playOptions(verdict)
	println(player)
   }

   private def playOptions:PartialFunction[Char, Unit] = {
      case 'h' => {
      			player.takeCard(deck(counter))
      			incrementCounter
      		}
      case 's' => {
			player.timeToPlay = false
                  }
      case 'd' if player.canDoubleDown(bet) => {
				bet += player.bet(bet)
				player.takeCard(deck(counter))
				incrementCounter
				player.timeToPlay = false
                  }
      case _ => println("You have pressed an invalid option! Please try again.")
   }

   private def playAgainstDealer = {
				dealer.timeToPlay = true
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
