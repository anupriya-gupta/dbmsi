package iterator;

import chainexception.*;

public class IteratorBMException extends ChainException {
  /**
   *
   */
  private static final long serialVersionUID = -1230414731631804092L;

  public IteratorBMException(String s) {
    super(null, s);
  }
  public IteratorBMException(Exception prev, String s){ super(prev,s);}
}
