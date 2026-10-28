package datastructures.problems;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode 104: Maximum Depth of Binary Tree
 * <p>
 * Finds the maximum depth of a binary tree, defined as the number of nodes
 * along the longest path from the root node down to the farthest leaf node.
 * <a href="https://neetcode.io/problems/maximum-depth-of-binary-tree/solution">NeetCode Solution</a>
 */
public class MaximumDepthOfBinaryTree {

    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * APPROACH 1: Recursive Depth First Search (DFS)
     * <p>
     * Recursively traverses subtrees, calculating depth as 1 plus the maximum
     * depth between the left and right child nodes.
     */
    public int maxDepthDFS(TreeNode root) {
        // Base Case: If the root is null, depth is 0
        if (root == null) {
            return 0;
        }

        // Recursively find the depth of left and right subtrees
        int leftDepth = maxDepthDFS(root.left);
        int rightDepth = maxDepthDFS(root.right);

        // Current depth is 1 (for the root) plus the max of subtrees
        return 1 + Math.max(leftDepth, rightDepth);
    }

    /**
     * APPROACH 2: Breadth First Search (BFS) / Level-Order Traversal
     * <p>
     * Employs a FIFO Queue to traverse the tree level by level, incrementing
     * the depth counter for each complete level processed.
     */
    public int maxDepthBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            // Process all nodes at the current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            // Increment depth after fully traversing a level
            depth++;
        }

        return depth;
    }

    /**
     * Utility tool to execute safe deep copies of binary trees.
     * Prevents in-place mutation overlap between successive test runs.
     */
    private static TreeNode cloneTree(TreeNode original) {
        if (original == null) return null;
        TreeNode copy = new TreeNode(original.val);
        copy.left = cloneTree(original.left);
        copy.right = cloneTree(original.right);
        return copy;
    }

    /**
     * Utility tool to print pre-order traversal for visualization.
     */
    private static void printPreOrder(TreeNode root) {
        if (root == null) {
            System.out.print("null ");
            return;
        }
        System.out.print(root.val + " ");
        printPreOrder(root.left);
        printPreOrder(root.right);
    }

    public static void main(String[] args) {
        MaximumDepthOfBinaryTree solver = new MaximumDepthOfBinaryTree();

        // --- TEST CASE 1: Standard multi-level binary tree ---
        //      3
        //    /   \
        //   9     20
        //        /  \
        //       15   7
        TreeNode root1 = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7))
        );

        // --- TEST CASE 2: Skewed binary tree ---
        //    1
        //     \
        //      2
        TreeNode root2 = new TreeNode(1, null, new TreeNode(2));

        // --- TEST CASE 3: Empty tree ---
        TreeNode root3 = null;

        TreeNode[] allRoots = {root1, root2, root3};
        String[] logs = {
                "Standard Multi-Level Binary Tree Configuration (Expected Max Depth: 3)",
                "Skewed Binary Tree Configuration              (Expected Max Depth: 2)",
                "Empty Tree Configuration                      (Expected Max Depth: 0)"
        };

        // Process test scenarios
        for (int i = 0; i < allRoots.length; i++) {
            System.out.println("===============================================================");
            System.out.println("RUNNING: " + logs[i]);
            System.out.println("===============================================================");

            TreeNode dfsInput = cloneTree(allRoots[i]);
            TreeNode bfsInput = cloneTree(allRoots[i]);

            System.out.print("Tree (Pre-Order): ");
            printPreOrder(allRoots[i]);
            System.out.println();

            System.out.println("DFS Computation Output: " + solver.maxDepthDFS(dfsInput));
            System.out.println("BFS Computation Output: " + solver.maxDepthBFS(bfsInput));
            System.out.println();
        }
    }
}