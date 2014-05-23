package mattix;

import java.io.File;

import mattix.util.Element;
import mattix.util.XMLIO;

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
  //TODO javaプロパティでの実装

  /*
   * 実装予定の物 ゲームタイプ ADBANCED or BASIC? →実装しないFieldの大きさとTip数と種類のみで十分 Fieldの大きさ
   * Tipの数と種類(クロスチップ以外)
   */

  private GamePropety() {
  }// デフォルトコンストラクタ殺し

  private int FieldHeight = 0;
  private int FieldWidth = 0;

  private int[] tips;//生成方法はどうする？

  public int getFieldHeight() {
    return FieldHeight;
  }

  public int getFieldWidth() {
    return FieldWidth;
  }

  public int[] getTips() {
    return this.tips;
  }

  /**
     * 
     * 
     */
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

  public static class GamePropetyFacroty {// TODO こいつ別ファイルに分離したいなぁ....

    public static final String KEY_GAME_PROPETY = "GamePropety";
    public static final String KEY_FIELD = "Field";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_WIDTH = "width";
    public static final String KEY_TIPS = "Tips";
    public static final String KEY_TIP = "Tip";

    /**
     * ファイルのデコードとGamePropetyの生成を行う
     * 
     * @param file
     * @return GamePropety {@link GamePropety}
     */
    public static GamePropety create(File file) {
      // TODO Propety の妥当性の検証 ex:チップの数が足りるか等

      GamePropety ans = new GamePropety();

      try {
        Element element = XMLIO.read(file);
        Element fieldElement = element.getChild(GamePropetyFacroty.KEY_FIELD);
        // System.out.println(fieldElement);
        ans.FieldHeight = Integer.parseInt(fieldElement.getChild(
            GamePropetyFacroty.KEY_HEIGHT).getValue());
        ans.FieldWidth = Integer.parseInt(fieldElement.getChild(
            GamePropetyFacroty.KEY_WIDTH).getValue());

        Element[] tipsElement = element.getChild(GamePropetyFacroty.KEY_TIPS)
            .getChildren();
        ans.tips = new int[tipsElement.length];

        for (int i = 0; i < tipsElement.length; i++) {
          ans.tips[i] = Integer.parseInt(tipsElement[i].getValue());
        }

      } catch (Exception e) {
        // handle exception
        e.printStackTrace();
      }
      return ans;
    }
  }

}
