package Programmers.LevelOne.MemoriesScore;

class Solution {
	

    public static int[] solution(String[] name, int[] yearning, String[][] photo) {
       
    	int[] answer = new int[photo.length];
        
        for(int i = 0; i<photo.length; i++) {
        	for(int j = 0; j < photo[i].length; j++) {
        		
        		int index = 0;
        		
        		for(int z = 0; z < name.length; z++) {
        			
        			if( photo[i][z].equals(name[z])) {
        				index = z;
        				break;
        			}
        		}
        	
        		System.out.println("출력할 데이터 : " + yearning[index]);
        		
        		answer[i] += yearning[index];
        		System.out.println("answer : " + answer[i]);
        	}
        }
        return answer;
    }
    
    public static void main(String[] args) {
    	
		/* 첫번재 예시 */
    	String [] name = {
    			"may", "kein", "kain", "radi" };
    	int [] yearning = {
    			5, 10, 1, 3 };
    	String [][] photo = {
    			{"may", "kein", "kain", "radi"},
    			{"may", "kein", "brin", "deny"},
    			{"kon", "kain", "may", "coni"}};
    	
    	/* 두번째 예시 */
//    	String [] name = {
//    			"kali", "mari", "don"};
//    	int [] yearning = {
//    			11, 1, 55 };
//    	String [][] photo = {
//    			{"kali", "mari", "don"},
//    			{"pony", "tom", "teddy"},
//    			{"con", "mona", "don"}};
    	
    	/* 3번째 예시 */
//    	String [] name = {
//    			"may", "kein", "kain", "radi"};
//    	int [] yearning = {
//    			5, 10, 1, 3 };
//    	String [][] photo = {
//    			{"may"},
//    			{"kein", "deny", "may"},
//    			{"kon", "coni"}};
    	
    	int [] answer = solution(name, yearning, photo);
    	
    	for(int score : answer)
    		System.out.println(score);
	}
}