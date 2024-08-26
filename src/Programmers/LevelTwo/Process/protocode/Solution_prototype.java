package Programmers.LevelTwo.Process.protocode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
/* 프로그래머스 - level 2 : 스택/큐 > 프로세스 */
import java.util.Set;

/*
 * 풀이 로직:
 * 1. 'priorities' 배열 내 각 번호별 리스트를 저장합니다. (HashMap으로 관리)
 * 2. 'priorities'에서 프로세스를 꺼낼 때, 우선순위가 더 높은 프로세스가 있는지 확인합니다.
 *    2.1. 우선순위가 더 높은 프로세스가 있다면, 해당 프로세스를 다시 큐의 뒤로 보냅니다.
 *    2.2. 우선순위가 더 높은 프로세스가 없다면, 해당 프로세스를 실행하고 리스트에서 제거합니다.
 * 3. 이 모든 과정은 'priorities' 배열을 연결리스트로 재구성하여 수행합니다.
 *    3.1. 배열을 사용하면 데이터를 뒤로 다시 삽입할 때 원형 큐 로직을 구현해야 하므로, 연결리스트를 사용하여 구현합니다.
 */

/* 양 방향 연결 리스트로 구현 : 양 방향으로 구현하는 이유는 front 포인터에서 제거 시 이전 노드 위치를 알아낼 때 단방향이면 near 부터 끝까지 전부 순회해야 되기 때문  */

class Node {
	
//	int sequences;		// 각 우선 순위 별로 생성되는 큐 내 각 노드가 들어온 순번.
	boolean processTarget;

	Node prev;	// 큐의 뒤쪽..데이터 입력 부분.
	Node next;	// 큐의 앞쪽..데이터 출력 부분
	
	public Node(boolean processTarget){
		
		this.processTarget = processTarget;
		
		prev = null;
		next = null;
	}
}

/* Node 를 가지고 queue 자료구조를 구현하는 클래스, 내부적으로 prev와 next 를 가진다. */
class queueNode {
	
	
	Node near;	// 노드 뒤쪽
	Node front;	// 노드 앞쪽
	
	int priority;	// 해당 큐의 우선순위를 표현한 것.
	
	Integer sequence;
	
	public queueNode(int priority) {
		
		near = null;
		front = null;
		
		this.priority = priority;
		this.sequence = new Integer(0);
	}
	
	/* =========== 해당 Queue 내에서 실제로 사용될 메소드들 ================ */
	
	public void enqueue(Node node) {
		
		System.out.println("enqueue()!\n");
		
		/* 최초 node 생성 되는 node */
		if(near == null ) {
			
			near = node;
			front = node;
		}
		
		else {
			node.next = near;	// 새롭게 생성한 노드의 next 링크를 현재 near 에 링크
			near.prev = node;	// 현재 near 노드의 뒷 링크를 새로운 node 에 연결.
			near = node;		// 새롭게 queue 내 생성된 node 를 near 노드로 적용.
		}
		System.out.println();
	}
	
	/* dequeue() : 단 방향 연결리스트에서 front 포인터의 노드 삭제. */
	public Node dequeue() {
		
		System.out.println("dequeue()!");
		
		Node node = front;
		
		/* 실제 반환할 Node 가 없을 시 null 반환. */
		if(front == null)
			return null;
		
		/* 만약 현재 노드가 1 개 이므로 near 과 front 가 같은 노드를 바라 볼 경우 */
		if(near == front) {
			
			near = null;
			front = null;
			
			return node;
		}
		
		else {
			
			front = front.prev;
			front.next.prev = null;
			front.next = null;
			
			return node;
		}
	}
	
	public void showqueue() {
	
		System.out.println("showqueue() - properties : " + this.priority + " !\n");
		
		Node node = near;
		
		while(node != null) {
			
			System.out.print(node.processTarget + " ");
			node = node.next;
		}
		
		System.out.println("\n\n");
	}
	
	/* 현재 Queue 내 모든 프로세스 순차적으로 꺼내어 실행하면서 'sequence' 갱신한다. */	
	public boolean processExecution() {
		
		System.out.println("processCheck() 실행!\n");
		
		while(true) {
			
			Node node = this.dequeue();
			
			/* Node 가 더 이상 없는 경우 종료한다. */
			if(node == null) {
				System.out.println("현재 큐에 대해서 더 이상 노드가 없으므로 종료.");
				return false;	// 종료될 때 까지 'Target' 에 해당하는 Node 가 안 나왓다는 건 해당 우선 순위 Queue 에서는 찾지 못 했다는 의미므로 'false' 반환.
			}
				
			/* 노드가 확인되는 경우 'sequence' 증가 및 해당 노드가 'Target' 여부 인지 확인 따라 반환 값 결정 */
			this.sequence += 1;
			
			if(node.processTarget == true)		// 'Target' 이 true 인 경우 반환으로 true 을 하며 아닌 경우 해당 반복문 내 코드 반복하면서 Queue 내 모든 노드를 추출하거나 혹은 추가로 발견할 때 까지 반복한다.
				return true;
			
			System.out.println("현재 node.processTarget : " + node.processTarget + ", sequence : " + sequence);
		}
	}	
}

public class Solution_prototype {
	
