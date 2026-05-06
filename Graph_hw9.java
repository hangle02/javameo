import java.util.HashMap;
import java.util.LinkedList;
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
            adj_list.get(x).add(y);
            return true;
        }
        return false;
    }

    public LinkedList<LinkedList<Integer>> find_components(){
        LinkedList<LinkedList<Integer>> components = new LinkedList<>();
        HashMap<Integer, Boolean> visited = new HashMap<>();
        for(int node: adj_list.keySet()){
            
        }
        return null;
    } 

    public int find_diameter_length(){return 0;}

    public LinkedList<LinkedList<Integer>> find_bridges(){return null;}

    public static void main(String[] args) {
        
    }
}
