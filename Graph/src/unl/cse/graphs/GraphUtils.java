package unl.cse.graphs;

import java.util.HashMap;
import java.util.Map;
import java.util.*;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

//import unl.cse.counting.LexicoPermutation;
//import unl.cse.counting.Permutation;

public class GraphUtils {

	/**
	 * Returns true if and only if the two graphs are isomorphic; false otherwise
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isomorphic(UndirectedGraph<String, DefaultEdge> a, UndirectedGraph<String, DefaultEdge> b) {
		return (getIsomorphicMap(a, b) != null);
	}
        
        //****************************************************************************************
        // create edges matrix. matrix example:
        //     v1  v2  v3  v4 (connect is 1, not connect is 0)
        // v1  0   1   1   0 
        // v2  1   0   1   0
        // v3  1   1   0   1
        // v4  0   0   1   1
        //
        public static int[][] getMatrix(UndirectedGraph<String, DefaultEdge> a, Object vertexs[]) {
            int[][] x = new int[vertexs.length][vertexs.length];
            for(int i=0; i<vertexs.length; i++) {
                for(int j=0; j<vertexs.length; j++) {
                    if(a.containsEdge((String)vertexs[i], (String)vertexs[j]))
                        x[i][j] = 1;
                    else
                        x[i][j] = 0;
                }
            }
            return x;
        }
        
        // compare 2 matrixs
        public static boolean compareMatrix(int[][] a, int[][] b) {
            for(int i=0; i<a.length; i++) {
                for(int j=0; j<a.length; j++) {
                    if(a[i][j] != b[i][j])
                        return false;
                }
            }
            return true;
        }
        
        // Get the list of all order of vertexs in the edges matrix
        // Example: vertexs set: a, b, c, d
        // order of vertexs: a, b, c, d
        //                   a, b, d, c
        //                   a, c, b, d
        //                   ..........
        //                   b, a, c, d
        //                   ..........
        // 4 vertexs => total order is 24
        public static void getListPath(LinkedList<Object[]> list, LinkedList<String> path, LinkedList<String> set) {
            if(set.size() == 1) {
                path.add(set.get(0));
                Object[] objects = path.toArray();
                list.add(objects);
                return;
            }
            
            for(int i=0; i<set.size(); i++) {
                LinkedList<String> temp = new LinkedList<String>();
                for(int j=0; j<set.size(); j++) {
                    temp.add(set.get(j));
                }
                String x = temp.remove(i);
                LinkedList<String> path1 = new LinkedList<String>();
                for(int j=0; j<path.size(); j++) {
                    path1.add(path.get(j));
                }
                path1.add(x);
                getListPath(list, path1, temp);
            }
        }
	//****************************************************************************************
        
	/**
	 * If the given graphs are isomorphic, returns a map between the vertex sets witnessing the isomorphism.  If 
	 * the given graphs are not isomorphic, returns null.
	 * @param a
	 * @param b
	 * @return
	 */
	public static Map<String, String> getIsomorphicMap(
        UndirectedGraph<String, DefaultEdge> a, 
        UndirectedGraph<String, DefaultEdge> b) {
		//TODO: implement
                /* Algorithm:
                 * Check the vertexs and edges of 2 graphs are the same 
                 * Create matrix edges to display for graph 1 as below:
                 *     v1  v2  v3  v4 (connect is 1, not connect is 0)
                 * v1  0   1   1   0 
                 * v2  1   0   1   0
                 * v3  1   1   0   1
                 * v4  0   0   1   1
                 * 
                 * Create a list of order of vertexs of graph 2 as below:
                 * Example: vertexs set: a, b, c, d
                 * order of vertexs: a, b, c, d
                 *                   a, b, d, c
                 *                   a, c, b, d
                 *                   ..........
                 *                   b, a, c, d
                 *                   ..........
                 * 
                 * With each order in the list:
                 * - create matrix edges for it
                 * - compare this matrix with the matrix of graph 1
                 * - if they are the same then create the map and return the map
                 */
                if(a.vertexSet().size() != b.vertexSet().size()) return null;
                if(a.edgeSet().size() != b.edgeSet().size()) return null;
            
                Object[] vertex1 = a.vertexSet().toArray();
                Object[] vertex2 = b.vertexSet().toArray();
                
                int[][] v1 = getMatrix(a, vertex1);
                
                LinkedList<Object[]> list = new LinkedList<Object[]>();
                LinkedList<String> path = new LinkedList<String>();
                LinkedList<String> set = new LinkedList<String>();
                
                for(int j=0; j<vertex2.length; j++) {
                    set.add((String)vertex2[j]);
                }
                getListPath(list, path, set);
                        
                Map<String, String> result = new HashMap<String, String>();
                
                while(list.size() > 0) {
                    Object[] temp = list.remove();
                    int[][] v2 = getMatrix(b, temp);
                    if(compareMatrix(v1, v2)) {
                        for(int i=0; i<vertex1.length; i++) {
                            result.put((String)vertex1[i], (String)temp[i]);
                        }
                        return result;
                    }
                }
                
		return null;
	}
	
	public static boolean equal(UndirectedGraph<String, DefaultEdge> a, UndirectedGraph<String, DefaultEdge> b) {
		
		if(!a.vertexSet().equals(b.vertexSet())) 
			return false;

		if(a.edgeSet().size() != b.edgeSet().size()) 
			return false;
		
		for(DefaultEdge e : a.edgeSet()) {
			if(b.getEdge(a.getEdgeSource(e), a.getEdgeTarget(e)) == null)
				return false;
		}

		return true;
	}
}
