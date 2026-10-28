package datastructures.problems;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LeetCode 226: Invert Binary Tree
 *
 * Inverts a binary tree by swapping the left and right children for every node.
 * <a href="https://neetcode.io/problems/invert-binary-tree/solution">NeetCode Solution</a>
 */
public class InvertBinaryTree {

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

    /**
     * APPROACH 1: Depth First Search (DFS) / Recursive Driver
     *
     * Recursively traverses the tree, swapping the left and right children of each node.
     */
    public TreeNode invertTreeDFS(TreeNode root) {
        // Base Case: If the root is null, return null
        if (root == null) {
            return null;
        }

        // Swap the left and right children
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        // Recursively invert the left and right subtrees
        invertTreeDFS(root.left);
        invertTreeDFS(root.right);

        return root;
    }

    /**
     * APPROACH 2: Breadth First Search (BFS) / Iterative Driver
     *
     * Employs a FIFO Queue to perform a level-order traversal, swapping children layer-by-layer.
     */
    public TreeNode invertTreeBFS(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            // Swap the left and right children of the current node
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;

            // Enqueue non-null children for further processing
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return root;
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
     * Utility tool to print the tree level by level or pre-order for visualization.
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
        InvertBinaryTree solver = new InvertBinaryTree();

        // --- TEST CASE 1: Standard 3-level binary tree ---
        //      4
        //    /   \
        //   2     7
        //  / \   / \
        // 1   3 6   9
        TreeNode root1 = new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(7, new TreeNode(6), new TreeNode(9))
        );

        // --- TEST CASE 2: Simple 2-node binary tree ---
        //    2
        //   /
        //  1
        TreeNode root2 = new TreeNode(2, new TreeNode(1), null);

        // --- TEST CASE 3: Empty tree ---
        TreeNode root3 = null;

        TreeNode[] allRoots = {root1, root2, root3};
        String[] logs = {
                "Standard Multi-Level Binary Tree Configuration",
                "Simple Two-Node Binary Tree Configuration",
                "Empty Tree Configuration"
        };

        // Process test scenarios
        for (int i = 0; i < allRoots.length; i++) {
            System.out.println("===============================================================");
            System.out.println("RUNNING: " + logs[i]);
            System.out.println("===============================================================");

            TreeNode dfsInput = cloneTree(allRoots[i]);
            TreeNode bfsInput = cloneTree(allRoots[i]);

            System.out.print("Original (Pre-Order): ");
            printPreOrder(allRoots[i]);
            System.out.println();

            TreeNode dfsResult = solver.invertTreeDFS(dfsInput);
            System.out.print("DFS Inverted (Pre-Order): ");
            printPreOrder(dfsResult);
            System.out.println();

            TreeNode bfsResult = solver.invertTreeBFS(bfsInput);
            System.out.print("BFS Inverted (Pre-Order): ");
            printPreOrder(bfsResult);
            System.out.println("\n");
        }
    }
}