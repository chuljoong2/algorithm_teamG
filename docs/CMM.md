# 연속 행렬 곱셈 (Chained Matrix Multiplications)

연속 행렬 곱셈 (Chained Matrix Multiplications) 문제는 연속된 행렬들의 곱셉에 필요한 원소 간의 최소 곱셉 횟수를 찾는 문제이다.

2 * 3 행렬과 3 * 4 행렬을 곱하면 2 * 4 행렬이 나온다. 원소를 곱하는 횟수를 구하면 2 * 3 * 4 = 24가 나온다. 이를 일반화 하면 i * j 행렬과 j * k 행렬을 곱하면 i * k 행렬이 나오고 원소 곱셈의 횟수는 i * j *k 가 된다. 예를들어 아래와 같은 행렬 곱셈이 있다고 하자.

![https://user-images.githubusercontent.com/63987872/162661597-bbe7fd38-258b-4052-809d-6db41928176b.png](https://user-images.githubusercontent.com/63987872/162661597-bbe7fd38-258b-4052-809d-6db41928176b.png)

29는 1 * 7 + 2 * 2 + 3 * 6를 통하여 만들어지고 총 3번 곱해서 만들어진다. 즉, k * j * i가 원소 곱셈 횟수가 된다.

### **연속 행렬 곱셈 (⊂ Dynamic Programming)**

- 연속된 행렬들의 곱셈에 필요한 원소 간 최소 곱셈 횟수를 찾는 문제
- 계산할 때 기본 행렬 곱셈과 같은 방식으로 계산함
- 교환 법칙이 성립하지 않음

> 연속 행렬 곱셈의 예시
>
- 10 x 20 행렬 A, 20 x 5 행렬 B, 5 x 15 행렬 C가 있다고 해보자.
- *A x B x C = (A x B) x C = A x (B x C)* 와 같은 식이 성립하는데, 이를 통해 **결합 법칙**이 성립함을 알 수 있다.
- **A[10 x 20] x B[20 x 5] = C[10 x 5]**
- **(A x B)[10 x 5] x C[5 x 15] = ABC[10 x 15]** ⋅⋅⋅ ①
- **A[10 x 20] x (B x C)[20 x 15] = ABC[10 x 15]** ⋅⋅⋅ ②

### **▼ 무슨 차이가 있을까??**

- ① (A x B) x C
    - A x B를 먼저 계산하는데 10 x 20 x 5 = 1000 & (A x B) x C를 계산하는데 10 x 5 x 15 = 750으로 총 1750번의 계산이 필요하다.
- ② A x (B x C)
    - B x C를 먼저 계산하는데 20 x 5 x 15 = 1500 & A x (B x C)를 계산하는데 10 x 20 x 15 = 3000으로 총 4500번의 계산이 필요하다.


**그렇다면 연쇄 행렬이 3개가 아닌 4개면 어떻게 될까??**

20 * 1 행렬(A), 1 * 30 행렬(B), 30 * 10 행렬(C), 10 * 10 행렬(D)이 있다고 가정하면 총 다섯가지의 경우의 수가 존재한다.

((AxB)xC)xD) = (20x1x30) + (20x30x10) + (20*10x10) = 8,600

Ax(Bx(CxD)) = (30x10x10) + (1x30x10) + (20x1x10) = 3,500

(AxB)x(CxD) = (20x1x30) + (30x10x10) + (20x30x10) = 9,600

