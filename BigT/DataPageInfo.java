package BigT;


/** File DataPageInfo.java */


import global.*;
import java.io.*;
import java.util.Arrays;

/** DataPageInfo class : the type of records stored on a directory page.
*
* April 9, 1998
*/

class DataPageInfo implements GlobalConst{


  /** HFPage returns int for avail space, so we use int here */
  int    availspace; 
  
  /** for efficient implementation of getRecCnt() */
  int    recct;    
  
  /** obvious: id of this particular data page (a HFPage) */
  PageId pageId = new PageId();   
    
  /** auxiliary fields of DataPageInfo */

  public static final int size = 12;// size of DataPageInfo object in bytes

  private byte [] data;  // a data buffer
  
  private int offset;


/**
 *  We can store roughly pagesize/sizeof(DataPageInfo) records per
 *  directory page; for any given HeapFile insertion, it is likely
 *  that at least one of those referenced data pages will have
 *  enough free space to satisfy the request.
 */


  /** Default constructor
   */
  public DataPageInfo() {  
    data = new byte[12]; // size of datapageinfo
    // int availspace = 0;
    recct =0;
    pageId.pid = INVALID_PAGE;
    offset = 0;
  }
  
  /** Constructor 
   * @param array  a byte array
   */
  public DataPageInfo(byte[] array) {
    data = array;
    offset = 0;
  }

      
  public byte [] returnByteArray() {
    return data;
  }
      
      
  /** constructor: translate a tuple to a DataPageInfo object
   *  it will make a copy of the data in the tuple
   * @param atuple: the input tuple
   */
  public DataPageInfo(Map _atuple) throws IOException
  {   
     // need check _atuple size == this.size ?otherwise, throw new exception
     if (_atuple.size("di")!=12){
      // throw new (null, "HEAPFILE: TUPLE SIZE ERROR");
    }

    else{
      data = _atuple.getMapByteArray("di");
      // System.out.println("DPINFO: "+ _atuple.getMapOffset());
      offset = _atuple.getMapOffset();
  
      availspace = ConvertMap.getIntValue(offset, data);
      recct = ConvertMap.getIntValue(offset+4, data);
      pageId = new PageId();
      pageId.pid = ConvertMap.getIntValue(offset+8, data);
      
    }
      // data = _aMap.getMapByteArray();
      // offset = _aMap.getMapOffset();
      
      // availspace = ConvertMap.getIntValue(offset, data);
      // recct = ConvertMap.getIntValue(offset+4, data);
      // pageId = new PageId();
      // pageId.pid = ConvertMap.getIntValue(offset+8, data);
      
  }
  
  
  /** convert this class objcet to a tuple(like cast a DataPageInfo to Tuple)
   *  
   *
   */
  public Map convertToMap() throws IOException
  {

    // 1) write availspace, recct, pageId into data []
    ConvertMap.setIntValue(availspace, offset, data);
    ConvertMap.setIntValue(recct, offset+4, data);
    ConvertMap.setIntValue(pageId.pid, offset+8, data);


    // 2) creat a Tuple object using this array
    Map aMap = new Map(data, offset); 
    // System.out.println("Here"+ Arrays.toString(data));
    // 3) return tuple object
    return aMap;

  }
  
    
  /** write this object's useful fields(availspace, recct, pageId) 
   *  to the data[](may be in buffer pool)
   *  
   */
  public void flushToMap() throws IOException
  {
     // write availspace, recct, pageId into "data[]"
    // System.out.println("Offsrt: "+offset);
    ConvertMap.setIntValue(availspace, offset, data);
    // System.out.println(recct);
    ConvertMap.setIntValue(recct, offset+4, data);
    // System.out.println(pageId.pid);
    ConvertMap.setIntValue(pageId.pid, offset+8, data);
    // System.out.println("After Flush: "+Arrays.toString(data));
    // here we assume data[] already points to buffer pool
  
  }
  
}






