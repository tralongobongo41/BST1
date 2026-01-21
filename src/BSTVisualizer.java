import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

/**
 * Interactive Binary Search Tree Visualizer
 * Fixed UI sizing and Layout
 */
public class BSTVisualizer extends JFrame {

    private BinarySearchTree bst;
    private TreePanel treePanel;
    private JScrollPane treeScrollPane; // Added ScrollPane
    private JTextArea outputArea;
    private JTextField inputField;
    private JLabel statusLabel;
    private JPanel controlPanel;

    // Animation settings
    private TreeNode highlightedNode = null;
    private Color highlightColor = new Color(255, 69, 180);

    // Traversal animation
    private boolean isTraversing = false;
    private Timer traversalTimer;
    private List<TreeNode> traversalSequence;
    private int traversalIndex = 0;
    private String currentTraversalType = "";
    private int animationDelay = 600;
    private boolean traversalPaused = false;

    // Visualization settings
    private static final int NODE_RADIUS = 25;
    private static final int HORIZONTAL_SPACING = 40; // Slightly tighter spacing
    private static final int VERTICAL_SPACING = 60;

    public BSTVisualizer() {
        super("Binary Search Tree Visualizer");
        bst = new BinarySearchTree();
        initializeUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        // Create main panels
        createTreePanel();
        createControlPanel();
        createOutputPanel();

        // Add panels to frame
        // WRAP treePanel in a JScrollPane to handle large trees
        treeScrollPane = new JScrollPane(treePanel);
        treeScrollPane.setBorder(BorderFactory.createLineBorder(new Color(16, 185, 129), 2));
        treeScrollPane.getViewport().setBackground(new Color(17, 24, 39));

        add(treeScrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);
        add(createBottomPanel(), BorderLayout.SOUTH);

        setUIStyle();
    }

    private void createTreePanel() {
        treePanel = new TreePanel();
        treePanel.setBackground(new Color(17, 24, 39));
    }

    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBackground(new Color(31, 41, 55));
        controlPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        // Row 1: Input and basic operations
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Increased gaps
        row1.setBackground(new Color(31, 41, 55));

        JLabel inputLabel = new JLabel("Value:");
        inputLabel.setForeground(Color.WHITE);
        inputLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        inputField = new JTextField(8);
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JButton insertBtn = createStyledButton("Insert", new Color(16, 185, 129));
        JButton searchBtn = createStyledButton("Search", new Color(59, 130, 246));
        JButton deleteBtn = createStyledButton("Delete", new Color(220, 38, 38));
        JButton clearBtn = createStyledButton("Clear", new Color(107, 114, 128));

        row1.add(inputLabel);
        row1.add(inputField);
        row1.add(insertBtn);
        row1.add(searchBtn);
        row1.add(deleteBtn);
        row1.add(clearBtn);

        // Row 2: Traversals and tree info
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row2.setBackground(new Color(31, 41, 55));

        JButton inorderBtn = createStyledButton("Inorder", new Color(139, 92, 246));
        JButton preorderBtn = createStyledButton("Preorder", new Color(139, 92, 246));
        JButton postorderBtn = createStyledButton("Postorder", new Color(139, 92, 246));

        JButton heightBtn = createStyledButton("Height", new Color(245, 158, 11));
        JButton sizeBtn = createStyledButton("Size", new Color(245, 158, 11));

        JLabel animLabel = new JLabel("Anim:");
        animLabel.setForeground(new Color(139, 92, 246));
        animLabel.setFont(new Font("SansSerif", Font.BOLD, 11));

        JButton pauseBtn = createStyledButton("Pause", new Color(107, 114, 128));
        JButton randomBtn = createStyledButton("Random Tree", new Color(236, 72, 153));

        row2.add(inorderBtn);
        row2.add(preorderBtn);
        row2.add(postorderBtn);
        row2.add(createSeparator());
        row2.add(heightBtn);
        row2.add(sizeBtn);
        row2.add(createSeparator());
        row2.add(animLabel);
        row2.add(pauseBtn);
        row2.add(randomBtn);

