package diskmgr;
import chainexception.*;

public class InvalidRunSizeException extends ChainException {
  
  /**
   *
   */
  private static final long serialVersionUID = -3497417363912294997L;

  public InvalidRunSizeException(Exception e, String name)
    { 
      super(e, name); 
    }
}




