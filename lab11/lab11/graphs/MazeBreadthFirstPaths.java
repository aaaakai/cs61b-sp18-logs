package lab11.graphs;

import java.util.ArrayDeque;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        ArrayDeque<Integer> verToVisit = new ArrayDeque<Integer>();
        verToVisit.add(s);
        while (!verToVisit.isEmpty()) {
            int vertice = verToVisit.removeFirst();
            marked[vertice] = true;
            announce();

            if (vertice == t) {
                targetFound = true;
            }

            if (targetFound) {
                return;
            }

            for (int w : maze.adj(vertice)) {
                if (!marked[w]) {
                    edgeTo[w] = vertice;
                    announce();
                    distTo[w] = distTo[vertice] + 1;
                    verToVisit.add(w);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

