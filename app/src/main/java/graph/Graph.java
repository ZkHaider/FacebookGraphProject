package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Haider on 10/11/2014.
 */

/*
Note since time is of the essence the graph approach here is a "brute force" approach
In the future it would be better to employ a linkedlist approach
 */

public class Graph {

    /*
    Setup Graph Variables
     */
    protected int numVertices;
    protected boolean[][] adjMatrixEdges;
    private int[] colors;
    protected List<Integer> degreeList = new ArrayList<Integer>();

    public Graph(int vertices) {
        // Initialize number of vertices in the graph
        numVertices = vertices;
        if (numVertices < 0) {
            throw new RuntimeException("The number of vertices cannot be a negative value");
        }

        // Declare size of the array.
        adjMatrixEdges = new boolean[numVertices][numVertices];

    }

    // Return whether or not two vertices are adjacent
    public boolean adjacent(int vertex1, int vertex2) {
        return adjMatrixEdges[vertex1][vertex2];
    }

    // Method will return the number of neighbors for a given vertex in the matrix
    public List<Integer> neighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<Integer>();
        for (int i = 0; i < adjMatrixEdges.length; i++) {
            // If there is an edge between vertex and i then add it to neighbors
            if (adjMatrixEdges[vertex][i]) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    // This method will count the number of neighbors for a specific vertex and
    // then add up the sum and then divide by number of vertices.
    public double averageDegree() {

        // create a variable for the count, and initialize the counter to 0.
        double edgeCounter = 0;

        for (int i = 0; i < adjMatrixEdges.length; i++) {
            for (int j = 0; j < adjMatrixEdges[i].length; j++) {
                // this if statement scans the specific vertex for true
                // statements in the boolean array
                if (adjMatrixEdges[i][j]) {
                    // this logical expression adds up the count of the true
                    // statements, in graph theory this is adding up the
                    // degree of that specific vertex.
                    edgeCounter++;
                }
            }
        }
        // Here neighbor count is the number of edges as well.
        edgeCounter = 2 * (edgeCounter - 1) / numVertices;
        System.out.println(edgeCounter);
        return edgeCounter;
    }

    public boolean[][] addVertex() {

        // add an extra vertex to the graph.
        numVertices++;

        // secondly we have to copy over the contents of the old array into a
        // new array.

        // Initialize a new array

        boolean[][] newAdjMatrixEdges = adjMatrixEdges;

        // setup a for loop which sets up new values for

        for (int i = 0; i < adjMatrixEdges.length; i++) {
            for (int j = 0; j < adjMatrixEdges.length; j++) {
                adjMatrixEdges[i][j] = newAdjMatrixEdges[i + 1][j + 1];
            }
        }
        return newAdjMatrixEdges;

    }

    public void removeVertex(int vertex) {

        // set a local variable.
        int vertex_Selected = vertex;

        // create a new 2-d array where you can copy the old one over.
        boolean[][] new_adj_Matrix_Edges = new boolean[adjMatrixEdges.length - 1][adjMatrixEdges.length - 1];

        // create a for loop setup to copy over all data from old array to the
        // new array.
        for (int g = 0; g < vertex_Selected; g++) {
            for (int h = 0; h < vertex_Selected; h++) {
                new_adj_Matrix_Edges[g][h] = adjMatrixEdges[g][h];
            }
        }

        // now that we resize the new array with removed vertex.

        for (int i = vertex_Selected + 1; i < new_adj_Matrix_Edges.length; i++) {
            for (int j = vertex_Selected + 1; j < new_adj_Matrix_Edges[i].length; j++) {
                new_adj_Matrix_Edges[i][j] = adjMatrixEdges[i][j];
            }
        }
    }

    public void addEdge(int vertex_add_1, int vertex_add_2) {

        // We set a if statement checking to see if the following code does not
        // contain a self loop
        // and that there isn't already an edge between the two vertices picked.
        if (vertex_add_1 > numVertices || vertex_add_2 > numVertices) {
            System.out
                    .println("You cannot add an edge to a vertex which doesn't exist.");
        }
        if (adjMatrixEdges[vertex_add_1][vertex_add_2] == false
                && vertex_add_1 != vertex_add_2) {
            adjMatrixEdges[vertex_add_1][vertex_add_2] = true;
            adjMatrixEdges[vertex_add_2][vertex_add_1] = true;
            // System.out.println("There is now an edge between vertex " +
            // vertex_add_1 + " and vertex " + vertex_add_2); //Test code to see
            // outputs.
        } else {
            adjMatrixEdges[vertex_add_1][vertex_add_2] = true;
            adjMatrixEdges[vertex_add_2][vertex_add_1] = true;
        }
    }

    // This method removes an edge if the two int values in the 2-d boolean
    // array are true, converts to false, otherwise it stays false if no edge
    // present
    public void removeEdge(int vertex_remove_1, int vertex_remove_2) {

        if (vertex_remove_1 > numVertices || vertex_remove_2 > numVertices) {
            System.out
                    .println("You cannot remove an edge to a vertex which doesn't exist.");
        }
        if (adjMatrixEdges[vertex_remove_1][vertex_remove_2] == true
                && vertex_remove_1 != vertex_remove_2) {
            adjMatrixEdges[vertex_remove_1][vertex_remove_2] = false;
            adjMatrixEdges[vertex_remove_2][vertex_remove_1] = false;
            // System.out.println("The between vertex " + vertex_remove_1 +
            // " and vertex "
            // + vertex_remove_2 + " was removed."); //Test Code
        } else {
            adjMatrixEdges[vertex_remove_1][vertex_remove_2] = false;
            adjMatrixEdges[vertex_remove_2][vertex_remove_1] = false;
        }
    }

    // setup a method which finds the shortest cycle in the graph.
    // We want to set the method to return an int value, because the girth of
    // the graph will represent an integer value
    // which cannot be a negative value.

    // if we find the diameter of a graph then the shortest cycle will be the
    // girth, which will be the
    // 2diam(G) + 1.
    public int girth() {

        int vertexCounter = 0;

        for (int i = 0; i < adjMatrixEdges.length; i++) {
            for (int j = 0; j < adjMatrixEdges[i].length; j++) {
                if (adjMatrixEdges[i][j] == adjMatrixEdges[j][i]) {
                    vertexCounter++;
                }
            }
        }

        return vertexCounter;

    }

    // The diamter of the graph will be the largest number of true elements in a
    // row.
    // So for example in a 3 vertex graph, if row 0 has 1 true value, and row 1
    // has 2 true values, and row 2 has 1
    // true value, then the diameter is the maximum number of true values in row
    // 1.
    public int diameter() {

        // Setup a counter which will count the number of true elements in a row
        // of the 2-d array
        int true_Counter = 0;

        // Setup a diameter variable.
        int diameter;

        // setup a for loop system to iterate through the array.

        for (int i = 0; i < adjMatrixEdges.length; i++) {
            for (int j = 0; j < adjMatrixEdges[i].length; j++) {
                if (adjMatrixEdges[i][j] == true) {
                    true_Counter++;
                }
            }
        }

        diameter = true_Counter / 2;
        return diameter;

    }

    // This method will get the number of edges using similar code to neighbors
    // method.
    // In detail, it will count the total neighbors for each vertex and then
    // divide by 2.
    public int numOfEdges() {
        // Initialize an edge counter.
        int numOfEdges = 0;

        List<Integer> neighbors = new ArrayList<Integer>();
        // Loop over the 2-d array and add neighbors to arraylist.
        for (int i = 0; i < adjMatrixEdges.length; i++) {
            for (int j = 0; j < adjMatrixEdges[i].length; j++) {
                // The if statement here does not need an equality sign since
                // booleans are in the 2-d matrix.
                if (adjMatrixEdges[i][j]) {
                    // adds that vertex j to the list if adjacent to vertex i.
                    neighbors.add(j);
                    numOfEdges = neighbors.size();
                }
            }
        }
        // System.out.println("The number of edges in your graph are " +
        // numOfEdges/2); //Test code
        return numOfEdges / 2;
    }

    // This method uses Euler's relationship to determine whether or not a graph
    // is planar.
    public boolean isPlanar(Graph graph) {

        if (numVertices >= 3 && graph.numOfEdges() > ((3 * numVertices) - 6)) {
            System.out.println("Graph is not planar."); // Test Code
            return false;
        }

        System.out.println("Graph is planar."); // Test Code
        return true;

    }

    // Depth First Search Method which searches through graph
    // and marks vertices if there is a colour
    private List<Integer> sortDegreeSequence(Graph g) {

        // Setup an arraylist which will sort all the vertices
        // from highest to lowest degree

        for (int i = 0; i < g.adjMatrixEdges.length; i++) {
            degreeList.add(i);
        }

        // Run a for loop through the adj matrix edges and use the collections
        // class to swap out the vertices if the degree of j is higher than k.
        for (int k = 1; k < g.adjMatrixEdges.length; k++) {
            for (int j = 0; j < g.adjMatrixEdges.length; j++) {
                if (g.neighbors(j).size() > g.neighbors(k).size()) {
                    Collections.swap(degreeList, k, j);
                }
            }
        }

        Collections.reverse(degreeList);

        // Test code to see what's coming out.
        //System.out.println(degreeList);
        return degreeList;
    }

    public boolean simpleColouring(int k, Graph g) {

        // Initialize the colors 1-d array to the size of degreeList
        colors = new int[degreeList.size()];

        // Initialized a dummy arraylist to store neighbors in.
        List<Integer> tempDegreeList = new ArrayList<Integer>();

        // Have a loop that takes first index out of out of degree list and
        // checks its
        // neighbors
        for (int i = 0; i < degreeList.size(); i++) {
            colors[i] = 1;
            tempDegreeList = g.neighbors(i);

            // Setup conditional
            for (int j = 0; j < tempDegreeList.size(); j++) {
                if (degreeList.get(i) != tempDegreeList.get(j)) {
                    colors[j] = 0;
                }
            }

        }

        // Setup counter value
        int counter = 0;

        for (int k1 = 0; k1 < colors.length; k1++) {
            for (int l1 = 0; l1 < k1; l1++) {
                if (colors[k1] != colors[l1]) {
                    counter += 1;
                }
            }
        }

        System.out.println(counter);

        if (counter <= k) {
            return true;
        }
        return false;

    }

    public boolean[][] getAdjMatrixEdges() {
        return adjMatrixEdges;
    }

    public int[] getColorArray() {
        return colors;
    }


}
