class Card(val display: CardFaceValue, val suit: String) {
	override def toString:String = {
		return display.symbol +suit
	}
}
