package btree;
import chainexception.*;

public class KeyTooLongException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = 6355522385735438501L;

  public KeyTooLongException() {
    super();
  }
  public KeyTooLongException(String s) {super(null,s);}
  public KeyTooLongException(Exception e, String s) {super(e,s);}

}
