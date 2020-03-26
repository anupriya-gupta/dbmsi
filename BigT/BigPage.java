/* File HFPage.java */

package BigT;

import java.io.*;
import java.util.Arrays;

import global.*;
import diskmgr.*;


 /**
  * Define constant values for INVALID_SLOT and EMPTY_SLOT
  */

interface ConstSlot{
  int INVALID_SLOT =  -1;
  int EMPTY_SLOT = -1;
}

/** Class heap file page.
 * The design assumes that records are kept compacted when
 * deletions are performed. 
 */

public class BigPage extends Page implements ConstSlot {
  
  
    public static final int SIZE_OF_SLOT = 4;
    public static final int DPFIXED =  4 * 2  + 3 * 4;
    
    public static final int SLOT_CNT = 0;
    public static final int USED_PTR = 2;
    public static final int FREE_SPACE = 4;
    public static final int TYPE = 6;
    public static final int PREV_PAGE = 8;
    public static final int NEXT_PAGE = 12;
    public static final int CUR_PAGE = 16;
    
    /* Warning:
        These items must all pack tight, (no padding) for
        the current implementation to work properly.
        Be careful when modifying this class.
    */
    
    /**
     * number of slots in use
     */
    private    short     slotCnt;  
    
    /**
     * offset of first used byte by data records in data[]
     */
    private    short     usedPtr;   
    
    /**
     * number of bytes free in data[]
     */
    private    short     freeSpace;  
    
    /**
     * an arbitrary value used by subclasses as needed
     */
    private    short     type;     
    
    /**
     * backward pointer to data page
     */
    private    PageId   prevPage = new PageId(); 
    
    /**
     * forward pointer to data page
     */
    private   PageId    nextPage = new PageId();  
    
    /**
     *  page number of this page
     */
    protected    PageId    curPage = new PageId();   
    
    /**
     * Default constructor
     */
    
    public BigPage ()   {  }
    
    /**
     * Constructor of class HFPage
     * open a HFPage and make this HFpage piont to the given page
     * @param  page  the given page in Page type
     */
    
    public BigPage(Page page)
        {
        data = page.getpage();
        }
    
    /**
     * Constructor of class HFPage
     * open a existed hfpage
     * @param  apage   a page in buffer pool
     */
    
    public void openHFpage(Page apage)
        {
        data = apage.getpage();
        }
    
    /**
     * Constructor of class HFPage
     * initialize a new page
     * @param	pageNo	the page number of a new page to be initialized
     * @param	apage	the Page to be initialized 
     * @see		Page
     * @exception IOException I/O errors
     */
    
    
    public void init(PageId pageNo, Page apage)
        throws IOException
        {
        data = apage.getpage();
        
        slotCnt = 0;                // no slots in use
        ConvertMap.setShortValue (slotCnt, SLOT_CNT, data);
        
        curPage.pid = pageNo.pid;
        ConvertMap.setIntValue (curPage.pid, CUR_PAGE, data);
        
        nextPage.pid = prevPage.pid = INVALID_PAGE;
        ConvertMap.setIntValue (prevPage.pid, PREV_PAGE, data);
        ConvertMap.setIntValue (nextPage.pid, NEXT_PAGE, data);
        
        usedPtr = (short) MAX_SPACE;  // offset in data array (grow backwards)
        ConvertMap.setShortValue (usedPtr, USED_PTR, data);
        
        freeSpace = (short) (MAX_SPACE - DPFIXED);    // amount of space available
        ConvertMap.setShortValue (freeSpace, FREE_SPACE, data);
      
    }
  
  /**
   * @return byte array
   */
  
    public byte [] getHFpageArray()
    {
      return data;
    }  
  
