abstract class AbstractPlayer {
		def hand:Hand
		var timeToPlay = true

   def clearHand = {
      hand.clear
   }

   def hasBlackJack = {
      score == 21 && hand.count == 2
   }

   def takeCard(card:Card) = {
      hand.add(card)
   }

   def score = {
      hand.score
   }


   def hasBusted:Boolean = {
      score > 21
   }
}

