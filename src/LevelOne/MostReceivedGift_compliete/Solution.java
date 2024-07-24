package LevelOne.MostReceivedGift_compliete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/* 문제 : 가장 많이 받은 선물 */
public class Solution {

	public static class friendInfoVO {
		
		private String  giftsname;
	    private int giftsGiven;
	    
	    public void setgiftsname(String giftsname) {
	    	
	    	this.giftsname = giftsname;
	    }
	    
	    public void setgiftsGiven(int giftsGiven) {
	    	
	    	this.giftsGiven = this.giftsGiven + giftsGiven;
	    }
	  
	    public String getgiftsname() {
			
			return giftsname;
		}
	    
	    public int getgiftsGiven() {
	    	
	    	return giftsGiven;
	    }
		
				
		public friendInfoVO() {
			
		}
		
		public friendInfoVO(int giftsGiven, int giftsReceive, int giftsIndex) {
		
			this.giftsGiven = giftsGiven;
		}
	}
	
    public static int solution(String[] friends, String[] gifts) {
        
    	int answer = 0;
        
    	HashMap<String, ArrayList<String>> giftsHash = new HashMap<String, ArrayList<String>>(); 
    	// 각 사람 별로 누구누구 에게 선물을 주었는 지 배열로 저장하는 변수..
    	HashMap<String, ArrayList<friendInfoVO>> giftIndex = new HashMap<String, ArrayList<friendInfoVO>>();
    	// 변수 'gitsHash' 기준으로 각 사람 별로 어떤 사람에게 몇 개의 선물을 주었는지 개수를 저장 하는 변수.
    	HashMap<String, int []> giftZisu = new HashMap<String, int[]>();
    	// 변수 'giftIndex' 기준으로 각 사람 별로 준 선물, 받은 선물, 선물 지수 개수를 저장하는 변수.
    	HashMap<String, Integer> nextMonthCount = new HashMap<String, Integer>();
    	
    	/* 변수 'giftZisu' 초기화 */
    	for(String friend : friends) {
    		
    		int [] a = new int [3];
    		giftZisu.put(friend, a);
    	}
    	
    	/* 변수 'nextMonthCount' 초기화 */
    	for(String friend : friends) {
    		
    		nextMonthCount.put(friend, 0);
    	}
    	
    	/* 사람 목록 변수 'gifts' 에서 선물 준 사람 별로 어떤 사람이 선물을 받았는 지 배열로 정리하여 'giftHash' 내 저장 작업. */
    	for(int i = 0; i < gifts.length; i = i + 2) {
    		
    		if(giftsHash.get(gifts[i]) == null) {	  // 동일한 키 값(동일한 선물 준 사람 이름)이 여러 개 생기면 안됨.
    			
    			giftsHash.put(gifts[i], new ArrayList<String>());
    			giftsHash.get(gifts[i]).add(gifts[i+1]);
    			
    		} else {	// 이미 등록된 동일 키(선물 준 사람 이름)가 존재 시 해당 HashMap 배열 내 선물 받은 사람 이름 목록 추가.
    			
    			giftsHash.get(gifts[i]).add(gifts[i+1]);
    		}
    	}
    	
    	
    	/* == 디버깅 : 선물 주고 받은 내역 잘 저장 했는 지 확인. == */
    	System.out.println("== 선물 준 내역 확인 : giftsHash == ");
    	for (String key : giftsHash.keySet())
    	    System.out.println("선물 제공 자 : " + key + ", 선물 제공 받은 사람들 이름: " + giftsHash.get(key));
        System.out.println();
    	
        
    	/* giftIndex : 현재 키와 값에 대한 각각의 사용자에 대해서 선물한 개수 세서 저장하기 */
        
    	HashMap<String, Integer> check;
    	
    	for(String friend : giftsHash.keySet()) {	// 변수 'gitHash' 내 키(선물 제공자 이름)을 기준으로 각 사용자 별 선물 받은 횟수 기록. 
    		
    		giftIndex.put(friend, new ArrayList<friendInfoVO>());		// 각 사용자를 키값으로 하여 사용자 별 어떠한 사용자에게 몇개의 선물을 주었는지 측정하기 위함.
    		check = new HashMap<String, Integer>();		// 변수 'giftIndex' 키(이미 추가한 선물 제공자 이름)를 저장하여 중복된 키 값 생성 방지.
    		
    		/* 'giftHash' 변수 내 */
    		for(int i = 0; i < giftsHash.get(friend).size() ; i ++) {   // 각 키 내 배열(선물 받은 사람 목록) 크기 만큼 반복.		
    			
    			/* 본격 적인 개수 세기 */
    			if(check.get(giftsHash.get(friend).get(i)) == null) {	// 키(선물 제공자 이름) 기준, 이미 지정된 인덱스 위치에 대한 카운팅이 안 되었을 시 추가적인 카운팅 작업 실행.
    				
    				friendInfoVO friendvo = new friendInfoVO();
        			
    				friendvo.setgiftsname(giftsHash.get(friend).get(i)); // vo 객체 이름 저장하기 위한 "name" 매개변수
        			friendvo.setgiftsGiven(1);	// vo 객체 내 각 사용자에 대해서 선물 받은 횟수 저장하기 위한 "given" 매개변수
        			
        			giftIndex.get(friend).add(friendvo);		// 현재 선물 제공자이름을 키 값으로 한 arraylist 추가
        			
        			check.put(giftsHash.get(friend).get(i), i);	// 현재 선묿 받은 사용자 정보(이름. 받은 횟수)인 arraylist 내 vo 객체와 그에 따른 인덱스를 저장.
        			
        			/* 지수 계산 작업 : 준 선물, 받은 선물, 선물 지수 계산 */
        			giftZisu.get(friend)[0] += 1; 	// '준 선물' 개수 추가, 현재 선물 제공자
        			giftZisu.get(giftsHash.get(friend).get(i))[1] += 1; // '받은 선물' 개수 추가, 선물 받은 사람.
        			
    			} else {
    				
    				giftIndex.get(friend).get(check.get(giftsHash.get(friend).get(i))).setgiftsGiven(1);
    				
    				/* 지수 계산 작업 : 준 선물, 받은 선물, 선물 지수 계산 */
        			giftZisu.get(friend)[0] += 1; 	// '준 선물' 개수 추가, 현재 선물 제공자
        			giftZisu.get(giftsHash.get(friend).get(i))[1] += 1; // '받은 선물' 개수 추가, 선물 받은 사람.
    			}
    		}
    	}
    	    	
    	/* 디버깅 : 선물 주고 받은 횟수 확인. */
    	System.out.println("== 각 사람 별로 누구에게 몇 개 주었는지 파악 : giftIndex ==");
    	for(String friend : giftIndex.keySet()) {
    		
    		System.out.print("[선물 준 사람 이름] " + friend + "\n");
    		for(int i = 0; i < giftIndex.get(friend).size() ; i++) {
    			
    			System.out.print("받은 사람 이름 : " + giftIndex.get(friend).get(i).getgiftsname() + ", ");
    			System.out.println("받은 선물 횟수 : " + giftIndex.get(friend).get(i).getgiftsGiven() + ", ");
    		}
    		
    		System.out.println();
    	}
    	System.out.println();
    	
    	/* 지수 값 출력 : giftZisu */
    	System.out.println("== 각 사람 별로 준 선물, 받은 선물, 선물 지수 : giftZisu ==");
    	for(String friend : friends) {
    		
    		giftZisu.get(friend)[2] = giftZisu.get(friend)[0] - giftZisu.get(friend)[1];
    		System.out.println("사용자 명 : " + friend + ", 준 선물 : " + giftZisu.get(friend)[0] +
    				", 받은 선물 : " + giftZisu.get(friend)[1] + ", 선물 지수 : " + giftZisu.get(friend)[2]);
    	}
    	System.out.println();
    	
    	/* 모든 사용자에 대해 선물 주고 받은 횟수 또는 지수 기준으로 다음 선물 받을 개수 포함한 최종 선물 개수 산정.
    	 * 1. 변수 'giftIndex' 참고하여 각 사용자 간 비교 후 다음 달 추가 수령 따른 총 집계
    	 * 2. 만약 비교 대상 사용자들 간 선물 주고 받은 내역이 없거나 주고 받은 수가 같다면 지수가 더 큰 사람이 선물 추가로 받는다.
    	 * */
    	
    	// 전체 사람 목록을 순차적으로 선택 후 이를 기준점으로 반복하여 현재 위치와 다음 위치의 사람을 기준으로 반복 비교한다.
    	for(int i = 0; i < friends.length - 1; i++) {		// 마지막 인덱스 사람 제외한 나머지 사람 기준으로 탐색.
    		
			int friend1 = 0; // 각 비교 시점에서 비교 대상 중 첫번 째 인덱스 위치의 사람이 두 번째 인덱스 사람에 대해서 두 번째 선물을 준 전이 있으면 1 이상 저장 됨.
			int friend2 = 0; // 각 비교 시점에서 비교 대상 중 두번 째 인덱스 위치의 사람이 첫 번째 인덱스 사람에 대해서 첫 번째 선물을 준 전이 있으면 1 이상 저장 됨.
			
			/* ===== 본격적인 시작, 각 friends 배열 내 각 사람이 다른 사람들과 비교하면서(2중 반복 문 사용) 다음 달 선물 하나 더 받을 사람 지목. ==== */
			
			for(int j = i + 1; j < friends.length ; j++) {
				
				// 현재 i 번째 인덱스에 지목된 사용자가 실제로 선물을 준 적이 있다면 내역 중 j 인덱스의 사람에게 선물 준 적이 있는지 탐색, 없다면 바로 j 인덱스에 해당하는 사람 탐색.
				if(giftIndex.get(friends[i]) != null) {		// 해당 i 인덱스는 j 가 증가하여도 변함 없음.
						
						/* 현재 인덱스 사람과 그 이후의 인덱스들에 해당하는 사람들을 순차적으로 반복하며 선물을 추가적으로 받는 사람 지목. */
						for(int k = 0; k < giftIndex.get(friends[i]).size() ; k++) {	// 현재 i 인덱스 사용자 내 선물 제공 내역을 확인 후 이를 변수 'friend1' 내 반영, 및 이후 선물 제공 개수 비교 연산에 사용.
							
							if(giftIndex.get(friends[i]).get(k).getgiftsname().equals(friends[j]))
								friend1 += giftIndex.get(friends[i]).get(k).getgiftsGiven(); 
					}
				}
				
				
				/* i+1 인덱스 사용자 기준으로 본인이 i 인덱스 위치의 사용자에게 선물을 주었는지, 즉 위 과정을 i+1 기준으로 i와 비교하기 위한 변수 friend2 저장. */
				if(giftIndex.get(friends[j]) != null) {
						
						/* 현재 인덱스 사람과 그 이후의 인덱스들에 해당하는 사람들을 순차적으로 반복하며 선물을 추가적으로 받는 사람 지목. */
						for(int k = 0; k < giftIndex.get(friends[j]).size() ; k++) {	// 현재 i 인덱스 사용자 내 선물 제공 내역을 확인 후 이를 변수 'friend1' 내 반영, 및 이후 선물 제공 개수 비교 연산에 사용.
							
							if(giftIndex.get(friends[j]).get(k).getgiftsname().equals(friends[i]))
								friend2 += giftIndex.get(friends[j]).get(k).getgiftsGiven();
					}
				}
			
				/* 다음 달 i 사용자과 i+1 사용자 간 둘 중 하나라도 서로에게 선물을 준 횟수가 있는, 즉 선물 주고 받은 적이 있거나 서로 동등한 개수로 선물을 주고 받지 '않았'음 경우
				 * 서로의 선물 주고 받은 개수를 비교하여 더 많이 선물 준 자에게 다음 달 선물 개수 1 추가. */
				if((friend1 != 0 || friend2 != 0) && (friend1 != friend2)) {
					
					int tempCount = 0;
					
					if(friend1 > friend2) {
						
						tempCount = nextMonthCount.get(friends[i]);
						nextMonthCount.put(friends[i], tempCount + 1);
					} else if((friend1 < friend2)){
						
						tempCount = nextMonthCount.get(friends[j]);
						nextMonthCount.put(friends[j], tempCount + 1);
					}
				}else {	/* 서로 간 선물을 주고 받은 적이 없거나 주고 받은 선물 개수가 동등할 경우 지수가 더 높은 측이 다음 달 선물 개수 1 추가. */
					
					int tempCount = 0;

					if(giftZisu.get(friends[i])[2] > giftZisu.get(friends[j])[2]) {
						
						tempCount = nextMonthCount.get(friends[i]);
						nextMonthCount.put(friends[i], tempCount + 1);
						
					} else if(giftZisu.get(friends[i])[2] < giftZisu.get(friends[j])[2]) {
						
						tempCount = nextMonthCount.get(friends[j]);
						nextMonthCount.put(friends[j], tempCount +1);
					}
						
				}
				
				/* 횟수 초기화 */
	    		friend1 = 0;
	    		friend2 = 0;		
			}
    	}
    	
    	/* 두 사람 간 선물 주고 받은 개수 자체가 없거나 두 사람 간 선물 주가 받은 개수가 동일하다면 선물 지수가 높은 쪽이 가져감. */
    	answer = nextMonthCount.get(friends[0]);
    	
		System.out.println("최종 결과 순위 ");
		for(int i = 0; i < nextMonthCount.size(); i++) {
			
			if(nextMonthCount.get(friends[i]) > answer)
				answer = nextMonthCount.get(friends[i]);
				
			System.out.println("이름 : " + friends[i] + ", 다음 달 선물 받은 선물 개수 : " + nextMonthCount.get(friends[i]));
		}
		
        return answer;
    }
    
