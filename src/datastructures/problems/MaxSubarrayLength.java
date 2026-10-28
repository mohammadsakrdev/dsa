package datastructures.problems;

import java.util.HashMap;
import java.util.Map;

public class MaxSubarrayLength {
    public int maxSubarrayLength(int[] nums, int k) {
        int maxLength = 0;
        int left = 0;
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int right = 0; right < nums.length; right++) {
            int currentNum = nums[right];
            // Add the current element to the frequency map
            frequencyMap.put(currentNum, frequencyMap.getOrDefault(currentNum, 0) + 1);

            // Shrink the window from the left if the current element's frequency exceeds k
            while (frequencyMap.get(currentNum) > k) {
                int leftNum = nums[left];
                frequencyMap.put(leftNum, frequencyMap.get(leftNum) - 1);
                left++;
            }

            // Calculate and update the maximum length of a valid subarray
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        MaxSubarrayLength maxSubarrayLength = new MaxSubarrayLength();
        // Sample Input
        int arr[] = {5, 10, 5, 10, 5, 10};
        int K = 3;
        System.out.print(maxSubarrayLength.maxSubarrayLength(arr, K));
    }
}
