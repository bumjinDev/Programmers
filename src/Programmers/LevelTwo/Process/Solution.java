package Programmers.LevelTwo.Process;

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
	
	int priority;
	boolean processTarget;
	
	Node prev;	// 노드 뒤쪽
	Node next;	// 노드 앞쪽
	
	
	public Node(int priority, boolean processTarget){
		
		this.priority = priority;
		this.processTarget = processTarget;
		
		this.prev = null;
		this.next = null;
	}
}

public class Solution {
	
	Node near = null;	// 큐의 뒤쪽..데이터 입력 부분.
	Node front = null;	// 큐의 앞쪽..데이터 출력 부분
	
	/* enqueue() : 단방향 연결리스트 추가
	 * 
	 * 1. 'near' 가 비어 있다면 공백 연결 리스트 라는 의미이므로 새로 생성한 노드에 대해서 큐 포인트 'near'과 'front' 모두 같은 하나의 새로운 노드 참조
	 * 2. 'near'가 비어 있지 않다면, 새로운 노드를 생성하여 'front' 포인터가 가리키는 노드에 연결하고 'front' 포인터를 새 노드로 이동시킵니다.
	 * 
	 * */
	
	public void enqueue(Node node) {
		
//		System.out.println("enqueue()!\n");
		
		if( near == null ) {
			
			near = node;
			front = node;
		}
		
		else {
			
			/* 기존 near Node 와 새로운 nearNode 간 양방향 링크 성립. */
			near.prev = node;
			node.next = near;
			
			near = node;
			
		}
	}
	
	/* dequeue() : 단 방향 연결리스트에서 front 포인터의 노드 삭제. */
	public Node dequeue() {
		
//		System.out.println("dequeue()!");
		
		Node node = front;
		
		front = front.prev;
		front.next.prev = null;
		front.next = null;
		
		System.out.println();
		return node;
	}
	
	public void showqueue(Node near) {
	
		System.out.println("showqueue()! ");
		
		Node node = near;
		
		System.out.print("node.priority : ");
		while(node != null) {
			
			System.out.print(node.priority + " ");
			node = node.next;
		}
		
		System.out.println("\n");
	}
	
	public boolean processCheck(Node node, HashMap<Integer, Integer> processPriorites) {
		
		for(int key : processPriorites.keySet())
			if(key < node.priority)
				if(processPriorites.get(key) > 0)
					return false;
		
		return true;
	}
	
	public void decressLists(Node node, HashMap<Integer, Integer> processPriorites) {
		
		System.out.println("decressLists() !");
		System.out.println("node.priority : " + node.priority + ", processPriorites.get(node.priority) : " + processPriorites.get(node.priority));
		
		processPriorites.put(node.priority, processPriorites.get(node.priority) - 1);
	}
	
	public static int solution(int[] priorities, int location) {
        
		Solution soultion = new Solution();
		
		/* 초기 작업 :
		 * 
		 * 1. HashMap 컬렉션 사용해서 priorities 내 각 숫자들의 리스트를 관리한다.
		 * 2. HashMap 숫자 리스트를 관리하기 위해 각 배열 내 접근할 때에 동시에 연결리스트로서 생성한다. */
		
		HashMap<Integer, Integer> processPriorites = new HashMap<Integer, Integer>();
		
		for(int i = 0 ; i < priorities.length  ; i++) {
			
			if(processPriorites.get(priorities[i]) == null)
				processPriorites.put(priorities[i], 1);
				
			else
				processPriorites.put(priorities[i], processPriorites.get(priorities[i]) + 1);
			
			/* 배열 'priorities'를 큐로 사용하기 위해 연결 리스트로 구성하고, 'near'와 'front' 포인터를 사용합니다. */
			// 현재 priority[i] 의 값을 가지고 Node를 생성 후 이를 큐 연결리스트 구현으로써 다룬다.
			if(location == i) {
				soultion.enqueue(new Node(priorities[i], true));
				System.out.println("현재 로케이션은? : " + i);
			}
			else
				soultion.enqueue(new Node(priorities[i], false));
		}
		
		soultion.showqueue(soultion.near);
		
		/* 실제 로직 시작. */
		
		Node node;
		int sequence = 0;
		
		while(true) {
			
			node = soultion.dequeue();
			System.out.println("현재 fornt 노드 : " + node.priority + ", target 여부 : " + node.processTarget);
			
			
			/* target 프로세스가 있을 때 해당 프로세스의 순번을 기준으로 먼저 처리 되어야 하는 프로세스들이 모두 처리가 되었다면 현재 처리된 순번(sequence)을 반환할 수 있도록 현재 반복문을 종료하며 아니라면 계속 진행. */
			if( node.processTarget == true) {
				
				if( soultion.processCheck(node, processPriorites) == true ) {
					
					System.out.println("목표 노드 찾음.." + node.priority);
					
					break;		
				} 
				else {
				
					sequence += 1;
					soultion.enqueue(node);
				}
			}
			
			else {
				
				soultion.enqueue(node);
				soultion.decressLists(node, processPriorites);
				sequence += 1;
			}
		}
		
        return sequence;
    }
	
	public static void main(String[] args) {
		
		// 1번째 예제
		int [] priorities = { 2, 1, 3, 2};
		int location = 2;
		
		System.out.println("결과 : " + solution(priorities, location));
	}
}
