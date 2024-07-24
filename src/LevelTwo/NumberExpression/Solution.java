package LevelTwo.NumberExpression;

public class Solution {

	 public static int solution(int n) {
	  
	  int answer = 0;
	  int res = 0;
	  
	  for(int step = 1; step <= n; step++) {
		  System.out.println("");
		  for(int i = step; i <= n; i++) {
			  
			 res += i;
			 			 
			 if(res >= n) {
				 if(res == n)
					 answer += 1;
				 break;
			 }
		  }
		  res = 0;
	  }
	  
	  return answer;
	}
	 
	public static void main(String[] args) {
		
		int n = 15;
		
		int answer = solution(n);
		System.out.println("답 : " + answer);
	}

}
