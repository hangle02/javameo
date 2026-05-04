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
    public boolean is_adjacent(int x, int y){
         return true;
    }
    public LinkedList<Integer> get_neighbors(int x){
        return null;
    }
    public boolean add_node(int x){
         return true;
    }
    public boolean remove_node(int x){
        return true;
    }
    public boolean add_edge(int x, int y){
        return true;
    }
    public boolean remove_edge(int x, int y){return true;}
    public int max_degree(){return 0;}
    public int max_in_degree(){return 0;}
    public int max_out_degree(){return 0;}
    //public double average_in_degree();
    //public double average_out_degree();
    public double average_degree(){return 0;}
    public LinkedList<Integer> get_hubs(int degree){return null;}
    public LinkedList<Integer> get_isolated_nodes(){return null;}
    public static void main(String[] args) {
        
    }
}
