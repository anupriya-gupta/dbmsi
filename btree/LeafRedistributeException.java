package btree;
import chainexception.*;

public class  LeafRedistributeException extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = -194640033473871420L;

  public LeafRedistributeException() {
    super();
  }
  public LeafRedistributeException(String s) {super(null,s);}
  public LeafRedistributeException(Exception e, String s) {super(e,s);}

}
