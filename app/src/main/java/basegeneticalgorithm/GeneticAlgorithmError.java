package basegeneticalgorithm;

/**
 * Credited to Jeff Heaton: Introduction to Neural Networks 2nd Edition
 */
public class GeneticAlgorithmError extends RuntimeException {

    /*
    Serial ID for this specific class
     */
    private static final long serialVersionUID = 7167228729133120101L;

    /*
    Construct message
     */
    public GeneticAlgorithmError(final String msg) {
        super(msg);
    }

    /*
    Construct exception
     */
    public GeneticAlgorithmError(final Throwable t) {
        super(t);
    }

}
