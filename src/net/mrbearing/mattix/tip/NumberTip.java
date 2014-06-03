package net.mrbearing.mattix.tip;

public class NumberTip implements ITip {

  private int value;

  public NumberTip(int val) {
    this.setValue(val);
  }

  @Override
  public int getValue() {
    return this.value;
  }

  /**
   * 値をセットするメソッド 他からいじられると嫌だからプライベート化
   * 
   * @param value
   */
  private void setValue(int value) {
    this.value = value;
  }

  public String toString() {
    String ans = "";
    if (this.getValue() <= -10)
      ans += ""; // "|-10|"絶対値10以下 マジックナンバーorz
    else if ((this.getValue() == 10) || (this.getValue() < 0))
      ans += ("."); // "|.-1|"0以下の時or "|.10|"の時
    else if ((0 < this.getValue()) || (this.getValue() < 10))
      ans += (".."); // "|..1|"0より大きく、10未満の時
    ans += getValue();

    return ans;
  }

  // @Override
  public NumberTip clone() {
    int rt_value = this.getValue();// deep_copyになるように。。。
    NumberTip rt = new NumberTip(rt_value);
    return rt;
  }

  /*
   * //プリミティブ型が参照コピーされてないことの確認。 //実行時は、setValueをpublicに public static void
   * main(String[] args){ NumberTip np1 = new NumberTip(10);
   * System.out.println("np1:"+np1);
   * 
   * NumberTip np2 = np1.clone(); System.out.println("np2"+np2);
   * 
   * np1.setValue(-1); System.out.println("np1:"+np1);
   * System.out.println("np2"+np2);
   * 
   * 
   * } /*
   */
}
