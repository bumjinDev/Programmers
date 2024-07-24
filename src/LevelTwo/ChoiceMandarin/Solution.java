package LevelTwo.ChoiceMandarin;

import java.util.HashMap;

/* 프로그래머스 코딩 테스트 문제 : 감귤 고르기, 주어진 배열 'tangerine' 내 포함된 귤들 크기에서 귤을 크기별로 분류했을 때 서로 다른 종류의 수를 최소화 하기 위한 알고리즘. */

class SolutionVO {
	
	public int size = 0; 
	public int count = 0;
	
	public SolutionVO(int size, int count) {
		this.size = size;
		this.count = count;
	}
}

public class Solution {
		
	 public static int solution(int k, int[] tangerine) {
		 
		 int answer = 0;
		 int count = 0;
		 
		 HashMap<Integer, Integer> hashTan = new HashMap<Integer, Integer>();	// 각 귤 크기 별 개수.
		 SolutionVO temp;
		 
		 /* 각 감귤 종류 별 개수를 카운트와 동시에 가장 큰 감귤 및 그에 대한 개수 파악 */
		 for(int i = 0; i< tangerine.length; i++) {
			 
			 if(hashTan.get(tangerine[i]) == null) 		// 기존 HashMap 내 키 값이 없다면 생성
				 hashTan.put(tangerine[i], 0);
			 
			 hashTan.put(tangerine[i], hashTan.get(tangerine[i]) + 1);	// HashMap 내 각 귤 크기를 키 값으로 하여 각 크기 별 개수 저장.
			 count += 1;
		 }
		 
		 SolutionVO [] lists = new SolutionVO[hashTan.size()];
		 
		 int i = 0;
		 /* 'hashTan' 내 각 데이터를 'SolutionVO [] lists'로 저장 */
		 for(int size : hashTan.keySet()) {
			 lists[i] = new SolutionVO(size, hashTan.get(size));	// 순서대로 귤 크기, 귤 크기 별 개수
			 i++;
		 }
		 

	
		 /* 'lists' 내 각 요소들에 대해서 개수가 가장 큰 순서대로 내림차순. */
		 for(int j = 0; j < lists.length - 1; j++) {
			    for(int j2 = 0 ; j2 < lists.length - 1 - j; j2++)
			        if(lists[j2].count > lists[j2+1].count){
			            temp = lists[j2+1];
			            lists[j2+1] = lists[j2];
			            lists[j2] = temp;
		        }
		}

		/* 내림차순으로 정렬된 배열 'lists' 에서 변수 'k' 횟수 만큼 앞에서 부터 해당 인덱스 제외하고 모든 귤의 개수 더하기 */
		 System.out.println("디버깅 - 정렬된 배열 확인.");
		 for(int j = 0; j < lists.length; j++)
			 System.out.println("lists : " + lists[j].size + ", " + lists[j].count);
		 System.out.println();
		 
		 int c = 0;
		 for(; count >= k; c++)
			 count -= lists[c].count;
		 
		 c -= 1;
		 System.out.println("남겨야 될 개수 - count : " + count + ", 현재 카운트 : " + c);
		 
		 for( ; c < lists.length; c++)
			 answer += 1;
		 
		return answer;
	 }
	 
	public static void main(String[] args) {

		 	int [] tangerine = {1, 3, 2, 5, 4, 5, 2, 3};

	        // k 값을 5에서 10 사이의 랜덤한 값으로 설정
	        int k = 6;

	        int answer = Solution.solution(k, tangerine);
	        System.out.println("결과 : " + answer);
	    }
}
