package LevelOne.bandaging;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Solution {

	public static void main(String[] args) {
		
//		Random rand = new Random();
//		
//		// bandage 배열 생성
//		int[] bandage = new int[3];
//		bandage[0] = rand.nextInt(5) + 1; // 시전 시간
//		bandage[1] = rand.nextInt(10) + 1; // 초당 회복량
//		bandage[2] = rand.nextInt(10) + 1; // 추가 회복량
//
//		// health 생성
//		int health = rand.nextInt(30) + 1;
//
//		// attacks 배열 생성
//		int attacksLength = 5;
//		int[][] attacks = new int[attacksLength][2];
//		for (int i = 0; i < attacksLength; i++) {
//		    attacks[i][0] = rand.nextInt(11) + 1; // 공격 시간
//		    attacks[i][1] = rand.nextInt(15) + 1; // 피해량
//		}
//
//		// attacks 배열을 공격 시간을 기준으로 오름차순 정렬
//		Arrays.sort(attacks, Comparator.comparingInt(a -> a[0]));
//			
//		int answer = run(bandage, health, attacks);
//		System.out.println("\n answer : " + answer);
	
		int [] bandage = {3,2,7};
		int health = 20;
		int [][] attacks = {{1, 15}, {5, 16}, {8, 6}};
		
		int answer = run(bandage, health, attacks);
		System.out.println("\n answer : " + answer);
	}
		
		static public int run(int[] bandage, int health, int[][] attacks) {
	    	
	    	int answer = health; // 캐릭터 현재 체력(처음에는 최대 체력으로 설정)이자 반환 값
	        int healthCount = 0; // 체력 회복될 때마다 1씩 증가하여 bandage[0] 횟수 만큼 연속적으로 회복한다면 추가 회복 부여
	        int attackCount = 0; // 몬스터에게 피격 당하는 타이밍은 매 횟수(매 초) 인덱스 값으로 실행하는게 아닌 배열 내 각 요소의 타이밍으로 계산.
	        int check = 0; // 몬스터에게 피격 받는 타이밍에는 회복 불가.
	        
	        System.out.println("\n== 입력 값 확인 ==");
	        System.out.println("bandage0 : " + bandage[0] + ", bandage1 : " + bandage[1] + ", bandage2 : " + bandage[2]);
	        System.out.println("health(최대 값) : " + health + "\n");
	        
	        for(int i =0; i<attacks.length; i++) {
	        	
	        	System.out.println("attack[" + i +"] : " + attacks[i][0] + " " + attacks[i][1]);
	        }
	        System.out.println("attacks[attacks.length-1][0] : " + attacks[attacks.length-1][0]);
	        System.out.println("");
	        
	        /* == 본격적인 로직 시작 == */
	    
	       for(int i = 1; i <= attacks[attacks.length-1][0]; i++) {  		// 몬스터 피격 횟수를 기준으로 반복문 구동.
 
	        	check = 0; // 현재 타이밍(현재 초)에서 몬스터 피격 여부 체크, 몬스터 피격 당했으면 해당 타이밍에서는 회복 로직 실행 불가.
	            
	            /* 몬스터에게 피격 당하는 알고리즘 */
	            if(i == attacks[attackCount][0]) {          // 현재 반복 횟수(반복 시간 초) 타이밍이 몬스터 공격 타이밍과 일치 시 몬스터 피격 실행.
	                answer = answer - attacks[attackCount][1];      // 몬스터 피격 구현
	                attackCount += 1;                               // 몬스터 피격 후 다음 피격 시점을 인덱스 'attackCount' 설정.
	                
	                System.out.println("현재 타이밍(초) : " + i + ", 피격 직후 체력 : " + answer + ", attackCount : " + attackCount +"\n");
	                
	                if(answer <= 0) {                               // 몬스터 피격 직후 유저 체력이 0 이하면 '-1' 반환 후 게임 종료
	                    return -1;
	                }                                        
	                
	                healthCount = 0;			// 유저의 붕대감기 횟수 초기화
	                check = 1;                  // 현재 반복 횟수(반복 시간 초) 타이밍에서 피격 받았으므로 다음 회복 로직 구현 불가토록 설정.
	            }
	            
	            /* 회복 알고리즘, 현재 반복 횟수(반복 시간 초) 타이밍이 몬스터에게 피격 당한 상태라면 실행되지 않음. */
	            if(healthCount + 1 <= bandage[0] && check == 0) {            /* 회복 타이밍 및 피격 여부 확인, 회복 타이밍인 healCount로 공격 안 받은 상태가 이어질 때에서의 연속적으로 회복 가능한 횟수를
	            																조건(bandage[0]) 과 붕대 횟수를 겸한 카운트이며 +1을 한 이유는 healthCount 로 붕대 타이밍은 0부터 시작하기 때문이다.*/	     
	            	answer = Math.min(answer + bandage[1], health);         // 회복 수식, 최대 체력 이상으로 회복 되지 않음.
	            	healthCount += 1;                          // 연속 붕대 감기 횟수 증가
	                
	                if(healthCount >= bandage[0]) {            // 연속 붕대 감기 횟수가 기준치 이상일 시 추가 회복량 부여
	                
	                	answer = Math.min(answer + bandage[2], health);     // 추가 회복 알고리즘 구현
	                    healthCount = 0;                       // 연속 붕대 감기 횟수 초기화
	                }
	                
	                System.out.println("현재 타이밍(초) : " + i + ", 회복 결과 : " + answer + ", healthCount : " + healthCount + ", 현재 회복량 : "+ bandage[1] + "\n");
	            }
	        }
	        return answer;                                     // 모든 로직 종료 후 현재 캐릭터 체력 상태 반환.
	}
}
