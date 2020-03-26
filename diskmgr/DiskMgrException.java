package diskmgr;
import chainexception.*;


public class DiskMgrException extends ChainException {

  /**
   *
   */
  private static final long serialVersionUID = -4409354231989598641L;

  public DiskMgrException(Exception e, String name)
  
  { 
    super(e, name); 
  }


}




