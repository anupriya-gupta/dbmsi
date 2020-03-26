package BigT;

import java.io.*;
import java.util.Arrays;

import global.ConvertMap;
import global.GlobalConst;

/** A unit of storing values in bigt.
*
*/

public class Map implements GlobalConst{

    byte[] data;
    public static final int max_size = MINIBASE_PAGESIZE;
    public static int size = 4;
    private int map_offset;
    
    /** 
     * private field
     * offset of the fields
    */
    
    private short[] fldOffset;


    /**
    * Class constructor
    * Creat a new map with length = max_size,tuple offset = 0.
    */

    public Map(){
        data = new byte[max_size];
        map_offset = 0;
    }

    /** Constructor
    * @param amap a byte array which contains the map
    * @param offset the offset of the map in the byte array
    * @param length the length of the map
    */
    public Map(byte[] amap, int offset){
        data = amap;
        map_offset = offset;
    }

    /** Constructor(used as map copy)
    * @param frommap   a byte array which contains the map
    * 
    */

    public Map(Map fromMap){
        data = fromMap.getMapByteArray();
        map_offset = 0;
    }
    
    /**  
    * Class constructor
    * Create a new map with length = size,map offset = 0.
    */
    public Map(int size){
       // Creat a new tuple
       data = new byte[size];
       map_offset = 0;     
    }

    // ------------ Getter Functions ------------- //

    /**
     * get the row label of a map
     * 
     * @return  rowlabel of the map
     */

    public String getRowLabel() throws IOException {
        String val;
        val = ConvertMap.getStrLabel(fldOffset[0], data, fldOffset[1] - fldOffset[0]);
        return val;
    }


    /**
     * get the column label of a map
     * 
     * @return  columnlabel of the map
     */

    public String getColumnLabel() throws IOException {
        String val;
        val = ConvertMap.getStrLabel(fldOffset[1], data, fldOffset[2] - fldOffset[1]);
        return val;
    }


    /**
     * get the value of a map
     * 
     * @return  value of the map
     */

    public String getValue() throws IOException {
        String val;
        val = ConvertMap.getStrLabel(fldOffset[3], data, fldOffset[4] - fldOffset[3]);
        return val;
    }


    /**
     * get the timestamp of a map
     * 
     * @return  timestamp of the map
     */

    public int getTimeStamp() throws IOException {
        int val;
        val = ConvertMap.getIntValue(fldOffset[2], data);
        return val;
    }


    /**
     * get the offset of a map
     * 
     * @return  offset of the map
     */
    public int getMapOffset(){
        return map_offset;
    }

    // ------------ Setter Functions ------------- //



    /**
     * set the row label of a map
     * 
     * @param  val String type
     * @exception IOException
     */

    public Map setRowLabel(String val) throws IOException {
        ConvertMap.setStrValue(val, fldOffset[0], data);
        return this;
    }

    /**
     * set the column label of a map
     * 
     * @param val String type
     * @exception IOException
     */

    public Map setColumnLabel(String val) throws IOException {
        ConvertMap.setStrValue(val, fldOffset[1], data);
        return this;
    }

    /**
     * set the timestamp of a map
     * 
     * @param val String type
     * @exception IOException
     */

    public Map setTimeStamp(int val) throws IOException {
        ConvertMap.setIntValue(val, fldOffset[2], data);
        return this;
    }

    /**
     * set the value of a map
     * 
     * @param val String type
     * @exception IOException
     */

    public Map setValue(String val) throws IOException {
        ConvertMap.setStrValue(val, fldOffset[3], data);
        return this;
    }

    // ------------ Others ---------------- //
    
    /**
     * copy the map to byte array out
     * 
     * @return mapcopy
     */

    public byte[] getMapByteArray() {
        byte [] mapcopy = new byte [fldOffset[4]-map_offset];
        System.arraycopy(data, map_offset, mapcopy, 0, fldOffset[4]-map_offset);
        return mapcopy;
    }

    /**
     * copy the map array out
     * @param di
     * @return mapcopy
     */

    public byte[] getMapByteArray(String di) {
        byte [] mapcopy = new byte [data.length];
        System.arraycopy(data, map_offset, mapcopy, 0, (data.length-map_offset));
        return mapcopy;
    }


    /**
     * prints the rowlabel, columnlabel, timestamp and value of the map
     * @exception IOException
     */

    public void print() throws IOException {
        String rowLabel, colLabel, val;
        int timestamp;
        System.out.print("[");

        rowLabel = ConvertMap.getStrLabel(fldOffset[0], data,fldOffset[1] - fldOffset[0]);
        System.out.print("Row Label: "+rowLabel);

        colLabel = ConvertMap.getStrLabel(fldOffset[1], data,fldOffset[2] - fldOffset[1]);
        System.out.print(", Column Label: "+colLabel);

        timestamp = ConvertMap.getIntValue(fldOffset[2], data);
        System.out.print(", Timestamp: "+timestamp);

        val = ConvertMap.getStrLabel(fldOffset[3], data,fldOffset[4] - fldOffset[3]);
        System.out.print(", Value: "+val);

        System.out.print("]\n");
        
    }

