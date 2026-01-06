/**
 * TreeNode class representing a single node in the Binary Search Tree.
 * DO NOT MODIFY THIS CLASS.
 */
class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;

    /**
     * Constructor to create a new tree node.
     * @param data The integer value to store in this node
     */
    public TreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}