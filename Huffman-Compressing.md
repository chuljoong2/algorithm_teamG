# 허프만 부호화 (Huffman coding)

허프만 부호화(코딩)는 주어진 파일의 크기를 줄이는 파일 압축 방법으로 그리디 알고리즘 중 하나이다. 문자열에서 빈번히 나타나는 문자에는 짧은 이진 코드를 할당하고 드물게 나타나는 문자에는 긴 이진코드를 할당한다.

만약 압축을 안쓰게 되면 영어로만 작성된 파일은 각 문자당 8bit(아스키코드)를 사용하므로 1000자를 사용했다면 16000bit을 차지하게 된다.

<br/>

## 허프만 압축
> ### **허프만 압축이란?**
- 특정 문자를 입력받았을 때 그 문자의 고정된 크기를 압축하여, **전체적인 파일의 크기를 줄이거나 전송 시간을 단축**하기 위한 것을 의미한다. 
- 표현 방식은 **이진 숫자**로 나타낸다.
- 이진 트리의 형태로 **허프만 트리**를 구성한다.
- 트리의 루트를 기준으로 **왼쪽으로 갈 땐 0, 오른쪽으로 갈 땐 1**로 표현한다.
- 허프만 압축을 통해 빈도수 낮은 문자에는 짧은 이진 숫자가 할당되고, 빈도수가 높은 문자에는 긴 이진 숫자가 할당된다. 
- 변환된 문자들은 서로 중복되지 않기에, 다른 문자들간 구별이 쉽다는 장점이 있다.
___
<br/>

## ① 허프만 압축 기본 내용 정리 

### **허프만 압축** *알고리즘*
```
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
### **빈도수** 체크 코드

노드 클래스를 선언한다.
```java
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;

class Node {
	private char cCharacter;		// 문자
	private int iFrequency;			// 빈도수
	
	// 생성자
	public Node() {};
	public Node(char cCharacter)
	{
		this.cCharacter = cCharacter;
		this.iFrequency = 0;
	}
	public Node(char cCharacter, int iFrequency)
	{
``` 

### 전체 구현 코드(**빈도수 구현** 코드 포함)
```java
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;

class Node {
	private char cCharacter;		// 문자
	private int iFrequency;			// 빈도수
	
	//생성자
	public Node() {};
	public Node(char cCharacter)
	{
		this.cCharacter = cCharacter;
		this.iFrequency = 0;
	}
	public Node(char cCharacter, int iFrequency)
	{
		this.cCharacter = cCharacter;
		this.iFrequency = iFrequency;
	}
	
	// get, set함수
	public void setiFrequency(int iFrequency)
	{
		this.iFrequency = iFrequency;
	}
	public int getiFrequency()
	{
		return this.iFrequency;
	}
	public void setcCharacter(char cCharacter)
	{
		this.cCharacter = cCharacter;
	}
	public char getcCharacter()
	{
		return this.cCharacter;
	}
	
}

public class PracticeClass {
	public static void main(String[] args) {
		Map<String , Integer> hm = new HashMap<>();	//해시맵 선언 <문자, 빈도수>
		String sInput = "abcdeeefGggHHisjdkk !d.";		//입력된 텍스트(임시)
		String[] sInputSplit = sInput.split("");		//입력된 텍스트를 한글자씩 배열에 넣음
		int Nodelength = 0;								//노드수
		
		for(int i = 0 ; i < sInput.length() ; i++)
		{
			if(hm.containsKey(sInputSplit[i]))			//해시맵에 이미 있는 글자면 value값을 1 증가시킴
			{
				hm.put(sInputSplit[i], hm.get(sInputSplit[i])+1);
			}
			else if(!hm.containsKey(sInputSplit[i]))	//해시맵에 없는 글자면 글자를 넣고 value를 1로 설정
			{
				hm.put(sInputSplit[i], 1);
				Nodelength++;
			}
		}
		
		Node[] node = new Node[Nodelength];
		int nodeindex = 0;
		
		for(Entry<String , Integer> entrySet : hm.entrySet())	//해시맵을 Entry에 넣고 그걸 node객체에 넣음
		{
			node[nodeindex++] = new Node(entrySet.getKey().charAt(0), entrySet.getValue());
		}
		/*Set<String> keySet = hm.keySet();
		for (String key : keySet) {
		System.out.println(key + " : " + hm.get(key));
		}*/
		
		for(int i = 0 ; i < Nodelength ; i++)
		{
			System.out.println(node[i].getcCharacter() + " : " + node[i].getiFrequency());		
		}

	}
}
```

### 출력값
- 빈칸과 각종 기호, 문자를 포함한 빈도수가 출력됨을 알 수 있다.
- 편의를 위해 출력값은 아래에 별도로 나타냈다.
```
    : 1
  a : 1
  ! : 1
  b : 1
  c : 1
  d : 3
  e : 3
  f : 1
  G : 1
  g : 2
  H : 2
  i : 1
  j : 1
  k : 2
  . : 1
  s : 1
```


> ## 허프만 알고리즘 수행 과정(우선순위 큐, 트리)

문자 **A, T, G, C**가 있다고 하고, 가중치는 각각 *450 90 120 270*이다.
1) **우선순위 큐(Q)** 생성
2) **가중치**가 작은 **T, C, G, A** 순서로 배열
3) **빈도수**가 제일 **작은 두 노드(T, G)** 를 제거
4) **새 노드 N1**을 만들어, 두 노드의 부모노드가 됨(*새 노드의 가중치는 두 노드의 가중치를 더한 값과 같음*)
5) Q에 남아있는 것 중 가중치가 새 노드와 비슷한 노드 C를 꺼내와서 N1과 C를 더해 새 노드 N2(480) 생성
6) Q에 남아있는 A와 N2를 비교 후 더하여 새로운 노드 N3(930)을 생성

<br/>

```
위 알고리즘에 따라 정리된 허프만 트리 형태
            N3
          /    \
         A     N2
              /  \
             N1   C
            /  \      
           T    G
```
### 허프만 코드 변환 값
```
A : 0
T : 100
G : 120
C : 11
```

### 허프만 압축 후 크기 비교
1) 기존 파일 크기(아스키코드)<br/>
계산 방법<br/>
**(모든 노드 가중치의 합) x 8** <br/>
(450 + 90 + 120 + 270) x 8 = 7440bit

2) 압축된 파일 크기<br/>
계산 방법<br/>
**(가중치 x 저장된 bit 수) + ...** <br/>
(450 x 1)+(90 x 3)+(120 x 3)+(270 x 2) = 1620bit <br/>

허프만 압축 과정을 통해 **약 5배** 정도 압축된 것을 확인할 수 있다.

<br/>

## ② 허프만 압축 응용 - 특정 문서를 입력받고 허프만 압축으로 파일 압축하기
### **허프만 압축 알고리즘**

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
            System.out.println(text); 
            // AAAAAAABBCCCDEEEEFFFFFFG
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
            System.out.println(dict); 
            // {A=7, B=2, C=3, D=1, E=4, F=6, G=1}
    
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
   
   <br/>

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

   알파벳 수는 100개를 넘기지 않으므로 우선순위 큐의 공간을 넉넉하게 100으로 잡았다.
   
   <br/>


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

   <br/>
   

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

   편의를 위해 띄어쓰기로 출력값을 구분하였다.

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


_  __
## Reference 
- 양성봉 著, **알기쉬운 알고리즘** 

## Part
- 개념 정리, 알고리즘 및 코드 구현 _ 안재일
- 개념 정리, 빈도수 체크, 코드 구현 _ 김호현
- 개념 정리, 파일 적재, 코드 구현 _ 윤철중