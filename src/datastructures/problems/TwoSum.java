package datastructures.problems;

import java.util.HashMap;

public class TwoSum {
    public int[] searchWithTwoPointers(int[] arr, int targetSum) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int currentSum = arr[left] + arr[right];
            if (currentSum == targetSum)
                return new int[]{left, right}; // found the pair

            if (targetSum > currentSum)
                left++; // we need a pair with a bigger sum
            else
                right--; // we need a pair with a smaller sum
        }
        return new int[]{-1, -1};
    }

    public int[] searchWithMap(int[] arr, int targetSum) {
        HashMap<Integer, Integer> nums = new HashMap<>(); // to store numbers and indices
        for (int i = 0; i < arr.length; i++) {
            if (nums.containsKey(targetSum - arr[i]))
                return new int[]{nums.get(targetSum - arr[i]), i};
            else
                nums.put(arr[i], i); // put the number and its index in the map
        }
        return new int[]{-1, -1}; // pair not found
    }

    public static void main(String[] args) {
        TwoSum twoSum = new TwoSum();

        int[] resultWithTwoPointers = twoSum.searchWithTwoPointers(new int[]{1, 2, 3, 4, 6}, 6);
        System.out.println("Pair with target sum: [" + resultWithTwoPointers[0] + ", " + resultWithTwoPointers[1] + "]");
        resultWithTwoPointers = twoSum.searchWithTwoPointers(new int[]{2, 5, 9, 11}, 11);
        System.out.println("Pair with target sum: [" + resultWithTwoPointers[0] + ", " + resultWithTwoPointers[1] + "]");

        int[] resultWithMap = twoSum.searchWithMap(new int[]{1, 2, 3, 4, 6}, 6);
        System.out.println("Pair with target sum: [" + resultWithMap[0] + ", " + resultWithMap[1] + "]");
        resultWithMap = twoSum.searchWithMap(new int[]{2, 5, 9, 11}, 11);
        System.out.println("Pair with target sum: [" + resultWithMap[0] + ", " + resultWithMap[1] + "]");
    }
}
