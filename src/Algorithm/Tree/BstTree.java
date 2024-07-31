package Algorithm.Tree;

import java.util.Random;

/* 이진 탐색 트리(BST) 자료 구조 구현. */

/*
  - 이진 트리 공통 탐색 로직 : 
	  모든 탐색은 재귀 호출을 반복하며 진행됩니다. 탐색 과정에서 각 노드는 서브트리의 루트 노드로 간주되며, 탐색을 이어 나간다.
	  탐색 방향은 왼쪽 노드를 우선적으로 재귀 호출하여 탐색하며. 만약 현재 서브트리의 루트 노드의 왼쪽 노드가 null일 경우, 오른쪽 노드를 탐색한다. 오른쪽 노드가 존재할 경우 해당 노드로 이동하여, 	그 노드를 새로운 서브트리의 루트 노드로 간주하고 왼쪽 노드를 순차적으로 탐색합니다.
	  
	  중요한 점은 전위 탐색, 중위 탐색, 후위 탐색 모두 위의 공통된 방향 따른 탐색을 이어 나가는데 탐색 순서에 대한 상세한 차이점은 모든 탐색 로직 자체는 위 로직에 따라서 왼쪽을 우선적으로 탐색하나	왼쪽 탐색하면서 확인되는 각각의 서브트리에 대한 루트 노드로 인식됨에 따라 탐색하는 방법이 달라진다.
 */
	 

class Node {
	
	int data;
	Node leftNode;
	Node rightNode;
	
	// Node 생성자
	public Node(int data) {
		this.data = data;
	}
	
	public Node() {}
}

public class BstTree {

	/* 각 트리 내 노드 정의 */
	
	/* 노드 add 메소드 : 'data' 입력 받아 Node 생성 후 트리 노드인 rootNode 내 저장.*/
	public static void addTree(Node addNode, Node rootNode) {
		
		if(addNode.data < rootNode.data) {
			
			if(rootNode.leftNode == null) {
				rootNode.leftNode = addNode;
			} else
				addTree(addNode, rootNode.leftNode);
			
		} else if(addNode.data > rootNode.data) {
			
			if(rootNode.rightNode == null) {
				rootNode.rightNode = addNode;
			} else
				addTree(addNode, rootNode.rightNode);
			
		} else if(addNode.data == rootNode.data)		// 동일한 값이라면 추가로 저장하지 않고 반환.
			return;
			
		
		return;	
	}
	
	/* 노드 pop 메소드 : 'data' 입력 받아 해당 데이터와 일치하는 데이터를 가진 Node 를 찾 제거하고 없으면 단순 null 반환, 찾는 알고리즘은 전위 탐색, 중위 탐색, 후위 탐색 중 하나를 택해서 찾는다. */
	public Node popTree(int data) {
		
		
		
		/* 탐색 알고리즘 실행해서 null 값 받으면 'data'를 가지고 있는 Node 가 없다는 것이고 반환 받으면 해당 노드를 pop 해도 된다는 의미. */
		return new Node();
	}
	
	
	/* 전위 탐색 메소드(로직) : Node addNode, Node rootNode를 입력받아서 해당 'data'가 있는 지 확인.
		1. 탐색 과정 중 모든 서브 트리의 루트 노드를 우선적으로 탐색하기 때문에 모든 탐색 과정 중 거쳐가는 서브트리별 루트노드는 찾을 때 마다 즉각적으로 데이터를 추출해 낸다.
		2. 순차적으로 탐색하면서 왼쪽 노드 링크가 null 이라면 우측 노드 링크를 따라가서 해당 노드를 서브트리의 루트노드로써 인식하여 재귀함수를 호출하여 위 과정을 반복한다.
		3. 리프노드까지 도달하여 더 이상 왼쪽 노드 및 우측 노드도 없다면 재귀함수를 복귀하여 순차적으로 이전 단계의 서브트리 노드로 복귀한다.
		4. 위 과정을 반복하여 탐색을 하여 지정된 값을 지정된 배열 또는 ArrayList 내 순차적으로 저장한다.
	*/
	public static Node preorderTraversal(int data, Node rootNode) {
		
		if(rootNode != null)
			System.out.println("현재 노드 데이터 : " + rootNode.data + ", 주어진 탐색 데이터 : " + data);
		
		if(rootNode == null)
			return null;
		
		if(rootNode.data == data)
			return rootNode;
			
		Node node = preorderTraversal(data, rootNode.leftNode);
			
		if(node != null)
			return rootNode.leftNode;
		
		return preorderTraversal(data, rootNode.rightNode);
	}	
	
	/* 중위 탐색 메소드 */
	
	/* 후위 탐색 메소드 */
	
	public static void main(String[] args) {
		
		Random rand = new Random();
	
		/* root 노드 생성, 해당 노드로부터 Tree 구성 */
		Node rootNode = new Node(rand.nextInt(11) + 50); // 50에서 60 사이의 랜덤 값 생성);
		
		Node firstNode = new Node(10);
		BstTree.addTree(firstNode, rootNode);
		
		Node result = BstTree.preorderTraversal(10, rootNode);
		System.out.println("result : " + result);
		
		
		Node secondNode = new Node(20);
		Node thirdNode = new Node(70);
		Node forthNode = new Node(80);
	}
}
