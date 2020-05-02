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
		//Get length of solution
		int solutionLength = oSolution.getNumberOfLocations();
		//Calculate apply times
		double tempIOM = intensityOfMutation * 5;
		int times = (int)Math.pow(2, tempIOM);
		if (times > 32) {
			times = 32;
		}
		//For each execution
		for (int i = 0; i < times; i++) {
			//Random index
			int applyLocation = oRandom.nextInt(solutionLength);
			int nextLocation = applyLocation % solutionLength;
			//Swap
			int tempIndex = oSolution.getSolutionRepresentation().getSolutionRepresentation()[applyLocation];
			oSolution.getSolutionRepresentation().getSolutionRepresentation()[applyLocation] = oSolution.getSolutionRepresentation().getSolutionRepresentation()[nextLocation];
			oSolution.getSolutionRepresentation().getSolutionRepresentation()[nextLocation] = tempIndex;
		}
		//Return solution
		double objectiveFunctionValue = PWPObjectiveFunction.getObjectiveFunctionValue(oSolution.getSolutionRepresentation());
		oSolution.setObjectiveFunctionValue(objectiveFunctionValue);
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

