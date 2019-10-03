package unl.cse.graphs;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class JgraphDemo {

	public static void main(String args[]) {
		
		UndirectedGraph<String, DefaultEdge> g1 =
	            new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

		UndirectedGraph<String, DefaultEdge> g2 =
	            new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

		UndirectedGraph<String, DefaultEdge> g3 =
	            new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

			String v1 = "v1";
	        String v2 = "v2";
	        String v3 = "v3";
	        String v4 = "v4";

	        // add the vertices
	        g1.addVertex(v1);
	        g1.addVertex(v2);
	        g1.addVertex(v3);
	        g1.addVertex(v4);

	        g2.addVertex(v1);
	        g2.addVertex(v2);
	        g2.addVertex(v3);
	        g2.addVertex(v4);

	        g3.addVertex(v1);
	        g3.addVertex(v2);
	        g3.addVertex(v3);
	        g3.addVertex(v4);

	        // add edges to create a circuit
	        g1.addEdge(v1, v2);
	        g1.addEdge(v1, v3);
	        g1.addEdge(v2, v3);
	        g1.addEdge(v3, v4);

	        g2.addEdge(v4, v2);
	        g2.addEdge(v4, v3);
	        g2.addEdge(v2, v3);
	        g2.addEdge(v3, v1);

	        g3.addEdge(v4, v2);
	        g3.addEdge(v4, v3);
	        g3.addEdge(v2, v3);
	        g3.addEdge(v3, v1);

	        System.out.println("g1 = "+g1);
	        System.out.println("g2 = "+g2);
	        System.out.println("g3 = "+g3);

	        System.out.println("g2.equals(g3) = "+g2.equals(g3));
	        System.out.println("vertex sets   = "+g2.vertexSet().equals(g3.vertexSet()));
	        System.out.println("edge sets     = "+g2.edgeSet().equals(g3.edgeSet()));

	        System.out.println("g1 = g2: "+GraphUtils.equal(g1, g2));
	        System.out.println("g2 = g3: "+GraphUtils.equal(g2, g3));
	        System.out.println("g1 = g3: "+GraphUtils.equal(g1, g3));

	        System.out.println(GraphUtils.isomorphic(g1, g2));
	        
	}


}
