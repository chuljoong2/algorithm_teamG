import java.util.*;
import java.io.*;

public class HuffmanCoding {
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

    }
}
