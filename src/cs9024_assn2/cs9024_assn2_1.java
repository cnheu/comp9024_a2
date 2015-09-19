package cs9024_assn2;


/**
 * Created by christophernheu on 8/09/15.
 */
public class cs9024_assn2_1 {
    public static void main(String[] args)
    {
        String values1[]={"Sydney", "Beijing","Shanghai", "New York", "Tokyo", "Berlin",
                "Athens", "Paris", "London", "Cairo"};
        int keys1[]={20, 8, 5, 30, 22, 40, 12, 10, 3, 5};
        String values2[]={"Fox", "Lion", "Dog", "Sheep", "Rabbit", "Fish"};
        int keys2[]={40, 7, 5, 32, 20, 30};

          /* Create the first AVL tree with an external node as the root and the
         default comparator */

        AVLTree<Integer, String> tree1=new AVLTree<Integer, String>();
//        ExtendedAVLTree<Integer, String> tree1=new ExtendedAVLTree<>();
        // Insert 10 nodes into the first tree


        for (int i=0; i<10; i++)
            tree1.insert(keys1[i], values1[i]);

          /* Create the second AVL tree with an external node as the root and the
         default comparator */

        AVLTree<Integer, String> tree2=new AVLTree<Integer, String>();

        // Insert 6 nodes into the tree

        for ( int i=0; i<6; i++)
            tree2.insert(keys2[i], values2[i]);

        ExtendedAVLTree.print(tree1);
        ExtendedAVLTree.print(tree2);
        ExtendedAVLTree.print(ExtendedAVLTree.clone(tree1));
        ExtendedAVLTree.print(ExtendedAVLTree.clone(tree2));
//
        ExtendedAVLTree.print(ExtendedAVLTree.merge(ExtendedAVLTree.clone(tree1),
                ExtendedAVLTree.clone(tree2)));

//        nullTreePrintTest();
//        nullTreeMergeTest();
//        singleNodeTreePrintTest();
    }

    public static void nullTreePrintTest() {
        AVLTree<Integer, String> tree1=new AVLTree<Integer, String>();
        ExtendedAVLTree.print(tree1);
        ExtendedAVLTree.print(ExtendedAVLTree.clone(tree1));
    }

    public static void nullTreeMergeTest() {

        AVLTree<Integer, String> tree1=new AVLTree<Integer, String>();
        String values2[]={"Fox", "Lion", "Dog", "Sheep", "Rabbit", "Fish"};
        int keys2[]={40, 7, 5, 32, 20, 30};
        AVLTree<Integer, String> tree2=new AVLTree<Integer, String>();
        for ( int i=0; i<6; i++)
            tree2.insert(keys2[i], values2[i]);

        AVLTree<Integer, String> tree3 = ExtendedAVLTree.merge(ExtendedAVLTree.clone(tree1),
                ExtendedAVLTree.clone(tree2));

//        System.out.println(tree2.root.element().getKey());
//        System.out.println(tree3.root.element().getKey());
        ExtendedAVLTree.print(tree3);
        ExtendedAVLTree.print(tree2);
    }

    public static void singleNodeTreePrintTest() {

        AVLTree<Integer, String> tree1=new AVLTree<Integer, String>();
        String values1[]={"Tiger"};
        int keys1[]={30};
        tree1.insert(keys1[0],values1[0]);

        ExtendedAVLTree.print(tree1);



        String values2[]={"Fox", "Lion", "Dog", "Sheep", "Rabbit", "Fish"};
        int keys2[]={40, 7, 5, 32, 20, 30};
        AVLTree<Integer, String> tree2=new AVLTree<Integer, String>();
        for ( int i=0; i<6; i++)
            tree2.insert(keys2[i], values2[i]);

        ExtendedAVLTree.print(tree2);

        AVLTree<Integer, String> tree3 = ExtendedAVLTree.merge(ExtendedAVLTree.clone(tree1),
                ExtendedAVLTree.clone(tree2));

        ExtendedAVLTree.print(tree3);
//        ExtendedAVLTree.print(tree2);


    }
}
//        LinkedList LL1 = ExtendedAVLTree.toSortedLinkedList(tree1);
//        LinkedList LL2 = ExtendedAVLTree.toSortedLinkedList(tree2);

//        for (int i = 0 ; i<LL1.size(); i ++) {
//            BinarySearchTree.BSTEntry myEntry = (BinarySearchTree.BSTEntry) LL1.get(i);
//            System.out.println(myEntry.getKey());
//        }
//        System.out.println("--------");
//        for (int i = 0 ; i<LL2.size(); i ++) {
//            BinarySearchTree.BSTEntry myEntry = (BinarySearchTree.BSTEntry) LL2.get(i);
//            System.out.println(myEntry.getKey());
//        }
//
//        System.out.println("--------");
//
//
//        LinkedList LL3 = ExtendedAVLTree.mergeSortedLinkedLists(LL1, LL2);
//        for (int i = 0 ; i<LL3.size(); i ++) {
//            BinarySearchTree.BSTEntry myEntry = (BinarySearchTree.BSTEntry) LL3.get(i);
//            System.out.println(myEntry.getKey());
////            System.out.println(LL3.get(i));
//        }
//
//        ArrayList AL3 = ExtendedAVLTree.linkedListToArrayList(LL3);
//        for (int i = 0; i<AL3.size(); i++) {
//            BinarySearchTree.BSTEntry myEntry = (BinarySearchTree.BSTEntry) AL3.get(i);
//            System.out.println(myEntry.getKey());
////            System.out.println(AL3.get(i));
//        }
//
//        AVLTree tree3 = new AVLTree();
//        AVLTree.AVLNode tree3root = new AVLTree.AVLNode();
//        tree3.root = tree3root;
//        ExtendedAVLTree.populateTree((AVLTree.AVLNode) tree3.root(), 0, AL3.size() - 1, AL3);
//        ExtendedAVLTree.print(tree3);
//        BinarySearchTree.BSTEntry rootEntry = (BinarySearchTree.BSTEntry) tree3.root().element();
//        BinarySearchTree.BSTEntry rootLeftEntry = (BinarySearchTree.BSTEntry) ((AVLTree.AVLNode) tree3.root()).getLeft().element();
//        BinarySearchTree.BSTEntry rootRightEntry = (BinarySearchTree.BSTEntry) ((AVLTree.AVLNode) tree3.root()).getRight().element();
//
//        System.out.println(rootEntry.getKey());
//        System.out.println(rootLeftEntry.getKey());
//        System.out.println(rootRightEntry.getKey());
//        ExtendedAVLTree.print(tree1);
//        ExtendedAVLTree.print(tree2);