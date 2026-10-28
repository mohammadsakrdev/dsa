package datastructures.problems;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * LeetCode 127: Word Ladder
 * <p>
 * Finds the length of the shortest transformation sequence from beginWord to endWord,
 * such that only one letter can be changed at a time and each transformed word must exist in the word list.
 * <a href="https://neetcode.io/solutions/word-ladder">https://neetcode.io/solutions/word-ladder</a>
 */
public class WordLadder {
    // Utility class to hold a pair of values
    static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    // Method to find the shortest transformation sequence length
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // Convert the word list to a HashSet for O(1) lookups and efficient visited tracking
        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord)) return -1; // If endWord is not in the word list, return -1

        Queue<Pair<String, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(beginWord, 1)); // Start BFS with the beginWord and initial length of 1

        while (!queue.isEmpty()) {
            Pair<String, Integer> current = queue.poll();

            String word = current.getKey();
            int level = current.getValue();

            if (word.equals(endWord)) return level; // Found the end word

            // --- TRANSFORMATION GENERATION SECTION ---
            // Iterate through each character position (index i) of the current word
            for (int i = 0; i < word.length(); i++) {
                // Convert the current word string into a mutable character array to allow in-place substitution
                char[] wordArray = word.toCharArray();

                // Try replacing the character at index i with every possible lowercase letter from 'a' to 'z'
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    wordArray[i] = ch; // Substitute the character at the current position

                    // Construct the newly formed candidate word string with a single-character mutation
                    String nextWord = new String(wordArray);

                    // Check if the generated mutation exists in our dictionary word set (and hasn't been visited yet)
                    if (wordSet.contains(nextWord)) {
                        // Enqueue the new valid word paired with the incremented sequence depth level
                        queue.add(new Pair<>(nextWord, level + 1));

                        // Immediately remove the word from the set to mark it as visited, preventing cycles and duplicates
                        wordSet.remove(nextWord);
                    }
                }
            }
        }

        return -1; // Transformation not possible
    }


    // Main method to test the solution
    public static void main(String[] args) {
        WordLadder solution = new WordLadder();
        System.out.println(solution.ladderLength("bit", "pog", Arrays.asList("but", "put", "pig", "pog", "big"))); // Example 1
        System.out.println(solution.ladderLength("cat", "dog", Arrays.asList("cot", "dot", "dog"))); // Example 2
        System.out.println(solution.ladderLength("sing", "ring", Arrays.asList("ring"))); // Example 3
    }
}
