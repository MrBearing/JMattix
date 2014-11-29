package net.mrbearing.mattix.exec;

import java.io.File;

import net.mrbearing.mattix.ETURN;
import net.mrbearing.mattix.GamePropety;
import net.mrbearing.mattix.GamePropetyFacroty;
import net.mrbearing.mattix.Mattix;
import net.mrbearing.mattix.player.CUIHumanPlayer;
import net.mrbearing.mattix.player.TestPlayer;

/**
 * 
 * @author 岡本拓海
 */

public class Main {

  public static final String GAME_PROPETY_XML_PATH = "./ini/GamePropety.xml";

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    System.out.println("mattix");
    
    GamePropety gp = GamePropetyFacroty.create(new File(GAME_PROPETY_XML_PATH));
    System.out.println(gp);    System.out.println();
    Mattix mat = new Mattix(gp);
    
    CUIHumanPlayer hp = new CUIHumanPlayer(mat);
    TestPlayer tp = new TestPlayer(mat);

    
    mat.addPlayer(tp,ETURN.WIDTH);
    mat.addPlayer(hp,ETURN.LENGTH);
    
    
    mat.startGame();
 
  }

}
