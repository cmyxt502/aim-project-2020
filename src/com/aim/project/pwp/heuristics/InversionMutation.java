package com.aim.project.pwp.heuristics;

import java.util.Random;

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
		int solutionLength = oSolution.getNumberOfLocations();
		//Calculate apply times
		double tempIOM = dIntensityOfMutation * 5;
		int times = (int)tempIOM + 1;
		if (times > 6) {
			times = 6;
		}
		for (int i = 0; i < times; i++) {
			//Generate two distinct index
			int startIndex = oRandom.nextInt(solutionLength);
			int endIndex = startIndex;
			int temp = 0;
			do {
				endIndex = oRandom.nextInt(solutionLength);
			} while(startIndex == endIndex);
			//Swap if end comes before start
			if (startIndex < endIndex) {
				startIndex = startIndex + endIndex;
				endIndex = startIndex - endIndex;
				startIndex = startIndex - endIndex;
			}
			while(startIndex < endIndex) {
				//Swap values
				temp = oSolution.getSolutionRepresentation().getSolutionRepresentation()[startIndex];
				oSolution.getSolutionRepresentation().getSolutionRepresentation()[startIndex] = oSolution.getSolutionRepresentation().getSolutionRepresentation()[endIndex];
				oSolution.getSolutionRepresentation().getSolutionRepresentation()[endIndex] = temp;
				temp = 0;
				//Increment counter
				startIndex++;
				endIndex--;
			}
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
