import java.util.LinkedList;
interface Hw9_interface {
    public boolean add_node(int x);
    public boolean add_edge(int x, int y);
    public LinkedList<LinkedList<Integer>> find_components(); 
    public int find_diameter_length();
    public LinkedList<LinkedList<Integer>> find_bridges(); 
}
public class Graph_hw9 implements Hw9_interface {
    public boolean add_node(int x){ return false;}

    public boolean add_edge(int x, int y){return false;}

    public LinkedList<LinkedList<Integer>> find_components(){return null;} 

    public int find_diameter_length(){return 0;}

    public LinkedList<LinkedList<Integer>> find_bridges(){return null;}

    public static void main(String[] args) {
        
    }
}
