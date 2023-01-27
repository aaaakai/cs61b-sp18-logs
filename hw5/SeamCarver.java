import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SeamCarver {

    //private int height;
    //private int width;
    private Picture currentPic;
    private double[][] energies;

    public SeamCarver(Picture picture) {
        currentPic = new Picture(picture.width(), picture.height());
        for (int x = 0; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {
                currentPic.set(x, y, new Color(picture.getRGB(x, y)));
            }
        }
        cacuEnergy();
    }

    private void cacuEnergy() {
        energies = new double[currentPic.width()][currentPic.height()];
        for (int x = 0; x < currentPic.width(); x++) {
            for (int y = 0; y < currentPic.height(); y++) {
                energies[x][y] = differ(left(x), y, right(x), y) +
                        differ(x, uppery(y), x, lower(y));
            }
        }
        return;
    }

    private int uppery(int y) {
        return (y == 0) ? currentPic.height() - 1 : y - 1;
    }

    private int lower(int y) {
        return (y == currentPic.height() - 1) ? 0 : y + 1;
    }

    private int left(int x) {
        return (x == 0) ? currentPic.width() - 1 : x - 1;
    }

    private int right(int x) {
        return (x == currentPic.width() - 1) ? 0 : x + 1;
    }

    private double differ(int x1, int y1, int x2, int y2) {
        Color color1 = currentPic.get(x1, y1),
                color2 = currentPic.get(x2, y2);
        int differr = color1.getRed() - color2.getRed(),
                differg = color1.getGreen() -color2.getGreen(),
                differb = color1.getBlue() -color2.getBlue();
        return differr * differr + differb * differb + differg * differg;
    }

    public Picture picture() {                       // current picture
        return currentPic;
    }

    public int width() {                        // width of current picture
        return energies.length;
    }

    public int height() {                       // height of current picture
        return energies[0].length;
    }

    public double energy(int x, int y) {           // energy of pixel at column x and row y
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new java.lang.IndexOutOfBoundsException("index out of bound");
        }
        return energies[x][y];
    }

    private ArrayList<Integer> upperThree(int x) {
        ArrayList<Integer> upper = new ArrayList<Integer>();
        if (x > 0) {
            upper.add(x - 1);
        }
        upper.add(x);
        if (x < width() - 1) {
            upper.add(x + 1);
        }
        return upper;
    }

    public int[] findVerticalSeam() {           // sequence of indices for horizontal seam
        double minPathE[][] = new double[width()][height()];
        for (int x = 0; x < width(); x++) {
            minPathE[x][0] = energies[x][0];
        }
        for (int y = 1; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                ArrayList<Double> upperE = new ArrayList<Double>();
                for (int i : upperThree(x)) {
                    upperE.add(minPathE[i][y - 1]);
                }
                minPathE[x][y] = energies[x][y] +
                        Collections.min(upperE);
            }
        }
        int[] seam = new int[height()];
        int minxindex = 0;
        for (int minx = 0; minx < width(); minx++) {
            if (minPathE[minx][height() - 1] < minPathE[minxindex][height() - 1]) {
                minxindex = minx;
            }
        }
        seam[height() - 1] = minxindex;
        for (int y = height() - 2; y >= 0; y--) {
            for (int minx : upperThree(minxindex)) {
                if (minPathE[minx][y] < minPathE[minxindex][y]) {
                    minxindex = minx;
                }
                seam[y] = minxindex;
            }
        }
        return seam;
    }

    private void transposing() {
        double[][] newE = new double[height()][width()];
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                newE[y][x] = energies[x][y];
            }
        }
        energies = newE;
    }

    public int[] findHorizontalSeam() {                   // sequence of indices for vertical seam
        transposing();
        int[] seam = findVerticalSeam();
        transposing();
        return seam;
    }
    public void removeHorizontalSeam(int[] seam) {  // remove horizontal seam from picture
        SeamRemover.removeHorizontalSeam(currentPic, seam);
        cacuEnergy();
    }
    public void removeVerticalSeam(int[] seam) {    // remove vertical seam from picture
        SeamRemover.removeVerticalSeam(currentPic, seam);
        cacuEnergy();
    }
}
