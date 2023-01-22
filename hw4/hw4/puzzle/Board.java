package hw4.puzzle;

import java.util.ArrayList;
import java.util.List;

public class Board implements WorldState {

    private int size;
    private int[][] boardTiles;

    public Board(int[][] tiles) {
        size = tiles.length;
        boardTiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardTiles[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i >= size || j < 0 || j >= size) {
            throw new java.lang.IndexOutOfBoundsException("index out of range");
        }
        return boardTiles[i][j];
    }

    public int size() {
        return size;
    }

    private int[][] tileCopy(int[][] source) {
        int[][] target = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                target[i][j] = source[i][j];
            }
        }
        return target;
    }

    public Iterable<WorldState> neighbors() {
        int i = 0, j = 0;
        for ( int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (boardTiles[x][y] == 0) {
                    i = x;
                    j = y;
                }
            }
        }
        List<WorldState> neighborList = new ArrayList<WorldState>();
        if (i > 0) {
            int[][] neighbor1 = tileCopy(boardTiles);
            neighbor1[i][j] = boardTiles[i - 1][j];
            neighbor1[i - 1][j] = 0;
            Board nei1 = new Board(neighbor1);
            neighborList.add(nei1);
        }
        if (i < size - 1) {
            int[][] neighbor2 = tileCopy(boardTiles);
            neighbor2[i][j] = boardTiles[i + 1][j];
            neighbor2[i + 1][j] = 0;
            Board nei2 = new Board(neighbor2);
            neighborList.add(nei2);
        }
        if (j > 0) {
            int[][] neighbor3 = tileCopy(boardTiles);
            neighbor3[i][j] = boardTiles[i][j - 1];
            neighbor3[i][j - 1] = 0;
            Board nei3 = new Board(neighbor3);
            neighborList.add(nei3);
        }
        if (j < size - 1) {
            int[][] neighbor4 = tileCopy(boardTiles);
            neighbor4[i][j] = boardTiles[i][j + 1];
            neighbor4[i][j + 1] = 0;
            Board nei4 = new Board(neighbor4);
            neighborList.add(nei4);
        }

        return neighborList;
    }

    public int hamming() {
        int dist = 0;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size; j++) {
                if (boardTiles[i][j] != (i * size + j + 1)) {
                    dist++;
                }
            }
        }
        for (int j = 0; j < size - 1; j++) {
            if (boardTiles[size - 1][j] != ((size - 1) * size + j + 1)) {
                dist++;
            }
        }
        return dist;
    }

    public int manhattan() {
        int x, y;
        int dist = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (boardTiles[i][j] != 0) {
                    x = (boardTiles[i][j] - 1) / size;
                    y = (boardTiles[i][j] - 1) % size;
                    dist += Math.abs(x - i) + Math.abs(y - j);
                }
            }
        }
        return dist;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        if (y.getClass() != this.getClass()) {
            return false;
        }
        boolean equal = true;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                equal = equal && this.boardTiles[i][j] == ((Board) y).boardTiles[i][j];
            }
        }
        return equal;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
