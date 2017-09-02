/**
 * Created by hankun on 2017/9/2.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // the size of gird
    private int _bound_size = 0;
    // container for sites
    private boolean[][] _sites = null;
    // number of opened sites
    private int _open_size = 0;

    // union instance
    private WeightedQuickUnionUF _union_instance = null;
    // back wash
    private WeightedQuickUnionUF _back_wash = null;

    // virtual top node
    private int _virtual_top_node = 0;
    // virtual bottom node
    private int _virtual_bottom_node = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("the number of grid should be larger than 0");
        }

        _bound_size = n;

        // initialize the container
        _sites = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                _sites[i][j] = false;
            }
        }

        _virtual_top_node = n * n;
        _virtual_bottom_node = n * n + 1;

        _union_instance = new WeightedQuickUnionUF(_bound_size * _bound_size + 2);
        _back_wash = new WeightedQuickUnionUF(_bound_size * _bound_size + 2);
    }

    // is the arguments beyond limits
    private void isArgBeyondLimits(int row, int col) {
        if (row < 1 || row > _bound_size) {
            throw new java.lang.IllegalArgumentException("the row is beyond limits");
        }

        if (col < 1 || col > _bound_size) {
            throw new java.lang.IllegalArgumentException("the col is beyond limits");
        }
    }

    private void connectTopNode(int row, int col) {
        if (row <= 1) {
            return;
        }

        if (_sites[row - 2][col - 1]) {
            int node = (row - 1) * _bound_size + (col - 1);
            int top = (row - 2) * _bound_size + (col - 1);
            _union_instance.union(node, top);
            _back_wash.union(node, top);
        }
    }

    private void connectLeftNode(int row, int col) {
        if (col <= 1) {
            return;
        }

        if (_sites[row - 1][col - 2]) {
            int node = (row - 1) * _bound_size + (col - 1);
            int left = (row - 1) * _bound_size + (col - 2);
            _union_instance.union(node, left);
            _back_wash.union(node, left);
        }
    }

    private void connectRightNode(int row, int col) {
        if (col >= _bound_size) {
            return;
        }

        if (_sites[row - 1][col]) {
            int node = (row - 1) * _bound_size + (col - 1);
            int right = (row - 1) * _bound_size + col;
            _union_instance.union(node, right);
            _back_wash.union(node, right);
        }
    }

    private void connectBottomNode(int row, int col) {
        if (row >= _bound_size) {
            return;
        }

        if (_sites[row][col - 1]) {
            int node = (row - 1) * _bound_size + (col - 1);
            int bottom = row * _bound_size + (col - 1);
            _union_instance.union(node, bottom);
            _back_wash.union(node, bottom);
        }
    }

    private void connectNodes(int row, int col) {
        connectTopNode(row, col);
        connectLeftNode(row, col);
        connectRightNode(row, col);
        connectBottomNode(row, col);
    }

    private void connectVirtualTop(int row, int col) {
        if (row != 1) {
            return;
        }

        int node = (row - 1) * _bound_size + (col - 1);
        _union_instance.union(node, _virtual_top_node);
        _back_wash.union(node, _virtual_top_node);
    }

    private void connectVirtualBottom(int row, int col) {
        if (row != _bound_size) {
            return;
        }

        int node = (row - 1) * _bound_size + (col - 1);
        _back_wash.union(node, _virtual_bottom_node);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        isArgBeyondLimits(row, col);
        if (!isOpen(row, col)) {
            _sites[row - 1][col - 1] = true;
            _open_size++;
        }

        connectNodes(row, col);
        connectVirtualTop(row, col);
        connectVirtualBottom(row, col);
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        isArgBeyondLimits(row, col);

        return _sites[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        isArgBeyondLimits(row, col);

        int node = (row - 1) * _bound_size + (col - 1);
        return _union_instance.connected(node, _virtual_top_node);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return _open_size;
    }

    // does the system percolate?
    public boolean percolates() {
        return _back_wash.connected(_virtual_bottom_node, _virtual_top_node);
    }

    // test client (optional)
    public static void main(String[] args) {

        Percolation p = new Percolation(5);

        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(4, 1);
        p.open(5, 1);

        if (p.percolates()) {
            System.out.println("success");
        }

    }
}

