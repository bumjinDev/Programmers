package LevelOne.DataAnalysis;



import java.util.HashSet;
import java.util.Random;

public class Solution {

	public static void main(String[] args) {
		
		   Random rand = new Random();

	        // ext와 sort_by의 모든 조합에 대해 테스트
	        String[] extOptions = {"code", "date", "maximum", "remain"};
	        for (String ext : extOptions) {
	            for (String sort_by : extOptions) {

	                // data 생성
	                int rows = 1 + rand.nextInt(500); // 행 수 (최소 1개, 최대 500개)
	                int[][] data = new int[rows][4];

	                HashSet<Integer> usedCodes = new HashSet<>(); // 이미 사용된 코드 번호를 추적

	                int maxMaximum = Integer.MIN_VALUE;
	                int maxRemain = Integer.MIN_VALUE;

	                for (int i = 0; i < rows; i++) {
	                    int code;
	                    do {
	                        code = 1 + rand.nextInt(100000); // 코드 번호 (1부터 100000까지)
	                    } while (usedCodes.contains(code));
	                    usedCodes.add(code);

	                    data[i][0] = code;

	                    int year = 2000 + rand.nextInt(1000); // 연도 (2000년부터 2999년까지)
	                    int month = 1 + rand.nextInt(12); // 월
	                    int day = 1 + rand.nextInt(28); // 일 (간단하게 28일까지만 고려)
	                    data[i][1] = year * 10000 + month * 100 + day; // 제조일

	                    data[i][2] = 1 + rand.nextInt(10000); // 최대 수량 (1부터 10000까지)
	                    maxMaximum = Math.max(maxMaximum, data[i][2]);

	                    data[i][3] = 1 + rand.nextInt(data[i][2]); // 현재 수량 (1부터 최대 수량까지)
	                    maxRemain = Math.max(maxRemain, data[i][3]);
	                }

	                // val_ext 생성
	                int val_ext;
	                if (ext.equals("code")) {
	                    val_ext = 1 + rand.nextInt(100000); // 1부터 100000까지
	                } else if (ext.equals("date")) {
	                    int year = 2000 + rand.nextInt(1000); // 연도 (2000년부터 2999년까지)
	                    int month = 1 + rand.nextInt(12); // 월
	                    int day = 1 + rand.nextInt(28); // 일 (간단하게 28일까지만 고려)
	                    val_ext = year * 10000 + month * 100 + day; // 날짜
	                } else if (ext.equals("maximum")) {
	                    val_ext = rand.nextInt(maxMaximum + 1); // 0부터 maximum 값까지
	                } else { // ext.equals("remain")
	                    val_ext = rand.nextInt(maxRemain + 1); // 0부터 remain 값까지
	                }

	                // 생성된 값 출력
	                System.out.println("Generated values:");
	                System.out.println("data:");
	                for (int i = 0; i < rows; i++) {
	                    System.out.println(data[i][0] + ", " + data[i][1] + ", " + data[i][2] + ", " + data[i][3]);
	                }
	                System.out.println("ext: " + ext);
	                System.out.println("val_ext: " + val_ext);
	                System.out.println("sort_by: " + sort_by);

	                // run 메소드 호출
	                int[][] result = run(data, ext, val_ext, sort_by);
	                System.out.println("\n");
            }
        }
	}
	
	
	static public int[][] run(int[][] data, String ext, int val_ext, String sort_by){
		
		int [][] answer = data;	
    	
    	/* 각 검색 기준 컬럼(ext)을 확인하여 어떤 커럼 기준으로 지정된 값(val_ext) 기준으로 고를 컬럼 선택, 이후 'searchFunc'로 분기하여 구동.*/
    	if(ext.equals("code")) {			/* 주어진 검색 조건 : "code" */
    		
    		System.out.println("choice code\n");
    		answer = searchFunc(answer, 0, val_ext, sort_by);
    		
    	} else if(ext.equals("date")) {		/* 주어진 검색 조건 : "date" */
    		
    		System.out.println("choice date\n");
    		answer = searchFunc(answer, 1, val_ext, sort_by);
    		
    	} else if(ext.equals("maximum")) {	/* 주어진 검색 조건 : "maximum" */
    		System.out.println("choice maximum\n");
    		answer = searchFunc(answer, 2, val_ext, sort_by);
    		
    	} else if(ext.equals("remain"))  {	/* 주어진 검색 조건 : "remain" */
    		System.out.println("choice remain\n");
    		answer = searchFunc(answer, 3, val_ext, sort_by);
    	}
    
    	return answer;
	}
	
