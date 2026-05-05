// package com.gradescope.cs201;
// import com.gradescope.cs201.Hw8_interface;
import java.util.HashMap;
import java.util.LinkedList;
interface Hw8_interface {
    public boolean is_adjacent(int x, int y);
    public LinkedList<Integer> get_neighbors(int x);
    public boolean add_node(int x);
    public boolean remove_node(int x);
    public boolean add_edge(int x, int y);
    public boolean remove_edge(int x, int y);
    public int max_degree();
    public int max_in_degree();
    public int max_out_degree();
    //public double average_in_degree();
    //public double average_out_degree();
    public double average_degree();
    public LinkedList<Integer> get_hubs(int degree);
    public LinkedList<Integer> get_isolated_nodes();
}

public class Graph_hw8 implements Hw8_interface {
    HashMap<Integer, LinkedList<Integer>> adj_list;
    int node_num;
    public static final int NO_CONNECTION = 0;
    public Graph_hw8(){
        this.adj_list = new HashMap<>();
        this.node_num =0;
    }
    public boolean is_adjacent(int x, int y){
         return (adj_list.get(y).contains(x));
    }
    public LinkedList<Integer> get_neighbors(int x){
        return adj_list.get(x);
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
        LinkedList<Integer> neighborsOfx = adj_list.get(x);
        for(int node: neighborsOfx){
            adj_list.get(node).remove((Integer) x);
        }
        adj_list.remove(x);
        node_num--;
        return true;
    }
    public boolean add_edge(int x, int y){
        if(adj_list.containsKey(y) && adj_list.containsKey(x) && !adj_list.get(y).contains(x)){
            adj_list.get(y).add(x);
            adj_list.get(x).add(y);
            return true;
        }
        return false;
    }
    public boolean remove_edge(int x, int y){
        return true;
    }
    public int max_degree(){return 0;}
    public int max_in_degree(){return 0;}
    public int max_out_degree(){return 0;}
    //public double average_in_degree();
    //public double average_out_degree();
    public double average_degree(){return 0;}
    public LinkedList<Integer> get_hubs(int degree){return null;}
    public LinkedList<Integer> get_isolated_nodes(){return null;}
    public static void main(String[] args) {
        Graph_hw8 my_graph = new Graph_hw8();
        my_graph.add_node(1);
        my_graph.add_node(2);
        my_graph.add_node(3);
        my_graph.add_node(4);
        my_graph.add_node(5);
        my_graph.add_node(6);
        my_graph.add_node(9);
        my_graph.add_node(10);
        System.out.println(my_graph.add_node(11)); // true
        System.out.println(my_graph.add_node(1) == false); // true
        my_graph.add_edge(1,2);
        my_graph.add_edge(1,6);
        my_graph.add_edge(2,3);
        my_graph.add_edge(2,4);
        my_graph.add_edge(2,5);
        my_graph.add_edge(2,10);
        my_graph.add_edge(3,4);
        my_graph.add_edge(4,9);
        my_graph.add_edge(5,6);
        my_graph.add_edge(6,3);
        my_graph.add_edge(6,5);
        my_graph.add_edge(9,3);
        System.out.println(my_graph.add_edge(9,11)); //true
        System.out.println(my_graph.add_edge(3, 4) == false); // true
        System.out.println(my_graph.add_edge(2, 8) == false); // true
        System.out.println(my_graph.add_edge(8, 10) == false); // true
        System.out.println(my_graph.remove_edge(2,10)); // true
        System.out.println(my_graph.remove_edge(1,4) == false); // true
        System.out.println(my_graph.remove_node(9)); // true
        System.out.println(my_graph.remove_node(9) == false); // true
        System.out.println(my_graph.remove_edge(4,9) == false); // true
        System.out.println(my_graph.is_adjacent(4,9) == false); // true
        System.out.println(my_graph.is_adjacent(1,2)); // true
        System.out.println(my_graph.is_adjacent(3,6) == false); // true
        System.out.println(my_graph.is_adjacent(3,6) == false); // true
        System.out.println(my_graph.get_neighbors(5).toString().equals("[2, 6]")); //true
    }
}
