package cs9024_assn2;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by christophernheu on 8/09/15.
 */
public class ExtendedAVLTree<K,V> extends AVLTree {
    public ExtendedAVLTree() {
        super();
    }

    /**
     * clone - takes in input tree, and outputs a replica of the original tree by initialising a new node node and
     * assigning properties during a pre-order traversal.
     *
     * clone only instantiates an empty tree and adds an empty node as the root.
     * It then calls the helper function cloneHelper to carry out the pre-order traversal.
     *
     * @param tree
     * @return
     *
     */
    public static AVLTree clone(AVLTree tree) {

        if (tree.isEmpty()) {
            throw new EmptyTreeException("Cannot clone an empty tree");
        }

        AVLNode treeRoot = (AVLNode) tree.root;
        ExtendedAVLTree clonedTree = new ExtendedAVLTree();
        AVLNode clonedRoot = (AVLNode) clonedTree.addRoot(null);
        clonedTree.cloneHelper(treeRoot, clonedRoot);

        clonedTree.numEntries = tree.numEntries; // upkeep for numEntries to make sure isEmpty works
        return clonedTree;
    }

    /**
     * cloneHelper carries out the pre-order traversal of the AVLTree.
     *
     * 1. Base Case - where rootA.element() == null, or we've reached a leaf node.
     * 2. Visit rootA
     * 2a. Given we're not at a leaf node, we instantiate a BSTEntry at rootB using rootA's own BSTEntry information.
     * 2b. Assign rootB's element to the new BSTEntry rootBElement
     * 2c. Assign rootB's height to rootA's.
     * 3. Go Left
     * 3a. Instantiate a new AVLNode, only setting it's parent parameter to rootB, the rest null.
     * 3b. Set rootB's left child to the newNode.
     * 3c. Go to the next leftChild
     * 4. Go Right
     * 4a. Instantiate a new AVLNode, only setting it's parent parameter to rootB, the rest null.
     * 4b. Set rootB's right child to the newNode.
     * 4c. Go to the next rightChild
     *
     * @param rootA - the original tree's root
     * @param rootB - the cloned tree's empty root
     */
    protected void cloneHelper(AVLNode rootA, AVLNode rootB) {

        if (rootA.element() == null) { // we've reached leaf node
            return;
        }

        // Visit the node and instantiate a BSTEntry
        BSTEntry rootAElement = (BSTEntry) rootA.element();
        BSTEntry rootBElement = new BSTEntry(rootAElement.getKey(), rootAElement.getValue(), rootB);
        rootB.setElement(rootBElement);
        rootB.setHeight(rootA.getHeight());

        // Create and add left child
        AVLNode newLeftNode = (AVLNode) createNode(null, rootB, null, null);
        rootB.setLeft(newLeftNode);

        // Create and add right child
        AVLNode newRightNode = (AVLNode) createNode(null, rootB, null, null);
        rootB.setRight(newRightNode);

        cloneHelper((AVLNode) rootA.getLeft(), (AVLNode) rootB.getLeft()); // go left
        cloneHelper((AVLNode) rootA.getRight(), (AVLNode) rootB.getRight()); // go right
    }

    /**
     * merge - merges two trees, tree1 and tree2 in O(m+ n) time.
     * where the number of nodes in tree1 be m and the number of nodes in tree2 be n.
     *
     * It calls several private methods which are explained in depth below.
     * 1. toSortedLinkedList = O(m) or O(n)
     * 2. mergeSortedLinkedLists = O(m+n)
     * 3. linkedListToArrayList = O(m+n)
     * 4. populatetree = O(m+n)
     *
     * @param tree1
     * @param tree2
     * @return
     */
    public static AVLTree merge(AVLTree tree1, AVLTree tree2 ) {
        LinkedList LL1 = toSortedLinkedList(tree1);
        LinkedList LL2 = toSortedLinkedList(tree2);
        LinkedList LL3 = mergeSortedLinkedLists(LL1, LL2);
        ArrayList AL3 = linkedListToArrayList(LL3);
        AVLTree tree3 = new AVLTree();
        populateTree((AVLTree.AVLNode) tree3.root(), 0, AL3.size() - 1, AL3);

        tree3.numEntries = tree1.numEntries + tree2.numEntries; // upkeep for isEmpty calls
        tree1.root = null; // nullify the root of tree1 to be sent to the garbage collector
        tree2.root = null; // nullify the root of tree2 to be sent to the garbage collector
        tree1.numEntries = 0;
        tree2.numEntries = 0;

        return tree3;
    }

