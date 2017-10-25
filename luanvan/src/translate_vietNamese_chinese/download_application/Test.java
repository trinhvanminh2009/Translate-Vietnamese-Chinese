package translate_vietNamese_chinese.download_application;

import java.io.IOException;

public class Test {


	private static void ToHopKe(int a[], int n, int k) {
		int i, j, tmp = 0;
		for (i = 1; i <= k; i++)
			if (a[i] != n - k + i) {
				tmp = 1;
				break;
			}
		if (tmp == 0)
			return;
		i = k;
		while (a[i] >= n - k + i)
			i--;
		a[i] = a[i] + 1;
		for (j = i + 1; j <= k; j++)
			a[j] = a[i] + j - i;
		for (i = 1; i <= n; i++)
			System.out.println(a[i]);
	}

	private static int lt(int n) {
		if (n == 0) {
			return 1;
		} else
			return (2 * lt(n - 1));
	}

	public static void main(String[] args) throws IOException {
		//System.out.println(lt(4));
		int []a = new int [3];
		a[0] = 1;
		a[1] = 1;
		a[2] = 3;

		ToHopKe(a,5,1);
		// TODO Auto-generated method stub
	}

}
