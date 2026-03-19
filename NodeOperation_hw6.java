// //package com.gradescope.cs201;

// interface Hw6_interface<T> {
//     public Node<T> reverse(Node<T> input_node);
//     public void delete_duplicate(Node<T> input_node);
// }
// class Node<T> {
//     T data;
//     Node<T> next;
//     public Node(T _data, Node<T> _next) {
//         data = _data;
//         next = _next;
//     }
// }
// public class NodeOperation_hw6<T> implements Hw6_interface<T> {
//     public Node<T> reverse(Node<T> input_node){
//         Node<T> head = input_node;
//         if(head==null|| head.next==null){
//             return input_node;
//         }
//         Node<T> preNode = null;
//         Node<T> curNode = head;
//         Node<T> nextNode = null;

//         while(curNode!=null){
//             nextNode = curNode.next;
//             curNode.next = preNode;
//             preNode = curNode;
//             curNode = nextNode;
//         }
//         return preNode;
//     }
//     public void delete_duplicate(Node<T> input_node){
//         Node<T> head = input_node;
//         if(head==null|| head.next==null){
//             return;}
        
//         Node<T> curNode = head;
//         //Node<T> nextNode = null;

//         while(curNode!=null && curNode.next!=null){
//             if(curNode.data.equals(curNode.next.data)){
//                 curNode.next = curNode.next.next;
//             }
//             else{
//                 curNode = curNode.next;
//             }
            
//         }
//     }
//     public static void main(String[] args) {
        
//     }
// }
