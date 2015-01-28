package geneticalgorithm;

import java.util.Random;

/**
 * Created by Haider on 10/12/2014.
 */

/*
Class will generate a random color number for a gene
Gene: color number
 */

public class ColorGene {

    private int color;

    public ColorGene() {
        color = randInt(0, 3);
    }

    public static int randInt(int min, int max) {
        Random random = new Random();
        int randNumber = random.nextInt((max - min) + 1) + min;
        return randNumber;
    }

    public int getColor() {
        return color;
    }

}
