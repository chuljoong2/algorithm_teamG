## **Part**

- 개념 정리, 알고리즘 및 코드 구현 _ 안재일
- 개념 정리, 빈도수 체크, 코드 구현 _ 김호현
- 개념 정리, 파일 적재, 코드 구현 _ 윤철중

# ****허프만 부호화 (Huffman coding)****

> 허프만 코딩이란?
>
- 특정 문자를 입력받았을 때 그 문자의 고정된 크기를 압축하여, **전체적인 파일의 크기를 줄이거나 전송 시간을 단축**하기 위한 것을 의미한다.
- 표현 방식은 **이진 숫자**로 나타낸다.
- 이진 트리의 형태로 **허프만 트리**를 구성한다.
- 트리의 루트를 기준으로 **왼쪽으로 갈 땐 0, 오른쪽으로 갈 땐 1**로 표현한다.
- 허프만 압축을 통해 **빈도수 높은 문자에는 짧은 이진코드가 할당**되고, **빈도수가 낮은 문자에는 긴 이진코드가 할당**된다.
- 변환된 문자들은 서로 중복되지 않기에, 다른 문자들간 구별이 쉽다는 장점이 있다.

### 알고리즘

```
허프만 압축 알고리즘

HuffmanCoding Algorithm

Input: 입력된 n개의 문자에 대한 각각의 빈도수
Output: Huffman Tree

입력받은 각 문자에 대한 노드를 만들고, 각각의 빈도수를 그 노드에 저장함.
각 노드들의 빈도수를 비교하기 위해 우선순위 큐 Q를 사용함.
while {Q의 Node 수 >= 2} {
    빈도수가 작은 2개(A, B)의 노드를 Q에서 제거
    새 노드 N을 만들고, 제거한 노드를 자식노드로 설정
    N의 빈도수 <- A의 빈도수 + B의 빈도수
    N을 Q에 삽입
}
return Q
```

### 수행과정

1. 다음과 같이 문자열이 담긴 파일을 허프만 코딩으로 압축하려 한다.

   **AAAAAAABBCCCDEEEEFFFFFFG**

2. 원본 데이터에 포함된 각 문자에 대한 빈도수를 구하여 내림차순으로 정렬한다.

   **A(7), F(6). E(4), C(3), B(2), D(1), G(1)**

3. 빈도수가 가장 낮은 D와 G를 묶어 이진 트리를 구성하고 이 둘을 묶는 트리의 루트 노드에는 두 문자 빈도수의 합인 2를 넣는다. 이후 내림차순 정렬이 다시 필요한 경우 재배열한다.

    ```
      2
     / \
    D   G
    ```

4. 마찬가지로 값이 가장 작은 두개의 노드를 묶어 이진 트리를 구성하고 그 루트 노드에는 두 빈도수의 합을 넣는다. 이때, B와 2(D,G)가 값이 가장 작은 값이므로 묶이게 되며 합은 4가 된다. 이후 내림차순 정렬이 다시 필요한 경우 재배열한다.

    ```
      4
     / \  
    B   2
       / \
      D   G
    ```

5. 동일한 방법으로 다음 단계를 수행하면 4(B,D,G)와 C(3)이 트리로 묶이게 된다. 이후 내림차순 정렬이 다시 필요한 경우 재배열한다.

    ```
      7
     / \ 
    C   4
       / \  
      B   2
         / \
        D   G
    ```

6. 이러한 과정을 반복하여 전체 트리가 구성되는 각 단계는 아래와 같다.

    ```
           24
           / \
     14          10
     / \	       / \
    A   7       F   E
    		 / \ 
    		C   4
    		   / \  
    		  B   2
    		     / \
    		    D   G
    ```

7. 마지막으로 전체 트리가 완성되면 왼쪽에는 0, 오른쪽에는 1을 넣어가며 허프만 트리를 완성시킨다.

    ```
    A:00
    G:01000
    D:01001
    B:0101
    C:011
    F:10
    E:11
    ```


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


### 코드

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

### 데이터 사이즈 비교

기존 데이터 사이즈 192bit에서 인코딩 되어 61bit로 압축할 수 있다. 이는 문자가 더 길면 길어질 수 록 효과는 극대화 될 것 이다.