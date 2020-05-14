package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.PWPObjectiveFunction;
import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


public class InversionMutation extends HeuristicOperators implements HeuristicInterface {
	
	private final Random oRandom;
	
	public InversionMutation(Random oRandom) {
	
		super();
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		//Get length of solution
		int solutionLength = oSolution.getNumberOfLocations() - 2;
		//Calculate apply times
		int times = getTimes(dIntensityOfMutation);
		
		double currentOFV = oSolution.getObjectiveFunctionValue();
		
		for (int i = 0; i < times; i++) {
			//Generate two distinct index
			int startIndex = oRandom.nextInt(solutionLength);
			int endIndex = oRandom.nextInt(solutionLength);
			while (startIndex == endIndex) {
				endIndex = oRandom.nextInt(solutionLength);
			}
			int temp = 0;
			//Set start & end to respective location
			if(startIndex > endIndex) {
				temp = startIndex;
				startIndex = endIndex;
				endIndex = temp;
			}
			//Calculate OFV value
			currentOFV = deltaIM(oSolution, currentOFV, startIndex, endIndex);
			//Swap each node between start & end
			for (int j = 0; j < (endIndex + 1 - startIndex) / 2; i++) {
				swap(oSolution, startIndex + j, endIndex - j);
			}
			//Update OFV value
			oSolution.setObjectiveFunctionValue(currentOFV);
		}
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

}
