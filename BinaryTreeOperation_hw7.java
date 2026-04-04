// package com.gradescope.cs201;
// import com.gradescope.cs201.Hw7_interface;
// import com.gradescope.cs201.BinaryTree;
import java.util.ArrayDeque;
import java.util.Deque;

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
        BinaryTree<T> result = new BinaryTree<>(null, null, null);
        Deque<BinaryTree<T> > bff = new ArrayDeque<>();
        bff.push(result);
        String node_data= new String();
        for(int i=1; i< input_str.length(); i++){
            char c = input_str.charAt(i);
            if(input_str.charAt(i) == '('){
                node_data = "";
                BinaryTree<T> new_node = new BinaryTree<>((T)"", null, null);
                if(input_str.charAt(i-1)==')'){
                    bff.peek().right = new_node;
                }
                else{
                    bff.peek().left = new_node;
                }
                bff.push(new_node);

            }
            else if(input_str.charAt(i) == ')'){
                bff.pop();
            }
            else{
                node_data += c;
                bff.peek().data = (T)node_data;
            }
        }
        return result;

    }


    public String print_tree_in_vertical_format(BinaryTree<T> input_tree){
        String finalString = new String();
        if(input_tree == null) return "";
        Deque<Object[]> stack = new ArrayDeque<>();
        stack.push(new Object[]{input_tree, "", true, true, false});
        while(!stack.isEmpty()){
            Object[] curr = stack.pop();
            BinaryTree<T> node = (BinaryTree<T>) curr[0];
            String prefix = (String) curr[1];
            boolean isRightTail = (boolean) curr[2];
            boolean isRoot = (boolean) curr[3];
            boolean isDummy = (boolean) curr[4];
            if (isRoot) {
                finalString += node.data + "\n";
            } else if (isDummy) {
                finalString += prefix + (isRightTail ? "|_" : "|_") + "\n";
                continue;

            } else {
                finalString += prefix + (isRightTail ? "|_" : "|_") + node.data + "\n";
            }
            String childPrefix = isRoot ? "" : prefix + (isRightTail ? "  " : "| ");
            boolean hasLeft = (node != null && node.left != null);
            boolean hasRight = (node != null && node.right != null);

            if (hasRight) {
                stack.push(new Object[]{node.right, childPrefix, true, false, false});
            }
            if (hasLeft || hasRight) {
                if (!hasLeft) {
                    stack.push(new Object[]{null, childPrefix, !hasRight, false, true});
                } else {
                    stack.push(new Object[]{node.left, childPrefix, !hasRight, false, false});
                }
            }
        }
        if (finalString.endsWith("\n")) {
            finalString = finalString.substring(0, finalString.length() - 1);
        }
        return finalString;
    }

   public String print_tree_in_horizontal_format(BinaryTree<T> input_tree) {
        if (input_tree == null) return "";

        Deque<BinaryTree<T>> queue = new ArrayDeque<>();
        String treeString = ""; 
        queue.offer(input_tree);
        
        while (!queue.isEmpty()) { 
            int tree_level = queue.size();
            for (int i = 0; i < tree_level; i++) {
                BinaryTree<T> tmp = queue.poll();
                treeString += tmp.data.toString(); 
                
                if (i < tree_level - 1) {
                    treeString += " ";
                }
                
                if (tmp.left != null) { queue.offer(tmp.left); }
                if (tmp.right != null) { queue.offer(tmp.right); }
            }
            if (!queue.isEmpty()) {
                treeString += "\n";
            }
        }
        return treeString;
    }

public static void main(String[] args) {
    // System.out.println("a ");

    // BinaryTreeOperation_hw7<Integer> bto_hw7_int = new BinaryTreeOperation_hw7<>();
    // BinaryTree<Integer> tree_1 = bto_hw7_int.build_tree("(1(2()(3(4)(5)))(6))");
    // System.out.println(bto_hw7_int.print_tree_in_vertical_format(tree_1));
}

}
