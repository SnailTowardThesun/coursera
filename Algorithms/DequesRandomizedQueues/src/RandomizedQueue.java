import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int nodeCount = 0;

    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return nodeCount == 0;
    }

    public int size() {
        return nodeCount;
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < nodeCount; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (nodeCount == queue.length) {
            resize(2 * queue.length);
        }

        queue[nodeCount++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int offset = StdRandom.uniform(nodeCount);
        Item item = queue[offset];

        if (offset != nodeCount - 1) {
            queue[offset] = queue[nodeCount - 1];
        }

        queue[nodeCount - 1] = null;
        nodeCount--;

        if (nodeCount > 0 && nodeCount == queue.length / 4) {
            resize(queue.length / 2);
        }

        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int offset = StdRandom.uniform(nodeCount);
        return queue[offset];
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private Item[] copyArray = (Item[]) new Object[queue.length];
        private int copyCount = nodeCount;

        public ArrayIterator() {
            for (int i = 0; i < queue.length; i++) {
                copyArray[i] = queue[i];
            }
        }

        public boolean hasNext() {
            return copyCount != 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            int offset = StdRandom.uniform(copyCount);
            Item item = copyArray[offset];

            if (offset != copyCount - 1) {
                copyArray[offset] = copyArray[copyCount - 1];
            }
            copyArray[copyCount - 1] = null;
            copyCount--;
            return item;
        }
    }

    public static void main(String[] args) {
    }
}