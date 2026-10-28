package datastructures.problems;

/**
 * LeetCode 62: Unique Paths
 * <p>
 * A robot is located at the top-left corner of an m x n grid.
 * The robot can only move either down or right at any point in time.
 * The robot is trying to reach the bottom-right corner of the grid.
 * Returns the number of possible unique paths that the robot can take to reach the bottom-right corner.
 *
 * <a href="https://algo.monster/liteproblems/62">https://algo.monster/liteproblems/62</a>
 * <a href="https://neetcode.io/solutions/unique-paths">https://neetcode.io/solutions/unique-paths</a>
 */
public class UniquePaths {
    /**
     * Finds the total number of unique paths using a recursive brute-force DFS.
     *
     * @param m Number of rows
     * @param n Number of columns
     * @return The number of unique paths to the bottom-right corner
     */
    public int uniquePaths(int m, int n) {
        return dfs(0, 0, m, n);
    }

    /**
     * Brute force, Recursive DFS helper function to explore all possible right and down paths.
     *
     * @param i Current row index
     * @param j Current column index
     * @param m Total rows in the grid
     * @param n Total columns in the grid
     * @return Number of valid paths from the current cell to the destination
     */
    public int dfs(int i, int j, int m, int n) {
        // Base case: if we have reached the bottom-right destination cell, return 1 valid path
        if (i == (m - 1) && j == (n - 1)) {
            return 1;
        }

        // Base case: if we go out of bounds of the grid, return 0 paths
        if (i >= m || j >= n) {
            return 0;
        }

        // Recursive choices: explore moving right (j + 1) and moving down (i + 1)
        return dfs(i, j + 1, m, n) +
                dfs(i + 1, j, m, n);
    }

    // Main method to test the solution with sample test cases
    public static void main(String[] args) {
        UniquePaths solution = new UniquePaths();

        // --- TEST CASE 1: Small grid for brute-force execution ---
        int m1 = 3, n1 = 7;
        System.out.println("===============================================================");
        System.out.println("RUNNING TEST 1: m = " + m1 + ", n = " + n1);
        System.out.println("===============================================================");
        System.out.println("Unique Paths Output: " + solution.uniquePaths(m1, n1));
        System.out.println();

        // --- TEST CASE 2: 3x2 Grid ---
        int m2 = 3, n2 = 2;
        System.out.println("===============================================================");
        System.out.println("RUNNING TEST 2: m = " + m2 + ", n = " + n2);
        System.out.println("===============================================================");
        System.out.println("Unique Paths Output: " + solution.uniquePaths(m2, n2));
        System.out.println();
    }
}
