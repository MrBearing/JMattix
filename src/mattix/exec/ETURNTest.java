package mattix.exec;

import mattix.ETURN;

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
