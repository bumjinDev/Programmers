package LevelTwo.FibonacciNumbers;

import java.util.ArrayList;

public class Soultion {

	 public int solution(int n) {
	        
		int answer = 0; 
		ArrayList<Integer> pibo = new ArrayList<Integer>(); 
		
		pibo.add(1);
		pibo.add(1);
		
//		System.out.println("n : " + n);
		
		/* 피보나치 수열을 만들기 위한 전체 반복 횟수 : n - 2 */
		for(int i = 1; i != n-1 ; i++) {	
			
			pibo.add((pibo.get(i-1) + pibo.get(i)) % 1234567);
//			System.out.println(pibo.get(i+1));
		}
		
		answer = pibo.get(pibo.size()-1);
		
		return answer;
	 }
	
	public static void main(String[] args) {
		
		Soultion sol = new Soultion();
		
		int n1 = 3;
		int n2 = 5;
		
		System.out.println("answer : " + sol.solution(20));
	}
}