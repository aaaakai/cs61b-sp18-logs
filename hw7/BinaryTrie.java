import java.io.Serializable;
import java.util.*;

public class BinaryTrie implements Serializable {

    private class Node implements Serializable {
        private char ch;
        private int freq;
        private Node left;
        private Node right;
        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
    }

    private Node root;
    private Map<Character, BitSequence> returnMap;
    private StringBuilder bitSeq = new StringBuilder();

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        Comparator<Node> cmp = new Comparator<Node>() {
            @Override
            public int compare(Node node, Node t1) {
                if (node.freq > t1.freq) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
        PriorityQueue<Node> pq = new PriorityQueue<>(cmp);
        for (Character chara : frequencyTable.keySet()) {
            pq.add(new Node(chara, frequencyTable.get(chara), null, null));
        }

        while (pq.size() > 1) {
            Node left = pq.remove();
            Node right = pq.remove();
            pq.add(new Node('\0', left.freq + right.freq, left, right));
        }
        root = pq.remove();
    }

    private boolean isLeaf(Node n) {
        return n.left == null && n.right == null;
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        Node p = root;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < querySequence.length(); i++) {
            int bit = querySequence.bitAt(i);
            sb.append(bit);
            if (bit == 0) {
                p = p.left;
            } else {
                p = p.right;
            }
            if (isLeaf(p)) {
                break;
            }
        }
        BitSequence bs = new BitSequence(sb.toString());
        return new Match(bs, p.ch);
    }
    public Map<Character, BitSequence> buildLookupTable() {
        returnMap = new HashMap<>();
        bitSeq = new StringBuilder();
        traverse(root);
        return returnMap;
    }

    private void traverse(Node p) {
        if (isLeaf(p)) {
            BitSequence bs = new BitSequence(bitSeq.toString());
            returnMap.put(p.ch, bs);
            return;
        }

        bitSeq.append(0);
        traverse(p.left);
        bitSeq.deleteCharAt(bitSeq.length() - 1);

        bitSeq.append(1);
        traverse(p.right);
        bitSeq.deleteCharAt(bitSeq.length() - 1);
    }
}
