package Programmers.LevelOne.GymClothes;

import java.util.*;

class Solution {
    public static int solution(int n, int[] lost, int[] reserve) {
        
    	// Set으로 변환하여 중복 제거
        Set<Integer> lostSet = new HashSet<>(Arrays.asList(Arrays.stream(lost).boxed().toArray(Integer[]::new)));
        Set<Integer> reserveSet = new HashSet<>(Arrays.asList(Arrays.stream(reserve).boxed().toArray(Integer[]::new)));
        
        // 중복 제거
        Set<Integer> common = new HashSet<>(lostSet);
        common.retainAll(reserveSet); // 공통 요소 추출
        lostSet.removeAll(common);    // lostSet에서 공통 요소 제거
        reserveSet.removeAll(common); // reserveSet에서 공통 요소 제거
        
        int answer = n - lostSet.size(); // 기본적으로 체육복을 가진 학생 수
        
        // 체육복 빌려주기
        for (int l : lostSet) {
            if (reserveSet.contains(l - 1)) {
                reserveSet.remove(l - 1);
                answer++;
            } else if (reserveSet.contains(l + 1)) {
                reserveSet.remove(l + 1);
                answer++;
            }
        }
        
        return answer;
    }
    
public static void main(String[] args) {
    	

    	
    	/* 문제 1 */
//    	int n = 5;
//    	int [] lost = {2, 4};
//    	int [] reserve = {1, 3, 5};
    	
    	/* 문제 2 */
    	int n = 5;
    	int [] lost = {2, 4};
    	int [] reserve = {3};

    	/* 문제 3 */
//    	int n = 3;
//    	int [] lost = {3};
//    	int [] reserve = {1};
    	
    	/* 극단적 케이스 : 모두 분실하몄으면서 여무도 여분을 갖고 있지 않음. */
//    	int n = 5;
//    	int[] lost = {1, 2, 3, 4, 5};
//    	int[] reserve = {};
    	
    	/* 일부 lost 학생이 체육복을 빌릴 수 없는 상황 */   
//    	int n = 5;
//    	int[] lost = {2, 4};
//    	int[] reserve = {3};
    	
    	/* 학생이 2명뿐인 경우, 한 명이 체육복을 빌려줌 */   
//    	int n = 2;
//    	int[] lost = {1};
//    	int[] reserve = {2};
    	
    	/* 학생이 30명이고 절반은 체육복을 분실했으며 절반은 여벌을 가지고 있음. */
//    	int n = 30;
//    	int[] lost = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29};
//    	int[] reserve = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30};

    	
    	System.out.println("return : " + solution(n, lost, reserve));
	}
}
