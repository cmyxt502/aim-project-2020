package com.aim.project.pwp.heuristics;


import java.util.Random;

import com.aim.project.pwp.PWPObjectiveFunction;
import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


/**
 * 
 * @author Warren G. Jackson
 * Performs adjacent swap, returning the first solution with strict improvement
 *
 */
public class NextDescent extends HeuristicOperators implements HeuristicInterface {
	
	private final Random oRandom;
	
	public NextDescent(Random oRandom) {
	
		super();
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		int solutionLength = oSolution.getNumberOfLocations() - 2;
		double currentOFV = oSolution.getObjectiveFunctionValue();
		//Randomly select start position
		int startLocation = oRandom.nextInt(solutionLength);
		
		int times = super.getTimes(dDepthOfSearch);
		int trace = 0;
		
		for (int i = 0; i < times; i++) {
			if (trace < solutionLength) {
				int startNode = (startLocation + i) % solutionLength;
				int nextNode = (startLocation + i + 1) % solutionLength;
				//Calculate new OFV
				double newOFV = deltaND(oSolution, currentOFV, startNode, nextNode);
				//When better solution found
				if (newOFV < currentOFV) {
					currentOFV = newOFV;
					oSolution.setObjectiveFunctionValue(currentOFV);
					super.swap(oSolution, startNode, nextNode);
				}
				trace ++;
			}
		}
		return currentOFV;
	}

	@Override
	public boolean isCrossover() {
		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {
		return false;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return true;
	}
}
