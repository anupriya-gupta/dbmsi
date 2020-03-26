package btree;
import chainexception.*;

public class AddFileEntryException  extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = -5058546571526883728L;

  public AddFileEntryException() {
    super();
  }
  public AddFileEntryException(Exception e, String s) {super(e,s);}
}
