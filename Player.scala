class Player extends AbstractPlayer {
        private var hands:List[Hand] = List(new Hand())
        private var bets:List[Int] = List(0)
        private var handIndex:Int = 0
        private var betsIndex:Int = 0
	var amount:Int = 0
        var bet:Int = bets(betsIndex)
        var hand:Hand = hands(handIndex)

   def this(initialAmount:Int) {
	this()
	amount = initialAmount	
   }

   override def clearHand = {
        hands = List(new Hand())
        bets = List(0)
        useHandAndBet(0)
   }

   def getTotalNumOfHands = hands.size

   override def takeCard(card:Card) = {
       super.takeCard(card)
       hands = hands.updated(handIndex, hand)
       if (hand.hasBusted) moveToNextHandOrStopTurn
   }

   def canDoubleDown:Boolean = {
	this.hasSufficientFunds(bet) && hand.count == 2
   }

   def doubleDown(card:Card) = {
       addToBet(bet)
       takeCard(card)
       
       if (!hand.hasBusted) moveToNextHandOrStopTurn
   }

   def stay = moveToNextHandOrStopTurn

   private def moveToNextHandOrStopTurn = {
       if (hands.size == (handIndex + 1)) timeToPlay = false
       else {
           handIndex += 1
           betsIndex += 1
           hand = hands(handIndex)
           bet = bets(betsIndex)
       }
   }

   def compareAgainstDealer(dealer:Dealer) = {
       if (!dealer.hasBusted && dealer.score > this.score){
           println("You lose!")
       } else if (dealer.score == this.score) {
       	   println("It's a push. You get your bet back")
       	   takeWinnings(this.bet)
       } else {
           println("You win!!")
           takeWinnings(2 * this.bet)
       }
   }

   def useHandAndBet(index:Int) = {
        handIndex = index
        betsIndex = index
        hand = hands(handIndex)
        bet = bets(betsIndex)
   }

   def canSplitHand:Boolean = {
        this.hasSufficientFunds(bet) && hand.canSplit
   }

   def splitHand = {

      import scala.collection.mutable.ArrayBuffer

      // split cards into new hands and include them into list of hands
      // handindex remains the same
      val (card1, card2) = hand.split
      val newHands = List(new Hand(card1), new Hand(card2))
      val (left, right) = hands.splitAt(handIndex)
      hands = left ++ newHands ++ right.tail

      //re-assign current hand
      hand = hands(handIndex)

      // handle bet for new hand
      // betsIndex does not move;  betIndex should only move when handIndex does
      bets = bets.updated(betsIndex + 1, bet)
      amount -= bet
      
   }

   def hasSufficientFunds(wager:Int):Boolean = {
	wager <= amount
   }

   def addToBet(wager:Int) = {
	amount -= wager
	bet += wager
        bets = bets.updated(betsIndex, bet)
   }

   def takeWinnings(winnings:Int) = amount += winnings

   def onlyHasOneCard = hand.count == 1

   def hasPlayableHand = {
       hands.exists( (hand) => !hand.hasBusted )
   }

   override def toString:String = {
	"You have the following cards: "+hand+", totaling "+hand.score
   }
}
