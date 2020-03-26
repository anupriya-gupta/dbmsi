package global;

import java.io.*;
import java.util.Arrays;

public class ConvertMap{

    public static String getStrLabel(int position, byte []data, int length) throws IOException {
        InputStream in;
        DataInputStream instr;
        String value;
        byte tmp[] = new byte[length];

        System.arraycopy (data, position, tmp, 0, length);
        in = new ByteArrayInputStream(tmp);
        instr = new DataInputStream(in);
        value = instr.readUTF();
        return value;
    }

    public static int getIntValue (int position, byte []data) throws java.io.IOException {
        InputStream in;
        DataInputStream instr;
        int value;
        byte tmp[] = new byte[4];
        
        // copy the value from data array out to a tmp byte array
        System.arraycopy (data, position, tmp, 0, 4);
        
        /* creates a new data input stream to read data from the
        * specified input stream
        */
        in = new ByteArrayInputStream(tmp);
        instr = new DataInputStream(in);
        value = instr.readInt();  
        
        return value;
    }

    public static void setIntValue (int value, int position, byte []data) throws java.io.IOException{
        /* creates a new data output stream to write data to 
        * underlying output stream
        */
        
        OutputStream out = new ByteArrayOutputStream();
        DataOutputStream outstr = new DataOutputStream (out);
        
        // write the value to the output stream
        
        outstr.writeInt(value);
        
        // creates a byte array with this output stream size and the
        // valid contents of the buffer have been copied into it
        byte []B = ((ByteArrayOutputStream) out).toByteArray();
        // System.out.println(Arrays.toString(data));
        // copies the first 4 bytes of this byte array into data[] 
        System.arraycopy(B, 0, data, position, 4);
      
    }

    public static short getShortValue (int position, byte []data) throws java.io.IOException {
      InputStream in;
      DataInputStream instr;
      short value;
      byte tmp[] = new byte[2];
      
      // copy the value from data array out to a tmp byte array
      System.arraycopy (data, position, tmp, 0, 2);
      
      /* creates a new data input stream to read data from the
       * specified input stream
       */
      in = new ByteArrayInputStream(tmp);
      instr = new DataInputStream(in);
      value = instr.readShort();
      
      return value;
    }

    public static void setShortValue(short value, int position, byte []data) throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        DataOutputStream outstr = new DataOutputStream (out);
      
        // write the value to the output stream
      
        outstr.writeShort(value);
      
        // creates a byte array with this output stream size and the
        // valid contents of the buffer have been copied into it
        byte []B = ((ByteArrayOutputStream) out).toByteArray();
        
        // copies the first 2 bytes of this byte array into data[] 
        System.arraycopy (B, 0, data, position, 2);
    }

    public static float getFloValue (int position, byte []data)
    throws java.io.IOException
    {
        InputStream in;
        DataInputStream instr;
        float value;
        byte tmp[] = new byte[4];
        
        // copy the value from data array out to a tmp byte array
        System.arraycopy (data, position, tmp, 0, 4);
        
        /* creates a new data input stream to read data from the
        * specified input stream
        */
        in = new ByteArrayInputStream(tmp);
        instr = new DataInputStream(in);
        value = instr.readFloat();  
        
        return value;
    }

    public static char getCharValue (int position, byte []data) throws java.io.IOException {
        InputStream in;
        DataInputStream instr;
        char value;
        byte tmp[] = new byte[2];
        // copy the value from data array out to a tmp byte array  
        System.arraycopy (data, position, tmp, 0, 2);
        
        /* creates a new data input stream to read data from the
        * specified input stream
        */
        in = new ByteArrayInputStream(tmp);
        instr = new DataInputStream(in);
        value = instr.readChar();
        return value;
    }

    public static void setStrValue (String value, int position, byte []data)throws java.io.IOException{
        /* creates a new data output stream to write data to
        * underlying output stream
        */
        
        OutputStream out = new ByteArrayOutputStream();
        DataOutputStream outstr = new DataOutputStream (out);
        
        // write the value to the output stream
        
        outstr.writeUTF(value);
        // creates a byte array with this output stream size and the 
        // valid contents of the buffer have been copied into it
        byte []B = ((ByteArrayOutputStream) out).toByteArray();
        
        int sz =outstr.size();  
        // copies the contents of this byte array into data[]
        System.arraycopy (B, 0, data, position, sz);
    
    }

    public static void setFloValue (float value, int position, byte []data) 
    throws java.io.IOException
    {
        /* creates a new data output stream to write data to 
        * underlying output stream
        */
        
        OutputStream out = new ByteArrayOutputStream();
        DataOutputStream outstr = new DataOutputStream (out);
        
        // write the value to the output stream
        
        outstr.writeFloat(value);
        
        // creates a byte array with this output stream size and the
        // valid contents of the buffer have been copied into it
        byte []B = ((ByteArrayOutputStream) out).toByteArray();
        
        // copies the first 4 bytes of this byte array into data[] 
        System.arraycopy (B, 0, data, position, 4);
        
    }
}