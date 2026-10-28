package datastructures.problems;

/**
 * 0/1 Knapsack Problem (Recursive Brute-Force Approach)
 * <p>
 * Given two integer arrays representing profits and weights, and a maximum capacity,
 * finds the maximum profit that can be achieved without exceeding the capacity
 * (where each item can be chosen at most once).
 */
public class Knapsack {
    // =========================================================================
    // APPROACH 1: Recursive Brute-Force
    // =========================================================================
    public int solveKnapsackBruteForce(int[] profits, int[] weights, int capacity) {
        return this.knapsackRecursive(profits, weights, capacity, 0);
    }

    private int knapsackRecursive(int[] profits, int[] weights, int capacity, int currentIndex) {
        // base checks
        if (capacity <= 0 || currentIndex >= profits.length)
            return 0;

        // recursive call after choosing the element at the currentIndex
        // if the weight of the element at currentIndex exceeds the capacity, we shouldn't process this
        int profit1 = 0;
        if (weights[currentIndex] <= capacity)
            profit1 = profits[currentIndex] + knapsackRecursive(profits, weights,
                    capacity - weights[currentIndex], currentIndex + 1);

        // recursive call after excluding the element at the currentIndex
        int profit2 = knapsackRecursive(profits, weights, capacity, currentIndex + 1);

        return Math.max(profit1, profit2);
    }

    // =========================================================================
    // APPROACH 2: Top-Down Dynamic Programming (Memoization)
    // =========================================================================
    public int solveKnapsackMemo(int[] profits, int[] weights, int capacity) {
        Integer[][] dp = new Integer[profits.length][capacity + 1];
        return this.knapsackRecursive(dp, profits, weights, capacity, 0);
    }

    private int knapsackRecursive(Integer[][] dp, int[] profits, int[] weights, int capacity,
                                  int currentIndex) {

        // base checks
        if (capacity <= 0 || currentIndex >= profits.length)
            return 0;

        // if we have already solved a similar problem, return the result from memory
        if (dp[currentIndex][capacity] != null)
            return dp[currentIndex][capacity];

        // recursive call after choosing the element at the currentIndex
        // if the weight of the element at currentIndex exceeds the capacity, we shouldn't process this
        int profit1 = 0;
        if (weights[currentIndex] <= capacity)
            profit1 = profits[currentIndex] + knapsackRecursive(dp, profits, weights,
                    capacity - weights[currentIndex], currentIndex + 1);

        // recursive call after excluding the element at the currentIndex
        int profit2 = knapsackRecursive(dp, profits, weights, capacity, currentIndex + 1);

        dp[currentIndex][capacity] = Math.max(profit1, profit2);
        return dp[currentIndex][capacity];
    }

    // Main method to test both implementations
    public static void main(String[] args) {
        Knapsack ks = new Knapsack();
        int[] profits = {1, 6, 10, 16};
        int[] weights = {1, 2, 3, 5};

        System.out.println("--- Brute-Force Approach ---");
        int maxProfit = ks.solveKnapsackBruteForce(profits, weights, 7);
        System.out.println("Total knapsack profit ---> " + maxProfit);
        maxProfit = ks.solveKnapsackBruteForce(profits, weights, 6);
        System.out.println("Total knapsack profit ---> " + maxProfit);

        System.out.println("\n--- Top-Down Memoization Approach ---");
        maxProfit = ks.solveKnapsackMemo(profits, weights, 7);
        System.out.println("Total knapsack profit ---> " + maxProfit);
        maxProfit = ks.solveKnapsackMemo(profits, weights, 6);
        System.out.println("Total knapsack profit ---> " + maxProfit);
    }
}
