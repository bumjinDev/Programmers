package LevelOne.WalkInThePark.Edit;

import java.util.HashMap;
import java.util.Random;

class Solution {
    
    public static class Pair {
        private String dir; // 방향 지정, E, S..
        private int dist; // 거리 지정, 1 or -1;

        public Pair(String dir, int dist) {
            this.dir = dir;
            this.dist = dist;
        }

        public Pair() {};

        public String getdir() {
            return this.dir;
        }

        public int getdist() {
            return this.dist;
        }
    }

    public static int[] run(String[] park, String[] routes) {
    	
        if (park == null || routes == null) {
            throw new IllegalArgumentException("park and routes must not be null");
        }

        int[] answer = new int[2];
        String[][] parkArray = new String[park.length][];
        String[][] routesArray = new String[routes.length][];

        // 배열 'park' 전처리
        for (int i = 0; i < park.length; i++)
            parkArray[i] = park[i].split("");

        // 배열 'routes' 전처리
        for (int i = 0; i < routes.length; i++)
            routesArray[i] = routes[i].split(" ");

        System.out.println("== 전처리 데이터 확인 == \n");

        System.out.println();

        System.out.println("배열 'routesArray' 확인");
        for (int i = 0; i < routesArray.length; i++) {
            for (int j = 0; j < routesArray[i].length; j++)
                System.out.print("routesArray[" + i + "][" + j + "] : " + routesArray[i][j] + ", ");
            System.out.println();
        }

        System.out.println("\n== 전처리 끝 ==\n");

        // 배열 'parkArray' 내 시작 지점인 'S'의 좌표를 탐색.
        int[] mvPoint = new int[2]; // 공원에서 배열 'routesArray' 따른 동작 시에 움직임이 시작할 위치 저장.
        mvPoint[0] = 200; // 더미 데이터

        for (int i = 0; i < parkArray.length; i++) {
            for (int j = 0; j < parkArray[i].length; j++) {
                if (parkArray[i][j].equals("S")) {
                    mvPoint[0] = i;
                    mvPoint[1] = j;
                    break;
                }
            }
            if (mvPoint[1] != 0)
                break;
        }

        if (mvPoint[0] == 200) {
            System.out.println("S 값이 없으므로 answer 반환 후 종료");

            answer[0] = 0;
            answer[1] = 0;
            return answer;
        }

        // 본격적인 로직 시작
        // dirControll : 'routesArray' 내 주어진 방향(routesArray[0]) 및 거리(routesArray[1])에 따라 이동할 방향 및 거리 등에 대한 지표 저장.
        // E : j , +1 / W : j, +1 / S : i, -1 / N : i, -1
        HashMap<String, Pair> dirCon = new HashMap<String, Pair>();
        dirCon.put("E", new Pair("j", 1));
        dirCon.put("W", new Pair("j", -1));
        dirCon.put("S", new Pair("i", 1));
        dirCon.put("N", new Pair("i", -1));

        // routesArray의 크기 따른 배열 반복하며 OutBoundException 및 장애물 해당 안되는 동작만 실행
        // 인덱스 'i'와 'j'는 'dirCon' 내 'Pair' 와 연계되어 사용.
        Pair pair;

        for (int i = 0; i < routesArray.length; i++) { // 요청 내용인 배열 'routesArray' 내 포함된 모든 명령 내용을 하나하나 확인하면서 실행.

            int check = 0;

            // 이동할 방향 및 거리 계산.
            // 1. 현재 명령어에 대해 현재 이동 시작 지점(mvPoint[][])으로부터 명령된 방향 및 거리를 이동하였을 때에 OutboundException 일어나는지 여부 확인.
            // 2. 1번 결과에 대해 문제가 없을 경우 지나가는 모든 경로를 하나하나 확인해 가면서 X 표시 없음 확인.
            // 3. mvPoint 인덱스를 routesArray 따라 재 계산.
            // 1. 지정된 방향 및 거리에 대해 OutBoundException을 계산.
            // 배열 'mvPoint'를 기준으로 'routesArray' 내 방향 및 거리를 'dirCon'를 사용하여 계산했을 때 '< 0' 또는 'park.length' 길이를
            // 벗어 나지 않으면 이상 없으므로 확인.
            // 1번 항목 계산식
            // 1. routesArray 내 포함된 방향(W,S,W,E) 따라 인덱스(i 또는 j)를 가져온다.
            // 2. 'pair' 내 포함된 i, j(행 및 열)과 숫자를 'calcIndex' 내 적용 계산한다.
            // 3. 계산된 'calcIndex' 내 계산된 방향을 기준으로 OutBoundException 을 판단.
            pair = dirCon.get(routesArray[i][0]); // 동작을 지시하는 배열 'routesArray' 내 주어진 방향 따라 방향 별 세부 계산 내용 가져오기.
            int index = 0;

            if (pair.getdir().equals("i")) { // 'routesArray' 지시된 이동 방향이 '행' 방향 일 때, 해당 방향에 대한 OutBoundException 실행.

                // 지정된 방향(routesArray[i][0] : i)과 지정된 크기(routesArray[i][1]) 만큼 움직였을 때 위치를 계산.
                index = mvPoint[0] + (Integer.parseInt(routesArray[i][1]) * pair.getdist()); // 이동을 했을 시에 대한 최종 인덱스 위치.
                // routesArray[i][1] 에 대해서 + 또는 - 범위에 대한 계산을 mvPoint[i]에 적용한 결과

                if (index >= 0 && index < parkArray.length) { // pair.getdist() 의 값의 +/- 두 경우 모두 고려한 수식
                    check = 1; // 'OutBoundException' 해당 되지 않을 시 2번째 조건 확인 위한 플래그 변수
                }

            } else if (pair.getdir().equals("j")) { // 열 방향 계산 시작

            	
                // 지정된 방향(routesArray[i][0] : j)과 지정된 크기(routesArray[i][1]) 만큼 움직였을 때 위치를 계산.
                index = mvPoint[1] + (Integer.parseInt(routesArray[i][1]) * pair.getdist()); // 이동을 했을 시에 대한 마지막 인덱스 위치.
                // routesArray[i][1] 에 대해서 + 또는 - 범위에 대한 계산을 mvPoint[j]에 적용한 결과

                System.out.println("\n\npair.getdir().equals(\"j\") : " + pair.getdir().equals("j"));
                System.out.println("디버깅 - 그냥 인덱스 넣는 순간 , mvPoint[0] + (Integer.parseInt(routesArray[i][1]) * pair.getdist()) : " + 
                		mvPoint[0] + (Integer.parseInt(routesArray[i][1]) * pair.getdist()) + 
                		", parkArray.length : " + parkArray.length + ", index : " + index);
                
                if (index >= 0 && index < parkArray[0].length) { // pair.getdist() 의 값의 +/- 두 경우 모두 고려한 수식
                	
                    check = 1; // 'OutBoundException' 해당 되지 않을 시 2번째 조건 확인 위한 플래그 변수
                    
                }
            }

            // 2번 항목 수행, 주어진 범위인 'index' 내에서 'mvPoint'으로부터 위치 이동 시의 "X" 여부 확인.
            if (check == 1) {

                // "행 방향" 이동 명령 시의 "X" 여부 탐색
                if (pair.getdir().equals("i")) {

                    if (mvPoint[0] <= index) { // '+' 방향 탐색
                        for (int k = mvPoint[0]; k <= index && k < parkArray.length; k++) {
                            if (parkArray[k][mvPoint[1]].equals("X")) {
                                check = 0;
                                break;
                            }
                        }
                    }

                    if (mvPoint[0] >= index) { // '-' 방향 탐색
                        for (int k = mvPoint[0]; k >= index && k >= 0; k--) {
                            if (parkArray[k][mvPoint[1]].equals("X")) {
                                check = 0;
                                break;
                            }
                        }
                    }
                    
                    if (check == 1 && mvPoint[0] + (Integer.parseInt(routesArray[i][1]) * pair.getdist()) < parkArray.length) {
                        mvPoint[0] = mvPoint[0] + (Integer.parseInt(routesArray[i][1]) * pair.getdist());
                    }
                }

                // "열 방향" 이동 명령 시의 "X" 여부 탐색
                if (pair.getdir().equals("j")) {

                    if (mvPoint[1] <= index) { // '+' 방향 탐색
                        for (int k = mvPoint[1]; k <= index && k < parkArray[0].length; k++) {
                            if (parkArray[mvPoint[0]][k].equals("X")) {
                                check = 0;
                                break;
                            }
                        }
                    }

                    if (mvPoint[1] >= index) { // '-' 방향 탐색
                        for (int k = mvPoint[1]; k >= index && k >= 0; k--) {
                            if (parkArray[mvPoint[0]][k].equals("X")) {
                                check = 0;
                                break;
                            }
                        }
                    }

                    System.out.println("디버깅 - 인덱스 변경 적용 직전, mvPoint[0] + (Integer.parseInt(routesArray[i][1]) * pair.getdist()) : " + 
                    		mvPoint[0] + (Integer.parseInt(routesArray[i][1]) * pair.getdist()) + 
                    		", parkArray.length : " + parkArray.length + ", index : " + index);
                    
                    if (check == 1 && mvPoint[1] + (Integer.parseInt(routesArray[i][1]) * pair.getdist()) < parkArray[0].length) {
                    	
                        mvPoint[1] = mvPoint[1] + (Integer.parseInt(routesArray[i][1]) * pair.getdist());
                    }
                }
            }
        } // 반복문 끝

        answer = mvPoint;
        return answer;
    }
    
