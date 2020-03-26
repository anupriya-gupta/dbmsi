package diskmgr;
import chainexception.*;


public class FileEntryNotFoundException extends ChainException {

  /**
   *
   */
  private static final long serialVersionUID = 2588886129932293114L;

  public FileEntryNotFoundException(Exception e, String name)
  { 
    super(e, name); 
  }

  


}




