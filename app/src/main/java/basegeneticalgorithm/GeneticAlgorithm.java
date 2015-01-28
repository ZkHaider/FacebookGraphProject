package basegeneticalgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * * Introduction to Neural Networks with Java, 2nd Edition
 * Copyright 2008 by Heaton Research, Inc.
 * http://www.heatonresearch.com/books/java-neural-2/
 *
 * ISBN13: 978-1-60439-008-7
 * ISBN:   1-60439-008-5
 *
 * This class is released under the:
 * GNU Lesser General Public License (LGPL)
 * http://www.gnu.org/copyleft/lesser.html
 */
abstract public class GeneticAlgorithm<CHROMOSOME_TYPE extends Chromosome<?, ?>> {

    /*
    Number of Chromosomes to be created
     */
    private int populationSize;

    /*
    Setup mating population
     */
    private double matingPopulation;

    /*
    Percent that should mutate
     */
    private double mutationPercent;

    /*
    Percent chosen to be mated. They will choose partners from the entire
    mating population
     */
    private double percentToMate;

    /*
    Prevent the same gene from repeating
     */
    private boolean preventRepeat = false;

    /*
    How much genetic material to cut
     */
    private int cutLength;

    /*
    An optional thread pool to use
     */
    private ExecutorService pool;

    /*
    The population
     */
    private CHROMOSOME_TYPE[] chromosomes;

    /*
    Get a specific chromosome
    Parameter: i is the chromosome to return, and 0 as the first one
    @return a chromosome
     */
    public CHROMOSOME_TYPE getChromosome(final int i) {
        return this.chromosomes[i];
    }

    /*
    Return the entire population
     */
    public CHROMOSOME_TYPE[] getChromosomes() {
        return this.chromosomes;
    }

    /*
    Get the Cut length
     */
    public int getCutLength() {
        return this.cutLength;
    }

    /*
    Get the mating population
     */
    public double getMatingPopulation() {
        return this.matingPopulation;
    }

    /*
    Get the mutation percent
     */
    public double getMutationPercent() {
        return this.mutationPercent;
    }

    /*
    get percent to mate
     */
    public double getPercentToMate() {
        return this.percentToMate;
    }

    /*
    Get the optional ExectuorService
     */
    public ExecutorService getPool() {
        return this.pool;
    }

    /*
    Get the population size
     */
    public int getPopulationSize() {
        return this.populationSize;
    }

    /*
    Should repeating genes be prevented
     */
    public boolean isPreventRepeat() {
        return this.preventRepeat;
    }

    /*
    Modify the weight matrix and thresholds based on last call to calcError
     */
    public void iteration() throws GeneticAlgorithmError {
        final int countToMate = (int) (getPopulationSize() * getPercentToMate()); // Number of mating individuals
        final int offspringCount = countToMate * 2; // Create two offspring from Mmating individuals
        int offspringIndex = getPopulationSize() - offspringCount; // Index for offspring
        final int matingPopulationSize = (int) (getPopulationSize() * getMatingPopulation());

        final Collection<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();

        // Mate and form the next generation
        for (int i = 0; i < countToMate; i++) {
            final CHROMOSOME_TYPE mother = this.chromosomes[i];
            final int fatherInt = (int) (Math.random() * matingPopulationSize);
            final CHROMOSOME_TYPE father = this.chromosomes[fatherInt];
            final CHROMOSOME_TYPE child1 = this.chromosomes[offspringIndex];
            final CHROMOSOME_TYPE child2 = this.chromosomes[offspringIndex + 1];

            final MateWorker<CHROMOSOME_TYPE> worker = new MateWorker<CHROMOSOME_TYPE>(
                    mother, father, child1, child2
            );

            try {
                if (this.pool != null) {
                    tasks.add(worker);
                } else {
                    worker.call();
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }

            //mother.mate(father, chromosomes[offspringIndex], chromosomes[offspringIndex + 1];
            offspringIndex += 2; // Add 2 to the index for each loop
        }

        if (this.pool != null) {
            try {
                this.pool.invokeAll(tasks, 120, TimeUnit.SECONDS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Sort the next generation of chromosomes
        sortChromosomes();
    }

    /*
    Set the specified chromosome
    @param i:
            the chromosome to set
    @param value:
            the value for that specified chromosome
     */
    public void setChromosome(final int i, final CHROMOSOME_TYPE value) {
        this.chromosomes[i] = value;
    }

    /*
    Set the entire population
    @param chromosomes:
            the chromosomes to set
     */
    public void setChromosomes(final CHROMOSOME_TYPE[] chromosomes) {
        this.chromosomes = chromosomes;
    }

    /*
    Set the cut length
    @param cutLength:
            the cut length
     */
    public void setCutLength(final int cutLength) {
        this.cutLength = cutLength;
    }

    /*
    Set the mating population percent
    @param matingPopulation:
            The mating population percent
     */
    public void setMatingPopulation(final double matingPopulation) {
        this.matingPopulation = matingPopulation;
    }

    /*
    Set the mutation percent
    @param mutationPercent:
     */
    public void setMutationPercent(final double mutationPercent) {
        this.mutationPercent = mutationPercent;
    }

    /*
    Set the percent to mate
    @param percentToMate:
     */
    public void setPercentToMate(final double percentToMate) {
        this.percentToMate = percentToMate;
    }

    /*
    Set the optional thread pool
    @param pool:
            the pool to set
     */
    public void setPool(final ExecutorService pool) {
        this.pool = pool;
    }

    /*
    Set the population size
    @param populationSize:
            The population size.
     */
    public void setPopulationSize(final int populationSize) {
        this.populationSize = populationSize;
    }

    /*
    Set the gene
    @param preventRepeat
     */
    public void setPreventRepeat(final boolean preventRepeat) {
        this.preventRepeat = preventRepeat;
    }

    public void sortChromosomes() {
        Arrays.sort(this.chromosomes);
    }



}
