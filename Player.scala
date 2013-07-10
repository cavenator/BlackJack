class Player {
   var amount:Int = 0
   var hand: Hand = new Hand()

   def this(initialAmount:Int) {
	this()
	amount = initialAmount	
   }

   def takeCard(card:Card) = {
	hand.add(card)
   }

   def score = {
	hand.score
   }

   def hasSufficientFunds(wager:Int):Boolean = {
	wager > amount
   }

   def bet(wager:Int):Int = {
	amount -= wager
	wager
   }

   def takeWinnings(winnings:Int) = {
	amount += winnings
   }

   def clearHand = {
	hand.clear
   }

   override def toString:String = {
	"You have the following cards: "+hand+", totaling "+hand.score
   }
}
