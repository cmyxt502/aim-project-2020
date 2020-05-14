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
	//Standard evaluation of objective function value
	//public double getObjectiveFunctionValue(SolutionRepresentationInterface oSolution) {
	public double getObjectiveFunctionValue(SolutionRepresentationInterface oSolution) {
			
		//Setup variables
		int solutionLength = oSolution.getNumberOfLocations();
		int solutionRepresentation[] = oSolution.getSolutionRepresentation();
		double returnValue = getCostBetweenDepotAnd(solutionRepresentation[0]);
		//Get distance between each pair of delivery addresses
		for(int i = 0; i < solutionLength - 1; i++) {
			returnValue = returnValue + getCost(solutionRepresentation[i], solutionRepresentation[i + 1]);
		}
		//Get distance between last delivery address & home
		returnValue = returnValue + getCostBetweenHomeAnd(solutionRepresentation[solutionLength - 1]);
		//Return objective function value
		return returnValue;
	}
	
	@Override
	public double getCost(int iLocationA, int iLocationB) {
		Location locationA = oInstance.getLocationForDelivery(iLocationA);
		Location locationB = oInstance.getLocationForDelivery(iLocationB);
		//Calculate distance
		return getDistance(locationA, locationB);
	}

	@Override
	public double getCostBetweenDepotAnd(int iLocation) {
		Location locationA = oInstance.getLocationForDelivery(iLocation);
		Location locationB = oInstance.getPostalDepot();
		//Calculate distance
		return getDistance(locationA, locationB);
	}

	@Override
	public double getCostBetweenHomeAnd(int iLocation) {
		Location locationA = oInstance.getLocationForDelivery(iLocation);
		Location locationB = oInstance.getHomeAddress();
		//Calculate distance
		return getDistance(locationA, locationB);
	}
	
	private double getDistance(Location locationA, Location locationB) {
		// TODO Auto-generated method stub
		double diffX = Math.pow(locationA.getX() - locationB.getX(), 2);
		double diffY = Math.pow(locationA.getY() - locationB.getY(), 2);
		return Math.sqrt(diffX + diffY);
	}
}
