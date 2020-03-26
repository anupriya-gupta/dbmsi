package diskmgr;
import chainexception.*;

public class DuplicateEntryException extends ChainException {
  
  /**
   *
   */
  private static final long serialVersionUID = 4357204191842207235L;

  public DuplicateEntryException(Exception e, String name)
    {
      super(e, name); 
    }
}

