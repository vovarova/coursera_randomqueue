import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        if(k==0){
            return;
        }
        int currentIndex = 0;
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while (!isEmpty()) {
            currentIndex++;
            String item = getItem();
            if (randomizedQueue.size() == k) {
                int uniform = StdRandom.uniform(currentIndex);
                if(uniform+1<=k){
                    randomizedQueue.dequeue();
                    randomizedQueue.enqueue(item);
                }
            }else {
                randomizedQueue.enqueue(item);
            }
        }
        for (int i = 0; i < k; i++) {
            System.out.println(randomizedQueue.dequeue());
        }
    }

    private static boolean isEmpty() {
        return StdIn.isEmpty();
    }

    private static String getItem() {
        return StdIn.readString();



        //return StdIn.readString();
    }
}
