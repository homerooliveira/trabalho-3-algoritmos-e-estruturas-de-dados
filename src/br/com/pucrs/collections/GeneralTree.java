package br.com.pucrs.collections;

import java.util.ArrayList;
import java.util.List;

public class GeneralTree<E> {

    private Node<E> root;
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
        Node<E> n = searchNodeRef(element, root);
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

    private Node<E> searchNodeRef(E element, Node<E> target) {
        Node<E> res = null;
        if (target != null) {
            if (element.equals(target.element)) {
                res = target;
            } else {
                Node<E> aux = null;
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
        Node<E> n = new Node<>(element);
        Node<E> nAux = null;
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

    private void positionsPreAux(Node<E> n, List<E> lista) {
        if (n != null) {
            lista.add(n.element);
            for (int i = 0; i < n.getSubtreesSize(); i++) {
                positionsPreAux(n.getSubtree(i), lista);
            }
        }
    }

    public List<E> positionsPos(){
        List<E> list = new ArrayList<>();
        positionsPosAux(root, list);
        return list;
    }

    private void positionsPosAux(Node<E> node, List<E> list) {
        if (node != null) {
            for (int i = 0; i < node.getSubtreesSize(); i++) {
                Node<E> subtree = node.getSubtree(i);
                positionsPreAux(subtree, list);
            }
            list.add(node.element);
        }
    }

    public List<E> positionsWidth() {
        ArrayList<E> lista = new ArrayList<>();

        Queue<Node<E>> fila = new Queue<>();
        Node<E> atual;

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
    private static class Node<E> {

        public Node<E> father;
        public E element;
        public List<Node<E>> subtrees;

        public Node(E element) {
            father = null;
            this.element = element;
            subtrees = new ArrayList<>();
        }

        public Node(E element, Node<E> father) {
            this.father = father;
            this.element = element;
            subtrees = new ArrayList<>();
        }

        public void addSubtree(Node<E> n) {
            n.father = this;
            subtrees.add(n);
        }

        public boolean removeSubtree(Node<E> n) {
            n.father = null;
            return subtrees.remove(n);
        }

        public Node<E> getSubtree(int i) {
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
