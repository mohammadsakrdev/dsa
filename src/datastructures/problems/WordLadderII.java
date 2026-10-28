package datastructures.problems;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * LeetCode 126: Word Ladder II
 * <p>
 * Finds all shortest transformation sequences from beginWord to endWord,
 * changing one letter at a time using only words from wordList.
 * <a href="https://www.hellointerview.com/community/questions/word-ladder-ii/cm5eh7nrh04qi838ol80fldhe">Hello Interview Solution</a>
 * <a href="https://algo.monster/liteproblems/126">AlgoMonster Solution</a>
 */
public class WordLadderII {
    // Store all shortest transformation sequences
    private List<List<String>> result;
    // Map to store predecessors for each word in the shortest paths
    private Map<String, Set<String>> predecessors;

    /**
     * Method to find all shortest transformation sequences from beginWord to endWord.
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        result = new ArrayList<>();

        // Convert word list to set for O(1) lookup
        Set<String> wordSet = new HashSet<>(wordList);

        // Early return if endWord is not in the word list
        if (!wordSet.contains(endWord)) {
            return result;
        }

        // Remove beginWord from set to avoid revisiting
        wordSet.remove(beginWord);

        // Track the distance (steps) from beginWord to each word
        Map<String, Integer> distanceMap = new HashMap<>();
        distanceMap.put(beginWord, 0);

        // Initialize predecessors map
        predecessors = new HashMap<>();

        // BFS queue for level-order traversal
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(beginWord);

        boolean targetFound = false;
        int currentStep = 0;

        // --- PHASE 1: Breadth First Search (BFS) to find all shortest paths ---
        while (!queue.isEmpty() && !targetFound) {
            currentStep++;
            int levelSize = queue.size();

            // Process all words at current level
            for (int i = 0; i < levelSize; i++) {
                String currentWord = queue.poll();

                // --- TRANSFORMATION GENERATION SECTION ---
                // Iterate through each character position (index charIndex) of the current word
                char[] wordChars = currentWord.toCharArray();
                for (int charIndex = 0; charIndex < wordChars.length; charIndex++) {
                    char originalChar = wordChars[charIndex];

                    // Try replacing the character at index charIndex with every possible lowercase letter from 'a' to 'z'
                    for (char newChar = 'a'; newChar <= 'z'; newChar++) {
                        wordChars[charIndex] = newChar;
                        String transformedWord = new String(wordChars);

                        // If we've seen this word at the exact same distance level, add another predecessor path
                        if (distanceMap.getOrDefault(transformedWord, 0) == currentStep) {
                            predecessors.get(transformedWord).add(currentWord);
                        }

                        // Skip if word is not in wordSet (already visited or invalid dictionary word)
                        if (!wordSet.contains(transformedWord)) {
                            continue;
                        }

                        // Record predecessor relationship
                        predecessors.computeIfAbsent(transformedWord, key -> new HashSet<>())
                                .add(currentWord);

                        // Mark as visited by removing from wordSet, record distance, and add to queue
                        wordSet.remove(transformedWord);
                        queue.offer(transformedWord);
                        distanceMap.put(transformedWord, currentStep);

                        // Check if we've reached the target endWord
                        if (endWord.equals(transformedWord)) {
                            targetFound = true;
                        }
                    }

                    // Restore original character for the next loop iteration
                    wordChars[charIndex] = originalChar;
                }
            }
        }

        // --- PHASE 2: Depth First Search (DFS) Path Reconstruction ---
        // If target was found, reconstruct all shortest paths backwards from endWord to beginWord
        if (targetFound) {
            Deque<String> currentPath = new ArrayDeque<>();
            currentPath.add(endWord);
            buildPaths(currentPath, beginWord, endWord);
        }

        return result;
    }

    /**
     * Recursive DFS helper to reconstruct all shortest paths from endWord to beginWord
     * using the predecessors map.
     */
    private void buildPaths(Deque<String> currentPath, String beginWord, String currentWord) {
        // Base case: successfully reached the beginning word
        if (currentWord.equals(beginWord)) {
            result.add(new ArrayList<>(currentPath));
            return;
        }

        // Recursively build paths through all stored predecessors
        for (String predecessor : predecessors.get(currentWord)) {
            currentPath.addFirst(predecessor);
            buildPaths(currentPath, beginWord, predecessor);
            currentPath.removeFirst(); // Backtrack
        }
    }

    // Main method to test the solution with sample test cases
    public static void main(String[] args) {
        WordLadderII solution = new WordLadderII();

        // --- TEST CASE 1: Standard multi-path configuration ---
        String beginWord1 = "hit";
        String endWord1 = "cog";
        List<String> wordList1 = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");

        System.out.println("===============================================================");
        System.out.println("RUNNING TEST 1: beginWord = \"" + beginWord1 + "\", endWord = \"" + endWord1 + "\"");
        System.out.println("===============================================================");
        List<List<String>> result1 = solution.findLadders(beginWord1, endWord1, wordList1);
        System.out.println("Shortest Paths Output: " + result1);
        System.out.println();

        // --- TEST CASE 2: Unreachable end word configuration ---
        String beginWord2 = "hit";
        String endWord2 = "cog";
        List<String> wordList2 = Arrays.asList("hot", "dot", "dog", "lot", "log");

        System.out.println("===============================================================");
        System.out.println("RUNNING TEST 2: beginWord = \"" + beginWord2 + "\", endWord = \"" + endWord2 + "\" (Unreachable)");
        System.out.println("===============================================================");
        List<List<String>> result2 = solution.findLadders(beginWord2, endWord2, wordList2);
        System.out.println("Shortest Paths Output: " + result2);
        System.out.println();
    }
}
