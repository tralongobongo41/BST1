import java.util.ArrayList;
import java.util.List;

/**
 * Binary Search Tree implementation in Java.
 *
 * @author [Aaron Tralongo]
 * @date [1-6-2026]
 */
public class BinarySearchTree {
    private TreeNode root;

    /**
     * Constructor - initializes an empty BST.
     */
    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Insert a value into the BST.
     * If the value already exists, do not insert it (no duplicates allowed).
     *
     * @param value The value to insert
     */
    public void insert(int value) {
        // TODO: Implement this method
        // Hint: Use a recursive helper method

        if(root == null)
        {
            root = new TreeNode(value);
        }
        else if(!search(value))
        {
            recurseInsert(root, value);
        }
    }

    public void recurseInsert(TreeNode current, int value)
    {
        if(current.data > value)
        {
            if(current.left == null)
            {
                current.left = new TreeNode(value);
            }
            else
            {
                recurseInsert(current.left, value);
            }
        }
        if(current.data < value)
        {
            if(current.right == null)
            {
                current.right = new TreeNode(value);
            }
            else
            {
                recurseInsert(current.right, value);
            }
        }


    }

    /**
     * Search for a value in the BST.
     *
     * @param value The value to search for
     * @return true if the value exists in the tree, false otherwise
     */
    private TreeNode current = root;

    public boolean search(int value) {
        // TODO: Implement this method
        // Hint: Use recursion and leverage BST property

        return searchRecurse(value, root);
    }

    public boolean searchRecurse(int value, TreeNode current){
        if(current == null)
            return false;
        if(current.data == value)
            return true;
        if(current.data > value && current.left != null)
        {
            current = current.left;
            return searchRecurse(value, current.right);
        }
        else if(current.data < value && current.right != null)
        {
            current = current.right;
            return searchRecurse(value, current.left);
        }
        return false;
    }

    /**
     * Delete a value from the BST.
     * If the value doesn't exist, do nothing.
     *
     * @param value The value to delete
     */
    public void delete(int value) {
        // TODO: Implement this method
        // Hint: Handle three cases - leaf, one child, two children
        // For two children, use inorder successor or predecessor

        //does this value exist?
        boolean valueExists = search(value);

        //if value doesn't exist, end method; otherwise, continue
        if(!valueExists)
        {
            return;
        }

        //call the helper to make a node that goes one before the one we want to delete
        TreeNode nodeOneBefore = nodeOneBefore(current, value);

        //check which side the node is on
        //left?
        if(nodeOneBefore.left != null && nodeOneBefore.left.data == value)
        {
            //check which case it is
            if(nodeOneBefore.left.left == null && nodeOneBefore.left.right == null) //case 1: leaf
                nodeOneBefore.left = null; //delete the node

            else if(nodeOneBefore.left.left == null) //case 2: one child (on the right)
                //connect the nodeOneBefore to the child on the right
                nodeOneBefore.left = nodeOneBefore.left.right;
            else if(nodeOneBefore.left.right == null) //case 2: one child (on the left)
                //connect the nodeOneBefore to the child on the left
                nodeOneBefore.left = nodeOneBefore.left.left;

            else if(nodeOneBefore.left.left != null && nodeOneBefore.left.right != null) //case 3: two children
                deleteWithTwoChildren(nodeOneBefore.left);
        }

        //right?
        else if(nodeOneBefore.right != null && nodeOneBefore.right.data == value)
        {
            //check which case it is
            if(nodeOneBefore.right.left == null && nodeOneBefore.right.right == null) //leaf case
                nodeOneBefore.right = null; //delete the node

            else if(nodeOneBefore.right.left == null) //case 2: one child (on the right)
                //connect the nodeOneBefore to the child on the right
                nodeOneBefore.right = nodeOneBefore.right.right;
            else if(nodeOneBefore.right.right == null) //case 2: one child (on the left)
                //connect the nodeOneBefore to the child on the left
                nodeOneBefore.right = nodeOneBefore.right.left;

            else if(nodeOneBefore.right.left != null && nodeOneBefore.right.right != null) //case 3: two children
                deleteWithTwoChildren(nodeOneBefore.right);
        }

    }

    //helper method for delete that handles the two-children case
    public void deleteWithTwoChildren(TreeNode replacing)
    {
        TreeNode oneBeforeCurrent = replacing;
        current = replacing.left;
        while(current.right != null)
        {
            current = current.right;
        }

        //save the value of current node
        int replacementValue = current.data;

        //call delete method for current node
        delete(current.data);

        //set data at replace node to replacementValue
        replacing.data = replacementValue;
    }


