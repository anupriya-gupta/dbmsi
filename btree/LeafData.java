package btree;
import global.*;

/**  IndexData: It extends the DataClass.
 *   It defines the data "rid" for leaf node in B++ tree.
 */
public class LeafData extends DataClass {
  private MID myRid;

  public String toString() {
     String s;
     s="[ "+ (new Integer(myRid.pageNo.pid)).toString() +" "
              + (new Integer(myRid.slotNo)).toString() + " ]";
     return s;
  }

  /** Class constructor
   *  @param    rid  the data rid
   */
  LeafData(MID rid) {myRid= new MID(rid.pageNo, rid.slotNo);};  

  /** get a copy of the rid
  *  @return the reference of the copy 
  */
  public MID getData() {return new MID(myRid.pageNo, myRid.slotNo);};

  /** set the rid
   */ 
  public void setData(MID rid) { myRid= new MID(rid.pageNo, rid.slotNo);};
}   