	public static int solution(int[] priorities, int location) {
        
		Integer sequence = 0;		// 모든 우선 순위 Queue 내 실행 횟수를 공통적으로 저장 해야 되니 int 형 아닌 Integer 클래스 타입 사용.
		HashMap<Integer, queueNode> prioritesQueue = new HashMap<Integer, queueNode>();
		
		/* 초기 작업 :
		 * 
		 * 1. HashMap 컬렉션 사용해서 priorities 내 각 프로세스 우선 순위 별 큐를 별도로 두어서 각 우선 순위 별 프로세스 들어 온 순서 별로 구현을 한다.
		 * 2. HashMap 숫자 리스트를 관리하기 위해 각 배열 내 접근할 때에 동시에 연결리스트로서 생성한다. */
		
		for(int i = priorities.length - 1; i >= 0 ; i --) {
			
			System.out.println("디버깅 - i : " + i + ", priorities[" + i + "] : " + priorities[i] + "\n");
			
			if(prioritesQueue.get(priorities[i]) == null) {	// 연결 리스트로써 관리될 현재 프로세스의 우선순위에 해당하는 Queue 가 없으면 생성
				
				System.out.println("디버깅 - 새로운 Queue 생성, priorites[" + i + "] : " + priorities[i] + "\n");
				prioritesQueue.put(priorities[i], new queueNode(priorities[i]));
			}
			
			// 이미 Queue 가 존재 하거나 새롭게 생성 시 Node 삽입.
			System.out.println("디버깅 - Queue 내 Node 삽입, priorites[" + i + "] : " + priorities[i]);
			
			Node node = null;
			
			/* 각 프로레스 별 우선순위 대로 큐를 별도로 만들어서 삽입 작업을 수행할 때 해당 Node 가 지정된 Node 인지 구별하기 위함.  */
			if(location ==  i) {		// 지정된 노드라면 매개변수 'Node.processTarget' 을 식별자로써 'true' 설정.
				
				System.out.println("디버깅 - location : " + location + ", i : " + i);
				node = new Node(true);
				
			} else {					// 지정된 노드가 아니라면 매개변수 'Node.processTarget' 을 식별자로써 'false' 설정.
				node = new Node(false);
			}
			
			prioritesQueue.get(priorities[i]).enqueue(node);
		}
		/* 각 queue 내 우선 순위 queue 따라 데이터 출력 및 현재 데이터 잘 들어갔나 확인하기 위한 keySet 재 구성 */
		Set<Integer> keySet = new HashSet<>();
		int [] keyList = null;
	
		for(int i = 0; i < priorities.length - 1 ; i++)		// HashMap 컬렉션 사용하여 중복 값 제거.
			keySet.add(priorities[i]);
	
		keyList = new int[keySet.size()];
		
		int z = 0;
		for(int keys : keySet) {
			
			keyList[z] = keys;
			z++;
		}
		
		for(int i = 0; i < keyList.length - 1 ; i++)
			for(int j = i + 1; j < keyList.length; j++)
				if(keyList[i] < keyList[j]) {
					
					int temp = keyList[i];
					keyList[i] = keyList[j];
					keyList[j] = temp;
				}
		
//		System.out.print("디버깅 - keyList : ");
//		for(int i = 0; i< keyList.length; i++)
//			System.out.print(keyList[i] + " ");
//		System.out.println();
		
		/* 각 우선 순위 큐 별로 높은 우선순위를 가진 큐 부터 내부에 올바르게 연결 리스트로써 Queue 구성 되었는지 확인. */
		System.out.println("각 Queue 우선 순위 별 실제로 저장된 데이터 확인.");
		for(int i = 0; i < keyList.length; i++)
			prioritesQueue.get(keyList[i]).showqueue();
				
		/* 실제 로직 : 각 우선 순위 높은 순서대로 Queue 를 호출하여 내부적으로 Queue 내 실제 프로세스 시작 및 시작한 순서 번호인 'sequence'를 적용하여 'Targer' 노드가 실행되는 순번을 저장. 시작. */
		System.out.println("\n======= 실제 로직 시작 =====\n");
		
		for(int prioritie : keyList) {
			
			System.out.println("반복 시작 지점.., prioritie : "  +  prioritie + " \n");
			
			if(prioritesQueue.get(prioritie).processExecution() == true) {	// 우선 순위 높은 Queue 순서대로 실행하면서 target 읆 만나면 즉시 종료하여 최종적으로 target이 실행되기 까지 횟수를 반환.
				
				sequence += prioritesQueue.get(prioritie).sequence;
				break;
			}
			
			else
				sequence += prioritesQueue.get(prioritie).sequence;
		}
		
		return sequence;
    }

	
	public static void main(String[] args) {
		
		// 1번째 예제
//		int [] priorities = {2, 1, 3, 2};
//		int location = 2;
		
		// 2번째 예제
		int [] priorities = {1, 1, 9, 1, 1, 1};
		int location = 0;
		
		System.out.print("지정된 Priorites : ");
		for(int i = 0; i < priorities.length; i++)
			System.out.print(priorities[i] + " ");
		System.out.println("\n지정된 location : " + location);
		System.out.println();
		
		System.out.println("==== 실제 로직 시작 =====");
		System.out.println("==================== \n\n");
		System.out.println("결과 : " + solution(priorities, location));
	}
}
