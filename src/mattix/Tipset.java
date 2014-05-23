package mattix;

import java.util.ArrayList;

import mattix.tip.CrossTip;
import mattix.tip.ITip;
import mattix.tip.NumberTip;

public class Tipset {

  // Tipset factory 実装？

  private CrossTip crosstip;

  private ArrayList<ITip> tips;

  /**
   * crosstipと一緒に入れちゃえ
   * 
   * 
   * @return t
   */
  public ArrayList<ITip> getTips() {
    return tips;
  }

  /*
   * public CrossTip getCrossTip() { return crosstip; }
   */

  private Tipset() {
    /* デフォルトコンストラクタ潰し */
    this.tips = new ArrayList<ITip>();
    this.crosstip = new CrossTip();
  }

  public static class TipsetFactory {
    public static Tipset create(GamePropety gp) {
      Tipset product = new Tipset();
      for (int i = 0; i < gp.getTips().length; i++) {
        NumberTip id = new NumberTip(gp.getTips()[i]);
        product.tips.add(id);
      }
      product.tips.add(product.crosstip);// 一緒に入れちゃう。

      return product;
    }
  }
}
