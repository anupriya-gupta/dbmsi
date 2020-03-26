package iterator;

import BigT.*;
import global.*;
import java.io.*;

public class PredEval
{
  /**
   *predicate evaluate, according to the condition ConExpr, judge if 
   *the two tuple can join. if so, return true, otherwise false
   *@return true or false
   *@param p[] single select condition array
   *@param t1 compared tuple1
   *@param t2 compared tuple2
   *@param in1[] the attribute type corespond to the t1
   *@param in2[] the attribute type corespond to the t2
   *@exception IOException  some I/O error
   *@exception UnknowAttrType don't know the attribute type
   *@exception InvalidTupleSizeException size of tuple not valid
   *@exception InvalidTypeException type of tuple not valid
   *@exception FieldNumberOutOfBoundException field number exceeds limit
   *@exception PredEvalException exception from this method
   */
  public static boolean Eval(CondExpr p[], Map m1, Map m2)
    throws IOException,
	   InvalidTupleSizeException,
	   InvalidTypeException,
	   FieldNumberOutOfBoundException,
	   PredEvalException
    {
		CondExpr temp_ptr;
		int       i = 0;
		Map    map1 = null, map2 = null;
		int      fld1, fld2;
		Map    value =   new Map();
		short[]     str_size = new short[3];
		AttrType[]  val_type = new AttrType[1];
		int [] v_type = new int[1];
		map1 = m1;
		AttrType  comparison_type = new AttrType(AttrType.attrInteger);
		int       comp_res;
		boolean   op_res = false, row_res = false, col_res = true;
      
      	if (p == null) {
			return true;
		}
      
      	while (p[i] != null) {
	  		temp_ptr = p[i];
			while (temp_ptr != null) {
				v_type[0] = temp_ptr.field; //new AttrType(temp_ptr.type1.attrType);
				fld1        = 1;
				switch (temp_ptr.field) {
					case 1:
						str_size[0] = (short)(temp_ptr.operand2.string.length()+2 );
						str_size[1] = (short)(temp_ptr.operand2.string.length()+2 );
						str_size[2] = (short)(temp_ptr.operand2.string.length()+2 );
						value.setHdr(str_size);
						value.setRowLabel(temp_ptr.operand2.string);
						map2 = value;
						// str_size[0] = (short)(temp_ptr.operand2.string.length()+1 );
						// value.setHdr(str_size);
						// value.setRowLabel(temp_ptr.operand2.string);
						// map2 = value;
						comparison_type.attrType = AttrType.attrString;
						break;
					case 2:
						str_size[0] = (short)(temp_ptr.operand2.string.length()+2 );
						str_size[1] = (short)(temp_ptr.operand2.string.length()+2 );
						str_size[2] = (short)(temp_ptr.operand2.string.length()+2 );
						value.setHdr(str_size);
						value.setColumnLabel(temp_ptr.operand2.string);
						// map1 = value;
						// str_size[1] = (short)(temp_ptr.operand2.string.length()+1 );
						// value.setHdr(str_size);
						// value.setColumnLabel(temp_ptr.operand2.string);
						map2 = value;
						comparison_type.attrType =AttrType.attrString; 
						break;
					case 3:
						value.setHdr(new short[]{32,32,32});
						value.setTimeStamp(temp_ptr.operand2.integer);
						// map1 = value;
						// value.setHdr(null);
						// value.setTimeStamp(temp_ptr.operand2.integer);
						map2 = value;
						comparison_type.attrType = AttrType.attrInteger;
						break;
					case 4:
						str_size[0] = (short)(temp_ptr.operand2.string.length()+2 );
						str_size[1] = (short)(temp_ptr.operand2.string.length()+2 );
						str_size[2] = (short)(temp_ptr.operand2.string.length()+2 );
						value.setHdr(str_size);
						value.setValue(temp_ptr.operand2.string);
						// map1 = value;
						// str_size[0] = (short)(temp_ptr.operand2.string.length()+1 );
						// value.setHdr(str_size);
						// value.setRowLabel(temp_ptr.operand2.string);
						map2 = value;
						comparison_type.attrType =AttrType.attrString; 
						break;
					default:
						break;
				}
	      
				// Setup second argument for comparison.
				// val_type[0] = new AttrType(temp_ptr.field);
				// fld2        = 1;
				// switch (temp_ptr.field) {
				// 	case AttrType.attrInteger:
				// 		value.setHdr((short)1, val_type, null);
				// 		value.setIntFld(1, temp_ptr.operand2.integer);
				// 		tuple2 = value;
				// 		break;
				// 	case AttrType.attrReal:
				// 		value.setHdr((short)1, val_type, null);
				// 		value.setFloFld(1, temp_ptr.operand2.real);
				// 		tuple2 = value;
				// 		break;
				// 	case AttrType.attrString:
				// 		str_size[0] = (short)(temp_ptr.operand2.string.length()+1 );
				// 		value.setHdr((short)1, val_type, str_size);
				// 		value.setStrFld(1, temp_ptr.operand2.string);
				// 		tuple2 = value;
				// 		break;
				// 	case AttrType.attrSymbol:
				// 		fld2 = temp_ptr.operand2.symbol.offset;
				// 		if (temp_ptr.operand2.symbol.relation.key == RelSpec.outer)
				// 			tuple2 = t1;
				// 		else
				// 			tuple2 = t2;
				// 		break;
				// 	default:
				// 		break;
				// }
	      
	      
				// Got the arguments, now perform a comparison.
				try {
					comp_res = MapUtils.CompareMapWithMap(map1, map2, temp_ptr.field);
	      		}catch (MapUtilsException e){
					throw new PredEvalException (e,"TupleUtilsException is caught by PredEval.java");
	      		}
	      		op_res = false;
	      
	     		switch (temp_ptr.op.attrOperator) {
					case AttrOperator.aopEQ:
						if (comp_res == 0) op_res = true;
						break;
					case AttrOperator.aopLT:
						if (comp_res <  0) op_res = true;
						break;
					case AttrOperator.aopGT:
						if (comp_res >  0) op_res = true;
						break;
					case AttrOperator.aopNE:
						if (comp_res != 0) op_res = true;
						break;
					case AttrOperator.aopLE:
						if (comp_res <= 0) op_res = true;
						break;
					case AttrOperator.aopGE:
						if (comp_res >= 0) op_res = true;
						break;
					case AttrOperator.aopNOT:
						if (comp_res != 0) op_res = true;
						break;
					default:
						break;
				}
	      
				row_res = row_res || op_res;
				if (row_res == true)
					break;                        // OR predicates satisfied.
				temp_ptr = temp_ptr.next;
			}
	  		i++;
	  
			col_res = col_res && row_res;
			if (col_res == false) {
				return false;
			}
			row_res = false;                        // Starting next row.
		}
      
      
      	return true;
      
    }
}

