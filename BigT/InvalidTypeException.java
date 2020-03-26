package BigT;
import chainexception.*;


public class InvalidTypeException extends ChainException {


  /**
   *
   */
  private static final long serialVersionUID = -6536400882361841420L;

  public InvalidTypeException()
  {
     super();
  }

  public InvalidTypeException (Exception ex, String name)
  {
    super(ex, name);
  }



}
