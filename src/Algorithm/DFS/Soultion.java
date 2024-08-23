package Algorithm.DFS;

import java.util.LinkedList;

public class Soultion {

	static class Graph {
        private int numNodes;
        private LinkedList<Integer>[] adjList;

        // 그래프 초기화
        Graph(int numNodes) {
            this.numNodes = numNodes;
            adjList = new LinkedList[numNodes];
            for (int i = 0; i < numNodes; i++) {
                adjList[i] = new LinkedList<>();
            }
        }

        // 엣지 추가
        void addEdge(int src, int dest) {
            adjList[src].add(dest);
            adjList[dest].add(src); // 무방향 그래프일 경우
        }

        // 그래프 출력
        void printGraph() {
            for (int i = 0; i < numNodes; i++) {
                System.out.print("Node " + i + ":");
                for (Integer node : adjList[i]) {
                    System.out.print(" -> " + node);
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        int numNodes = 8;
        Graph graph = new Graph(numNodes);

        // 엣지 추가 (사진에 있는 그래프 구조에 맞게)
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(2, 6);
        graph.addEdge(3, 7);
        graph.addEdge(4, 7);
        graph.addEdge(5, 7);
        graph.addEdge(6, 7);

        // 그래프 출력
        graph.printGraph();
    }
}
