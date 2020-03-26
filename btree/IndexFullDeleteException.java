package btree;
import chainexception.*;

public class IndexFullDeleteException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = -2100262923943360726L;

  public IndexFullDeleteException() {
    super();
  }
  public IndexFullDeleteException(String s) {super(null,s);}
  public IndexFullDeleteException(Exception e, String s) {super(e,s);}

}