  /**
   * Dump contents of a page
   * @exception IOException I/O errors
   */
    public void dumpPage() throws IOException {
        int i, n ;
        int length, offset;
        
        curPage.pid =  ConvertMap.getIntValue (CUR_PAGE, data);
        nextPage.pid =  ConvertMap.getIntValue (NEXT_PAGE, data);
        usedPtr =  ConvertMap.getShortValue (USED_PTR, data);
        freeSpace =  ConvertMap.getShortValue (FREE_SPACE, data);
        slotCnt =  ConvertMap.getShortValue (SLOT_CNT, data);
        
        System.out.println("dumpPage");
        System.out.println("curPage= " + curPage.pid);
        System.out.println("nextPage= " + nextPage.pid);
        System.out.println("usedPtr= " + usedPtr);
        System.out.println("freeSpace= " + freeSpace);
        System.out.println("slotCnt= " + slotCnt);
        
        for (i= 0, n=DPFIXED; i < slotCnt; n +=SIZE_OF_SLOT, i++) {
            length =  ConvertMap.getShortValue (n, data);
            offset =  ConvertMap.getShortValue (n+2, data);
            System.out.println("slotNo " + i +" offset= " + offset);
            System.out.println("slotNo " + i +" length= " + length);
        }
      
    }
  
  /**
   * @return	PageId of previous page
   * @exception IOException I/O errors
   */
    public PageId getPrevPage() throws IOException 
    {
        prevPage.pid =  ConvertMap.getIntValue (PREV_PAGE, data);
        return prevPage;
    }
    
    /**
     * sets value of prevPage to pageNo
     * @param       pageNo  page number for previous page
     * @exception IOException I/O errors
     */
    public void setPrevPage(PageId pageNo) throws IOException
    {
        prevPage.pid = pageNo.pid;
        ConvertMap.setIntValue (prevPage.pid, PREV_PAGE, data);
    }
    
    /**
     * @return     page number of next page
     * @exception IOException I/O errors
     */
    public PageId getNextPage() throws IOException
    {
        nextPage.pid =  ConvertMap.getIntValue (NEXT_PAGE, data);    
        return nextPage;
    }
    
    /**
     * sets value of nextPage to pageNo
     * @param	pageNo	page number for next page
     * @exception IOException I/O errors
     */
    public void setNextPage(PageId pageNo) throws IOException
    {
        nextPage.pid = pageNo.pid;
        ConvertMap.setIntValue (nextPage.pid, NEXT_PAGE, data);
    }
    
    /**
     * @return 	page number of current page
     * @exception IOException I/O errors
     */
    public PageId getCurPage() throws IOException
    {
        curPage.pid =  ConvertMap.getIntValue (CUR_PAGE, data);
        return curPage;
    }
    
    /**
     * sets value of curPage to pageNo
     * @param	pageNo	page number for current page
     * @exception IOException I/O errors
     */
    public void setCurPage(PageId pageNo) throws IOException
    {
        curPage.pid = pageNo.pid;
        ConvertMap.setIntValue (curPage.pid, CUR_PAGE, data);
    }
    
    /**
     * @return 	the ype
     * @exception IOException I/O errors
     */
    public short getType() throws IOException {
        type =  ConvertMap.getShortValue (TYPE, data);
        return type;
    }
    
    /**
     * sets value of type
     * @param	valtype     an arbitrary value
     * @exception IOException I/O errors
     */
    public void setType(short valtype) throws IOException
    {
        type = valtype;
        ConvertMap.setShortValue (type, TYPE, data);
    }
    
    /**
     * @return 	slotCnt used in this page
     * @exception IOException I/O errors
     */
    public short getSlotCnt() 
        throws IOException
        {
        slotCnt =  ConvertMap.getShortValue (SLOT_CNT, data);
        return slotCnt;
        }
    
    /**
     * sets slot contents
     * @param       slotno  the slot number 
     * @param 	length  length of record the slot contains
     * @param	offset  offset of record
     * @exception IOException I/O errors
     */
    public void setSlot(int slotno, int length, int offset)
        throws IOException
        {
        int position = DPFIXED + slotno * SIZE_OF_SLOT;
        ConvertMap.setShortValue((short)length, position, data);
        ConvertMap.setShortValue((short)offset, position+2, data);
        }
    
    /**
     * @param	slotno	slot number
     * @exception IOException I/O errors
     * @return	the length of record the given slot contains
     */
    public short getSlotLength(int slotno)
        throws IOException
        {
        int position = DPFIXED + slotno * SIZE_OF_SLOT;
        short val= ConvertMap.getShortValue(position, data);
        return val;
        }
    
