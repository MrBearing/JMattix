package mattix;

import java.util.ArrayList;

import mattix.tip.CrossTip;
import mattix.tip.ITip;
import mattix.tip.NullTip;
import mattix.tip.NumberTip;
import mattix.util.MT19937;

public class Field {
  private ITip[/* y */][/* x */] _field;
/**
 * 
 * @return Position
 */
  public Position searchCrossTip() {
   
    int[] ans = new int[2];
    Position pos =null;
    
    for (int y = 0; y < this.getHeight(); y++) {
      for (int x = 0; x < this.getWidth(); x++) {
        if (CrossTip.isCrossTip(this.get(x, y))) {
          ans[0] = x;
          ans[1] = y;
          pos = new Position(x, y);
          break;
        }
      }
    }
    return pos;
  }
  
  
/**
 * 
 * @param x
 * @param y
 * @return ITip
 */
  public ITip get(int x, int y) {
    return this._field[y][x];
  }

 /**
  * 
  * @param x
  * @param y
  * @param tip
  */
  private void set(int x, int y, ITip tip) {
    this._field[y][x] = tip;
  }

  private int getHeight() {
    return this._field.length;
  }

  private int getWidth() {
    return this._field[0].length;
  }

  /**
   * toString
   * 
   * @return Fieldの文字列を表現を返します。
   */
  public String toString() {
    String ans = "";
    String BR = System.getProperty("line.separator");// 改行コード取得

    String HR = "  ";
    for (int i = 0; i < ((this.getWidth() * (ITip.STRING_LEMGTH + 1)) + 1); i++)
      HR += "-";// 行区切り作成
    HR += BR;// 改行追加

    ans += "  |";
    for (int i = 0; i < this.getWidth(); i++)
      ans += "%" + i + "%|";// 列番号表示
    ans += (BR + HR);

    for (int y = 0; y < this.getHeight(); y++) {
      ans += (y + ":");// 行番号表示
      ans += "|";
      for (int x = 0; x < this.getWidth(); x++)
        ans += this.get(x, y).toString() + "|";

      ans += BR;// 改行
      ans += HR;
    }
    return ans;
  }

  // @Override
  public Field clone() {
    int width = this.getWidth();
    int height = this.getHeight();

    Field rt = new Field(width, height);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        ITip tip = this.get(j, i).clone();
        rt.set(j, i, tip);
      }
    }

    return rt;
  }
  
  private ITip[] getRow(int row){
    return this._field[row];
  }
  
  private ITip[] getColumn(int column){
    ITip[] rt = new ITip[this._field.length];
    
    for(int i =0; i < this._field.length; i++)
      rt[i] = this.get(column, i);
    
    return rt;
  }
  
  
  /**
   * 合法手を返す
   * 
   * 
   * @param turn
   * @return 合法手が入った配列
   */
  public int[] getLegalMove(ETURN turn){
    //_TODO 合法手がない場合はnullを返す仕様にする。
    Position pos_ct = this.searchCrossTip();
    
    int[] rt = null;
    ArrayList<Integer> dst = new ArrayList<Integer>();
    
    ITip[] tg = null;
    
    if(turn == ETURN.WIDTH){
      
      tg = this.getRow(pos_ct.getY());
    }else if(turn == ETURN.LENGTH){
      tg = this.getColumn(pos_ct.getX());
    }
    
    //System.out.print("leagal move is ");
    for(int i= 0;i< tg.length ;i++){
     
      if(tg[i].getValue() == CrossTip.CROSS_TIP_VALUE
          || tg[i].getValue() == NullTip.NULL_TIP_VALUE);//NullTip or CrossTip ?
      else{
        dst.add(i);//ArrayListに追加 
        //System.out.print(tg[i].getValue()+" :");
      }
    }
    
    //System.out.println(":Field");
    
    if(dst.size() > 0){//0じゃなかったら
      rt = new int[dst.size()];
    
      for(int i=0; i< rt.length; i++)//配列に詰め直し
        rt[i] = dst.get(i);
    }
    
    return rt;
  }
  
  
  
  /**
   * 
   * 
   * 動けるかの条件判定をexceptionで実装すべきか、returnで実装すべきか
   *  →とりあえず、Exceptionで実装
   * 
   * @param pos
   *          どの行・列か
   * 
   */
  public NumberTip moveCrossTip(ETURN turn, int pos)
      throws IllegalMoveException {
    ITip rt = null;
    ITip ct = null;
    
 
    
    Position pos_ct = searchCrossTip();//クロスチップ探索
    

    
    rt = this.get(turn, pos);
    
    if (CrossTip.isCrossTip(rt)) {
      // CrossTipだったException投げ
      throw new IllegalMoveException(IllegalMoveException.TO_GET_CROSS_TIP);
    }
    
    if (rt == null) {
      // nullだったら
      throw new IllegalMoveException(IllegalMoveException.CANT_MOVE);
    }
    
    
    ct = this.get(pos_ct.getX(), pos_ct.getY());//crossTipを移動
    this.set(pos_ct.getX(), pos_ct.getY(), new NullTip());// 元の場所にはnull
    
    
    if (turn == ETURN.WIDTH) {
      this.set(pos, pos_ct.getY(), ct);
    } else if (turn == ETURN.LENGTH) {
      this.set(pos_ct.getX(), pos, ct);
    }

    return (NumberTip) rt;
  }

  /**
   * 
   */
  public ITip get (ETURN turn, int pos) throws IllegalMoveException{
    Position pos_ct = searchCrossTip();//クロスチップ探索
    
    ITip rt = null;
    try {
      if (turn == ETURN.WIDTH) {// 先手番だったら横移動
        rt = this.get(pos, pos_ct.getY());// cross
      } else if (turn == ETURN.LENGTH) {// 後手番だったら縦移動
        rt = this.get(pos_ct.getX(), pos);
      }
    }catch(ArrayIndexOutOfBoundsException e){
      //動かせないとこに入れたら、Exception投げ
      throw new IllegalMoveException(IllegalMoveException.CANT_MOVE);
    }
    
    return rt;
  }
  
  
  /**
   * 
   * @param gp
   */

  public Field(GamePropety gp) {
    this(gp.getFieldWidth(), gp.getFieldHeight());

  }

  private Field() {
    /* なにもしない */
    /* デフォルトコンストラクタつぶし */
  }

  private Field(int x, int y) {
    //設計をどうするか決めてないのでとりあえず仮に定義
    this();
    this._field = new ITip[y][x];
    for (int i = 0; i < y; i++)
      for (int j = 0; j < x; j++)
        this.set(j, i, new NullTip());
  }

  class Position{
    private int x;
    private int y;
    
    public Position(int x,int y){
      this.x = x;
      this.y = y;
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
    
    public String toString(){
      return "x:"+this.x+",y:"+this.y;
    }
  }
  
  /**
   * 
   * @author 岡本拓海
   * 
   */
  static class FieldFactory {

    public static Field create(GamePropety gp, Tipset ts) {
      Field field = new Field(gp.getFieldWidth(), gp.getFieldHeight());
      MT19937 mt = new MT19937();//生成のためのメルセンヌ・ツイスタ

      for (int y = 0; y < field.getHeight(); y++) {
        for (int x = 0; x < field.getWidth(); x++) {

          int a = Math.abs(mt.nextInt()) % ts.getTips().size();// ランダムに選択。長さで割って
          ITip tip = ts.getTips().get(a);// 取って
          field.set(x, y, tip);// 入れて
          ts.getTips().remove(a);// 消す。リスト内は詰められる。
        }
      }

      return field;
    }
  }
}