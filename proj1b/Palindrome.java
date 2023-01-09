public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        LinkedListDeque<Character> chardeque = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            chardeque.addLast(word.charAt(i));
        }
        return chardeque;
    }

    private boolean palihelper(Deque chardeque) {
        if (chardeque.size() > 1) {
            boolean isequal = (chardeque.removeFirst() == chardeque.removeLast());
            return isequal && palihelper(chardeque);
        } else {
            return true;
        }
    }
    public boolean isPalindrome(String word) {
        Deque chardeque = wordToDeque(word);
        return palihelper(chardeque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> chardeque = wordToDeque(word);
        boolean result = true;
        while (chardeque.size() > 1) {
            result = result && cc.equalChars(chardeque.removeFirst(),
                    chardeque.removeLast());
        }
        return result;
    }
}
