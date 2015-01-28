package haiderllc.com.facebookgraphproject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import geneticalgorithm.ColorChromosome;
import geneticalgorithm.ColorGene;
import geneticalgorithm.ColorGeneticAlgorithm;
import graph.Graph;


/*
Class Description:
    This Activity will simulate the best coloring array for a graph
    using a Genetic Algorithm. The Genetic Algorithm will be initialized
    and loaded into a Chromosome Object

    *******************************************************************
    * STEPS INVOLVED
    * *****************************************************************
    *
    * 1.) CREATE INITIAL POPULATION:
    *       Here we have already initialized a population to 1000 chromosomes
    *
    * 2.) SUITABILITY AND PRIVILAGE TO MATE:
    *       Suitability and privilage to mate is based on the cost function of each chromosome
    *       and the initial values set for favoredPopulation matingPopulation
    *
    * 3.) MATING:
    *       The actual mating process involves crossing over the chromosomes and finding the cut
    *       length to create the offspring of the mother and father chromosomes
    *
    * 4.) MUTATION:
    *       Some of the offspring are randomly mutated using the mutate() method in

 */

public class MainActivity extends Activity {

    /*
    Debugging tag
     */
    public static final String TAG = "MainActivity";

    /*
    Aynsc task for loading the graph data
     */
    private GraphTask graphTask;

    /*
    Textview for displaying number of edges from async task
     */
    private TextView noOfEdges;

    /*
    For .split() method
     */
    private final String space = " ";

    /*
    Graph which will hold the edges for the data from the raw text file
     */
    private Graph facebookGraph;

    /*
    How many chromosomes to use
     */
    public static final int POPULATION_SIZE = 1000;

    /*
    What percentage of new offspring to mutate
     */
    public static final double MUTATION_RATE = 0.10;

    /*
    Population eligable for mating
     */
    protected int matingPopulationSize = POPULATION_SIZE / 2;

    /*
    Part of population favored for mating
     */
    protected int favoredPopulationSize = this.matingPopulationSize / 2;

    /*
    How much genetic material to take during a mating
     */
    protected int cutLength = facebookGraph.getAdjMatrixEdges().length / 5;

    /*
    The current generation or epoch
     */
    protected int generation;

    /*
    The background worker thread
     */
    protected Thread worker;

    /*
    Is this thread started?
     */
    protected boolean started = false;

    /*
    List of colors for a chromosome
     */
    protected int[] colors;

    /*
    Inherited Genetic Algorithm to be implemented
     */
    protected ColorGeneticAlgorithm colorGeneticAlgorithm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noOfEdges = (TextView) findViewById(R.id.numOfEdges);

        // Initialize Graph to 4039 nodes, in the future the graph should dynamically determine
        // number of vertices
        facebookGraph = new Graph(4039);

        graphTask = new GraphTask();
        graphTask.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GraphTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
             /*
        Code to grab data from the raw text file in the resources directory
            Uses an InputStream to grab the bytes from the file and then read into a
            BufferedReader.
         */
            try {
                InputStream is = getResources().openRawResource(R.raw.facebook_combined);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                // Grab the lines from the data file and store into a graph data structure
                String previousValue = null;
                while (br.readLine()!= null) {
                    String line = br.readLine();
                    // Log.i(TAG, line); // Test Code: Reads properly
                    // Partition values into a String array
                    String[] numbers = line.split(space);

                    // No need to setup if and else statements simple brute force approach
                    facebookGraph.addEdge(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));

                }

            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            noOfEdges.setText(String.valueOf(facebookGraph.numOfEdges()));
        }

    }

}
