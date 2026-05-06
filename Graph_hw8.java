package com.gradescope.cs201;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
// interface Hw8_interface {
//     public boolean is_adjacent(int x, int y);
//     public LinkedList<Integer> get_neighbors(int x);
//     public boolean add_node(int x);
//     public boolean remove_node(int x);
//     public boolean add_edge(int x, int y);
//     public boolean remove_edge(int x, int y);
//     public int max_degree();
//     public int max_in_degree();
//     public int max_out_degree();
//     //public double average_in_degree();
//     //public double average_out_degree();
//     public double average_degree();
//     public LinkedList<Integer> get_hubs(int degree);
//     public LinkedList<Integer> get_isolated_nodes();
// }

public class Graph_hw8 implements Hw8_interface {
    //directed graph
    HashMap<Integer, LinkedList<Integer>> adj_list;
    int node_num;
    public Graph_hw8(){
        this.adj_list = new HashMap<>();
        this.node_num =0;
    }
    public boolean is_adjacent(int x, int y){
        if(adj_list.containsKey(y)&&adj_list.containsKey(x)){
            return (adj_list.get(x).contains(y));
        }
         return false;
    }
    public LinkedList<Integer> get_neighbors(int x){
        LinkedList<Integer> neighbors = new LinkedList<>(adj_list.get(x));
        for(int node: adj_list.keySet()){
            if(adj_list.get(node).contains(x) && !neighbors.contains(node)){
                neighbors.add(node);
            }
        }
        neighbors.sort(Comparator.naturalOrder());
        return neighbors;
    }
    public boolean add_node(int x){
        if(adj_list.containsKey(x)){
            return false;
        }
        node_num++;
        adj_list.putIfAbsent(x, new LinkedList<>());
        return true;
    }
    public boolean remove_node(int x){
        if(!adj_list.containsKey(x)){
            return false;
        }
        for(int node: adj_list.keySet()){
            if(adj_list.get(node).contains(x)){
                adj_list.get(node).remove((Integer)x);
            }
        }
        adj_list.remove(x);
        node_num--;
        return true;
    }
    public boolean add_edge(int x, int y){
        if(adj_list.containsKey(y) && adj_list.containsKey(x) && !adj_list.get(x).contains(y)){
            //adj_list.get(y).add(x);
            adj_list.get(x).add(y);
            return true;
        }
        return false;
    }
    public boolean remove_edge(int x, int y){
        if(adj_list.containsKey(x) && adj_list.containsKey(y) && adj_list.get(x).contains(y)){
            adj_list.get(x).remove((Integer) y);
           return true;
        }
        return false;
    }
    public int max_degree(){
        int max_deg = 0;
        for(int node: adj_list.keySet()){
            int node_deg = get_neighbors(node).size();
            //int node_deg = get_in_deg(node) + get_out_deg(node);
            if(node_deg>max_deg){
                max_deg=node_deg;
            }
        }
        return max_deg;
    }
    private int get_in_deg(int x){
        int in_deg=0;
        for(int node: adj_list.keySet()){
            if(adj_list.get(node).contains(x)){
                in_deg++;
            }
        }
        return in_deg;
    }
    private int get_out_deg(int x){
        return adj_list.get(x).size();
    }
    public int max_in_degree(){
        int max_ideg = 0;
        for(int node: adj_list.keySet()){
            if(get_in_deg(node)>max_ideg){
                max_ideg = get_in_deg(node);
            }
        }
        return max_ideg;}
    public int max_out_degree(){
        int max_odeg = 0;
        for(int node: adj_list.keySet()){
            if(get_out_deg(node)>max_odeg){
                max_odeg = get_out_deg(node);
            }
        }
        return max_odeg;}
    //public double average_in_degree();
    //public double average_out_degree();
    public double average_degree(){
        //  int sum_deg = 0;
        // for(int node: adj_list.keySet()){
        //     int node_deg = get_in_deg(node) + get_out_deg(node)-1;
        //     sum_deg+=node_deg;
        // }
        // double avg_deg = sum_deg/(adj_list.size());
        // return avg_deg;
        if (adj_list.isEmpty()) return 0.0;
        
        int total_edges = 0;
        for (int node : adj_list.keySet()) {
            total_edges += adj_list.get(node).size(); // Tổng số cạnh
        }
        
        // Công thức của assignment: E / V
        return (double) total_edges / adj_list.size();
    }
    
    public LinkedList<Integer> get_hubs(int degree){
        LinkedList<Integer> hubs = new LinkedList<>();
        for(int node: adj_list.keySet()){
            int node_deg = get_in_deg(node) + get_out_deg(node);
            if(node_deg>=degree){
                hubs.add(node);
            }
        }
        hubs.sort(Comparator.naturalOrder());
        return hubs;
    }
    public LinkedList<Integer> get_isolated_nodes(){
        LinkedList<Integer> isolated_nodes = new LinkedList<>();
        for(int node: adj_list.keySet()){
            int node_deg = get_in_deg(node) + get_out_deg(node);
            if(node_deg==0){
                isolated_nodes.add(node);
            }
        }
        isolated_nodes.sort(Comparator.naturalOrder());
        return isolated_nodes;}
    public static void main(String[] args) {
        // Graph_hw8 my_graph = new Graph_hw8();
        // my_graph.add_node(1);
        // my_graph.add_node(2);
        // my_graph.add_node(3);
        // my_graph.add_node(4);
        // my_graph.add_node(5);
        // my_graph.add_node(6);
        // my_graph.add_node(9);
        // my_graph.add_node(10);
        // System.out.println(my_graph.add_node(11)); // true
        // System.out.println(my_graph.add_node(1) == false); // true
        // my_graph.add_edge(1,2);
        // my_graph.add_edge(1,6);
        // my_graph.add_edge(2,3);
        // my_graph.add_edge(2,4);
        // my_graph.add_edge(2,5);
        // my_graph.add_edge(2,10);
        // my_graph.add_edge(3,4);
        // my_graph.add_edge(4,9);
        // my_graph.add_edge(5,6);
        // my_graph.add_edge(6,3);
        // my_graph.add_edge(6,5);
        // my_graph.add_edge(9,3);
        // System.out.println(my_graph.add_edge(9,11)); //true
        // System.out.println(my_graph.add_edge(3, 4) == false); // true
        // System.out.println(my_graph.add_edge(2, 8) == false); // true
        // System.out.println(my_graph.add_edge(8, 10) == false); // true
        // System.out.println(my_graph.remove_edge(2,10)); // true
        // System.out.println(my_graph.remove_edge(1,4) == false); // true
        // System.out.println(my_graph.remove_node(9)); // true
        // System.out.println(my_graph.remove_node(9) == false); // true
        // System.out.println(my_graph.remove_edge(4,9) == false); // true
        // System.out.println(my_graph.is_adjacent(4,9) == false); // true
        // System.out.println(my_graph.is_adjacent(1,2)); // true
        // System.out.println(my_graph.is_adjacent(3,6) == false); // true
        // System.out.println(my_graph.is_adjacent(3,6) == false); // true
        // System.out.println(my_graph.get_neighbors(5).toString().equals("[2, 6]")); //true
        // System.out.println(my_graph.max_degree() == 4); // true
        // System.out.println(my_graph.max_in_degree() == 2); // true
        // System.out.println(my_graph.max_out_degree() == 3); // true
        // System.out.println(((int)Math.round(8*my_graph.average_degree()))==9); //true
        // System.out.println(my_graph.get_hubs(3).toString().equals("[2, 3, 5, 6]")); //true
        // System.out.println(my_graph.get_isolated_nodes().toString().equals("[10, 11]")); // true
    }
}
