import java.util.Iterator;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item value;
        Node previous;
        Node next;
    }

    private Node firstNode;
    private Node lastNode;
    private int nodeCounts;

    public Deque() {
        firstNode = new Node();
        lastNode = new Node();
        firstNode.next = lastNode;
        firstNode.previous = null;
        lastNode.previous = firstNode;
        lastNode.next = null;

        nodeCounts = 0;
    }

    public boolean isEmpty() {
        return firstNode == lastNode;
    }

    public int size() {
        return nodeCounts;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Node newNode = new Node();
        newNode.value = item;

        Node tmp = firstNode.next;
        firstNode.next = newNode;
        newNode.previous = firstNode;
        newNode.next = tmp;
        tmp.previous = newNode;

        nodeCounts++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Node newNode = new Node();
        newNode.value = item;

        Node tmp = lastNode.previous;
        newNode.next = lastNode;
        lastNode.previous = newNode;

        newNode.previous = tmp;
        tmp.next = newNode;

        nodeCounts++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node tmp = firstNode.next;
        firstNode.next = tmp.next;
        tmp.next.previous = firstNode;

        nodeCounts--;
        return tmp.value;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node tmp = lastNode.previous;
        lastNode.previous = tmp.previous;
        tmp.previous.next = lastNode;

        nodeCounts--;

        return tmp.value;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = firstNode;

        @Override
        public boolean hasNext() {
            return current.next == lastNode;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                return null;
            }

            Item item = current.value;
            current = current.next;

            return item;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (optional)
    public static void main(String[] args) {
    }

}
