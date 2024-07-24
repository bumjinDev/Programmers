package LevelTwo.correctparentheses;

public class Solution {

	public  static boolean solution(String s) {

        /* 풀이 내용 :
         * 1. ")" 패턴이 맨 처음 나오면 false 반환 
         * 2. "(" 패턴 개수 및 ")" 패턴 개수가 다르다면 false 반환 
         * 3. ")* 패턴 발견 시 "( - )" 의 결과가 - 단위 일 시 false */
        
        String [] str = s.split("");
        
        int open = 0;
        int close = 0;
        
        for(String st : str) {
        	
        	if(st.equals("("))
        		open += 1;
        	if(st.equals(")")) {
        		close += 1;
        		
        		if(open - close < 0)	/* ")" 패턴 시 이전 "(" 과 비교해서 "(" 개수 - ")" 개수 의 결과로 0 개 "미만" 일 때 false 반환 */
        			return false;
        	}
        }
        
        if(str[0].equals(")") || (open != close))	// ")" 이 먼저 발견되거나 "(" 과 ")" 개수가 동일하지 않으면 false
        	return false;
        
        return true;
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String str1 = "()()";
		String str2 = "(())()";
		String str3 = ")()(";
		String str4 = "(()(";
		
		boolean answer = solution(str4);
		System.out.println("정답 : " + answer);
	}

}
