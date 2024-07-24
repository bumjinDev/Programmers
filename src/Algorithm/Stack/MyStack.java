package Algorithm.Stack;

import java.util.Arrays;
import java.util.EmptyStackException;

public class MyStack<E> {
		
		private static final int DEFAULT_CAPACITY = 6; 
		private E [] stackArr;
		private int top;
		
		public MyStack()  {
			
	        this.stackArr = (E[]) new Object[DEFAULT_CAPACITY]; // 6 용량으로 내부 배열 생성
	        this.top = -1;
	    }
		
		/* 스택이 가득 찼는 지 확인. */
		public boolean isFull() {
			System.out.println("isFulll() 실행!");
			return top == this.stackArr.length-1;
		}
		
		/* 스택이 비어 있는 지 확인 */
		public boolean isEmpty() {
			return top == -1;
		}
		
		/* 클래스 용량 늘리기 */
		private void resize() {
			
			System.out.println("resize() 실행!");
			int arr_capacity = this.stackArr.length - 1;
			
			// 용량 가득 찬 경우 : 사이즈 늘린다.
			if(this.top == arr_capacity) {
				
				int new_capacity = this.stackArr.length * 2;
				this.stackArr = Arrays.copyOf(stackArr, new_capacity);
				return;
			}
			
			if(this.top < (arr_capacity / 2)) {
				
				System.out.println("용량 줄이기 실행");
				int half_capacity = stackArr.length / 2;
				
				stackArr = Arrays.copyOf(this.stackArr, Math.max(half_capacity, DEFAULT_CAPACITY));
				return;
			}
		}
		
		@SuppressWarnings("unchecked")
		/* 스택 push 구현. */
		public E push(E value) {
			
			// 1. 배열이 꽉 차 있는지 검사한다.
			if(isFull()) {	// 꽉 찼으면 배열 리사이징 실행.
				resize();
			}
			// 스택 내 원소 추가할 것이니 현재 top 위치 1 증가
			top ++;
			
			// top 위치에 원소 하나 추가
			stackArr[top] = value;
			
			// 추가한 원소 값 반환
			return value;
		}
		
		public E pop() {
			
			// 배열 비어 있으면 예외 발생.
			if(isEmpty())
				throw new EmptyStackException();
			
			// 원소를 꺼낸다.
			E value = (E) stackArr[top];
			
			// 현재 탑 위치에 잇는 원소를 삭제.
			stackArr[top] = null;
			
			// top 위치 감소
			top --;
			
			// 현재 배열 크기를 확인해서 차 있는 크기가 현재 배열 크기의 반 미만이라면 기본 크기와 현재 배열 크기의 절반 중에서 큰 값으로 배열 재 구성 할당.
			resize();
			
			// 백업 요소 반환.
			return value;
		}
		
		@SuppressWarnings("unchecked")
		public E peek() {
			
			if(isEmpty())
				throw new EmptyStackException();
			
			return (E) stackArr[top];
		}
		
		public int search(E value) {

		    // 스택 맨 위 서부터 아래로 순회하여 찾고자 하는 값의 위치를 구한다.
		    for (int i = top; i >= 0; i--) {
		        if(stackArr[i].equals(value)) {
		            return top - i + 1;
		        }
		    }
		    // 만일 찾고자 하는 값이 없다면 -1을 반환
		    return -1;
		}
		
		@Override
		public String toString() {
		    return Arrays.toString(stackArr);
		}
		
		public static void main(String[] args) {
			
			MyStack<Integer> stack = new MyStack<>();
			
			stack.push(1);
			stack.push(2);
			stack.push(3);
			stack.push(4);
			stack.push(5);
			stack.push(6);
			stack.push(7);
			stack.push(8);
			
			System.out.println();
			System.out.println("stack.peek() : " + stack.peek());;
			
			System.out.println();
			stack.pop();
			System.out.println("stack.peek() : " + stack.peek());;
			
			stack.pop();
			System.out.println("stack.peek() : " + stack.peek());;
			
			stack.pop();
			stack.pop();
			stack.pop();
			stack.pop();
			
			stack.pop();
			
			
			System.out.println();
			System.out.println(stack.toString());
			
		}
}