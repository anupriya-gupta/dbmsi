package btree;
import chainexception.*;

public class  InsertException extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = 7283453242285318196L;

  public InsertException() {
    super();
  }
  public InsertException(String s) {super(null,s);}
  public InsertException(Exception e, String s) {super(e,s);}

}
