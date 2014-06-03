package net.mrbearing.mattix;

public class IllegalMoveException extends Exception {
  /**
     * 
     */
  private static final long serialVersionUID = -3320221134067720914L;

  static final String TO_GET_CROSS_TIP = "CROSSチップを取得しようとしました。";
  static final String CANT_MOVE = "その場所には動かせません。";

  public IllegalMoveException(String s) {
    super(s);
  }
}
