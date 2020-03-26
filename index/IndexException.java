package index;

import chainexception.*;

public class IndexException extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = 1572494949712251320L;

  public IndexException() {
    super();
  }
  public IndexException(String s) {super(null,s);}
  public IndexException(Exception e, String s) {super(e,s);}
}