    private static final int MAX_FRIENDS = 5; // 친구 수 최대값
    private static final int MAX_GIFTS = 8; // 선물 기록 최대값
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
    	
    	String [] friends = {"muzi", "ryan", "frodo", "neo"};
    	String [] gifts = {"muzi", "frodo", "muzi", "frodo", "ryan", "muzi", "ryan", "muzi", "ryan", "muzi", "frodo", "muzi", "frodo", "ryan", "neo", "muzi"};
    	
//    	String [] friends = {"joy", "brad", "alessandro", "conan", "david"};
//    	String [] gifts = {"alessandro", "brad", "alessandro", "joy", "alessandro", "conan", "david", "alessandro", "alessandro", "david"};
    	
//    	String [] friends = {"a", "b", "c"};
//    	String [] gifts = {"a", "b", "b", "a", "c", "a", "a", "c", "a", "c", "c", "a"};
    	
    	System.out.print("friends : ");
    	
    	for(String friend : friends)
    		System.out.print(friend + ", ");
    	
    	System.out.println();
    	
    	System.out.print("gifts : ");
    	for(String gift : gifts)
    		System.out.print(gift + ", ");
    	System.out.println("\n");
    	
    	System.out.println("gifts : " + gifts.toString());
    	
    	// 본격적인 로직 시작!
    	int answer = solution(friends, gifts);
    	System.out.println("\n개수는 ? : " + answer);
    	
    	
//        // 친구들의 이름을 담을 배열 생성
//        String[] friends = generateRandomFriends(MAX_FRIENDS);
//        // 선물 기록을 담을 배열 생성
//        String[] gifts = generateRandomGifts(friends, MAX_GIFTS);
//
//        // 결과 출력
//        System.out.println("입력된 사람들 명 : " + arrayToString(friends));
//        System.out.println("선물 주고 받는 관계 : " + arrayToString(gifts));
//        System.out.println("");
//        
//        // 본격적인 로직 시작!
//        int answer = solution(friends, gifts);
//        System.out.println("\n개수는 ? : " + answer);
    }

    private static String[] generateRandomFriends(int maxFriends) {
        Set<String> names = new HashSet<>();
        while (names.size() < maxFriends) {
            names.add(generateRandomString(1, 10));
        }
        return names.toArray(new String[0]);
    }

    private static String[] generateRandomGifts(String[] friends, int maxGifts) {
        ArrayList<String> giftsList = new ArrayList<>();
        for (int i = 0; i < maxGifts; i++) {
            int giverIndex = ThreadLocalRandom.current().nextInt(friends.length);
            int receiverIndex;
            do {
                receiverIndex = ThreadLocalRandom.current().nextInt(friends.length);
            } while (giverIndex == receiverIndex);
            giftsList.add(friends[giverIndex]);
            giftsList.add(friends[receiverIndex]);
        }
        return giftsList.toArray(new String[0]);
    }

    private static String generateRandomString(int minLength, int maxLength) {
        int length = ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(ThreadLocalRandom.current().nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    private static String arrayToString(String[] array) {
        return "[" + String.join(", ", array) + "]";
    }
}
