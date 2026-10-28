package datastructures.problems;

import java.util.LinkedList;
import java.util.Queue;

public class NumberOfIslands {

    // https://neetcode.io/solutions/number-of-islands

    private static final int[][] directions = {{1, 0}, {-1, 0},
            {0, 1}, {0, -1}};

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int ROWS = grid.length, COLS = grid[0].length;
        int islands = 0;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == '1') {
                    dfs(grid, r, c);
                    islands++;
                }
            }
        }
        return islands;
    }

    // 2. BFS Driver Implementation (Added to test your bfs method)
    public int numIslandsBFS(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int ROWS = grid.length, COLS = grid[0].length;
        int islands = 0;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == '1') {
                    bfs(grid, r, c);
                    islands++;
                }
            }
        }
        return islands;
    }

    private void dfs(char[][] grid, int r, int c) {
        if (r < 0 || c < 0 || r >= grid.length ||
                c >= grid[0].length || grid[r][c] == '0') {
            return;
        }

        grid[r][c] = '0';
        for (int[] dir : directions) {
            dfs(grid, r + dir[0], c + dir[1]);
        }
    }

    private void bfs(char[][] grid, int r, int c) {
        Queue<int[]> q = new LinkedList<>();
        grid[r][c] = '0';
        q.add(new int[]{r, c});

        while (!q.isEmpty()) {
            int[] node = q.poll();
            int row = node[0], col = node[1];

            for (int[] dir : directions) {
                int nr = row + dir[0], nc = col + dir[1];
                if (nr >= 0 && nc >= 0 && nr < grid.length &&
                        nc < grid[0].length && grid[nr][nc] == '1') {
                    q.add(new int[]{nr, nc});
                    grid[nr][nc] = '0';
                }
            }
        }
    }

    // Helper method to deep copy 2D primitive char arrays
    private static char[][] cloneGrid(char[][] original) {
        if (original == null) return null;
        char[][] copy = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    public static void main(String[] args) {
        NumberOfIslands solver = new NumberOfIslands();

        // Test Case 1: Multiple distinct islands
        char[][] grid1 = {
                {'1', '1', '0', '0', '1'},
                {'1', '1', '0', '0', '1'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };

        // Test Case 2: One big connected island
        char[][] grid2 = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };

        // Test Case 3: Completely water (no islands)
        char[][] grid3 = {
                {'0', '0', '0'},
                {'0', '0', '0'}
        };

        // Test Case 4: Diagonal land elements (should count as separate islands)
        char[][] grid4 = {
                {'1', '0', '1'},
                {'0', '1', '0'},
                {'1', '0', '1'}
        };

        char[][][] allTests = {grid1, grid2, grid3, grid4};
        String[] descriptions = {
                "Multiple distinct islands (Expected: 4)",
                "One continuous island (Expected: 1)",
                "All water grid (Expected: 0)",
                "Diagonal-only connections (Expected: 5)"
        };

        for (int i = 0; i < allTests.length; i++) {
            System.out.println("--- Test " + (i + 1) + ": " + descriptions[i] + " ---");

            // Clone grids since mutations happen in-place ('1' -> '0')
            char[][] dfsGrid = cloneGrid(allTests[i]);
            char[][] bfsGrid = cloneGrid(allTests[i]);

            System.out.println("DFS Island Count: " + solver.numIslands(dfsGrid));
            System.out.println("BFS Island Count: " + solver.numIslandsBFS(bfsGrid));
            System.out.println();
        }
    }
}
