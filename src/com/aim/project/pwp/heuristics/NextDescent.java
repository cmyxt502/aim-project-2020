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
		int solutionLength = oSolution.getNumberOfLocations();
		double initialOFV = oSolution.getObjectiveFunctionValue();
		double bestOFV = initialOFV;
		double currentOFV = bestOFV + 1;
		int startLocation = oRandom.nextInt(solutionLength);
		dDepthOfSearch = dDepthOfSearch * 5;
		int times = (int)dDepthOfSearch + 1;
		int temp = 0;
		boolean accepted = false;
		for (int i = 0; i < times; i++) {
			//Reset acceptance flag
			accepted = false;
			do {
				//Proceed to next node
				startLocation = (startLocation + 1) % solutionLength;
				//Swap node with next one
				temp = oSolution.getSolutionRepresentation().getSolutionRepresentation()[startLocation];
				oSolution.getSolutionRepresentation().getSolutionRepresentation()[startLocation] = oSolution.getSolutionRepresentation().getSolutionRepresentation()[startLocation + 1];
				oSolution.getSolutionRepresentation().getSolutionRepresentation()[startLocation + 1] = temp;
				//Get OFV value
				currentOFV = PWPObjectiveFunction.getObjectiveFunctionValue(oSolution.getSolutionRepresentation());
				//Rule if change is accepted
				if(currentOFV > bestOFV) {
					//Return to previous state
				} else {
					accepted = true;
					bestOFV = currentOFV;
				}
			} while (!accepted);
		}
		return bestOFV;
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
