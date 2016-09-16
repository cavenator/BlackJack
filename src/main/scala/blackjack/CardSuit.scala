package blackjack

object CardSuit extends Enumeration {
	val SPADES = "\u2660"
	val HEARTS = "\u2665"
	val DIAMONDS = "\u2666"
	val CLUBS = "\u2663"

	def getSuits = {
		Seq(SPADES, HEARTS, DIAMONDS, CLUBS)
	}
}