(Ax((BxC)xD) = (1x30x10) + (1x10x10) + (20x1x10) = 600

위와 같이 곱셈을 하는 순서에 따라 600~9600번의 곱셈 횟수가 나오게 되는데,그 중 최소 곱셈 횟수는 600번이다.

※ 연쇄행렬 최소곱셈 알고리즘은 **행렬곱셈에서 곱하는 순서에 따라 곱셈의 횟수가 달라지는데 이러한 법칙을 이용하여 최소로 곱하는 횟수를 구하는 것**이다.

### 문제 정의

- n개의 연쇄 행렬 곱셈: A1 * A2 * ... * An
- **Ak-1의 행의 개수와 Ak의 열의 개수가 같다.**
- dk는 행렬 Ak의 행의 개수로 정한다. (1 ≤ k ≤ n)
- **dk-1은 행렬 Ak의 열의 개수, Ak-1의 행의 개수가 된다.**
- d0은 A1의 열의 개수가 된다.

### 문제 해결

1. 재귀 관계식을 구한다.

   M: 연쇄 행렬을 곱하는데 필요한 곱셈의 최소 횟수 행렬

   M[i][j]: Ai에서 Aj까지 행렬을 곱하는데 필요한 곱셈의 최소 횟수

   목표: Ai, ..., Aj 행렬을 (Ai, Ai+1, ... Ak)(Ak+1, Ak+2, ..., Aj)로 분할하는 재귀 관계식 찾기

   예를들어 A1 ~ A6은 다음과 같이 분할이 가능하다.

    - [k = 1] (A1)(A2 A3 A4 A5 A6)
    - [K = 2] (A1 A2)(A3 A4 A5 A6)
    - [k = 3] (A1 A2 A3)(A4 A5 A6)
    - [k = 4] (A1 A2 A3 A4)(A5 A6)
    - [k = 5] (A1 A2 A3 A4 A5)(A6)

   각 분할의 곱셈횟수 = 각 부분행렬의 곱셈횟수 + 두 행렬의 곱셈횟수

   **M[1][k] + M[k+1]M[6] + (d0*dk*d6)

   이때, M[i][j]에는 Ai에서 Aj까지 행렬을 곱하는데 필요한 곱셈의 최소 횟수가 들어가야하므로 최적분할에 대해 고려해줘야한다. 따라서 M[1][6]은 아래와 같다.

   **M[1][6] = min(M[1][k] + M[k+1]M[6] + (d0*dk*d6)) {k = 1 ~ 5}

   이를 일반화 한 재귀 관계식은 아래와 같다.

    ```
    for 1 <= i <= j <= n,
    if i == j, M[i][j] = 0
    if i < j, M[i][j] = min(M[i][k] + M[k+1]M[j] + (di-1*dk*dj)) {k = i ~ j-1}
    ```

2. 상향식으로 구한다.

   초기화: M[i][j] = 0 (i와 j가 같은 경우)

   최종목표: M[1][n]

   ![https://user-images.githubusercontent.com/63987872/162661591-bff3c824-4b1d-4bd3-b0e2-fce4b99391d2.png](https://user-images.githubusercontent.com/63987872/162661591-bff3c824-4b1d-4bd3-b0e2-fce4b99391d2.png)


### 구현

1. 변수 선언 과정을 수행한다.

    ```java
    import java.util.*;
    
    public class ChainedMatrixMultiplications {
        private static int[] d;
        private static int[][] m;
    
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            d = new int[n]; // 행의 개수
            m = new int[n][n]; // 최소 곱셈 횟수
    
            for(int i = 0; i < n; i++) {
                d[i] = scanner.nextInt();
            }
        }
    }
    ```

   **n: 행렬의 개수**

   **d: 행의 개수 배열 (’dk-1은 행렬 Ak의 열의 개수, Ak-1의 행의 개수가 된다.’이므로 열의 개수는 따로 입력받지 않음)**

   **m: 최소 곱셈 횟수 배열**

2. 재귀 관계식을 통한 구현

    ```
    for 1 <= i <= j <= n,
    if i == j, M[i][j] = 0
    if i < j, M[i][j] = min(M[i][k] + M[k+1]M[j] + (di-1*dk*dj)) {k = i ~ j-1}
    ```

   위와 같은 재귀 관계식으로 구현하면 아래와 같다.

    ```java
    import java.util.*;
    
    public class ChainedMatrixMultiplications {
        private static int[] d;
        private static int[][] m;
        final static int INF = Integer.MAX_VALUE / 2; // 양의 무한대
    
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            d = new int[n]; // 행의 개수
            m = new int[n][n]; // 최소 곱셈 횟수
    
            for(int i = 0; i < n; i++) {
                d[i] = scanner.nextInt();
            }
    
            for (int L = 0; L < n-1; L++){
                for (int i = 1; i <= n-1-L; i++) {
                    int j = i + L;
                    if (i == j){
                        m[i][j] = 0;
                        continue;
                    }
                    m[i][j] = INF;
                    for (int k = i; k <= j-1; k++){
                        m[i][j] = Math.min(m[i][j], m[i][k] +m[k+1][j] + d[i-1]*d[k]*d[j]);
                    }
    								System.out.println(m[i][j]); // 갱신되는 값 확인
                }
            }
    
            System.out.println(m[1][n-1]); // answer
        }
    }
    ```

   n = 5, d = [20, 1, 30, 10, 10]을 입력한다고 하면 결과는 다음과 같다.

   **600 300 3000 500 400 600**

   ans = 600


### 코드

```java
import java.util.*;

public class ChainedMatrixMultiplications {
    private static int[] d;
    private static int[][] m;
    final static int INF = Integer.MAX_VALUE / 2; // 양의 무한대

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        d = new int[n]; // 행의 개수
        m = new int[n][n]; // 최소 곱셈 횟수

        for(int i = 0; i < n; i++) {
            d[i] = scanner.nextInt();
        }

        for (int L = 0; L < n-1; L++){
            for (int i = 1; i <= n-1-L; i++) {
                int j = i + L;
                if (i == j){
                    m[i][j] = 0;
                    continue;
                }
                m[i][j] = INF;
                for (int k = i; k <= j-1; k++){
                    m[i][j] = Math.min(m[i][j], m[i][k] +m[k+1][j] + d[i-1]*d[k]*d[j]);
                }

            }
        }
        System.out.println(m[1][n-1]); // answer
    }
}
```