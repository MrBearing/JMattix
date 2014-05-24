package net.mrbearing.mattix.tip;

public class NullTip implements ITip {

  public static final int NULL_TIP_VALUE =100;
  @Override
  public int getValue() {
    return NullTip.NULL_TIP_VALUE;
  }

  public NullTip clone() {
    return new NullTip();
  }

  public String toString() {
    return "...";
  }
}
