package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.PWPObjectiveFunction;
import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


public class AdjacentSwap extends HeuristicOperators implements HeuristicInterface {

	private final Random oRandom;
	
	public AdjacentSwap(Random oRandom) {

		super();
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double depthOfSearch, double intensityOfMutation) {
		//Get length of solution (excluding depot & home)
		int solutionLength = oSolution.getNumberOfLocations() - 2;
		//Get OFV value before heuristics
		double currentOFV = oSolution.getObjectiveFunctionValue();
		//Calculate apply times
		int times = getTimesAS(intensityOfMutation);
		//For each execution
		for (int i = 0; i < times; i++) {
			//Random index
			int applyLocation = oRandom.nextInt(solutionLength);
			int nextLocation = (applyLocation + 1) % solutionLength;
			//Calculate OFV value
			currentOFV = deltaAS(oSolution, currentOFV, applyLocation, nextLocation);
			//Swap
			super.swap(oSolution, applyLocation, nextLocation);
			//Update OFV value
			oSolution.setObjectiveFunctionValue(currentOFV);
		}
		//Return solution
		return oSolution.getObjectiveFunctionValue();
	}

	@Override
	public boolean isCrossover() {
		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {
		return true;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return false;
	}
	
	private double deltaOFV() {
		return -1;
	}

}

