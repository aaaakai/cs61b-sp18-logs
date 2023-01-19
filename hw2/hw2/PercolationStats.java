package hw2;

import static edu.princeton.cs.introcs.StdRandom.*;

public class PercolationStats {
    private int[] threshold;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("args should be positive");
        }
        threshold = new int[T];
        for (int i = 0; i < T; i++) {
            Percolation model = pf.make(N);
            while (!model.percolates()) {
                int row = uniform(N);
                int col = uniform(N);
                if (!model.isOpen(row, col)) {
                    model.open(row, col);
                }
            }
            threshold[i] = model.numberOfOpenSites();
        }
    }

    public double mean() {
        return edu.princeton.cs.introcs.StdStats.mean(threshold);
    }

    public double stddev() {
        return edu.princeton.cs.introcs.StdStats.stddev(threshold);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(threshold.length);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(threshold.length);
    }


    public static void main(String[] args) {
        PercolationFactory tool = new PercolationFactory();
        PercolationStats testmodel = new PercolationStats(20, 1000, tool);
        System.out.print(testmodel.mean() + "\n");
        System.out.print(testmodel.stddev() + "\n");
        System.out.print(testmodel.confidenceLow() + "\n");
    }
     
}
