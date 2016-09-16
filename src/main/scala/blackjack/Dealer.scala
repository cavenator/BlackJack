package blackjack

class Dealer extends AbstractPlayer {
	override val hand:Hand = new Hand()

	def showFirstCard:Card = {
		hand.cards(0)
	}

	override def toString:String =  {
		if (!isPlayingTurn) "Dealer is showing a "+showFirstCard
		else "Dealer is showing "+hand+", totaling "+hand.score
	}
}
