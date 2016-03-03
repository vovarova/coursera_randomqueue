import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

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
        /*for (int i = 0; i < size; i++) {
            temp[i] = items[i];
        }*/
        System.arraycopy(items, 0, temp, 0, items.length);
        items = temp;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (size == items.length) resize(2 * items.length);
        items[size++] = item;
    }

    public Item dequeue() {
        int randomNumber = StdRandom.uniform(size - 1);
        Item result = items[randomNumber];
        System.arraycopy(items, randomNumber+1, items, randomNumber, size - randomNumber - 1);
        items[--size] = null;
        if (size > 0 && size == items.length / 4) resize(items.length / 2);
        return result;
    }

    public Item sample() {
        int randomNumber = StdRandom.uniform(size - 1);
        return items[randomNumber];
    }

    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    class RandomizedIterator implements Iterator<Item> {
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
            return items[randomNumbers[currentNumber++]];
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> integers = new RandomizedQueue<>();
        for (int i = 0; i < 13; i++) {
            integers.enqueue(i);
        }
        integers.dequeue();

        integers.forEach(System.out::println);

    }
}