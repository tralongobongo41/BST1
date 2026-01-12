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
            recurseInsertAGirlfriend(root, value);
        }
    }

    public void recurseInsertAGirlfriend(TreeNode current, int value)
    {
        if(current.data > value)
        {
            if(current.left == null)
            {
                current.left = new TreeNode(value);
            }
            else
            {
                recurseInsertAGirlfriend(current.left, value);
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
                recurseInsertAGirlfriend(current.right, value);
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
        if(current.data == value)
            return true;
        else if(current.data > value && current.left != null)
        {
            current = current.left;
            return search(value);
        }
        else if(current.data < value && current.right != null)
        {
            current = current.right;
            return search(value);
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

        boolean valueExists = search(value);

        if(!valueExists)
        {
            return;
        }

        if(current.data == value)
        {
            deleteHelper(value, );
        }
        else if(current.data > value && current.left != null)
        {

        }
        else if(current.data < value && current.right != null)
        {

        }




    }

    public void deleteHelper(int value)
    {
        if(current.left != null && current.right != null)

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

        boolean b = true;
        while(b)
        {
            if (current.left != null)
            {
                current = current.left;
            }
            else
            {
                b = false;
            }
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

        boolean b = true;
        while(b)
        {
            if (current.right != null)
            {
                current = current.right;
            }
            else
            {
                b = false;
            }
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
    }

    /**
     * Perform a preorder traversal of the BST.
     *
     * @return A list of values in preorder sequence
     */
    public List preorderTraversal() {
        // TODO: Implement this method
        // Hint: Root -> Left -> Right
    }

    /**
     * Perform a postorder traversal of the BST.
     *
     * @return A list of values in postorder sequence
     */
    public List postorderTraversal() {
        // TODO: Implement this method
        // Hint: Left -> Right -> Root
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
    }

    /**
     * Count the number of nodes in the BST.
     *
     * @return The number of nodes
     */
    public int size() {
        // TODO: Implement this method
        // Hint: Recursively count nodes
    }

    /**
     * Check if the BST is empty.
     *
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        // TODO: Implement this method
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