package edu.ycp.cs320.booksdb.persist;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import edu.ycp.cs320.booksdb.model.Bit;
import edu.ycp.cs320.booksdb.model.Client;
import edu.ycp.cs320.booksdb.model.Horse;


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
	
	
	// no horses in here because they will be automatically inserted 
	// when the horses are entered into their table
	public Integer insertClient(final String firstName, final String lastName, final String farmName, 
			final String address, final String comment);
	public ArrayList<Client> findAllClients();
	public ArrayList<Client> ClientByFirstAndLast();
	
	// here it will be given the client ID, create the horse entry in the table, then retrieve the new horse ID
	// and update the client entry with that new horse ID that it retrieved in a few sql methods
	public Integer insertHorse(final int clientID, final String barnName, final String showName, final String breed, 
			final String height, final String sport);
	public ArrayList<Horse> findAllHorses();
	public ArrayList<Horse> HorsesByClient();
	
	
}
