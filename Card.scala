class Card(val display: CardFaceValue, val suit: String) {
	def highValue:Int = {
		display.highValue
	}

	def lowValue:Int = {
		display.lowValue
	}

	def isAce:Boolean = {
		this.display.equals(CardTypeEnum.ACE)
	}

	override def toString:String = {
		return display.symbol +suit
	}
}
