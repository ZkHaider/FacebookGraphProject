package basegeneticalgorithm;

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


import java.util.HashSet;
import java.util.Set;


/**
 * Chromosome: Implements a chromosome to genetic algorithm.
 * This is an abstract class.  Other classes are provided in this
 * book that use this base class to train neural networks or
 * provide an answer to the traveling salesman problem.
 *
 * Lifeforms in this genetic algorithm consist of one single
 * chromosome each.  Therefore, this class represents a virtual
 * lifeform.  The chromosome is a string of objects that represent
 * one solution.  For a neural network, this string of objects
 * usually represents the weight and threshold matrix.
 *
 * Chromosomes are made up of genes.  These are of the generic type
 * GENE_TYPE.  For a neural network this type would most likely
 * be double values.
 *
 * @author Jeff Heaton
 * @version 2.1
 */

abstract public class Chromosome<GENE_TYPE, GA_TYPE extends GeneticAlgorithm<?>>
        implements Comparable<Chromosome<GENE_TYPE, GA_TYPE>> {

    /*
    Indvidual elements of the chromosome
     */
    protected GENE_TYPE[] genes;
    /*
    The cost fot this specific chromosome. The lower the cost the better less cost means more
    Reproduction
     */
    private double cost;
    /*
    The genetic algorithm that this chromosome is associated with
     */
    private GA_TYPE geneticAlgorithm;

    /*
    Call this to calculate the cost for this chromosome
     */
    abstract public void calculateCost() throws GeneticAlgorithmError;

    /*
    Used to compare two chromosomes. Used to sort by cost.

    Parameters:
        other = other chromosome to compare to

    Return:
        Value 0 is returned if the argument is a chromosome that has
        an equal cost to the former chromosome, a value of less than 0
        if the argument is a chromosome with a greater cost than the other
        chromosome, and value of greater than 0 if the argument is less than the
        former chromosome
     */
    public int compareTo(final Chromosome<GENE_TYPE, GA_TYPE> other) {
//        if (getCost() > other.getCost()) {
//            return 1;
//        } else {
//            return -1;
//        }
        return (int)(getCost() - other.getCost());
    }

    /*
    Return the cost
     */
    public double getCost() {
        return this.cost;
    }

    /*
    Set the cost of the chromosome
    @param cost:
            the cost to set
     */
    public void setCost(final double cost) {
        this.cost = cost;
    }

    /*
    Get the specified gene
    @param gene the specified gene
    @return the gene specified
     */
    public GENE_TYPE getGene(final int gene) {
        return this.genes[gene];
    }

    /*
    Use this to get the entire gene array
     */
    public GENE_TYPE[] getGenes() {
        return this.genes;
    }

    /*
    Set the entire gene array
    @param genes:
            the genes to set
     */
    public void setGenes(final GENE_TYPE[] genes) throws GeneticAlgorithmError {
        this.genes = genes;
    }

    /*
    @return the Genetic Algorithm used
     */
    public GA_TYPE getGeneticAlgorithm() {
        return this.geneticAlgorithm;
    }

    /*
    Set the genetic algorithm
    @param geneticAlgorithm:
            the geneticalgorithm to set
     */
    public void setGeneticAlgorithm(final GA_TYPE geneticAlgorithm) {
        this.geneticAlgorithm = geneticAlgorithm;
    }

    /*
    Get a list of the genes that have not been taken before.
    This is useful if you do not wish to make the same gene
    appear more than once in a chromosome.

    @Param source:
            The pool of genes to select from.
    @Param taken:
            An array of the taken genes.
    @return The genes in source which are not alreay taken.
     */
    private GENE_TYPE getNotTaken(final Chromosome<GENE_TYPE, GA_TYPE> source,
                                  final Set<GENE_TYPE> taken) {
        final int geneLength = source.size();

        for (int i = 0; i < geneLength; i++) {
            final GENE_TYPE trial = source.getGene(i);
            if (!taken.contains(trial)) { //Added trial != null here just in case
                taken.add(trial);
                return trial;
            }
        }
        System.err.println("I AM RETURNING NULL! ");
        throw new RuntimeException("This should not happen" + source + " " + taken);
    }

    /*
    Assuming the Chromosome is the "mother" mate with the
    passed in "father".

    Process Description: mating process treats the genes
        of a chromosome as a long array of elements.

    @param father:
            The father mate
    @param offspring1:
            returns the first offspring
    @param offspring2:
            returns the second offspring
    @return the amount of mutation that was applied
    @throws GeneticAlgorithmError
     */
    public void mate(final Chromosome<GENE_TYPE, GA_TYPE> father,
                     final Chromosome<GENE_TYPE, GA_TYPE> offspring1,
                     final Chromosome<GENE_TYPE, GA_TYPE> offspring2)
            throws GeneticAlgorithmError {

        //Note: This algorithm overwrites offspring1 and offspring2
        // with mating data from this (mother) and father.

        //Debug check
        for(int i = 0; i < offspring1.getGenes().length; i++) {
            if (offspring1.getGene(i) == null)
                System.err.println("O1 Input error!" + i);
            if (offspring2.getGene(i) == null)
                System.err.println("O2 Input error!" + i);
            if (this.getGene(i) == null)
                System.err.println("Mother Input error!" + i);
            if (father.getGene(i) == null)
                System.err.println("Father Input error!" + i);
        }

        final int geneLength = getGenes().length;

        // The chromosome must be cut at two positions, determine them randomly
        final int cutpoint1 = (int) (Math.random() * (geneLength - geneticAlgorithm
                .getCutLength()));
        final int cutpoint2 = cutpoint1 + geneticAlgorithm.getCutLength();

        // Keep track of which sections of the gene have been taken in each of
        // the two offspring, default is false.
        final Set<GENE_TYPE> taken1 = new HashSet<GENE_TYPE>();
        final Set<GENE_TYPE> taken2 = new HashSet<GENE_TYPE>();

        // Handle that cut section or the "middle" section
        // (optimized a little - BJP)
        for (int i = cutpoint1; i <= cutpoint2; i++) {
            offspring1.setGene(i, father.getGene(i)); // Replace that gene with father's at i
            offspring2.setGene(i, this.getGene(i)); // Replace that gene with mother's at i
            taken1.add(offspring1.getGene(i)); // Add that gene from offspring1 to taken1 hashset
            taken2.add(offspring2.getGene(i)); // Add that gene from offspring2 to taken2 hashset
        }

        // Now handle the outer sections of the gene
        for (int i = 0; i < geneLength; i++) {
            if ((i < cutpoint1) || (i > cutpoint2)) {
//                if (geneticAlgorithm.isPreventRepeat()) {
//                    offspring1.setGene(i, getNotTaken(this, taken1));
//                    offspring2.setGene(i, getNotTaken(father, taken2));
//                } else {
                offspring1.setGene(i, this.getGene(i));
                offspring2.setGene(i, father.getGene(i));
//                }
            }
        }

        // Mutate the gene randomly
        if (Math.random() < this.geneticAlgorithm.getMutationPercent()) {
            offspring1.mutate();
        }
        if (Math.random() < this.geneticAlgorithm.getMutationPercent()) {
            offspring2.mutate();
        }

        //Debug check
        for(int i = 0; i < offspring1.getGenes().length; i++)
            if(offspring1.getGene(i) == null)
                System.err.println("Reproduction error!" + i);
        for(int i = 0; i < offspring2.getGenes().length; i++)
            if(offspring2.getGene(i) == null)
                System.err.println("Reproduction error!" + i);

        // Calculate the costs associated
        offspring1.calculateCost();
        offspring2.calculateCost();
    }

    // This method is called to mutate the chromosome
    abstract public void mutate();

    /*
    Set the specified gene's value.
    @param gene:
            the specified gene
    @param value:
            the value to set to the specified gene
     */
    public void setGene(final int gene, final GENE_TYPE value) {
        this.genes[gene] = value;
    }

    /*
    Set the genes directly, not allowed to be overriden
    @param genes:
            the genes to set
     */
    public final void setGenesDirect(final GENE_TYPE[] genes)
            throws GeneticAlgorithmError {
        this.genes = genes;
    }

    /*
    Get the size of the gene array
    @return the size of the gene array
     */
    private int size() {
        return this.genes.length;
    }

    /*
    Convert the chromosome to a string
    @return:
            return the chromosome as a string
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Chromosome: cost=");
        builder.append(getCost());
        return builder.toString();
    }


}
