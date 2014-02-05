import scala.collection.mutable.Seq
class Hand {
	var cards = scala.collection.mutable.ArrayBuffer[Card]()

	def add(card:Card) = {
		cards += card
	}

        private def computePossibleScores:List[Int] = {
		def mergeScores(acc: Set[Int], card:Card) = {
			for { i <- acc; j <- card.values } yield i + j
		}
		cards.foldLeft(Set(0))(mergeScores).toList
	}

	def score:Int = {
		val possibleScores = computePossibleScores
		val (goodScores, badScores) = possibleScores.partition( x => x <= 21)
		if (goodScores.isEmpty) badScores.min
		else goodScores.max
	}

	def clear = {
		cards.clear
	}

	def count:Int = {
		cards.size
	}

	override def toString:String = {
		cards.mkString(" ")
	}
}
