import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Comparator;
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

    public int find_diameter_length(){return 0;}

    public LinkedList<LinkedList<Integer>> find_bridges(){return null;}

    public static void main(String[] args) {
        Graph_hw9 my_graph = new Graph_hw9();
        my_graph.add_node(1);
        my_graph.add_node(2);
        my_graph.add_node(3);
        my_graph.add_node(4);
        my_graph.add_node(5);
        my_graph.add_node(6);
        my_graph.add_node(7);
        my_graph.add_node(8);
        my_graph.add_node(9);
        my_graph.add_node(10);
        my_graph.add_node(11);
        my_graph.add_node(12);
        my_graph.add_node(13);
        my_graph.add_node(14);
        my_graph.add_node(15);
        my_graph.add_node(16);
        my_graph.add_edge(1,6);
        my_graph.add_edge(2,5);
        my_graph.add_edge(2,6);
        my_graph.add_edge(3,5);
        my_graph.add_edge(3,6);
        my_graph.add_edge(4,5);
        my_graph.add_edge(4,6);
        my_graph.add_edge(7,8);
        my_graph.add_edge(7,9);
        my_graph.add_edge(8,9);
        my_graph.add_edge(10,11);
        my_graph.add_edge(12,9);
        my_graph.add_edge(12,13);
        my_graph.add_edge(12,14);
        my_graph.add_edge(13,14);
        my_graph.add_edge(14,15);
        my_graph.add_edge(16,7);
        System.out.println(my_graph.find_components().toString().equals("[[1, 2, 3, 4, 5, 6], [7, 8, 9, 12, 13, 14, 15, 16], [10, 11]]")); // true
        System.out.println(my_graph.find_components().toString()); // true
    
    }
}
