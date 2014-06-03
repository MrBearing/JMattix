package net.mrbearing.mattix;


/**
 * Mattixのプロパティを定義する。
 * 
 * GameElementFactoryに渡す
 * 
 * @author 岡本拓海
 * 
 */
public class GamePropety {
  //設計未定 プロパティで実装する

  /*
   * 実装予定の物 ゲームタイプ ADBANCED or BASIC? →実装しないFieldの大きさとTip数と種類のみで十分
   * Fieldの大きさ
   * Tipの数と種類(クロスチップ以外)
   */

  protected GamePropety() {
  }// デフォルトコンストラクタ殺し

  int FieldHeight = 0;
  int FieldWidth = 0;

  int[] tips;//生成方法はどうする？
  /**
   * フィールドの高さを返す
   * @return
   */
  public int getFieldHeight() {
    return FieldHeight;
  }
  /**
   * フィールドの幅を返す
   * @return
   */
  public int getFieldWidth() {
    return FieldWidth;
  }

  public int[] getTips() {
    return this.tips;
  }

  public String toString() {
    String ans = "";
    String BR = System.getProperty("line.separator");// 改行コード取得

    ans += "Height:" + this.FieldHeight + BR;
    ans += "Width:" + this.FieldWidth + BR;

    ans += "Tips:" + BR + "  ";
    for (int i = 0; i < tips.length; i++) {
      ans += this.tips[i] + " ";
    }

    return ans;
  }

}
