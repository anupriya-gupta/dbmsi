package btree;
import chainexception.*;

public class KeyNotMatchException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = -5030175789737489083L;

  public KeyNotMatchException() {
    super();
  }
  public KeyNotMatchException(String s) {super(null,s);}
  public KeyNotMatchException(Exception e, String s) {super(e,s);}

}
