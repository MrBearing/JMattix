package net.mrbearing.mattix.exec;

import net.mrbearing.mattix.ETURN;

public class ETURNTest {

  /**
   * @param args
   */
  public static void main(String[] args) {
    ETURN turn = ETURN.WIDTH;
    System.out.println(turn);
    turn = turn.next();
    
    System.out.println(turn);
    

  }

}
