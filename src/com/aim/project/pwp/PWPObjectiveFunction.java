package com.aim.project.pwp;

import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;

public class PWPObjectiveFunction implements ObjectiveFunctionInterface {
	
	private final PWPInstanceInterface oInstance;
	
	public PWPObjectiveFunction(PWPInstanceInterface oInstance) {
		
		this.oInstance = oInstance;
	}

	@Override
	public double getObjectiveFunctionValue(SolutionRepresentationInterface oSolution) {
		//Setup variables
		int solutionLength = oSolution.getNumberOfLocations();
		int solutionRepresentation[] = oSolution.getSolutionRepresentation();
		double returnValue = 0;
		//Get distance between depot & first delivery address
		returnValue = returnValue + getCostBetweenDepotAnd(solutionRepresentation[1]);
		//Get distance between each pair of delivery addresses
		for(int i = 1; i < solutionLength - 3; i++) {
			returnValue = returnValue + getCost(solutionRepresentation[i], solutionRepresentation[i + 1]);
		}
		//Get distance between last delivery address & home
		returnValue = returnValue + getCostBetweenHomeAnd(solutionRepresentation[solutionLength - 2]);
		//Return objective function value
		return returnValue;
	}
	
	@Override
	public double getCost(int iLocationA, int iLocationB) {
		//Create location for delivery addresses
		Location locationA = null;
		Location locationB = null;
		//Set location for delivery addresses
		locationA = oInstance.getLocationForDelivery(iLocationA);
		locationB = oInstance.getLocationForDelivery(iLocationB);
		//Calculate distance
		double diffX = Math.pow(locationA.getX() - locationB.getX(), 2);
		double diffY = Math.pow(locationA.getY() - locationB.getY(), 2);
		return Math.sqrt(diffX + diffY);
	}

	@Override
	public double getCostBetweenDepotAnd(int iLocation) {
		//Create location for delivery address and depot
		Location locationA = null;
		Location locationDepot = null;
		//Set location for delivery address and depot
		locationA = oInstance.getLocationForDelivery(iLocation);
		locationDepot = oInstance.getPostalDepot();
		//Calculate distance
		double diffX = Math.pow(locationA.getX() - locationDepot.getX(), 2);
		double diffY = Math.pow(locationA.getY() - locationDepot.getY(), 2);
		return Math.sqrt(diffX + diffY);
	}

	@Override
	public double getCostBetweenHomeAnd(int iLocation) {
		//Create location for delivery address and home
		Location locationA = null;
		Location locationHome = null;
		//Set location for delivery address and home
		locationA = oInstance.getLocationForDelivery(iLocation);
		locationHome = oInstance.getHomeAddress();
		//Calculate distance
		double diffX = Math.pow(locationA.getX() - locationHome.getX(), 2);
		double diffY = Math.pow(locationA.getY() - locationHome.getY(), 2);
		return Math.sqrt(diffX + diffY);
	}
}