    /**
     * toSortedLinkedList - takes a tree and outputs a sortedLinkedList in O(n) time.
     *
     * It calls the recursive function toSortedLinkedListHelper which takes O(n) time.
     *
     * The output sortedLinkedList is in a descending order. I.e. the head node is the smallest key in the AVLTree.
     *
     * @param tree
     * @return
     */
    public static LinkedList toSortedLinkedList(AVLTree tree) {
        LinkedList sortedLinkedList = new LinkedList(); // O(1)
        toSortedLinkedListHelper((AVLNode) tree.root, sortedLinkedList); // O(n)
        return sortedLinkedList;
    }

    /**
     * toSortedLinkedListHelper - performs in order traversal starting with the smallest number.
     *
     * Input: root node and the empty LL.
     * Output: void, but updated LL
     * Time Complexity: O(n)
     *
     * We want a LL so that we get to use add and pop functions when merging the LL. We also do not need to maintain or traverse
     * the sorted LL in mergeSortedLinkedLists so this enables us to stay in O(m+n) complexity.
     *
     * @param root
     * @param LL
     */
    private static void toSortedLinkedListHelper(AVLNode root, LinkedList LL) {
        if (root.getLeft() == null && root.getRight() == null) { // if we've reached leaf
            return;
        }
        if (root.getLeft() != null) { // go left
            toSortedLinkedListHelper((AVLNode) root.getLeft(), LL);
        }
        LL.addLast(root.element()); // perform visit
        if (root.getRight() != null) { // go right
            toSortedLinkedListHelper((AVLNode) root.getRight(), LL);
        }
    }

    /**
     * mergeSortedLinkedLists
     * Input: Two sorted LL's representing tree1 and tree2.
     * Output: A merged, sorted LL which will represent tree3.
     * Time Complexity: O(m+n)
     *
     * 1. Initialises empty LL 'c'.
     * 2. For loop iterates over m+n times, this is given by 'limit'.
     * 3. At each loop, we check which head is the smallest with a peek.
     * 4. We add the smallest one to c, whilst popping it from LL a or b.
     * 5. We continue until all nodes have been added to c.
     *
     * @param a
     * @param b
     * @return
     */
    private static LinkedList mergeSortedLinkedLists(LinkedList a, LinkedList b) {
        int limit = a.size() + b.size(); // O(1)
        LinkedList c = new LinkedList(); // O(1)

        for (int i = 0; i < limit; i++) { // O(m+n)
            BSTEntry aEntry, bEntry; // O(1)
            int aKey, bKey; // O(1)

            if (!a.isEmpty() && !b.isEmpty()) { // O(1)
                aEntry = (BSTEntry) a.peek(); // O(1)
                bEntry = (BSTEntry) b.peek(); // O(1)

                aKey = (Integer) aEntry.key; // O(1)
                bKey = (Integer) bEntry.key; // O(1)

                if (aKey <= bKey) { //O(1)
                    c.add(a.pop()); //O(1)
                }
                else {
                    c.add(b.pop()); //O(1)
                }
            }
            else if (!a.isEmpty() && b.isEmpty()) { // O(1)
                c.add(a.pop()); // O(1)

            }
            else if (!b.isEmpty() && a.isEmpty()) { // O(1)
                c.add(b.pop()); // O(1)
            }
        }
        return c;
    }

    /**
     * linkedListToArrayList
     *
     * Input: single LL (representing tree3 with m+n) elements.
     * Output: ArrayList representing tree3 with m+n elements.
     * Time Complexity: O(m+n)
     *
     * We require an ArrayList for constant time indexing when we perform populateTree.
     *
     * @param LL
     * @return
     */
    private static ArrayList linkedListToArrayList(LinkedList LL){
        int limit = LL.size(); // O(1)
        ArrayList AL = new ArrayList(limit); // O(1)

        for (Object entry: LL) { // O(m+n)
            AL.add(entry); // O(1)
        }
        return AL;
    }

