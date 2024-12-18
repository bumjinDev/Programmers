package Programmers.LevelOne.ApplyMorePaint;

public class ApplyMorePaint {

	static int solution(int n, int m, int section[]) { // , size_t section_len)
	    
	    int answer = 0;
	    int sectionCount = 0;
	    
	    for(int i = 1; i <= n; i ++) {
	    	System.out.println("확인 할 벽 위치 : " + i + ", 벽 전체 길이 : " + n);
	    	if(i >= section[sectionCount]) {	// 벽면 처음 위치부터 탐색하면서 비어있는 위치 확인.
	    		
	    		answer += 1;	// 칠한 횟수 1 증가.
	    		i += m - 1 ;	// 붓 길이 만큼 칠하기.
	    		sectionCount += 1;
	    		
	    		/* 현재 붓 길이 "m" 만큼 칠한 후 최종 길이인 i 내 포함되는 모든 빈 공간에 대해서는 더 이상 확인하지 않기 위함..*/
	    		for(; sectionCount < section.length ; sectionCount ++ ) {
	    			if(section[sectionCount] > i)
	    				break;
	    		}
	    		
	    		if(sectionCount >= section.length)
	    			break;
	    	}
	    	System.out.println("칠한 범위 : " + i );
	    }
	    return answer;
	}
	
	public static void main(String[] args) {

		System.out.println(solution(5, 4, new int[]{ 1, 3}));
//		System.out.println(solution(4, 1, new int[]{ 1,2, 3, 4 }));
	}
}
