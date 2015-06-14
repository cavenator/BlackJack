import scala.collection.mutable.Seq
class Hand {

	var cards = List[Card]()
  val MAX_SCORE = 21

  def this(cards: List[Card]){
    this()
    this.cards = cards
  }

	def add(card:Card) = {
		cards = cards :+ card
	}

  def canSplit:Boolean = count == 2 && cards(0).symbol == cards(1).symbol

  def hasBlackJack = score == MAX_SCORE && count == 2

  def split(card1: Card, card2: Card):(Hand, Hand) = {
		val (left, right) = cards.splitAt(1)
		(Hand(left :+ card1), Hand(right :+ card2))
	}

  def hasBusted = score > MAX_SCORE

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
		cards = List[Card]()
	}

	def count:Int = {
		cards.size
	}

	override def toString:String = {
		cards.mkString(" ")
	}
}

object Hand {
   def apply(cards: List[Card]) = new Hand(cards)
   def calculateMinimumScorePossible(cards: List[Card]):Int = {
      val hand = Hand(cards)
      hand.score
   }
}
