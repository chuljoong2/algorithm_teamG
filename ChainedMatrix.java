package PackageNo2;

public class ChainedMatrix {
	final static int INF = Integer.MAX_VALUE / 2; // INF값

	public static void main(String[] args) {
		int[][] Mult = {
				{0, }
		};
		
		int operation = 0;
		
		int n = Mult.length;
		
		for (int i = 0 ; i < n ; i++)	// 대각행렬 0으로 초기화
		{
			Mult[i][i] = 0;
		}
		
		for(int L = 0; L < n - 1 ; L++)
			for(int i = 0 ; i < n - L ; i++)
			{
				int j = i + L;
				Mult[i][j] = INF;
				for(int k = i ; k < j-1 ; k++)
				{
					int temp = 0; // 연산값
					if(temp < Mult[i][j]) //연산값이 행렬에 있는 값보다 작으면 행렬 업데이트
						Mult[i][j] = temp;
				}
			}
	}

}
