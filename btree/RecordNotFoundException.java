package btree;
import chainexception.*;

public class RecordNotFoundException extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = 5331700725041885398L;

  public RecordNotFoundException() {
    super();
  }
  public RecordNotFoundException(String s) {super(null,s);}
  public RecordNotFoundException(Exception e, String s) {super(e,s);}

}
