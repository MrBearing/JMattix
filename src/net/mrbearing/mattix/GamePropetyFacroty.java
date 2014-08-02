package net.mrbearing.mattix;

import java.io.File;

import net.mrbearing.mattix.util.Element;
import net.mrbearing.mattix.util.XMLIO;

public class GamePropetyFacroty {
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
    //　TODO javaプロパティでの実装
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