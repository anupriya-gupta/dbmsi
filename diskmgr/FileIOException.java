package diskmgr;
import chainexception.*;


public class FileIOException extends ChainException {

  /**
   *
   */
  private static final long serialVersionUID = 6320388561056052984L;

  public FileIOException(Exception e, String name)
  
  { 
    super(e, name); 
  }


}




