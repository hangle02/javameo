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

class Node1<T>{
    T data;
    Node1<T> next;
    Node1(T _data, Node1<T> _next){ //Constructor
        data = _data;
        next = _next;
    }
}

public class linklist<T> implements List<T> {
    Node1<T> head;
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
        Node1<T> tmp = new Node1(value, null);
        length++;

        //2. modify the link of the last element to this
        //Where the last Node1?
        //Standard solution
        // Node1<T> current_Node1 = head;
        // while (current_Node1.next != null) { 
        //     current_Node1 = current_Node1.next;
        // }
        // Node1<T> last_Node1 = current_Node1;
        // last_Node1.next = tmp;

        // 2nd solution
        Node1<T> last_Node1 = head;
        if(head == null){
            head = tmp;
            
        }
        else{
        while (last_Node1.next != null) { 
            last_Node1 = last_Node1.next;
        }
        last_Node1.next = tmp;
        }

    }

    //add(index, value)
    public void addbyIndex(int index, T value){}

    public boolean contains(T value){
        Node1<T> current_Node1 = head;
        if(current_Node1 == null){
            return false;
        }
        while (current_Node1.next != null) { 
            if(current_Node1.data == value){
                return true;
            }
            current_Node1 = current_Node1.next;
        }
        return false;
    }

    public int indexOf(T value){
        Node1<T> current_Node1 = head;
        int index = 0;
        if(current_Node1 == null){
            return -1;
        }
        while (current_Node1 != null) {
            if(current_Node1.data.equals(value)){
                return index;
            }
            current_Node1 = current_Node1.next;
            index++; 
        }
        return -1;
    }

    public boolean remove(T value){
        Node1<T> current_Node1 = head;
        if(current_Node1 == null){
            return false;
        }
        while (current_Node1!= null) { 
            if(current_Node1.data.equals(value)){
                current_Node1.data = null;
                length = length-1;
                return true;
            }
            current_Node1 = current_Node1.next;
        }
       return false;
    }


    public boolean removeI(int index){
        Node1<T> current_Node1 = head;
        int i = -1;
        if(current_Node1 == null){
            return false;
        }
        while (current_Node1!= null) {
            i++;
            if(i==index){
                current_Node1.data = null;
                length = length-1;
                return true;
            }
            current_Node1 = current_Node1.next;
        }
       return false;
    }


    public void set(int index, T value){
        Node1<T> new_Node1 = new Node1(value, null);
        Node1<T> leftNode1 = head;
        Node1<T> rightNode1 = head;
        if(head == null){
            head = new_Node1;
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



