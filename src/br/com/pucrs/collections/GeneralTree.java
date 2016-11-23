package br.com.pucrs.collections;

import java.util.ArrayList;
import java.util.List;

public class GeneralTree<E> {

    private Node root;
    private int count;

    public GeneralTree() {
        root = null;
        count = 0;
    }

    public E getRoot() {
        if (isEmpty()) {
            throw new EmptyTreeException();
        }
        return root.element;
    }

    public void setRoot(E element) {
        if (isEmpty()) {
            throw new EmptyTreeException();
        }
        root.element = element;
    }

    public boolean isRoot(E element) {
        if (root != null) {
            if (root.element.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public int size() {
        return count;
    }

    public void clear() {
        root = null;
        count = 0;
    }

    public E getFather(E element) {
        Node n = searchNodeRef(element, root);
        if (n == null || n.father == null) {
            return null;
        } else {
            return n.father.element;
        }
    }

    public boolean contains(E element) {
        Node nAux = searchNodeRef(element, root);
        return (nAux != null);
    }

    private Node searchNodeRef(E element, Node target) {
        Node res = null;
        if (target != null) {
            if (element.equals(target.element)) {
                res = target;
            } else {
                Node aux = null;
                int i = 0;
                while ((aux == null) && (i < target.getSubtreesSize())) {
                    aux = searchNodeRef(element, target.getSubtree(i));
                    i++;
                }
                res = aux;
            }
        }
        return res;
    }

    public boolean add(E element, E father) {
        Node n = new Node(element);
        Node nAux = null;
        boolean res = false;
        if (father == null) {   // Insere na raiz
            if (root != null) { //Atualiza o pai da raiz
                n.addSubtree(root);
                root.father = n;
            }
            root = n;   //Atualiza a raiz
            res = true;
        } else {        //Insere no meio da Árvore
            nAux = searchNodeRef(father, root);
            if (nAux != null) {
                nAux.addSubtree(n);
                res = true;
            }
        }
        count++;
        return res;
    }

    public List<E> positionsPre() {
        List<E> lista = new ArrayList<>();
        positionsPreAux(root, lista);
        return lista;
    }

    private void positionsPreAux(Node n, List<E> lista) {
        if (n != null) {
            lista.add(n.element);
            for (int i = 0; i < n.getSubtreesSize(); i++) {
                positionsPreAux(n.getSubtree(i), lista);
            }
        }
    }

    public List<E> positionsWidth() {
        ArrayList<E> lista = new ArrayList<>();

        Queue<Node> fila = new Queue<>();
        Node atual;

        if (root != null) {
            fila.enqueue(root);
            while (!fila.isEmpty()) {
                atual = fila.dequeue();
                lista.add(atual.element);
                for (int i = 0; i < atual.getSubtreesSize(); i++) {
                    fila.enqueue(atual.getSubtree(i));
                }
            }
        }
        return lista;
    }

    // Classe interna Node
    private class Node {

        public Node father;
        public E element;
        public List<Node> subtrees;

        public Node(E element) {
            father = null;
            this.element = element;
            subtrees = new ArrayList<>();
        }

        public Node(E element, Node father) {
            this.father = father;
            this.element = element;
            subtrees = new ArrayList<>();
        }

        public void addSubtree(Node n) {
            n.father = this;
            subtrees.add(n);
        }

        public boolean removeSubtree(Node n) {
            n.father = null;
            return subtrees.remove(n);
        }

        public Node getSubtree(int i) {
            if ((i < 0) || (i >= subtrees.size())) {
                throw new IndexOutOfBoundsException();
            }
            return subtrees.get(i);
        }

        public int getSubtreesSize() {
            return subtrees.size();
        }
    }

}