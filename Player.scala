class Player {
   var amount:Int = 0
   var hand: Hand = new Hand()

   def this(initialAmount:Int) {
	this()
	amount = initialAmount	
   }

   def hasBlackJack:Boolean = {
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

   def canDoubleDown(bet:Int):Boolean = {
	this.hasSufficientFunds(bet) && hand.count == 2
   }

   def hasSufficientFunds(wager:Int):Boolean = {
	wager <= amount
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
