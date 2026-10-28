package datastructures.problems;

import java.util.LinkedList;
import java.util.Queue;


/**
 * LeetCode 695: Max Area of Island
 * Finds the maximum area of a connected grid island of 1s (4-directionally).
 * <a href="https://neetcode.io/problems/max-area-of-island/solution">...</a>
 */
public class MaxAreaOfIsland {
    // Cardinal directions used to explore 4-way neighbors (Down, Up, Right, Left)
    private static final int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /**
     * APPROACH 1: Depth First Search (DFS) Driver
     * Iterates through the grid to locate land, launching a recursive deep dive on discovery.
     */
    public int maxAreaOfIslandDFS(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int ROWS = grid.length, COLS = grid[0].length;
        int maxArea = 0;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                // When land is encountered, invoke DFS to calculate its exact cluster area
                if (grid[r][c] == 1) {
                    maxArea = Math.max(maxArea, dfs(grid, r, c));
                }
            }
        }
        return maxArea;
    }

    /**
     * Recursive DFS Helper
     * Sinks the island as it explores to guard against infinite loops.
     */
    private int dfs(int[][] grid, int r, int c) {
        // Base Case: Verify grid boundaries and check if cell is water/visited
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] == 0) {
            return 0;
        }

        // Sink the current cell (mark as 0) to avoid re-visiting
        grid[r][c] = 0;

        // Count the current valid cell
        int area = 1;

        // Recursively accumulate the areas from all 4 neighboring directions
        for (int[] dir : directions) {
            area += dfs(grid, r + dir[0], c + dir[1]);
        }
        return area;
    }

    /**
     * APPROACH 2: Breadth First Search (BFS) Driver
     * Iterates through the grid, launching an iterative level-order expansion on discovery.
     */
    public int maxAreaOfIslandBFS(int[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int ROWS = grid.length, COLS = grid[0].length;
        int maxArea = 0;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == 1) {
                    maxArea = Math.max(maxArea, bfs(grid, r, c));
                }
            }
        }
        return maxArea;
    }

    /**
     * Iterative BFS Helper
     * Employs a FIFO Queue to expand outward across adjacent nodes layer-by-layer.
     */
    private int bfs(int[][] grid, int r, int c) {
        Queue<int[]> q = new LinkedList<>();

        // Sink and enqueue the starting land coordinate
        grid[r][c] = 0;
        q.add(new int[]{r, c});

        int area = 1;

        while (!q.isEmpty()) {
            int[] node = q.poll();
            int row = node[0], col = node[1];

            // Scan all 4 adjacent neighbors
            for (int[] dir : directions) {
                int nr = row + dir[0];
                int nc = col + dir[1];

                // Boundary verification and validation checks for adjacent land
                if (nr >= 0 && nc >= 0 && nr < grid.length && nc < grid[0].length && grid[nr][nc] == 1) {
                    q.add(new int[]{nr, nc});
                    // Critical: Sink immediately upon discovery to avoid queuing duplicate coordinates
                    grid[nr][nc] = 0;
                    area++;
                }
            }
        }
        return area;
    }

    /**
     * Utility tool to execute safe deep copies of 2D arrays.
     * Prevents in-place mutation overlap between successive test runs.
     */
    private static int[][] cloneGrid(int[][] original) {
        if (original == null) return null;
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    public static void main(String[] args) {
        MaxAreaOfIsland solver = new MaxAreaOfIsland();

        // --- TEST CASE 1: Standard multi-island matrix with mixed areas ---
        int[][] grid1 = {
                {0,0,1,0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,1,1,0,1,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,1,0,0,1,0,1,0,0},
                {0,1,0,0,1,1,0,0,1,1,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,1,1,0,0,0,0}
        }; // Largest island is in the middle-right/bottom tracking an area of 6.

        // --- TEST CASE 2: No valid land masses ---
        int[][] grid2 = {
                {0, 0, 0},
                {0, 0, 0}
        };

        // --- TEST CASE 3: Single cell island isolated ---
        int[][] grid3 = {
                {1}
        };

        int[][][] allGrids = {grid1, grid2, grid3};
        String[] logs = {
                "Standard Multi-Island Configuration (Expected Maximum Area: 6)",
                "Barren Water Grid Matrix            (Expected Maximum Area: 0)",
                "Single Element Isolated Matrix      (Expected Maximum Area: 1)"
        };

        // Process test scenarios
        for (int i = 0; i < allGrids.length; i++) {
            System.out.println("===============================================================");
            System.out.println("RUNNING: " + logs[i]);
            System.out.println("===============================================================");

            // Pass clones to safeguard initial states
            int[][] dfsInput = cloneGrid(allGrids[i]);
            int[][] bfsInput = cloneGrid(allGrids[i]);

            System.out.println("DFS Computation Output: " + solver.maxAreaOfIslandDFS(dfsInput));
            System.out.println("BFS Computation Output: " + solver.maxAreaOfIslandBFS(bfsInput));
            System.out.println();
        }
    }
}
