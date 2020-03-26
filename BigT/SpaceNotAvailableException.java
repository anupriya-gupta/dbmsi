package BigT;
import chainexception.*;

public class SpaceNotAvailableException extends ChainException{


  /**
   *
   */
  private static final long serialVersionUID = -6846112396951145440L;

  public SpaceNotAvailableException()
  {
     super();
  
  }

  public SpaceNotAvailableException(Exception ex, String name)
  {
    super(ex, name);
  }



}
