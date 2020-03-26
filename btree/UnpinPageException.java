package btree;
import chainexception.*;

public class UnpinPageException  extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = -1037304356515052970L;

  public UnpinPageException() {
    super();
  }
  public UnpinPageException(String s) {super();}
  public UnpinPageException(Exception e, String s) {super(e,s);}

}
