package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

public class HeuristicOperators {

	private ObjectiveFunctionInterface oObjectiveFunction;

	public HeuristicOperators() {

	}

	public void setObjectiveFunction(ObjectiveFunctionInterface f) {

		this.oObjectiveFunction = f;
	}

	/**
	 * TODO implement any common functionality here so that your
	 * 			heuristics can reuse them!
	 * E.g.  you may want to implement the swapping of two delivery locations here!
	 */
	
	public ObjectiveFunctionInterface getObjectiveFunction(){
		return this.oObjectiveFunction;
	}
	
	//Get times of execution for adjacent swap heuristic
	public int getTimesAS(double as) {
		int temp = (int) (as * 10);
		temp = temp / 2;
		int times = 1;
		for (int i = 0; i < temp; i++) {
			times = times * 2;
		}
		return times;
	}
	
	//Get times of execution for heuristics (excluding adjacent swap)
	public int getTimes(double dVal) {
		int times = (int) (dVal * 10);
		times = times / 2;
		times = times + 1;
		return times;
	}
	
	//Swap two nodes in a solution representation
	public void swap(PWPSolutionInterface oSolution, int index1, int index2) {
		int[] oRepresentation = oSolution.getSolutionRepresentation().getSolutionRepresentation();
		int temp = oRepresentation[index1];
		oRepresentation[index1] = oRepresentation[index2];
		oRepresentation[index2] = temp;
	}
	
	//Delta evaluation of objective function value
	public double delta(int[] solutionArray, double ofv, int index1, int index2) {
		int solutionLength = solutionArray.length;
		int index3 = 0;
		
		if (index1 == 0) {
			ofv = ofv - getObjectiveFunction().getCostBetweenDepotAnd(solutionArray[index1]);
			ofv = ofv + getObjectiveFunction().getCostBetweenDepotAnd(solutionArray[index2]);
		} else {
			if (index1 == solutionLength - 1) {
				ofv = ofv - getObjectiveFunction().getCostBetweenHomeAnd(solutionArray[index1]);
				ofv = ofv + getObjectiveFunction().getCostBetweenHomeAnd(solutionArray[index2]);
			}
			index3 = index1 - 1;
			ofv = ofv - getObjectiveFunction().getCost(solutionArray[index1], solutionArray[index3]);
			ofv = ofv + getObjectiveFunction().getCost(solutionArray[index2], solutionArray[index3]);
		}
		
		if (index2 == solutionLength - 1) {
			ofv = ofv - getObjectiveFunction().getCostBetweenHomeAnd(solutionArray[index2]);
			ofv = ofv + getObjectiveFunction().getCostBetweenHomeAnd(solutionArray[index1]);
		} else {
			if (index2 == 0) {
				ofv = ofv - getObjectiveFunction().getCostBetweenDepotAnd(solutionArray[index2]);
				ofv = ofv + getObjectiveFunction().getCostBetweenDepotAnd(solutionArray[index1]);
			}
			index3 = index2 + 1;
			ofv = ofv - getObjectiveFunction().getCost(solutionArray[index2], solutionArray[index3]);
			ofv = ofv + getObjectiveFunction().getCost(solutionArray[index1], solutionArray[index3]);
		}
		return ofv;
	}
	
	public double deltaAS(PWPSolutionInterface oSolution, double ofv, int index1, int index2) {
		int[] solutionArray = oSolution.getSolutionRepresentation().getSolutionRepresentation();
		return delta(solutionArray, ofv, index1, index2);
	}
	
