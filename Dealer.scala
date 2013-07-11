class Dealer {
	var hand:Hand = new Hand()
	var timeToPlay:Boolean = false

	def takeCard(card:Card) = {
		hand.add(card)
	}

	def hasBlackJack = {
	    score == 21 && hand.count == 2
	}

	def score = {
		hand.score
	}

	def showFirstCard:Card = {
		hand.cards(0)
	}

	def hasBusted:Boolean = {
		score > 21
	}

	def clearHand = {
		hand.clear
	}

	override def toString:String =  {
		if (!timeToPlay) "Dealer is showing a "+showFirstCard
		else "Dealer is showing "+hand+", totaling "+hand.score
	}
}
