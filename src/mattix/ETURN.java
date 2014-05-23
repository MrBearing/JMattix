package mattix;

public enum ETURN {// 手番を表す WIDTH先手:横移動

  WIDTH {
    @Override
    public ETURN next() {
      //this = ETURN.LENGTH;
      return ETURN.LENGTH;
    }
  },
  LENGTH {
    @Override
    public ETURN next() {
      return ETURN.WIDTH;
    }
  };

  public abstract ETURN next();
}
