object Deck {

   def createDeckOfCards = {
      val deck = for (suit <- CardSuit.getSuits; symbol <- Cards.symbolToSuitMapping.keys) yield Card(Cards.symbolToSuitMapping(symbol),symbol,suit)
      deck.toArray
   }

   def shuffle(cards:Array[Card]) = {
      import scala.util.Random
      val randomizer = new Random()
      for (i <- 0 until 52; j = randomizer.nextInt(52)) {
         val placeholder = cards(i)
	 cards(i) = cards(j)
	 cards(j) = placeholder
      }
   }
}
