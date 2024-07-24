package LevelTwo.MaximumMinimum;

public class Soultion {

	public static String solution(String s) {
		
        String answer = "";
        
        String [] str = s.split(" ");
        
        int [] temp = new int[str.length];
        
        int max = 0, min = 0;
        
        int i = 0;
        for(String st : str) {
        	
        	temp[i] = Integer.valueOf(st);
        	
        	if(max == 0 || max > temp[i])
        		max = temp[i];
        	
        	if(min == 0 || min < temp[i])
        		min = temp[i];
        	
        	i++;
        }
        
        answer = (String.valueOf(max)) + " " + (String.valueOf(min)); 
        
        return answer;
    }
	
	public static void main(String[] args) {
	
		String str1 = "1 2 3 4";
		String str2 = "-1 -2 -3 -4";
		String str3 = "-1 -2 -3 -4";
		
		String answer = solution(str2);
		System.out.println("답 : " + answer);
	}
}
