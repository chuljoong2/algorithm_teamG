import java.util.*;
import java.io.*;

class Node {
    public char ch;
    public int freq;
    public Node left, right;
}

class FrequencyComparator implements Comparator<Node>{
    public int compare(Node a, Node b){
        int freqA = a.freq;
        int freqB = b.freq;
        return freqA - freqB;
    }
}

public class HuffmanCoding {
    public static PriorityQueue<Node> queue;

    public static void main(String[] args) throws IOException {
        String text = new Scanner(new File("src/alice.txt")).next();
        System.out.println(text);

        HashMap<Character, Integer> dict = new HashMap<Character, Integer>();
        for (int i = 0; i < text.length(); i++){
            char temp = text.charAt(i);
            if (dict.containsKey(temp)) {
                dict.put(temp, dict.get(temp)+1);
            } else {
                dict.put(temp, 1);
            }
        }
        System.out.println(dict);

        queue = new PriorityQueue<Node>(100, new FrequencyComparator());
        int number = 0;
        for (Character c: dict.keySet()){
            Node temp = new Node();
            temp.ch = c;
            temp.freq = dict.get(c);
            queue.add(temp);
            number++;
        }
        System.out.println(number);
    }
}