    /**
     * populateTree
     *
     * Input: rootNode of empty tree3, startIndex and endIndex of the arrayList so that we can recursively add each value left/right child
     * in O(1) time.
     * Output: Integer, but it's irrelevant and used for storing height in each parent node as we come back up.
     * Time Complexity: O(m+n)
     *
     * This algorithm employs a Euler Tour to populate the nodes in place and then capture their height property on their way back.
     * As with all tree traversals, as long as each visit occurs in O(1) time, the total complexity is O(m+n).
     *
     * 1. 'populateTree', takes in an empty tree and populates it. There are two key strategies:
     *  a. We break the ArrayList representing the tree's nodes into sub problems by taking the index at the middle,
     *  adding it to the tree and then further slicing it.
     *  b. The second key strategy is the fact that we use a Euler Tour, we come back to the parent node after visiting
     *  it's children and adding it's height parameter.
     * 2. We first determine the 'targetIndex'.
     * 3. We instantiate the root's left and right children nodes with null elements.
     * 4. We then take connect the new children nodes to the root and set the root's element to the entry at the 'targetindex'.
     * 5. We cover four main cases:
     *  a. We have no internal children (i.e. 'startIndex' == 'endIndex'), in which case we set the height of root's children to zero (they're leafs)
     *  and return 1 (the height of root).
     *  b. We have one left child but no right internal child, we set the rightChild's height to zero, and we move into the left child.
     *  c. We have one right child but no left internal child, we set the leftChild's height to zero and we move into the right child.
     *  d. We have both left and right children that are internal, so we move into them both.
     *  Note: with this strategy we only visit internal nodes we do not go to the leaf node.
     * 6. Finally, we set the height for the parent node, root, since we now have information about the max height of left and right children.
     *
     * @param root
     * @param startIndex
     * @param endIndex
     * @param list
     * @return
     */
    private static Integer populateTree(AVLNode root, int startIndex, int endIndex, ArrayList list) {
        int targetIndex = (startIndex + endIndex)/2;
        int leftHeight,rightHeight,nodeHeight;
        leftHeight = 0;
        rightHeight = 0;

        // Pre-order visit in O(1)
        AVLNode leftChild, rightChild;
        leftChild = new AVLNode(null, root, null, null);
        rightChild = new AVLNode(null, root, null, null);
        root.setLeft(leftChild); // O(1)
        root.setRight(rightChild); // O(1)
        root.setElement(list.get(targetIndex)); // O(1)

        // if we're at last internal node add leafs
        if (startIndex == endIndex) {
            leftChild.setHeight(0);
            rightChild.setHeight(0);
            return 1;
        }
        // if we only have left child and no right child
        else if (targetIndex == endIndex) {
            rightChild.setHeight(0);
            leftHeight = populateTree(leftChild, startIndex, targetIndex-1, list);
        }
        // if we only have right child and no left child
        else if (targetIndex == startIndex) {
            leftChild.setHeight(0);
            rightHeight = populateTree(rightChild, targetIndex+1, endIndex, list);
        }
        // if we have both left child and right child
        else {
            leftHeight = populateTree(leftChild, startIndex, targetIndex-1, list);
            rightHeight = populateTree(rightChild, targetIndex+1, endIndex, list);
        }
        // Post-order visit in O(1)
        nodeHeight = Math.max(leftHeight, rightHeight) + 1; // O(1)
        root.setHeight(nodeHeight); // O(1)
        return nodeHeight;
    }

