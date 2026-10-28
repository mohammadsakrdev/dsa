package datastructures.problems;

import java.util.Arrays;

/**
 * LeetCode 162: Find Peak Element
 * <p>
 * A peak element is an element that is strictly greater than its neighbors.
 * Given a 0-indexed integer array nums, find a peak element, and return its index.
 * If the array contains multiple peaks, return the index to any of the peaks.
 *
 * <a href="https://neetcode.io/solutions/find-peak-element">https://neetcode.io/solutions/find-peak-element</a>
 * <a href="https://algo.monster/liteproblems/162">https://algo.monster/liteproblems/162</a>
 */
public class FindPeakElement {
    /**
     * APPROACH 1: Brute Force
     * <p>
     * Scans from left to right. The first time we find an element greater than its
     * next neighbor, we return its index. If no such element exists, the last element
     * is the peak.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int findPeakElementBruteForce(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                return i;
            }
        }
        return nums.length - 1;
    }

    /**
     * APPROACH 2: Binary Search (Iterative)
     * <p>
     * Uses binary search by comparing the midpoint with its neighbors to determine
     * which half contains a peak.
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public int findPeakElementBinarySearch(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (m > 0 && nums[m] < nums[m - 1]) {
                r = m - 1;
            } else if (m < nums.length - 1 && nums[m] < nums[m + 1]) {
                l = m + 1;
            } else {
                return m;
            }
        }
        return -1;
    }

    /**
     * APPROACH 3: Recursive Binary Search
     * <p>
     * Recursively narrows the search space by comparing the midpoint with its right neighbor.
     * Time Complexity: O(log n)
     * Space Complexity: O(log n) due to the recursion call stack
     */
    public int findPeakElementRecursive(int[] nums) {
        return binarySearchRecursive(nums, 0, nums.length - 1);
    }

    private int binarySearchRecursive(int[] nums, int l, int r) {
        if (l == r) {
            return l;
        }
        int m = l + (r - l) / 2;
        if (nums[m] > nums[m + 1]) {
            return binarySearchRecursive(nums, l, m);
        }
        return binarySearchRecursive(nums, m + 1, r);
    }

    /**
     * APPROACH 4: Optimal Binary Search 🏆
     * <p>
     * Simplified binary search using `l < r` condition. Eliminates extra neighbor checks
     * inside the loop, directly converging onto a peak index.
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     */
    public int findPeakElementOptimal(int[] nums) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int m = (l + r) >> 1;
            if (nums[m] > nums[m + 1]) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    /**
     * Utility tool to print test configuration headers.
     */
    private static void printHeader(String title) {
        System.out.println("===============================================================");
        System.out.println("RUNNING: " + title);
        System.out.println("===============================================================");
    }

    // Main method to test all solutions with sample test cases
    public static void main(String[] args) {
        FindPeakElement solution = new FindPeakElement();

        int[] nums = {1, 2, 3, 1};
        printHeader("Input Array: " + Arrays.toString(nums));

        System.out.println("Brute Force Output:             " + solution.findPeakElementBruteForce(nums));
        System.out.println("Binary Search Output:           " + solution.findPeakElementBinarySearch(nums));
        System.out.println("Recursive Binary Search Output: " + solution.findPeakElementRecursive(nums));
        System.out.println("Optimal Binary Search Output 🏆: " + solution.findPeakElementOptimal(nums));
        System.out.println();
    }
}