    /**
     * @param       slotno  slot number
     * @exception IOException I/O errors
     * @return      the offset of record the given slot contains
     */
    public short getSlotOffset(int slotno)
        throws IOException
        {
        int position = DPFIXED + slotno * SIZE_OF_SLOT;
        short val= ConvertMap.getShortValue(position +2, data);
        return val;
        }
    
    
    /**
     * inserts a new record onto the page, returns RID of this record 
     * @param	record 	a record to be inserted
     * @return	RID of record, null if sufficient space does not exist
     * @exception IOException I/O errors
     * in C++ Status insertRecord(char *recPtr, int recLen, RID& mid)
     */
    public MID insertMap ( byte[] map) throws IOException
    {
        MID mid = new MID();
        
        int mapLen = map.length;
        int spaceNeeded = mapLen + SIZE_OF_SLOT;
        // System.out.println("Maplen:"+mapLen);
        // Start by checking if sufficient space exists.
        // This is an upper bound check. May not actually need a slot
        // if we can find an empty one.
        
        freeSpace = ConvertMap.getShortValue (FREE_SPACE, data);
        if (spaceNeeded > freeSpace) {
            return null;
        
        } else {
        
        // look for an empty slot
            slotCnt = ConvertMap.getShortValue (SLOT_CNT, data); 
            int i; 
            short length;
            for (i= 0; i < slotCnt; i++) 
            {
                length = getSlotLength(i); 
                if (length == EMPTY_SLOT)
                break;
            }
            
            if(i == slotCnt)   //use a new slot
            {           
                // adjust free space        
                freeSpace -= spaceNeeded;
                ConvertMap.setShortValue (freeSpace, FREE_SPACE, data);
                
                slotCnt++;
                ConvertMap.setShortValue (slotCnt, SLOT_CNT, data);
                
            }
            else {
            // reusing an existing slot
                freeSpace -= mapLen;
                ConvertMap.setShortValue (freeSpace, FREE_SPACE, data);
            }
                
            usedPtr = ConvertMap.getShortValue (USED_PTR, data);
            usedPtr -= mapLen;    // adjust usedPtr
            ConvertMap.setShortValue (usedPtr, USED_PTR, data);
            
            //insert the slot info onto the data page
            setSlot(i, mapLen, usedPtr);   
            
            // insert data onto the data page
            System.arraycopy (map, 0, data, usedPtr, mapLen);
            curPage.pid = ConvertMap.getIntValue (CUR_PAGE, data);
            mid.pageNo.pid = curPage.pid;
            mid.slotNo = i;
            return   mid ;
        }
    } 
    
    /**
     * delete the record with the specified mid
     * @param	mid 	the record ID
     * @exception	InvalidSlotNumberException Invalid slot number
     * @exception IOException I/O errors
     * in C++ Status deleteRecord(const RID& mid)
     */
    public void deleteMap ( MID mid ) throws IOException, InvalidSlotNumberException {
        int slotNo = mid.slotNo;
        short mapLen = getSlotLength (slotNo);
        slotCnt = ConvertMap.getShortValue (SLOT_CNT, data);
        
        // first check if the record being deleted is actually valid
        if ((slotNo >= 0) && (slotNo < slotCnt) && (mapLen > 0)) { 
        // The records always need to be compacted, as they are
        // not necessarily stored on the page in the order that
        // they are listed in the slot index.
        
        // offset of record being deleted
            int offset = getSlotOffset(slotNo); 
            usedPtr = ConvertMap.getShortValue (USED_PTR, data);
            int newSpot= usedPtr + mapLen;
            int size = offset - usedPtr;
            
            // shift bytes to the right
            System.arraycopy(data, usedPtr, data, newSpot, size);
            
            // now need to adjust offsets of all valid slots that refer
            // to the left of the record being removed. (by the size of the hole)
            
            int i, n, chkoffset;
            for (i = 0, n = DPFIXED; i < slotCnt; n +=SIZE_OF_SLOT, i++) {
                if ((getSlotLength(i) >= 0)) {
                    chkoffset = getSlotOffset(i);
                    if(chkoffset < offset)
                    {
                        chkoffset += mapLen;
                        ConvertMap.setShortValue((short)chkoffset, n+2, data);
                    }
                }
            }
        
            // move used Ptr forwar
            usedPtr += mapLen;   
            ConvertMap.setShortValue (usedPtr, USED_PTR, data);
            
            // increase freespace by size of hole
            freeSpace = ConvertMap.getShortValue(FREE_SPACE, data);
            freeSpace += mapLen;  
            ConvertMap.setShortValue (freeSpace, FREE_SPACE, data);
            
            setSlot(slotNo, EMPTY_SLOT, 0);  // mark slot free
        } else {
            throw new InvalidSlotNumberException (null, "BigTable: INVALID_SLOTNO");
        }
    }
    
