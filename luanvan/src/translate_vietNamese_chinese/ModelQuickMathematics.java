package translate_vietNamese_chinese;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by trinh on 8/31/2017.
 */

public class ModelQuickMathematics {

    /**There are three difficulty levels with different operators
     * randomUnknownPosition to know where is unknown number in that question
     * */
    public static List<QuickMathematics> randomQuickMathematics(int currentScore) {
        List<QuickMathematics> quickMathematicsList = new ArrayList<>();
        ArrayList<String> levelList = new ArrayList<>();
        ArrayList<String> operatorsEasyList = new ArrayList<>();
        ArrayList<String> operatorsNormalList = new ArrayList<>();
        ArrayList<String> operatorsHardList = new ArrayList<>();
        Random random = new Random();

        operatorsEasyList.add("+");
        operatorsEasyList.add("-");


        operatorsNormalList.add("+");
        operatorsNormalList.add("-");
        operatorsNormalList.add("*");

        operatorsHardList.add("+");
        operatorsHardList.add("-");
        operatorsHardList.add("*");
        operatorsHardList.add("/");

        levelList.add("easy");
        levelList.add("normal");
        levelList.add("hard");

        String currentOperator;
        int randomX;
        int randomY;
        int randomUnknownPosition;
        int result = 0;
        ArrayList<Integer> listWrongResult = new ArrayList<>(3);
        if (currentScore <= 10) {
                randomX = random.nextInt(10) + 1;
                randomY = random.nextInt(10) + 1;
                randomUnknownPosition = random.nextInt(3) + 1;
                currentOperator = operatorsEasyList.get(random.nextInt(operatorsEasyList.size()));
                switch (currentOperator) {
                    case "+":
                        result = randomX + randomY;
                        break;
                    case "-":
                        result = randomX - randomY;
                        break;
                }
                System.out.println("Current out loop result: " +result);
                for (int i = 0; i < 10; i++) {
                    int randomWrongEasyResult = random.nextInt(10) + 1;
                    if(listWrongResult.size() <3)
                    {
                    	   if (!listWrongResult.contains(randomWrongEasyResult)) {
                    		   if(randomWrongEasyResult != result)
                    		   {
                    			   System.out.println("Current in loop result: " +result);
                    			   listWrongResult.add(randomWrongEasyResult);
                    		   }
                              
                           }
                    }
                 
              
                }
                System.out.println("Current out loop result: " +result);
                QuickMathematics quickMathematics = new QuickMathematics(randomX, randomY,
                        currentOperator, result, listWrongResult, randomUnknownPosition);

                quickMathematicsList.add(quickMathematics);
                result = 0;
            
        }
        if (currentScore > 10 && currentScore <= 30) {
                randomX = random.nextInt(30) + 1;
                randomY = random.nextInt(30) + 1;
                randomUnknownPosition = random.nextInt(3) + 1;
                currentOperator = operatorsNormalList.get(random.nextInt(operatorsNormalList.size()));
                switch (currentOperator) {
                    case "+":
                        result = randomX + randomY;
                        break;
                    case "-":
                        result = randomX - randomY;
                        break;
                    case "*":
                        result = randomX * randomY;
                        break;
                }
                for (int i = 0; i < 10; i++) {
                    int randomWrongEasyResult = random.nextInt(50) + 1;
                    if (randomWrongEasyResult != result) {
                        listWrongResult.add(randomWrongEasyResult);
                    }
                    if (listWrongResult.size() == 3) {
                        break;
                    }

                }
                QuickMathematics quickMathematics = new QuickMathematics(randomX, randomY,
                        currentOperator, result, listWrongResult, randomUnknownPosition);

                quickMathematicsList.add(quickMathematics);
            }
  

        if (currentScore > 30) {
          
      
                    randomX = random.nextInt(99) + 1;
                    randomY = random.nextInt(99) + 1;
                    randomUnknownPosition = random.nextInt(3) + 1;
                    currentOperator = "/";
                 
                    switch (currentOperator) {
                        case "+":
                            result = randomX + randomY;
                            break;
                        case "-":
                            result = randomX - randomY;
                            break;
                        case "*":
                            result = randomX * randomY;
                            break;
                        case "/":
                        	double numberX = random.nextInt(99)+1;
                        	double numberY = random.nextInt(99)+1;
                        	while((numberX % numberY) != 0)
                        	{
                        		 numberX = random.nextInt(99)+1;
                            	 numberY = random.nextInt(99)+1;
                        	}
                        	randomX = (int)numberX;
                        	randomY = (int)numberY;
                        	result = (int)numberX / (int)numberY;
                        
                        	break;
                    
                        	
                    }
                    for (int i = 0; i < 10; i++) {
                        int randomWrongEasyResult = random.nextInt(99) + 1;
                        if (randomWrongEasyResult != result) {
                            listWrongResult.add(randomWrongEasyResult);
                        }
                        if (listWrongResult.size() == 3) {
                            break;
                        }

                    }
                    QuickMathematics quickMathematics = new QuickMathematics(randomX, randomY,
                            currentOperator, result, listWrongResult, randomUnknownPosition);
                    quickMathematicsList.add(quickMathematics);
                }
        
        return quickMathematicsList;
    }

    public static QuickMathematics getQuestionMathematics()
    {
        List<QuickMathematics> quickMathematicsList = ModelQuickMathematics.randomQuickMathematics(35);
        int index = new Random().nextInt(quickMathematicsList.size());
        return quickMathematicsList.get(index);
    }
}