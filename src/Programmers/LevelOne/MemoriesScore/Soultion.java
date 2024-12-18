package Programmers.LevelOne.MemoriesScore;

import java.util.HashMap;
import java.util.Map;

class Solution {
    public static int[]  solution(String[] name, int[] yearning, String[][] photo) {
       
    	int[] answer = new int[photo.length];
        Map<String, Integer> nameYearning = new HashMap<>();
    	
        
        /* 각 사람 이름 별 점수 초기화, 만약 점수가 없는 사람이라면 0으로 초기화 */
    	int i = 0;
    	
    	System.out.println("nameYearning 초기화!");
        for(; i < yearning.length; i ++) {	
        	if(i >= yearning.length) {
        		
        		nameYearning.put(name[i], 0);
        		System.out.println("사람 이름 : " + name[i] + ", 점수 : " + yearning[i]);
        	} else {
        		
        		nameYearning.put(name[i], yearning[i]);
        		System.out.println("사람 이름 : " + name[i] + ", 점수 : " + yearning[i]);
        	}
    	}
        System.out.println("");
        
        int answerIndex = 0;
        
        /* String[][] photo 별 포함된 사람들의 총 그리움 점수 합산. */
        for(String[] photos : photo) {
        	
        	System.out.println("\nanswerIndex : " + answerIndex + "\n");
        	
        	int resultValue = 0;
        	for(int index = 0; index < photos.length; index ++) {
        		
        		System.out.println("photos[index] : " + photos[index]);
        		System.out.println("nameYearning.get(photos[index])_value : " + nameYearning.get(photos[index]));
        		
        		if(nameYearning.get(photos[index]) == null)
        			resultValue += 0;
        		else
        			resultValue += nameYearning.get(photos[index]);
        		
        		answer[answerIndex] = resultValue;
        	}
        	System.out.println("answer.get(" + answerIndex +") : " + answer[answerIndex] + "\n");;
        	answerIndex += 1;
        }
        return answer;
    }
    
    public static void main(String[] args) {
    	System.out.println("추억 점수 시작");
    	
		/* 첫번재 예시 */
    	String [] name = {
    			"may", "kein", "kain", "radi" };
    	int [] yearning = {
    			5, 10, 1, 3 };
    	String [][] photo = {
    			{"may", "kein", "kain", "radi"},
    			{"may", "kein", "brin", "deny"},
    			{"kon", "kain", "may", "coni"}};
    	
    	/* 두번째 예시 */
//    	String [] name = {
//    			"kali", "mari", "don"};
//    	int [] yearning = {
//    			11, 1, 55 };
//    	String [][] photo = {
//    			{"kali", "mari", "don"},
//    			{"pony", "tom", "teddy"},
//    			{"con", "mona", "don"}};
    	
    	/* 3번째 예시 */
//    	String [] name = {
//    			"may", "kein", "kain", "radi"};
//    	int [] yearning = {
//    			5, 10, 1, 3 };
//    	String [][] photo = {
//    			{"may"},
//    			{"kein", "deny", "may"},
//    			{"kon", "coni"}};
    	
    	solution(name, yearning, photo);
	}
}