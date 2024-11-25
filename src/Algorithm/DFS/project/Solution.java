package Algorithm.DFS.project;

import java.util.ArrayList;
import java.util.List;

class Node {
    int value;
    List<Node> neighbors;

    public Node(int value) {
        this.value = value;
        this.neighbors = new ArrayList<>();
    }
}

public class Solution {
    private List<Node> nodes;

    public Solution(int[][] adjacencyMatrix) {
        int numNodes = adjacencyMatrix.length;
        nodes = new ArrayList<>(numNodes);

        // 노드 생성
        for (int i = 0; i < numNodes; i++) {
            nodes.add(new Node(i));
        }

        // 인접 행렬 정보를 바탕으로 연결 리스트 생성
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    nodes.get(i).neighbors.add(nodes.get(j));
                }
            }
        }
    }

    /* 실제 풀이 로직 */

    public static void main(String[] args) {
        int[][] graph = {
                {0, 0, 1, 1, 0},
                {0, 0, 0, 1, 1},
                {1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0}
        };

        Solution graphObj = new Solution(graph);
        // 이후 graphObj를 이용하여 그래프 관련 연산 수행
        
     // 연결 리스트 정보 출력 (예시)
        for (Node node : graphObj.nodes) {
            System.out.print("Node " + node.value + " is connected to: ");
            for (Node neighbor : node.neighbors) {
                System.out.print(neighbor.value + " ");
            }
            System.out.println();
        }
    }
}