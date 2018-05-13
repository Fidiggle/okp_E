//Yhden pelaajan ventti cli-käyttöliittymällä.
//Copyright 2018 Juhani Vähä-Mäkilä & Eero Nirhamo. All Rights Reserved.
package ventti
import collection.mutable._
import util.control.Breaks._
import scala.io._
import scala.util.Random

//Staattisten muuttujien emulointiin.
object Ventti extends App {
  var dealersHand=new ListBuffer[Kortti]()
  var playersHand=new ListBuffer[Kortti]()
  var deck=new ListBuffer[Kortti]()
  var valueOfHands=Array(0,0) //alkio 0==jakaja, alkio 1==pelaaja
}

class Ventti {
  var jatka="k"

	//Tarkistaa onko pelaajalla tai jakajalla ässä.
	//Parametri: True mikäli pelaaja, False jos jakaja.
	def hasAce(player:Boolean):Boolean={
		if(player)
			Ventti.playersHand.apply(0).getValue==1 || Ventti.playersHand.apply(1).getValue==1
		else
			Ventti.dealersHand.apply(0).getValue==1 || Ventti.dealersHand.apply(1).getValue==1
	  }
	def laske(x: Int, y: Int): Int = x + y

	//Palauttaa kortin oikean arvon, jotta laskeminen menisi oikein.
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
	  		if(hasAce(false) && Ventti.dealersHand.head.getValue==1)
	   			println("Käden arvo: 1/11")
	   		else
	   			println("Käden arvo: "+cardValue(Ventti.dealersHand.apply(0).getValue))
	      }
	      else{
	         println(Ventti.dealersHand.toString())
	         println("Käden arvo: "+Ventti.valueOfHands(0))
	      }
	  	println()
	  	println("Sinä sait käden:")
		println(Ventti.playersHand.toString())
	  	if(hasAce(true) && Ventti.valueOfHands(1)<21)
			println("Käden arvo: "+Ventti.valueOfHands(1)+"/"+Ventti.valueOfHands(1)+10)
		else
			println("Käden arvo: "+Ventti.valueOfHands(1))
	  }

	def askUser():Int={
		println("Mitä haluat tehdä?")
		println("1) Yksi kortti lisää.")
		println("2) Jää tähän.")
		print(": ")
		StdIn.readInt()
	}
	//Pelin ensimmäinen jako.
	def firstDeal()={
		Ventti.dealersHand.append(jaaKortti())
		Ventti.playersHand.append(jaaKortti())
		Ventti.dealersHand.append(jaaKortti())
		Ventti.playersHand.append(jaaKortti())
	}
	//Jakajan vuoro.
	def dealersTurn()={
		while(Ventti.valueOfHands(0)<17){
			val kortti=jaaKortti()
			Ventti.dealersHand.append(kortti)
			Ventti.valueOfHands(0)+=cardValue(kortti.getValue)
		}
	}
	//Pakan luominen.
	def luoPakka(): Unit ={
		Ventti.deck.clear()
		val maat = List[String] ("♠","♥","♦","♣")
		for(i <- 1 to 13){
			for(maa <- maat){
				Ventti.deck += new Kortti(i,maa)
			}
		}
		Random.shuffle(Ventti.deck)
	}
	//Kortin jakaminen pakasta
	def jaaKortti(): Kortti ={
		var topdeck = Ventti.deck.head
		Ventti.deck.remove(0)
		topdeck
	}
	//Pelaaja ottaa uuden kortin.
	def moreCards()={
		val kortti=jaaKortti()
		Ventti.playersHand.append(kortti)
		Ventti.valueOfHands(1)+=cardValue(kortti.getValue)
	}
	def main(args: Array[String]) {
		//Itse pelin looppi.
		while(jatka=="k"){
    		println("Tervetuloa pelaamaan venttiä.")
    		println("Aluksi sinulle ja jakajalle jaetaan kaksi korttia.")
    		println("Tarkoitus on päästä mahdollisimman lähelle 21:tä kuitenkaan ylittämättä sitä.")
    		println("Kortit J, Q ja K ovat arvoltaan 10, ässä joko 1 tai 11, muut kortit ovat arvonsa mukaisia.")
			luoPakka()
			firstDeal()
			Ventti.valueOfHands(0)=laske(cardValue(Ventti.dealersHand.apply(0).getValue), cardValue(Ventti.dealersHand.apply(1).getValue) )
				Ventti.valueOfHands(1)=laske(cardValue(Ventti.playersHand.apply(0).getValue),cardValue(Ventti.playersHand.apply(1).getValue))
			printHands(true)
			if(Ventti.valueOfHands(0)+10==21 || Ventti.valueOfHands(1)+10==21){
					if(Ventti.valueOfHands(0)+10==21){
						println("Jakaja voitti.")
						}
					else{
						println("Sinä voitit.")
						}
				}
				else{
					var input=askUser()
					while(input==1) {
						moreCards()
						printHands(true)
						println()
						input=askUser()
						}
				if(Ventti.valueOfHands(1)>21){
					println("Sinä hävisit.")
					}
			    	else {
			    		dealersTurn()
			       		printHands(false)
    					if(Ventti.valueOfHands(0)>=Ventti.valueOfHands(1)){
    						println("Jakaja voitti.")
    						println()
    						}
    					else{
    						println("Sinä voitit.")
    						println()
    						}
    					}
				print("Haluatko pelata uudestaan? (k/e): ")
				jatka=StdIn.readLine()
				println()
				}
		}
	}
}
