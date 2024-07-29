package Algorithm.queue;

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
	
	int flag;
	
	private E[] queue;
	
	/* 기본 각 인덱스 별 시작 지점 :
	 * front : 0번째 인덱스
	 * near : 1번째 인덱스 */
	@SuppressWarnings("unchecked")
	public MyQueue() {
		
		this.front = 0;
		this.near = 0;	// near 은 front + 1(혹은 맨 처음 인덱스 0) 이지만 push 로직 위해 초기 값 0 설정
		this.queue = (E[]) new Object[defaultSize];
	}
	
	/* 데이터를 push 하기 전에 비어 있는지 확인. */
	public void isFull() {
		
		System.out.println("\nisFull() 호출!");
		
		boolean resBool = false;
		
		if (front < near) {
			if(near - front >= queue.length -1 && queue[near] != null) {
				System.out.println("front < near !");
				resBool =  true;
			}
		} else if(near < front) {
			if(front - near <= 1 && queue[near] != null) {
				System.out.println("near < front !");
				resBool =  true;
			}
		}
		
		flag = 1;
		
		System.out.println("isFull() 결과 : " + resBool + "\n");
		
		if(resBool)
			resize();
	}
	
	/* 데이터를 pop 하기 전에 비어 있는지 확인. */
	public boolean isEmpty() {
		
		System.out.println("isEmpty() 호출!");
		boolean resBool = false;
		
		if (front < near) {
			System.out.println("near : " + near + ", front : " + front);
			if(near - front == 1 && queue[near] == null)
				resBool = true;
			
		} else if(near < front) {
			if(front == queue.length -1 && near == 0 && queue[near] == null)
				resBool = true;
			else
				resBool = false;
		}
		
		return resBool;
	}
	
	/* resize() :
	 * 	1. push() 실행 전 이미 원형 큐가 가득 찼는지.
	 * 	2. pop() 직후 최대 원형 큐 크기 대비 절반 미만으로 찼으면 */
	@SuppressWarnings("unchecked")
	public void resize() {
		System.out.println("\nresize() 실행!\n");
		
		/* isFull() 실행 되어 참일 경우 현재 배열 크기의 2배의 배열을 새로 선언 후 원형 큐에 맞게 새로운 배열 내 저장. */
		if(flag == 1) {
			
			E[] queueTemp = (E[]) new Object[queue.length * 2];
			
			int i = -1;	// 실제 데이터가 들어가는 인덱스는 near 부터..
			
			if(front < near) {
				for(Object obj : queue) {	
					i++;
					queueTemp[i] = (E) obj;
				}
			}
			
			if(near < front) {	// 반복문은 2번 실행한다, 한번은 front 인덱스부터 queues 마지막 인덱스 위치까지 반복, 두번째는 near 부터 front - 1 까지 반복.
				
				System.out.println("첫번째 정렬 - near < front : " + near + ", " + front);
				/* front 부터 queue 의 마지막 인덱스 까지 복사하여 새로운 배열 내 저장. */
				for(int j = front; j <= queue.length - 1; j ++) {
			
					i ++;
					queueTemp[i] = (E)queue[j];
					System.out.println("'(E)queue[j]' : " + (E)queue[j] + ", i : " + i + ", j : " + j);
				}
				
				System.out.println("두번째 정렬 - near : " + near + ", front : " + front);
				/* near 부터 시작해서 front - 1 위치까지 새로운 배열 내 저장. */
				for(int j = 0; j < front; j++) {
					i++;
					queueTemp[i] = (E)queue[j];
//					System.out.println("");
				}
			}
		
		front = 0;
		near = i-1;
		queue = queueTemp;
		
		/* pop() 에서 호출 시 전체 원형 큐 크기에 비해 점유하고 잇는 크기가 반 미만일 경우 크기 조정 */
		} else if (flag == 0) {	// 현재 배열 크기를 현재의 배열 크기의 반 혹은 기본 배열 값 중 선택적으로 더 큰 값으로써 재 할당.
			
			E[] queueTemp = (E[]) new Object[queue.length / 2];
			
			int i = 0;
	
			/* 현재 원형 큐 내 실제로 사용하고 있는 크기가 절반 여부 판단. */
			// front 인덱스가 near 인덱스 보다 적다면.. 실제로 점유하는 크기가 절반 이하인지 판단.
			if(front < near) {
				System.out.println("near - front : " + (near - front));
				if(near - front < queue.length / 2 ) {
					int j = -1;
					for(i = front -1 ; i < near;) {	// 당연히 원형 큐면 front 는 기본적으로 공백으로 자리 잡으니 front 내 null 도 동일하게 입력.

						i++; j++;
						queueTemp[j] = queue[i];
						System.out.println("queue[" + i + "] : " + queue[i] + ", queueTemp[" + j + "] : " + queueTemp[j]);
					}
				}
				
				front = 0;
				near = i;
				
				System.out.println("queryTemp 디버깅!");
				for(int k = 0; k < queueTemp.length; k++)
					System.out.println("queueTemp[" + k + "] : " + queueTemp[k]);
		
				queue = queueTemp;
				
				System.out.println("query 디버깅!");
				for(int k = 0; k < queue.length; k++)
					System.out.println("queue[" + k + "] : " + queue[k]);
				
			// near 인덱스가 front 인덱스 보다 크다면...즉 한바퀴 돌았다면..
			} else if (near < front) {
				
				int range1 = (queue.length-1) - front;
				int range2 = near + 1;
				int res = range1 + range2;
				
				System.out.println("res : " + res + ", queue.length : " + queue.length);
				
				if(res < queue.length) {
					
					/* front 부터 마지막 인덱스 까지 반복하며 새로운 배열에 넣기. */
					for(int j = front + 1; j <= queue.length - 1; j++) {
						i++;
						queueTemp[i] = (E) queue[j];
					}
					
					/* 0번 인덱스 부터 front - 1 인덱스 까지 반복하며 새로운 배열 내 넣기. */
					for(int j = 0; j <= front - 1; j++) {
						queueTemp[i] = (E) queue[j];
					}
				}
				
				front = 0;
				near = i;
				queue = queueTemp;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object [] push(Object val) {
		
		System.out.println("push() 실행!");
		System.out.println("front : " + front + ", near : " + near);
		
		isFull();	// 이미 완형 큐가 가득차 있다면 내부저적으로 resize 호출해서 2배로 늘린 후 실행.
		
		if((near+1) > queue.length - 1)	// * 만약 현재 near 인덱스가 queue 의 마지막 인덱스를 벗어나게 된다면 원형 큐 구현 따라 처음 인덱스 0으로 설정.
			near = 0;
		else
			near += 1;
		queue[near] = (E) val;
	
		System.out.println("push() 실행 후 인덱스 - front : " + front + ", near : " + near + "\n");
		return queue;
	}
	
	/* isEmpty 의 결과로 비어 있지 않다면 실행. */
	public Object pop() {
		
		System.out.println("pop() 실행!");
		
		/* 현재 뺄 데이터가 남아 있다면 빼고 없다면 안 뺀다. */
		Boolean bool = isEmpty();	 // 참이면 비어 있으므로 빼는 작업 못함.
		System.out.println("현재 인덱스 - front : " + front +", near : " + near);
		if(bool) {
			System.out.println("비어 있으므로 뺄 수 없습니다.!\n");
			/* 원형 큐의 pop() 메소드에서 인덱스를 확인하는 순서는 다음과 같다.
			 * 
			 * 1. front < near: near은 현재 원형 큐 배열의 끝 인덱스 범위 이내에 있으므로 이를 고려하여 인덱스를 조정.
			 *    - if ((front + 1) == near): front가 near 바로 직전에 있다는 의미이므로 near 인덱스의 값을 null로 치환하고 front 는 별도의 인덱스 변환 없음
			 *    - if ((front + 1) < near): front + 1을 하여도 해당 인덱스가 near보다 최소 한 칸 전이므로 해당 인덱스로 front를 치환하고 해당 인덱스의 값을 null로 치환.
			 * 
			 * 2. near < front: 원형 큐로 동작하여 값을 채워 넣을 때 마지막 인덱스 범위를 넘어 초기 0번부터 다시 채워 나갔다는 의미이므로 1번과는 다른 pop() 메소드 로직이 필요.
			 *    - if ((front + 1) <= queue.length - 1): front 인덱스 값을 +1 후 해당 위치의 값을 null로 치환.
			 *    - if ((front + 1) > queue.length - 1 ): front 인덱스를 0번으로 옮긴 후 null로 처리합니다. 만약 near가 0인 경우 near 인덱스의 값을 null로 처리하는 로직을 하위 if 문에 적용.
			 *       - if (0 == near): near 인덱스의 값을 null로 치환 후 front는 그대로 둔다.
			 *       - if (0 < near): queue 배열의 0번 인덱스를 front 인덱스로 치환.
			 */
		} else {
			
			if(front < near) {
				if ((front + 1) == near)
					queue[near] = null;
				
				if ((front + 1) < near) {
					
					front += 1;
					queue[front] = null;
				}
				
			} else if(near < front) {
				
				if ((front + 1) <= queue.length - 1) {
					front += 1;
					queue[front] = null;
				}
				
				if((front + 1) > queue.length - 1) {
					if(0 == near)
						queue[near] = null;
					if(0 < near) {
						front = 0;
						queue[front] = null;
					}
				}
			}
		}
		
		flag = 0;
//		resize();	// resize() 호출하여 확인해서 전체 현재 배열 크기 중 절반 미만이라면 현재 배열의 절반 크기로 줄인다.
		
		return new Object();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		
		String result = "";
		
		for(Object temp : queue) {
		
			if(temp == null) {
				result += "_ ";
			}
			
			if(temp != null) {
				E obj = (E) temp;
				result += obj + " ";
			}
		}
		return result;
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
		
		MyQueue<String> myQueue = new MyQueue<String>();
		
		System.out.println("quueue start! \n");
		
		myQueue.push("A");
		myQueue.push("B");
		myQueue.push("C");
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		myQueue.pop();
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		myQueue.pop();
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		myQueue.push("D");
		myQueue.push("F");
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		myQueue.push("G");
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		myQueue.push("H");
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		myQueue.push("I");
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		
		System.out.println("\npop() start! \n");
		myQueue.pop();
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		myQueue.resize();
		System.out.println("resize() : " + myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		myQueue.pop();
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		myQueue.pop();
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		myQueue.pop();
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		myQueue.pop();
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		myQueue.pop();
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
		myQueue.pop();
		System.out.println(myQueue.toString() + ", front : " + myQueue.front + ", near : " + myQueue.near + "\n");
	}
}
