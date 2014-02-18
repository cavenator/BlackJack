class Dealer extends AbstractPlayer {
	override val hand:Hand = new Hand()
	timeToPlay = false

	def showFirstCard:Card = {
		hand.cards(0)
	}

	override def toString:String =  {
		if (!timeToPlay) "Dealer is showing a "+showFirstCard
		else "Dealer is showing "+hand+", totaling "+hand.score
	}
}
