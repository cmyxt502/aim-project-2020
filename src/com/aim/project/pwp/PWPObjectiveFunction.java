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
		
	}
	
	@Override
	public double getCost(int iLocationA, int iLocationB) {
		Location locationA = null;
		Location locationB = null;
		locationA = oInstance.getLocationForDelivery(iLocationA);
		locationB = oInstance.getLocationForDelivery(iLocationB);
		double diffX = Math.pow(locationA.getX() - locationB.getX(), 2);
		double diffY = Math.pow(locationA.getY() - locationB.getY(), 2);
		return Math.sqrt(diffX + diffY);
	}

	@Override
	public double getCostBetweenDepotAnd(int iLocation) {
		Location locationA = null;
		Location locationDepot = null;
		locationA = oInstance.getLocationForDelivery(iLocation);
		locationDepot = oInstance.getPostalDepot();
		double diffX = Math.pow(locationA.getX() - locationDepot.getX(), 2);
		double diffY = Math.pow(locationA.getY() - locationDepot.getY(), 2);
		return Math.sqrt(diffX + diffY);
	}

	@Override
	public double getCostBetweenHomeAnd(int iLocation) {
		Location locationA = null;
		Location locationHome = null;
		locationA = oInstance.getLocationForDelivery(iLocation);
		locationHome = oInstance.getHomeAddress();
		double diffX = Math.pow(locationA.getX() - locationHome.getX(), 2);
		double diffY = Math.pow(locationA.getY() - locationHome.getY(), 2);
		return Math.sqrt(diffX + diffY);
	}
}
