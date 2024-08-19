package Programmers.LevelTwo.LRUCache;

import java.util.ArrayList;
import java.util.Random;


/* LRU 알고리즘 구현해서 실행 시간 구하기 */
public class Solution {

	public static int solution(int cacheSize, String[] cities) {
	
		int answer = 0;		// 캐시 크기 판별
		int flag = 1;
		
		// 실제 캐시 메모리로 사용할 메모리 할당.
		ArrayList<String> cacheMemory = new ArrayList<String>();	// 실제 캐시 공간 구현.
		
		for(String citys : cities) {
			
			String city = citys.toLowerCase(); 
			
			System.out.println("city : " + city);
			System.out.println("cacheMemory.toString() : " + cacheMemory.toString() + "\n");
			
			for(int i = 0; i < cacheMemory.size(); i++) {
				
				String chacheValue = cacheMemory.get(i).toLowerCase();
				
				if(chacheValue.equals(city)){
					
					answer += 1;
					System.out.println("히트 발생! " + city + "\n");
					
					flag = 0;
					
					/* 히트 발생하면 최근에 사용했단 것이니 LRU는 마지막 히트 발생한 객체를 지우므로 현재 히트 발생한 건, 최신이라는 의미로 맨 뒤에 다시 넣어서 삭제할 때 대비.  */
					cacheMemory.add(cacheMemory.get(i));
					cacheMemory.remove(i);
					
					System.out.println("히트 된 것을 맨 뒤로 변경 : " + cacheMemory.toString());
					
					break;
				}
				
			}
			/* 캐시 미스 : 소요 시간 5 추가.
			 * 	1. 캐시 메모리 가득 찼다면 페이지 폴트 동작으로써 LRU 알고리즘 구현
			 * 	2. 캐시 메모리 비어 있다면 단순 빈 공간에 데이터 추가.  */	
			
			if(flag == 1) {
				
				answer += 5;
				
				if(cacheMemory.size() < cacheSize)	// 캐시 메모리 비어 있으므로 단순 빈 공간에 데이터 추가.
					cacheMemory.add(city);
				
				else 		// 캐시 메모리 비어 있지 않으므로 LRU 알고리즘 구현한 메소드 호출하여 캐시 스왑할 메모리 인덱스 반환.
					lRuAlgorithm(cacheMemory, city);
				
			}	
			flag = 1;
		}
		return answer;
	}
	
	/* 내부적으로 카운터 값을 사용해서 가장 마지막에 탐색된 인덱스 반환. */
	public static void lRuAlgorithm(ArrayList<String> cacheMemory, String city) {
		
		System.out.println("lRuAlgorithm() !" + ", 정렬 전 전체 캐시 : " + cacheMemory.toString());
	
		cacheMemory.add(city);
	    cacheMemory.remove(0);
		
		System.out.println("정렬 후 전체 캐시 : " + cacheMemory.toString() + "\n");
	}
	
	public static void main(String[] args) {
		
        int[] cacheSizes = {0, 1, 2, 3, 4, 5};
        String[] cityNames = {
            "Jeju", "Pangyo", "Seoul", "NewYork", "LA", "SanFrancisco", "Rome", "Paris", "London", "Berlin",
            "Tokyo", "Osaka", "Beijing", "Shanghai", "Moscow", "Sydney", "Melbourne", "Dubai", "Mumbai", "Delhi",
            "Bangkok", "Singapore", "HongKong", "KualaLumpur", "Jakarta", "Istanbul", "Cairo", "MexicoCity", "Toronto", "Vancouver"
        };
        
        Random random = new Random();
        int cacheSize = cacheSizes[random.nextInt(cacheSizes.length)];
        String[] cities = new String[random.nextInt(18) + 1];
        
        for (int i = 0; i < 1; i++) {
            
            for (int j = 0; j < cities.length; j++) {
                cities[j] = cityNames[random.nextInt(cityNames.length)];
            }
            System.out.println(cacheSize + "\t" + java.util.Arrays.toString(cities));
        }
		
		// 6번째(마지막) 예시
//		int cacheSize = 0;
//		String cities[] = {"Jeju", "Pangyo", "Seoul", "NewYork", "LA"};
        
	    int answer = solution(4, cities);
	    System.out.println("결과 : " + answer);
		

	}
}
