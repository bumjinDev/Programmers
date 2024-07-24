package LevelTwo.OlioildrillingIng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/* [PCCP 기출문제] 2번 / 석유 시추 */

/* 석유 탐색 알고리즘 개요:
*
* 1. 탐색 진행 :
*    - 각 열의 첫 번째 행 방향으로 한 칸 한칸 석유 탐색 진행, 탐색 완료한 배열 위치는 "searchComplete"에 탐색한 인덱스를 기록해 중복 탐색을 방지합니다.('HashMap<Integer, ArrayList<Integer>>'(<행 번호, 열 번호>) 형태로 저장.)
*    - 이미 탐색된 인덱스는 건너뛰고, 모든 행을 탐색한 후에는 다음 열로 이동합니다.
*
* 2. 방향별 탐색:
*    - 탐색하는 각 배열 내 위치 마다 정의된 방향(변수 "dirName"와 "dirIndex", 하 -> 우 -> 상 -> 좌 순 방향 탐색)에 따라 석유를 탐색합니다.
*    - 현재 탐색 기준이 되는 인덱스에서 연속적으로 연결되어 있는 인덱스를 점진적으로 탐색하면서 연속적으로 인접한 배열 위치에 석유가 없거나, 경계를 벗어나거나, 이미 탐색된 인덱스를 발견한 경우 현재 탐색 중인 해당 방향에 대한 탐색 종료
* 
* 3. 탐색 기록:
*    - 석유가 있는 인덱스를 발견하면, 현재 그룹으로써 저장합니다, 현재 그룹은 연속적으로 인접한 석유 위치를 발견한 모든 배열 위치를 저장한 것.(HashMap<Integer, ArrayList<int[2]>, Integer : 현재 그룹을 번호로 식별, ArrayList<> : 배열 저장)
*    - 현재 연속적 배열들 내 석유를 발견 시 단순 ArrayList 내 연속적으로 저장하여 '2번 방향별 탐색' 내 지정된 기준대로 탐색을 완료할 시 연속적으로 인접된 석유를 모두 찾기 위해 해당 ArrayList 내 포함된 모든 인덱스에 대해 위 과정을 반복.
*
* 4. 전체 탐색:
*    - 현재 열 내 다음 인덱스 행을 찾아가면서 현재 까지의 과정을 반복하며 현재 행 내 모든 열에 대해 모든 탐색을 완료 했을 시 다음 열로 넘어간다.
*    
* 5. 시추관 꼽는 위치 정하기 : 
*    - 모든 열에 대한 탐색을 마친 후 'oliGroups' 내 저장된 모든 각 배열 크기를 확인.
*    - 각 배열 중 가능 큰 배열 내 포함된 첫번 째 위치의 
*/


public class Solution {

	static HashMap<Integer, ArrayList<Integer>> searchCom = new HashMap<Integer, ArrayList<Integer>>();		/* 이미 찾은 목록 자체는 각 행별 열번호 순서대로 진행. */
	
	public static class obj{
		
		int i;
		int j;
		
		public obj(int i, int j) {
			
			this.i = i;
			this.j = j;
		}
		
		public int getI() {
			return this.i;
		}
		
		public int getJ() {
			return this.j;
		}
	}
	
