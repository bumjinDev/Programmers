package Programmers.LevelOne.BanknoteFolding;

class Solution {
	
	public static int solution(int[] wallet, int[] bill) {
		
		int answer = 0;		// 지폐를 몇번 접어야 하는지.
		
		while(true) {
//			
//			System.out.println();
//			System.out.println("wallet : " + wallet[0] + " " + wallet[1]);
//			System.out.println("bill : " + bill[0] + " " + bill[1]);
//			
//			System.out.println("answer : " + answer);
			
			/* 지폐를 그대로 지갑에 넣을 수 있다면 알고리즘 종료, 만약에 안될 시(else) 90도 돌려서 확인해 보거나 긴 쪽을 접는 로직 실행! */
			
			if(wallet[0] >= bill[0] && wallet[1] >= bill[1])	// 가로와 세로 중 하나라도 안 된다면 
				break;
			else {
				
				/* 현재 시점에서 가로와 세로 크기를 확인하여 90도 돌렸을 때 가능한지 확인 후 가능하다면 90도 돌려서 넣기.
				 * 	만약 돌려서 안된다면 무조건 문제 요구에 따라 긴쪽 접기. */
				
				// 90도 돌렸다는 가정하여 비교
				if(wallet[0] >= bill[1] && wallet[1] >= bill[0]) {
//					System.out.println("접기 로직 종료!");
					break; // 가능하므로 접는 횟수 종료
				} else { // 안되므로 긴쪽을 접거나 길이가 동일하면 그냥 현재 가로 길이 접기
					
//					System.out.println("돌리거나 접자");
//					System.out.println("가로 길이 : " + bill[0] + ", 세로 길이 : " + bill[1]);
					
					if( bill[0] > bill[1] || bill[0] == bill[1]) {
						
						System.out.print("지폐의 현재 길이가 가로가 더 길거나 길이가 동등하니 가로 길이를 짜르기");
						bill[0] = bill[0] / 2;
						
					} else if( bill[0] < bill[1] ) {
						
//						System.out.print("지폐의 현재 길이가 세로 길이가 기므로 세로 길이 짜르기");
						bill[1] = bill[1] / 2;
					}
					answer += 1;
				}
			}
		}
		
        return answer;
    }
	
    public static void main(String[] args) {
    	
//    	int wallet [] = { 30, 15 };
//    	int bill [] = { 26, 17};
    	
    	int wallet [] = { 50, 50 };
    	int bill [] = { 100, 241};
    	
    	int count = solution(wallet, bill);
    	
    	System.out.println("접은 횟수 : " + count);
	}
}