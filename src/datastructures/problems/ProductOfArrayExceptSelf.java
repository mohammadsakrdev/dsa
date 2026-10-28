package datastructures.problems;

import java.util.Arrays;

public class ProductOfArrayExceptSelf {

    /**
     * METHOD 1: Brute Force Approach
     * <p>
     * For every element at index i, use a nested loop to multiply all other elements
     * in the array except the one at index i.
     * Time Complexity: O(n^2)
     * Space Complexity: O(1) auxiliary space (excluding the output array)
     */
    public int[] productExceptSelfBruteForce(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];

        // For each index i, compute the product of all elements except nums[i]
        for (int i = 0; i < n; i++) {
            int currentProduct = 1;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    currentProduct *= nums[j];
                }
            }
            answer[i] = currentProduct;
        }

        return answer;
    }

    /**
     * METHOD 2: Using Prefix and Suffix Product Arrays
     * <p>
     * Computes prefix products (product of all elements to the left) and suffix products
     * (product of all elements to the right) into separate helper arrays, then combines them.
     * Time Complexity: O(n)
     * Space Complexity: O(n) for the prefix and suffix arrays
     */
    public int[] productExceptSelfPrefixSuffix(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n];
        int[] suffix = new int[n];
        int[] answer = new int[n];

        // Build prefix products: prefix[i] contains the product of all elements to the left of i
        prefix[0] = 1;
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] * nums[i - 1];
        }

        // Build suffix products: suffix[i] contains the product of all elements to the right of i
        suffix[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            suffix[i] = suffix[i + 1] * nums[i + 1];
        }

        // Combine prefix and suffix products for each index
        for (int i = 0; i < n; i++) {
            answer[i] = prefix[i] * suffix[i];
        }

        return answer;
    }

    /**
     * METHOD 3: Space Optimized Method 2 🏆
     * <p>
     * Optimizes Method 3 by using the output array itself to store the prefix products,
     * and a running variable to compute suffix products on the fly during a reverse pass.
     * Time Complexity: O(n)
     * Space Complexity: O(1) auxiliary space (not counting the output array)
     */
    public int[] productExceptSelfSpaceOptimized(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];

        // Step 1: answer[i] stores the product of all elements to the left of i
        answer[0] = 1;
        for (int i = 1; i < n; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }

        // Step 2: Maintain a running suffix product and multiply it with the left products in-place
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            answer[i] = answer[i] * rightProduct;
            rightProduct *= nums[i];
        }

        return answer;
    }

    /**
     * Utility tool to print test configuration headers.
     */
    private static void printHeader(String title) {
        System.out.println("===============================================================");
        System.out.println("RUNNING: " + title);
        System.out.println("===============================================================");
    }

    // Main method to test all three implementations with sample test cases
    public static void main(String[] args) {
        ProductOfArrayExceptSelf solution = new ProductOfArrayExceptSelf();

        int[] nums = {1, 2, 3, 4};
        printHeader("Input Array: " + Arrays.toString(nums));

        System.out.println("Brute Force Output (Method 1):        " +
                Arrays.toString(solution.productExceptSelfBruteForce(nums)));
        System.out.println("Prefix/Suffix Arrays Output (Method 3): " +
                Arrays.toString(solution.productExceptSelfPrefixSuffix(nums)));
        System.out.println("Space Optimized Output (Method 4 🏆):   " +
                Arrays.toString(solution.productExceptSelfSpaceOptimized(nums)));
        System.out.println();
    }
}
