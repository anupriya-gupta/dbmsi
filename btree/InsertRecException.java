package btree;
import chainexception.*;

public class InsertRecException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = -460523801936959019L;

  public InsertRecException() {
    super();
  }
  public InsertRecException(String s) {super(null,s);}
  public InsertRecException(Exception e, String s) {super(e,s);}

}
