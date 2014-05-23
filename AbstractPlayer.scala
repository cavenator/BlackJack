abstract class AbstractPlayer {
		def hand:Hand
		var timeToPlay = true

   def clearHand = {
      hand.clear
   }

   def hasBlackJack = {
      hand.hasBlackJack
   }

   def takeCard(card:Card):Unit = {
      hand.add(card)
   }

   def score = {
      hand.score
   }


   def hasBusted:Boolean = {
      hand.hasBusted
   }
}