	public double deltaIM(PWPSolutionInterface oSolution, double ofv, int index1, int index2) {
		int[] solutionArray = oSolution.getSolutionRepresentation().getSolutionRepresentation();
		int solutionLength = solutionArray.length;
	    if (index1 == 0) {
	      ofv -= getObjectiveFunction().getCostBetweenDepotAnd(solutionArray[index1]);
	      ofv += getObjectiveFunction().getCostBetweenDepotAnd(solutionArray[index2]);
	    } else {
	      ofv -= getObjectiveFunction().getCost(solutionArray[index1], solutionArray[index1 - 1]);
	      ofv += getObjectiveFunction().getCost(solutionArray[index2], solutionArray[index1 - 1]);
	    }
	    if (index2 == solutionLength - 1) {
	      ofv -= getObjectiveFunction().getCostBetweenHomeAnd(solutionArray[index2]);
	      ofv += getObjectiveFunction().getCostBetweenHomeAnd(solutionArray[index1]);
	    } else {
	      ofv -= getObjectiveFunction().getCost(solutionArray[index2], solutionArray[index2 + 1]);
	      ofv += getObjectiveFunction().getCost(solutionArray[index1], solutionArray[index2 + 1]);
	    }
	    return ofv;
	}
	
	public double deltaRI(PWPSolutionInterface oSolution, double ofv, int index1, int index2) {
		int[] solutionArray = oSolution.getSolutionRepresentation().getSolutionRepresentation();
		int solutionLength = solutionArray.length;
		
		if (index1 == 0) {
		      ofv -= getObjectiveFunction().getCostBetweenDepotAnd(solutionArray[index1]);
		      ofv -= getObjectiveFunction().getCost(solutionArray[index1], solutionArray[index1 + 1]);
		      ofv += getObjectiveFunction().getCostBetweenDepotAnd(solutionArray[index1 + 1]);
		    } else if (index1 == solutionLength - 1) {
		      ofv -= getObjectiveFunction().getCostBetweenHomeAnd(solutionArray[index1]);
		      ofv -= getObjectiveFunction().getCost(solutionArray[index1], solutionArray[index1 - 1]);
		      ofv += getObjectiveFunction().getCostBetweenHomeAnd(solutionArray[index1 - 1]);
		    } else {
		      ofv -= getObjectiveFunction().getCost(solutionArray[index1], solutionArray[index1 - 1]);
		      ofv -= getObjectiveFunction().getCost(solutionArray[index1], solutionArray[index1 + 1]);
		      ofv += getObjectiveFunction().getCost(solutionArray[index1 - 1], solutionArray[index1 + 1]);
		    }

		    // cur off connnection in destination, bound the src node with corresponding neighbors.
		    if (index2 == 0) {
		      ofv -= getObjectiveFunction().getCostBetweenDepotAnd(solutionArray[index2]);
		      ofv += getObjectiveFunction().getCostBetweenDepotAnd(solutionArray[index1]);
		      ofv += getObjectiveFunction().getCost(solutionArray[index1], solutionArray[index2]);
		    } else if (index2 == solutionLength - 1) {
		      ofv -= getObjectiveFunction().getCostBetweenHomeAnd(solutionArray[index2]);
		      ofv += getObjectiveFunction().getCostBetweenHomeAnd(solutionArray[index1]);
		      ofv += getObjectiveFunction().getCost(solutionArray[index1], solutionArray[index2]);
		    } else {
		      int shift = (index1 < index2) ? 1 : -1;
		      ofv -= getObjectiveFunction().getCost(solutionArray[index2], solutionArray[index2 + shift]);
		      ofv += getObjectiveFunction().getCost(solutionArray[index1], solutionArray[index2]);
		      ofv += getObjectiveFunction().getCost(solutionArray[index1], solutionArray[index2 + shift]);
		    }
		return ofv;
	}
	
	public double deltaDH(PWPSolutionInterface oSolution, double ofv, int index1, int index2) {
		int[] solutionArray = oSolution.getSolutionRepresentation().getSolutionRepresentation();
		return delta(solutionArray, ofv, index1, index2);
	}
	
	public double deltaND(PWPSolutionInterface oSolution, double ofv, int index1, int index2) {
		int[] solutionArray = oSolution.getSolutionRepresentation().getSolutionRepresentation();
		return delta(solutionArray, ofv, index1, index2);
	}
}
