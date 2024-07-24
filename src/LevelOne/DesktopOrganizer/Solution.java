package LevelOne.DesktopOrganizer;

import java.util.ArrayList;

/* 코테 연습, Level 1 : 바탕화면 정리 */

class FileVO {
	
	public int iIndex;
	public int jIndex;
	
	public FileVO(int iIndex, int jIndex) {
		
		this.iIndex = iIndex;
		this.jIndex = jIndex;
	}
}

public class Solution {

	/* 풀이 로직 : 
	 * 1. 각 열 번호 순서대로 순회하면서 각 열 내 행들을 일일이 찾는다.
	 * 2. 찾은 첫번째 행 번호를 저장하고 및 탐색 진행.
	 * 3. 끝 자리 파일은 행과 열 번호 모두 큰 것으로 인덱스 저장.
	 * 4. 위 인덱스를 반환. */
	public static int[] run(String [] wallpaper) {
		
		int [] answer = new int[4];
		
		/* 바탕화면 배열 "wallpaper" 을 사용하기 위한 전처리 작업. */
		int vertical = wallpaper.length;	// 'wallpaper' 의 행 길이 지정
		int horizontal = wallpaper[0].length() ; // 'wallpaper' 의 열 길이 지정
		
		String [][] wallpaperArray = new String[vertical][horizontal];
		
		for(int i = 0; i < wallpaper.length; i++)
			wallpaperArray[i] = wallpaper[i].split("");
				
		/* 배열 "wallpaper" 를 처음부터 끝 위치까지 전부 찾아가며 첫번째 파일 인덱스와 마지막 파일 인덱스를 전부 찾는다,
		 * 또한 첫번째 드래그 위치 기준은 "첫번째 파일"이 위치한 열 번호에 따른 가장 0번째에 가까운 파일 위치를 행번호로 지정하고,
		 * 마지막 드래그 위치는 마지막 인덱스를 기준으로 한다. */
		
		ArrayList<FileVO> fileVo = new ArrayList<FileVO>();		/* 모든 내용을 저장하는 ArrayList 컬렉션. */
		
		System.out.println("디버깅 - wallpaperArray[0].length : " + wallpaperArray[0].length +
			", wallpaperArray[i].length : " + wallpaperArray.length);
		System.out.println("== 'wallpaperArray' 출력 ==");
		
		for(int i = 0; i < wallpaperArray.length; i++) {
			for(int j = 0 ; j < wallpaperArray[i].length; j++)
				System.out.print(wallpaperArray[i][j] + " ");
			System.out.println();
		}
		
		int stIndex[] = {			// 순서대로 마우스 드래그 포인터의 시작 지점에 대한 x와 y 값
				
			wallpaperArray.length,
			wallpaperArray[0].length
		};
		
		int enIndex[] = {			// 순서대로 마우스 드래그 포인터의 끝 지점에 대한 x와 y 값
				
				0,
				0
			};
		
		/* 로직 - 모든 #을 저장한다. */
		for(int j = 0; j < wallpaperArray[0].length; j++) {
			for(int i = 0; i < wallpaperArray.length; i++) {
				
				if(wallpaperArray[i][j].equals("#")) {
					
					fileVo.add(new FileVO(i, j));
				}
			}
		}
		
		/* 마우스 포인터 시작 지점 및 끝 인덱스를 지정, */
		for(int i = 0; i < fileVo.size(); i++) {
			
			/* 마우스 포인터 시작 지점 */
			if(stIndex[0] > fileVo.get(i).iIndex) {
				stIndex[0] = fileVo.get(i).iIndex;
			}
			if(stIndex[1] > fileVo.get(i).jIndex)
				stIndex[1] = fileVo.get(i).jIndex;
		
			/* 마우스 포인터 끝 지점 */
			if(enIndex[0] < fileVo.get(i).iIndex)
				enIndex[0] = fileVo.get(i).iIndex;
			if(enIndex[1] < fileVo.get(i).jIndex)
				enIndex[1] = fileVo.get(i).jIndex;	
		}
		
		System.out.println();
		System.out.println("== 결과 출력 ==");
		System.out.println("마우스 포인터 시작 지점 : " + stIndex[0] + " " + stIndex[1]);
		System.out.println("마우스 포인터 끝 지점 : " + enIndex[0] + " " + enIndex[1]);
		
		answer[0] = stIndex[0];
		answer[1] = stIndex[1];
		answer[2] = enIndex[0] + 1 ;
		answer[3] = enIndex[1] + 1 ;
		
		for(int an : answer)
			System.out.print(an + " ");
		
		return answer;
	}
	
	public static void main(String[] args) {
		
//		String [] wallpaper = {
//			".#...",
//			"..#..",
//			"...#."
//		};
//		
//		String [] wallpaper = {
//			"..........",
//			".....#....",
//			"......##..",
//			"...##.....",
//			"....#....."
//		};
//		
		String [] wallpaper = {
			".##...##.",
			"#..#.#..#",
			"#...#...#",
			".#.....#.",
			"..#...#..",
			"...#.#...",
			"....#...."
		};
//		
//		String [] wallpaper = {
//				"..",
//				"#."
//		};
		
		int answer [] = run(wallpaper);
	}
}