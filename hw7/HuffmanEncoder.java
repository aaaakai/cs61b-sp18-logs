import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> returnMap = new HashMap<>();
        for (char ch : inputSymbols) {
            if (returnMap.containsKey(ch)) {
                int prefreq = returnMap.get(ch);
                returnMap.replace(ch, prefreq + 1);
            } else {
                returnMap.put(ch, 1);
            }
        }
        return returnMap;
    }
    public static void main(String[] args) {
        char[] inputSym = FileUtils.readFile(args[0]);
        Map<Character, Integer> freqTable = buildFrequencyTable(inputSym);
        BinaryTrie decoder = new BinaryTrie(freqTable);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(decoder);
        ow.writeObject((Integer) inputSym.length);
        Map<Character, BitSequence> lookup = decoder.buildLookupTable();
        List<BitSequence> bitSeqs = new ArrayList<>();
        for (char ch : inputSym) {
            bitSeqs.add(lookup.get(ch));
        }
        BitSequence fileCode = BitSequence.assemble(bitSeqs);
        ow.writeObject(fileCode);
    }
}