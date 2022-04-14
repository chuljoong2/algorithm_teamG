import java.util.*;
import java.io.*;

public class HuffmanCoding {
    public static PriorityQueue<Node> priorityQueue;
    public static HashMap<Character, String> binaryCodeTable = new HashMap<>();
    public static class Node {
        public char c;
        public int freq;
        public Node left;
        public Node right;
    }

    public static void generateCode(Node node, String code){
        if (node==null){
            return;
        }
        generateCode(node.left, code + "0");
        generateCode(node.right, code + "1");
        if (node.c != '\0'){
            System.out.println(node.c + ":" +code);
            binaryCodeTable.put(node.c, code);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("src/alice.txt"));
        HashMap<Character, Integer> dict = new HashMap<Character, Integer>();
        List<String> textStr = new ArrayList<String>();
        int nodeCount = 0;
        System.out.println("► alice.txt 내용");
        while (scanner.hasNextLine()){
            String text = scanner.nextLine();
            textStr.add(text);
            System.out.println(text);
            for (int i = 0; i < text.length(); i++){
                char temp = text.charAt(i);
                if (temp == ' '){
                    continue;
                }
                if (dict.containsKey(temp)) {
                    dict.put(temp, dict.get(temp)+1);
                } else {
                    dict.put(temp, 1);
                    nodeCount++;
                }
            }
        }
        System.out.println();

        System.out.println("► 문자 빈도수 계산");
        System.out.println(dict);
        System.out.println();

        priorityQueue = new PriorityQueue<Node>(nodeCount, new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
                return a.freq - b.freq;
            }
        });
        for (Map.Entry<Character, Integer> item: dict.entrySet()){
            Node node = new Node();
            node.c = item.getKey();
            node.freq = item.getValue();
            priorityQueue.add(node);
        }

        for (int i = 0; i < nodeCount-1; i++){
            Node node = new Node();
            node.right = priorityQueue.poll();
            node.left = priorityQueue.poll();
            node.freq = node.right.freq + node.left.freq;
            priorityQueue.add(node);
        }
        Node root = priorityQueue.poll();

        System.out.println("► 허프만 트리를 통해 이진코드 생성");
        generateCode(root, new String());
        System.out.println();

        String result = new String();
        int charCount = 0;
        for (int i = 0; i < textStr.size(); i++){
            String text = textStr.get(i);
            for (int j = 0; j < text.length(); j++){
                char temp = text.charAt(j);
                if (temp == ' '){
                    continue;
                }
                result = result + binaryCodeTable.get(temp) + "";
                charCount ++;
            }
        }
        System.out.println("► 인코딩된 텍스트 결과");
        System.out.println(result);
        System.out.println();

        int dataSize = charCount * 8;
        int encodedDataSize = result.length();
        System.out.println("► 기존 데이터 크기: " + dataSize + "bit");
        System.out.println("► 인코딩된 데이터 크기: "+ encodedDataSize + "bit");
    }
}
