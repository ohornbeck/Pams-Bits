package edu.ycp.cs320.booksdb.persist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.Date;
import java.text.SimpleDateFormat;

import edu.ycp.cs320.booksdb.model.Bit;

public class InitialData {


	// reads initial Bit data from CSV file and returns a List of Bits
	public static List<Bit> getBits() throws IOException {
		
		List<Bit> bitList = new ArrayList<Bit>();
		ReadCSV readBits = new ReadCSV("bits.csv");
		
		try {

			while (true) {
				List<String> tuple = readBits.next();
				
				if (tuple == null) {
					break;
				}
				
				Iterator<String> i = tuple.iterator();
				Bit bit = new Bit();
				
				// Integer.parseInt(i.next());
				bit.setType(i.next());
				bit.setCheekpiece(i.next());
				bit.setSize(i.next());
				bit.setPurpose(i.next());
				bit.setComment1(i.next());
				bit.setComment2(i.next());
				bit.setComment3(i.next());
				
				bitList.add(bit);
			}
			
			System.out.println("bitList loaded from CSV file");			
			return bitList;
			
		} finally {
			readBits.close();
		}
		
	}
	
}


