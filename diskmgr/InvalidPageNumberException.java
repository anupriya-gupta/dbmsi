package diskmgr;
import chainexception.*;

public class InvalidPageNumberException extends ChainException {
  
  
  /**
   *
   */
  private static final long serialVersionUID = -6450758945430247804L;

  public InvalidPageNumberException(Exception ex, String name) 
    { 
      super(ex, name); 
    }
}




