package mattix.tip;

/**
 * シングルトンパターンを使ってみた →辞めた: 局面の表現が出来なくなる。。。
 * 
 * 
 * @author 岡本拓海
 * 
 */
public class CrossTip implements ITip {
  public static final int CROSS_TIP_VALUE = 0;

  // private static CrossTip crosstip = new CrossTip();//インスタンスは使うときに生成される。

  // constructor privateなので外から使えない
  // private CrossTip(){}

  /**
   * 
   * ---@return CrossTip クロスチップのインスタンスを返す
   * 
   * public static CrossTip getInstance(){ return crosstip; }
   */
  public static boolean isCrossTip(ITip t) {
    return t.getValue() == CrossTip.CROSS_TIP_VALUE;
  }

  @Override
  public int getValue() {
    return CrossTip.CROSS_TIP_VALUE;
  }

  public String toString() {
    return ".+.";
  }

  public CrossTip clone() {
    CrossTip rt = new CrossTip();
    return rt;
  }

}
