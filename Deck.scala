object Deck extends App {
		def createDeckOfCards = {
			val deck = for (suit <- CardSuit.getSuits; card <- FaceCardFactory.getCards)
				yield Card(card,suit)
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
