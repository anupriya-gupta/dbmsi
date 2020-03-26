package diskmgr;
import chainexception.*;


public class FileNameTooLongException extends ChainException {
  
  /**
   *
   */
  private static final long serialVersionUID = 4523781843517859191L;

  public FileNameTooLongException(Exception ex, String name)
    { 
      super(ex, name); 
    }
  
}