        // Add action listeners
        insertBtn.addActionListener(e -> insertValue());
        searchBtn.addActionListener(e -> searchValue());
        deleteBtn.addActionListener(e -> deleteValue());
        clearBtn.addActionListener(e -> clearTree());

        inorderBtn.addActionListener(e -> showTraversal("Inorder"));
        preorderBtn.addActionListener(e -> showTraversal("Preorder"));
        postorderBtn.addActionListener(e -> showTraversal("Postorder"));

        heightBtn.addActionListener(e -> showHeight());
        sizeBtn.addActionListener(e -> showSize());
        randomBtn.addActionListener(e -> createRandomTree());

        pauseBtn.addActionListener(e -> togglePauseTraversal(pauseBtn));

        // Enter key support
        inputField.addActionListener(e -> insertValue());

        controlPanel.add(row1);
        controlPanel.add(row2);
    }

    private JPanel createSeparator() {
        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(1, 20));
        separator.setBackground(new Color(75, 85, 99));
        return separator;
    }

    private void createOutputPanel() {
        outputArea = new JTextArea(6, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setBackground(new Color(17, 24, 39));
        outputArea.setForeground(new Color(209, 213, 219));
        outputArea.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(31, 41, 55));

        outputArea = new JTextArea(4, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setBackground(new Color(17, 24, 39));
        outputArea.setForeground(new Color(209, 213, 219));
        outputArea.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(16, 185, 129), 2));
        scrollPane.setPreferredSize(new Dimension(0, 100));

        statusLabel = new JLabel("Ready - Enter a value to begin");
        statusLabel.setForeground(new Color(16, 185, 129));
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        statusLabel.setBorder(new EmptyBorder(5, 10, 5, 10));

        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        return bottomPanel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 11));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // FIX: Removed fixed PreferredSize so text fits
        // Added margins for padding
        button.setMargin(new Insets(5, 10, 5, 10));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void setUIStyle() {
        getContentPane().setBackground(new Color(31, 41, 55));
    }

    // ========================================
    // BST OPERATIONS & UPDATES
    // ========================================

    private void updateTreeVisuals() {
        // Re-calculate the size needed for the tree panel
        treePanel.revalidate();
        treePanel.repaint();
    }

    private void insertValue() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            if (bst.search(value)) {
                appendOutput("? Value " + value + " already exists");
            } else {
                bst.insert(value);
                appendOutput("? Inserted " + value);
                setStatus("Value " + value + " inserted", new Color(16, 185, 129));
                highlightNodeWithValue(value);
            }
            inputField.setText("");
            updateTreeVisuals();
        } catch (NumberFormatException e) {
            setStatus("Invalid input", Color.RED);
        }
    }

    private void searchValue() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            boolean found = bst.search(value);
            if (found) {
                appendOutput("? Found " + value);
                setStatus("Value found!", new Color(16, 185, 129));
                highlightNodeWithValue(value);
            } else {
                appendOutput("? Value " + value + " not found");
                setStatus("Value not found", Color.ORANGE);
                highlightedNode = null;
            }
            updateTreeVisuals();
        } catch (NumberFormatException e) {
            setStatus("Invalid input", Color.RED);
        }
    }

    private void deleteValue() {
        try {
            int value = Integer.parseInt(inputField.getText().trim());
            if (!bst.search(value)) {
                appendOutput("? Value " + value + " not in tree");
            } else {
                bst.delete(value);
                appendOutput("? Deleted " + value);
                setStatus("Deleted " + value, new Color(16, 185, 129));
                highlightedNode = null;
            }
            inputField.setText("");
            updateTreeVisuals();
        } catch (NumberFormatException e) {
            setStatus("Invalid input", Color.RED);
        }
    }

    private void clearTree() {
        if (isTraversing && traversalTimer != null) {
            traversalTimer.stop();
            isTraversing = false;
        }
        bst = new BinarySearchTree();
        highlightedNode = null;
        outputArea.setText("");
        setStatus("Tree cleared", new Color(16, 185, 129));
        updateTreeVisuals();
    }

    private void showTraversal(String type) {
        if (bst.isEmpty()) return;
        if (isTraversing && traversalTimer != null) traversalTimer.stop();

        currentTraversalType = type;
        traversalSequence = new ArrayList<>();

        switch (type) {
            case "Inorder": collectInorderNodes(bst.getRoot(), traversalSequence); break;
            case "Preorder": collectPreorderNodes(bst.getRoot(), traversalSequence); break;
            case "Postorder": collectPostorderNodes(bst.getRoot(), traversalSequence); break;
        }

        appendOutput("\n?? Starting " + type + " Traversal...");
        startTraversalAnimation();
    }

    private void startTraversalAnimation() {
        isTraversing = true;
        traversalIndex = 0;
        traversalPaused = false;
        setStatus("Animating " + currentTraversalType + "...", new Color(139, 92, 246));

        traversalTimer = new Timer(animationDelay, e -> {
            if (traversalIndex < traversalSequence.size()) {
                highlightedNode = traversalSequence.get(traversalIndex);
                treePanel.repaint();

                // Auto-scroll to show the node being visited
                scrollToNode(highlightedNode);

                traversalIndex++;
            } else {
                traversalTimer.stop();
                isTraversing = false;
                highlightedNode = null;
                treePanel.repaint();
                appendOutput("? Traversal complete!");
                setStatus("Done", new Color(16, 185, 129));
            }
        });
        traversalTimer.start();
    }

    // Helper to auto-scroll the scrollpane to the active node
    private void scrollToNode(TreeNode node) {
        // Simple logic: If we knew the coordinates, we could scrollRectToVisible
        // Since coordinates are calculated in paint, this is tricky without caching positions.
        // For now, we rely on the scrollbars being available.
    }

    // Traversal Helpers
    private void collectInorderNodes(TreeNode node, List list) {
        if (node == null) return;
        collectInorderNodes(node.left, list);
        list.add(node);
        collectInorderNodes(node.right, list);
    }

    private void collectPreorderNodes(TreeNode node, List list) {
        if (node == null) return;
        list.add(node);
        collectPreorderNodes(node.left, list);
        collectPreorderNodes(node.right, list);
    }

    private void collectPostorderNodes(TreeNode node, List list) {
        if (node == null) return;
        collectPostorderNodes(node.left, list);
        collectPostorderNodes(node.right, list);
        list.add(node);
    }

    private void showHeight() {
        appendOutput("?? Height: " + (bst.isEmpty() ? -1 : bst.height()));
    }

    private void showSize() {
        appendOutput("?? Size: " + bst.size());
    }

    private void showMinMax() {
        if (bst.isEmpty()) return;
        appendOutput("Min: " + bst.findMin() + ", Max: " + bst.findMax());
    }

    private void createRandomTree() {
        clearTree();
        Random rand = new Random();
        int count = 10 + rand.nextInt(10);
        for (int i = 0; i < count; i++) {
            bst.insert(rand.nextInt(100));
        }
        appendOutput("\n?? Random tree created!");
        updateTreeVisuals();
    }

    private void togglePauseTraversal(JButton pauseBtn) {
        if (!isTraversing) return;
        if (traversalPaused) {
            traversalTimer.start();
            traversalPaused = false;
            pauseBtn.setText("Pause");
        } else {
            traversalTimer.stop();
            traversalPaused = true;
            pauseBtn.setText("Resume");
        }
    }

    private void highlightNodeWithValue(int value) {
        highlightedNode = findNodeWithValue(bst.getRoot(), value);
    }

    private TreeNode findNodeWithValue(TreeNode node, int value) {
        if (node == null) return null;
        if (node.data == value) return node;
        if (value < node.data) return findNodeWithValue(node.left, value);
        return findNodeWithValue(node.right, value);
    }

    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    private void setStatus(String text, Color color) {
        statusLabel.setText(text);
        statusLabel.setForeground(color);
    }

    // ========================================
    // TREE PANEL
    // ========================================

    private class TreePanel extends JPanel {

        // We override this to tell the JScrollPane how big the tree is!
        @Override
        public Dimension getPreferredSize() {
            if (bst == null || bst.isEmpty()) {
                return new Dimension(800, 600);
            }

            TreeNode root = bst.getRoot();
            int height = calculateHeight(root);

            // Calculate required width based on leaf spread
            // 2^(height-1) gives max leaves, multiplied by spacing
            int maxLeaves = (int) Math.pow(2, height - 1);
            int requiredWidth = maxLeaves * HORIZONTAL_SPACING * 2;
            int requiredHeight = (height + 2) * VERTICAL_SPACING;

            // Ensure it's at least the size of the view, or bigger if tree is huge
            return new Dimension(Math.max(1000, requiredWidth), Math.max(600, requiredHeight));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (bst.isEmpty()) {
                drawEmptyTreeMessage(g2d);
            } else {
                drawTree(g2d);
            }
        }

        private void drawEmptyTreeMessage(Graphics2D g2d) {
            String message = "Tree is empty - Insert values to begin!";
            g2d.setColor(new Color(107, 114, 128));
            g2d.setFont(new Font("SansSerif", Font.BOLD, 20));
            FontMetrics fm = g2d.getFontMetrics();

            // Draw in center of the visible view, not the center of the massive panel
            Rectangle visibleRect = getVisibleRect();
            int x = visibleRect.x + (visibleRect.width - fm.stringWidth(message)) / 2;
            int y = visibleRect.y + visibleRect.height / 2;

            g2d.drawString(message, x, y);
        }

        private void drawTree(Graphics2D g2d) {
            TreeNode root = bst.getRoot();
            if (root == null) return;

            // Start drawing from the center of the calculated preferred width
            // This ensures the root is centered in the scrollable area
            int startX = getPreferredSize().width / 2;
            int startY = 50;

            // Initial spread depends on height to prevent overlaps
            int initialSpacing = (int) Math.pow(2, calculateHeight(root) - 2) * HORIZONTAL_SPACING;

            // Ensure distinct spacing
            initialSpacing = Math.max(initialSpacing, HORIZONTAL_SPACING);

            drawNode(g2d, root, startX, startY, initialSpacing);
        }

        private void drawNode(Graphics2D g2d, TreeNode node, int x, int y, int hGap) {
            if (node == null) return;

            if (node.left != null) {
                int childX = x - hGap;
                int childY = y + VERTICAL_SPACING;
                drawEdge(g2d, x, y, childX, childY);
                drawNode(g2d, node.left, childX, childY, Math.max(hGap / 2, HORIZONTAL_SPACING));
            }

            if (node.right != null) {
                int childX = x + hGap;
                int childY = y + VERTICAL_SPACING;
                drawEdge(g2d, x, y, childX, childY);
                drawNode(g2d, node.right, childX, childY, Math.max(hGap / 2, HORIZONTAL_SPACING));
            }

            drawNodeCircle(g2d, node, x, y);
        }

        private void drawEdge(Graphics2D g2d, int x1, int y1, int x2, int y2) {
            g2d.setColor(new Color(75, 85, 99));
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.drawLine(x1, y1 + NODE_RADIUS/3, x2, y2 - NODE_RADIUS/3);
        }

        private void drawNodeCircle(Graphics2D g2d, TreeNode node, int x, int y) {
            boolean isHighlighted = (node == highlightedNode);

            if (isHighlighted) {
                g2d.setColor(new Color(255, 105, 180, 100));
                g2d.fillOval(x - NODE_RADIUS - 5, y - NODE_RADIUS - 5, (NODE_RADIUS + 5) * 2, (NODE_RADIUS + 5) * 2);
                g2d.setColor(highlightColor);
            } else {
                g2d.setColor(new Color(16, 185, 129));
            }

            if (!isHighlighted) g2d.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
            else {
                g2d.fillOval(x - NODE_RADIUS - 2, y - NODE_RADIUS - 2, NODE_RADIUS * 2 + 4, NODE_RADIUS * 2 + 4);
            }

            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(isHighlighted ? 3.0f : 2.0f));
            g2d.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
            String valueStr = String.valueOf(node.data);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x - fm.stringWidth(valueStr) / 2;
            int textY = y + fm.getAscent() / 2 - 2;
            g2d.drawString(valueStr, textX, textY);
        }

        private int calculateHeight(TreeNode node) {
            if (node == null) return 0;
            return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            BSTVisualizer visualizer = new BSTVisualizer();
            visualizer.setVisible(true);
            visualizer.appendOutput("System Ready.");
        });
    }
}