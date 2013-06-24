class Dealer {
	var hand:Hand = new Hand()
	var timeToPlay:Boolean = false

	def takeCard(card:Card) = {
		hand.add(card)
	}

	def score = {
		hand.score
	}

	def showFirstCard:Card = {
		hand.cards(0)
	}

	override def toString:String =  {
		if (timeToPlay) "Dealer is showing a "+showFirstCard
		else "Dealer is showing "+hand+", totaling "+hand.score
	}
}
