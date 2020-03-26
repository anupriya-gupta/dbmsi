package bufmgr;
import chainexception.*;

public class BufMgrException extends ChainException{

  /**
   *
   */
  private static final long serialVersionUID = -3104101463355601559L;

  public BufMgrException(Exception e, String name)
  { super(e, name); }
 
}

