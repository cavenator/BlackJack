class CardFaceValue(val highValue:Int,val lowValue:Int,val symbol:String){
}

object CardFaceValue extends App {
	def apply(symbol:String,value:Int) = {
		val cardFaceValue = new CardFaceValue(value,0,symbol)
		cardFaceValue
	}
	def apply(symbol:String, value:Int, lowValue:Int) = {
		val cardFaceValue = new CardFaceValue(value, lowValue, symbol)
		cardFaceValue
	}
}
