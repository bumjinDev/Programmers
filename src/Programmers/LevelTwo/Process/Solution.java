package Programmers.LevelTwo.Process;

import java.util.ArrayList;
import java.util.HashMap;

/* 프로그래머스 - level 2 : 스택/큐 > 프로세스 */
/*
 * 풀이 로직:
 * 1. 'priorities' 배열 내 각 번호별 리스트를 저장합니다. (HashMap으로 관리)
 * 2. 'priorities'에서 프로세스를 꺼낼 때, 우선순위가 더 높은 프로세스가 있는지 확인합니다.
 *    2.1. 우선순위가 더 높은 프로세스가 있다면, 해당 프로세스를 다시 큐의 뒤로 보냅니다.
 *    2.2. 우선순위가 더 높은 프로세스가 없다면, 해당 프로세스를 실행하고 리스트에서 제거합니다.
 * 3. 이 모든 과정은 'priorities' 배열을 연결리스트로 재구성하여 수행합니다.
 *    3.1. 배열을 사용하면 데이터를 뒤로 다시 삽입할 때 원형 큐 로직을 구현해야 하므로, 연결리스트를 사용하여 구현합니다.
 */

/* 양 방향 연결 리스트로 구현 : 양 방향으로 구현하는 이유는 front 포인터에서 제거 시 이전 노드 위치를 알아낼 때 단방향이면 near 부터 끝까지 전부 순회해야 되기 때문  */

class Node {
    boolean processTarget;
    int processPriority;
    Node prev;
    Node next;

    public Node(boolean processTarget, int processPriority) {
        this.processTarget = processTarget;
        this.processPriority = processPriority;
        prev = null;
        next = null;
    }
}

/* Node 를 가지고 queue 자료구조를 구현하는 클래스, 내부적으로 prev와 next 를 가진다. */
class QueueNode {
    Node near;
    Node front;
    Integer sequence;

    public QueueNode() {
        near = null;
        front = null;
        this.sequence = new Integer(0);
    }

    /* =========== 해당 Queue 내에서 실제로 사용될 메소드들 ================ */
    public void enqueue(Node node) {
        if (near == null) {
            near = node;
            front = node;
        } else {
            node.next = near;
            near.prev = node;
            near = node;
        }
    }

    /* dequeue() : 단 방향 연결리스트에서 front 포인터의 노드 삭제. */
    public Node dequeue() {
        Node node = front;
        if (front == null)
            return null;
        if (near == front) {
            near = null;
            front = null;
            return node;
        } else {
            front = front.prev;
            front.next.prev = null;
            front.next = null;
            return node;
        }
    }

    public void showqueue() {
        Node node = near;
        while (node != null) {
            System.out.println("node.processPriority : " + node.processPriority + ", node.processTarget : " + node.processTarget);
            node = node.next;
        }
    }

    /* 현재 Queue 내 모든 프로세스 순차적으로 꺼내어 실행하면서 'sequence' 갱신한다. */
    public void processExecution(HashMap<Integer, Integer> processCount) {
        ArrayList<Integer> prioritys = new ArrayList<Integer>();
        boolean executionBool = true;

        while (true) {
            Node node = this.dequeue();
            if (node == null) {
                System.out.println("현재 큐에 대해서 더 이상 노드가 없으므로 종료.");
                return;
            }
            /* 현재 'dequeue()' 메소드 통해 Node 하나를 꺼내온 상태에서 현재 노드보다 높은 우선 순위가 있는 노드가 있는지 확인하고 있다면 그냥 다시 enqueue 하기 */
            for (int priority : processCount.keySet()) {
                if (priority >= node.processPriority)
                    prioritys.add(priority);
                if (priority > node.processPriority) {
                    executionBool = false;
                    this.enqueue(node);
                    break;
                }
            }

            if (executionBool) {
                this.sequence += 1;
                if (!prioritys.isEmpty()) { // prioritys 리스트가 비어 있지 않은지 확인
                    processCount.put(prioritys.get(0), processCount.get(prioritys.get(0)) - 1);
                    if (processCount.get(prioritys.get(0)) == 0)
                        processCount.remove(prioritys.get(0));
                }
                if (node.processTarget == true)
                    return;
            }

            prioritys.clear();
            executionBool = true;
        }
    }
}

public class Solution {
    public static int solution(int[] priorities, int location) {
        QueueNode queue = new QueueNode();
        HashMap<Integer, Integer> processCount = new HashMap<Integer, Integer>();

        /* 초기 작업 :
         * 
         * 1. HashMap 컬렉션 사용해서 priorities 내 각 프로세스 우선 순위 별 큐를 별도로 두어서 각 우선 순위 별 프로세스 들어 온 순서 별로 구현을 한다.
         * 2. HashMap 숫자 리스트를 관리하기 위해 각 배열 내 접근할 때에 동시에 연결리스트로서 생성한다. */

        for (int i = 0; i < priorities.length; i++) {
            if (processCount.get(priorities[i]) == null) // 연결 리스트로써 관리될 현재 프로세스의 우선순위에 해당하는 Queue 가 없으면 생성
                processCount.put(priorities[i], 0);

            processCount.put(priorities[i], processCount.get(priorities[i]) + 1);

            Node node = null;

            /* 각 프로레스 별 우선순위 대로 큐를 별도로 만들어서 삽입 작업을 수행할 때 해당 Node 가 지정된 Node 인지 구별하기 위함. */
            if (location == i) { // 지정된 노드라면 매개변수 'Node.processTarget' 을 식별자로써 'true' 설정.
                node = new Node(true, priorities[i]);
            } else { // 지정된 노드가 아니라면 매개변수 'Node.processTarget' 을 식별자로써 'false' 설정.
                node = new Node(false, priorities[i]);
            }

            queue.enqueue(node);
        }

        /* queue 내 포함된 데이터 확인. */
        queue.showqueue();

        /* 실제 로직 : 각 우선 순위 높은 순서대로 Queue 를 호출하여 내부적으로 Queue 내 실제 프로세스 시작 및 시작한 순서 번호인 'sequence'를 적용하여 'Targer' 노드가 실행되는 순번을 저장. 시작. */
        queue.processExecution(processCount);

        return queue.sequence;
    }

    public static void main(String[] args) {
        int[] priorities1 = {2, 1, 3, 2};
        int location1 = 2;
        System.out.println("결과: " + solution(priorities1, location1)); // 1

        int[] priorities2 = {1, 1, 9, 1, 1, 1};
        int location2 = 0;
        System.out.println("결과: " + solution(priorities2, location2)); // 5
    }
}
