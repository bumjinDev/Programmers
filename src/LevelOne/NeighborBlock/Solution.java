package LevelOne.NeighborBlock;

import java.util.Random;

public class Solution {

	public static void main(String[] args) {
		 	
//		String[][][] boards = {
//		        {{"blue", "red", "orange", "red"}, {"red", "red", "blue", "orange"}, {"blue", "orange", "red", "red"}, {"orange", "orange", "red", "blue"}},
//		        {{"yellow", "green", "blue"}, {"blue", "green", "yellow"}, {"yellow", "blue", "blue"}}
//		};
//		    
//		Random rand = new Random();
//
//		// 랜덤한 2차원 배열 선택
//		String[][] board = boards[rand.nextInt(boards.length)];
//
//		// 랜덤한 위치 h, w 생성 (0 ≤ h, w < board의 길이)
//		int h = rand.nextInt(board.length);
//		int w = rand.nextInt(board[0].length);
//
//		// 결과 출력
//		System.out.println("Selected board:");
//		    
//		for (int i = 0; i < board.length; i++) {
//		    System.out.print("[");
//		      
//		    for (int j = 0; j < board[i].length; j++) {
//		        
//		    	System.out.print("\"" + board[i][j] + "\"");
//		    
//		         if (j < board[i].length - 1) {
//		               
//		         	System.out.print(", ");
//		        }
//		    }
//		    System.out.println("]");
//	    }
		
//		/* == */    
		
		String[][] board = {{"yellow", "green", "blue"}, {"blue", "green", "yellow"}, {"yellow", "blue", "blue"}};
		int w = 1, h = 0;
		
		// 결과 출력
		System.out.println("Selected board:");
		    
		for (int i = 0; i < board.length; i++) {
		    System.out.print("[");
		      
		    for (int j = 0; j < board[i].length; j++) {
		        
		    	System.out.print("\"" + board[i][j] + "\"");
		    
		         if (j < board[i].length - 1) {
		               
		         	System.out.print(", ");
		        }
		    }
		    System.out.println("]");
		}
		/* == */
		
	    System.out.println("\nRandom position: (" + w + ", " + h + ")");
	    System.out.println("Random positon color : " + board[w][h] + "\n");
	    
	    int answer = run(board, w, h);
	    System.out.println("\n지정된 위치와 동일한 색깔의 개수 : " + answer);
	}
	
	/* [PCCE 기출문제] 9번 / 이웃한 칸 */
	
	static public int run(String[][] board, int w, int h) {
		
		int answer = 0;		// 총 개수 반환할 변수.
		
		int [] moveIndex = {-1, 1};			// 가로 범위 탐색 시 사용될 이동 인덱스 값 지정
		
		/* 탐색 과정 시작 */
		for(int i = 0; i < 2 ; i++) {
			
			/* 현재 보드 내 위치에서 가로 세로 각각 1칸 씩 "이전" 인덱스 위치에 대한 유효성 검사 및 동시에 같은 색깔인지에 대한 여부 판단 후 일치 시 answer 내 개수 1 증가 */
			if(moveIndex[i] == -1) {
				
				/* 가로 위치에 대한 로직 실행  */
				if(!((w + moveIndex[i]) < 0) && board[w + moveIndex[i]][h].equals(board[w][h])) {
					
					System.out.println("w + moveIndex[i] : " + (w + moveIndex[i]) + ", board[w + moveIndex[i]][h].equals(board[w][h]) : " + board[w + moveIndex[i]][h].equals(board[w][h]) +
							", board[w + moveIndex[i]][h] : " + board[w + moveIndex[i]][h] + ", board[w][h] : " + board[w][h]);
					
					answer = answer + 1;
				}
				
				/* 세로 위치에 대한 로직 실행 */
				if(!((h + moveIndex[i]) < 0) && board[w][h + moveIndex[i]].equals(board[w][h])) {
					
					System.out.println("moveIndex[0] : -1 실행");
					answer = answer + 1;
				}
			
			/* 현재 보드 내 위치에서 가로 세로 각각 1칸 씩 "+1" 인덱스 위치에 대한 유효성 검사 및 동시에 같은 색깔인지에 대한 여부 판단 후 일치 시 answer 내 개수 1 증가 */
			} else if(moveIndex[i] == 1) {
				
				/* 가로 위치에 대한 로직 실행 */
				if(!((w + moveIndex[i]) > board.length -1) && board[w + moveIndex[i]][h].equals(board[w][h])) {
					
					answer = answer + 1;
				}
				
				/* 세로 위치에 대한 로직 실행 */				
				if(!((h + moveIndex[i]) > board.length -1) && board[w][h + moveIndex[i]].equals(board[w][h])){
					
					answer = answer + 1;
				}
			}
		}
		return answer;
	}
}
