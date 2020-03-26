package btree;
import chainexception.*;

public class RedistributeException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = 3054066646191335781L;

  public RedistributeException() {
    super();
  }
  public RedistributeException(String s) {super(null,s);}
  public RedistributeException(Exception e, String s) {super(e,s);}

}
