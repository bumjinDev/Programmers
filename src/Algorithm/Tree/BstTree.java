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
		
		if(rootNode == null)
			return null;
			
		if (data == rootNode.data) {
	        return rootNode;
	    }

	    Node leftResult = preorderTraversal(data, rootNode.leftNode);
	    if (leftResult != null) {
	        return leftResult;
	    }

	    return preorderTraversal(data, rootNode.rightNode);
	}	
	
	/* 중위 탐색 메소드 */
	public static Node inOrderTraversal(int data, Node rootNode) {
		
		System.out.println("inOrderTraversal() 실행!\n");
		
		/* 다음 것이 존재한다면 없는 지점, 즉 마지막 왼쪽 노드를 찾을 때 까지 재귀적으로 재 호출한다. */
		if(rootNode.leftNode != null) {
			inOrderTraversal(data, rootNode.leftNode);
		}
		/* 마지막 왼쪽 노드인 마지막 서브트리로서 기능하는 루트 노드를 찾으면 분기하는 지점,
		 * !! 만약 현재 노드 내 데이터가 탐색 조건으로 주어진 'data' 와 동일한 노드를 찾았으면 그 다음 별도의 로직은 적용하지 않고. 지금까지 호출한 재귀 함수 스택을 없애면서 복귀를 한다.
		 */

		
		if(rootNode.data == data) {
			
			return rootNode;
			
		/* 1. 마지막 레벨의 가장 왼쪽 노드에서 값을 찾지 못한 경우, 오른쪽 노드가 있는지 확인한다. 
		 * 2. 만약 오른쪽 노드가 존재한다면, 해당 시점부터 왼쪽을 다시 탐색하기 위해 현재 함수의 재귀적 호출을 다시 실행하여 마지막 왼쪽 노드에 도달할 때까지 반복한다.
		 * 3. 'if(rootNode.leftNode != null)' 조건을 완료하도록 유도한다.
		 * 4. 중요한 점은, 아래 else 문은 마지막 왼쪽 노드에서 오른쪽 노드를 탐색한 결과로 null이 나오면 단순히 현재 재귀 함수 호출을 종료하기 위한 목적으로만 null을 반환한다.
		 * 5. 이에 따라 이전 순차대로 실행된 재귀 함수 복귀 지점은 'return inOrderTraversal(data, rootNode.leftNode);'가 된다.
		 * 6. 그러므로 이어서 복귀된 함수들이 재 실행되는 지점은 'if(rootNode.data == data)'가 된다. (이 구문을 확인하는 것 자체가 성능 낭비이긴 하다.)
		 * 7. 각 복귀할 때마다 'if(inOrderTraversal(data, rootNode.rightNode) != null)' 코드가 동일하게 실행되므로 각 노드별로 공통적으로 오른쪽 노드가 존재하는지에 따라 분기 실행되도록 유도한다.
		 * 8. 결과적으로 루트 노드에 도달했을 때 자연스럽게 루트 노드 또한 오른쪽 노드를 확인함으로써 탐색을 완료한다.
		 */
			
		} else {
			if(rootNode.rightNode != null) {
				return inOrderTraversal(data, rootNode.rightNode);
				
			} else
				return null;
		}
	}
	
	/* 후위 탐색 메소드 */
	public static Node postOrderTraversal(int data, Node rootNode) {
	    
	    /* 마지막 왼측 노드인 마지막 서브트리로 판정되는 말단 노드 탐색. */
	    if (rootNode.leftNode != null) {
	        Node leftResult = postOrderTraversal(data, rootNode.leftNode);
	        if (leftResult != null) {
	            return leftResult;
	        }
	    }
	    
	    /* 말단 노드 및 재귀적으로 복귀하면서 복귀 될 때에 각 호출되어 있는 메소드 스택 내 rootNode(각 서브트리 내 루트 노드로 간주) 내 data 값을 우측 -> 좌측 -> 루트 노드 순으로 비교해야 되는데 이때 우측 노드가 있으면 이어서 왼쪽 노드 끝단 탐색 재 실행. */
	    if (rootNode.rightNode != null) {
	        Node rightResult = postOrderTraversal(data, rootNode.rightNode);
	        if (rightResult != null) {
	            return rightResult;
	        }
	    }
	    
	    /* 이제부터 복귀 시점 부터 data 를 확인. */
	    if (rootNode.data == data) {
	        return rootNode;
	    }
	    
	    return null;
	}


	public static void main(String[] args) {
		
		Random rand = new Random();
	
		/* root 노드 생성, 해당 노드로부터 Tree 구성 */
		Node rootNode = new Node(rand.nextInt(11) + 50); // 50에서 60 사이의 랜덤 값 생성);
		System.out.println("rootNode 값 : " + rootNode.data + "\n");
		
		/* 첫번째 노드 삽입 */
		Node firstNode = new Node(10);
		BstTree.addTree(firstNode, rootNode);
		
		Node result = BstTree.preorderTraversal(firstNode.data, rootNode);
		System.out.println("result : " + result.data);
		System.out.println("\n");
		
		Node secondNode = new Node(70);
		BstTree.addTree(secondNode, rootNode);
		
		System.out.println("두번째 찾기 시작!");
		Node result2 = BstTree.inOrderTraversal(secondNode.data, rootNode);
		System.out.println("result2 : " + result2.data + ", secondNode : " + secondNode.data);
		

		 Node thirdNode = new Node(70);
		 BstTree.addTree(thirdNode, rootNode);

		 Node forthNode = new Node(80);
		 BstTree.addTree(forthNode, rootNode);

		
	    /* 후위 탐색 테스트 */
	    System.out.println("세번째 찾기 시작!");
	    Node result3 = BstTree.postOrderTraversal(thirdNode.data, rootNode);
	    System.out.println("result3 : " + result3.data + ", thirdNode : " + thirdNode.data);

	    System.out.println("네번째 찾기 시작!");
	    Node result4 = BstTree.postOrderTraversal(forthNode.data, rootNode);
	    System.out.println("result4 : " + result4.data + ", forthNode : " + forthNode.data);
	}
}
