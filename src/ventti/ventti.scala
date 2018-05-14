//Yhden pelaajan ventti cli-käyttöliittymällä.
//Copyright 2018 Juhani Vähä-Mäkilä & Eero Nirhamo. All Rights Reserved.
package ventti
import collection.mutable._
import util.control.Breaks._
import scala.io._
import scala.util.Random

//Staattisten muuttujien emulointiin.
object Peli {
  var dealersHand=new ListBuffer[Kortti]()
  var playersHand=new ListBuffer[Kortti]()
  var deck=new ListBuffer[Kortti]()
  var valueOfHands=Array(0,0) //alkio 0==jakaja, alkio 1==pelaaja
}

object Ventti extends App{

//	def main(args: Array[String]):Unit ={
	pelaa()
//	}
	//Tarkistaa onko pelaajalla tai jakajalla ässä.
	//Parametri: True mikäli pelaaja, False jos jakaja.
	def hasAce(player:Boolean):Boolean={
		if(player)
			Peli.playersHand.apply(0).getValue==1 || Peli.playersHand.apply(1).getValue==1
		else
			Peli.dealersHand.apply(0).getValue==1 || Peli.dealersHand.apply(1).getValue==1
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
	  		println(Peli.dealersHand.head.toString()+"[piilotettu]")
	  		if(hasAce(false) && Peli.dealersHand.head.getValue==1)
	   			println("Käden arvo: 1/11")
	   		else
	   			println("Käden arvo: "+cardValue(Peli.dealersHand.apply(0).getValue))
	      }
	      else{
	         println(Peli.dealersHand.toString())
	         println("Käden arvo: "+Peli.valueOfHands(0))
	      }
	  	println()
	  	println("Sinä sait käden:")
		println(Peli.playersHand.toString())
	  	if((hasAce(true) && Peli.valueOfHands(1)<21) && Peli.playersHand.size<=2)
			println("Käden arvo: "+Peli.valueOfHands(1)+"/"+Integer.toString(Peli.valueOfHands(1)+10))
		else
			println("Käden arvo: "+Peli.valueOfHands(1))
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
		Peli.dealersHand.append(jaaKortti())
		Peli.playersHand.append(jaaKortti())
		Peli.dealersHand.append(jaaKortti())
		Peli.playersHand.append(jaaKortti())
	}
	//Jakajan vuoro.
	def dealersTurn()={
		while(Peli.valueOfHands(0)<17){
			val kortti=jaaKortti()
			Peli.dealersHand.append(kortti)
			Peli.valueOfHands(0)+=cardValue(kortti.getValue)
		}
	}
	//Pakan luominen.
	def luoPakka(): Unit ={
		Peli.deck.clear()
		val maat = List[String] ("♠","♥","♦","♣")
		for(i <- 1 to 13){
			for(maa <- maat){
				Peli.deck += new Kortti(i,maa)
			}
		}
		Random.shuffle(Peli.deck)
	}
	//Kortin jakaminen pakasta
	def jaaKortti(): Kortti ={
		var topdeck = Peli.deck.head
		Peli.deck.remove(0)
		topdeck
	}
	//Pelaaja ottaa uuden kortin.
	def moreCards()={
		val kortti=jaaKortti()
		Peli.playersHand.append(kortti)
		Peli.valueOfHands(1)+=cardValue(kortti.getValue)
	}
	def pelaa()={
		var jatka="k"
		//Itse pelin looppi.
		while(jatka=="k"){
		  Peli.playersHand.clear()
		  Peli.dealersHand.clear()
    		println("Tervetuloa pelaamaan venttiä.")
    		println("Aluksi sinulle ja jakajalle jaetaan kaksi korttia.")
    		println("Tarkoitus on päästä mahdollisimman lähelle 21:tä kuitenkaan ylittämättä sitä.")
    		println("Kortit J, Q ja K ovat arvoltaan 10, ässä joko 1 tai 11, muut kortit ovat arvonsa mukaisia.")
			luoPakka()
			firstDeal()
			Peli.valueOfHands(0)=laske(cardValue(Peli.dealersHand.apply(0).getValue), cardValue(Peli.dealersHand.apply(1).getValue) )
				Peli.valueOfHands(1)=laske(cardValue(Peli.playersHand.apply(0).getValue),cardValue(Peli.playersHand.apply(1).getValue))
			printHands(true)
			if(Peli.valueOfHands(0)+10==21 || Peli.valueOfHands(1)+10==21){
					if(Peli.valueOfHands(0)+10==21){
						println("Jakaja voitti.")
						}
					else{
						println("Sinä voitit.")
						}
				}
				else{
					var input=askUser()
					while(input==1 && Peli.valueOfHands(1)<=21) {
						moreCards()
						printHands(true)
						println()
						if(Peli.valueOfHands(1)>=21)
							input=2
						else
							input=askUser()

						}
				if(Peli.valueOfHands(1)>21){
					println("Sinä hävisit.")
					}
			    	else {
			    		dealersTurn()
			       		printHands(false)
    					if(Peli.valueOfHands(0)>=Peli.valueOfHands(1)){
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
