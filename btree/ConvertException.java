package btree;
import chainexception.*;

public class ConvertException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = 3647181604482649084L;

  public ConvertException() {
    super();
  }
  public ConvertException(String s) {super(null,s);}
  public ConvertException(Exception e, String s) {super(e,s);}
}
