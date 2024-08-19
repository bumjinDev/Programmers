package Programmers.LevelTwo.KakaoDeveloperWinterInternship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* Level 2 : 2019 카카오 개발자 겨울 인턴십 */
public class Solution {

	public int[] solution(String s) {
		
		/* 데이터 파싱 과정 */
		s = s.replaceAll("[{}]", " ");
	        
		String[] parts = s.trim().split(" , ");
	    
		String[][] arrays = new String[parts.length][];
        for (int i = 0; i < parts.length; i++) {
            arrays[i] = parts[i].split(",");
        }

        // 디버깅을 위해 각 배열을 출력
        for (String[] arr : arrays) {
            System.out.println(Arrays.toString(arr));
        }
  
		
        /* 데이터 실제 풀이 과정 */
        String [] array = {};
        
        int j = 0;
        
        for(int i = 0; i < arrays.length; i++)
        	if( array.length < arrays[i].length ) {
        		array = arrays[i];
        		j = i;
        	}
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(array));
        
        for(int i = 0; i < arrays.length && i != j ; i++) {
        	for(String arr : arrays[i])
        		for(String item)
        }
        
        return new int[3];
	}
	
	public static void main(String[] args) {
		
		Solution sol = new Solution();
        String s = "{{2},{2,1},{2,1,3},{2,1,3,4}}";
        sol.solution(s);

	}
}