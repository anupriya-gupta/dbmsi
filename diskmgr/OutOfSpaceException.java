package diskmgr;
import chainexception.*;

public class OutOfSpaceException extends ChainException {

  /**
   *
   */
  private static final long serialVersionUID = -5064356249401870103L;

  public OutOfSpaceException(Exception e, String name)
    { 
      super(e, name); 
    }
}