    /**
     * print - prints out an AVLTree in an external window using JFrame
     *
     * The first part essentially instantiates a clone of the original tree and a HashMap "store".
     * We use the printHelper perform a pre-order traversal and put all the data required for rendering in "store"
     * The second part applies the subclass TreeComponent and adds it to the JFrame object to be rendered.
     *
     * @param tree
     */
    public static void print(AVLTree tree) {

        AVLTree tempPrintTree = clone(tree);
        AVLNode root = (AVLNode) tempPrintTree.root();
        int rootHeight = root.height;
        HashMap<Integer,ArrayList<ArrayList>> store = new HashMap<>();

//        System.out.println("Number of entries for tree: " + tree.size());
        if (tree.isEmpty()) {
            throw new EmptyTreeException("Cannot print an empty tree");
        }

        printHelper(root, null, store, rootHeight, 0);

        int UNIT = 18;
        int height = (int) Math.pow(store.size()-1,2) * 2 * UNIT;
        height = Math.max(height, 200);
        int width = (int) Math.pow(2,store.size()-1) * 2 * UNIT + 100;

        JFrame window = new JFrame();
        window.setSize(width, height);
        window.setTitle("ExtendedAVLTree Print()");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(new TreeComponent(store));
        window.setVisible(true);

        tempPrintTree.root = null;
        tempPrintTree.numEntries = 0;

    }

    /**
     * printHelper - performs a pre-order traversal and records key information inside the "store".
     *
     * The variable store's structure can be described as the following:
     * { level : [[BSTEntry node1, String "internal" || "external" || "null", int padding],
     *            [BSTEntry node2, String "internal" || "external" || "null", int padding],...]}
     *
     * 1. Base Case: if level parameter exceeds the rootHeight, then we know we've gone PAST the leaf node.
     * 2. Visit
     *  a. If the store has no key for this level, we add a new ArrayList which holds the MAX number of nodes at this level.
     *  b. We assess whether the node is external, internal or a null placeholder (that we use for printing).
     *  c. We use the parent parameter, to indicate to us whether we are reading an external (leaf) node or a null placeholder.
     *  d. We add a pointer to the Entry at the node and a string indicator of the nodeType to tempNodeData.
     *  e. We add the padding required to tempNodeData (the last information required).
     *  f. Finally we add tempNodeData to the ArrayList at this level.
     * 3. Go Left
     *  a. We must pass different information into the recursive call of printerHelper depending on the nodeType.
     *  b. IF there is a leftChild, and the leftChild is not a leaf, then we simply make the recursive call to leftChild,
     *  set root to parent and add 1 to the level.
     *  c. IF there is a leftChild, but the leftChild is a leaf, we have to first add temporary leafs (no element) to
     *  the leftLeafChild before we move into it.
     *  d. IF there is no leftChild, we need to instantiate a leftPlaceholderChild node. We follow a similar procedure to instantiate
     *  the leftPlaceholderChild's children (which in turn can become placeholders).
     *  e. For b,c,d, cases, we nullify any placeholder nodes and BSTEntry objects AFTER the recursive call.
     *
     * @param root
     * @param parent
     * @param store
     * @param rootHeight
     * @param level
     */
    protected static void printHelper(AVLNode root, AVLNode parent, HashMap<Integer,ArrayList<ArrayList>> store, int rootHeight, int level) {

        if (level > rootHeight) { // if we've passed the leaf node
            return;
        }

        int maxNumOfNodes = (int) Math.pow(2,level);
        int padding;
        ArrayList<Object> tempNodeData = new ArrayList<>(4);

        // Add an ArrayList for each level
        if (!store.containsKey(level)) {
            store.put(level, new ArrayList(maxNumOfNodes));
        }

        // Add the actual node element and then add the nodeType
        if (root.element() != null) {
            BSTEntry rootEntry = (BSTEntry) root.element();
            tempNodeData.add(rootEntry);
            tempNodeData.add("internal"); // Internal node with an element
        }
        else {
            tempNodeData.add(null);
            if (parent.element() != null) {
                tempNodeData.add("external"); // External/Leaf Placeholder
            }
            else {
                tempNodeData.add("null"); // Null Placeholder
            }
        }

        // Calculate and add the padding
        padding = (int) Math.pow(2,(rootHeight-level))-1;
        tempNodeData.add(padding);

        // Add the nodeData to ArrayList at each level
        store.get(level).add(tempNodeData);

        // Determine the operations to take depending on the nodeType
        // Go Left
        if (root.getLeft() != null) {
            if (root.getLeft().element() != null) { // internal
                printHelper((AVLNode) root.getLeft(), root, store, rootHeight, level + 1);
            }
            else { // external
                AVLNode leftLeafChild = (AVLNode) root.getLeft();
                leftLeafChild.setLeft(new AVLNode(null, leftLeafChild, null, null));
                leftLeafChild.setRight(new AVLNode(null, leftLeafChild, null, null));

                printHelper(leftLeafChild, root, store, rootHeight, level + 1);

                leftLeafChild.setLeft(null);
                leftLeafChild.setRight(null);
            }
        }
        else { // placeholder
            AVLNode leftPlaceholderChild = new AVLNode(null,root,null,null);
            root.setLeft(leftPlaceholderChild);
            leftPlaceholderChild.setLeft(new AVLNode(null, leftPlaceholderChild, null, null));
            leftPlaceholderChild.setRight(new AVLNode(null, leftPlaceholderChild, null, null));

            printHelper(leftPlaceholderChild, root, store, rootHeight, level + 1);

            leftPlaceholderChild.setLeft(null);
            leftPlaceholderChild.setRight(null);
            root.setLeft(null);
        }
        // Go Right
        if (root.getRight().element() != null) {
            if (root.getRight().element() != null) { // internal
                printHelper((AVLNode) root.getRight(), root, store, rootHeight, level + 1);
            }
            else { // external
                AVLNode rightLeafChild = (AVLNode) root.getRight();
                rightLeafChild.setLeft(new AVLNode(null, rightLeafChild, null, null));
                rightLeafChild.setRight(new AVLNode(null, rightLeafChild, null, null));

                printHelper(rightLeafChild, root, store, rootHeight, level + 1);

                rightLeafChild.setLeft(null);
                rightLeafChild.setRight(null);
            }
        }
        else { // placeholder
            AVLNode rightPlaceholderChild = new AVLNode(null,root,null,null);
            root.setRight(rightPlaceholderChild);
            rightPlaceholderChild.setLeft(new AVLNode(null, rightPlaceholderChild, null, null));
            rightPlaceholderChild.setRight(new AVLNode(null, rightPlaceholderChild, null, null));

            printHelper(rightPlaceholderChild, root, store, rootHeight, level + 1);

            rightPlaceholderChild.setLeft(null);
            rightPlaceholderChild.setRight(null);
            root.setRight(null);
        }
    }
    /**
     * TreeComponent extends the JComponent.
     * The treeStore ArrayList is passed into TreeComponent for initialisation.
     * When the instantiated object of TreeComponent is "added" to a JFrame, paintComponent is run.
     */
    public static class TreeComponent extends JComponent {

