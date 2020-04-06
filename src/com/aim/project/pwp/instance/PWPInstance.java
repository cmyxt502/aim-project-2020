package com.aim.project.pwp.instance;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.IntStream;

import com.aim.project.pwp.PWPObjectiveFunction;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.solution.SolutionRepresentation;
import com.aim.project.pwp.solution.PWPSolution;


public class PWPInstance implements PWPInstanceInterface {
	
	private final Location[] aoLocations;
	
	private final Location oPostalDepotLocation;
	
	private final Location oHomeAddressLocation;
	
	private final int iNumberOfLocations;
	
	private final Random oRandom;
	
	private ObjectiveFunctionInterface oObjectiveFunction = null;
	
	/**
	 * 
	 * @param numberOfLocations The TOTAL number of locations (including DEPOT and HOME).
	 * @param aoLocations The delivery locations.
	 * @param oPostalDepotLocation The DEPOT location.
	 * @param oHomeAddressLocation The HOME location.
	 * @param random The random number generator to use.
	 */
	public PWPInstance(int numberOfLocations, Location[] aoLocations, Location oPostalDepotLocation, Location oHomeAddressLocation, Random random) {
		
		this.iNumberOfLocations = numberOfLocations;
		this.oRandom = random;
		this.aoLocations = aoLocations;
		this.oPostalDepotLocation = oPostalDepotLocation;
		this.oHomeAddressLocation = oHomeAddressLocation;
	}

	@Override
	public PWPSolution createSolution(InitialisationMode mode) {
		
		// TODO construct a new 'PWPSolution' using RANDOM initialisation
		//Create variables
		PWPSolution returnSolution = null;
		SolutionRepresentation returnSolutionRepresentation = null;
		int[] solutionArray = new int[this.iNumberOfLocations];
		int temp = -1;
		double returnObjectiveFunctionValue = -1;
		//Make sure random is used
		if(mode == InitialisationMode.RANDOM) {
			//Initialise ascending solution array
			for (int i = 0; i < this.iNumberOfLocations; i++) {
				solutionArray[i] = i;
			}
			//Shuffle the solution array, avoiding first (postal depot) and last element (home address)
			for (int i = 1; i < this.iNumberOfLocations - 1; i++) {
				//Choose random index to swap
				//Included mechanism to avoid selecting first and last element
				int randomIndex = this.oRandom.nextInt(this.iNumberOfLocations - 2) + 1;
				//Perform swap
				temp = solutionArray[randomIndex];
				solutionArray[randomIndex] = solutionArray[i];
				solutionArray[i] = temp;
			}
		}
		//Filling return variables
		returnSolutionRepresentation = new SolutionRepresentation(solutionArray);
		returnSolution = new PWPSolution(returnSolutionRepresentation, returnObjectiveFunctionValue);
		return returnSolution;
	}
	
	@Override
	public ObjectiveFunctionInterface getPWPObjectiveFunction() {
		
		if(oObjectiveFunction == null) {
			this.oObjectiveFunction = new PWPObjectiveFunction(this);
		}

		return oObjectiveFunction;
	}

	@Override
	public int getNumberOfLocations() {

		return iNumberOfLocations;
	}

	@Override
	public Location getLocationForDelivery(int deliveryId) {

		return aoLocations[deliveryId];
	}

	@Override
	public Location getPostalDepot() {
		
		return this.oPostalDepotLocation;
	}

	@Override
	public Location getHomeAddress() {
		
		return this.oHomeAddressLocation;
	}
	
	@Override
	public ArrayList<Location> getSolutionAsListOfLocations(PWPSolutionInterface oSolution) {
		
		// TODO return an 'ArrayList' of ALL LOCATIONS in the solution.
		int[] solutionRepresentation = new int[this.iNumberOfLocations];
		int temp = -1;
		ArrayList<Location> returnLocationArray = new ArrayList<Location>();
		for (int i = 0; i < this.iNumberOfLocations; i++) {
			//Get solution representation array
			solutionRepresentation = oSolution.getSolutionRepresentation().getSolutionRepresentation();
			//Get each location by its order
			temp = solutionRepresentation[i];
			//Add respective element to return location array
			returnLocationArray.add(this.aoLocations[temp]);
		}
		return returnLocationArray;
	}

}
