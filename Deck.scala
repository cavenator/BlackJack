object Deck extends App {
		def createDeckOfCards = {
			for (suit <- CardSuit.getSuits; card <- FaceCardFactory.getCards)
				yield Card(card,suit)
		}

		def shuffle(cards:Seq[Card]) = {
			println("NOT YET IMPLEMENTED")
			// use Seq.patch(starting_index, Seq(card), 1)
		}
}
