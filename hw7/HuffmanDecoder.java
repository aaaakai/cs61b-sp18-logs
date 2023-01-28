public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0]);
        BinaryTrie decoder = (BinaryTrie) or.readObject();
        int symNum = (int) or.readObject();
        BitSequence fileCode = (BitSequence) or.readObject();
        char[] fileSym = new char[symNum];

        for (int i = 0; i < symNum; i++) {
            Match match = decoder.longestPrefixMatch(fileCode);
            fileSym[i] = match.getSymbol();
            fileCode = fileCode.allButFirstNBits(match.getSequence().length());
        }
        FileUtils.writeCharArray(args[1], fileSym);
    }
}