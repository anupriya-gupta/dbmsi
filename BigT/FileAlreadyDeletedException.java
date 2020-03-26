package BigT;
import chainexception.*;

public class FileAlreadyDeletedException extends ChainException{

   /**
    *
    */
   private static final long serialVersionUID = -3851941118131484533L;

   public FileAlreadyDeletedException()
   {
      super();
   }
   
   public FileAlreadyDeletedException(Exception ex, String name)
   {
      super(ex, name); 
   }

}
