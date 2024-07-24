package LevelOne.ReceiveReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* 프로그래머스 문제 : level.1 신고 결과 받기 */
public class ReceiveReport {

	/* 풀이 로직 
	 * 
	 * 1. report 배열 내 신고 대상자 각 이름 별로 신고 당한 횟수를 HashMap 컬렉션으로 저장과 동시에,
	 *     각 신고 대상자를 키로써 신고자를 값으로 hashMap 저장.
	 * 2. id_list 내 사용자 목록을 기준으로 순차적으로 신고 대상자 HashMap 컬렉션 확인하여 k 값 이상인 대상자 추려서 결과로 저장.
	 * 3. 해당 배열을 가지고 이전, 각 신고 대상자를 키로써 신고자를 값으로 저장한 HashMap 테이블 가지고 각 사용자별 리포트 횟수 증가 및 반환.
	 * */
	
	public static int [] run(String[] id_list, String[] report, int k) {
		
		int[] answer = new int[id_list.length];
		
		 List<String> resultList = new ArrayList<>();

	     for (String entry : report) {
	         String[] splitEntry = entry.split(" ");
	         for (String name : splitEntry) {
	             resultList.add(name);
	         }
	     }

	     report = resultList.toArray(new String[0]);
	     
		
		HashMap<String, Integer> reportCount = new HashMap<String, Integer>();	// 신고 대상자 별 신고 당함 횟수 카운트
		HashMap<String, ArrayList<String>> reportMember = new HashMap<String, ArrayList<String>>();	// 신고 대상자를 키, 신고자들을 ArrayList로 저장.
		HashMap<String, Integer> reportResponse = new HashMap<String, Integer>(); // 정지 대상자를 신고한 사용자 별 신고 횟수 누적 카운트
		
		/* HashMap 컬렉션 'reportCount', 'reportMember' 를 저장 */
		
		for(int i = 0; i < report.length; i += 2) {
			
			if(reportCount.get(report[i+1]) == null)	// 신고 대상자 별 신고 횟수는 +1 증감식으로 계산되므로 0으로 초기화.
				reportCount.put(report[i+1], 0);
	
			if(reportMember.get(report[i+1]) == null)	// 
				reportMember.put(report[i+1], new ArrayList<String>());
			
			// 이미 동일한 이용자가 해당 이용자를 추가 신고 했을 경우 추가적으로 카운트 하지 않음, 'reportMember' 내 확인.
			int duplicationCheck = 0;
			for(int j = 0; j < reportMember.get(report[i+1]).size() ; j++) {
				
				if(reportMember.get(report[i+1]).get(j).equals(report[i]))
					duplicationCheck = 1;
			}
			
			if(duplicationCheck != 1) {
				
				reportCount.put(report[i+1], reportCount.get(report[i+1]) + 1);
				reportMember.get(report[i+1]).add(report[i]);
			}
			
		}

		/* 디버깅 - 유저 별 신고 당한 횟수 : reportCount 출력 */
		for(int i = 0; i < report.length; i++) {
			
			if(reportCount.get(report[i]) != null)
				System.out.println("report[" + i +"] : " + report[i] + "reportCount.get(report[" + i +"]) " + reportCount.get(report[i]));
		}
		
		/* 정지 대상자 따른 메일 전송자를 추리는 과정 
		 * 1. HashMap 컬렉션 'reportCount' 내 저장된 각각의 신고 대상자 별 신고 횟수를 순차적으로 확인하여 k 값 이상 이용자 추림.
		 * 2. 해당 대상자를 신고한 유저 별로 정지 대상으로 신고한 신고자 카운트 하여 배열로 저장.
		 * */
		
		for(int i = 0; i < id_list.length; i++) {		// 각 신고 받은 사용자 길이 만큼 반복.
			
			if(reportCount.get(id_list[i]) != null && reportCount.get(id_list[i]) >= k)			// 이용자 배열 기준으로 순차적으로 탐색 하면서 각 신고 당한 유저가 정지 기준 횟수 이상일 경우 다음 과정 진행.
									
				/* 신고 받은 유저 중 정지 대상 유저에 한해 신고한 이용자들에 대해서 신고 카운트 1 증가. */
				for(int j = 0; j < reportMember.get(id_list[i]).size(); j ++ ) {
					
					if(reportResponse.get(reportMember.get(id_list[i]).get(j)) == null)
						reportResponse.put(reportMember.get(id_list[i]).get(j), 0);
					
					reportResponse.put(reportMember.get(id_list[i]).get(j), reportResponse.get(reportMember.get(id_list[i]).get(j)) +1);	
				}
		}
		
		for(int i = 0 ; i < answer.length ; i++) {
			
			if(reportResponse.get(id_list[i]) != null)
					answer[i] = reportResponse.get(id_list[i]);
		}
		
		return answer;
	}
	
	public static void main(String[] args) {
		
//		String [] id_list = {"muzi", "frodo", "apeach", "neo"};
//		String[] report = {"muzi", "frodo","apeach", "frodo","frodo", "neo","muzi", "neo","apeach", "muzi"};
//		int k = 2;
		
		String [] id_list = {"con", "ryan"};
		String[] report = {"ryan", "con", "ryan", "con", "ryan", "con", "ryan", "con"};
		int k = 3;
		
		System.out.println("이용자 목록 : ");
		for(String id : id_list)
			System.out.println(id + " ");
		System.out.println();
		
		System.out.println("리포트 리스트 : ");
		for(int i = 0; i < report.length; i += 2) {
			System.out.println("신고자 이름 : " + report[i] + ", 신고 대상 : " + report[i+1]);
		}
		System.out.println("\n");
		
		int [] answer = run(id_list, report, k);
		System.out.print("\n\n[");
		
		for(int number : answer)
			System.out.print(number + ", ");
		System.out.println("]");
	}
}
