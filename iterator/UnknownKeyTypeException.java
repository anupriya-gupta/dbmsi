package iterator;

import chainexception.*;

public class UnknownKeyTypeException extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = 3961091976551504649L;

  public UnknownKeyTypeException() {
    super();
  }
  public UnknownKeyTypeException(String s) {super(null,s);}
  public UnknownKeyTypeException(Exception e, String s) {super(e,s);}
}
