interface Stack<T> {
    // isEmpty: check if the stack is empty
    public boolean isEmpty();
    
    // push: put a new item on top of the stack
    public void push(T item);

    // pop: remove the top item from the top of the stack and return it
    public T pop();
    
    // peek: return the top item from the stack, but don't remove it from the stack
    public T peek();

};

class ArrayStack<T> implements Stack<T> {
    T[] item_array;
    int item_number;
    public ArrayStack(int max_size) {
        //item_array = new T[max_size];
        item_array = (T[]) new Object[max_size];
        item_number = 0;
    }

    public boolean isEmpty() {
        return (item_number == 0);
    }

    public void push(T item) {
        item_array[item_number] = item;
        item_number++;
    }

    public T pop() {
        item_number--;
        return item_array[item_number];
    }

    public T peek() {
        return item_array[item_number - 1];
    }
}


interface Queue<T> {
    // isEmpty: check if the queue is empty
    public boolean isEmpty();
    
    // enqueue: put a new item to the end of the queue
    public void enqueue(T item);

    // dequeue: remove the item from the head of the queue and return it
    public T dequeue();
    
    // peek: return the item from the head of the queue, but don't remove it
    public T peek();

};



public class lecture_6 {
    public static void main(String[] args) {
        ArrayStack<Integer> stack_1 = new ArrayStack(100);

        stack_1.push(1);
        stack_1.push(2);
        stack_1.push(3);
        stack_1.push(4);

        stack_1.pop();
        stack_1.peek();
        IO.println(stack_1.peek());
        //boolean[] tmp = new boolean[100];
        //IO.println(tmp[50]);
    }
}

//item1 item2   