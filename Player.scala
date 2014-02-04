class Player extends AbstractPlayer {
   var amount:Int = 0
   override val hand: Hand = new Hand()

   def this(initialAmount:Int) {
	this()
	amount = initialAmount	
   }

   def canDoubleDown(bet:Int):Boolean = {
	this.hasSufficientFunds(bet)
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

   override def toString:String = {
	"You have the following cards: "+hand+", totaling "+hand.score
   }
}
