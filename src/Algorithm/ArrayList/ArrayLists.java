package Algorithm.ArrayList;

/* ArrayList 는 객체를 생성 시 제너릭 타입으로 받아 객체를 생성하며 내부적으로는 Object로 업캐스팅 해서 저장. */
class ArrayObj<E> {
	
	private Object[] array;
	private final int DEFAULTSIZE = 10;
	
	int endIndex;
	
	/* 생성자는 주어진 제너릭 타입으로 배열을 생성한다. */
	@SuppressWarnings("unchecked")
	public ArrayObj() {
		
		this.array = (E[]) new Object[DEFAULTSIZE];
		endIndex = -1;
	}
	
	/* isFull() : 현재 배열이 가득 차 있는 지 확인하는 메소드 */
	public boolean isFull() {
		
		return endIndex + 1 < array.length - 1;
	}
	
	
	/* isEmplty() : 현재 배열이 비어 있는지 확인하는 메소드 */

	/* reSize() : 현재 배열이 가득 찼는지 또는 비어 있는 지에 따라 크기를 배열을 증가 시킬 건지 또는 배열을 감소 시킬 건지 선택 */
	@SuppressWarnings("unchecked")
	public void reSize(int choice) {
		
		if(choice == 1) {		// 배열이 가득 찼으므로 배열 2배로 증가
			
			Object [] newArray = (E[]) new Object[array.length * 2];
			
			int i = -1;
			for(Object obj : array) {
				++ i;
				newArray[i] = obj;
			}
			array = newArray;
			endIndex = i;
		}
		
		// remove() 메소드 실행하여 요소 하나 제거 했을 시 전체 배열 크기에서 실제 차지하고 있는 요소가 적을 시 크기 조정.
		if(choice == 0) {	
	
			/* 기본 배열 선언 크기와 현재 배열의 절반 크기 중 더 큰것을 선택해서 새로운 배열 생성 */
			if((array.length / 2) > endIndex) {
				
				Object [] newArray = (E[]) new Object[Math.max((array.length / 2), DEFAULTSIZE)];
				
				System.out.println("디버깅 - arry.leng : " + array.length + ", newArray.length : " + newArray.length);
				System.out.println("디버깅 - 현재 array 내 포함된 모든 데이터 출력!");
				for(int i = 0; i< array.length ; i ++)
					System.out.println(i + " 번째 데이터 : " + array[i]);
					
				int i = 0;
				for(; i <= endIndex; i++) {
					
					newArray[i] = array[i];
				}
				array = newArray;
				endIndex = i;
			}
		}
	}
	
	/* add() : 현재 배열 내 요소 추가하는 메소드 */
	public void add(Object item) {
		
		/* 배열이 비어 있다면 아이템을 추가하고, 배열 여유 공간이 없다면 resize() 실행 후 */
		if(isFull() == false)
			reSize(1);
		
		endIndex += 1;
		array[endIndex] = item;
		
	}
	
	/* get() : 현재 배열 내 요소를 반환하는 메소드.(데이터 제거하지 않음) */
	@SuppressWarnings("unchecked")
	public E get(int index) {
		
		// 요청한 인덱스가 실제로 존재하는 지 확인.
		if(index > endIndex) {
			System.out.println("현재 인덱스 범위를 벗어 났습니다!! \n");
			return null;
		}
			
		return (E) array[index];
	}
	
	/* remove() : 현재 배열 내 요소 삭제하는 메소드 */
	@SuppressWarnings("unchecked")
	public void remove(int index) {
		
		// 요청한 인덱스가 실제로 존재하는 지 확인.
		if(index > endIndex) {
			System.out.println("현재 인덱스 범위를 벗어 났습니다!! \n");
			return;
		}
		
		// 존재한다면 지정한 인덱스 요소 삭제 한 후에 재 배치
		array[index] = null;
		
		if(index == endIndex) {
			
			endIndex -= 1;
			reSize(1);
			return;
			
		} else {
			
			for(int i = 0; i < endIndex; i++)
				if(array[i] == null) {
					for(; i < endIndex; i++)
						array[i] = array[i+1];
					
					reSize(0);
					return;
				}
		}	
	}	
}



public class ArrayLists {
	
	public static void main(String[] args) {
		 ArrayObj<Integer> arrayList = new ArrayObj<>();
		  // 요소 추가
	        arrayList.add(1);
	        arrayList.add(2);
	        arrayList.add(3);
	        arrayList.add(4);
	        arrayList.add(5);
	        
	        // get 메소드 테스트
	        System.out.println("인덱스 2의 요소: " + arrayList.get(2));
	        
	        // remove 메소드 테스트
	        arrayList.remove(2);
	        System.out.println("요소 제거 후 인덱스 2의 요소: " + arrayList.get(2));
	        
	        // isFull 메소드 테스트
	        System.out.println("배열이 가득 찼는가? " + arrayList.isFull());
	        
	        // 배열 크기 조정 테스트
	        arrayList.reSize(1);
	        System.out.println("배열 크기 증가 후 인덱스 4의 요소: " + arrayList.get(4));
	        
	        arrayList.reSize(0);
	        System.out.println("배열 크기 감소 후 인덱스 4의 요소: " + arrayList.get(4));
	}
}