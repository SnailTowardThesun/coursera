import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private int count;

    private class Node {
        Item it;
        Node next;
        Node previous;
    }

    private Node first, last;

    // construct an empty deque
    public Deque() {
        count = 0;

        first = new Node();
        first.previous = null;
        first.next = last;

        last = new Node();
        last.previous = first;
        last.next = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newNode = first;
        newNode.it = item;

        first = new Node();
        first.previous = null;
        first.next = newNode;

        newNode.previous = first;

        count++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node newNode = last;
        last.it = item;

        last = new Node();
        last.next = null;
        last.previous = newNode;

        newNode.next = last;

        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node delNode = first.next;
        first.next = delNode.next;
        delNode.next.previous = first;

        count--;
        return null;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        count--;
        return null;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return null;
    }

    // unit testing (optional)
    public static void main(String[] args) {
        System.out.println("test for Deque<item>");

    }
}