	static public int Olioildrilling(int [][] land) {
		
		int answer = 0;
		
		/* 탐색 순서 : 하 -> 우 -> 상 -> 좌, 각 탐색 방향에 따라 진행하기 위해 인덱스 배열인 "dirName"과 "dirIndex"을 혼용. */
		String [] dirction = {"i", "j", "i", "j"};	/* 하 -> 우 -> 상 -> 좌 순서대로 진행하기 위한 인덱스 */
		int [] coordinates = {1, 1, -1, -1};			// 순서대로 '하' 와 '우' 방향은 + 인덱스, '상' 과 '하' 방향은 - 인덱스
		
		ArrayList<obj> findsArray;	// 매 oilGroups 번호 별(행벌) 탐색 시 발견된 그룹 내 배열 목록들을 저장, 이후 해당 배열 내 포함된 석유가 포함된 배열들에 대한 재 탐색 진행.
		HashMap<obj, ArrayList<obj>> oilGroups = new HashMap <obj, ArrayList<obj>>();	// 각 번보흘 지정해 주어지는 오일 그룹 넘버링에 따른 실제 오일 그룹 식별.

		// 탐색 과정 중에서 이미 완료한 인덱스에 대해서는 중복적인 탐색을 하지 않기 위해 이미 탐색한 인덱스에 대해서 각 행 - 열로 저장. 
		
		/* 탐색 범위는 주어진 2차원 배열 'land' 전체이며 각 열 번호에 대한 행을 순차적으로 확인한다, 즉 열 번호 별 행번호를 1개씩 늘려가며 탐색한다.(열 -> 행) */
		for(int j = 0; j < land[0].length; j++) {	// 각 열을 0 부터 순차적으로 탐색
			for(int i = 0; i < land.length ; i++ ) {	// 지정된 열 내 행을 순차적으로 탐색.
				
				System.out.println("if(land[" + i + "][" + j +"]) : " + land[i][j]);
				
				if(land[i][j] == 1) {		// 석유가 발견될 시 위 로직 실행
					
					int check = 1;
					
					/*
					 * 1. 현재 인덱스에서 석유 확인하면 현재의 행/열 번호를 oilGroups의 번호로 매개서 그룹 생성.
					 * 2. 'dirction' 과 'coordinates' 크기 만큼 반복하며  하 -> 우 -> 상 -> 좌 순서대로 인덱스를 점진적으로 탐색(반복문)하면서 연속적으로 인접한 배열 위치에 석유가 없거나, 경계를 벗어나거나, 이미 탐색된 인덱스를 발견한 경우 현재 탐색 중인 해당 방향에 대한 탐색 종료.
					 * 3. 각 인덱스 별 탐색 과정 중에 연속적으로 석유가 확인된 배열들을 'findsArray', 'searchComplete', 내 저장.
					 * 4. 2번 과정 중에 더이상 발견할 내용이 없으면 해당 반복문 밑 'if(land[i][j] == 1)' 종료되어 해당 열 또는 다음 열 내 행들을 순차적으로 반복하면서 위 과정 재 실행.
					* */
					
					/* 그룹 생성하기 전 확인 과정으로 'oilGroups' 내 모든 키 값(new obj(i, j)을 꺼내가며 내부 인덱스 i 와 j 가 이미 탐색한 'searchCom' 내 포함되어 있다면 즉시 해당 인덱스에 대한 반복문을 빠져 나간다. */
					Set<obj> keys = oilGroups.keySet();
					for (obj key : keys)
						for(int k = 0; k < searchCom.get(key.getI()).size(); k++ )
							if(searchCom.get(key.getI()).get(k) == j)
								check = 0;
					
					System.out.println("check : " + check + "\n");
					
					if(check == 1) {
						System.out.println("디버깅 - check : " + check + ", i : " + i + ", j : " + j);
						
						findsArray = new ArrayList<obj>();	// 실제 그룹 단위인 'oilGroups' 내 데이터를 순차적으로 저장하기 위한 컬렉션.
						oilGroups.put(new obj(i, j), findsArray);	// 실제 그룹 생성.
						
						findsArray.add(new obj(i, j));				// 현재 석유를 발견 했으니 첫번째 값 입력
						
						System.out.println("\n======= oil 값 디버깅 =======");
						for(obj key : oilGroups.keySet())
							if(key.i == i)
								System.out.println("i : " + i + ", j : " + j);
							
						/* 각 i 행 별 searchCom 생성 */
						searchCom.put(i, new ArrayList<Integer>());
						searchCom.get(i).add(j);			// 현재 그룹 시작 지점 부터 이미 석유를 하나 찾은 것.
						
						ArrayList<obj> reSearch = new ArrayList<obj>();		// reSearch는 현재 그룹(oilGroups)의 위치를 기준으로 탐색된 석유 지점들을 저장한다. 첫 번째 값은 제외하고, 이 리스트에 저장된 석유가 있는 위치들에 대해 추가적으로 모든 방향을 탐색하여, 그 결과를 'findsArray’에 저장한다.
						
						for(int k = 0; k < dirction.length; k++) {		// 각 열별 행 위치 증가하면서 본격적인 각 탐색 시작, 2번 과정 실행
							
							/* '각 탐색 방향 별 별' 탐색 범위 지정 : 현재 인덱스로부터 OutBoundException 발생 하지 않게 하기 위함. */
							
							/* 탐색 방향 지정 : 'ChoiceDir()' 호출, 배열 'dirction', 'coordinates' 따라 방향 선택.
							 * 이후 각 방향 별 탐색 시 findsArray 내 저장하고 및 reSeachList 내 확인된 내용들에 대해 반복하면서 저장 한다 또한 동시에 'searchComplete' 내 저장하여 이미 탐색한 배열에 대해서는 탐색하지 않는다.  
							*/
							
							String dir = Solution.ChoiceDir(dirction[k], coordinates[k]);
							
							System.out.println("direction : " +  dir);
							Solution.OilSearchFunc(dir, findsArray, i, j, land, reSearch);
						}
						
						/* 현재 그룹의 시작 행 인덱스에 대한 모든 방향의 탐색이 완료되었지만, 시작 행 인덱스를 제외한 각각의 탐색이 완료된 석유가 있는 배열들에 대해서는 추가적인 석유 탐사가 필요함. */
						while(reSearch.size() > 0) {		// 첫 사이클에서 reSearch를 저장했다고 해도 내부 반복문에서 추가적으로 가져왔다면 추가적으로 쌓이니 이애 따라 길이 확인해서 재 반복
							
							System.out.println();
							System.out.println("reSearch 실행 시작!, reSearch.size() : " + reSearch.size());
							
							for(int s = reSearch.size() - 1; s >= 0; s--) {			// 'reSearch' 는 호출된 함수 'OilSearchFunc' 내에서 이미 찾은 오일 위치들에 대한 인덱스 값들을 이미 저장.
								for(int k = 0; k < dirction.length ; k++) {
									System.out.println("디버깅 - reSearch.get(s).getI() : " + reSearch.get(s).getI() + ", reSearch.get(s).getJ() : " + reSearch.get(s).getJ());
									Solution.OilSearchFunc(Solution.ChoiceDir(dirction[k], coordinates[k]), findsArray, reSearch.get(s).getI() , reSearch.get(s).getJ(), land, reSearch);
								}
								reSearch.remove(s);		// 이미 한번 탐색을 완료한 것은 삭제
							}
						}
						
						System.out.println("\n============");
						System.out.println("== 현재 oilgroup [" + i +"][" + j +"] ==");
						
						for (obj key : keys) 
							if(key.i == i && key.j == j) {
								System.out.println("현재 oilGroup - i : " + i + ", j : " + j);
								for(int e = 0; e < oilGroups.get(key).size(); e++)
									System.out.println("그룹원 : \n i : " + oilGroups.get(key).get(e).i + ", j : " + oilGroups.get(key).get(e).i);
							}
						}
				}
			}
		}
		
		System.out.println("\n============");
		System.out.println("== 결과 확인 ==");
		
		Set<obj> keys = oilGroups.keySet();
		for (obj key : keys) {
		    
			System.out.println();
			System.out.println("그룹 명(인덱스) - i : " + key.getI() + ", j : " + key.getJ()  + ", oilGroups.keySet().size() : " + oilGroups.keySet().size());
//			System.out.println("그룹 내 인덱스들 : ");
//			
//			for(int i = 0; i < oilGroups.get(key).size(); i++)
//				System.out.print("i : " + oilGroups.get(key).get(i).getI() + ", j : " + oilGroups.get(key).get(i).getJ() + "\n");
		}
		
		return answer;
	}
	
