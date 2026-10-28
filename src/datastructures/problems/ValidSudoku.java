package datastructures.problems;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * LeetCode 36: Valid Sudoku
 * <p>
 * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be validated
 * according to the following rules:
 * 1. Each row must contain the digits 1-9 without repetition.
 * 2. Each column must contain the digits 1-9 without repetition.
 * 3. Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 without repetition.
 *
 * <a href="https://neetcode.io/solutions/valid-sudoku">https://neetcode.io/solutions/valid-sudoku</a>
 * <a href="https://algo.monster/liteproblems/36">https://algo.monster/liteproblems/36</a>
 */
public class ValidSudoku {
    /**
     * APPROACH 1: Brute Force
     * <p>
     * Iterates through every row, column, and 3x3 sub-box independently, using a fresh HashSet
     * for each individual constraint to check for duplicate numbers.
     * <p>
     * Time Complexity: O(1) for a fixed 9x9 board (O(n^2) generally)
     * Space Complexity: O(1)
     */
    public boolean isValidSudokuBruteForce(char[][] board) {
        // 1. Validate all rows
        for (int r = 0; r < 9; r++) {
            Set<Character> seen = new HashSet<>();
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') {
                    continue;
                }
                if (seen.contains(board[r][c])) {
                    return false;
                }
                seen.add(board[r][c]);
            }
        }

        // 2. Validate all columns
        for (int c = 0; c < 9; c++) {
            Set<Character> seen = new HashSet<>();
            for (int r = 0; r < 9; r++) {
                if (board[r][c] == '.') {
                    continue;
                }
                if (seen.contains(board[r][c])) {
                    return false;
                }
                seen.add(board[r][c]);
            }
        }

        // 3. Validate all 3x3 sub-boxes
        for (int box = 0; box < 9; box++) {
            Set<Character> seen = new HashSet<>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int r = (box / 3) * 3 + i;
                    int c = (box % 3) * 3 + j;
                    if (board[r][c] == '.') {
                        continue;
                    }
                    if (seen.contains(board[r][c])) {
                        return false;
                    }
                    seen.add(board[r][c]);
                }
            }
        }

        return true;
    }

    /**
     * APPROACH 2: Hash Set (One Pass) using Maps of Sets
     * <p>
     * Maintains three separate maps of HashSets (`rows`, `cols`, and `boxes`) to track
     * which numbers have been seen across the entire grid simultaneously in a single traversal.
     * <p>
     * Explanation:
     * - As we visit each cell `(r, c)` once, we compute its corresponding sub-box index
     * using the formula `(r / 3) * 3 + (c / 3)`.
     * - We check if the current digit `val` already exists in the row's set, column's set,
     * or sub-box's set. If it does, a duplicate is found and we instantly return `false`.
     * - Otherwise, we initialize the sets lazily via `putIfAbsent` and record the digit.
     * <p>
     * Time Complexity: O(1) for a fixed 9x9 board (O(n^2) generally)
     * Space Complexity: O(1)
     */
    public boolean isValidSudokuOnePassHashSet(char[][] board) {
        Map<Integer, Set<Character>> rows = new HashMap<>();
        Map<Integer, Set<Character>> cols = new HashMap<>();
        Map<Integer, Set<Character>> boxes = new HashMap<>();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char val = board[r][c];
                if (val == '.') {
                    continue;
                }

                // Compute the unique 3x3 sub-box identifier (0 to 8)
                int boxIndex = (r / 3) * 3 + (c / 3);

                // LAZY INITIALIZATION:
                // Ensure a tracking HashSet exists for the current row, column, and box index
                // before we attempt to query them. This prevents a NullPointerException
                // on the first encounter of any new row, column, or sub-box.
                rows.putIfAbsent(r, new HashSet<>());
                cols.putIfAbsent(c, new HashSet<>());
                boxes.putIfAbsent(boxIndex, new HashSet<>());

                // Check for duplicate presence in any of the three constraints.
                // Since putIfAbsent guaranteed the sets exist, .get() will never return null.
                if (rows.get(r).contains(val) ||
                        cols.get(c).contains(val) ||
                        boxes.get(boxIndex).contains(val)) {
                    return false;
                }

                // Record the digit in all respective tracking sets for future lookups
                rows.get(r).add(val);
                cols.get(c).add(val);
                boxes.get(boxIndex).add(val);
            }
        }

        return true;
    }

    /**
     * APPROACH 3: Single-Pass Encoded Hash Set (Cleanest Solution) 🏆
     * <p>
     * Iterates through the board once. For each non-empty cell, it generates unique string
     * identifiers encoding the value's presence in its row, column, and 3x3 sub-box.
     * <p>
     * Box Mapping Explanation:
     * Integer division `(i / 3)` and `(j / 3)` maps any row/column index (0-8) into a 3x3 box
     * grid index (0-2). For example, a digit '5' at row 4, col 5 generates the identifier
     * "5@box11", ensuring uniqueness across the specific sub-box without collision.
     * <p>
     * Time Complexity: O(1) for a fixed 9x9 board (O(n^2) generally)
     * Space Complexity: O(1)
     */
    public boolean isValidSudokuHashMap(char[][] board) {
        Set<String> seen = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c == '.') {
                    continue;
                }

                // Attempt to add encoded strings to the set; Set.add() returns false if the element already exists
                if (!seen.add(c + "@row" + i) ||
                        !seen.add(c + "@col" + j) ||
                        !seen.add(c + "@box" + (i / 3) + (j / 3))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Utility tool to print test configuration headers.
     */
    private static void printHeader(String title) {
        System.out.println("===============================================================");
        System.out.println("RUNNING: " + title);
        System.out.println("===============================================================");
    }

    // Main method to test all solutions with valid and invalid test cases
    public static void main(String[] args) {
        ValidSudoku solution = new ValidSudoku();

        // --- TEST CASE 1: Valid Sudoku Board ---
        char[][] validBoard = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        printHeader("Test Case 1: Valid Board");
        System.out.println("Brute Force Output:             " + solution.isValidSudokuBruteForce(validBoard));
        System.out.println("One-Pass Hash Sets Output:      " + solution.isValidSudokuOnePassHashSet(validBoard));
        System.out.println("Single-Pass Encoded Set Output 🏆: " + solution.isValidSudokuHashMap(validBoard));
        System.out.println();
    }
}
