package iterator;

import chainexception.*;

public class LowMemException extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = -2038777089749615419L;

  public LowMemException(String s) {
    super(null, s);
  }
  public LowMemException(Exception e,String s) {super(e,s);}
}
