package translate_vietNamese_chinese;

import java.util.ArrayList;
import java.util.Random;

public class Test {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int count = 0;
		for(int i = 0; i < 100; i++)
		{
			QuickMathematics quickMathematics = ModelQuickMathematics.getQuestionMathematics();
			System.out.println( "Question: "+quickMathematics.getNumberX() +
					 quickMathematics.getOperator() +
					quickMathematics.getNumberY() + "\n");
			System.out.println( "Result: "+quickMathematics.getResult() + "\n");
			for(int j = 0; j< quickMathematics.getListWrongResult().size(); j++)
			{
				System.out.println( quickMathematics.getListWrongResult().get(j));
			}
			System.out.println("-------------------------------");
			count ++;
		}
		
		System.out.println(count);
	}

}
