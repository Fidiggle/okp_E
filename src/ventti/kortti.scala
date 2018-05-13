package ventti
class Kortti(var arvo: Int, var maa: String){

  def getValue = arvo

  override def toString: String = {
    if (this.getValue == 1)
      s"($maa, Ässä)"
    if (this.getValue == 11)
      s"($maa, Jätkä)"
    if (this.getValue == 12)
      s"($maa, Rouva)"
    if (this.getValue == 13)
      s"($maa, Kuningas)"
    else
      s"($maa, ,$arvo)"
  }
}