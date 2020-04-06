package com.aim.project.pwp.instance.reader;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.instance.PWPInstance;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPInstanceReaderInterface;


public class PWPInstanceReader implements PWPInstanceReaderInterface {

	@Override
	public PWPInstanceInterface readPWPInstance(Path path, Random random) {
		BufferedReader bfr;
		PWPInstance returnInstance = null;
		try {
			//Preparation
			//Path path = Paths.get("../aim-project-2020-empty/instances/pwp/libraries-15.pwp");
			bfr = Files.newBufferedReader(path);
			int numPostalAddresses = getNumberOfPostalAddresses(path);
			Location[] postalAddresses = new Location[numPostalAddresses];
			Location postalOffice = null;
			Location workerAddress = null;
			
			//Read first line (NAME)
            String thisLine = bfr.readLine();
            //Loop through all lines
            while (thisLine != null) {
            	switch(thisLine) {
            		//Detect "Postal Office" coordinate
            		case "POSTAL_OFFICE":
            			thisLine = bfr.readLine();
            			//Create location
            			postalOffice = new Location(Double.valueOf(thisLine.split("\t")[0]), Double.valueOf(thisLine.split("\t")[1]));
            			break;
            			
            		//Detect "Worker Address" coordinate
            		case "WORKER_ADDRESS":
            			thisLine = bfr.readLine();
            			//Create location
            			workerAddress = new Location(Double.valueOf(thisLine.split("\t")[0]), Double.valueOf(thisLine.split("\t")[1]));
            			break;
            			
            		//Detect all "Postal Addresses" coordinates
            		case "POSTAL_ADDRESSES":
            			//postalAddresses index
            			int i = 0;
            			
            			//Read first line (POSTAL_ADDRESSES)
            			thisLine = bfr.readLine();
            			//Loop through all lines
            			while (!thisLine.contains("EOF")) {
            				//Create location
            				Location postalAddress = new Location(Double.valueOf(thisLine.split("\t")[0]), Double.valueOf(thisLine.split("\t")[1]));
                			postalAddresses[i] = postalAddress;
            				//Read next line
            				thisLine = bfr.readLine();
            				
            				//Increment index
            				i++;
            			}
            			break;
            			
            		//Default case, skip
            		default:
            			break;
            	}
            	//Read next line
            	thisLine = bfr.readLine();
            	
            	//Close reader
            	bfr.close();
            	
            	//Create PWPInstance
            	returnInstance = new PWPInstance(numPostalAddresses + 2, postalAddresses, postalOffice, workerAddress, random);
             }
		} catch (IOException e) {

			e.printStackTrace();
			//return null;
		}
		//Return instance
		return returnInstance;
	}
	
	private static int getNumberOfPostalAddresses(Path path) {
		int i = 0;
		BufferedReader bfrAlt;
		try {
			bfrAlt = Files.newBufferedReader(path);
            while ((bfrAlt.readLine()) != null) {
            	i++;
            }
            i = i - 8;
            bfrAlt.close();
		} catch (IOException e) {

			e.printStackTrace();
			return -1;
		}
		return i;
	}
}
