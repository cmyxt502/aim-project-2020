package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.PWPObjectiveFunction;
import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


public class Reinsertion extends HeuristicOperators implements HeuristicInterface {

	private final Random oRandom;
	
	public Reinsertion(Random oRandom) {

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
		for (int i = 0; i< times; i++) {
			//Generate two distinct index
			int selectedIndex = oRandom.nextInt(solutionLength);
			int reinsertIndex = oRandom.nextInt(solutionLength);
			while (selectedIndex == reinsertIndex) {
				reinsertIndex = oRandom.nextInt(solutionLength);
			}
			//Calculate OFV value
			currentOFV = deltaRI(oSolution, currentOFV, selectedIndex, reinsertIndex);
			//Reinsert node
			reinsert(oSolution, selectedIndex, reinsertIndex);
			//Update OFV value
			oSolution.setObjectiveFunctionValue(currentOFV);
			
		}
		return currentOFV;
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
