//Be sure to include if dealer has blackjack at beginning of turn
//Include correct logic for when Double Down option is presentable


class BlackJack {
   val player = new Player(100)
   val dealer = new Dealer()
   val deck = Deck.createDeckOfCards
   val threshold = deck.size - 7
   var counter = 50
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
       if (counter >= threshold){
           Deck.shuffle(deck)
	   counter = 0
	}
   }

   def playGame = {
	shuffleIfNecessary
        declareBets
        assignCards
        dealer.timeToPlay = false
        var playersTurn = true
	if (player.hasBlackJack){
	   playersTurn = false
	   println(player)
	   println("You have BLACKJACK! Woot!!")
	   if (dealer.hasBlackJack){
		println("But Dealer has BlackJack too! It's a wash")
		player.takeWinnings(bet)
	   } else {
                player.takeWinnings(2 * bet)
	   }
	} else {
        	println(dealer)
        	while (playersTurn){
		   playersTurn = gameOptions
        	}

		if (!player.hasBusted){
		    playAgainstDealer
		} else { 
		    println("Oh no!  You busted! You lose!")
		}
	}
	printf("Player now has $%d left\n", player.amount)
	cleanup
   }

   private def cleanup = {
	bet = 0
        dealer.clearHand
        player.clearHand
   }

   private def gameOptions:Boolean = {
	   var turn = true
           println(player)
           println("What do you want to do? ('h' to Hit, 's' to Stay, or 'd' to Double Down)")
           val verdict = readChar()
           verdict match {
                case 'h' => {
                                player.takeCard(deck(counter))
                                incrementCounter
	   			if (player.hasBusted) turn = false
                            }
                case 's' => {
                                dealer.timeToPlay = true
                                turn = false
                            }
                case 'd' => {
                                bet += player.bet(bet)
                                player.takeCard(deck(counter))
                                incrementCounter
                                dealer.timeToPlay = true
                                turn = false
                            }
		case _ => println("You have pressed an invalid option! Please try again.")
           }
	println(player)
	turn
   }

   private def playAgainstDealer = {
        println(dealer)
        while (dealer.score < 17){
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
