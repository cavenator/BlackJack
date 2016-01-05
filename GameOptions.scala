object GameOptions {

    val DEFAULT_OPTIONS = "What would you like to do? Press 1 for Hit, Press 2 for Stay"

    def displayGameOptionsTo(player:Player) = {
       val stringBuilder = new StringBuilder(DEFAULT_OPTIONS)
       if (player.canDoubleDown) stringBuilder.append(", Press 3 for Double Down")
       if (player.canSplitHand) stringBuilder.append(", Press 4 to Split")
       println(stringBuilder.append(".").toString)
    }

    def playOptionsForPlayer(player:Player, deck:Deck, option: Int) = {
	option match {
	   case 1 => player.takeCard(deck.removeCard)
	   case 2 => player.stay
	   case 3 if player.canDoubleDown => player.doubleDown(deck.removeCard)
	   case 4 if player.canSplitHand => player.splitHand(deck.removeCard, deck.removeCard)
	   case _ => println("You have pressed an invalid option! Please try again.")
	}
    }   
}
