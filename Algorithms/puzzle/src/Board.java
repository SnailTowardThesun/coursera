import java.util.ArrayList;

public class Board {
    private int _dimension = -1;
    private int[][] _tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException();
        }

        _tiles = tiles.clone();

        _dimension = tiles.length;
    }

    // string representation of this board
    public String toString() {
        StringBuilder ss = new StringBuilder();

        if (_dimension == -1) {
            return ss.toString();
        }

        ss.append(_dimension);
        ss.append('\n');
        for (int i = 0; i < _dimension; i++) {
            for (int j = 0; j < _dimension; j++) {
                ss.append(String.format("%2d ", _tiles[i][j]));
            }

            ss.append('\n');
        }

        return ss.toString();
    }

    // board dimension n
    public int dimension() {
        return _dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int score = 0;

        int sequence = 1;
        for (int i = 0; i < _dimension; i++) {
            for (int j = 0; j < _dimension; j++) {
                if (_tiles[i][j] != sequence && _tiles[i][j] != 0) {
                    score++;
                }

                sequence++;
            }
        }

        return score;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int score = 0;

        int sequence = 1;
        for (int i = 0; i < _dimension; i++) {
            for (int j = 0; j < _dimension; j++) {

                if (_tiles[i][j] != sequence && _tiles[i][j] != 0) {
                    int dis = Math.abs(_tiles[i][j] - sequence);
                    score += Math.floorDiv(dis, _dimension) + dis % _dimension;
                }

                sequence++;
            }
        }

        return score;
    }

    // is this board the goal board?
    public boolean isGoal() {

        int sequence = 1;
        for (int i = 0; i < _dimension; i++) {
            for (int j = 0; j < _dimension; j++) {
                if (_tiles[i][j] != sequence && sequence < _dimension * _dimension) {
                    return false;
                }
                sequence++;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (y == this) {
            return true;
        }

        if (!y.getClass().isInstance(this)) {
            return false;
        }

        Board by = (Board) y;
        if (by._dimension != _dimension) {
            return false;
        }

        for (int i = 0; i < _dimension; i++) {
            for (int j = 0; j < _dimension; j++) {
                if (by._tiles[i][j] != _tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // all neighboring boards
        ArrayList<Board> neighbors = new ArrayList<Board>();

        for (int row = 0; row < _dimension; row++) {
            for (int col = 0; col < _dimension; col++) {
                if (_tiles[row][col] != 0) {
                    continue;
                }

                // top
                if (row > 0) {
                    Board board = new Board(_tiles);
                    board.swap(row, col, row - 1, col);
                    neighbors.add(board);
                }

                // bottom
                if (row < _dimension - 1) {
                    Board board = new Board(_tiles);
                    board.swap(row, col, row + 1, col);
                    neighbors.add(board);
                }

                // left
                if (col > 0) {
                    Board board = new Board(_tiles);
                    board.swap(row, col, row, col - 1);
                    neighbors.add(board);
                }

                // right
                if (col < _dimension - 1) {
                    Board board = new Board(_tiles);
                    board.swap(row, col, row, col + 1);
                    neighbors.add(board);
                }
            }
        }

        return neighbors;
    }

    private void swap(int vRow, int vCol, int wRow, int wCol) {
        int t = _tiles[vRow][vCol];
        _tiles[vRow][vCol] = _tiles[wRow][wCol];
        _tiles[wRow][wCol] = t;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board obj = new Board(_tiles);

        int firRow = 0;
        int firCol = 0;

        if (_tiles[firRow][firCol] == 0) {
            firCol++;
        }

        for (int row = 0; row < _dimension; row++) {
            for (int col = 0; col < _dimension; col++) {
                if (_tiles[row][col] != _tiles[firRow][firCol] && _tiles[row][col] != 0) {
                    obj.swap(firRow, firCol, row, col);
                    return obj;
                }
            }
        }
        return obj;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        {
            // int[][] matrix = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};
            int[][] matrix = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};

            Board b = new Board(matrix);
            System.out.printf("%s\n\n", b.toString());

            System.out.printf("haming: %d\n", b.hamming());
            System.out.printf("manhattan: %d\n", b.manhattan());

        }

        {
            int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
            Board b = new Board(matrix);
            if (b.isGoal()) {
                System.out.println("success");
            }
        }

    }
}
