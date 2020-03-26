package btree;
import chainexception.*;

public class  IndexInsertRecException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = -1393318757328026221L;

  public IndexInsertRecException() {
    super();
  }
  public IndexInsertRecException(String s) {super(null,s);}
  public IndexInsertRecException(Exception e, String s) {super(e,s);}

}