	static public int[][] searchFunc(int[][] data, int index, int val_ext, String sort_by){
    	
    	int sortIndex = 0;		// 오름 차순 정렬 위한 컬럼 인덱스 지정 목적 변수.
    	int count = 0;			// 조건에 맞는 데이터를 새로운 배열 내 넣기 위한 변수.
    	
    	
    	int[][] resultData = new int[data.length][4];	// ext 내 지정된 val_ext 값 보다 작은 배열 찾아서 저장.("sort" 변수 내 지정된 컬럼 명으로 오른 차순 하기 전..)
    	
    	System.out.println("\n" + "index() : " + index + ", var_ext : " + val_ext + ", data.length : " + data.length + "\n");
    	
    	/* index 위치(컬럼) 내 값이 기준 값인 val_ext 보다 작은 값만 추리기 */
    	System.out.println("==기준 값 보다 작게 걸러진 값들 ==\n");
    	
    	for(int i = 0; i<data.length; i++) {		/* 원본 데이터 발생 행 길이 만큼 반복하면서 ext 컬럼 내 val_ext 값 기준으로 작은 행들만 재 저장. */
    		
    		if(data[i][index] < val_ext) {			/* 기준이 부합하면 data 배열 내 모든 데이터를 resultData 배열 내 재 저자 */
    			
    			resultData[count][0] = data[i][0];
    			resultData[count][1] = data[i][1];
    			resultData[count][2] = data[i][2];
    			resultData[count][3] = data[i][3];
    			
//    			System.out.println(resultData[count][0] + ", " +resultData[count][1] + ", " +resultData[count][2] + ", " +resultData[count][3]);
    			
    			count ++;
    		}
    	}
    	System.out.println("");
    	
    	/* 추려진 값들 이후의 인덱스를 제외하기 위해 사용하는 배열 "resArray" */
    	int[][] resArray = new int[count][];
    	
    	for(int i = 0; i < count; i++) {
    		
    		resArray[i] = resultData[i];
    	}
    	
    	/* 추려진 값들에 대해서 내림차순 하기 */
    	if(sort_by.equals("code")) {
    		
    		System.out.println("choice sort_by : code");
    		sortIndex = 0;
    		
    	} else if(sort_by.equals("date")) {
    		
    		System.out.println("choice sort_by : date");
    		sortIndex = 1;
    		
    	} else if(sort_by.equals("maximum")) {
    		
    		System.out.println("choice sort_by : maximum");
    		sortIndex = 2;
    		
    	} else if(sort_by.equals("remain")) {
    		
    		System.out.println("choice sort_by : remain");
    		sortIndex = 3;
    	}
    	
    	/* 정렬 알고리즘(버블 정렬) */
    	int [][] temp = new int[1][4];

    	for(int i = 0; i<resArray.length-1; i++) {
    	    for(int j = 0; j<resArray.length-1-i; j++) {
    	        if(resArray[j][sortIndex] > resArray[j+1][sortIndex]) {
//    	            System.out.println("버블 과정 : 변경 전 - resArray["+j+"] : " + resArray[j][sortIndex] + ", resArray["+(j+1)+"] : " + resArray[j+1][sortIndex]);
    	            
    	            temp[0] = resArray[j];
    	            resArray[j] = resArray[j+1];
    	            resArray[j+1] = temp[0];
    	            
//    	            System.out.println("버블 과정 : 변경 후 - resArray["+j+"] : " + resArray[j][sortIndex] + ", resArray["+(j+1)+"] : " + resArray[j+1][sortIndex] + "\n");
    	        }
    	    }
    	}


    	for(int i = 0; i<resArray.length; i++) {
    		
//    		System.out.println("resArray["+i+"] : " + resArray[i][0] + ", "  + resArray[i][1] + ", " + resArray[i][2] + ", " + resArray[i][3]);
    	}
    	
    	return resArray;
    }
}