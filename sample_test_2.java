import java.util.ArrayList;
import java.util.List;

class TreeNode {
    public int data;
    public TreeNode left, right;
    public TreeNode (int data_, TreeNode left_, TreeNode right_) {
        this.data = data_;
        this.left = left_;
        this.right = right_;
    }
}
public class sample_test_2 {
    private static void get_valList(TreeNode node, List<Integer> result){
        if(node==null){
            return;
        }
        else{
           get_valList(node.left, result);
           result.add(node.data);
           get_valList(node.right, result);
        }
    }

    public static int get_median_element(TreeNode bst_root){
        //inorder visit
        List<Integer> result = new ArrayList<>();
        get_valList(bst_root, result);
        int mid_index = result.size()/2;
        int mid_val = result.get(mid_index);
        return mid_val;
    }

    public static TreeNode delete_one_child_node(TreeNode root){
        if(root==null){
            return null;
        }
        root.left = delete_one_child_node(root.left);
        root.right = delete_one_child_node(root.right);
        if(root.left ==null&&root.right!=null){
            return root.right;
        }
        else if(root.right==null && root.left!=null){
            return root.left;
        }
        return root;

    }
    public static void main(String[] args) {
        System.out.println("");
        TreeNode root_1 = new TreeNode(20, new TreeNode(10, null, null), new TreeNode(30, null, null));
        System.out.println(get_median_element(root_1) == 20); // true

        TreeNode root_2 = new TreeNode(50, null, new TreeNode(60, null, new TreeNode(70, null, null)));
        System.out.println(get_median_element(root_2) == 60); // true
        TreeNode node_1 = new TreeNode(1, null, null);
TreeNode node_2 = new TreeNode(2, node_1, null);
TreeNode new_node = delete_one_child_node(node_2);
System.out.println(new_node == node_1); // true

TreeNode n3 = new TreeNode(3, null, null);
TreeNode n4 = new TreeNode(4, n3, null);
TreeNode n1 = new TreeNode(1, null, null);
TreeNode n6 = new TreeNode(6, n1, n4);
TreeNode n9 = new TreeNode(9, null, null);
TreeNode n2 = new TreeNode(2, null, n9);
TreeNode n7 = new TreeNode(7, null, n2);
TreeNode n5 = new TreeNode(5, n6, n7);
TreeNode new_root = delete_one_child_node(n5);
System.out.println(new_root == n5
&& new_root.left == n6
&& new_root.right == n9
&& new_root.left.left == n1
&& new_root.left.right == n3); // true
    }
}
