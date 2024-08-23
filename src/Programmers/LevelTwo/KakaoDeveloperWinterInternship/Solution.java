package Programmers.LevelTwo.KakaoDeveloperWinterInternship;

import java.util.ArrayList;
import java.util.Arrays;


/* Level 2 : 2019 카카오 개발자 겨울 인턴십 */
public class Solution {

	public int[] solution(String s) {
		
		// 데이터 파싱 과정
        s = s.replaceAll("[{}]", " ");
        String[] parts = s.trim().split(" , ");
        int[][] intArrays = new int[parts.length][];
        for (int i = 0; i < parts.length; i++) {
            String[] stringArray = parts[i].split(",");
            intArrays[i] = new int[stringArray.length];
            for (int j = 0; j < stringArray.length; j++) {
                intArrays[i][j] = Integer.parseInt(stringArray[j].trim());
            }
        }
  
        /* 위 2차원 배열을 작은 수 부의 배열 부터 정렬 : 버블정렬 */
        for(int i = 0; i < intArrays.length - 1; i++)
        	for(int j = i + 1; j < intArrays.length; j++)
        		if(intArrays[i].length > intArrays[j].length) {
        			
        			int [] temp = intArrays[j];
        			intArrays[j] = intArrays[i];
        			intArrays[i] = temp;
        		}
        
      
        /* 시작 지점 컬렉션 생성 */
        ArrayList<Integer> resArray = new ArrayList<>();

        /* 문제 풀이! */
        for(int [] arry : intArrays) {
        	for(int arryItem : arry) {
        		
        		int flag = 1;
        		int item = arryItem;
        		
        		for(int intArray : resArray) {
        			
        			if(intArray == arryItem)
        				flag = 0;
        		}
        		
        		if(flag == 1) {
        			resArray.add(item);
        		}
        	}
    		System.out.println();
        }
        
        int[] array = new int[resArray.size()];
        for (int i = 0; i < resArray.size(); i++) {
            array[i] = resArray.get(i);
        }

        return array;
	}
	
	public static void main(String[] args) {
		
		Solution sol = new Solution();
		
        String s = "{{2},{2,1},{2,1,3},{2,1,3,4}}";
        
        /* 2차원 숫자 배열로 변환 */
        sol.solution(s);
        
 
	}
}