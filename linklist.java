interface List<T>{
    //add value
    public void add(T value);

    //add(index, value)
    public void addbyIndex(int index, T value);

    public boolean contains(T value);

    public int indexOf(T value);

    public boolean isEmpty();

    public void remove(T value);

    public void clear();

    public void removeI(int index);

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
        return (head == null || length ==0);
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
        while (last_node.next != null) { 
            last_node = last_node.next;
        }
        last_node.next = tmp;
    }

    //add(index, value)
    public void addbyIndex(int index, T value){}

    public boolean contains(T value){
        Node<T> current_node = head;
        while (current_node.next != null) { 
            if(current_node.data == value){
                return true;
            }
            current_node = current_node.next;
        }
        return false;
    }

    public int indexOf(T value){
        return 0;
    }

    public void remove(T value){}


    public void removeI(int index){}


    public void set(int index, T value){}

}



