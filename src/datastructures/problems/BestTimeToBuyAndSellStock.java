package datastructures.problems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 121: Best Time to Buy and Sell Stock
 * Calculates the maximum single-transaction profit achievable from a given price index.
 * <a href="https://neetcode.io/solutions/best-time-to-buy-and-sell-stock">...</a>
 */
public class BestTimeToBuyAndSellStock {

    /**
     * APPROACH 1: Brute Force
     * Checks every single possible valid combination of buy and sell days.
     *
     * Time Complexity: O(N²) - Nested loops checking all element pairs.
     * Space Complexity: O(1) - Constant operational allocations.
     */
    public int maxProfitBruteForce(int[] prices) {
        int maxProfit = 0;

        // Loop through each day 'i' acting as a prospective purchase day
        for (int i = 0; i < prices.length; i++) {
            int buyPrice = prices[i];

            // Loop through future days 'j' acting as potential sales windows
            for (int j = i + 1; j < prices.length; j++) {
                int sellPrice = prices[j];

                // Track the rolling maximum profit margins discovered
                maxProfit = Math.max(maxProfit, sellPrice - buyPrice);
            }
        }
        return maxProfit;
    }

    /**
     * APPROACH 2: Two Pointers / Sliding Window
     * Employs left (buy) and right (sell) boundary pointers to step through chronologically.
     *
     * Time Complexity: O(N) - Single pass scan across the sequence.
     * Space Complexity: O(1) - Primitive pointers only.
     */
    public int maxProfitTwoPointers(int[] prices) {
        int left = 0;  // Left pointer tracks our optimal historical buy candidate
        int right = 1; // Right pointer evaluates future profit taking positions
        int maxProfit = 0;

        while (right < prices.length) {
            // Case A: Profitable transaction found
            if (prices[left] < prices[right]) {
                int runningProfit = prices[right] - prices[left];
                maxProfit = Math.max(maxProfit, runningProfit);
            } else {
                // Case B: Found a lower valley floor price point.
                // Shift the buy day directly to 'right' because it offers a better price foundation.
                left = right;
            }
            // Always step the timeline forward by expanding the window
            right++;
        }
        return maxProfit;
    }

    /**
     * APPROACH 3: Dynamic Programming / Greedy (One Pass)
     * Continually maintains the lowest purchase price floor seen so far while moving forward.
     *
     * Time Complexity: O(N) - Linear progression traversal.
     * Space Complexity: O(1) - Tracks historical minima and max margins.
     */
    public int maxProfitDP(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        int maxProfit = 0;
        int minBuyPrice = prices[0]; // Anchor minimum buy threshold on day 1

        for (int sellPrice : prices) {
            // 1. Calculate potential gains if we sold today using historical valley min
            maxProfit = Math.max(maxProfit, sellPrice - minBuyPrice);

            // 2. Refresh the absolute lower buy limit seen so far
            minBuyPrice = Math.min(minBuyPrice, sellPrice);
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        BestTimeToBuyAndSellStock solver = new BestTimeToBuyAndSellStock();

        // Establish a diverse matrix of analytical test inputs
        Map<String, int[]> testSuite = new HashMap<>();
        testSuite.put("Standard Fluctuating Market (Expected: 6)", new int[]{10, 1, 5, 6, 7, 1});
        testSuite.put("Monotonically Decreasing Crash (Expected: 0)", new int[]{10, 8, 7, 5, 2});
        testSuite.put("Immediate Rally from Day 1 (Expected: 5)", new int[]{1, 6, 2, 4, 3});
        testSuite.put("Flat Static Marketplace (Expected: 0)", new int[]{5, 5, 5, 5, 5});
        testSuite.put("Single-Day Data Limit (Expected: 0)", new int[]{3});

        // Run validation tracks across every approach variant
        for (Map.Entry<String, int[]> testCase : testSuite.entrySet()) {
            System.out.println("=================================================================");
            System.out.println("SCENARIO: " + testCase.getKey());
            System.out.println("Prices Sequence: " + Arrays.toString(testCase.getValue()));
            System.out.println("=================================================================");

            int[] inputData = testCase.getValue();

            // Note: Unlike grid mutations, these operations do not overwrite
            // array states in place, but calling clean inputs separates state scope.
            System.out.println("1. Brute Force Calculation Output: " + solver.maxProfitBruteForce(inputData.clone()));
            System.out.println("2. Two Pointers Calculation Output: " + solver.maxProfitTwoPointers(inputData.clone()));
            System.out.println("3. Dynamic Prog. Calculation Output: " + solver.maxProfitDP(inputData.clone()));
            System.out.println();
        }
    }
}