    /**
     * @return RID of first record on page, null if page contains no records.  
     * @exception  IOException I/O errors
     * in C++ Status firstRecord(RID& firstRid)
     * 
     */ 
    public MID firstMap() throws IOException {
        MID mid = new MID();
        // find the first non-empty slot
        
        
        slotCnt = ConvertMap.getShortValue (SLOT_CNT, data);
        
        int i;
        short length;
        for (i= 0; i < slotCnt; i++)
        {
            length = getSlotLength (i);
            if (length != EMPTY_SLOT)
                break;
        }
        
        if(i== slotCnt)
            return null;
        
        // found a non-empty slot
        
        mid.slotNo = i;
        curPage.pid= ConvertMap.getIntValue(CUR_PAGE, data);
        mid.pageNo.pid = curPage.pid;
        
        return mid;
    }
    
    /**
     * @return RID of next record on the page, null if no more 
     * records exist on the page
     * @param 	curRid	current record ID
     * @exception  IOException I/O errors
     * in C++ Status nextRecord (RID curRid, RID& nextRid)
     */
    public MID nextMap (MID curRid) throws IOException {
        MID mid = new MID();
        slotCnt = ConvertMap.getShortValue (SLOT_CNT, data);
        
        int i=curRid.slotNo;
        short length; 
        
        // find the next non-empty slot
        for (i++; i < slotCnt;  i++)
        {
            length = getSlotLength(i);
            if (length != EMPTY_SLOT)
                break;
        }
        
        if(i >= slotCnt)
        return null;
        
        // found a non-empty slot
        
        mid.slotNo = i;
        curPage.pid = ConvertMap.getIntValue(CUR_PAGE, data);
        mid.pageNo.pid = curPage.pid;
        
        return mid;
    }

    public MID prevMap (MID curMid) throws IOException {
        MID mid = new MID();
        slotCnt = ConvertMap.getShortValue (SLOT_CNT, data);
        
        int i=curMid.slotNo;
        short length; 
        
        // find the next non-empty slot
        for (i--; i > 0;  i--)
        {
            length = getSlotLength(i);
            if (length != EMPTY_SLOT)
                break;
        }
        
        if(i < 0)
            return null;
        
        // found a non-empty slot
        
        mid.slotNo = i;
        curPage.pid = ConvertMap.getIntValue(CUR_PAGE, data);
        mid.pageNo.pid = curPage.pid;
        
        return mid;
    }
  
  /**
   * copies out record with RID mid into record pointer.
   * <br>
   * Status getRecord(RID mid, char *recPtr, int& recLen)
   * @param	mid 	the record ID
   * @return 	a tuple contains the record
   * @exception   InvalidSlotNumberException Invalid slot number
   * @exception  	IOException I/O errors
   * @see 	Tuple
   */
    public Map getMap ( MID mid ) throws IOException, InvalidSlotNumberException {
        short mapLen;
        short offset;
        byte []record;
        PageId pageNo = new PageId();
        pageNo.pid= mid.pageNo.pid;
        curPage.pid = ConvertMap.getIntValue (CUR_PAGE, data);
        int slotNo = mid.slotNo;
        
        // length of record being returned
        mapLen = getSlotLength (slotNo);
        // System.out.println(mapLen);
        slotCnt = ConvertMap.getShortValue (SLOT_CNT, data);
        if (( slotNo >=0) && (slotNo < slotCnt) && (mapLen >0) && (pageNo.pid == curPage.pid)) {
            offset = getSlotOffset (slotNo);
            record = new byte[mapLen];
            System.arraycopy(data, offset, record, 0, mapLen);
            Map map = new Map(record, 0);
            return map;
        }
      
      else {
        throw new InvalidSlotNumberException (null, "HEAPFILE: INVALID_SLOTNO");
      }
     
      
    }
  
