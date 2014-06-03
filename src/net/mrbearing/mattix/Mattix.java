package net.mrbearing.mattix;

import java.util.HashMap;

import net.mrbearing.mattix.player.Player;

/**
 * 
 * 
 * mattixのゲーム本体
 * 
 * @author 岡本拓海
 * 
 */
public class Mattix {
  private ETURN turn;
  private Field _field;
  
  //private ArrayList<Player> playerlist; //2人以上のプレーヤー数への拡張性の確保
  
  private HashMap<ETURN , Player> playermap ;
  
  public Field getField() {
    return this._field;
  }
  
  public ETURN getTurn(){
    return this.turn;
  }
  
  
  private void nextTurn() {
    this.turn = this.turn.next();
  }

  public int[] getLegalMove(){
    return this.getField().getLegalMove(this.turn);
  }
  
  public void addPlayer(Player p ,ETURN e){
//    this.playerlist.add(p);
    this.playermap.put(e,p);
  }
  
/*
 *   public ArrayList<Player> getPlayers(){
 
    return this.playerlist;
  }
*/
  public HashMap<ETURN, Player> getPlayersMap(){
    return this.playermap;
  }
  
  
  /**
   * 
   */
  private boolean gameRoutine(){
    //System.out.println("Mattix:gameRoutin");
    System.out.println(this.turn+":Mattix");
    
    int[] legalMove = this.getLegalMove();//有効手の取得
    
    if(legalMove == null)
      return false;
    
    this.getPlayersMap().get(this.turn).selectMove(legalMove);
    
    nextTurn();
    return true;
  }

  public void startGame(){
    boolean is_fin= true;
    while(is_fin){
      // 合法手が存在しなくなるまでループを続ける。
      is_fin = this.gameRoutine();
    }
    this.judge();
  }
  /**
   * 勝敗判定
   */
  public void judge(){
    if(this.playermap.get(ETURN.WIDTH).getPoint()< this.playermap.get(ETURN.LENGTH).getPoint()){
      this.playermap.get(ETURN.WIDTH).lose();
      this.playermap.get(ETURN.LENGTH).win();
    }else{
      this.playermap.get(ETURN.WIDTH).lose();
      this.playermap.get(ETURN.LENGTH).win();
    }
  }
  
  
  public Mattix(GamePropety gp) {
    this.turn = ETURN.WIDTH;// 手番を先手番に初期化
    // Tipの生成
    Tipset tipset = Tipset.TipsetFactory.create(gp);
    // Fieldの生成
    this._field = Field.FieldFactory.create(gp, tipset);
    
    //this.playerlist  = new ArrayList<Player>();
    this.playermap = new HashMap<ETURN, Player>();
    
    
    /* リスト中のプレイヤに対して順次手番を回す。
     * 2プレーヤー限定。
     * ループを続ける部分の実装は、別のメソッドで実装すること。
     * →スレッド化したときに問題になるから。
     */
    
    
    
  }
}
