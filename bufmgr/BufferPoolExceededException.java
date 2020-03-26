package bufmgr;
import chainexception.*;

public class BufferPoolExceededException extends ChainException{

  /**
   *
   */
  private static final long serialVersionUID = -2743585677168409277L;

  public BufferPoolExceededException(Exception e, String name)
  { super(e, name); }
 
}
