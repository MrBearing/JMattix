package net.mrbearing.mattix.player;

import java.util.Random;

import net.mrbearing.mattix.Mattix;
/**
 *ランダムに次手を決定するクラス。
 * 
 * 
 * @author 岡本拓海
 *
 */
public class TestPlayer extends Player {
  
  Random mt;
  public TestPlayer(Mattix mattix) {
    super(mattix);
    this.mt = new Random();
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
