package LevelOne.PersonalityTypeTest;

import java.util.ArrayList;
import java.util.HashMap;

class Personality{
	
	public String type;
	public int value;
	
	public Personality(String type, int value) {
		
		this.type = type;
		this.value = value;
	}
}

public class Solution {

	public static String [] solution(String[] survey, int[] choices) {
		
		
		int [] choiceScore = {3, 2, 1, 0, 1, 2, 3};		// choice 에 따라 선택적으로 각 지표 별 점수 산정 위한 배열.
		
		/* indicators : 성격 유형 검사 매기고 이를 "순서"대로 꺼내와서 결과 반환 하기 위한 "지표" 구현한 자료구조, 초기 점수 값은 0.
		 * index : 성격 유형을 문자열로 입력 받을 시 이를 "indicators" 내에서 찾기 위한 인덱싱 값. */
		
		HashMap<Integer, ArrayList<Personality>> indicators = new HashMap<Integer, ArrayList<Personality>>();
		HashMap<String, int []> index = new HashMap<String, int []>();
		
		int [] searchIndex;	// "index" 결과를 저장.
		
		ArrayList<String> result = new ArrayList<String>();
		String [] answer = new String[4];
		
		/* "indicators" */
		ArrayList<Personality> PersonOne = new ArrayList<Personality>();
		ArrayList<Personality> PersonTwo = new ArrayList<Personality>();
		ArrayList<Personality> PersonThree = new ArrayList<Personality>();
		ArrayList<Personality> PersonFour = new ArrayList<Personality>();
		
		PersonOne.add(new Personality("R", 0)); PersonOne.add(new Personality("T", 0));
		PersonTwo.add(new Personality("C", 0)); PersonTwo.add(new Personality("F", 0));
		PersonThree.add(new Personality("J", 0)); PersonThree.add(new Personality("M", 0));
		PersonFour.add(new Personality("A", 0)); PersonFour.add(new Personality("N", 0));
		
		indicators.put(1, PersonOne);
		indicators.put(2, PersonTwo);
		indicators.put(3, PersonThree);
		indicators.put(4, PersonFour);
		
		/* "index" */
		int R[] = {1 , 0}, T[] = {1 , 1};
		int C[] = {2 , 0}, F[] = {2 , 1};
		int J[] = {3 , 0}, M[] = {3 , 1};
		int A[] = {4 , 0}, N[] = {4 , 1};
		
		index.put("R", R); index.put("T", T);
		index.put("C", C); index.put("F", F);
		index.put("J", J); index.put("M", M);
		index.put("A", A); index.put("N", N);
		
		/* "survey"를 2차원 배열로 파싱 및 결과를 "surveyRes" 내 저장 */
	
		String [][] surveyRes = new String[survey.length][];
		
		for(int i = 0; i < survey.length; i++)
			surveyRes[i] = survey[i].split("");
		
		System.out.println("surveyRes 결과 확인 : ");
		for(int i = 0; i < surveyRes.length; i ++) {
			for(int j = 0; j < surveyRes[i].length; j++) 
				System.out.print("surveyRes[" + i + "][" + j + "] : " + surveyRes[i][j] + " ");
			System.out.println();
		}
		/* 풀이 로직 :
		 * 1. "surveyRes"와 "choices" 따라 "choices" 내 어느 곳에 얼마의 점수를 할당할 건지 판단.
		 * 2. "surveyRes" 내 선택된 문자열을 기준으로 "index" 를 조회하여 "indicators" 내 접근 위한 인덱스 추출.
		 * 3. 해당 "index" 조회 결과를 사용하여 "indicators" 내 접근하여 정해진 점수를 입력.
		 * 4. 위 과정을 "choices" 만큼 반복.
		 * 5. "indicators" 전체를 순회하면서 각 키 값 내 점수를 비교 한 후 이를 컬렉션 "result"에 순차적으로 저장.
		 * 6. 반환을 위해 "result" 크기 만큼 배열 "answer" 할당 및 초기화. */
		
		System.out.println("디버깅 - survery : AN, CF, MJ, RT, NA");
		System.out.println("디버깅 - choices : 5, 3, 2, 7, 5 \n");
		
		for(int i = 0; i < choices.length; i++) {
			
			
			if(choices[i] < 4) {	// "surveyRes[i][0]" 번 범위 지정 
				
				searchIndex = index.get(surveyRes[i][0]);	// "surveyRes[i][0]" 에 대해서 지정된 "choices[i]" 인덱스 대로 선택지(choiceScore) 대로 점수 추가
				indicators.get(searchIndex[0]).get(searchIndex[1]).value += choiceScore[choices[i]-1];
				
			} else if(choices[i] > 4) {	// "surveyRes[i][1]" 번 범위 지정
				
				searchIndex = index.get(surveyRes[i][1]);
				indicators.get(searchIndex[0]).get(searchIndex[1]).value += choiceScore[choices[i]-1];
				
			}
			System.out.println("");
		}
		
		for(int key : indicators.keySet()) {
			System.out.println("Key : " + key);
			for(int i = 0; i < indicators.get(key).size(); i++) {
				System.out.print("type : " + indicators.get(key).get(i).type + ", "+ ", value : " + indicators.get(key).get(i).value + ", ");
			}
			System.out.println("\n");
		}
		System.out.println();
	
		int i = 0;
		
		for(int key : indicators.keySet()) {
			
			if(indicators.get(key).get(0).value > indicators.get(key).get(1).value)
				answer[i] = indicators.get(key).get(0).type;
			if(indicators.get(key).get(0).value < indicators.get(key).get(1).value)
				answer[i] = indicators.get(key).get(1).type;
			if(indicators.get(key).get(0).value == indicators.get(key).get(1).value)
				answer[i] = indicators.get(key).get(0).type;
			
			i++;
		}
		
		return answer;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String [] survey = {"AN", "CF", "MJ", "RT", "NA"};
//		int [] choices = {5, 3, 2, 7, 5};

		String [] survey = {"TR", "RT", "TR"};
		int [] choices = {7, 1, 3};
		
		String answer [] = solution(survey, choices);
		
		for(String an : answer)
			System.out.print(an + " ");
	}
}