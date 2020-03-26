package BigT;
import chainexception.*;


public class InvalidSlotNumberException extends ChainException {


  /**
   *
   */
  private static final long serialVersionUID = 2955689020723026591L;
  /**
   *
   */

  public InvalidSlotNumberException()
  {
     super();
  }

  public InvalidSlotNumberException (Exception ex, String name)
  {
    super(ex, name);
  }



}
