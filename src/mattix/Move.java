package mattix;



/**
 * 「手」の抽象表現
 * @author 岡本拓海
 *
 */
public class Move {
  private int value;//その手を売った際に得ることのできる得点
  private int x;//対象のNumberTipのx座標
  private int y;//対象のNumberTipのy座標
  
  public Move(int _x, int _y, int _value){
    this.x = _x;
    this.y = _y;
    this.value = _value;
  }
  
  public int getValue() {
    return value;
  }
  public void setValue(int value) {
    this.value = value;
  }
  
  
  public int getX() {
    return x;
  }
  
  public void setX(int x) {
    this.x = x;
  }
  
  public int getY() {
    return y;
  }
  
  public void setY(int y) {
    this.y = y;
  }
  
  
}
