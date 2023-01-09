public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> chardeque = new ArrayDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            chardeque.addLast(word.charAt(i));
        }
        return chardeque;
    }

    /*
    private boolean palihelper(Deque chardeque) {
        if (chardeque.size() > 1) {
            boolean isequal = (chardeque.removeFirst() == chardeque.removeLast());
            return isequal && palihelper(chardeque);
        } else {
            return true;
        }
    }
    */

    public boolean isPalindrome(String word) {
        Deque chardeque = wordToDeque(word);
        for (int i = 0; i < word.length() / 2; i++) {
            if (chardeque.removeLast() != chardeque.removeFirst()) {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> chardeque = wordToDeque(word);
        for (int i = 0; i < word.length() / 2; i++) {
            if (!cc.equalChars(chardeque.removeFirst(), chardeque.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
