package btree;
import chainexception.*;

public class LeafDeleteException extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = 5864282744983635387L;

  public LeafDeleteException() {
    super();
  }
  public LeafDeleteException(Exception e, String s) {super(e,s);}

}
