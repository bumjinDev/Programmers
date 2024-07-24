package LevelOne.NewIdRecommendation;

import java.util.ArrayList;
import java.util.Arrays;

public class Solution {

	 public static String solution(String new_id) {
	     
		 String answer = "";
		 
		 /* 배열 "new_id"을 ArrayList 로 전처리. */
		 String string = "apple,banana,kiwi,strawberry,grape";
		 String[] array = string.split("");
		
		 ArrayList<String> list = new ArrayList<String>(Arrays.asList(array));
		 
		 
		 
		 return answer;
	   }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String new_id = "...!@BaT#*..y.abcdefghijklm";
		String new_id2 = "z-+.^.";
		String new_id3 = "=.=";
		String new_id4 = "123_.def";
		String new_id5 = "defghijklmn.p";
		
		String answer = solution(new_id);
	}

}
