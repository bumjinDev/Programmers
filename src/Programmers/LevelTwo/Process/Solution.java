package Programmers.LevelTwo.Process;

import java.util.ArrayList;
import java.util.HashMap;

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

/* 양 방향 연결 리스트로 구현 : 양 방향으로 구현하는 이유는 front 포인터에서 제거 시 이전 노드 위치를 알아낼 때 단방향이면 near 부터 끝까지 전부 순회해야 되기 때문  */

class Node {
	
//	int sequences;		// 각 우선 순위 별로 생성되는 큐 내 각 노드가 들어온 순번.
	boolean processTarget;
	int processPriority;
	
	Node prev;	// 큐의 뒤쪽..데이터 입력 부분.
	Node next;	// 큐의 앞쪽..데이터 출력 부분
	
	public Node(boolean processTarget, int processPriority){
		
		this.processTarget = processTarget;
		this.processPriority = processPriority;
		
		prev = null;
		next = null;
	}
}

/* Node 를 가지고 queue 자료구조를 구현하는 클래스, 내부적으로 prev와 next 를 가진다. */
class QueueNode {
	
	
	Node near;	// 노드 뒤쪽
	Node front;	// 노드 앞쪽
	
	Integer sequence;
	
	public QueueNode() {
		
		near = null;
		front = null;
	
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
	
		System.out.println("showqueue()! \n");
		
		Node node = near;
		
		while(node != null) {
			
			System.out.println("node.processPriority : " + node.processPriority + ", node.processTarget : " + node.processTarget);
			node = node.next;
		}
		
		System.out.println("\n\n");
	}
	
	/* 현재 Queue 내 모든 프로세스 순차적으로 꺼내어 실행하면서 'sequence' 갱신한다. */	
	public void processExecution(HashMap<Integer, Integer> processCount) {
		
		System.out.println("processCheck() 실행!\n");
		
		ArrayList<Integer> prioritys = new ArrayList<Integer>();
		boolean executionBool = true;
		
		while(true) {
			
			Node node = this.dequeue();
			
			/* Node 가 더 이상 없는 경우 종료한다. */
			if(node == null) {
				System.out.println("현재 큐에 대해서 더 이상 노드가 없으므로 종료.");
			}
			/* 현재 'dequeue()' 메소드 통해 Node 하나를 꺼내온 상태에서 현재 노드보다 높은 우선 순위가 있는 노드가 있는지 확인하고 있다면 그냥 다시 enqueue 하기 */
			for(int priority : processCount.keySet()) {
				if(priority >= node.processPriority)
					prioritys.add(priority);
				if(priority > node.processPriority) {
					executionBool = false;
					this.enqueue(node);
					break;
				}
			}
			
			System.out.println("디버깅 -  node.processPriority : " + node.processPriority + ", node.processTarget : " + node.processTarget + "\n");
			
			/* 현재 'node.processPriority' 보다 더 높은 것이 없다면 실행 */
			if(executionBool) {
				System.out.println("if(executionBool) 실행");
				
				/* 노드가 확인되는 경우 'sequence' 증가 및 해당 노드가 'Target' 여부 인지 확인 따라 반환 값 결정 */
				this.sequence += 1;	
				 if (!prioritys.isEmpty()) { // prioritys 리스트가 비어 있지 않은지 확인
					/* 현재 프로세스 우선순위에 따른 남은 실행 프로세스 개수를 1 줄인다. */
					processCount.put(prioritys.get(0), processCount.get(prioritys.get(0)) - 1);
					
					/* 현재 실행한 우선순위에 해당하는 프로세스 리스트를 1 줄였을 때 0으로 되었다면 리스트 아에 삭제. */
					if(processCount.get(prioritys.get(0)) == 0)
						processCount.remove(prioritys.get(0));
					
					if(node.processTarget == true)
						return;
				 }
			}
			
			prioritys.clear();
			
			executionBool = true;
		}
	}	
}

public class Solution {
	
	public static int solution(int[] priorities, int location) {
		
		QueueNode queue = new QueueNode();
		HashMap<Integer, Integer> processCount = new HashMap<Integer, Integer>();
		
		/* 초기 작업 :
		 * 
		 * 1. HashMap 컬렉션 사용해서 priorities 내 각 프로세스 우선 순위 별 큐를 별도로 두어서 각 우선 순위 별 프로세스 들어 온 순서 별로 구현을 한다.
		 * 2. HashMap 숫자 리스트를 관리하기 위해 각 배열 내 접근할 때에 동시에 연결리스트로서 생성한다. */
		
		for(int i = 0; i < priorities.length ; i ++) {
			
			System.out.println("디버깅 - i : " + i + ", priorities[" + i + "] : " + priorities[i] + "\n");
			
			if(processCount.get(priorities[i]) == null)	// 연결 리스트로써 관리될 현재 프로세스의 우선순위에 해당하는 Queue 가 없으면 생성
				processCount.put(priorities[i], 0);
			
			processCount.put(priorities[i], processCount.get(priorities[i]) + 1);
			
			Node node = null;
			
			/* 각 프로레스 별 우선순위 대로 큐를 별도로 만들어서 삽입 작업을 수행할 때 해당 Node 가 지정된 Node 인지 구별하기 위함.  */
			if(location ==  i) {		// 지정된 노드라면 매개변수 'Node.processTarget' 을 식별자로써 'true' 설정.
				
				System.out.println("디버깅 - location : " + location + ", i : " + i);
				node = new Node(true, priorities[i]);
				
			} else {					// 지정된 노드가 아니라면 매개변수 'Node.processTarget' 을 식별자로써 'false' 설정.
				node = new Node(false, priorities[i]);
			}
			
			queue.enqueue(node);
		}
		
		/* queue 내 포함된 데이터 확인. */
		queue.showqueue();
		
		/* 실제 로직 : 각 우선 순위 높은 순서대로 Queue 를 호출하여 내부적으로 Queue 내 실제 프로세스 시작 및 시작한 순서 번호인 'sequence'를 적용하여 'Targer' 노드가 실행되는 순번을 저장. 시작. */
		System.out.println("\n======= 실제 로직 시작 =====\n");
		
		queue.processExecution(processCount);
		
		return queue.sequence;
    }

	
	public static void main(String[] args) {
		
		// 1번째 예제
		int [] priorities = {2, 1, 3, 2};
		int location = 2;
		
		// 2번째 예제
//		int [] priorities = {1, 1, 9, 1, 1, 1};
//		int location = 0;
		
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
