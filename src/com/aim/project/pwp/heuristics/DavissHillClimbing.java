package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.PWPObjectiveFunction;
import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aimframework.helperfunctions.ArrayMethods;


/**
 * 
 * @author Warren G. Jackson
 * Performs adjacent swap, returning the first solution with strict improvement
 *
 */
public class DavissHillClimbing extends HeuristicOperators implements HeuristicInterface {
	
	private final Random oRandom;
	
	public DavissHillClimbing(Random oRandom) {
	
		super();
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
		int solutionLength = oSolution.getNumberOfLocations() - 2;
		double currentOFV = oSolution.getObjectiveFunctionValue();
		int times = getTimes(dDepthOfSearch);
		int[] solutionArray = new int[solutionLength];
		for (int i = 0; i < times; i++) {
			//Copy solution into array
			for (int j = 0; j < solutionLength; j++) {
				solutionArray[j] = oSolution.getSolutionRepresentation().getSolutionRepresentation()[j];
			}
			//Shuffle array
			solutionArray = ArrayMethods.shuffle(solutionArray, oRandom);
			//Traverse thru all delivery locations
			for (int k = 0; k < times; k++) {
				int startNode = solutionArray[i];
				int nextNode = (startNode + 1) % solutionLength;
				//Calculate OFV
				double newOFV = super.deltaDH(oSolution, currentOFV, startNode, nextNode);
				//Check if improved
				if (newOFV < currentOFV) {
					currentOFV = newOFV;
					oSolution.setObjectiveFunctionValue(currentOFV);
					super.swap(oSolution, startNode, nextNode);
				}
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
