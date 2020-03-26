package btree;
import chainexception.*;

public class DeleteFashionException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = -7645048514511802141L;

  public DeleteFashionException() {
    super();
  }
  public DeleteFashionException(String s) {super(null,s);}
  public DeleteFashionException(Exception e, String s) {super(e,s);}

}
