> # 연속 행렬 곱셈(Chained Matrix Multipilications)

* **연속 행렬 곱셈**
- 연속된 행렬들의 곱셈에 필요한 원소 간 최소 곱셈 횟수를 찾는 문제
- 계산할 때 기본 행렬 곱셈과 같은 방식으로 계산함
- 교환 법칙이 성립하지 않음
<br/>

> **연속 행렬 곱셈**의 *예시*
- 10 x 20 행렬 A, 20 x 5 행렬 B, 5 x 15 행렬 C가 있다고 해보자. 
- *A x B x C = (A x B) x C = A x (B x C)* 와 같은 식이 성립하는데, 이를 통해 **결합 법칙**이 성립함을 알 수 있다.
- **A[10 x 20] x B[20 x 5] = C[10 x 5]** <br/>
- **(A x B)[10 x 5] x C[5 x 15] = ABC[10 x 15]** ⋅⋅⋅ ①
- **A[10 x 20] x (B x C)[20 x 15] = ABC[10 x 15]** ⋅⋅⋅ ②

<br/>

### ▼ 무슨 **차이**가 있을까?
- ① (A x B) x C
  - A x B를 먼저 계산하는데 10 x 20 x 5 = 1000 & (A x B) x C를 계산하는데 10 x 5 x 15 = 750으로 총 1750번의 계산이 필요하다.
- ② A x (B x C)
  - B x C를 먼저 계산하는데 20 x 5 x 15 = 1500 & A x (B x C)를 계산하는데 10 x 20 x 15 = 3000으로 총 4500번의 계산이 필요하다.<br/>

※ 이처럼 **동일한 계산**임에도 **행렬 곱셈 횟수**에서 차이가 나므로, 효율적인 연속 행렬 곱셈 방법을 찾기 위해 연속 행렬 곱셈 알고리즘을 사용한다. 

___
### 연속 행렬 곱셈 출력 ---  코드 및 행렬 연속 곱셈 개녕 추가 필요!

▲ Code<br/>



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
		int ans= 2147483647;
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