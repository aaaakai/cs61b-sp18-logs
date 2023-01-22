package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Solver {
    private MinPQ<SearchNode> SearchNodes;
    private SearchNode BestSolution;


    private class SearchNode implements Comparable<SearchNode> {
        private WorldState currentState;
        private int moves;
        private SearchNode previous;
        private int estDist;

        private SearchNode(WorldState state, int n, SearchNode prenode) {
            currentState = state;
            moves = n;
            previous = prenode;
            estDist = state.estimatedDistanceToGoal();
        }
        private int priority() {
            return moves + estDist;
        }

        @Override
        public int compareTo(SearchNode node2) {
            if (this.priority() - node2.priority() < 0) {
                return -1;
            } else if (this.priority() - node2.priority() == 0) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public Solver(WorldState initial) {
        SearchNode init = new SearchNode(initial, 0, null);
        SearchNodes = new MinPQ<SearchNode>();
        SearchNodes.insert(init);
        while (!SearchNodes.isEmpty()) {
            SearchNode BMS = SearchNodes.delMin();
            if (BMS.currentState.estimatedDistanceToGoal() == 0) {
                BestSolution = BMS;
                return;
            }
            for (WorldState nextState : BMS.currentState.neighbors()) {
                if (BMS.previous == null || !nextState.equals(BMS.previous.currentState)) {
                    SearchNode newSeq = new SearchNode(nextState, BMS.moves + 1, BMS);
                    SearchNodes.insert(newSeq);
                }
            }
        }
    }

    public int moves() {
        return BestSolution.moves;
    }

    public Iterable<WorldState> solution() {
        ArrayList<WorldState> reversere = new ArrayList<WorldState>();
        ArrayList<WorldState> result = new ArrayList<WorldState>();
        SearchNode temp = BestSolution;
        while (temp != null) {
            reversere.add(temp.currentState);
            temp = temp.previous;
        }
        for (int i = BestSolution.moves; i >= 0; i--) {
            result.add(reversere.get(i));
        }
        return result;
    }
}
