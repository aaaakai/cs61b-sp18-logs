package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;

public class Solver {
    private MinPQ<SearchNode> searchNodes;
    private SearchNode bestSolution;


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
        searchNodes = new MinPQ<SearchNode>();
        searchNodes.insert(init);
        while (!searchNodes.isEmpty()) {
            SearchNode bms = searchNodes.delMin();
            if (bms.currentState.estimatedDistanceToGoal() == 0) {
                bestSolution = bms;
                return;
            }
            for (WorldState nextState : bms.currentState.neighbors()) {
                if (bms.previous == null || !nextState.equals(bms.previous.currentState)) {
                    SearchNode newSeq = new SearchNode(nextState, bms.moves + 1, bms);
                    searchNodes.insert(newSeq);
                }
            }
        }
    }

    public int moves() {
        return bestSolution.moves;
    }

    public Iterable<WorldState> solution() {
        ArrayList<WorldState> reversere = new ArrayList<WorldState>();
        ArrayList<WorldState> result = new ArrayList<WorldState>();
        SearchNode temp = bestSolution;
        while (temp != null) {
            reversere.add(temp.currentState);
            temp = temp.previous;
        }
        for (int i = bestSolution.moves; i >= 0; i--) {
            result.add(reversere.get(i));
        }
        return result;
    }
}