    /**
     * gets the size of the map
     * @return size
     */

    public short size(){
        // return ((short) (fldOffset[4] - map_offset));
        return (short) (data.length - map_offset);
    }

    /**
     * gives the size of map
     * 
     * @param di of type String 
     * @return size
     */

    public short size(String di){
        return (short) (data.length - map_offset);
    }

    /**
     * copy the given map
     * 
     * @param fromMap of type Map
     * 
     */

    public void mapCopy (Map fromMap) {
        byte [] temparray = fromMap.getMapByteArray();
        fldOffset = fromMap.getFldOffset();
        System.arraycopy(temparray, 0, data, map_offset, fldOffset[4] - map_offset); 
        
    }


    /**
     * initializes a map without constructor
     * 
     * @param val String type
     */

    public void mapInit(byte [] amap, int offset){
        data = amap;
        map_offset = offset;
    }

    /**
     * set the column label of a map
     * @param frommap of type byte
     * @param offset of type int
     */

    public void mapSet(byte[] frommap, int offset) {
        System.arraycopy(frommap, offset, data, 0, fldOffset[4] - map_offset);
        map_offset = 0;
    }

    /**
     * set the fieldoffset of the map
     * 
     * @param val String type
     * @exception IOException
     */

    public void setFldOffsetData(short[] fldOffset){ 
        for (int i=0; i<fldOffset.length; i++) {
            this.fldOffset[i] = fldOffset[i];
        }
    }

    /**
     * get the fieldoffset of the map
     * @return the fldOffset
     */
    public short[] getFldOffset() {
        return fldOffset;
    }


    /**
     *  set the strfld of the map
     * @param fldNo of type int
     * @param val of type String
     * @exception IOException, FieldNumberOutOfBoundException
     */
    public Map setStrFld(int fldNo, String val) throws IOException, FieldNumberOutOfBoundException {
     if ( (fldNo > 0) && (fldNo <= 4))        
      {
         ConvertMap.setStrValue (val, fldOffset[fldNo -1], data);
         return this;
      }
     else 
       throw new FieldNumberOutOfBoundException (null, "TUPLE:TUPLE_FLDNO_OUT_OF_BOUND");
    }


    /**
     * set the HDR of the map
     @param strSizes of type short array
     @exception IOException
     */
    public void setHdr(short strSizes[]) throws IOException {
        ConvertMap.setShortValue((short) 10, map_offset, data);
        int pos = map_offset+2; 
        // short incr;
        fldOffset = new short[5];
        fldOffset[0] = (short) (map_offset+10);
        ConvertMap.setShortValue(fldOffset[0], pos, data);

        short incr;
        
        // fldOffset[0] = (short)map_offset;

        incr = (short) (strSizes[0] + 2);
        fldOffset[1]  = (short) (fldOffset[0] + incr);
        ConvertMap.setShortValue(fldOffset[1], pos, data);
        pos +=2;
        
        incr = (short) (strSizes[1] + 2);
        fldOffset[2]  = (short) (fldOffset[1] + incr);
        ConvertMap.setShortValue(fldOffset[2], pos, data);
        pos +=2;

        incr = 4;
        fldOffset[3]  = (short) (fldOffset[2] + incr);
        ConvertMap.setShortValue(fldOffset[3], pos, data);
        pos +=2;

        incr = (short) (strSizes[2] + 2);
        fldOffset[4]  = (short) (fldOffset[3] + incr);
        ConvertMap.setShortValue(fldOffset[4], pos, data);
        pos +=2;

    }


    /**
     * setup the map for use
     * @exception IOException
     */
    public void mapSetup() throws IOException {
        //ConvertMap.setShortValue((short) 4, map_offset, data);
        int pos = map_offset+2; 
        // short incr;
        fldOffset = new short[5];
        // fldOffset[0] = (short) (map_offset+10);
        byte tmp[] = new byte[2];
        System.arraycopy(data, 0, tmp, 0, 2);
        fldOffset[0] = ConvertMap.getShortValue(0, tmp);

        System.arraycopy(data, 2, tmp, 0, 2);
        fldOffset[1] = ConvertMap.getShortValue(0, tmp);

        System.arraycopy(data, 4, tmp, 0, 2);
        fldOffset[2] = ConvertMap.getShortValue(0, tmp);

        System.arraycopy(data, 6, tmp, 0, 2);
        fldOffset[3] = ConvertMap.getShortValue(0, tmp);

        System.arraycopy(data, 8, tmp, 0, 2);
        fldOffset[4] = ConvertMap.getShortValue(0, tmp);
    }
}