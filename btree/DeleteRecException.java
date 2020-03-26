package btree;
import chainexception.*;

public class DeleteRecException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = 4115828279608308945L;

  public DeleteRecException() {
    super();
  }
  public DeleteRecException(String s) {super(null,s);}
  public DeleteRecException(Exception e, String s) {super(e,s);}

}
