package net.mrbearing.mattix.player;

import net.mrbearing.mattix.Mattix;
import net.mrbearing.mattix.util.MT19937;
/**
 * メルセンヌ・ツイスを用いて,ランダムに次手を決定するクラス。
 * 
 * 
 * @author 岡本拓海
 *
 */
public class TestPlayer extends Player {
  
  MT19937 mt;
  public TestPlayer(Mattix mattix) {
    super(mattix);
    this.mt = new MT19937();//メルセンヌ・ツイスタ生成
  }
  
 
  @Override
  public int thinkMove(int[] legalMove) {
    
    int ans_index = Math.abs(this.mt.nextInt())%legalMove.length;
    
    System.out.println("TestPlayer#thinkMove:ans_index:"+ legalMove[ans_index]);
    System.out.println("");
    return legalMove[ans_index];
  }


  @Override
  public void win() { /*何もしない*/ }


  @Override
  public void lose() { /*何もしない*/ }

}
