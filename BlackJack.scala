//Be sure to include if dealer has blackjack in this code
//Be sure to include logic for when both dealer and player push

class BlackJack {
   val player = new Player(100)
   val dealer = new Dealer()
   val deck = Deck.createDeckOfCards
   val threshold = deck.size - 7
   var counter = 0
   val MINIMUM_BET = 5
   var bet = 0

   def doesPlayerHaveSufficientFundsToPlay:Boolean = {
	player.amount >= MINIMUM_BET
   }

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

   private def declareBets = {
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

   private def shuffleIfNecessary = {
       if (counter >= threshold)
           Deck.shuffle(deck)
   }

   def playGame = {
	shuffleIfNecessary
        declareBets
        assignCards
        dealer.timeToPlay = false
        var playersTurn = true
	if (player.hasBlackJack){
	   playersTurn = false
	}
        println(dealer)
        while (playersTurn){
           println(player)
           println("What do you want to do? ('h' to Hit, 's' to Stay, 'd' to Double Down or 'q' to quit)")
           val verdict = readChar()
           verdict match {
                case 'h' => {
                                player.takeCard(deck(counter))
                                incrementCounter
	   			if (player.hasBusted) playersTurn = false
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
		case 'q' => {
				System.exit(1)
			    }
		case _ => println("You have pressed an invalid option! Please try again.")
           }
        }

	if (player.hasBlackJack){
	    println(player)
	    println("Player has BLACKJACK! Woot!!")
            player.takeWinnings(bet + MINIMUM_BET)
	}
	else if (!player.hasBusted){
	    playAgainstDealer
	} else { 
	    println("Oh no!  You busted! You lose!")
	}
	printf("Player now has $%d left\n", player.amount)
	cleanup
   }

   private def cleanup = {
	bet = 0
        dealer.clearHand
        player.clearHand
   }

   private def playAgainstDealer = {
        println(dealer)
        while (dealer.score <= 17){
                dealer.takeCard(deck(counter))
                incrementCounter
        	println(dealer)
        }

        if (!dealer.hasBusted && dealer.score > player.score){
                println("You lose!")
        } else {
                println("You win!!")
                player.takeWinnings(bet)
        }
   }

}
