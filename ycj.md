# 허프만 부호화 (Huffman coding)

허프만 복호화는 주어진 파일의 크기를 줄이는 파일 압축 방법 중 하나로 파일에 빈번히 나타나는 문자에는 짧은 이진 코드를 할당하고 드물게 나타나는 문자에는 긴 이진코드를 할당한다.

만약 압축을 안쓰게 되면 영어로만 작성된 파일은 각 문자당 8bit(아스키코드)를 사용하므로 1000자를 사용했다면 16000bit을 차지하게 된다.

## 허프만 압축 알고리즘

1. 다음과 같이 문자열이 담긴 파일을 허프만 코딩으로 압축하려 한다.

   AAAAAAABBCCCDEEEEFFFFFFG

2. 원본 데이터에 포함된 각 문자에 대한 빈도수를 구하여 내림차순으로 정렬한다.

   A(7), F(6). E(4), C(3), B(2), D(1), G(1)

3. 빈도수가 가장 낮은 D와 G를 묶어 이진 트리를 구성하고 이 둘을 묶는 트리의 루트 노드에는 두 문자 빈도수의 합인 2를 넣는다. 이후 내림차순 정렬이 다시 필요한 경우 재배열한다.
4. 마찬가지로 값이 가장 작은 두개의 노드를 묶어 이진 트리를 구성하고 그 루트 노드에는 두 빈도수의 합을 넣는다. 이때, B와 2(D,G)가 값이 가장 작은 값이므로 묶이게 되며 합은 4가 된다. 이후 내림차순 정렬이 다시 필요한 경우 재배열한다.
5. 동일한 방법으로 다음 단계를 수행하면 4(B,D,G)와 C(3)이 트리로 묶이게 된다. 이후 내림차순 정렬이 다시 필요한 경우 재배열한다.
6. 이러한 과정을 반복하여 전체 트리가 구성되는 각 단계는 아래와 같다.
7. 마지막으로 전체 트리가 완성되면 왼쪽에는 0, 오른쪽에는 1을 넣어가며 허프만 트리를 완성시킨다.

### 구현

1. 문자열일 담긴 txt파일을 읽어온다.

    ```
    src/alice.txt
    
    AAAAAAABBCCCDEEEEFFFFFFG
    ```

   파일명은 src폴더에 alice.txt이다.

    ```java
    import java.util.*;
    import java.io.*;
    
    public class HuffmanCoding {
        public static void main(String[] args) throws IOException {
            String text = new Scanner(new File("src/alice.txt")).next();
            System.out.println(text); // AAAAAAABBCCCDEEEEFFFFFFG
        }
    }
    ```

   text변수에 읽어온 값을 저장하고 잘 읽어왔는지 테스트해본다.

2. 입력받은 문자열을 문자단위로 끊어 해쉬 맵에 저장한 후 빈도수를 계산한다.

    ```java
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
            System.out.println(dict); // {A=7, B=2, C=3, D=1, E=4, F=6, G=1}
    
        }
    }
    ```

3. 우선순위 큐에 삽입하기 전 초기화 과정을 수행한다.

    ```java
    class Node {
        public char ch;
        public int freq;
        public Node left, right;
    }
    ```

   Node는 우선순위 큐에 삽입된다.

    ```java
    class FrequencyComparator implements Comparator<Node>{
        public int compare(Node a, Node b){
            int freqA = a.freq;
            int freqB = b.freq;
            return freqA - freqB;
        }
    }
    ```

   FrequencyComparator는 두개의 노드의 빈도수를 비교한다. 이후 우선순위 큐의 우선순위를 정해주는 역할을 한다.

4. 빈도수와 문자로 이루어진 해쉬 맵을 빈도수를 우선순위로 우선순위 큐에 삽입한다.

    ```java
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
            System.out.println(number); // 7
        }
    }
    ```

   알파벳 수는 100개를 넘기지 않으므로 큐에 공간을 넉넉하게 100으로 잡았다.

5. 우선순위 큐를 바탕으로 트리구조를 만들어준다.

    ```java
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
        public static Node huffmanEncoding(int n){
            for (int i = 0; i < n - 1; i++){
                Node temp = new Node();
                z.right = queue.poll();
                temp.left = queue.poll();
                temp.freq = temp.right.freq + temp.left.freq;
                queue.add(temp);
            }
            return queue.poll();
        }
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
            Node root = huffmanEncoding(number);
            System.out.println(root);
        }
    }
    ```

6. 허프만 트리를 바탕으로 이진코드를 만들어준다.

    ```java
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
            Node root = huffmanEncoding(number);
            System.out.println(root);
    
            binaryEncode(root, new String());
        }
    
        private static void binaryEncode(Node n, String s) {
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
    ```

   생성된 이진코드는 binaryCodeTable 해쉬 맵에 저장한다.

    ```
    A:00
    G:01000
    D:01001
    B:0101
    C:011
    F:10
    E:11
    ```

   생성된 이진코드를 출력하면 위와 같다.

7. 문자를 이진코드로 변환하여 출력하여 허프만코딩 결과를 확인한다.

    ```java
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
            Node root = huffmanEncoding(number);
            System.out.println(root);
    
            binaryEncode(root, new String());
            String result = new String();
            for (int i = 0; i < text.length(); i++){
                result = result + binaryCodeTable.get(text.charAt(i)) +" ";
            }
            System.out.println(result);
        }
    
        private static void binaryEncode(Node n, String s) {
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
    ```

   편의를 위해 띄어쓰기로 구분하였다.

    ```
    00 00 00 00 00 00 00 0101 0101 011 011 011 01001 11 11 11 11 10 10 10 10 10 10 01000
    ```


### 전체 코드

```java
import java.nio.charset.StandardCharsets;
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
        String text = new Scanner(new File("src/alice.txt")).next();
        System.out.println(text); // print #1

        HashMap<Character, Integer> dict = new HashMap<Character, Integer>();
        for (int i = 0; i < text.length(); i++){
            char temp = text.charAt(i);
            if (dict.containsKey(temp)) {
                dict.put(temp, dict.get(temp)+1);
            } else {
                dict.put(temp, 1);
            }
        }
        System.out.println(dict); // print #2

        queue = new PriorityQueue<Node>(100, new FrequencyComparator());
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
        System.out.println(result); // print #4

        int originDataByteSize = text.getBytes(StandardCharsets.UTF_8).length;
				// print #5, #6
        System.out.println("기존 데이터 사이즈: " + originDataByteSize * 8 + "Bit");
        System.out.println("인코딩 데이터 사이즈: " + result.length() + "Bit");
    }

    private static void binaryEncode(Node n, String s) {
        if (n == null){
            return;
        }
        binaryEncode(n.left, s + "0");
        binaryEncode(n.right, s + "1");
        if (n.ch != '\0'){
            System.out.println(n.ch + ":" + s); // print #3
            binaryCodeTable.put(n.ch, s);
        }
    }
}
```

### 출력

```
AAAAAAABBCCCDEEEEFFFFFFG
{A=7, B=2, C=3, D=1, E=4, F=6, G=1}
A:00
G:01000
D:01001
B:0101
C:011
F:10
E:11
0000000000000001010101011011011010011111111110101010101001000
기존 데이터 사이즈: 192Bit
인코딩 데이터 사이즈: 61Bit
```