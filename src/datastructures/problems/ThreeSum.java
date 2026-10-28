package datastructures.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ThreeSum {
    // https://neetcode.io/solutions/3sum
    // Brute Force
    public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> tmp = Arrays.asList(nums[i], nums[j], nums[k]);
                        res.add(tmp);
                    }
                }
            }
        }
        return new ArrayList<>(res);
    }

    // Hash Map
    public List<List<Integer>> threeSumWithHashMap(int[] nums) {
        Arrays.sort(nums);
        Map<Integer, Integer> count = new HashMap<>();
        for (int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            count.put(nums[i], count.get(nums[i]) - 1);
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            for (int j = i + 1; j < nums.length; j++) {
                count.put(nums[j], count.get(nums[j]) - 1);
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                int target = -(nums[i] + nums[j]);
                if (count.getOrDefault(target, 0) > 0) {
                    res.add(Arrays.asList(nums[i], nums[j], target));
                }
            }

            for (int j = i + 1; j < nums.length; j++) {
                count.put(nums[j], count.get(nums[j]) + 1);
            }
        }
        return res;
    }

    public List<List<Integer>> threeSumWithTowPointers(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) break;
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int l = i + 1, r = nums.length - 1;
            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum > 0) {
                    r--;
                } else if (sum < 0) {
                    l++;
                } else {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    l++;
                    r--;
                    while (l < r && nums[l] == nums[l - 1]) {
                        l++;
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        ThreeSum solver = new ThreeSum();
        // Define a map of distinct test cases to trace coverage
        Map<String, int[]> testCases = new HashMap<>();
        testCases.put("Standard Case (with duplicates and negatives)", new int[]{-1, 0, 1, 2, -1, -4});
        testCases.put("No Valid Triplets Possible", new int[]{0, 1, 1});
        testCases.put("Multiple Identity Triplets (Zeros)", new int[]{0, 0, 0, 0});
        testCases.put("Duplicate Bound Handling", new int[]{-2, 0, 0, 2, 2});

        // Run each test case across all three implementations
        for (Map.Entry<String, int[]> entry : testCases.entrySet()) {
            System.out.println("==================================================");
            System.out.println("TEST CASE: " + entry.getKey());
            System.out.println("Input Array: " + Arrays.toString(entry.getValue()));
            System.out.println("==================================================");

            // Clone arrays since each method sorts the array in-place
            int[] input1 = entry.getValue().clone();
            int[] input2 = entry.getValue().clone();
            int[] input3 = entry.getValue().clone();

            System.out.println("1. Brute Force Result:       " + solver.threeSum(input1));
            System.out.println("2. Hash Map Result:          " + solver.threeSumWithHashMap(input2));
            System.out.println("3. Two Pointers Result:      " + solver.threeSumWithTowPointers(input3));
            System.out.println();
        }

    }
}
