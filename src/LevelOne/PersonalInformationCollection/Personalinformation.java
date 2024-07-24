package LevelOne.PersonalInformationCollection;

import java.util.ArrayList;
import java.util.HashMap;

/* 프로그래머스 문제 풀이 : 개인정보 수집 유효기간 */
public class Personalinformation {
	
	public static class privaciesVO {
		
		int serialNumber;
		String joinDate;
		String termsType;
		
		public privaciesVO(int serialNumber, String joinDate, String termsType){
			
			this.serialNumber = serialNumber;
			this.joinDate = joinDate;
			this.termsType = termsType;
		}
		
		public int getserialNumber() {
			
			return serialNumber;
		}
		
		public String getjoinDate() {
			
			return joinDate;
		}
		
		public String gettermsType() {
			
			return termsType;
		}
	}
	
	/* 풀이 로직 : 
	 * 1. 각 금일 날짜, 약관 종류, 개인정보 담고 있는 변수 'today', 'terms', 'privacies' 전달 받는다.
	 * 2. 변수 'terms'는 HashMap 컬렉션 사용하여 약관 종류를 키, 휴효기간 개월수를 값으로 저장
	 * 3. 변수 'privacies' 는 객체 vo 내 저장하며 배열 내 순서대로 인덱스 또한 vo 내 저장.
	 * 4. 저장된 vo 객체를 포함한 ArrayList를 순차적으로 확인하면서 각 개인정보 별 종류 및 개월 수와 현재 날짜 비교하여 파기될 약관 번호를
	 *     int 형 배열 answer 내 저장 후 결과로 반환.
	 * */
	public static int [] run(String today, String [] terms, String [] privacies) {
		
		int [] answer;
		int [] tempanswer;
		int size = 0;
		
		HashMap<String, Integer> termsHash = new HashMap<String, Integer>();	// 배열 'terms'를 '약관 종류 - 유효 달 수' 저장.
		ArrayList<privaciesVO> arrayPri = new ArrayList<privaciesVO>();
		
		/* 문자열 배열 'terms', 'privacies'에 대한 전처리 작업 */
		
		for(String term : terms) {	// 문자열 'terms'
			
			String [] temps = term.split(" ");
			termsHash.put(temps[0], Integer.parseInt(temps[1]));

		}
		
		int i = 0;
		for(String privacie : privacies) {	// 문자열 'privacies'
			
			String [] temps = privacie.split(" ");
			arrayPri.add(new privaciesVO((i+1), temps[0], temps[1]));

			i ++;
 		}
		
		tempanswer = new int[i];
		
		/* 'arrayPri' 내 순차적으로 약관 종류를 확인 및 날짜 비교하여 현재 날짜와 비교해서 다르면 배열 answer 내 저장. */
		
		i = 0; 		// i 초기화.
		for(privaciesVO privace : arrayPri) {
			
			String [] todayTemp = today.split("\\.");	// today 변수 내 년, 월, 일를 순서대로 문자열 저장.
			
			int [] todays = { Integer.parseInt(todayTemp[0]) , Integer.parseInt(todayTemp[1]) , Integer.parseInt(todayTemp[2]) };	// 문자열 'today' 내 포함된 년, 월, 일을 분리하여 순차적으로 int 형 배열 내 저장.
			
			int termsDate = termsHash.get(privace.gettermsType()); // 현재 개인정보 내 포함된 약관 종류에 따른 유효 개월 수.
			
			String[] tempprivaceDate = privace.joinDate.split("\\.");	// arrayList 내 보관 중이던 각 유효 날짜를 분리하여 순차적으로 년, 월, 일을 문자열 형태로 저장.
			int [] privaceDate = { Integer.parseInt(tempprivaceDate[0]) , Integer.parseInt(tempprivaceDate[1]) , Integer.parseInt(tempprivaceDate[2]) };	// 분리된 약관 내 년 월 일 문자열을 각각 int 형 배열 내 순차적으로 저장.
			
			/*  실제 비교 시작., 현재 개인정보 내 포함된 회원정보 수집 일자를 termsDate 와 더한 후 이를 현재 날짜와 비교하여 현재 날짜가 유효기관 6개월 내 포함되지 않으면 반환 목록에 추가 */
			
			/* 각 회원 별 약관 따른 년, 월 단위로 유효기간 끝 날짜를 계산 */
			
			/*
			 * 1. termsDate 내 남아 있는 수 확인.
			 * 2. 현재 달 수 - 남아 있는 수를 확인
			 * 3. termsDate - 현재 남아 있는 수 = termsDate, 이때 우선적으로 확인하여 termsDate 수가 현재 남아 있는 수 보다 작으면 그냥 termDate 를 전부 일수에 추가하고 메소드 종료
			 * 5. 현재 달을 +1 하되, 13 월이 되면 현재 달 수를 1월달으로 초기화 후 년도 증가.
			 *  */
			
			termsDate = termsDate * 28;

			while(termsDate > 0) {
				
				int calc = 28 - privaceDate[2];
				
				if(termsDate > calc) {	// 다음달로 넘어가기 위한 일수를 채울 수 있다면 그것에 따른 로직 실행.
			
					/* 날짜 중 '일' 단위 계산. */
					termsDate -= (calc + 1);	// 여기서 1을 더 빼는 이유는 다음 달 넘어갈 때 1일을 지정해야 되기 때문.
				
					privaceDate[2] = 1;
					
					/* 날짜 중 '월' 단위 계산 */
					privaceDate[1] += 1;
					
					if(privaceDate[1] >= 13) {
						
						privaceDate[1] = 1;
						privaceDate[0] += 1;
					}
				} else {
					privaceDate[2] += termsDate;
					termsDate = 0;
				}
			}
			
			
			if(privaceDate[0] < todays[0]) {
				tempanswer[i] = privace.serialNumber;
				size += 1;
			}
			else if(privaceDate[0] <= todays[0] && privaceDate[1] < todays[1]) {
				tempanswer[i] = privace.serialNumber;
				size += 1;

			} else if (privaceDate[0] <= todays[0] && privaceDate[1] <= todays[1] && privaceDate[2]-1 < (todays[2])) {
				
				tempanswer[i] = privace.serialNumber;
				size += 1;
			}
			i++;
		}
		
		answer = new int[size];
		int index = 0;
		
		for(int j = 0; j < tempanswer.length; j++) {
			
			if(tempanswer[j] != 0) {
				answer[index] = tempanswer[j];
				index ++;
			}
		}
		
		return answer;
	}
	
	public static void main(String[] args) {
		
//		String today = "2022.05.19";
//		String[] terms = {"A 6", "B 12", "C 3"};
//		String[] privacies = {"2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"};
		
		String today = "2020.01.01";
		String[] terms = {"Z 3", "D 5"};
		String[] privacies = {"2019.01.01 D", "2019.11.15 Z",  "2019.08.02 D", "2019.07.01 D", "2018.12.28 Z"};
	
		int answer[] = run(today, terms, privacies);
		
		for(int a : answer) {
			
			System.out.println("a : " + a);
		}
		
//		System.out.println("answer : " + answer[0] + ", " + answer[1]);
	}
}