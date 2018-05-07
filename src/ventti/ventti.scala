package ventti
import collection.mutable._
import Kortti

class Ventti {
  val dealers_hand=new ListBuffer()
  val players_hand=new ListBuffer()
  val deck=new ListBuffer()
  val valueOfHands=Array(0,0) 
  
  def isAce(kortti:Kortti){
    kortti.getValue()==1
  }
  def isPicture(kortti:Kortti){
    kortti.getValue()>10
  }
  
}