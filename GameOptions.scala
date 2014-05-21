object GameOptions {

    val DEFAULT_OPTIONS = "What would you like to do? Press 1 for Hit, Press 2 for Stay"

    def displayGameOptionsTo(player:Player, bet: Int) = {
       val stringBuilder = new StringBuilder(DEFAULT_OPTIONS)
       if (player.canDoubleDown(bet)) stringBuilder.append(", Press 3 for Double Down")
       println(stringBuilder.append(".").toString)
    }
   
}
