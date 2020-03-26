package btree;
import chainexception.*;

public class ConstructPageException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = -6100300278537567810L;

  public ConstructPageException() {
    super();
  }
  public ConstructPageException(String s) {super(null,s);}
  public ConstructPageException(Exception e, String s) {super(e,s);}

}
