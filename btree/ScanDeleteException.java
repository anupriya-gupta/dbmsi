package btree;
import chainexception.*;

public class ScanDeleteException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = -3796063204837291846L;

  public ScanDeleteException() {
    super();
  }
  public ScanDeleteException(String s) {super(null,s);}
  public ScanDeleteException(Exception e, String s) {super(e,s);}

}
