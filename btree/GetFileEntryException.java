package btree;
import chainexception.*;

public class  GetFileEntryException extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = -2983447088892634805L;

  public GetFileEntryException() {
    super();
  }
  public GetFileEntryException(String s) {super(null,s);}
  public GetFileEntryException(Exception e, String s) {super(e,s);}

}
