class Card(val display: CardFaceValue, val suit: String) {
	def highValue:Int = {
		display.highValue
	}

	def lowValue:Int = {
		display.lowValue
	}

	def isAce:Boolean = {
		this.display.equals(FaceCardFactory.ACE)
	}

	override def toString:String = {
		return display.symbol +suit
	}
}

object Card extends App {

	def apply(display:CardFaceValue,suit:String) = {
		val card = new Card(display, suit)
		card
	}
}
