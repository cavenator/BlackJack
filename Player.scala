class Player extends AbstractPlayer {
	var amount:Int = 0
        var bet:Int = 0
	override val hand: Hand = new Hand()

   def this(initialAmount:Int) {
	this()
	amount = initialAmount	
   }

   override def clearHand = {
        super.clearHand
        bet = 0
   }

   def canDoubleDown:Boolean = {
	this.hasSufficientFunds(bet) && hand.count == 2
   }

   def canSplitHand:Boolean = {
        this.hasSufficientFunds(bet) && hand.canSplit
   }

   def hasSufficientFunds(wager:Int):Boolean = {
	wager <= amount
   }

   def addToBet(wager:Int) = {
	amount -= wager
	bet += wager
   }

   def takeWinnings(winnings:Int) = amount += winnings

   override def toString:String = {
	"You have the following cards: "+hand+", totaling "+hand.score
   }
}
