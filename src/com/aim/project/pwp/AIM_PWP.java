package com.aim.project.pwp;


import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.aim.project.pwp.heuristics.AdjacentSwap;
import com.aim.project.pwp.heuristics.CX;
import com.aim.project.pwp.heuristics.DavissHillClimbing;
import com.aim.project.pwp.heuristics.InversionMutation;
import com.aim.project.pwp.heuristics.NextDescent;
import com.aim.project.pwp.heuristics.OX;
import com.aim.project.pwp.heuristics.Reinsertion;
import com.aim.project.pwp.instance.InitialisationMode;
import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.instance.reader.PWPInstanceReader;
import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.Visualisable;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;

import AbstractClasses.ProblemDomain;

public class AIM_PWP extends ProblemDomain implements Visualisable {

	private String[] instanceFiles = {
		"square", "libraries-15", "carparks-40", "tramstops-85", "trafficsignals-446", "streetlights-35714"
	};
	
	private PWPSolutionInterface[] aoMemoryOfSolutions;
	
	public PWPSolutionInterface oBestSolution;
	
	public PWPInstanceInterface oInstance;
	
	private HeuristicInterface[] aoHeuristics;
	
	private ObjectiveFunctionInterface oObjectiveFunction;
	
	private final long seed;
		
	public AIM_PWP(long seed) {
		
		super(seed);

		// TODO - set default memory size and create the array of low-level heuristics
		
	}
	
	public PWPSolutionInterface getSolution(int index) {
		
		// TODO 
		return aoMemoryOfSolutions[index];
	}
	
	public PWPSolutionInterface getBestSolution() {
		
		// TODO 
		return oBestSolution;
	}

	@Override
	public double applyHeuristic(int hIndex, int currentIndex, int candidateIndex) {
		
		// TODO - apply heuristic and return the objective value of the candidate solution
		//			remembering to keep track/update the best solution
	}

	@Override
	public double applyHeuristic(int hIndex, int parent1Index, int parent2Index, int candidateIndex) {
		
		// TODO - apply heuristic and return the objective value of the candidate solution
		//			remembering to keep track/update the best solution
	}

	@Override
	public String bestSolutionToString() {
		
		// TODO return the location IDs of the best solution including DEPOT and HOME locations
		//		e.g. "DEPOT -> 0 -> 2 -> 1 -> HOME"
		//Setup variables
		int[] bestSolutionArray;
		String bestSolutionString = "DEPOT"; //Set as "DEPOT" by default
		//Retrieve solution representation
		bestSolutionArray = oBestSolution.getSolutionRepresentation().getSolutionRepresentation();
		//Loop through each solution to concatenate them
		for (int i = 1; i < bestSolutionArray.length; i++) {
			//Concatenate " -> "
			bestSolutionString = bestSolutionString + " -> ";
			//Concatenate location id
			bestSolutionString = bestSolutionString + String.valueOf(bestSolutionArray[i]);
		}
		//Concatenate "HOME"
		bestSolutionString = bestSolutionString + "HOME";
		//Return best solution in string
		return bestSolutionString;
	}

