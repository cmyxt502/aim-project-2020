package com.aim.project.pwp.instance.reader;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.instance.PWPInstance;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPInstanceReaderInterface;


public class PWPInstanceReader implements PWPInstanceReaderInterface {

	@Override
	public PWPInstanceInterface readPWPInstance(Path path, Random random) {
		BufferedReader bfr;
		int numPostalAddresses = 0;
		ArrayList<Location> postalAddresses = new ArrayList<>();
		Location postalOffice = new Location(0.0, 0.0);
		Location workerAddress = new Location(0.0, 0.0);
		int line = 0;
		
		try {
			//Preparation
			bfr = Files.newBufferedReader(path);
            String thisLine;
            //Loop through all lines
            while (!(thisLine = bfr.readLine()).equals("EOF")) {
            	line++;
            	switch(line) {
            		case 1:
            			break;
            		case 2:
            			break;
            		case 3:
            			break;
            		//Detect "Postal Office" coordinate
            		case 4:
            			postalOffice = getLocation(thisLine);
            			break;
            		case 5:
            			break;
            		//Detect "Worker Address" coordinate
            		case 6:
            			workerAddress = getLocation(thisLine);
            			break;
            		case 7:
            			break;
            		//Default case as postal addresses
            		default:
            			postalAddresses.add(getLocation(thisLine));
            			break;
            	}
            }
            bfr.close();
            Location[] postalAddressesArray = postalAddresses.toArray(new Location[0]);
    		return new PWPInstance(line - 5, postalAddressesArray, postalOffice, workerAddress, random);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}
	
	private Location getLocation(String line) {
		return new Location(Double.valueOf(line.split("\\s+")[0]), Double.valueOf(line.split("\\s+")[1]));
	}
	
	private ArrayList<Location> getPostalAddresses(BufferedReader bfr) {
		String thisLine;
		Location postalAddress = new Location(0.0, 0.0);
		ArrayList<Location> postalAddresses = new ArrayList<>();
		try {
			while (!(thisLine = bfr.readLine()).equals("EOF")) {
				postalAddress = getLocation(thisLine);
				postalAddresses.add(postalAddress);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return postalAddresses;
	}
	
}
