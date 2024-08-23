package Programmers.LevelTwo.lifeboat;

import java.util.Arrays;
import java.util.Random;

/* 프로그래머스 문제 2 : 구명보트 */

public class Solution {

	public static int solution(int[] people, int limit) {
        
		 // 사람 몸무게를 정렬
	    Arrays.sort(people);
	    
	    int i = 0;
	    int j = people.length - 1;
	    int boatCount = 0;
	    
	    while (i <= j) {
	        
	        if (people[i] + people[j] <= limit) {
	            i++; 
	        }
	        
	        j--;
	        boatCount++;
	    }
	    
	    return boatCount;
    }
	
	public static void main(String[] args) {
		
		Random random = new Random();
        int[] weights = new int[5];

        for (int i = 0; i < weights.length; i++) {
            weights[i] = random.nextInt(201) + 40; // 40에서 240 사이의 무게
        }
        
        int lifeboatWeight = random.nextInt(201) + 40; // 40에서 240 사이의 무게//        
        
        System.out.print("사람들 무게 : ");
        for(int weigth : weights)
        	System.out.print(weigth + " ");
        System.out.println();
        System.out.println("구명 보트 무게: " + lifeboatWeight + "\n");
		
        int resCount = solution(weights, lifeboatWeight);
        
		// ----
		
//		int [] people = {70, 50, 80, 50};
//		int limit = 100;
//		
//		System.out.print("배열 출력 : ");
//		for(int i = 0; i < people.length; i++)
//			System.out.print(people[i] + " ");
//		System.out.println("\n");
      
//      int resCount = solution(people, limit);
		// ----
		
		

		System.out.println("resCount : " + resCount);
	}

}
