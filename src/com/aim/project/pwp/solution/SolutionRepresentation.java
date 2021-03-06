package com.aim.project.pwp.solution;

import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;

/**
 * 
 * @author Warren G. Jackson
 * 
 *
 */
public class SolutionRepresentation implements SolutionRepresentationInterface {

	private int[] aiSolutionRepresentation;
	//Not including depot & home

	public SolutionRepresentation(int[] aiRepresentation) {

		this.aiSolutionRepresentation = aiRepresentation;
	}

	@Override
	public int[] getSolutionRepresentation() {

		return aiSolutionRepresentation;
	}

	@Override
	public void setSolutionRepresentation(int[] aiSolutionRepresentation) {

		this.aiSolutionRepresentation = aiSolutionRepresentation;
	}

	@Override
	public int getNumberOfLocations() {

		return this.aiSolutionRepresentation.length;
	}

	@Override
	public SolutionRepresentationInterface clone() {

		// TODO perform a DEEP clone of the solution representation!
		//Setup variables
		int solutionLength = this.getNumberOfLocations();
		int[] clonedSolution = new int[solutionLength];
		//Duplicate solution array
		for(int i = 0; i< solutionLength; i++) {
			clonedSolution[i] = this.getSolutionRepresentation()[i];
		}
		//New solutionrepresentation class
		SolutionRepresentation returnSolutionRepresentation = new SolutionRepresentation(clonedSolution);
		//Return solutionrepresentation class
		return returnSolutionRepresentation;
	}

}
