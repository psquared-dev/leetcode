package util;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class BST<V extends Comparable<V>> {
    @Data
    public class Node {
        V value;
        Node left;
        Node right;

        public Node(V value) {
            this.value = value;
        }
    }

    private Node root;

    public BST(V value) {
        root = new Node(value);
    }

    public boolean insert(V value) {
        Node curr = root;
        Node prev = null;

        while (curr != null) {
            if (value.compareTo(curr.getValue()) > 0) {
                prev = curr;
                curr = curr.right;
            } else {
                prev = curr;
                curr = curr.left;
            }
        }

        Node newNode = new Node(value);

        assert prev != null;

        if (value.compareTo(prev.getValue()) > 0) {
            prev.right = newNode;
        } else {
            prev.left = newNode;
        }

        return true;
    }

    public int findLeafNodes() {
        Function<Node, Integer> helper = new Function<>() {
            @Override
            public Integer apply(Node node) {
                if (node == null) {
                    return 0;
                }

                if (node.left == null && node.right == null) {
                    return 1;
                }

                int left = this.apply(node.left);
                int right = this.apply(node.right);
                return left + right;
            }
        };

        return helper.apply(root);
    }

    public int findNonLeafNodes() {
        Function<Node, Integer> helper = new Function<Node, Integer>() {
            @Override
            public Integer apply(Node node) {
                if (node == null) {
                    return 0;
                }

                if (node.left == null && node.right == null) {
                    return 0;
                }

                Integer left = this.apply(node.left);
                Integer right = this.apply(node.right);
                return 1 + left + right;
            }
        };

        return helper.apply(root);
    }

    public int findTotalNodes() {
        return this.findNonLeafNodes() + this.findLeafNodes();
    }

    public boolean isStrictBst() {
        Function<Node, Boolean> helper = new Function<Node, Boolean>() {
            @Override
            public Boolean apply(Node node) {
                if (node == null) {
                    return false;
                }

                if (node.left == null && node.right == null) {
                    return true;
                }

                if (node.left != null && node.right != null) {
                    return this.apply(node.left) && this.apply(node.right);
                }

                return false;
            }
        };

        return helper.apply(root);
    }

    public int findHeight() {
        Function<Node, Integer> helper = new Function<Node, Integer>() {
            @Override
            public Integer apply(Node node) {
                if (node == null) {
                    return 0;
                }

                if (node.left == null && node.right == null) {
                    return 0;
                }

                return 1 + Math.max(this.apply(node.left), this.apply(node.right));
            }
        };

        return helper.apply(root);
    }

    public boolean isEqual(BST<V> bst2) {
        BiFunction<Node, Node, Boolean> helper = new BiFunction<Node, Node, Boolean>() {
            @Override
            public Boolean apply(Node node1, Node node2) {
                if (node1 == null && node2 == null) {
                    return true;
                }

                if ((node1 == null && node2 != null) || (node1 != null && node2 == null)) {
                    return false;
                }

                if (node1.value == node2.value) {
                    return this.apply(node1.left, node2.left) && this.apply(node1.right, node2.right);
                }

                return false;
            }
        };

        return helper.apply(root, bst2.root);
    }

    public void invert() {
        Consumer<Node> helper = new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                if (node == null) {
                    return;
                }

                Node tmp = node.left;
                node.left = node.right;
                node.right = tmp;

                this.accept(node.left);
                this.accept(node.right);
            }
        };

        helper.accept(root);
    }

    public ArrayList<Node> preorder(){
        ArrayList<Node> result = new ArrayList<>();

        Consumer<Node> helper = new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                if(node != null){
                    result.add(node);
                    this.accept(node.left);
                    this.accept(node.right);
                }
            }
        };

        helper.accept(root);

        return result;
    }

    public ArrayList<Node> postorder(){
        ArrayList<Node> result = new ArrayList<>();

        Consumer<Node> helper = new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                if(node != null){
                    this.accept(node.left);
                    this.accept(node.right);
                    result.add(node);
                }
            }
        };

        helper.accept(root);

        return result;
    }

    public ArrayList<Node> inorder(){
        ArrayList<Node> result = new ArrayList<>();

        Consumer<Node> helper = new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                if(node != null){
                    this.accept(node.left);
                    result.add(node);
                    this.accept(node.right);
                }
            }
        };

        helper.accept(root);

        return result;
    }

    public ArrayList<Node> bfs(){
        ArrayList<Node> result = new ArrayList<>();

        Consumer<Node> helper = new Consumer<Node>() {
            @Override
            public void accept(Node node) {
                LinkedList<Node> queue = new LinkedList<>();
                queue.add(node);

                while(!queue.isEmpty()){
                    Node remove = queue.remove();
                    result.add(remove);

                    if(remove.left != null){
                        queue.add(remove.left);
                    }

                    if(remove.right != null){
                        queue.add(remove.right);
                    }
                }
            }
        };

        helper.accept(root);

        return result;
    }

    public static void main(String[] args) {
        BST<Integer> root = new BST<>(30);
        boolean insert = root.insert(20);
        root.insert(40);
        root.insert(10);
        root.insert(25);
        root.insert(35);
        root.insert(50);

        BST<Integer> root2 = new BST<>(30);
        root2.insert(20);
        System.out.println(root);
        System.out.println(root.isEqual(root2));
        root.invert();
        System.out.println(root.preorder().stream().map(e -> e.value).collect(Collectors.toList()));
        System.out.println(root.postorder().stream().map(e -> e.value).collect(Collectors.toList()));
        System.out.println(root.inorder().stream().map(e -> e.value).collect(Collectors.toList()));
        System.out.println(root.bfs().stream().map(e -> e.value).collect(Collectors.toList()));

//        System.out.println(root.findLeafNodes());
//        System.out.println(root.findNonLeafNodes());
//        System.out.println(root.findTotalNodes());
//        System.out.println(root.isStrictBst());
//        System.out.println(root.findHeight());

//        System.out.println(Helper.toJson(root));
//
//        BST<String> root2 = new BST<>("m");
//        root2.insert("c");
//        root2.insert("x");
//        root2.insert("a");
//        root2.insert("e");
//        root2.insert("u");
//        root2.insert("z");
//
//        System.out.println(Helper.toJson(root2));
    }
}
