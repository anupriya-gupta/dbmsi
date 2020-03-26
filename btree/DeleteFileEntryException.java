package btree;
import chainexception.*;

public class  DeleteFileEntryException  extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = -4329842108777735756L;

  public DeleteFileEntryException() {
    super();
  }
  public DeleteFileEntryException(String s) {super(null,s);}
  public DeleteFileEntryException(Exception e, String s) {super(e,s);}

}
