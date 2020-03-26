/* File hferr.java  */

package BigT;
import chainexception.*;

public class HFDiskMgrException extends ChainException{


  /**
   *
   */
  private static final long serialVersionUID = 6187631346532108568L;

  public HFDiskMgrException()
  {
     super();
  
  }

  public HFDiskMgrException(Exception ex, String name)
  {
    super(ex, name);
  }



}
