package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Haider on 10/11/2014.
 */
public class DataReader {

    private final String space = " ";
    private int[][] data;

    public DataReader(File f) {

        try {
            @SuppressWarnings("resource")
            Scanner inFile = new Scanner(f);

            int noOfRowsInData = 0;

            LineNumberReader lnr = new LineNumberReader(new FileReader(f));
            try {
                lnr.skip(Long.MAX_VALUE);
                noOfRowsInData = lnr.getLineNumber();
                lnr.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String line = inFile.nextLine();

            String[] numbers = line.split(space);

            // Initialize data array to number or rows and length of string array
            data = new int[noOfRowsInData][numbers.length];

            //System.out.println(noOfRowsInData);

            int i = 0;
            // While there is another line in inFile.
            inFile = new Scanner(f);

            while (inFile.hasNextLine()) {

                line = inFile.nextLine();
                numbers = line.split(space);

                // Cope over contents of file to data array
                data[i][0] = Integer.parseInt(numbers[0]);
                data[i][1] = Integer.parseInt(numbers[1]);


                i++;
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for (int i = 0; i < data.length; i++) {
                System.out.println(data[i][0] + " " + data[i][1]);
        }

    }


    public int randInt(int min, int max) {
        // Generate a random int between min and max.
        Random random = new Random();
        // nextInt is normally exclusive to the top value, so we add 1 here
        int randomNum = random.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static void main(String[] args) {
        DataReader dataReader = new DataReader(new File("C:\\Heart Disease Machine Learning\\facebook.txt"));

    }

}