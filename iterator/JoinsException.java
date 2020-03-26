package iterator;
import chainexception.*;

public class JoinsException extends ChainException {
  /**
   *
   */
  private static final long serialVersionUID = 7313228419177208700L;

  public JoinsException(String s) {
    super(null, s);
  }
  public JoinsException(Exception prev, String s){ super(prev,s);}
}
