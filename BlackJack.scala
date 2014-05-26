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

       	println(dealer+"\n"+player)
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
        	while (player.hasPlayableHand && player.timeToPlay){
                   if (player.onlyHasOneCard){
                      println("Player dealt another card for hand resulting from split.")
                      player.takeCard(deck.removeCard)
                      println(player)
                   } else {
                      GameOptions.displayGameOptionsTo(player)
                      executeUserInput
                   }
        	}

		if (player.hasPlayableHand){
		    playAgainstDealer
		} else { 
		    println("Oh no!  You lose!")
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

   //TODO:  Modify flow in here to deal with the possibility of multiple hands due to split.
   private def playOptions:PartialFunction[Int, Unit] = {
      case 1 => {
      			player.takeCard(deck.removeCard)
      		}
      case 2 => {
                        player.stay 
                  }
      case 3 if player.canDoubleDown => {
                                player.doubleDown(deck.removeCard)
                  }
      case 4 if player.canSplitHand => {
                                player.splitHand
                                player.takeCard(deck.removeCard)
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

        for (i <- 0 until player.getTotalNumOfHands){
            player.useHandAndBet(i)
            player.compareAgainstDealer(dealer)
        }
        
   }

}
