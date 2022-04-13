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