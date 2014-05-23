package mattix.player;

import java.util.Scanner;

import mattix.IllegalMoveException;
import mattix.Mattix;
import mattix.tip.NumberTip;

public class CUIHumanPlayer extends Player {

  private Scanner scanner;

  public CUIHumanPlayer(Mattix mattix) {
    super(mattix);
    this.scanner = new Scanner(System.in);

  }

  @Override
  protected int thinkMove(int[] legalMove) {
    System.out.println(this.getMattix().getField().toString());
    System.out.println("現在のポイント:" + this.getPoint());

    int ans;

    // 有効手の表示
    System.out.print("有効手は:");
    for (int i = 0; i < legalMove.length; i++){
      System.out.print(legalMove[i] + ",");
    }
    System.out.println("です。");
    
    ans = this.scanner.nextInt();

    System.out.println(ans+"を選択しました。");
    
    return ans;
  }

  @Override
  public void win() {
    System.out.println("あなたの勝ちです。");
    System.out.println("現在のポイント:" + this.getPoint());
  }

  @Override
  public void lose() {
    System.out.println("あなたの負けです。");
  }

  // for debag
  /*
   * public static void main(String[] args){ Mattix mat =
   * TestMattixCreator.create(); CUIHumanPlayer cp = new CUIHumanPlayer(mat);
   * 
   * cp.selectMove(mat.getField().getLegalMove(mat.getTurn()));
   * System.out.println("現在の得点は"+cp.getPoint());
   * 
   * }
   */

}
