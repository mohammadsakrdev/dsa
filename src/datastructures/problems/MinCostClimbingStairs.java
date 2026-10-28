package datastructures.problems;

import java.util.Arrays;

/**
 * LeetCode 746: Min Cost Climbing Stairs
 * <p>
 * You are given an integer array cost where cost[i] is the cost of taking the i-th step on a staircase.
 * Once you pay the cost, you can either climb one or two steps. You can start from either step 0 or step 1.
 * Returns the minimum total cost to reach the top of the floor.
 *
 * <a href="https://algo.monster/liteproblems/746">https://algo.monster/liteproblems/746</a>
 * <a https://neetcode.io/solutions/min-cost-climbing-stairs">https://neetcode.io/solutions/min-cost-climbing-stairs</a>
 */
public class MinCostClimbingStairs {

    // Memoization array to store computed results for each stair index
    private Integer[] memo;
    // Reference to the input cost array
    private int[] cost;

    /**
     * APPROACH 1: Top-Down Dynamic Programming (Memoization / DFS)
     * <p>
     * Recursively explores choices from step 0 and step 1, caching subproblem results
     * in a memo array to avoid redundant recursive calculations.
     */
    public int minCostClimbingStairsMemo(int[] cost) {
        this.cost = cost;
        this.memo = new Integer[cost.length];

        // Try starting from both step 0 and step 1, return the minimum
        return Math.min(dfs(0), dfs(1));
    }

    /**
     * Recursive helper function with memoization to calculate minimum cost
     * from a given stair index to the top.
     */
    private int dfs(int currentIndex) {
        // Base case: if we've reached or passed the top, no additional cost
        if (currentIndex >= cost.length) {
            return 0;
        }

        // Check if we've already computed the result for this index
        if (memo[currentIndex] == null) {
            // Calculate minimum cost: current stair cost + minimum of next two possible steps
            int oneStep = dfs(currentIndex + 1);
            int twoSteps = dfs(currentIndex + 2);
            memo[currentIndex] = cost[currentIndex] + Math.min(oneStep, twoSteps);
        }

        return memo[currentIndex];
    }

    /**
     * APPROACH 2: Dynamic Programming (Space-Optimized In-Place Modification)
     * <p>
     * Iterates backwards from the third-last step to the beginning, modifying the input
     * array in-place to achieve O(1) auxiliary space complexity.
     */
    public int minCostClimbingStairsSpaceOptimized(int[] cost) {
        // Start from the third-last step and move backwards towards the start
        for (int i = cost.length - 3; i >= 0; i--) {
            // Update the current step cost by adding the minimum of the next 1 or 2 steps ahead
            cost[i] += Math.min(cost[i + 1], cost[i + 2]);
        }

        // Since you can choose to start at either index 0 or index 1, return the minimum of both
        return Math.min(cost[0], cost[1]);
    }

    // Main method to test both solutions with sample test cases
    public static void main(String[] args) {
        MinCostClimbingStairs solution = new MinCostClimbingStairs();

        // --- TEST CASE 1: Standard cost array ---
        int[] cost1 = {10, 15, 20};
        System.out.println("===============================================================");
        System.out.println("RUNNING TEST 1: cost = " + Arrays.toString(cost1));
        System.out.println("===============================================================");
        System.out.println("Memoization DFS Output:       " + solution.minCostClimbingStairsMemo(cost1.clone()));
        System.out.println("Space-Optimized DP Output:    " + solution.minCostClimbingStairsSpaceOptimized(cost1.clone()));
        System.out.println();

        // --- TEST CASE 2: Longer cost array ---
        int[] cost2 = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        System.out.println("===============================================================");
        System.out.println("RUNNING TEST 2: cost = " + Arrays.toString(cost2));
        System.out.println("===============================================================");
        System.out.println("Memoization DFS Output:       " + solution.minCostClimbingStairsMemo(cost2.clone()));
        System.out.println("Space-Optimized DP Output:    " + solution.minCostClimbingStairsSpaceOptimized(cost2.clone()));
        System.out.println();
    }
}
