package mattix.player;


import mattix.IllegalMoveException;
import mattix.Mattix;
import mattix.tip.NumberTip;


/**
 * ゲームのプレーヤー
 * 
 * 
 * @author 岡本拓海
 *
 */
public abstract class Player {
  
  //コンストラクタにMAttixクラスを渡すようにして、合法手の参照等を行う。
  private Mattix _mattix;
  private int point;
  
  public Player(Mattix mattix){
    //このように宣言しておくとデフォルトコンストラクタがこれになる。
    this.setPoint(0);
    this._mattix = mattix;
  }
  
  /**
   * このメソッドがMattixから呼ばれる。
   * とってくるところはPlayerに実装。
   * 
   * 
   * @param legalMove
   */
  public void selectMove(int[] legalMove){
    
    NumberTip getTip = null;
    boolean isloop = true;
    int pos;
    while(isloop){
      pos = this.thinkMove(legalMove);//思考部
      isloop = false;//ループ終了

      try {
        getTip = this.getMattix().getField()
            .moveCrossTip(this.getMattix().getTurn(), pos);//対象の場所のチップを取ってくる。
      } catch (IllegalMoveException e) {
        e.printStackTrace();
        isloop = true;//ループ継続
      }
    }
    
    //System.out.println(this.getMattix().getField().toString());
    //System.out.println("Player#selectMove:");
    
    this.addPoint(getTip);
  }
  
  public void addPoint(NumberTip getTip){
    int getPoint = getTip.getValue();
    this.point += getPoint;
  }
  
  public int getPoint() {
    return point;
  }

  private void setPoint(int point) {
    this.point = point;
  }
  
  protected Mattix getMattix(){
    return this._mattix;
  }
  
  protected int getEnemyPoint(){
    
    return 0;
  }
  
  /**
   * 
   * このメソッドに思考ルーチンorユーザーインターフェース空の入力を実装。
   * 
   */
  protected abstract int thinkMove(int[] legalMove);
  public abstract void win();
  public abstract void lose();


}
