package datastructures.problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * LeetCode 815: Bus Routes
 * <p>
 * Finds the minimum number of buses needed to travel from a source stop to a target stop.
 * <a href="https://neetcode.io/solutions/bus-routes">...</a>
 */
public class BusRoutes {

    /**
     * APPROACH 1: Breadth First Search (Stops as Nodes)
     * <p>
     * Models stop as graph nodes and use a level-order traversal to find the shortest
     * path in terms of bus transfers, tracking seen stops and buses to avoid redundant work.
     */
    public int numBusesToDestinationStops(int[][] routes, int source, int target) {
        if (source == target) {
            return 0;
        }

        // Map bus stops to buses that visit them
        Map<Integer, List<Integer>> stopToBuses = new HashMap<>();

        for (int bus = 0; bus < routes.length; bus++) {
            for (int stop : routes[bus]) {
                stopToBuses.computeIfAbsent(stop, k -> new ArrayList<>()).add(bus);
            }
        }

        if (!stopToBuses.containsKey(source) || !stopToBuses.containsKey(target)) {
            return -1;
        }

        // BFS initialization
        Queue<int[]> queue = new LinkedList<>();
        Set<Integer> visitedStops = new HashSet<>();
        Set<Integer> usedBuses = new HashSet<>();

        queue.offer(new int[]{source, 0});
        visitedStops.add(source);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentStop = current[0];
            int busesTaken = current[1];

            for (int bus : stopToBuses.get(currentStop)) {
                if (usedBuses.contains(bus)) continue;
                else usedBuses.add(bus);

                for (int stop : routes[bus]) {
                    if (stop == target) {
                        return busesTaken + 1;
                    }

                    if (!visitedStops.contains(stop)) {
                        visitedStops.add(stop);
                        queue.offer(new int[]{stop, busesTaken + 1});
                    }
                }

            }

        }


        return -1;
    }
}