    //helper method for delete that goes to the node one before the deletion node
    public TreeNode nodeOneBefore(TreeNode current, int value)
    {
        if(current.data != value) {
            if (current.data > value && current.left != null) {
                if(current.left.data == value)
                    return current;
                return nodeOneBefore(current.left, value);
            }
            else if (current.data < value && current.right != null) {
                if (current.right.data == value)
                    return current;
                return nodeOneBefore(current.right, value);
            }
        }
        return current;
    }

    /**
     * Find the minimum value in the BST.
     *
     * @return The minimum value
     * @throws IllegalStateException if the tree is empty
     */
    public int findMin() {
        // TODO: Implement this method
        // Hint: Keep going left!
        current = root;

        boolean b = true;
        while(b)
        {
            if (current.left != null)
                current = current.left;
            else
                b = false;
        }
        return current.data;
    }

    /**
     * Find the maximum value in the BST.
     *
     * @return The maximum value
     * @throws IllegalStateException if the tree is empty
     */
    public int findMax() {
        // TODO: Implement this method
        // Hint: Keep going right!
        current = root;

        boolean b = true;
        while(b)
        {
            if (current.right != null)
                current = current.right;
            else
                b = false;
        }
        return current.data;
    }

    /**
     * Perform an inorder traversal of the BST.
     *
     * @return A list of values in inorder sequence
     */
    public List inorderTraversal() {
        // TODO: Implement this method
        // Hint: Left -> Root -> Right
        // Should return values in sorted order!

        List<TreeNode> values = new ArrayList<>();

        inorderHelper(root, values);

        return values;
    }

    public void inorderHelper(TreeNode current, List values)
    {
        if(current == null)
            return;

        if(current.left != null)
            inorderHelper(current.left, values);

        values.add(current.data);

        if(current.right != null)
            inorderHelper(current.right, values);
    }

    /**
     * Perform a preorder traversal of the BST.
     *
     * @return A list of values in preorder sequence
     */
    public List preorderTraversal() {
        // TODO: Implement this method
        // Hint: Root -> Left -> Right

        List<TreeNode> values = new ArrayList<>();

        inorderHelper(root, values);

        return values;
    }

    public void preorderHelper(TreeNode current, List values)
    {
        if(current == null)
            return;

        values.add(current.data);

        if(current.left != null)
            inorderHelper(current.left, values);

        if(current.right != null)
            inorderHelper(current.right, values);
    }

    /**
     * Perform a postorder traversal of the BST.
     *
     * @return A list of values in postorder sequence
     */
    public List postorderTraversal() {
        // TODO: Implement this method
        // Hint: Left -> Right -> Root

        List<TreeNode> values = new ArrayList<>();

        inorderHelper(root, values);

        return values;
    }

    public void postorderHelper(TreeNode current, List values)
    {
        if(current == null)
            return;

        if(current.left != null)
            inorderHelper(current.left, values);

        if(current.right != null)
            inorderHelper(current.right, values);

        values.add(current.data);
    }

    /**
     * Calculate the height of the BST.
     * Height is defined as the number of edges on the longest path from root to leaf.
     * An empty tree has height -1, a tree with one node has height 0.
     *
     * @return The height of the tree
     */
    public int height() {
        // TODO: Implement this method
        // Hint: Use recursion - height = 1 + max(left height, right height)
        return heightHelperMethod(root);
    }

    public int heightHelperMethod(TreeNode current)
    {
        if(current == null)
            return -1; //empty tree

        int left = heightHelperMethod(current.left);
        int right = heightHelperMethod(current.right);

        return 1 + Math.max(left, right);
    }

    /**
     * Count the number of nodes in the BST.
     *
     * @return The number of nodes
     */

    private int size;

    public int size() {
        // TODO: Implement this method
        // Hint: Recursively count nodes

        size = 0;

        sizeHelper(current);

        return size;
    }

    public void sizeHelper(TreeNode current)
    {
        if(current == null)
            return;

        if(current.left != null)
            sizeHelper(current.left);

        if(current.right != null)
            sizeHelper(current.right);
    }

    /**
     * Check if the BST is empty.
     *
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        // TODO: Implement this method
        if(root == null)
            return true;
        return false;
    }

    /**
     * Get the root of the tree (for testing purposes).
     *
     * @return The root node
     */
    public TreeNode getRoot() {
        return this.root;
    }

    // ========================================
    // HELPER METHODS
    // You may add private helper methods below
    // ========================================

    // Example helper method structure:
    // private TreeNode insertHelper(TreeNode node, int value) {
    //     // Your code here
    // }

}