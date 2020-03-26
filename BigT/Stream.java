package BigT;

/** JAVA */
/**
 * Scan.java-  class Scan
 *
 */

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

import global.*;
import bufmgr.*;
import diskmgr.*;


/**	
 * A Scan object is created ONLY through the function openScan
 * of a HeapFile. It supports the getNext interface which will
 * simply retrieve the next record in the heapfile.
 *
 * An object of type scan will always have pinned one directory page
 * of the heapfile.
 */
public class Stream implements GlobalConst{
 
    /**
     * Note that one record in our way-cool HeapFile implementation is
     * specified by six (6) parameters, some of which can be determined
     * from others:
     */
    boolean isRowFilterARange = false;
    String rowval1 = "";
    String rowval2 = "";
    boolean isColFilterARange = false;
    String colval1 = "";
    String colval2 = "";
    boolean isValFilterARange = false;
    String val1 = "";
    String val2 = "";
    /** The heapfile we are using. */
    private bigt  _hf;

    /** PageId of current directory page (which is itself an HFPage) */
    private PageId dirpageId = new PageId();

    /** pointer to in-core data of dirpageId (page is pinned) */
    private BigPage dirpage = new BigPage();

    /** record ID of the DataPageInfo struct (in the directory page) which
     * describes the data page where our current record lives.
     */
    private MID datapageRid = new MID();

    /** the actual PageId of the data page with the current record */
    private PageId datapageId = new PageId();

    /** in-core copy (pinned) of the same */
    private BigPage datapage = new BigPage();

    /** record ID of the current record (from the current data page) */
    private MID userrid = new MID();

    /** Status of next user status */
    private boolean nextUserStatus;

    private int order;

    private String rowFilter = "", columnFilter = "", valueFilter = "";
    
     
    /** The constructor pins the first directory page in the file
     * and initializes its private data members from the private
     * data member from hf
     *
     * @exception InvalidTupleSizeException Invalid tuple size
     * @exception IOException I/O errors
     *
     * @param hf A HeapFile object
     */
    public Stream(bigt hf) throws InvalidTupleSizeException, IOException
    {
        init(hf);
    }

    public Stream(bigt hf, int order, String rowFilter, String columnFilter, String valueFilter)
            throws InvalidTupleSizeException, IOException, HFBufMgrException {
        init(hf, order, rowFilter, columnFilter, valueFilter);
    }


  
    /** Retrieve the next record in a sequential scan
     *
     * @exception InvalidTupleSizeException Invalid tuple size
     * @exception IOException I/O errors
     *
     * @param rid Record ID of the record
     * @return the Tuple of the retrieved record.
     */
    public Map getNext(MID rid) throws InvalidTupleSizeException, IOException
    {
        // System.out.println(rowFilter+" c"+columnFilter+ " "+ valueFilter);
        // if (order =)
        Map recptrmap = null;
        
        if (nextUserStatus != true) {
            // if(order == MapOrder.Ascending)
                nextDataPage();
            // else
            //     prevDataPage();
        }
        
        if (datapage == null) return null;
        
        rid.pageNo.pid = userrid.pageNo.pid;    
        rid.slotNo = userrid.slotNo;
            
        try {
            recptrmap = datapage.getMap(rid);
        } catch (Exception e) {
        //    System.err.println("SCAN: Error in Scan" + e);
            e.printStackTrace();
        }   
        //query mydata2.bigdb_1 1 1 Singapore * * 100
        // if (order == MapOrder.Ascending) {
            userrid = datapage.nextMap(rid);    
        // }else {
        //     userrid = datapage.prevMap(rid);
        // }
        if(userrid == null) nextUserStatus = false;
        else nextUserStatus = true;
        recptrmap.mapSetup();
        String rowLabel = recptrmap.getRowLabel();
        String colLabel = recptrmap.getColumnLabel();
        String valLabel = recptrmap.getValue();
        // if (isRowFilterARange){
        //     if(rowLabel.compareToIgnoreCase(rowval1) < 0 || rowLabel.compareToIgnoreCase(rowval2) > 0)
        //         return getNext(new MID());
        // }
        // if (isColFilterARange){
        //     if(colLabel.compareToIgnoreCase(colval1) < 0 || colLabel.compareToIgnoreCase(colval2) > 0)
        //         return getNext(new MID());
        // }
        // if (isValFilterARange){
        //     if(valLabel.compareToIgnoreCase(val1) < 0 || valLabel.compareToIgnoreCase(val2) > 0)
        //         return getNext(new MID());
        // }
        // if (!isRowFilterARange && rowFilter != "" && !rowLabel.equals(rowFilter))
        //     return getNext(new MID());
        // if (!isColFilterARange && columnFilter != "" && !colLabel.equals(columnFilter))
        //     return getNext(new MID());
        // if (!isValFilterARange && valueFilter != "" && !valLabel.equals(valueFilter))
        //     return getNext(new MID());
        // if (recptrmap.getRowLabel().contains(rowFilter) && recptrmap.getColumnLabel().contains(columnFilter) && recptrmap.getValue().contains(valueFilter))
        //     return recptrmap;
        
        return recptrmap;
        
    }

