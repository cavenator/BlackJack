package blackjack

abstract class AbstractPlayer {
		def hand:Hand
		private var timeToPlay = false

   def clearHand = {
      hand.clear
   }

   def isPlayingTurn = timeToPlay

   def stopTurn = {
      timeToPlay = false
   }

   def startTurn = {
    timeToPlay = true
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

