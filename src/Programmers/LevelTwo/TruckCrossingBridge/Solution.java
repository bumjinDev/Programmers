package Programmers.LevelTwo.TruckCrossingBridge;

/* 스택/큐 > 다리를 지나는 트럭 : 대기 중인 트럭들이 정해진 다리 위에서 지나갈 때의 최단 시간 초 구하기. */

class Node {

	int weight;
	int moveTime;
	
    Node prev;
    Node next;

    public Node(int weight, int moveTime) {
        this.weight = weight;
        this.moveTime = moveTime;
        prev = null;
        next = null;
    }
}

/* Node 를 가지고 queue 자료구조를 구현하는 클래스, 내부적으로 prev와 next 를 가진다. */
class Queue {
	
	int truckCount;
	int truckWeight;
	int allTime;
	
    Node near;
    Node front;

    public Queue() {
    	
    	truckCount = 0;
    	truckWeight = 0;
    	allTime = 0;
    	
        near = null;
        front = null;
    }

    /* =========== 해당 Queue 내에서 실제로 사용될 메소드들 ================ */
    public void enqueue(Node node) {
        if (near == null) {
            near = node;
            front = node;
        } else {
            node.next = near;
            near.prev = node;
            near = node;
        }
    }

    /* dequeue() : 단 방향 연결리스트에서 front 포인터의 노드 삭제. */
    public Node dequeue() {
        Node node = front;
        if (front == null)
            return null;
        if (near == front) {
            near = null;
            front = null;
            return node;
        } else {
            front = front.prev;
            front.next.prev = null;
            front.next = null;
            return node;
        }
    }

    public void showqueue() {
        Node node = near;
        while (node != null) {
            System.out.println("node.weight : " + node.weight + ", node.moveTime : " + node.moveTime);
            node = node.next;
        }
    }

    public void increaseTime() {
        Node node = near;
        while (node != null) {
            node.moveTime += 1;
            node = node.next;
        }
    }
}

public class Solution {

	// bridge_length : 한 다리에 한꺼번에 지나갈 수 있는 트럭 수.
	// weight : 한 다리 내 한꺼번에 지나갈 수 있는 트록 총 무게.
	// [] truck_weights : 다리를 지나가야 되는 대기트럭으로 각 배열 내 값들을 각 트럭 별 무게(kg).
	
	/* 풀이 과정 : 
	 * 1. 현재 큐 내 포함 된 트럭의 총 무게 합 및 길이를 확인 하고 현재 트럭 전체 배열을 순차적으로 찾음으로써 다음 트럭이 들어갈 수 있는 무게인지 판단.
	 * 2. 있다면 추가하고 없다면 추가하지 않음. 
	 * 3. 큐의 모든 노드 들에 대해서 이동 시간(node.moveTime)을 확인하여 'bride_length' 비교 하여 만약 동일하다면 다리의 끝에 온걸로 간주하고 해당 노드의 이동시간(node.time)을 1 증가(다리 끝에 도달한 시점이라고 해도 완전히 벗어날려면 1이 더 추가되어야 하기 때문)
	 * 	  하고 해당 노드를 큐에서 제거.
	 * 4. 큐를 꺼내는 과정 중 만약 현재 큐 내 아무런 트럭이 존재하지 않고 더 이상 꺼낼 트럭 조차 없다면 현재까지 진행된 전체 시간을 기준으로 총 경과 시간을 측정하여 반환한다.
	 * */
	public int solution(int bridge_length, int weight, int[] truck_weights) {
        
		/* Queue 생성 */
		Queue queue = new Queue();
		
		int truckIndex = 0;
		
		while(true) {
			
			queue.increaseTime();	// 각 트럭 개별 진행 시간, 즉 우선 기존 내용물들을 이동 시킴.
			
			/* 다리(queue) 에서 가장 front 위치 트럭(노드)가 이미 다리를 벗어난 상태라면 노드를 삭제한다. */
			if(queue.front != null && queue.front.moveTime > bridge_length) {
			
				Node node = queue.dequeue();
				queue.truckWeight -= node.weight;
				queue.truckCount -= 1;
			}
			
			/* 다리(queue) 내 무게가 다음 차례 트럭 무게 까지 감당을 못하거나 길이가 포화 상태가 아니라면 반복적으로 받는다. 그리고 이때 인덱스 값을 확인하여 남은 트럭 여부 또한 확인. */
			if( truckIndex < truck_weights.length && queue.truckCount + 1 <= bridge_length && queue.truckWeight + truck_weights[truckIndex] <= weight) {
				
				/* queue 내 트럭을 추가하고 길이 및 무게를 변경. */
				queue.enqueue(new Node(truck_weights[truckIndex], 0));		// 시간은 트럭이 quque, 즉 다리 위에 올라 갔을 때 부터 셈 하지 않고, 일괄적으로 전체 추가한다.
				queue.truckWeight += truck_weights[truckIndex];
				queue.truckCount += 1;
				
				truckIndex += 1;	// 다음 차례에 꺼내어질 트럭 인덱스 조정.
				queue.near.moveTime += 1;	
			}
			
			queue.allTime += 1;		// 전체 진행 시간
			
			/* 더 이상 추가할 버스 노드들이 없으면서 queue 내부 트럭 노드들 또한 비어 있다면 종료. */
			if(truckIndex >= truck_weights.length && queue.front == null)
				return queue.allTime;
		}
    }
	
	public static void main(String[] args) {

		Solution solution = new Solution();
		
		// 첫번째 입출력 예
//		int bridge_length = 2;
//		int weight = 10;
//		int[] truck_weights = {7, 4, 5, 6};

		// 두번째 입출력 예
		int bridge_length = 100;
		int weight = 100;
		int[] truck_weights = {10,10,10,10,10,10,10,10,10,10};
		
		System.out.print("다리 전체 길이 : " + bridge_length + ", 다리 수용 무게 : " + weight + ", 트럭 들 : ");
		for(int i = 0; i < truck_weights.length; i++)
			System.out.print(truck_weights[i] + " ");
		System.out.println("\n");
		
		int result = solution.solution(bridge_length, weight, truck_weights);
		System.out.println("\n결과 : " + result);
	}

}
