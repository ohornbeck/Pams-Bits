package edu.ycp.cs320.booksdb.persist;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import edu.ycp.cs320.booksdb.model.Bit;


public interface IDatabase {
	
	
	public Integer insertBit(final String type, final String cheekpiece, final String size, final String purpose);
	public Integer insertCommentIntoBit(final int bitID, final String comment);
	
	public ArrayList<Bit> findAllBits();
	public ArrayList<Bit> BitsByType();
	public ArrayList<Bit> BitsByCheekpiece();
	public ArrayList<Bit> BitsBySize();
	public ArrayList<Bit> BitsByPurpose();
	
	public ArrayList<Bit> BitsByTypeCheekpiece();
	public ArrayList<Bit> BitsByTypeSize();
	public ArrayList<Bit> BitsByTypePurpose();
	public ArrayList<Bit> BitsByCheekpieceSize();
	public ArrayList<Bit> BitsByCheekpiecePurpose();
	public ArrayList<Bit> BitsBySizePurpose();
	
	public ArrayList<Bit> BitsByTypeCheekpieceSize();
	public ArrayList<Bit> BitsByCheekpieceSizePurpose();
	public ArrayList<Bit> BitsBySizePurposeType();
	public ArrayList<Bit> BitsByPurposeTypeCheekpiece();
	
	public ArrayList<Bit> BitsByTypeCheekpieceSizePurpose();
	
	
}
