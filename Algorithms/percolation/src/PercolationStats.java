/**
 * Created by hankun on 2017/9/2.
 */
public class PercolationStats {
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("the n or trails should be larger than 0");
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return 0.0;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0.0;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0.0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0.0;
    }

    // test client (described below)
    public static void main(String[] args) {
        System.out.println("percolation stats test");

        PercolationStats p = new PercolationStats(Integer.valueOf(args[1]), Integer.valueOf(args[2]));
    }
}