    /** Position the scan cursor to the record with the given rid.
     * 
     * @exception InvalidTupleSizeException Invalid tuple size
     * @exception IOException I/O errors
     * @param rid Record ID of the given record
     * @return 	true if successful, 
     *			false otherwise.
     */
    public boolean position(MID rid) throws InvalidTupleSizeException, IOException { 
        MID    nxtrid = new MID();
        boolean bst;

        bst = peekNext(nxtrid);

        if (nxtrid.equals(rid)==true) 
            return true;

        // This is kind lame, but otherwise it will take all day.
        PageId pgid = new PageId();
        pgid.pid = rid.pageNo.pid;
 
        if (!datapageId.equals(pgid)) {

            // reset everything and start over from the beginning
            reset();
      
            bst =  firstDataPage();

            if (bst != true) return bst;
      
            while (!datapageId.equals(pgid)) {
	            bst = nextDataPage();
                if (bst != true) return bst;
            }
        }
    
        // Now we are on the correct page.
        
        try{
            userrid = datapage.firstMap();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        if (userrid == null)
        {
            bst = false;
            return bst;
        }

        bst = peekNext(nxtrid);

        while ((bst == true) && (nxtrid != rid))
            bst = mvNext(nxtrid);
    
        return bst;
    }


    /** Do all the constructor work
     *
     * @exception InvalidTupleSizeException Invalid tuple size
     * @exception IOException I/O errors
     *
     * @param hf A HeapFile object
     */
    private void init(bigt hf) throws InvalidTupleSizeException, IOException
    {
        _hf = hf;

        firstDataPage();
    }

    private void init(bigt hf, int order, String rowFilter, String columnFilter, String valueFilter)
            throws InvalidTupleSizeException, IOException, HFBufMgrException {
        _hf = hf;
        this.order = order;
        this.rowFilter = rowFilter;
        this.columnFilter = columnFilter;
        this.valueFilter = valueFilter;
        if (rowFilter.equals("*")) {
            this.rowFilter = "";
        }
        if (columnFilter.equals("*")) {
            this.columnFilter = "";
        }
        if (valueFilter.equals("*")) {
            this.valueFilter = "";
        }
        // System.out.println(rowFilter+coulmnFilter.charAt(0)+valueFilter);
        if (rowFilter.charAt(0) == '['){
            isRowFilterARange = true;
            rowFilter = rowFilter.substring(1, rowFilter.length()-1 );
            StringTokenizer s = new StringTokenizer(rowFilter);
            rowval1 = s.nextToken(",").trim();
            rowval2 = s.nextToken(",").trim();
        }
        // System.out.println(coulmnFilter.charAt(0));
        if (columnFilter.charAt(0) == '['){
            isColFilterARange = true;
            columnFilter = columnFilter.substring(1, columnFilter.length()-1 );
            StringTokenizer s = new StringTokenizer(columnFilter);
            colval1 = s.nextToken(",").trim();
            colval2 = s.nextToken(",").trim();
        }
        if (valueFilter.charAt(0) == '['){
            isValFilterARange = true;
            valueFilter = valueFilter.substring(1, valueFilter.length()-1 );
            StringTokenizer s = new StringTokenizer(valueFilter);
            val1 = s.nextToken(",").trim();
            val2 = s.nextToken(",").trim();
        }
        if (order == MapOrder.Ascending) {
            firstDataPage();
        }else {
            lastDataPage();
        }
    }


    /** Closes the Scan object */
    public void closescan() {
    	reset();
    }
   

    /** Reset everything and unpin all pages. */
    private void reset() { 

        if (datapage != null) {
    
            try{
                unpinPage(datapageId, false);
            }
            catch (Exception e){
                // 	System.err.println("SCAN: Error in Scan" + e);
                e.printStackTrace();
            }  
        }
        datapageId.pid = 0;
        datapage = null;

        if (dirpage != null) {
        
            try{
                unpinPage(dirpageId, false);
            }
            catch (Exception e){
                //     System.err.println("SCAN: Error in Scan: " + e);
                e.printStackTrace();
            }
        }
        dirpage = null;
    
        nextUserStatus = true;

    }
 
 
    /** Move to the first data page in the file. 
     * @exception InvalidTupleSizeException Invalid tuple size
     * @exception IOException I/O errors
     * @return true if successful
     *         false otherwise
     */
    private boolean firstDataPage() throws InvalidTupleSizeException, IOException
    {
        DataPageInfo dpinfo;
        Map        rectuple = null;
        Boolean      bst;

        /** copy data about first directory page */
    
        dirpageId.pid = _hf._firstDirPageId.pid;  
        nextUserStatus = true;

        /** get first directory page and pin it */
        try {
            dirpage  = new BigPage();
            pinPage(dirpageId, (Page) dirpage, false);	   
        } catch (Exception e) {
            //    System.err.println("SCAN Error, try pinpage: " + e);
            e.printStackTrace();
        }
    
        /** now try to get a pointer to the first datapage */
        datapageRid = dirpage.firstMap();
        
        if (datapageRid != null) {
            /** there is a datapage record on the first directory page: */
            
            try {
                rectuple = dirpage.getMap(datapageRid);
            } catch (Exception e) {
                //	System.err.println("SCAN: Chain Error in Scan: " + e);
                e.printStackTrace();
            }		
      			    
            dpinfo = new DataPageInfo(rectuple);
            datapageId.pid = dpinfo.pageId.pid;

        } else {

            /** the first directory page is the only one which can possibly remain
             * empty: therefore try to get the next directory page and
             * check it. The next one has to contain a datapage record, unless
             * the heapfile is empty:
             */
            PageId nextDirPageId = new PageId();
            
            nextDirPageId = dirpage.getNextPage();
            
            if (nextDirPageId.pid != INVALID_PAGE) {
	
                try {
                    unpinPage(dirpageId, false);
                    dirpage = null;
                }
                
                catch (Exception e) {
                //	System.err.println("SCAN: Error in 1stdatapage 1 " + e);
                    e.printStackTrace();
                }
        	
                try {
                    dirpage = new BigPage();
                    pinPage(nextDirPageId, (Page )dirpage, false);
                } catch (Exception e) {
                    //  System.err.println("SCAN: Error in 1stdatapage 2 " + e);
                    e.printStackTrace();
                }
	
                /** now try again to read a data record: */
                
                try {
                    datapageRid = dirpage.firstMap();
                    // System.out.println("dRID: "+datapageRid.slotNo);
                } catch (Exception e) {
                    //  System.err.println("SCAN: Error in 1stdatapg 3 " + e);
                    e.printStackTrace();
                    datapageId.pid = INVALID_PAGE;
                }
       
                if(datapageRid != null) {
                    
                    try { 
                        rectuple = dirpage.getMap(datapageRid);
                    } catch (Exception e) {
                        //    System.err.println("SCAN: Error getRecord 4: " + e);
                        e.printStackTrace();
                    }
                
                    if (rectuple.size() != DataPageInfo.size) return false;
        
                    dpinfo = new DataPageInfo(rectuple);
                    datapageId.pid = dpinfo.pageId.pid;
        
                } else {
                    // heapfile empty
                    datapageId.pid = INVALID_PAGE;
                }
            } else {// heapfile empty
                datapageId.pid = INVALID_PAGE;
            }
        }	
	
	    datapage = null;

        try{
            nextDataPage();
        }
        
        catch (Exception e) {
            //  System.err.println("SCAN Error: 1st_next 0: " + e);
            e.printStackTrace();
        }
	
        return true;
      
      /** ASSERTIONS:
       * - first directory page pinned
       * - this->dirpageId has Id of first directory page
       * - this->dirpage valid
       * - if heapfile empty:
       *    - this->datapage == NULL, this->datapageId==INVALID_PAGE
       * - if heapfile nonempty:
       *    - this->datapage == NULL, this->datapageId, this->datapageRid valid
       *    - first datapage is not yet pinned
       */
    
    }
    
    private boolean lastDataPage() throws HFBufMgrException, IOException {
        DataPageInfo dpinfo;
        Map        rectuple = null;
        Boolean      bst;
        BigPage cuDirPage = new BigPage();
        PageId nextDirPageId = new PageId();
        /** copy data about first directory page */
    
        dirpageId.pid = _hf._firstDirPageId.pid; 
        nextDirPageId = dirpageId;
        nextUserStatus = true;

        // try {
        //     dirpage  = new BigPage();
        //     pinPage(dirpageId, (Page) dirpage, false);	   
        // } catch (Exception e) {
        //     //    System.err.println("SCAN Error, try pinpage: " + e);
        //     e.printStackTrace();
        // }
        while(nextDirPageId.pid != INVALID_PAGE){
            dirpage  = new BigPage();
            pinPage(dirpageId, (Page) dirpage, false);
            nextDirPageId = dirpage.getNextPage();
            unpinPage(dirpageId, false);
            if (nextDirPageId.pid != INVALID_PAGE) {
                dirpageId.pid = nextDirPageId.pid;
            }
            // unpinPage(datapageId, false);
        }

        /** get first directory page and pin it */
        // try {
        //     dirpage  = new BigPage();
        //     pinPage(dirpageId, (Page) dirpage, false);	   
        // } catch (Exception e) {
        //     //    System.err.println("SCAN Error, try pinpage: " + e);
        //     e.printStackTrace();
        // }

        datapageRid = dirpage.firstMap();
        MID tr = datapageRid;
        while (tr != null){
            tr = dirpage.nextMap(datapageRid);
            if( tr != null)
                datapageRid = tr;
        }
        if (datapageRid != null) {
            /** there is a datapage record on the first directory page: */
            
            try {
                
                rectuple = dirpage.getMap(datapageRid);
            } catch (Exception e) {
                //	System.err.println("SCAN: Chain Error in Scan: " + e);
                e.printStackTrace();
            }		
      			    
            dpinfo = new DataPageInfo(rectuple);
            datapageId.pid = dpinfo.pageId.pid;

        }

        datapage = null;

        try{
            prevDataPage();
        }catch (Exception e){

        }

        return true;
    }

    private boolean prevDataPage() throws IOException {
        DataPageInfo dpinfo;
        
        boolean nextDataPageStatus;
        PageId prevDirPageId = new PageId();
        Map rectuple = null;

        if ((dirpage == null) && (datapageId.pid == INVALID_PAGE))
            return false;

        if (datapage == null) {
            if (datapageId.pid == INVALID_PAGE) {
                // heapfile is empty to begin with
    
                try{
                    unpinPage(dirpageId, false);
                    dirpage = null;
                }
                catch (Exception e){
                    //  System.err.println("Scan: Chain Error: " + e);
                    e.printStackTrace();
                }
        
            } else {
        
                // pin first data page
                try {
                    datapage  = new BigPage();
                    pinPage(datapageId, (Page) datapage, false);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                
                try {
                    userrid = datapage.firstMap();
                    MID rMid = userrid;
                    while(rMid != null){
                        rMid = datapage.nextMap(userrid);
                        if (rMid != null) {
                            userrid = rMid;    
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
        
                return true;
            }
        }

        try{
            unpinPage(datapageId, false /* no dirty */);
            datapage = null;
        }
            catch (Exception e){
        
        }

        if (dirpage == null) {
            return false;
        }
    
        datapageRid = dirpage.prevMap(datapageRid);

        if (datapageRid == null) return false;

        else if (datapageRid.slotNo == INVALID_PAGE || datapageRid.pageNo.pid == INVALID_PAGE) {
            nextDataPageStatus = false;
            // we have read all datapage records on the current directory page
            
            // get next directory page
            prevDirPageId = dirpage.getPrevPage();
    
            // unpin the current directory page
            try {
                unpinPage(dirpageId, false /* not dirty */);
                dirpage = null;
                
                datapageId.pid = INVALID_PAGE;
            } catch (Exception e) {
	
            }
		    
            if (prevDirPageId.pid == INVALID_PAGE)
	            return false;
            else {
                // ASSERTION:
                // - nextDirPageId has correct id of the page which is to get
                
                dirpageId = prevDirPageId;
                
                try { 
                    dirpage  = new BigPage();
                    pinPage(dirpageId, (Page)dirpage, false);
                } catch (Exception e){ }
	
                if (dirpage == null)
                    return false;
                
                try {
                    datapageRid = dirpage.firstMap();
                    while (datapageRid != null) {
                        datapageRid = dirpage.nextMap(datapageRid);
                    }
                    nextDataPageStatus = true;
                } catch (Exception e){
                    nextDataPageStatus = false;
                    return false;
	            } 
            }
        }
        try {
            rectuple = dirpage.getMap(datapageRid);
        }
	
        catch (Exception e) {
            System.err.println("HeapFile: Error in Scan" + e);
        }
	
        if (rectuple.size() != DataPageInfo.size)
            return false;
                        
        dpinfo = new DataPageInfo(rectuple);
        datapageId.pid = dpinfo.pageId.pid;
	
        try {
            datapage = new BigPage();
            pinPage(dpinfo.pageId, (Page) datapage, false);
        } catch (Exception e) {
            System.err.println("HeapFile: Error in Scan" + e);
        }
	
     
        // - directory page is pinned
        // - datapage is pinned
        // - this->dirpageId, this->dirpage correct
        // - this->datapageId, this->datapage, this->datapageRid correct

        userrid = datapage.firstMap();
        MID rt = userrid;
        while (rt != null){
            rt = datapage.nextMap(userrid);
            if(rt != null)
                userrid = rt;
        }
        
        if(userrid == null)
        {
            nextUserStatus = false;
            return false;
        }
  
        return true;

    }

  /** Move to the next data page in the file and 
   * retrieve the next data page. 
   *
   * @return 		true if successful
   *			false if unsuccessful
   */
    private boolean nextDataPage() throws InvalidTupleSizeException,
	   IOException{
        DataPageInfo dpinfo;
        
        boolean nextDataPageStatus;
        PageId nextDirPageId = new PageId();
        Map rectuple = null;

        // ASSERTIONS:
        // - this->dirpageId has Id of current directory page
        // - this->dirpage is valid and pinned
        // (1) if heapfile empty:
        //    - this->datapage==NULL; this->datapageId == INVALID_PAGE
        // (2) if overall first record in heapfile:
        //    - this->datapage==NULL, but this->datapageId valid
        //    - this->datapageRid valid
        //    - current data page unpinned !!!
        // (3) if somewhere in heapfile
        //    - this->datapageId, this->datapage, this->datapageRid valid
        //    - current data page pinned
        // (4)- if the scan had already been done,
        //        dirpage = NULL;  datapageId = INVALID_PAGE
    
        if ((dirpage == null) && (datapageId.pid == INVALID_PAGE))
            return false;

        if (datapage == null) {
            if (datapageId.pid == INVALID_PAGE) {
	            // heapfile is empty to begin with
	
                try{
                    unpinPage(dirpageId, false);
                    dirpage = null;
                }
                catch (Exception e){
                    //  System.err.println("Scan: Chain Error: " + e);
                    e.printStackTrace();
                }
        
            } else {
        
                // pin first data page
                try {
                    datapage  = new BigPage();
                    pinPage(datapageId, (Page) datapage, false);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                
                try {
                    userrid = datapage.firstMap();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
        
                return true;
            }
        }
  
        // ASSERTIONS:
        // - this->datapage, this->datapageId, this->datapageRid valid
        // - current datapage pinned

        // unpin the current datapage
        try{
            unpinPage(datapageId, false /* no dirty */);
            datapage = null;
        }
            catch (Exception e){
        
        }
            
        // read next datapagerecord from current directory page
        // dirpage is set to NULL at the end of scan. Hence
        
        if (dirpage == null) {
            return false;
        }
    
        datapageRid = dirpage.nextMap(datapageRid);
    
        if (datapageRid == null) {
            nextDataPageStatus = false;
            // we have read all datapage records on the current directory page
            
            // get next directory page
            nextDirPageId = dirpage.getNextPage();
    
            // unpin the current directory page
            try {
                unpinPage(dirpageId, false /* not dirty */);
                dirpage = null;
                
                datapageId.pid = INVALID_PAGE;
            } catch (Exception e) {
	
            }
		    
            if (nextDirPageId.pid == INVALID_PAGE)
	            return false;
            else {
                // ASSERTION:
                // - nextDirPageId has correct id of the page which is to get
                
                dirpageId = nextDirPageId;
                
                try { 
                    dirpage  = new BigPage();
                    pinPage(dirpageId, (Page)dirpage, false);
                } catch (Exception e){ }
	
                if (dirpage == null)
                    return false;
                
                try {
                    datapageRid = dirpage.firstMap();
                    nextDataPageStatus = true;
                } catch (Exception e){
                    nextDataPageStatus = false;
                    return false;
	            } 
            }
        }
    
        // ASSERTION:
        // - this->dirpageId, this->dirpage valid
        // - this->dirpage pinned
        // - the new datapage to be read is on dirpage
        // - this->datapageRid has the Rid of the next datapage to be read
        // - this->datapage, this->datapageId invalid
    
        // data page is not yet loaded: read its record from the directory page
        try {
            rectuple = dirpage.getMap(datapageRid);
        }
	
        catch (Exception e) {
            System.err.println("HeapFile: Error in Scan" + e);
        }
	
        if (rectuple.size() != DataPageInfo.size)
            return false;
                        
        dpinfo = new DataPageInfo(rectuple);
        datapageId.pid = dpinfo.pageId.pid;
	
        try {
            datapage = new BigPage();
            pinPage(dpinfo.pageId, (Page) datapage, false);
        } catch (Exception e) {
            System.err.println("HeapFile: Error in Scan" + e);
        }
	
     
        // - directory page is pinned
        // - datapage is pinned
        // - this->dirpageId, this->dirpage correct
        // - this->datapageId, this->datapage, this->datapageRid correct

        userrid = datapage.firstMap();
        
        if(userrid == null)
        {
            nextUserStatus = false;
            return false;
        }
  
        return true;
    }


    private boolean peekNext(MID rid) {
    
        rid.pageNo.pid = userrid.pageNo.pid;
        rid.slotNo = userrid.slotNo;
        return true;
    
    }


    /** Move to the next record in a sequential scan.
     * Also returns the RID of the (new) current record.
     */
    private boolean mvNext(MID rid) throws InvalidTupleSizeException, IOException
    {
        MID nextrid;
        boolean status;

        if (datapage == null)
            return false;

    	nextrid = datapage.nextMap(rid);
	
        if( nextrid != null ){
            userrid.pageNo.pid = nextrid.pageNo.pid;
            userrid.slotNo = nextrid.slotNo;
            return true;
        } else {
	  
            status = nextDataPage();

            if (status==true){
                rid.pageNo.pid = userrid.pageNo.pid;
                rid.slotNo = userrid.slotNo;
            }
	
	    }
	    return true;
    }

    /**
   * short cut to access the pinPage function in bufmgr package.
   * @see bufmgr.pinPage
   */
    private void pinPage(PageId pageno, Page page, boolean emptyPage) throws HFBufMgrException {

        try {
            SystemDefs.JavabaseBM.pinPage(pageno, page, emptyPage);
        }
        catch (Exception e) {
            throw new HFBufMgrException(e,"Scan.java: pinPage() failed");
        }

    } // end of pinPage

  /**
   * short cut to access the unpinPage function in bufmgr package.
   * @see bufmgr.unpinPage
   */
    private void unpinPage(PageId pageno, boolean dirty) throws HFBufMgrException {

        try {
            SystemDefs.JavabaseBM.unpinPage(pageno, dirty);
        }
        catch (Exception e) {
            throw new HFBufMgrException(e,"Scan.java: unpinPage() failed");
        }

    } // end of unpinPage


}
