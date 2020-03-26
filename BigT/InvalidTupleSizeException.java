package BigT;
import chainexception.*;

public class InvalidTupleSizeException extends ChainException{

   /**
    *
    */
   private static final long serialVersionUID = 2453429124856797268L;

   public InvalidTupleSizeException()
   {
      super();
   }
   
   public InvalidTupleSizeException(Exception ex, String name)
   {
      super(ex, name); 
   }

}

