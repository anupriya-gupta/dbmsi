package iterator;
import chainexception.*;

public class MapUtilsException extends ChainException {
  /**
   *
   */
  private static final long serialVersionUID = 4705992101842113428L;

  public MapUtilsException(String s) {
    super(null, s);
  }
  public MapUtilsException(Exception prev, String s){ super(prev,s);}
}
