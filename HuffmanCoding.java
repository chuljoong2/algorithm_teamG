import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;

class Node {
    public char ch;
    public int freq;
    public Node left, right;
}

class FrequencyComparator implements Comparator<Node>{  //두 노드의 빈도수를 비교
    public int compare(Node a, Node b){
        int freqA = a.freq;
        int freqB = b.freq;
        return freqA - freqB;
    }
}

public class HuffmanCoding {
    public static PriorityQueue<Node> queue;
    public static HashMap<Character, String> binaryCodeTable = new HashMap<>();

    public static Node huffmanEncoding(int n){
        for (int i = 0; i < n - 1; i++){
            Node temp = new Node();
            temp.right = queue.poll();
            temp.left = queue.poll();
            temp.freq = temp.right.freq + temp.left.freq;
            queue.add(temp);
        }
        return queue.poll();
    }
    public static void main(String[] args) throws IOException {
        String text = new Scanner(new File("src/alice.txt")).next();    //입력 txt파일
        System.out.println(text);       //txt파일 내용 출력

        HashMap<Character, Integer> dict = new HashMap<Character, Integer>();
        for (int i = 0; i < text.length(); i++){
            char temp = text.charAt(i);             //문자열을 문자단위로 나눔
            if (dict.containsKey(temp)) {           //해시맵에 저장하고 빈도수 계산
                dict.put(temp, dict.get(temp)+1);
            } else {
                dict.put(temp, 1);
            }
        }
        System.out.println(dict);

        queue = new PriorityQueue<Node>(100, new FrequencyComparator());    //우선순위 큐
        int number = 0;
        for (Character c: dict.keySet()){
            Node temp = new Node();
            temp.ch = c;
            temp.freq = dict.get(c);
            queue.add(temp);
            number++;
        }

        Node root = huffmanEncoding(number);

        binaryEncode(root, new String());
        String result = new String();
        for (int i = 0; i < text.length(); i++){
            result = result + binaryCodeTable.get(text.charAt(i)) +"";
        }
        System.out.println(result);

        int originDataByteSize = text.getBytes(StandardCharsets.UTF_8).length;
        System.out.println("기존 데이터 사이즈: " + originDataByteSize * 8 + "Bit");    //기존 데이터 사이즈 출력
        System.out.println("인코딩 데이터 사이즈: " + result.length() + "Bit");         //인코딩된 데이터 사이즈 출력
    }

    private static void binaryEncode(Node n, String s) {    //이진코드 생성
        if (n == null){
            return;
        }
        binaryEncode(n.left, s + "0");
        binaryEncode(n.right, s + "1");
        if (n.ch != '\0'){
            System.out.println(n.ch + ":" + s);
            binaryCodeTable.put(n.ch, s);
        }
    }
}
