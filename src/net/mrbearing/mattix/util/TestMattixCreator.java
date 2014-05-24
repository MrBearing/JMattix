package net.mrbearing.mattix.util;

import java.io.File;

import net.mrbearing.mattix.GamePropety;
import net.mrbearing.mattix.Mattix;
import net.mrbearing.mattix.GamePropety.GamePropetyFacroty;
import net.mrbearing.mattix.exec.Main;

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
