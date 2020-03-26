package btree;
import chainexception.*;

public class LeafInsertRecException extends ChainException 
{
  /**
   *
   */
  private static final long serialVersionUID = 7083775078042538692L;

  public LeafInsertRecException() {
    super();
  }
  public LeafInsertRecException(String s) {super(null,s);}
  public LeafInsertRecException(Exception e, String s) {super(e,s);}

}
