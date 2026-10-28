package datastructures.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * LeetCode 56: Merge Intervals
 * <p>
 * Given an array of intervals where intervals[i] = [start_i, end_i], merge all overlapping
 * intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.
 *
 * <a href="https://neetcode.io/solutions/merge-intervals">https://neetcode.io/solutions/merge-intervals</a>
 */
public class MergeIntervals {

    /**
     * APPROACH 1: Sorting and Linear Scan (Optimal Sorting Approach) 🏆
     * <p>
     * Sorts the intervals by their starting values and merges overlapping ranges
     * with the most recently added interval in the output collection.
     * <p>
     * Time Complexity: O(n log n) due to sorting
     * Space Complexity: O(n) to store the output collection
     */
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> output = new ArrayList<>();
        output.add(intervals[0]);

        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];
            int lastEnd = output.get(output.size() - 1)[1];

            if (start <= lastEnd) {
                output.get(output.size() - 1)[1] = Math.max(lastEnd, end);
            } else {
                output.add(new int[]{start, end});
            }
        }
        return output.toArray(new int[output.size()][]);
    }

    /**
     * APPROACH 2: Sweep Line Algorithm using TreeMap
     * <p>
     * Uses a TreeMap to track interval start points (+1) and end points (-1) in sorted order.
     * It scans through the timeline, keeping track of active overlapping intervals via a counter (`have`).
     * <p>
     * Time Complexity: O(n log n) due to TreeMap insertions and ordered traversal
     * Space Complexity: O(n) to store the coordinate points in the map
     */
    public int[][] mergeSweepLine(int[][] intervals) {
        TreeMap<Integer, Integer> map = new TreeMap<>();

        for (int[] interval : intervals) {
            map.put(interval[0], map.getOrDefault(interval[0], 0) + 1);
            map.put(interval[1], map.getOrDefault(interval[1], 0) - 1);
        }

        List<int[]> res = new ArrayList<>();
        int have = 0;
        int[] interval = new int[2];

        for (int point : map.keySet()) {
            if (have == 0) {
                interval[0] = point;
            }
            have += map.get(point);
            if (have == 0) {
                interval[1] = point;
                res.add(new int[]{interval[0], interval[1]});
            }
        }

        return res.toArray(new int[res.size()][]);
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
        MergeIntervals solution = new MergeIntervals();

        int[][] intervals = {
                {1, 3},
                {2, 6},
                {8, 10},
                {15, 18}
        };

        printHeader("Test Case: Intervals Merging");

        int[][] sortedResult = solution.merge(intervals);
        System.out.print("Sorted Scan Output 🏆:              [");
        for (int i = 0; i < sortedResult.length; i++) {
            System.out.print(Arrays.toString(sortedResult[i]) + (i == sortedResult.length - 1 ? "" : ", "));
        }
        System.out.println("]");

        int[][] sweepResult = solution.mergeSweepLine(intervals);
        System.out.print("Sweep Line Output (TreeMap):        [");
        for (int i = 0; i < sweepResult.length; i++) {
            System.out.print(Arrays.toString(sweepResult[i]) + (i == sweepResult.length - 1 ? "" : ", "));
        }
        System.out.println("]");
    }
}
