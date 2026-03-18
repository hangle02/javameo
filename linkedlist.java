


interface List2<T>{
    //add value
    public void add(T value);

    //add(index, value)
    public boolean addbyIndex(int index, T value);

    public boolean contains(T value);

    public int indexOf(T value);

    public boolean isEmpty();

    public boolean remove(T value);

    public void clear();

    public T getIndex(int index);

    public boolean removeI(int index);

    public int size();

    // public void set(int index, T value);

    public void reverse();

    public void delete_duplicate();

    public void printlinklist();

    // public void linklistSort(Comparator<T> comparator);

    public boolean checkCircular();

    // public T getMid();
}

class Node2<T>{
    T data;
    Node2<T> next;
    Node2(T data_, Node2<T> next_){
        data = data_;
        next = next_;
    }
}

public class linkedlist<T> implements List2<T> {
    Node2<T> head;
    int length;

    public linkedlist(){
        head = null;
        length = 0;
    }

        //add value
    public void add(T value){
        Node2<T> newNode = new Node2(value, null);
        length++;
        if(head == null){
            head = newNode;
        }
        else{
        Node2<T> curNode = head;

        while(curNode.next!=null){
            curNode = curNode.next;
        }
        curNode.next = newNode;
        }
    }

    //add(index, value)
    public boolean  addbyIndex(int index, T value){
        Node2<T> newNode = new Node2(value, null);
        if(index >= length){
            return false;
        }
        Node2<T> curNode = head;
        int curIndex = 0;
        while(curNode.next!=null){
            if(curIndex == index-1){
                newNode.next = curNode.next;
                curNode.next = newNode;
                return true;
            }
            curNode = curNode.next;
            curIndex++;
        }
        return false;
    }

    public boolean contains(T value){
        if(head == null){
            return false;
        }
        Node2<T> curNode = head;
        while(curNode!=null){
            if(curNode.data.equals(value)){
                return true;
            }
            curNode = curNode.next;
        }
        return false;
    }

    public int indexOf(T value){
        if(head == null){
            return -1;
        }
        Node2<T> curNode = head;
        int curIndex = 0;
        while(curNode!=null){
            if(curNode.data.equals(value)){
                return curIndex;
            }
            curNode = curNode.next;
            curIndex++;
        }
        return -1;
    }

    public boolean isEmpty(){
        return (head==null && length ==0);
    }

    public boolean remove(T value){
        if(head==null){return false;}

        return false;
    }

    public void clear(){
        length =0;
        head = null;
    }

    public T getIndex(int index){
        Node2<T> curNode = head;
        int curIndex =0;
        while(curNode!= null){
            if(curIndex == index){
                return curNode.data;
            }
            curNode=curNode.next;
            curIndex++;
        }
        return null;
    }

    public boolean removeI(int index){
        if(index >= length){
            return false;
        }
        if(index ==0){
            head = head.next;
            length--;
            return true;
        }
        Node2<T> curNode = head;
        int curIndex =0;
        while (curNode!=null && curNode.next!=null) {
            if(curIndex==(index-1)){
                curNode.next = curNode.next.next;
                length--;
                return true;
            }
            curNode = curNode.next;
            curIndex++; 
        }
        return false;
    }

    public int size(){
        return length;
    }

    // public void set(int index, T value){}

    public void reverse(){
        if(head == null||length<2){return;}
        Node2<T> preNode = null;
        Node2<T> curNode = head;
        Node2<T> nextNode = null;

        while(curNode!=null){
            nextNode = curNode.next;
            curNode.next = preNode;
            preNode = curNode;
            curNode = nextNode;
        }
        head = preNode;
    }

    public void delete_duplicate(){
        if(head==null||length<2){
            return;
        }
        Node2<T> curNode = head;
        while(curNode!=null && curNode.next!=null){
            if(curNode.data.equals(curNode.next.data)){
                curNode.next = curNode.next.next;
                length--;
            }
            else{curNode = curNode.next;}
        }
    }

    public void printlinklist(){
        if(head == null){
            return;
        }
        Node2<T> curNode = head;
        while(curNode!=null){
            IO.println(curNode.data);
            curNode = curNode.next;
        }
    }

    // public void linklistSort(Comparator<T> comparator){}

    public boolean checkCircular(){
        if(head == null || length<2){
            return false;
        }
        Node2<T> slow = head;
        Node2<T> fast = head.next;
        while(fast!=null && fast.next != null){
            if(slow == fast){
                return true;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        return false;
    }

    public T getMid(){
        if(head==null){return null;}
        Node2<T> slow = head;
        Node2<T> fast = head;
        while(fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow.data;
    }
    public static void main(String[] args) {
        linkedlist<Integer> list_1 = new linkedlist<>();
        list_1.add(1);
        list_1.add(2);
        list_1.add(3);

        list_1.add(10);
        list_1.add(6);
        // IO.println(list_1.addbyIndex(1, 5));
        // IO.println(list_1.contains(10));
        // IO.println(list_1.contains(11)==false);
        IO.println(list_1.indexOf(2)==1);
        list_1.removeI(0);
        list_1.printlinklist();
    }
}
