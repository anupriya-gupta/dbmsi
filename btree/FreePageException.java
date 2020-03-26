package btree;
import chainexception.*;

public class  FreePageException  extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = 1941746963240401555L;

  public FreePageException() {
    super();
  }
  public FreePageException(String s) {super(null,s);}
  public FreePageException(Exception e, String s) {super(e,s);}

}
