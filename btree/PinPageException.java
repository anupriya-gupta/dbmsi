package btree;
import chainexception.*;

public class PinPageException   extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = -1070452943453534253L;

  public PinPageException() {
    super();
  }
  public PinPageException(String s) {super(null,s);}
  public PinPageException(Exception e, String s) {super(e,s);}

}
