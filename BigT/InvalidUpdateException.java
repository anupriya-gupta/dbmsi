package BigT;
import chainexception.*;

public class InvalidUpdateException extends ChainException{


  /**
   *
   */
  private static final long serialVersionUID = 4558073003420045861L;

  public InvalidUpdateException()
  {
     super();
  }

  public InvalidUpdateException (Exception ex, String name)
  {
    super(ex, name);
  }



}
