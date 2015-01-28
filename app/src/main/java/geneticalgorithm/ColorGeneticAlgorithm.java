package geneticalgorithm;

import java.util.ArrayList;
import java.util.List;

import basegeneticalgorithm.GeneticAlgorithm;
import basegeneticalgorithm.GeneticAlgorithmError;
import graph.Graph;

/**
 * Created by Haider on 10/12/2014.
 */

/*
Description:
    @param: genes
        array of bits between 0-3 which represent the color numbers of a graph structure
    @param: populationSize
        declare the population size of the chromosome pool
    @param: mutationPercent:
        determine the percent to mutate
    @param: percentToMate
        determine the percent to mate in the chromosome pool
    @param: matingPopulationPercent
        determine which percent of the chromosome pool gets to mate
    @param: cutLength
        determine the length of the cut in the chromosome when crossed over
 */

public class ColorGeneticAlgorithm extends GeneticAlgorithm<ColorChromosome> {

    /*
    int array which will return an array of int values containing the color numebrs for the graph
     */
    private int[] colorArray;

    /*
    Input graph
     */
    private Graph graph;

    /*
    ArrayList of ColorGenes to be implemented in the ColorGeneticAlgorithm
     */
    ArrayList<Integer[]> colorGenePool = new ArrayList<Integer[]>();

    /*
    ColorChromosome to be used in the Fitness Function
     */
    private ColorChromosome colorChromosome;

    /*
    Cost variable for each chromosome
     */
    private int cost;


    public ColorGeneticAlgorithm(final ArrayList<Integer[]> colorGenePool, final int populationSize,
                                 final double mutationPercent, final double percentToMate,
                                 final double matingPopulationPercent, final int cutLength, Graph graph)
            throws GeneticAlgorithmError {
        this.graph = graph;
        this.colorGenePool = colorGenePool;
        this.setPopulationSize(populationSize);
        this.setMutationPercent(mutationPercent);
        this.setPercentToMate(percentToMate);
        this.setMatingPopulation(matingPopulationPercent);
        this.setCutLength(cutLength);
        this.setPreventRepeat(true);

        /*
        Set number of Chromosomes equal to the population size, then for each Chromosome
        Grab the ColorGene array from the ArrayList which is passed in and pass it into
        the parameter for that ColorChromosome and set that Chromosome.
         */
        setChromosomes(new ColorChromosome[getPopulationSize()]);
        for (int i = 0; i < getChromosomes().length; i++) {
            final Integer[] colorGenes = colorGenePool.get(i);
            final ColorChromosome colorChromosome = new ColorChromosome(this, colorGenes, graph);
            setChromosome(i, colorChromosome);
        }
        sortChromosomes();
    }

}
