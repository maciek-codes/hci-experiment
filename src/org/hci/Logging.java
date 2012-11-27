package org.hci;

import java.util.Date;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

//class for saving data from the program in the correct format
public class Logging {
	
	// Singleton instance
	private static Logging logger;
	
	// No of fiels in CSV
	private int noOfFields = 12;
	
	
	// Name of file
	String fileName = "results.csv";
	
	// File handle
	FileWriter fstream;
	BufferedWriter out;
	
	int userId;
	
	// Private constructor prevents others from initialising
	private Logging() {
		// Create file
		
		// Write header
		String[] header = new String[noOfFields];
		
		//key,user_id,test_id,datetime_start,datetime_finish,gender,age,resolution,no_objects,object_velocity,time_visual,time_pointing
		header[0] = "key";
		header[1] = "user_id";
		header[2] = "test_id";
		header[3] = "datetime_start";
		header[4] = "datetime_finish";
		header[5] = "gender";
		header[6] = "age";
		header[7] = "resolution";
		header[8] = "no_objects";
		header[9] = "object_velocity";
		header[10] = "time_visual";
		header[11] = "time_pointing";
		
		// Check user id
		userId = checkUserId();
	
		try {
			 boolean exists = false;
			 
			 File file = new File(fileName);
			 exists = file.exists();
			 
			 fstream = new FileWriter(fileName, true);
			 
			 out = new BufferedWriter(fstream);
			 
			 if(!exists)
				 WriteToCsv(header);
			  
		} catch (IOException e)
		{
			  System.err.println("Error: " + e.getMessage());
		}
		
	}
	
	private void WriteToCsv(String[] array) throws IOException {
		String line = new String();
		
		for(int i = 0; i < array.length; i++) {
			 line += array[i];
			 
			 if(i < array.length - 1)
			 line += ",";
		 }
		
		line += "\n";

		out.append(line);
	}
	
	// Get instance of logger
	public static Logging GetLogger() {
		if(logger == null) {
			logger = new Logging();
		}
		return logger;
	}
	
	//key,user_id,test_id,datetime_start,datetime_finish,gender,age,resolution,no_objects,object_velocity,time_visual,time_pointing
	public void Log(int testId, Date start, Date finish, char gender, int age, 
			Resolution res, int noObjects, int velocity, double timeVisual, double timePointing ) {
		
		
		// Get date now and use it as a key
		Date dateNow = new Date();
		long key = dateNow.getTime();

		
		
		String[] values = new String[noOfFields];
		
		values[0] = String.valueOf(key);
		values[1] = String.valueOf(userId);
		values[2] = String.valueOf(testId);
		values[3] = start.toString();
		values[4] = finish.toString();
		values[5] = String.valueOf(gender);
		values[6] = String.valueOf(age);
		values[7] = res.toString();
		values[8] = String.valueOf(noObjects);
		values[9] = String.valueOf(velocity);
		values[10] = String.valueOf(timeVisual);
		values[11] = String.valueOf(timePointing);
		
		try {
			WriteToCsv(values);
		} catch (IOException e)
		{
			  System.err.println("Error: " + e.getMessage());
		}
	}
	
	private int checkUserId() {
		int userId = 0;
		try {
			File file = new File(fileName);
			if(file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
				
				String line = null, tmp;
				if(reader != null) {
					
					while((tmp = reader.readLine()) != null) {
						line = tmp;
					}
					
				}
				
				if(line == null) {
					userId = 0;
				}
				
				String[] fields = line.split(",");
				
				try {
					int currentUser = Integer.parseInt(fields[1]);
					userId = currentUser + 1;
				} catch(Exception ex) {
					userId = 0;
				}
				
				fis.close();
				
			}
		} catch(IOException e) {
			System.out.println("Error in logger: " + e.getMessage());
			return 0;
		}
		
		return userId;
	}

	public void Close() {
		try {
		
			//Close the output stream
			out.close();
			
			// Close file
			fstream.close();
			  
		} catch (IOException e)
		{
			  System.err.println("Error: " + e.getMessage());
		}
	}

}
