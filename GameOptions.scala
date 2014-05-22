object GameOptions {

    val DEFAULT_OPTIONS = "What would you like to do? Press 1 for Hit, Press 2 for Stay"

    def displayGameOptionsTo(player:Player) = {
       val stringBuilder = new StringBuilder(DEFAULT_OPTIONS)
       if (player.canDoubleDown) stringBuilder.append(", Press 3 for Double Down")
       if (player.canSplitHand) stringBuilder.append(", Press 4 to Split")
       println(stringBuilder.append(".").toString)
    }
   
}
