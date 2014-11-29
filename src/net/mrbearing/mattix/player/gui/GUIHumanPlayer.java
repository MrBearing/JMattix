package net.mrbearing.mattix.player.gui;

import net.mrbearing.mattix.Mattix;
import net.mrbearing.mattix.player.AbstractPlayer;

public class GUIHumanPlayer extends AbstractPlayer {

  public GUIHumanPlayer(Mattix mattix) {
    super(mattix);
    // TODO FXMLの読み込み及び起動
    //
  }

  @Override
  protected int thinkMove(int[] legalMove) {
    //イベントを飛ばして有効化
    // TODO 自動生成されたメソッド・スタブ
    return 0;
  }

  @Override
  public void win() {
    // TODO 自動生成されたメソッド・スタブ

  }

  @Override
  public void lose() {
    // TODO 自動生成されたメソッド・スタブ

  }

}