  /**
   * returns a tuple in a byte array[pageSize] with given RID mid.
   * <br>
   * in C++	Status returnRecord(RID mid, char*& recPtr, int& recLen)
   * @param       mid     the record ID
   * @return      a tuple  with its length and offset in the byte array
   * @exception   InvalidSlotNumberException Invalid slot number
   * @exception   IOException I/O errors
   * @see 	Tuple
   */  
    public Map returnRecord ( MID mid ) throws IOException, InvalidSlotNumberException
    {
        short recLen;
        short offset;
        PageId pageNo = new PageId();
        pageNo.pid = mid.pageNo.pid;
        
        curPage.pid = ConvertMap.getIntValue (CUR_PAGE, data);
        int slotNo = mid.slotNo;
        
        // length of record being returned
        recLen = getSlotLength (slotNo);
        // System.out.println("recordLength: "+recLen);
        slotCnt = ConvertMap.getShortValue (SLOT_CNT, data);
        
        if (( slotNo >=0) && (slotNo < slotCnt) && (recLen >0) && (pageNo.pid == curPage.pid))
        {
        
            offset = getSlotOffset (slotNo);
            // System.out.println("recordLength: "+Arra);
            Map tuple = new Map(data, offset);
            return tuple;
        } else {   
            throw new InvalidSlotNumberException (null, "HEAPFILE: INVALID_SLOTNO");
        }
      
    }
  
  /**
   * returns the amount of available space on the page.
   * @return  the amount of available space on the page
   * @exception  IOException I/O errors
   */  
    public int available_space() throws IOException
    {
      freeSpace = ConvertMap.getShortValue (FREE_SPACE, data);
      return (freeSpace - SIZE_OF_SLOT);
    }
  
  /**      
   * Determining if the page is empty
   * @return true if the HFPage is has no records in it, false otherwise  
   * @exception  IOException I/O errors
   */
    public boolean empty() throws IOException
    {
        int i;
        short length;
        // look for an empty slot
        slotCnt = ConvertMap.getShortValue (SLOT_CNT, data);
        
        for (i= 0; i < slotCnt; i++) 
        {
            length = getSlotLength(i);
            if (length != EMPTY_SLOT)
                return false;
        }    
        
        return true;
    }
  
  /**
   * Compacts the slot directory on an HFPage.
   * WARNING -- this will probably lead to a change in the RIDs of
   * records on the page.  You CAN'T DO THIS on most kinds of pages.
   * @exception  IOException I/O errors
   */
    protected void compact_slot_dir() throws IOException {
        int  current_scan_posn = 0;   // current scan position
        int  first_free_slot   = -1;   // An invalid position.
        boolean move = false;          // Move a record? -- initially false
        short length;
        short offset;		
        
        slotCnt = ConvertMap.getShortValue (SLOT_CNT, data);
        freeSpace = ConvertMap.getShortValue (FREE_SPACE, data);
        
        while (current_scan_posn < slotCnt) {
            length = getSlotLength (current_scan_posn);
        
            if ((length == EMPTY_SLOT) && (move == false)) {
                move = true;
                first_free_slot = current_scan_posn;
            } 
            else if ((length != EMPTY_SLOT) && (move == true)) {
                offset = getSlotOffset (current_scan_posn);
                
                // slot[first_free_slot].length = slot[current_scan_posn].length;
                // slot[first_free_slot].offset = slot[current_scan_posn].offset; 
                setSlot ( first_free_slot, length, offset);
                
                // Mark the current_scan_posn as empty
                //  slot[current_scan_posn].length = EMPTY_SLOT;
                setSlot (current_scan_posn, EMPTY_SLOT, 0);
                
                // Now make the first_free_slot point to the next free slot.
                first_free_slot++;
                
                // slot[current_scan_posn].length == EMPTY_SLOT !!
                while (getSlotLength (first_free_slot) != EMPTY_SLOT)  
                {
                    first_free_slot++;
                }
            }
        
            current_scan_posn++;           
        }
        
        if (move == true) {
            // Adjust amount of free space on page and slotCnt
            freeSpace += SIZE_OF_SLOT * (slotCnt - first_free_slot);
            slotCnt = (short) first_free_slot;
            ConvertMap.setShortValue (freeSpace, FREE_SPACE, data);
            ConvertMap.setShortValue (slotCnt, SLOT_CNT, data);
        }
    }
  
}