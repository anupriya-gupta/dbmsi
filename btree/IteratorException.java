package btree;
import chainexception.*;

public class IteratorException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = -9191226158420243891L;

  public IteratorException() {
    super();
  }
  public IteratorException(String s) {super(null,s);}
  public IteratorException(Exception e, String s) {super(e,s);}

}
