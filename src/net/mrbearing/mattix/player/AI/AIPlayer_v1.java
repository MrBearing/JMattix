package net.mrbearing.mattix.player.AI;

import net.mrbearing.mattix.IllegalMoveException;
import net.mrbearing.mattix.Mattix;
import net.mrbearing.mattix.player.Player;
import net.mrbearing.mattix.tip.NumberTip;

/**
 * 1手先まで読むAI
 * 
 * 
 * @author 岡本拓海
 * 
 */
public class AIPlayer_v1 extends Player {

  public AIPlayer_v1(Mattix mattix) {
    super(mattix);

  }

  @Override
  protected int thinkMove(int[] legalMove) {
    NumberTip rt = null;
    int max_point;
    int max_index; 
    
    for(int i = 0; i<legalMove.length ; i++){
      
      try {
        NumberTip tip = (NumberTip)this.getMattix().getField().get(this.getMattix().getTurn(), legalMove[i]);
      } catch (IllegalMoveException e) {
        
        e.printStackTrace();
      }
      
      
    }
    
    return legalMove[0];
  }

  @Override
  public void win() {

  }

  @Override
  public void lose() {

  }

}
