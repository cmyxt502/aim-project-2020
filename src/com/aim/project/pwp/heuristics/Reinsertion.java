package com.aim.project.pwp.heuristics;

import java.util.Random;

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
		int solutionLength = oSolution.getNumberOfLocations();
		//Calculate apply times
		double tempIOM = dIntensityOfMutation * 5;
		int times = (int)tempIOM + 1;
		if (times > 6) {
			times = 6;
		}
		//Select removed node & reinsert location
		int selectedNode = oRandom.nextInt(solutionLength);
		int insertionNode= 0;
		do {
			insertionNode = oRandom.nextInt(solutionLength);
		} while (insertionNode == selectedNode - 1 || insertionNode == selectedNode);
		//Insert based on condition
		int selectedValue = oSolution.getSolutionRepresentation().getSolutionRepresentation()[selectedNode];
		if (selectedNode < insertionNode) {
			//Move each forward
			for (int i = selectedNode; i<= insertionNode; i++) {
				oSolution.getSolutionRepresentation().getSolutionRepresentation()[i] = oSolution.getSolutionRepresentation().getSolutionRepresentation()[i+1];
			}
			oSolution.getSolutionRepresentation().getSolutionRepresentation()[insertionNode] = selectedValue;	
		} else {
			//Move each backward
			for (int i = selectedNode; i > insertionNode; i--) {
				oSolution.getSolutionRepresentation().getSolutionRepresentation()[i] = oSolution.getSolutionRepresentation().getSolutionRepresentation()[i-1];
			}
			oSolution.getSolutionRepresentation().getSolutionRepresentation()[insertionNode] = selectedValue;
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