	/* 실제 각 그룹 별 탐색하는 함수, 매개변수로 방향, 그룹 내 저장되는 ArrayList 컬렉션, searchComplite 저장, 반환 값은 따로 없다. */
	public static void OilSearchFunc(String dirction, ArrayList<obj> findsArray, int i, int j, int [][] land, ArrayList<obj> reSearch) {
		
		System.out.println("\n== OilSearchFunc 실행 - " + dirction + " ==");
		
		/* i2 는 searchCheck 내 현재 확인하는 것이 이미 탐색 완료된 인덱스인지 검사할 때에 현재 검사 시작지점인 oilGroups 내 그룹 구문 목적 인덱스라면 해당 인덱스만 예외로 처리하기 위해*/
		int i2 = i;
		int j2 = j;
		
		int iIndex = 0;
		int jIndex = 0;
		
		if(dirction.equals("S"))
			iIndex = +1;
		else if(dirction.equals("N"))
			iIndex = -1;
		if(dirction.equals("E"))
			jIndex = +1;
		else if(dirction.equals("W"))
			jIndex = -1;
		
		/* 본격적인 탐색 로직 시작, 
		 * 1. 'dirction' 내 지시된 방향에 따라 탐색된 때 석유가 발견된 인덱스는 `findsArray`에 저장된다. 함수가 종료될 때, 이 `findsArray`는 호출한 메소드의 'oilGroups'에 자동으로 반영.
		 * 2. 석유가 발견된 인덱스는 `findsArray`뿐만 아니라 `reSearchList` 배열에도 추가됩니다. 함수 호출 시 'direction'에 따른 탐색이 모두 완료되면, 해당 방향에서 발견된 석유들에 대해 추가적인 반복문을 실행하여 "모든 방향에서" 석유를 점진적으로 탐색합니다.
		 * 3. 2번 과정 수행 결과로 탐색된 모든 인덱스 또한 'findsArray' 내 저장.
	    */
		
		/* 탐색 방향에 따라 분기적으로 탐색 진행, 이유는 인덱스인 i와 j의 방향을 구분해야 되기 때문 */
		
		if(dirction.equals("S") || dirction.equals("N")) {	// 남쪽 또는 북쪽 탐색 순서라면 인덱스 i를 기준으로 탐색
			
			for(; i >= 0 && i < land.length ; i += iIndex) { // 지정된 인덱스를 + 또는 - 방향으로 변하면서 찾아 갈 때에 두 개로 분기하지 않기 위한 반복문 내 조건 식, 해당 인덱스 내 석유가 존재 시 추가적으로 점진적 석유 탐색 진행하며 없거나 혹은 이미 한번 탐색한 위치의 인덱스라면 해당 방향에 대한 탐색 종료.
				
				int searchCheck = 1;
				if(searchCom.get(i) == null)	// 그룹 이름에 해당하는 인덱스 말고도 i 방향으로 한칸씩 내려가기 때문에 추가를 해야 됨.
					searchCom.put(i, new ArrayList<Integer>());
				
				for(int s = 0; s < searchCom.get(i).size(); s++)
					if(searchCom.get(i).get(s) == j)
						searchCheck = 0;	
				
				System.out.println("디버깅 - dirction : " + dirction + ", land[" + i +"][" + j + "] : " + (land[i][j]) + ", searchCheck : " + searchCheck);
				
				if(land[i][j] == 1 && searchCheck == 1) {
					
					findsArray.add(new obj(i, j));		// 현 그룹 번호에 해당하는 'oilGroups'의 데이터를 저장하는 'findsArray' 내 데이터를 추가한다.
					searchCom.get(i).add(j);    // 중복 탐색 하지 않기 위한 저장.
					reSearch.add(new obj(i, j));
					System.out.println("저장되는 인덱스 - i : " + i + ", j : " + j);
				} else if(i != i2)			// 더 이상 연속적으로 탐색이 안된다면 해당 방향은 더 이상 탐색 종료, 다만 탐색 시작 지점인 oilGroups 와 동일한 인덱스라면 계속 진행.
					break;
			}
			
		} else if(dirction.equals("E") || dirction.equals("W")) {	// 동쪽 또는 서쪽 탐색 순서라면 인덱스 j를 기준으로 탐색
			
			for(; j >= 0 && j < land[0].length ; j += jIndex) { // 지정된 인덱스를 + 또는 - 방향으로 변하면서 찾아 갈 때에 두 개로 분기하지 않기 위한 반복문 내 조건 식 
				// 해당 인덱스 내 석유가 존재 시 추가적으로 점진적 석유 탐색 진행하며 없거나 혹은 이미 한번 탐색한 위치의 인덱스라면 해당 방향에 대한 탐색 종료.
				int searchCheck = 1;
				if(searchCom.get(i) == null)
					searchCom.put(i, new ArrayList<Integer>());
				
				for(int s = 0; s < searchCom.get(i).size(); s++)
					if(searchCom.get(i).get(s) == j)
						searchCheck = 0;
				
				System.out.println("디버깅 - dirction : " + dirction + ", land[" + i +"][" + j + "] : " + (land[i][j]) + ", searchCheck : " + searchCheck);
				
				if(land[i][j] == 1  && searchCheck == 1) {
					
					findsArray.add(new obj(i, j));
					searchCom.get(i).add(j);    			// 중복 탐색 하지 않기 위한 저장.
					reSearch.add(new obj(i, j));
					System.out.println("저장되는 인덱스 - i : " + i + ", j : " + j);
					
				} else if(j != j2)			// 더 이상 연속적으로 탐색이 안된다면 해당 방향은 더 이상 탐색 종료, 다만 탐색 시작 지점인 oilGroups 와 동일한 인덱스라면 계속 진행.
					break;
			}
		}
		System.out.println();
	}
	
