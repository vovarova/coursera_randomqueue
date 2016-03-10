import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[10];
        size = 0;
    }

    private void resize(int capacity) {
        assert capacity >= size;
        Item[] temp = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, temp, 0, size);
        items = temp;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("Item can't be null.");
        if (size == items.length) resize(2 * items.length);
        items[size++] = item;
    }

    private int getRandomNumber() {
        int number = 0;
        if (size > 1) {
            number = StdRandom.uniform(size);
        }
        return number;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int randomNumber = getRandomNumber();
        Item result = items[randomNumber];
        int lastEl = size - 1;
        if (randomNumber != lastEl) {
            items[randomNumber] = items[lastEl];
        }
        items[lastEl] = null;
        size--;
        if (size > 0 && size == items.length / 4) resize(items.length / 2);
        return result;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int randomNumber = getRandomNumber();
        return items[randomNumber];
    }

    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int currentNumber = 0;
        private int[] randomNumbers;

        public RandomizedIterator() {
            randomNumbers = new int[size];
            for (int i = 0; i < size; i++) {
                randomNumbers[i] = i;
            }
            StdRandom.shuffle(randomNumbers);
        }

        @Override
        public boolean hasNext() {
            return currentNumber < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[randomNumbers[currentNumber++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Operation is not suported");
        }
    }

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();

        RandomizedQueue<Integer> integers = new RandomizedQueue<>();
        int i1 = 20;
        for (int i = 1; i <= i1; i++) {
            integers.enqueue(i);
        }
        for (int i = 0; i < i1; i++) {
            System.out.println(integers.dequeue());
        }
        System.out.println(stopwatch.elapsedTime());

    }
}