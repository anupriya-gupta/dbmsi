package iterator;

import chainexception.*;

public class SortException extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = -2776777584947158701L;

  public SortException(String s) {
    super(null, s);
  }
  public SortException(Exception e, String s) {super(e,s);}
}
