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
//        ExtendedAVLTree.print(tree2);
        ExtendedAVLTree.print(ExtendedAVLTree.clone(tree1));
//        ExtendedAVLTree.print(ExtendedAVLTree.clone(tree2));
//        ExtendedAVLTree.print(ExtendedAVLTree.merge(ExtendedAVLTree.clone(tree1),
//                ExtendedAVLTree.clone(tree2)));

//        nullTreePrintTest();
//        nullTreeMergeTest();
//        singleNodeTreePrintTest();
//        singleNodeAddNodeTest();
//        singleNodeAddRootTest();
//        emptyTreeAddSingleNodeTest();
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

    public static void singleNodeAddNodeTest() {
        AVLTree<Integer, String> tree1=new AVLTree<Integer, String>();
        String values1[]={"Tiger"};
        int keys1[]={30};
        tree1.insert(keys1[0],values1[0]);
        ExtendedAVLTree.print(tree1);

        tree1.insert(5, "Hello");
        ExtendedAVLTree.print(tree1);

    }

    public static void singleNodeAddRootTest() {
        AVLTree<Integer, String> tree1=new AVLTree<Integer, String>();
        String values1[]={"Tiger"};
        int keys1[]={30};
        tree1.insert(keys1[0], values1[0]);
        ExtendedAVLTree.print(tree1);

        try {
            tree1.addRoot(new BinarySearchTree.BSTEntry(5,"howdy",null));
        }
        catch (NonEmptyTreeException e) {
            System.out.println("Could not add root since. You already have one.");
        }
        ExtendedAVLTree.print(tree1);

    }

    public static void emptyTreeAddSingleNodeTest() {
        AVLTree<Integer, String> tree1=new AVLTree<Integer, String>();

        try {
            ExtendedAVLTree.print(tree1);
        }
        catch (EmptyTreeException e) {
            System.out.println("Could not print an empty tree");
        }


        tree1.insert(5,"howdy");
//        System.out.println(tree1.root.element().getKey());
        ExtendedAVLTree.print(tree1);

    }
}
