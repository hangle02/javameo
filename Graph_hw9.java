// package com.gradescope.cs201;
// import com.gradescope.cs201.Hw9_interface;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

interface Hw9_interface {
    public boolean add_node(int x);
    public boolean add_edge(int x, int y);
    public LinkedList<LinkedList<Integer>> find_components(); 
    public int find_diameter_length();
    public LinkedList<LinkedList<Integer>> find_bridges(); 
}
public class Graph_hw9 implements Hw9_interface {
    HashMap<Integer, LinkedList<Integer>> adj_list;
    public Graph_hw9(){
        this.adj_list = new HashMap<>();
    }

    public boolean add_node(int x){
        if(adj_list.keySet().contains(x)){
            return false;
        }
        adj_list.putIfAbsent(x, new LinkedList<>()); 
        return true;
    }

    public boolean add_edge(int x, int y){
        if(adj_list.containsKey(y) && adj_list.containsKey(x) && !adj_list.get(x).contains(y)){
            adj_list.get(y).add(x);
            adj_list.get(x).add(y);
            return true;
        }
        return false;
    }

    public LinkedList<LinkedList<Integer>> find_components(){
        LinkedList<LinkedList<Integer>> components = new LinkedList<>();
        //HashMap<Integer, Boolean> visited = new HashMap<>();
        //int[] visited = new int[adj_list.size()];
        List<Integer> visited = new ArrayList<>();
        for(int node: adj_list.keySet()){
            if(!visited.contains(node)){
                LinkedList<Integer> cur_components = new LinkedList<>();
                Deque<Integer> queue = new ArrayDeque<>();
                queue.offer(node);
                visited.add(node);
                while(!queue.isEmpty()){
                    int cur = queue.poll();
                    cur_components.add(cur);
                    for(int neighbor: adj_list.get(cur)){
                        if(!visited.contains(neighbor)){
                            visited.add(neighbor);
                            queue.add(neighbor);
                        }
                    }
                }
                cur_components.sort(Comparator.naturalOrder());
                components.add(cur_components);
            }
        }
        return components;
    } 

    public int find_diameter_length(){
        int max_diameter = 0;
        for(int node: adj_list.keySet()){
            HashMap<Integer, Integer> distances = new HashMap<>();
            Deque<Integer> queue = new ArrayDeque<>();
            queue.offer(node);
            distances.put(node, 0);
            while(!queue.isEmpty()){
                int cur = queue.poll();
                int cur_dis = distances.get(cur);
                max_diameter = Math.max(max_diameter, cur_dis);
                for(int neighbor: adj_list.get(cur)){
                    if(!distances.containsKey(neighbor)){
                        distances.put(neighbor, cur_dis+1);
                        queue.offer(neighbor);
                    }
                }
            }
        }
        return max_diameter;
    }

    public LinkedList<LinkedList<Integer>> find_bridges(){
        LinkedList<LinkedList<Integer>> bridges = new LinkedList<>();
        
        int initialComponents = find_components().size();

        List<int[]> allEdges = new ArrayList<>();
        for (int u : adj_list.keySet()) {
            for (int v : adj_list.get(u)) {
                if (u < v) { 
                    allEdges.add(new int[]{u, v});
                }
            }
        }

        for (int[] edge : allEdges) {
            int u = edge[0];
            int v = edge[1];

            adj_list.get(u).remove((Integer) v);
            adj_list.get(v).remove((Integer) u);

            int currentComponents = find_components().size();

            if (currentComponents > initialComponents) {
                LinkedList<Integer> bridge = new LinkedList<>();
                bridge.add(u);
                bridge.add(v);
                bridges.add(bridge);
            }

            adj_list.get(u).add(v);
            adj_list.get(v).add(u);
        }

        return bridges;
    }

    public static void main(String[] args) {
        // Graph_hw9 my_graph = new Graph_hw9();
        // my_graph.add_node(1);
        // my_graph.add_node(2);
        // my_graph.add_node(3);
        // my_graph.add_node(4);
        // my_graph.add_node(5);
        // my_graph.add_node(6);
        // my_graph.add_node(7);
        // my_graph.add_node(8);
        // my_graph.add_node(9);
        // my_graph.add_node(10);
        // my_graph.add_node(11);
        // my_graph.add_node(12);
        // my_graph.add_node(13);
        // my_graph.add_node(14);
        // my_graph.add_node(15);
        // my_graph.add_node(16);
        // my_graph.add_edge(1,6);
        // my_graph.add_edge(2,5);
        // my_graph.add_edge(2,6);
        // my_graph.add_edge(3,5);
        // my_graph.add_edge(3,6);
        // my_graph.add_edge(4,5);
        // my_graph.add_edge(4,6);
        // my_graph.add_edge(7,8);
        // my_graph.add_edge(7,9);
        // my_graph.add_edge(8,9);
        // my_graph.add_edge(10,11);
        // my_graph.add_edge(12,9);
        // my_graph.add_edge(12,13);
        // my_graph.add_edge(12,14);
        // my_graph.add_edge(13,14);
        // my_graph.add_edge(14,15);
        // my_graph.add_edge(16,7);
        // System.out.println(my_graph.find_components().toString().equals("[[1, 2, 3, 4, 5, 6], [7, 8, 9, 12, 13, 14, 15, 16], [10, 11]]")); // true
        // // System.out.println(my_graph.find_components().toString()); // true
        // System.out.println(my_graph.find_diameter_length() == 5); // true
        // System.out.println(my_graph.find_bridges().toString().equals("[[1, 6], [7, 16], [9, 12], [10, 11], [14, 15]]")); // true

    }
}
