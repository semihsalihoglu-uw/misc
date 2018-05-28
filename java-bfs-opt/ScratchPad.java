import java.io.IOException;
import java.util.Random;

public class ScratchPad {

    public static void main(String[] args) throws NumberFormatException, IOException {
	int graphSize = 10000000;
	int numBFSSteps = 7;
	Random rnd = new Random(395801);
	long startTime = System.currentTimeMillis();
	int[][] graph = getRandomKRegularGraph(rnd, graphSize, 16);
	System.out.println("Constructed the graph. Time taken. " +
			   (System.currentTimeMillis() - startTime));
	boolean[] visited = new boolean[graphSize];
	for (int i = 0; i < visited.length; ++i) {
	    visited[i] = false;
	}
	int[] cQueue = new int[graph.length];
	int[] nQueue = new int[graph.length];
	int source = rnd.nextInt(graphSize);
	System.out.println("Starting BFS.");
	startTime = System.currentTimeMillis();
	bFSOneByOne(graph, visited, cQueue, nQueue, source, numBFSSteps);
	// bFSEightByEight(graph, visited, cQueue, nQueue, source, numBFSSteps);
	long endTime = System.currentTimeMillis();
	System.out.println("Time Taken: " + (endTime - startTime));
    }

    private static void bFSOneByOne(int[][] graph, boolean[] visited,
				    int[] cQueue, int[] nQueue, int source, int numSteps) {
	int[] tmpQueue;
	cQueue[0] = source;
	visited[source] = true;
	int cQueueLength = 1;
	int nQueueLength = 0;
	int nextV;
	int nbr;
	int[] nextVAdjList;
	int numEdgesVisited = 0;
	for (int step = 0; step < numSteps; ++step) {
	    for (int i = 0; i < cQueueLength; ++i) {
		nextV = cQueue[i];
		nextVAdjList = graph[nextV];
		for (int j = 0; j < nextVAdjList.length; ++j) {
		    nbr = nextVAdjList[j];
		    if (!visited[nbr]) {
			nQueue[nQueueLength++] = nbr;
			visited[nbr] = true;
			numEdgesVisited++;
		    }
		}
	    }
	    tmpQueue = nQueue;
	    nQueue = cQueue;
	    cQueue = tmpQueue;
	    cQueueLength = nQueueLength;
	    nQueueLength = 0;
	}
	System.out.println("numEdgesVisited: " + numEdgesVisited);
    }

    private static void bFSEightByEight(int[][] graph, boolean[] visited,
					int[] cQueue, int[] nQueue, int source, int numSteps) {
	int[] tmpQueue;
	cQueue[0] = source;
	visited[source] = true;
	int cQueueLength = 1;
	int nQueueLength = 0;
	int nextV;
	int nbr;
	int[] nextVAdjList;
	int numEdgesVisited = 0;
	for (int step = 0; step < numSteps; ++step) {
	    for (int i = 0; i < cQueueLength/8; ++i) {
		//                System.out.println("Starting an interation. NumIters: " + (cQueueLength/8));
		for (int t = 0; t < 8; ++t) {
		    nextV = cQueue[i*8 + t];
		    nextVAdjList = graph[nextV];
		    for (int j = 0; j < 16; ++j) {
			nbr = nextVAdjList[j];
			if (!visited[nbr]) {
			    nQueue[nQueueLength++] = nbr;
			    visited[nbr] = true;
			    numEdgesVisited++;
			}
		    }
		}
	    }
	    if (cQueueLength % 8 > 0) {
		for (int i = (cQueueLength/8)*8; i < cQueueLength; ++i) {
		    nextV = cQueue[i];
		    nextVAdjList = graph[nextV];
		    for (int j = 0; j < nextVAdjList.length; ++j) {
			nbr = nextVAdjList[j];
			if (!visited[nbr]) {
			    nQueue[nQueueLength++] = nbr;
			    visited[nbr] = true;
			    numEdgesVisited++;
			}
		    }
		}
	    }
	    tmpQueue = nQueue;
	    nQueue = cQueue;
	    cQueue = tmpQueue;
	    cQueueLength = nQueueLength;
	    nQueueLength = 0;
	}
	System.out.println("numEdgesVisited: " + numEdgesVisited);
    }

    public static int[][] getRandomKRegularGraph(Random random, int numV, int k) {
	int[][] graph = new int[numV][k];
	for (int i = 0; i < numV; ++i) {
	    for(int j = 0; j < k; ++j) {
		graph[i][j] = random.nextInt(numV);
	    }
	}
	return graph;
    }
}
