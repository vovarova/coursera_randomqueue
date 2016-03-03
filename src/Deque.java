import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        public Node(Item val, Node next, Node prev) {
            this.next = next;
            this.prev = prev;
            this.val = val;
        }

        Node prev;
        Node next;
        Item val;
    }


    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private boolean addFirstElementToCollection(Item item) {
        if (size == 1) {
            first = new Node(item, null, null);
            last = first;
            return true;
        }
        return false;
    }

    private void makeCollectionEmpty() {
        first = null;
        last = null;
    }

    private void validateItem(Item item) {
        if (item == null) throw new NullPointerException("Element can't be null");
    }

    private void validateRemovingItem() {
        if (isEmpty()) throw new NoSuchElementException("Collection is empty");
    }


    public void addFirst(Item item) {
        validateItem(item);
        size++;
        if (addFirstElementToCollection(item)) {
            return;
        }
        Node oldFirst = first;
        first = new Node(item, oldFirst, null);
        oldFirst.prev = first;

    }

    public void addLast(Item item) {
        validateItem(item);
        size++;
        if (addFirstElementToCollection(item)) {
            return;
        }
        Node preLast = last;
        last = new Node(item, null, preLast);
        preLast.next = last;
    }

    Item removeElement(Node node, Consumer<Node> removeOperation) {
        validateRemovingItem();
        size--;
        Item result = node.val;
        if (size == 0) {
            makeCollectionEmpty();
        } else {
            removeOperation.accept(node);
        }
        return result;
    }

    public Item removeFirst() {
        Item item = removeElement(first, n -> {
            first = n.next;
            first.prev = null;
        });
        return item;
    }

    public Item removeLast() {
        Item item = removeElement(last, n -> {
            last = n.prev;
            last.next = null;
        });
        return item;

    }

    public Iterator<Item> iterator() {
        return new CustomIterator();
    }

    class CustomIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item res = current.val;
            current = current.next;
            return res;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Operation is not suported");

        }

        @Override
        public void forEachRemaining(Consumer<? super Item> action) {
            forEach(action);
        }
    }


    public static void main(String[] args) {
        Deque<Integer> integers = new Deque<>();
        integers.addFirst(12);
        integers.addFirst(1);
        integers.addFirst(0);
        integers.addLast(44);
        integers.addLast(44);
        integers.removeLast();
        integers.removeFirst();
        integers.removeFirst();
        integers.removeFirst();
        integers.forEach(System.out::println);

    }
}