interface List<T>{
    //add value
    public void add(T value);

    //add(index, value)
    public void addbyIndex(int index, T value);

    public boolean contains(T value);

    public int indexOf(T value);

    public boolean isEmpty();

    public boolean remove(T value);

    public void clear();

    public boolean removeI(int index);

    public int size();

    public void set(int index, T value);
}

class Node<T>{
    T data;
    Node<T> next;
    Node(T _data, Node<T> _next){ //Constructor
        data = _data;
        next = _next;
    }
}

public class linklist<T> implements List<T> {
    Node<T> head;
    int length;
    public linklist(){
        head = null;
        length = 0;
    }

    public boolean isEmpty(){
        return (head == null);
    }

    public void clear(){
        head = null;
        length =0;
    }

    public int size(){
        return length;
    }

     //add value
    public void add(T value){
        //need 2 step: 1. which memory address that this value store
        Node<T> tmp = new Node(value, null);
        length++;

        //2. modify the link of the last element to this
        //Where the last node?
        //Standard solution
        // Node<T> current_node = head;
        // while (current_node.next != null) { 
        //     current_node = current_node.next;
        // }
        // Node<T> last_node = current_node;
        // last_node.next = tmp;

        // 2nd solution
        Node<T> last_node = head;
        if(head == null){
            head = tmp;
            
        }
        else{
        while (last_node.next != null) { 
            last_node = last_node.next;
        }
        last_node.next = tmp;
        }

    }

    //add(index, value)
    public void addbyIndex(int index, T value){}

    public boolean contains(T value){
        Node<T> current_node = head;
        if(current_node == null){
            return false;
        }
        while (current_node.next != null) { 
            if(current_node.data == value){
                return true;
            }
            current_node = current_node.next;
        }
        return false;
    }

    public int indexOf(T value){
        Node<T> current_node = head;
        int index = 0;
        if(current_node == null){
            return -1;
        }
        while (current_node != null) {
            if(current_node.data.equals(value)){
                return index;
            }
            current_node = current_node.next;
            index++; 
        }
        return -1;
    }

    public boolean remove(T value){
        Node<T> current_node = head;
        if(current_node == null){
            return false;
        }
        while (current_node!= null) { 
            if(current_node.data.equals(value)){
                current_node.data = null;
                length = length-1;
                return true;
            }
            current_node = current_node.next;
        }
       return false;
    }


    public boolean removeI(int index){
        Node<T> current_node = head;
        int i = -1;
        if(current_node == null){
            return false;
        }
        while (current_node!= null) {
            i++;
            if(i==index){
                current_node.data = null;
                length = length-1;
                return true;
            }
            current_node = current_node.next;
        }
       return false;
    }


    public void set(int index, T value){
        Node<T> new_Node = new Node(value, null);
        Node<T> leftNode = head;
        Node<T> rightNode = head;
        if(head == null){
            head = new_Node;
        }
        while (true) { 
            
        }

    }

    public static void main(String[] args) {
        linklist<Integer> list_1 = new linklist();
        list_1.add(1);
        list_1.add(2);
        list_1.add(3);

        list_1.clear();

        //list_1.add(-1);
        list_1.add(10);
        list_1.add(6);
        list_1.add(1);
        list_1.add(2);
        list_1.add(3);

        IO.println(list_1.contains(10));
        IO.println(list_1.isEmpty());
        IO.println(list_1.remove(3));
        IO.println(list_1.contains(3));

    }

}



