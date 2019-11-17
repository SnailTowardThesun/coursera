import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        public Board board;
        public int moves;
        public SearchNode preSearchNode;

        public final int priority;

        public SearchNode(Board inboard, SearchNode inPreSearchNode) {
            board = inboard;
            preSearchNode = inPreSearchNode;
            if (inPreSearchNode == null) {
                moves = 0;
            } else {
                moves = inPreSearchNode.moves + 1;
            }

            priority = moves + board.manhattan();
        }

        @Override
        public int compareTo(SearchNode o) {
            return Integer.compare(this.priority, o.priority);
        }
    }

    private SearchNode currentNode;
    private SearchNode twincurrentNode;
    private Stack<Board> solution;

    private void putNeighBorsIntoPQ(SearchNode searchNode, MinPQ<SearchNode> pq) {
        Iterable<Board> neighbors = searchNode.board.neighbors();
        for (Board neighbor : neighbors) {
            if (searchNode.preSearchNode == null || !neighbor.equals(searchNode.preSearchNode.board)) {
                pq.insert(new SearchNode(neighbor, searchNode));
            }
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Constructor argument Board is null!");
        }

        currentNode = new SearchNode(initial, null);
        twincurrentNode = new SearchNode(initial.twin(), null);
        MinPQ<SearchNode> priorityQueue = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPriorityQueue = new MinPQ<SearchNode>();
        priorityQueue.insert(currentNode);
        twinPriorityQueue.insert(twincurrentNode);
        while (true) {
            currentNode = priorityQueue.delMin();
            if (currentNode.board.isGoal()) break;
            putNeighBorsIntoPQ(currentNode, priorityQueue);

            twincurrentNode = twinPriorityQueue.delMin();
            if (twincurrentNode.board.isGoal()) break;
            putNeighBorsIntoPQ(twincurrentNode, twinPriorityQueue);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return currentNode.board.isGoal();
    }

    // min number of moves to solve initial board
    public int moves() {
        if (currentNode.board.isGoal()) {
            return currentNode.moves;
        }

        return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (currentNode.board.isGoal()) {
            solution = new Stack<Board>();
            SearchNode node = currentNode;
            while (node != null) {
                solution.push(node.board);
                node = node.preSearchNode;
            }
            return solution;
        } else
            return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
