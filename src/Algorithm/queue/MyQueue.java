package Algorithm.queue;

/* 원형 큐 로직 구현. 
 * push: near 인덱스의 다음 위치에 데이터를 추가하고 near 인덱스를 증가시킵니다. 또한, 'isFull' 메소드를 먼저 호출하여 여유 공간이 있는지 확인합니다.
 * pop: front 인덱스의 다음 위치에 있는 데이터를 확인하여 존재할 경우 데이터를 출력하고 front 인덱스를 증가시킵니다. 또한, 'isEmpty' 메소드를 먼저 호출하여 비어 있지 않은지 확인합니다.
 * pick: front 인덱스의 다음 위치에 있는 데이터를 확인하여 존재할 경우 데이터를 출력만 합니다. 또한, 'isEmpty' 메소드를 먼저 호출하여 비어 있지 않은지 확인합니다.
 * resize: 'push' 메소드 실행 중 'isFull' 메소드 실행 시 가득 찼다고 확인되면, Array.copyOf(현재 배열, 새 배열 크기)를 사용하여 배열 크기를 증가시킵니다.
 * search: 함수 호출 시 주어진 값에 해당하는 데이터가 있는지 front부터 near까지 순차적으로 탐색하여 지정된 값을 찾습니다. 값이 없을 경우 -1을 반환합니다.
 * isEmpty: front 인덱스부터 near 인덱스까지의 거리를 계산하고, 계산된 거리 내에서 데이터를 반복 탐색하여 데이터가 없으면 1을 반환합니다.
 * isFull: front부터 near까지의 거리가 전체 크기 -2이고 near에 데이터가 있으면 가득 찬 상태로 간주합니다. 그렇지 않으면 비어 있지 않음을 출력합니다.
 */
public class MyQueue<E> {

	/* 
	 * front 위치는 비워둔다. 이유는 다음과 같다:
	 * 1. front에 데이터를 넣지 않으면, near 인덱스가 값을 삭제할 때마다 한 칸씩 이동해도 front를 앞지르지 않는다.
	 * 2. 별도의 front와 near 인덱스 비교 확인하는 로직 없이도 front와 near가 겹치는 상황을 방지할 수 있다.
	 */
	
	
	/* 데이터 출력에 대한 인덱스(앞쪽) - front 는 결과적으로 near 가 채운 인덱스를 순차적으로 따라 가면서 데이터 출력하는 인덱스를 순차적으로 지정한다. */
	private int front;
	/* 데이터 삽입에 대한 인덱스(뒤쪽) - near */
	private int near;
	/* 기본 크기 : 6 */
	private static final int defaultSize = 6;
	
	private E[] queue;
	
	/* 기본 각 인덱스 별 시작 지점 :
	 * front : 0번째 인덱스
	 * near : 1번째 인덱스 */
	public MyQueue() {
		
		this.front = 0;
		this.near = 1;
		this.queue = (E []) new Object[defaultSize];
	}
	
	public boolean isFull(int front, int near, Object [] queues) {
		
		System.err.println("isFull() 호출!");
		
		@SuppressWarnings("unchecked")
		E[] queue = (E[]) queues;
		
		if (front < near) {
			if(front - near == queue.length -1 && queue[near] != null)
				return true;
			
		} else if(near < front) {
			if(front - near == 1 && queue[near] != null)
				return true;
		}
		
		return false;
	}
	
	public boolean isEmplty(int front, int near, Object [] queues) {
		
		System.out.println("isEmpty() 호출!");
		
		@SuppressWarnings("unchecked")
		E[] queue = (E[]) queues;
		
		if (front < near) {
			if(near - front == 1 && queues[near] == null)
				return true;
			
		} else if(near < front) {
			if(front == queue.length -1 && near == 0 && queues[near] == null)
				return true;
			else
				return false;
		}
		return false;
	}
	
	/* isFull() - true : 현재 사이즈의 2배 증가 */
	public boolean resize() {

		// 배열 크기 증가 : isFull()의 결과로써 가득 찼음을 확인.
		if() {
			
			
		}
		
		// 배열 크기 감소 : isPop()의 결과로써 현재 크기에서 절반 정도만 차지 했음을 확인.
		
		return true;
	}
	
	public Object push(int front, int near, Object [] queus) {
		
		boolean bool = isFull(front, near, queus);
		
		if(!bool)
			resize();
		
		return "";
	}
	
	/* search : 지정 받은 값을 찾았는지 확인.
	 * 원형 큐이므로 front 인덱스 위치와 near 인덱스 위치를 비교합니다.
	 * near가 front 위치보다 낮다면, near가 이미 최대 인덱스 크기 범위를 넘어섰다고 판단하여 추가적인 로직을 실행합니다.
	 * 
	 * 1. front 위치와 near 위치를 비교
	 * 		1-1. near 가 front 위치보다 낮다 :
	 * 				(마지막 인덱스 위치 - front) = 첫번째 for 문에서 탐색할 횟수
	 * 				(front - near) = 2번째 for 문에서 전체 반복 탐색 횟수
	 * 	 	1-2. front 가 near 위치보다 낮다
	 * 				(front - near) => 첫번째 for 문에서 탐색할 횟수
	 */
	
	public static void main(String[] args) {
		
		
	}
}
