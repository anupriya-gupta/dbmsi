package BigT;
import chainexception.*;

public class FieldNumberOutOfBoundException extends ChainException{

   /**
    *
    */
   private static final long serialVersionUID = -360757364736062168L;

   public FieldNumberOutOfBoundException()
   {
      super();
   }
   
   public FieldNumberOutOfBoundException (Exception ex, String name)
   {
      super(ex, name); 
   }

}

