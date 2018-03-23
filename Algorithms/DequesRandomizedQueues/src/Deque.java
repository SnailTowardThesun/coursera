import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int nodeCount;
    private Node firstNode, lastNode;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    public Deque() {
        firstNode = lastNode = null;
        nodeCount = 0;
    }

    public boolean isEmpty() {
        return nodeCount == 0;
    }

    public int size() {
        return nodeCount;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node firstOld = firstNode;
        firstNode = new Node();
        firstNode.item = item;
        firstNode.previous = null;

        if (isEmpty()) {
            lastNode = firstNode;
            firstNode.next = null;
        } else {
            firstNode.next = firstOld;
            firstOld.previous = firstNode;
        }

        nodeCount++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node lastOld = lastNode;
        lastNode = new Node();
        lastNode.item = item;
        lastNode.next = null;

        if (isEmpty()) {
            firstNode = lastNode;
            lastNode.previous = null;
        } else {
            lastNode.previous = lastOld;
            lastOld.next = lastNode;
        }

        nodeCount++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = firstNode.item;
        firstNode = firstNode.next;
        nodeCount--;
        if (isEmpty()) {
            lastNode = firstNode = null;
        } else {
            firstNode.previous = null;
        }
        return item;

    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = lastNode.item;
        lastNode = lastNode.previous;
        nodeCount--;
        if (isEmpty()) {
            firstNode = lastNode = null;
        } else {
            lastNode.next = null;
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator(firstNode);
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator(Node first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
    }
}