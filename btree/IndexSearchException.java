package btree;
import chainexception.*;

public class IndexSearchException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = -1782974844251649017L;

  public IndexSearchException() {
    super();
  }
  public IndexSearchException(String s) {super(null,s);}
  public IndexSearchException(Exception e, String s) {super(e,s);}

}
