package Programmers.LevelOne.BanknoteFolding;

class Solution {
	
	public static int solution(int[] wallet, int[] bill) {
		
		int answer = 0;		// 지폐를 몇번 접어야 하는지.
		
		while(true) {

			if(wallet[0] >= bill[0] && wallet[1] >= bill[1]) 
				break;
			else {
				
				if(wallet[0] >= bill[1] && wallet[1] >= bill[0]) {

					break;
				} else { 
					
					if( bill[0] > bill[1] || bill[0] == bill[1]) {
						
						System.out.print("지폐의 현재 길이가 가로가 더 길거나 길이가 동등하니 가로 길이를 짜르기");
						bill[0] = bill[0] / 2;
						
					} else if( bill[0] < bill[1] ) {

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