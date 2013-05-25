import scala.collection.mutable.Seq
class Hand {
	var cards = scala.collection.mutable.ArrayBuffer[Card]()

	def add(card:Card) = {
		cards += card
	}

	override def toString:String = {
		cards.mkString
	}
}
