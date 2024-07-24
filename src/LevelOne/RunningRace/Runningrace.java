package LevelOne.RunningRace;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/* 프로그래머스 level 1 문제 : 달리기 경주 */
public class Runningrace {
	
	public static String [] run(String [] players, String [] callings) {
		
		HashMap <String, Integer> hashPlayers = new HashMap<String, Integer>();
		
		int index = 0;
		for(String player : players) {
			
			hashPlayers.put(player, index);
			index += 1;
		}
		
		int i = 0;
		String tempStr;
		
		for(String calling : callings) {
			
			i = hashPlayers.get(calling);
			System.out.println("\n\nhashPlayers.get(calling) : " + hashPlayers.get(calling) + ", i : " + i + "\n");
			String[] indexName = new String[2];
			
			if(i >= 1) {
				
				System.out.println("players[i-1] : " + players[i-1] + ", players[i] : " + players[i]);
				
				/* 순서 바꾸기. */
				tempStr = players[i-1];
				indexName[0] = players[i-1];
				indexName[1] = players[i];
				
				players[i-1] = players[i];
				players[i] = tempStr;
				
				/* HashMap 갱신 */
				hashPlayers.put(indexName[0], hashPlayers.get(indexName[0]) + 1);
				hashPlayers.put(indexName[1], hashPlayers.get(indexName[0]) - 1);
			}
			
			System.out.println("\n뒤 바뀐 결과 : ");
			for(String ca : callings)
				System.out.print(ca + " ");
		}
		
		return players;
	}
	
	public static void main(String[] args) {
		
//		// 플레이어의 이름을 저장할 배열 생성
//        String[] players = new String[10];
//        // 호출(callings)을 저장할 배열 생성
//        String[] callings = new String[20];
//
//        // 플레이어의 이름을 랜덤으로 생성하여 배열에 추가
//        for (int i = 0; i < players.length; i++) {
//            players[i] = generateRandomName(5, 10);
//        }
//
//        // 호출(callings) 배열을 랜덤으로 채움
//        for (int i = 0; i < callings.length; i++) {
//            callings[i] = players[ThreadLocalRandom.current().nextInt(players.length)];
//        }
//
//        // 결과 출력
//        System.out.println("Players: ");
//        for (String player : players) {
//            System.out.print(player + " ");
//        }
//        System.out.println("\nCallings: ");
//        for (String calling : callings) {
//            System.out.print(calling + " ");
//        }
		
		String [] players = {"mumu", "soe", "poe", "kai", "mine"};
		String [] callings = {"kai", "kai", "mine", "mine"};
		
		System.out.println("Players: ");
		for (String player : players) {
          System.out.print(player + " ");
		}
		
		System.out.println("\n\nCallings: ");
		
		for (String calling : callings) {
          System.out.print(calling + " ");
		}
		
        System.out.println();
        
        String [] answers = run(players, callings);
        
        System.out.println("\n결과 출력");
        for(String answer : answers)
        	System.out.print(answer + " ");
    }

    // 랜덤 이름 생성 메소드
    private static String generateRandomName(int minLength, int maxLength) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int length = ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = ThreadLocalRandom.current().nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }
        return sb.toString();
    }
}