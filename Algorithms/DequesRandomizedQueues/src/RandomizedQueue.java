import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int nodeCount;

    private class Node {
        Item value;
        Node previous;
        Node next;
    }

    private Node firstNode;
    private Node lastNode;

    // construct an empty randomized queue
    public RandomizedQueue() {
        nodeCount = 0;
        firstNode = new Node();
        lastNode = new Node();

        firstNode.previous = null;
        firstNode.next = lastNode;

        lastNode.previous = firstNode;
        lastNode.next = null;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return nodeCount == 0;
    }

    // return the number of items on the queue
    public int size() {
        return nodeCount;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Node newNode = new Node();
        newNode.value = item;

        Node tmp = lastNode.previous;

        lastNode.previous = newNode;
        newNode.next = lastNode;

        newNode.previous = tmp;

        nodeCount++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        int pos = StdRandom.uniform(1, nodeCount);

        Node target = firstNode;
        for (int i = 0; i < pos; ++i) {
            target = target.next;
        }

        target.previous.next = target.next;
        target.next.previous = target.previous;

        nodeCount--;

        return target.value;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        int pos = StdRandom.uniform(1, nodeCount);

        Node target = firstNode;
        for (int i = 0; i < pos; ++i) {
            target = target.next;
        }

        return target.value;
    }

    private class RandomQueueIteration implements Iterator<Item> {
        private int size = nodeCount;

        @Override
        public boolean hasNext() {
            return size != 0;
        }

        @Override
        public Item next() {

            int pos = StdRandom.uniform(1, size);

            Node target = firstNode;
            for (int i = 0; i < pos; ++i) {
                target = target.next;
            }

            // remove target
            target.previous.next = target.next;
            target.next.previous = target.previous;

            // insert target to end
            lastNode.previous.next = target;
            target.previous = lastNode.previous;

            target.next = lastNode;
            lastNode.previous = target;

            size--;

            return target.value;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomQueueIteration();
    }

    // unit testing (optional)
    public static void main(String[] args) {
    }

}