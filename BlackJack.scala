class BlackJack(MINIMUM_BET:Int) {
   val dealer = new Dealer()
   val player = new Player(100)
   val playerStateMaintainer = new PlayerStateMaintainer(dealer, player)
   val deck = Deck()
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

   def placeBets = {
      printf("How much $$ do you want to bet?(MINIMUM BET = %d)\n", MINIMUM_BET)
      val money = readBetFromUserInput
      if (money >= MINIMUM_BET && player.hasSufficientFunds(money))
           player.addToBet(money)
      else if (money < MINIMUM_BET) {
           printf("Applying MINIMUM_BET ($%d) since $%d is not a valid bet\n",MINIMUM_BET, money)
           player.addToBet(MINIMUM_BET)
      }
      else {
           printf("Insufficient funds! You only have %d\nGiving you the minimum bet: %d\n",player.amount, MINIMUM_BET)
           player.addToBet(MINIMUM_BET)
      } 
   }

   def playGame = {
      deck.shuffleIfNecessary

      placeBets

      playerStateMaintainer.playersTurn

      assignCards

       	println(dealer+"\n"+player)
			if (player.hasBlackJack){
                playerStateMaintainer.dealersTurn
				println("You have BLACKJACK! Woot!!")
				if (dealer.hasBlackJack){
					println("But Dealer has BlackJack too! It's a wash")
					player.takeWinnings(player.bet)
				} else {
					player.takeWinnings(2 * player.bet)
				}
			} else {
        	while (player.hasPlayableHand && player.isPlayingTurn){
						GameOptions.displayGameOptionsTo(player)
						executeUserInput
        	}

		if (player.hasPlayableHand){
                    dealerDrawsUntilMinimumScore
                    player.compareAgainstDealer(dealer)
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
      val verdict:Int = readInt()
      GameOptions.playOptionsForPlayer(player,deck,verdict)
      println(player)
   }

   private def dealerDrawsUntilMinimumScore = {
        playerStateMaintainer.dealersTurn
        println(dealer)
        while (dealer.score < DEALER_SCORE_MINIMUM){
           dealer.takeCard(deck.removeCard)
           println(dealer)
        }
   }

}
