package btree;
import chainexception.*;

public class NodeNotMatchException extends ChainException
{
  /**
   *
   */
  private static final long serialVersionUID = 2174759734572013207L;

  public NodeNotMatchException() {
    super();
  }
  public NodeNotMatchException(String s) {super(null,s);}
  public NodeNotMatchException(Exception e, String s) {super(e,s);}

}
