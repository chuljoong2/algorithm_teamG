> # 연속 행렬 곱셈(Chained Matrix Multipilications)

### **연속 행렬 곱셈** (⊂ Dynamic Programming)
- 연속된 행렬들의 곱셈에 필요한 원소 간 최소 곱셈 횟수를 찾는 문제
- 계산할 때 기본 행렬 곱셈과 같은 방식으로 계산함
- 교환 법칙이 성립하지 않음
<br/>

> ### **연속 행렬 곱셈**의 *예시*
- 10 x 20 행렬 A, 20 x 5 행렬 B, 5 x 15 행렬 C가 있다고 해보자. 
- *A x B x C = (A x B) x C = A x (B x C)* 와 같은 식이 성립하는데, 이를 통해 **결합 법칙**이 성립함을 알 수 있다.
- **A[10 x 20] x B[20 x 5] = C[10 x 5]** <br/>
- **(A x B)[10 x 5] x C[5 x 15] = ABC[10 x 15]** ⋅⋅⋅ ①
- **A[10 x 20] x (B x C)[20 x 15] = ABC[10 x 15]** ⋅⋅⋅ ②

<br/>

### ▼ 무슨 **차이**가 있을까??
- ① (A x B) x C
  - A x B를 먼저 계산하는데 10 x 20 x 5 = 1000 & (A x B) x C를 계산하는데 10 x 5 x 15 = 750으로 총 1750번의 계산이 필요하다.
- ② A x (B x C)
  - B x C를 먼저 계산하는데 20 x 5 x 15 = 1500 & A x (B x C)를 계산하는데 10 x 20 x 15 = 3000으로 총 4500번의 계산이 필요하다.<br/>

※ 이처럼 **동일한 계산**임에도 **행렬 곱셈 횟수**에서 차이가 나므로, 효율적인 연속 행렬 곱셈 방법을 찾기 위해 연속 행렬 곱셈 알고리즘을 사용한다. 

___
## 연속 행렬 곱셈(JAVA)

▲ Code<br/>

## 1) 재귀적인 방법

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	static int[][] matrix;
	static int[][] dp;
	static int N;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		matrix = new int[N][2];
		dp = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			matrix[i][0] = sc.nextInt();
			matrix[i][1] = sc.nextInt();
		}
		
		find(0, N-1);
		System.out.println(dp[0][N-1]);
		
	}
	
	public static int find(int x, int y) {
		int ans = Integer.MAX_VALUE;
		if(x == y) {
			return 0; 	
		}
		if(dp[x][y]!=0) {
			return dp[x][y];
		}
		for(int i = x; i < y; i++) {
			ans = Math.min(ans, find(x,i)+find(i+1,y)+matrix[x][0]*matrix[i][1]*matrix[y][1]);
			dp[x][y]=ans;
		}
		return ans;
	}
}
```

## 2) 반복문 사용
### 연속 행렬 코드 (Java)
> ### ▼ 순차적 설명
#### Header File
- Scnner을 사용할 수 있지만, 계산 시간 절약을 위해 BufferReader를 사용함
- 예외 처리를 위해 IOEExpection 사용함

```java
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
```

#### Input
- 시간을 절약하면서 입력을 받기 위해 BufferReader을 사용함
- 입력받은 문자열(String)을 정수(int)형으로 바꾸기 위해 Integer.parseInt() 사용함
```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
```

#### Matrix 만들기
- 새로운 정수형 행렬 선언
- 한 줄에 두 개 이상의 여러 값을 입력받아 사용하기 위해 *br.readLine().split(" ");* 사용
- 선언한 행렬에 정수형 숫자 대입

```java
matrix = new int[n + 1][2];
        for (int i = 1; i <= n; i++) {
            String[] s = br.readLine().split(" ");
            matrix[i][0] = Integer.parseInt(s[0]);
            matrix[i][1] = Integer.parseInt(s[1]);
        }
```

#### 동적 계획 알고리즘 이용(DP, Dynamic Programming)
- 
```java
dp = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++)
            dp[i][1] = 0;
        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= i; j++) {
                int min = Integer.MAX_VALUE;
                for (int t = 1; t <= j-1; t++) {
                    int cur = cal(i - j + t, t, i, j - t);
                    if (cur < min)
                        min = cur;
                }
                dp[i][j] = min;
            }
        }
```

* #### 출력
- 위의 과정을 거쳐 최종적으로 구한 연속 행렬 곱셈 값을 출력
```java
System.out.println(dp[n][n
]);
```

<br/>

> ### ▼ 전체 코드
```java
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[][] matrix;
    static int[][] dp;

    public static int cal(int a, int b, int c, int d) {
        return matrix[a - b + 1][0] * matrix[a][1] * matrix[c][d] + dp[a][b] + dp[c][d];
    }

    public static void main(String[] args) throws IOException {
        // Input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 행렬 만들기
        matrix = new int[n + 1][2];
        for (int i = 1; i <= n; i++) {
            String[] s = br.readLine().split(" ");
            matrix[i][0] = Integer.parseInt(s[0]);
            matrix[i][1] = Integer.parseInt(s[1]);
        }

        // 동적 계획 알고리즘(Dynamic Programming) 이용!
        dp = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++)
            dp[i][1] = 0;
        for (int i = 2; i <= n; i++) {
            for (int j = 2; j <= i; j++) {
                int min = Integer.MAX_VALUE;
                for (int t = 1; t <= j-1; t++) {
                    int cur = cal(i - j + t, t, i, j - t);
                    if (cur < min)
                        min = cur;
                }
                dp[i][j] = min;
            }
        }
        // 최종 출력
        System.out.println(dp[n][n]);
    }
}
```