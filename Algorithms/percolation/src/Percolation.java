/**
 * Created by hankun on 2017/9/2.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // the size of gird
    private int _size = 0;
    // container for sites
    private boolean[][] _sites = null;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("the number of grid should be larger than 0");
        }

        _size = n;
        _sites = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                _sites[i][j] = false;
            }
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > _size || col > _size) {
            throw new java.lang.IllegalArgumentException("the row or col is beyond limits");
        }

        _sites[row][col] = true;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > _size || col > _size) {
            throw new java.lang.IllegalArgumentException("the row or col is beyond limits");
        }
        return false;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > _size || col > _size) {
            throw new java.lang.IllegalArgumentException("the row or col is beyond limits");
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return 0;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        System.out.println("homework for algorithms part 1 lesson 1");

        Percolation p = new Percolation(10);


    }
}

