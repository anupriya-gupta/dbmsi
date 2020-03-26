/* File hferr.java  */

package BigT;
import chainexception.*;

public class HFException extends ChainException{


  /**
   *
   */
  private static final long serialVersionUID = 2632479306298557586L;

  public HFException()
  {
     super();
  
  }

  public HFException(Exception ex, String name)
  {
    super(ex, name);
  }



}
