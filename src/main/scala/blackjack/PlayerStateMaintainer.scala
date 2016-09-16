package blackjack

class PlayerStateMaintainer(dealer: AbstractPlayer, player:AbstractPlayer) {
    def dealersTurn = {
        player.stopTurn
        dealer.startTurn
    }

    def playersTurn = {
        player.startTurn
        dealer.stopTurn
    }
}