	/* 탐색 방향 지침 */
	public static String ChoiceDir(String dir, int coord) {
		
		if(dir.equals("i") && coord == 1)	// 남쪽
			return "S";
		
		if(dir.equals("j") && coord == 1)	// 동쪽
			return "E";
		
		if(dir.equals("i") && coord == -1) // 남쪽
			return "N";
		
		if(dir.equals("j") && coord == -1) // 서쪽
			return "W";
		
		return "";
	}
	
	
	
	public static void main(String[] args) {
		// n과 m의 최소 크기를 정의합니다.
        final int MIN_N = 5;
        final int MIN_M = 5;

        // n과 m의 최대 크기를 정의합니다.
        final int MAX_N = 8;
        final int MAX_M = 8;

        // 랜덤 객체를 생성합니다.
        Random rand = new Random();

        // n과 m을 랜덤하게 생성합니다. (8 ≤ n, m ≤ 500)
        int n = rand.nextInt(MAX_N - MIN_N + 1) + MIN_N;
        int m = rand.nextInt(MAX_M - MIN_M + 1) + MIN_M;

        // land 배열을 생성하고 랜덤한 값(0 또는 1)으로 채웁니다.
        int[][] land = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                land[i][j] = rand.nextInt(2); // 0 또는 1
            }
        }

        // 생성된 land 배열을 출력합니다.
        System.out.println("Generated land array (n(세로) : " + n + ", m(가로) : " + m + "):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(land[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        int answer = Olioildrilling(land);
        System.out.println("\n결과 : " + answer);
    }
}