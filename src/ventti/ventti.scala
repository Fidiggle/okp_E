package ventti
import collection.mutable._
import Kortti._

class Ventti {
  val dealers_hand=new ListBuffer()
  val players_hand=new ListBuffer()
  val deck=new ListBuffer()
  val valueOfHands=Array(0,0) //alkio 0==jakaja, alkio 1==pelaaja
  
  def isAce(kortti:Kortti):Boolean={
    kortti.getValue()==1
  }
  def isPicture(kortti:Kortti):Boolean={
    kortti.getValue()>10
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
  println(dealers_hand.head.toString()+"[piilotettu]")
   println("Käden arvo: "+dealers_hand.apply(0).getValue())
      }
      else{
         println(dealers_hand.toString())
             println("Käden arvo: "+valueOfHands(0))
      }
  println()
  println("Sinä sait käden:")
  println(players_hand.toString())
  println("Käden arvo: "+valueOfHands(1))
  }
  def pelaa()={
    luoPakka()
    println("Tervetuloa pelaamaan venttiä.")
    println("Aluksi sinulle ja jakajalle jetaan kaksi korttia.")
    dealers_hand.append(jaaKortti())
  players_hand.append(jaaKortti())
     dealers_hand.append(jaaKortti())
  players_hand.append(jaaKortti())
  valueOfHands(0)=laske(cardValue(dealers_hand.apply(0).getValue()), cardValue(dealers_hand.apply(1).getValue()))
    valueOfHands(1)=laske(cardValue(players_hand.apply(0).getValue()),cardValue(players_hand.apply(1).getValue()))
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
}