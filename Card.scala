sealed case class Card(val values: Set[Int], val symbol: String, val suit: String){
   override def toString = symbol+suit
}

object Cards {
   val symbolToSuitMapping = Map("2" -> Set(2), 
				 "3" -> Set(3),
				 "4" -> Set(4),
				 "5" -> Set(5),
				 "6" -> Set(6),
				 "7" -> Set(7),
				 "8" -> Set(8),
				 "9" -> Set(9),
				 "10" -> Set(10), 
				 "J" -> Set(10),
				 "Q" -> Set(10),
				 "K" -> Set(10), 
				 "A" -> Set(1,11))
}
