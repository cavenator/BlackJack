class Deck(val cards:Array[Card]){
   private var counter = 50
   private val RESHUFFLING_THRESHOLD_SCORE = 42 // the threshold score required for reshuffling

   def shuffleIfNecessary = {
      val remainingDeckScore = Hand.calculateMinimumScorePossible(cards.takeRight(cards.size - counter).toList)
      if (remainingDeckScore < RESHUFFLING_THRESHOLD_SCORE){
         shuffle
         counter = 0
      }
   }

   def removeCard:Card = {
      val card = cards(counter)
      counter += 1
      card
   }

   private def shuffle = {
      import scala.util.Random
      val randomizer = new Random()
			println("Cards need to be reshuffled .....")
      for (i <- 0 until cards.size; j = randomizer.nextInt(52)) {
         val placeholder = cards(i)
         cards(i) = cards(j)
         cards(j) = placeholder
      }
			println("Cards done shuffling!")
   }
}

object Deck {
   def apply() = new Deck(createDeckOfCards)

   def createDeckOfCards = {
      val deck = for (suit <- CardSuit.getSuits; symbol <- Cards.symbolToSuitMapping.keys) yield Card(Cards.symbolToSuitMapping(symbol),symbol,suit)
      deck.toArray
   }
}
