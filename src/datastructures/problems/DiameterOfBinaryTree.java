package datastructures.problems;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode 543: Diameter of Binary Tree
 *
 * Finds the length of the longest path between any two nodes in a tree (which may or may not pass through the root).
 * <a href="https://neetcode.io/problems/diameter-of-binary-tree/solution">...</a>
 */
public class DiameterOfBinaryTree {

    /**
     * Definition for a binary tree node.
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {}

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // Global variable to keep track of the maximum diameter found during DFS traversal
    private int maxDiameter;

    /**
     * APPROACH 1: Depth First Search (DFS) Driver
     *
     * Computes the diameter by recursively finding the height of left and right subtrees
     * for every node, while maintaining a running maximum of (leftHeight + rightHeight).
     */
    public int diameterOfBinaryTreeDFS(TreeNode root) {
        maxDiameter = 0;
        calculateHeightAndDiameter(root);
        return maxDiameter;
    }

    /**
     * Recursive DFS Helper
     *
     * Returns the height of the current node while updating the global maxDiameter.
     */
    private int calculateHeightAndDiameter(TreeNode node) {
        // Base Case: If node is null, height is 0
        if (node == null) {
            return 0;
        }

        // Recursively get the heights of the left and right subtrees
        int leftHeight = calculateHeightAndDiameter(node.left);
        int rightHeight = calculateHeightAndDiameter(node.right);

        // Update the maximum diameter if the path through the current node is larger
        maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);

        // Return the height of the current node
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * APPROACH 2: Breadth First Search (BFS) / Level-Order Traversal with Height Helper
     *
     * Iterates through every node level-by-level, calculating the diameter path sum
     * for each node using a separate height computation.
     */
    public int diameterOfBinaryTreeBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int maxLen = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        // Visit every node via BFS and calculate the diameter passing through it
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            // Diameter passing through the current node is leftHeight + rightHeight
            int currentDiameter = getHeight(node.left) + getHeight(node.right);
            maxLen = Math.max(maxLen, currentDiameter);

            // Enqueue children to continue exploring all nodes
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return maxLen;
    }

    /**
     * Helper method to compute the height of a given subtree.
     */
    private int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
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
        DiameterOfBinaryTree solver = new DiameterOfBinaryTree();

        // --- TEST CASE 1: Standard multi-level binary tree ---
        //      1
        //     / \
        //    2   3
        //   / \
        //  4   5
        TreeNode root1 = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3)
        );

        // --- TEST CASE 2: Two-node binary tree ---
        //    1
        //   /
        //  2
        TreeNode root2 = new TreeNode(1, new TreeNode(2), null);

        // --- TEST CASE 3: Single node tree ---
        TreeNode root3 = new TreeNode(1);

        TreeNode[] allRoots = {root1, root2, root3};
        String[] logs = {
                "Standard Multi-Level Binary Tree Configuration (Expected Diameter: 3)",
                "Two-Node Binary Tree Configuration            (Expected Diameter: 1)",
                "Single Node Tree Configuration                (Expected Diameter: 0)"
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

            System.out.println("DFS Computation Output: " + solver.diameterOfBinaryTreeDFS(dfsInput));
            System.out.println("BFS Computation Output: " + solver.diameterOfBinaryTreeBFS(bfsInput));
            System.out.println();
        }
    }
}