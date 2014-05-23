package mattix.util;

import java.io.File;

import mattix.GamePropety;
import mattix.Mattix;
import mattix.GamePropety.GamePropetyFacroty;
import mattix.exec.Main;

public class TestMattixCreator {
  public static Mattix create(){
    System.out.println("mattix");
    
    GamePropety gp = GamePropetyFacroty.create(new File( Main.GAME_PROPETY_XML_PATH));
    System.out.println(gp);
    System.out.println();
    Mattix mat = new Mattix(gp);
    return mat;
  }
}
