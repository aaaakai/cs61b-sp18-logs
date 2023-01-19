package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import static org.junit.Assert.*;

public class Percolation {
    private class Site {
        boolean full;
        boolean open;
        boolean connectedToBottom;
        int index;
        Site(int x, int y, int N) {
            full = false;
            open = false;
            connectedToBottom = false;
            index = y + x * N;
        }
    }
    private boolean percolation;
    private int size;
    private Site[][] grid;
    private int openSites;
    private WeightedQuickUnionUF gridUnion;

    // create N-by-N grid, with all Sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("arg should be positive");
        }
        size = N;
        percolation = false;
        grid = new Site[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = new Site(i, j, N);
            }
        }
        openSites = 0;
        gridUnion = new WeightedQuickUnionUF(N * N);
    }

    private Site represent(int index) {
        int repre = gridUnion.find(index);
        return grid[repre / size][repre % size];
    }

    private void connect(Site a, Site b) {
        if (a.open && b.open) {
            boolean isfull = represent(a.index).full || represent(b.index).full;
            boolean atbottom = represent(a.index).connectedToBottom
                    || represent(b.index).connectedToBottom;
            gridUnion.union(a.index, b.index);
            represent(a.index).full = isfull;
            represent(a.index).connectedToBottom = atbottom;
            if (isfull && atbottom) {
                percolation = true;
            }
        }
    }
    // open the Site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new java.lang.IndexOutOfBoundsException("row or col is out of range");
        }
        if (grid[row][col].open) {
            return;
        }
        grid[row][col].open = true;
        openSites++;
        if (row == 0) {
            grid[row][col].full = true;
        }
        if (row == size - 1) {
            grid[row][col].connectedToBottom = true;
        }
        if (row > 0) {
            connect(grid[row - 1][col], grid[row][col]);
        }
        if (row < size - 1) {
            connect(grid[row + 1][col], grid[row][col]);
        }
        if (col > 0) {
            connect(grid[row][col - 1], grid[row][col]);
        }
        if (col < size - 1) {
            connect(grid[row][col + 1], grid[row][col]);
        }
        Site repre = represent(grid[row][col].index);
        if (repre.full && repre.connectedToBottom) {
            percolation = true;
        }
    }

    // is the Site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new java.lang.IndexOutOfBoundsException("row or col is out of range");
        }
        return grid[row][col].open;
    }

    // is the Site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new java.lang.IndexOutOfBoundsException("row or col is out of range");
        }
        return represent(grid[row][col].index).full;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return percolation;
    }

    public static void main(String[] args) {
        Percolation model = new Percolation(10);
        for (int i = 0; i < 9; i++) {
            model.open(i, 0);
        }

        for (int i = 0; i < 9; i++) {
            assertTrue(model.isOpen(i, 0));
            assertTrue(model.isFull(i, 0));
        }
        assertTrue(!model.percolates());
        model.open(9, 0);
        assertTrue(model.percolates());
        assertTrue(model.numberOfOpenSites() == 10);

        Percolation model1 = new Percolation(20);
        for (int i = 0; i < 19; i++) {
            model1.open(i, 0);
        }
        for (int i = 0; i < 19; i++) {
            assertTrue(model1.isOpen(i, 0));
            assertTrue(model1.isFull(i, 0));
        }
        for (int i = 1; i < 20; i++) {
            model1.open(i, 2);
        }
        for (int i = 1; i < 20; i++) {
            assertTrue(model1.isOpen(i, 2));
            assertTrue(!model1.isFull(i, 2));
        }
        assertTrue(!model1.percolates());
        model1.open(10, 1);
        assertTrue(model1.isFull(10, 1));
        assertTrue(model1.percolates());
        assertTrue(model1.numberOfOpenSites() == 39);

        Percolation model2 = new Percolation(1);
        model2.open(0, 0);
        assertTrue(model2.percolates());

        Percolation model3 = new Percolation(2);
        model3.open(0, 0);
        model3.open(1, 0);
        assertTrue(model3.percolates());
    }
}
