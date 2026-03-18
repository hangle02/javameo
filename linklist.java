
import java.util.Comparator;

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

    public void reverse();

    public void delete_duplicate();

    public void printlinklist();

    public void linklistSort(Comparator<T> comparator);

    public boolean checkCircular();

    public T getMid();
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

    public void reverse(){
        //check linked list
        if(length<2 || head == null){
            return;
        }
        Node1<T> preNode = null;
        Node1<T> curNode = head;
        Node1<T> nextNode = null;
        while( curNode!=null){
            nextNode = curNode.next;
            curNode.next = preNode;
            preNode = curNode;
            curNode = nextNode;
        }
        head = preNode;
    }

    public void delete_duplicate(){
        Node1<T> curNode = head;
        if(length <2 || head == null){
            return;
        }
        while(curNode!= null && curNode.next != null){
            if(curNode.data.equals(curNode.next.data) ){
                curNode.next = curNode.next.next;
                length--;
            }
            else{curNode = curNode.next;}

        }

    }

    public void printlinklist(){
        Node1<T> curNode = head;
        while(curNode!= null){
            IO.println(curNode.data);
            curNode = curNode.next;
        }
    }

    public void linklistSort(Comparator<T> comparator){
       
        if(head == null || length<2){return;}

        boolean swapped=true;

        while(swapped){
            Node1<T> curNode = head;
            swapped = false;
            while(curNode!=null && curNode.next!=null){
                T curdata = curNode.data;
                T nextdata = curNode.next.data;

                if(comparator.compare(curdata, nextdata)>0 ){
                    curNode.data = nextdata;
                    curNode.next.data = curdata;
                    swapped = true;
                }
                curNode = curNode.next;
            }
        }
    }

    public boolean checkCircular(){
        if(head == null || length <2){
            return false;
        }

        Node1<T> slow = head;
        Node1<T> fast = head.next;

        while(fast!= null  && fast.next!=null){
            if(slow == fast){
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;

        }
        return false;
    }

    public T getMid(){
        if(head==null){
            return null;
        }
        Node1<T> slow = head;
        Node1<T> fast = head;

        while(fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow.data;
    }

    public static void main(String[] args) {
        linklist<Integer> list_1 = new linklist<>();
        list_1.add(1);
        list_1.add(2);
        list_1.add(3);

        list_1.clear();

        //list_1.add(-1);
        list_1.add(10);
        list_1.add(6);
        list_1.add(1);
        list_1.add(1);
        list_1.add(1);

        list_1.linklistSort((a,b)-> a-b);
        list_1.printlinklist();

    }

}



