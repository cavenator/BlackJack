class CardFaceValue(val highValue:Int, val symbol:String, val lowValue:Int=0){
}

object CardFaceValue extends App {
	def apply(value:Int, symbol:String) = {
		val cardFaceValue = new CardFaceValue(value,symbol)
		cardFaceValue
	}
	def apply(value:Int, symbol:String, lowValue:Int) = {
		val cardFaceValue = new CardFaceValue(value, symbol, lowValue)
		cardFaceValue
	}
}
