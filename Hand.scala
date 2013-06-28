import scala.collection.mutable.Seq
class Hand {
	var cards = scala.collection.mutable.ArrayBuffer[Card]()

	def add(card:Card) = {
		cards += card
	}

	private def calculateHand(useHighValueForAce:Boolean):Int = {

		def determineCardValue(card:Card, highValueUsed:Boolean):Int = {
			if (card.isAce && !highValueUsed) card.lowValue
			else card.highValue
		}

		def calculationHelper(card:Card, remainingHand:List[Card], useHighValue:Boolean):Int = {
			if (remainingHand.isEmpty) return determineCardValue(card, useHighValue)
			else if (card.isAce) determineCardValue(card, useHighValue) + calculationHelper(remainingHand.head, remainingHand.tail, false)
			else determineCardValue(card, useHighValue) + calculationHelper(remainingHand.head, remainingHand.tail, useHighValue)
		}

		val listOfCards = cards.toList
		calculationHelper(listOfCards.head, listOfCards.tail,useHighValueForAce)
	}
	
	def score:Int = {
		val computedScore = calculateHand(true)
		if (computedScore <= 21) computedScore
		else calculateHand(false)
	}

	def clear = {
		cards.clear
	}

	override def toString:String = {
		cards.mkString
	}
}
