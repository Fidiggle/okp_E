package ventti
class Kortti(var arvo: Int, var maa: String){

  def getValue = arvo

  override def toString: String = {
    if (arvo == 1)
      s"[$maa A]"
    else if (arvo == 11)
      s"[$maa J]"
    else if (arvo == 12)
      s"[$maa Q]"
    else if (arvo == 13)
      s"[$maa K]"
    else
      s"[$maa $arvo]"
  }
}