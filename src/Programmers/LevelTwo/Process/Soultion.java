package Programmers.LevelTwo.Process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/* 프로그래머스 - level 2 : 스택/큐 > 프로세스 */

/*
 * 풀이 로직:
 * 1. 'priorities' 배열 내 각 번호별 리스트를 저장합니다. (HashMap으로 관리)
 * 2. 'priorities'에서 프로세스를 꺼낼 때, 우선순위가 더 높은 프로세스가 있는지 확인합니다.
 *    2.1. 우선순위가 더 높은 프로세스가 있다면, 해당 프로세스를 다시 큐의 뒤로 보냅니다.
 *    2.2. 우선순위가 더 높은 프로세스가 없다면, 해당 프로세스를 실행하고 리스트에서 제거합니다.
 * 3. 이 모든 과정은 'priorities' 배열을 연결리스트로 재구성하여 수행합니다.
 *    3.1. 배열을 사용하면 데이터를 뒤로 다시 삽입할 때 원형 큐 로직을 구현해야 하므로, 연결리스트를 사용하여 구현합니다.
 */

/* 단방향 연결 리스트로 구현..이유는 near 와 front 포인터를 사용해서 큐로써 구현해야 되는데
 * 우선순위 스케줄링에 맞지 않는 항목을 다시 near 에 넣는다고 해서 fornt의 입장에서 원형 큐로 포인팅을 near에 
 * 해버리면 이건 큐 자료구조에 어긋나기 때문...  */
class Node {
	
	int data;
	Node link;
	
	public Node(int data, Node link){
		
		this.data = data;
		this.link = link;
	}
}

public class Soultion {

	
	
	public static int solution(int[] priorities, int location) {
        
		HashMap<Integer, ArrayList<Integer>> processList = new HashMap<Integer, ArrayList<Integer>>(); 
		
		Node near, front; 	// 각각 큐 자료구조에서 데이터 입구 포인터, 데이터 출구 포인터
		
		
		/* 초기 작업 :
		 * 
		 * 1. HashMap 컬렉션 사용해서 priorities 내 각 숫자들의 리스트를 관리한다.
		 * 2. HashMap 숫자 리스트를 관리하기 위해 각 배열 내 접근할 때에 동시에 연결리스트로서 생성한다. */
		
		for(int i = 0; i < priorities.length; i++) {
			
			/* HashMap 내 리스트 생성 혹은 추가 */
			if(processList.get(priorities[i]) == null)
				processList.put(priorities[i], new ArrayList<Integer>());
			
			else
				processList.get(priorities[i]).add(priorities[i]);
			
			/* 단방향 연결리스트 구현 */
			if(near == null)
				
			
		}
		
		
        return 0;
    }
	
	public static void main(String[] args) {
		
		// 1번째 예제
		int [] priorities = { 2,1,3,2};
		int location = 2;
		
		System.out.println(solution(priorities, location));
	}
}
