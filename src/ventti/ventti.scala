package ventti
import collection.mutable._
import Kortti._

object Ventti{
  var dealersHand=new ListBuffer()
  var playersHand=new ListBuffer()
  var deck=new ListBuffer()
  var valueOfHands=Array(0,0) //alkio 0==jakaja, alkio 1==pelaaja
}

class Ventti {
  var jatka="k"

  def isAce(kortti:Kortti):Boolean={
    kortti.getValue()==1
  }
  def laske(x: Int, y: Int): Int = x + y

  def cardValue(arvo:Int):Int= {
    arvo match {
      case arvo if arvo>10 =>10
      case _  => arvo
    }
  }

  def printHands(first:Boolean)={
      println("Jakaja sai kortit:")
      if(first){
  		println(Ventti.dealersHand.head.toString()+"[piilotettu]")
   		println("Käden arvo: "+Ventti.dealersHand.apply(0).getValue())
      }
      else{
         println(Ventti.dealersHand.toString())
         println("Käden arvo: "+Ventti.valueOfHands(0))
      }
  	println()
  	println("Sinä sait käden:")
  	println(Ventti.playersHand.toString())
  	println("Käden arvo: "+Ventti.valueOfHands(1))
  }
  /*
  def pelaa()={
    luoPakka()
    println("Tervetuloa pelaamaan venttiä.")
    println("Aluksi sinulle ja jakajalle jetaan kaksi korttia.")
    dealers_hand.append(jaaKortti())
  players_hand.append(jaaKortti())
     dealers_hand.append(jaaKortti())
  players_hand.append(jaaKortti())
  Ventti.valueOfHands(0)=laske(cardValue(Ventti.dealersHand.apply(0).getValue()), cardValue(Ventti.dealersHand.apply(1).getValue()))
    Ventti.valueOfHands(1)=laske(cardValue(playersHand.apply(0).getValue()),cardValue(playersHand.apply(1).getValue()))
    printHands(true)
println("Mitä haluat tehdä?")
println("1) Yksi kortti lisää.")
println("2) Jää tähän.")
val input=scala.io.StdIn.readInt()
if(input==1)
  moreCards()
  else
    dealersTurn()
}
*/
def askUser():Int={
	println("Mitä haluat tehdä?")
	println("1) Yksi kortti lisää.")
	println("2) Jää tähän.")
	print(": ")
	scala.io.StdIn.readInt()
}
while(jatka=="k"){
    println("Tervetuloa pelaamaan venttiä.")
    println("Aluksi sinulle ja jakajalle jaetaan kaksi korttia.")
	luoPakka()
	firstDeal()
	//TODO do something based on value of hand
	printHands(true)
	var input=askUser()
	while(input==1) {
	  moreCards()
	  printHands(true)
	  input=askUser()
		}
    dealersTurn()
    printHands(false)
    if(Ventti.valueOfHands(0)>=Ventti.valueOfHands(1))
    	println("Jakaja voitti.")
    else
    	println("Sinä voitit.")
    	println()
	print("Haluatko pelata uudestaan? (k/e): ")
	jatka=scala.io.StdIn.readLine()
}

}
