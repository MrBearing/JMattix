package mattix.tip;

public interface ITip {
  static final int STRING_LEMGTH = 3;// チップが文字表現で使用する長さ

  public int getValue();

  public String toString();

  public ITip clone();
}
