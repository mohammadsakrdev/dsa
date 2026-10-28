package datastructures.problems;

import java.util.Arrays;

/**
 * LeetCode 11: Container With Most Water
 *
 * Given n non-negative integers height where each represents a point at coordinate (i, height[i]).
 * Find two lines that together with the x-axis form a container, such that the container contains the most water.
 *
 * <a href="https://neetcode.io/solutions/container-with-most-water">https://neetcode.io/solutions/container-with-most-water</a>
 * <a href="https://algo.monster/liteproblems/11">https://algo.monster/liteproblems/11</a>
 */
public class ContainerWithMostWater {

    /**
     * APPROACH 1: Brute Force
     *
     * Tries every possible pair of lines and computes the area they form,
     * keeping track of the maximum area found.
     * Time Complexity: O(n^2)
     * Space Complexity: O(1)
     */
    public int maxAreaBruteForce(int[] heights) {
        int res = 0;

        // Check every possible pair (i, j) where j > i
        for (int i = 0; i < heights.length; i++) {
            for (int j = i + 1; j < heights.length; j++) {
                // Area = width (j - i) * min height between the two bars
                int area = getArea(heights, i, j);
                res = Math.max(res, area);
            }
        }

        return res;
    }

    /**
     * APPROACH 2: Two Pointers (Greedy)
     *
     * Starts with the widest container using pointers at both ends and moves
     * the pointer with the shorter height inward to search for larger areas.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int maxAreaTwoPointers(int[] heights) {
        int l = 0;
        int r = heights.length - 1;
        int res = 0;

        // Iterate while the two pointers haven't met
        while (l < r) {
            // Calculate the water area: width multiplied by the minimum height
            int area = getArea(heights, l, r);
            res = Math.max(res, area);

            // Greedily move the pointer pointing to the shorter line inward
            if (heights[l] <= heights[r]) {
                l++;
            } else {
                r--;
            }
        }

        return res;
    }

    private static int getArea(int[] heights, int l, int r) {
        return Math.min(heights[l], heights[r]) * (r - l);
    }

    /**
     * Utility tool to print test configuration headers.
     */
    private static void printHeader(String title) {
        System.out.println("===============================================================");
        System.out.println("RUNNING: " + title);
        System.out.println("===============================================================");
    }

    // Main method to test both implementations with sample test cases
    public static void main(String[] args) {
        ContainerWithMostWater solution = new ContainerWithMostWater();

        // --- TEST CASE 1 ---
        int[] height1 = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        printHeader("Test Case 1: heights = " + Arrays.toString(height1));
        System.out.println("Brute Force Output:     " + solution.maxAreaBruteForce(height1));
        System.out.println("Two Pointers Output:    " + solution.maxAreaTwoPointers(height1));
        System.out.println();

        // --- TEST CASE 2 ---
        int[] height2 = {1, 1};
        printHeader("Test Case 2: heights = " + Arrays.toString(height2));
        System.out.println("Brute Force Output:     " + solution.maxAreaBruteForce(height2));
        System.out.println("Two Pointers Output:    " + solution.maxAreaTwoPointers(height2));
        System.out.println();
    }

}