	@Override
	public boolean compareSolutions(int iIndexA, int iIndexB) {

		// TODO return true if the objective values of the two solutions are the same, else false
		if(aoMemoryOfSolutions[iIndexA].getObjectiveFunctionValue() == aoMemoryOfSolutions[iIndexB].getObjectiveFunctionValue()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void copySolution(int iIndexA, int iIndexB) {

		// TODO - BEWARE this should copy the solution, not the reference to it!
		//			That is, that if we apply a heuristic to the solution in index 'b',
		//			then it does not modify the solution in index 'a' or vice-versa.
		
		aoMemoryOfSolutions[iIndexB] = aoMemoryOfSolutions[iIndexA].clone();
	}

	@Override
	public double getBestSolutionValue() {

		// TODO
		return oBestSolution.getObjectiveFunctionValue();
	}
	
	@Override
	public double getFunctionValue(int index) {
		
		// TODO
		return aoMemoryOfSolutions[index].getObjectiveFunctionValue();
	}

	@Override
	public int[] getHeuristicsOfType(HeuristicType type) {
		
		// TODO return an array of heuristic IDs based on the heuristic's type.
		
	}

	@Override
	public int[] getHeuristicsThatUseDepthOfSearch() {
		
		// TODO return the array of heuristic IDs that use depth of search.
		//Setup variables
		int totalHeuristics = aoHeuristics.length;
		int totalDoSHeuristics = totalHeuristics; //Size of array that holds DoS mutation IDs
		int heuristicsTemp[] = new int[totalHeuristics];
		//Loop through all heuristics, set non-DoS ones' value in heuristicsTemp to -1
		for (int i = 0; i < totalHeuristics; i++) {
			heuristicsTemp[i] = i;
			if (!aoHeuristics[i].usesDepthOfSearch()) {
				heuristicsTemp[i] = -1;
				totalDoSHeuristics--;
			}
		}
		//Setup variables
		int heuristicsDoS[] = new int[totalDoSHeuristics];
		int k = 0;
		//Loop through heuristicsTemp and copy non -1 values
		for (int j = 0; j < totalHeuristics; j++) {
			if(heuristicsTemp[j] != -1) {
				heuristicsDoS[k] = heuristicsTemp[j];
				k++;
			}
		}
		return heuristicsDoS;
	}

	@Override
	public int[] getHeuristicsThatUseIntensityOfMutation() {
		
		// TODO return the array of heuristic IDs that use intensity of mutation.
		//Setup variables
		int totalHeuristics = aoHeuristics.length;
		int totalIoMHeuristics = totalHeuristics; //Size of array that holds IoM mutation IDs
		int heuristicsTemp[] = new int[totalHeuristics];
		//Loop through all heuristics, set non-DoS ones' value in heuristicsTemp to -1
		for (int i = 0; i < totalHeuristics; i++) {
			heuristicsTemp[i] = i;
			if (!aoHeuristics[i].usesIntensityOfMutation()) {
				heuristicsTemp[i] = -1;
				totalIoMHeuristics--;
			}
		}
		//Setup variables
		int heuristicsIoM[] = new int[totalIoMHeuristics];
		int k = 0;
		//Loop through heuristicsTemp and copy non -1 values
		for (int j = 0; j < totalHeuristics; j++) {
			if(heuristicsTemp[j] != -1) {
				heuristicsIoM[k] = heuristicsTemp[j];
				k++;
			}
		}
		return heuristicsIoM;
	}

	@Override
	public int getNumberOfHeuristics() {

		// TODO - has to be hard-coded due to the design of the HyFlex framework...
		return 7;
	}

	@Override
	public int getNumberOfInstances() {

		// TODO return the number of available instances
		return instanceFiles.length;
	}

	@Override
	public void initialiseSolution(int index) {
		
		// TODO - initialise a solution in index 'index' 
		// 		making sure that you also update the best solution!
		
	}

	// TODO implement the instance reader that this method uses
	//		to correctly read in the PWP instance, and set up the objective function.
	@Override
	public void loadInstance(int instanceId) {

		String SEP = FileSystems.getDefault().getSeparator();
		String instanceName = "instances" + SEP + "pwp" + SEP + instanceFiles[instanceId] + ".pwp";

		Path path = Paths.get(instanceName);
		Random random = new Random(seed);
		PWPInstanceReader oPwpReader = new PWPInstanceReader();
		oInstance = oPwpReader.readPWPInstance(path, random);

		oObjectiveFunction = oInstance.getPWPObjectiveFunction();
		
		for(HeuristicInterface h : aoHeuristics) {
			h.setObjectiveFunction(oObjectiveFunction);
		}
	}

	@Override
	public void setMemorySize(int size) {

		// TODO sets a new memory size
		// IF the memory size is INCREASED, then
		//		the existing solutions should be copied to the new memory at the same indices.
		// IF the memory size is DECREASED, then
		//		the first 'size' solutions are copied to the new memory.
	}

	@Override
	public String solutionToString(int index) {

		// TODO

	}

	@Override
	public String toString() {

		// TODO change 'AAA' to be your username
		return "scymc1's G52AIM PWP";
	}
	
	private void updateBestSolution(int index) {
		
		// TODO
		
	}
	
	@Override
	public PWPInstanceInterface getLoadedInstance() {

		return this.oInstance;
	}

	@Override
	public Location[] getRouteOrderedByLocations() {

		int[] city_ids = getBestSolution().getSolutionRepresentation().getSolutionRepresentation();
		Location[] route = Arrays.stream(city_ids).boxed().map(getLoadedInstance()::getLocationForDelivery).toArray(Location[]::new);
		return route;
	}
}
