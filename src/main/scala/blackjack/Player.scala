package blackjack

sealed case class HandAndBet(hand:Hand = new Hand(), bet:Int = 0)

class Player extends AbstractPlayer {
   private var handsWithBets:List[HandAndBet] = List(HandAndBet())
   private var index:Int = 0
   var amount:Int = 0
   var hand = handsWithBets(index).hand
   var bet = handsWithBets(index).bet

   def this(initialAmount:Int) {
	this()
	amount = initialAmount	
   }

   override def clearHand = {
        handsWithBets = List(HandAndBet())
        useHandAndBet(0)
   }

   override def takeCard(card:Card) = {
       super.takeCard(card)
       handsWithBets = handsWithBets.updated(index, HandAndBet(hand, bet))
       if (hand.hasBusted){
            println("You have busted with "+hand+", totaling "+hand.score)
            moveToNextHandOrStopTurn
       }
   }

   def canDoubleDown:Boolean = {
	this.hasSufficientFunds(bet) && hand.count == 2
   }

   def doubleDown(card:Card) = {
       addToBet(bet)
       takeCard(card)
       if (hand.hasBusted){
           println("You have busted with "+hand+", totaling "+hand.score)
       }
       moveToNextHandOrStopTurn
   }

   def stay = moveToNextHandOrStopTurn

   private def moveToNextHandOrStopTurn = {
       if (handsWithBets.size == (index + 1)) stopTurn
       else useHandAndBet(index + 1)
   }

   private def extractHandAndBet(handWithBet:HandAndBet) = handWithBet match {
        case HandAndBet(h,b) => this.hand = h; this.bet = b;
   }

   def compareAgainstDealer(dealer:Dealer) = {
       def compareHandWithDealers(handWithBet:HandAndBet, dealerHand:Hand) = {
               extractHandAndBet(handWithBet)
               val dealerScore = dealerHand.score
               val playerScore = hand.score
               if (!dealerHand.hasBusted && dealerScore > playerScore){
                   printf("You lose! dealer: %d, player: %d\n", dealerScore, playerScore)
               } else if (dealerScore == playerScore) {
               	   printf("It's a push (%d vs %d). You get your bet back\n", dealerScore, playerScore)
               	   takeWinnings(this.bet)
               } else {
                   printf("You win!! dealer: %d, you: %d\n", dealerScore, playerScore)
                   takeWinnings(2 * this.bet)
               }
       }
       handsWithBets.foreach{ (handWithBet) => compareHandWithDealers(handWithBet, dealer.hand) }
   }

   def useHandAndBet(index:Int) = {
        this.index = index
        extractHandAndBet(handsWithBets(index))
   }

   def canSplitHand:Boolean = {
        this.hasSufficientFunds(bet) && hand.canSplit
   }

   def splitHand(card1: Card, card2: Card) = {

      // split cards into new hands and include them into list of hands
      // index remains the same
      val (hand1, hand2) = hand.split(card1, card2)
      val newHands = List(HandAndBet(hand1, bet), HandAndBet(hand2,bet))
      val (left, right) = handsWithBets.splitAt(index)
      handsWithBets = left ++ newHands ++ right.tail

      //re-assign current hand
      hand = handsWithBets(index).hand

      //deduct additional bet from amount
      amount -= bet
      
   }

   def hasSufficientFunds(wager:Int):Boolean = {
	wager <= amount
   }

   def addToBet(wager:Int) = {
	amount -= wager
	bet += wager
        handsWithBets = handsWithBets.updated(index, HandAndBet(hand, bet))
   }

   def takeWinnings(winnings:Int) = amount += winnings

   def hasPlayableHand = {
       handsWithBets.exists( handAndBet => !handAndBet.hand.hasBusted )
   }

   override def toString:String = {
	"You have the following cards: "+hand+", totaling "+hand.score
   }
}
