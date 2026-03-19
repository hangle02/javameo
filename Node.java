// package com.gradescope.cs201;
public class Node {
    public int data;
    public Node next;
    public Node (int data_, Node next_){
        this.data=data_;
        this.next=next_;
    }
    public static int get_first_even(Node head){
        if(head == null){return -1;}
        Node curNode = head;
        int curIndex =0;
        while(curNode!=null){
            if(curNode.data%2==0){
                return curIndex;
            }
            curIndex++;
            curNode = curNode.next;
        }
        return -1;
    }
    public static Node delete_last_even(Node head){
        if(head == null){return head;}
        Node curNode = head;
        int curIndex =0;
        int last_even_index = -1;
        while(curNode!=null){
            if(curNode.data%2==0){
                last_even_index = curIndex;
            }
            curNode = curNode.next;
            curIndex++;
        }
        if(last_even_index==-1){return head;}
        if(last_even_index==0){
            head = head.next;
        }
        else{
            curNode = head;
            curIndex = 0;
            while(curNode!=null && curNode.next!=null){
                if(curIndex==(last_even_index-1)){
                    curNode.next = curNode.next.next;
                }
                curNode = curNode.next;
                curIndex ++;
            }
        }
        return head;
    }
    public static void main(String[] args) {
        // Node x1 = new Node (30, null);
        // Node y1 = new Node (10,x1);
        // Node n1 = delete_last_even(y1);
        // IO.println(n1==y1&&n1.next==null);
    }
}
