package btree;
import chainexception.*;

public class ScanIteratorException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = 6923801608941959662L;

  public ScanIteratorException() {
    super();
  }
  public ScanIteratorException(String s) {super(null,s);}
  public ScanIteratorException(Exception e, String s) {super(e,s);}

}
