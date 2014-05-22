class BlackJack {
   val player = new Player(100)
   val dealer = new Dealer()
   val deck = Deck()
   val MINIMUM_BET = 5
   val DEALER_SCORE_MINIMUM = 17

   private def assignCards = {
        for (i <- 0 until 2){
           player.takeCard(deck.removeCard)
           dealer.takeCard(deck.removeCard)
        }
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
           player.addToBet(money)
      else {
           printf("Insufficient funds! You only have %d\nGiving you the minimum bet: %d\n",player.amount, MINIMUM_BET)
           player.addToBet(MINIMUM_BET)
      }
   }

   private def initializePlayerAndDealerState = {
      player.timeToPlay = true
      dealer.timeToPlay = false 
   }

   def playGame = {
      deck.shuffleIfNecessary

      placeBets

      initializePlayerAndDealerState

      assignCards

	if (player.hasBlackJack){
	   player.timeToPlay = false
	   println("You have BLACKJACK! Woot!!")
	   if (dealer.hasBlackJack){
		println("But Dealer has BlackJack too! It's a wash")
		player.takeWinnings(player.bet)
	   } else {
                player.takeWinnings(2 * player.bet)
	   }
	} else {
        	println(dealer+"\n"+player)
        	while (!player.hasBusted && player.timeToPlay){
                   GameOptions.displayGameOptionsTo(player)
                   executeUserInput
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
      throw new RuntimeException("Sorry! You do not have enough to continue the game. Good bye!")
   }

   private def cleanup = {
      dealer.clearHand
      player.clearHand
   }

   private def executeUserInput = {
      val verdict = readInt()
      playOptions(verdict)
      println(player)
   }

   private def playOptions:PartialFunction[Int, Unit] = {
      case 1 => {
      			player.takeCard(deck.removeCard)
      		}
      case 2 => {
			player.timeToPlay = false
                  }
      case 3 if player.canDoubleDown => {
				player.addToBet(player.bet) //doubling your original bet
				player.takeCard(deck.removeCard)
				player.timeToPlay = false
                  }
      case 4 if player.canSplitHand => {
                                //prompt user for additional bet for splitting
                                //player split cards
                                //player takes a card from deck
                  }
      case _ => println("You have pressed an invalid option! Please try again.")
   }

   private def playAgainstDealer = {
				dealer.timeToPlay = true
        println(dealer)
        while (dealer.score < DEALER_SCORE_MINIMUM){
           dealer.takeCard(deck.removeCard)
           println(dealer)
        }

        if (!dealer.hasBusted && dealer.score > player.score){
                println("You lose!")
        } else if (dealer.score == player.score) {
		println("It's a push. You get your bet back")
		player.takeWinnings(player.bet)
	} else {
                println("You win!!")
                player.takeWinnings(2 * player.bet)
        }
   }

}
