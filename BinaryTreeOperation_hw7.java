// package com.gradescope.cs201;
// import com.gradescope.cs201.Hw7_interface;
// import com.gradescope.cs201.BinaryTree;
class BinaryTree<T> {
    T data;
    BinaryTree<T> left;
    BinaryTree<T> right;
    public BinaryTree(T data, BinaryTree<T> left, BinaryTree<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
    public String print_to_string() {
    if (left == null && right == null)
        return "(" + data.toString() + ")";
    else
        return "(" + data.toString()
        + ((left != null)?left.print_to_string():"()")
        + ((right != null)?right.print_to_string():"()") + ")";
    }
}

interface Hw7_interface<T> {
    public BinaryTree<T> build_tree(String input_str);
    // Print the tree out with the old-fashion Windows style
    public String print_tree_in_vertical_format(BinaryTree<T> input_tree);
    // Print the tree out with the graphical style
    public String print_tree_in_horizontal_format (BinaryTree<T> input_tree);
}
public class BinaryTreeOperation_hw7<T> implements Hw7_interface<T> {
    public BinaryTree<T> build_tree(String input_str){
        BinaryTree<T> root = new BinaryTree(null, null, null);
        return root;
    }

    public String print_tree_in_vertical_format(BinaryTree<T> input_tree){
        String s = new String();
        return s;
    }

    public String print_tree_in_horizontal_format (BinaryTree<T> input_tree){
        String s = new String();
        return s;
    }
}