        int UNIT = 18; //arbitrarily picked and optimises for a 13" 2560 pixel wide display.

        HashMap<Integer,ArrayList<ArrayList>> treeStore;

        public TreeComponent(){
            this.treeStore = null;
            System.out.println("Please enter a tree to print.");
        }

        public TreeComponent(HashMap<Integer,ArrayList<ArrayList>> treeStore) {

            this.treeStore = treeStore;
        }

        /**
         * countTreeNodes - helper function for counting the number of node in the treeStore array that aren't placeholders.
         * @param treeStore
         * @return
         */
        public int countTreeNodes(HashMap<Integer,ArrayList<ArrayList>> treeStore) {
            int nodeCount = 0;
            for (int i = 0; i < treeStore.size(); i++) {
                ArrayList treeLevelArray = this.treeStore.get(i);
                for (Object nodeDatum: treeLevelArray) {
                    ArrayList treeNodeArray = (ArrayList) nodeDatum;
                    String nodeType = (String) treeNodeArray.get(1);
                    if (nodeType == "internal" || nodeType == "external") {
                        nodeCount++;
                    }
                }
            }
            return nodeCount;
        }

        /**
         * paintComponent
         *
         * Interprets the "treeStore" data structure and pulls out the information required to draw the elements.
         *
         * "treeStore" consists of a HashMap dictionary. Each key represents a level, with level zero being the root.
         * Each value in treeStore is an ArrayList ("treeLevelArray"), which itself consists of one or more ArrayLists ("nodeDatum") that contain node information.
         *
         * The algorithm consists of:
         * 1. For loop, through the "treeStore", we process each node one at a time and determine how to draw them based on the "nodeType".
         * 2. During the For Loop 1, we store information for lines into the ArrayList "nodePoints".
         * 3. "nodePoints" is constructed so that it operates like a PriorityQueue. We loop through "nodePoints", we can then traverse the array
         * as though it were a tree, calculating on the fly the coordinates such that a branch leaving a parent node leaves at the centre-bottom of the parent shape and arrives
         * as the centre-top of the child's shape. The information is used to populate "linePoints".
         * 4. "linePoints" is then iterated through and used to draw each line.
         *
         * @param g
         */
        public void paintComponent(Graphics g) {
            int cursorX, cursorY; // used to track the cursor when drawing the tree's nodes
            int spacingX, spacingY; // used to set constant spacing between nodes, based onthe total tree size.
            int treeHeight = treeStore.size();

            spacingX = UNIT * 2;
            spacingY = UNIT * (treeHeight-1);


            Graphics2D g2 = (Graphics2D) g;
            Font newFont = g2.getFont().deriveFont(g2.getFont().getSize() * 0.7F);
            g.setFont(newFont);
            ArrayList<ArrayList> nodePoints = new ArrayList<>(countTreeNodes(treeStore)+1);
            LinkedList<Point[]> linePoints = new LinkedList<>();
            nodePoints.add(null);
//            System.out.println(countTreeNodes(treeStore));

            // Draw the Nodes
            for (int i = 0; i < treeHeight; i++) {
                ArrayList treeLevelArray = this.treeStore.get(i);
                cursorX = spacingX; // set the x-cursor at the start of each line
                cursorY = spacingY * (i + 1); // set the y-cursor at the start of each line

                for (Object nodeDatum: treeLevelArray) {
                    // Get key node information such as the padding required, nodeType.
                    ArrayList treeNodeArray = (ArrayList) nodeDatum;
                    int paddingX = (int) treeNodeArray.get(2);
                    String nodeType = (String) treeNodeArray.get(1);

                    cursorX += paddingX * UNIT;
                    ArrayList pointDatum = new ArrayList(2);
                    pointDatum.add(new Point(cursorX, cursorY));
                    pointDatum.add(nodeType);
                    nodePoints.add(pointDatum);

                    if (nodeType == "internal") {
                        BSTEntry nodeEntry = (BSTEntry) treeNodeArray.get(0);
                        String nodeKey = Integer.toString((int) nodeEntry.getKey());
                        g2.setColor(Color.black);
                        g2.draw(new Ellipse2D.Double(cursorX, cursorY, UNIT, UNIT));
                        g2.drawString(nodeKey, cursorX+5, cursorY+15);
                    }
                    else if (nodeType == "external") {
                        g2.setColor(Color.black);
                        g2.draw(new Rectangle(cursorX, cursorY, UNIT, UNIT));
                    }
                    else { // placeholder
                        g2.setColor(new Color(0, 0, 0, 0));
                        g2.fill(new Rectangle(cursorX, cursorY, UNIT, UNIT));
                    }
                    cursorX += (paddingX + 1) * UNIT + UNIT; // add to the x-cursor after each node/placeholder
                }
            }

            // Calculate the line's coordinates
            g2.setColor(Color.black);
            for (int i = 1; i < nodePoints.size(); i++) {
                if (nodePoints.get(i).get(1) == "internal") {
                    // Add a point pair for each child (i.e. a line from parent to each child) to linePoints
                    Point parentPoint = (Point) nodePoints.get(i).get(0);
                    Point leftPoint = (Point) nodePoints.get(2*i).get(0);
                    Point rightPoint = (Point) nodePoints.get(2*i + 1).get(0);
                    Point[] leftPointPair = new Point[2];
                    Point[] rightPointPair = new Point[2];
                    leftPointPair[0] = parentPoint;
                    rightPointPair[0] = parentPoint;
                    leftPointPair[1] = leftPoint;
                    rightPointPair[1] = rightPoint;
                    linePoints.push(leftPointPair);
                    linePoints.push(rightPointPair);
                }
            }

            // Draw the lines
            for (Point[] linePoint: linePoints) {
                g2.drawLine(linePoint[0].x + UNIT/2, linePoint[0].y + UNIT,linePoint[1].x + UNIT/2,linePoint[1].y);
            }

        }
    }
}
