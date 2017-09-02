/**
 * Created by hankun on 2017/9/2.
 */

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] _results = null;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("the n or trails should be larger than 0");
        }

        _results = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            int open_sites = 0;
            do {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    open_sites++;
                }
            } while (!p.percolates());
            _results[i] = (double) open_sites / ((double) n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(_results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(_results);
    }

    private double confidence() {
        return (1.96 * stddev() / Math.sqrt(_results.length));
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confidence();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confidence();
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}
