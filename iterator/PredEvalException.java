package iterator;
import chainexception.*;


public class  PredEvalException extends ChainException {
  /**
   *
   */
  private static final long serialVersionUID = 5634391966181990510L;

  public PredEvalException(String s) {
    super(null, s);
  }
  public PredEvalException(Exception prev, String s){ super(prev,s);}
}