    private static final String[] DIRECTIONS = {"N", "S", "W", "E"};
	 private static final String[] PARK_ELEMENTS = {"O", "O", "O", "O", "O", "X"};
	 private static final Random RANDOM = new Random();
	
	public static void main(String[] args) {
		
		String[] park = generatePark(50);
       String[] routes = generateRoutes(7);

       // 출력
       System.out.println("Generated park:");
       for (String row : park) {
           System.out.println(row);
       }
       System.out.println();
       
       System.out.println("\nGenerated routes:");
       for (String route : routes) {
           System.out.print(route  + ", ");
       }
       System.out.println("\n");
       
       int [] answer = run(park, routes);
       
       for(int a : answer)
       	System.out.print(a + ", ");
       
       System.out.println("\n");
   }

	private static String[] generatePark(int size) {
       String[] park = new String[size];
       for (int i = 0; i < size; i++) {
           StringBuilder sb = new StringBuilder();
           for (int j = 0; j < size; j++) {
               sb.append(PARK_ELEMENTS[RANDOM.nextInt(PARK_ELEMENTS.length)]);
           }
           park[i] = sb.toString();
       }
       // 시작 지점 'S'를 랜덤 위치에 설정
       int randomRow = RANDOM.nextInt(size);
       int randomCol = RANDOM.nextInt(size);
       park[randomRow] = park[randomRow].substring(0, randomCol) + "S" + park[randomRow].substring(randomCol + 1);
       return park;
   }

   private static String[] generateRoutes(int size) {
       String[] routes = new String[size];
       for (int i = 0; i < size; i++) {
           String direction = DIRECTIONS[RANDOM.nextInt(DIRECTIONS.length)];
           int steps = RANDOM.nextInt(9) + 1;  // 1~9 사이의 랜덤한 수
           routes[i] = direction + " " + steps;
       }
       return routes;
   }
}
