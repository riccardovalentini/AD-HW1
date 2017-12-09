import graph.*;
import java.util.*;

public class HubConductor
{
	static int N = 100;
	
	public static void populingSuccessors(Graph G,Node root,Node from)
	{
		int n=G.outGoing(root).size();
		if(n==0) root.setSuccessorLabel(1);
		else{
				int successors =1;
				for(int i=0; i<n; i++)
				{
					Edge e=G.outGoing(root).get(i);
					if(e.getFirst()==from || e.getSecond()==from) continue;
					Node s = G.opposite(G.outGoing(root).get(i),root);
					populingSuccessors(G,s,root);
					successors+=s.getSuccessorLabel();
				}
				root.setSuccessorLabel(successors);
			}
			return;
	}
	
	public static Node findHubConductor(Graph G)
	{
		int v = G.nodes().size();
		Node root=G.getNodeAt(0);
		populingSuccessors(G,root,null);
		Node predecessor = null;
		if(G.outGoing(root).size()==0) return root;
		while(root!=null)
		{
			List<Edge> outgoing=G.outGoing(root);
			int n=G.outGoing(root).size();
			Node max = null; 
			for(int i=0; i<n; i++)
			{
				Node x = G.opposite(outgoing.get(i),root);
				if(x == predecessor) continue;
				if(max==null) max=x;
				if(x.getSuccessorLabel() > max.getSuccessorLabel()) max = x;
			}
			if(max.getSuccessorLabel() <= v/2)
			{
				return root;
			}
			predecessor = root;
			root = max;
		}
		return null;

	}
	
	
	public static void main(String[] args)
	{
		Node[] nodes = new Node[N];
		for(int i=1; i<N; i++) nodes[i]=new Node("V"+i,i);
		
		/* First graph */
		Edge a1 = new Edge(nodes[1],nodes[2]);
		Edge a2 = new Edge(nodes[1],nodes[3]);
		Edge a3 = new Edge(nodes[1],nodes[4]);
		
		Edge a4 = new Edge(nodes[2],nodes[5]);
		Edge a5 = new Edge(nodes[2],nodes[6]);
		
		Edge a6 = new Edge(nodes[3],nodes[7]);
		
		Edge a7 = new Edge(nodes[4],nodes[8]);
		Edge a8 = new Edge(nodes[4],nodes[9]);
		Edge a9 = new Edge(nodes[4],nodes[10]);
		
		Edge a10 = new Edge(nodes[5],nodes[11]);
		Edge a11 = new Edge(nodes[5],nodes[12]);
		Edge a12 = new Edge(nodes[5],nodes[13]);
		Edge a13 = new Edge(nodes[5],nodes[14]);
		
		Edge a14 = new Edge(nodes[6],nodes[15]);
		
		Edge a15 = new Edge(nodes[7],nodes[16]);
		
		Edge a16 = new Edge(nodes[9],nodes[17]);
		Edge a17 = new Edge(nodes[9],nodes[18]);
		
		List<Node> nodeList1 = new ArrayList<Node>();
		for(int i=1; i<=18; i++) nodeList1.add(nodes[i]);
		
		Graph G1 = new Graph(nodeList1);
		System.out.println("G1 is "+Graph.spanningVisit(G1));
		System.out.println("Hub Constructor  is "+findHubConductor(G1));


		/* Second graph */
		Edge b1 = new Edge(nodes[19],nodes[20]);

		Edge b2 = new Edge(nodes[20],nodes[21]);
		Edge b3 = new Edge(nodes[20],nodes[22]);

		Edge b4 = new Edge(nodes[21],nodes[23]);
		Edge b5 = new Edge(nodes[21],nodes[24]);
		Edge b6 = new Edge(nodes[21],nodes[25]);

		Edge b7 = new Edge(nodes[24],nodes[37]);
		Edge b8 = new Edge(nodes[24],nodes[38]);
		
		Edge b9 = new Edge(nodes[22],nodes[26]);
		Edge b10 = new Edge(nodes[22],nodes[27]);
		Edge b11 = new Edge(nodes[22],nodes[28]);

		Edge b12 = new Edge(nodes[26],nodes[29]);
		Edge b13 = new Edge(nodes[26],nodes[30]);
		
		Edge b14 = new Edge(nodes[28],nodes[31]);
		Edge b15 = new Edge(nodes[28],nodes[32]);
		
		Edge b16 = new Edge(nodes[31],nodes[33]);
		Edge b17 = new Edge(nodes[31],nodes[34]);		
		
		Edge b18 = new Edge(nodes[34],nodes[35]);
		Edge b19 = new Edge(nodes[34],nodes[36]);
		
		
		List<Node> nodeList2 = new ArrayList<Node>();
		for(int i=19; i<=38; i++) nodeList2.add(nodes[i]);
		
		Graph G2 = new Graph(nodeList2);
		System.out.println("G2 is "+Graph.spanningVisit(G2));
		System.out.println("Hub Constructor  is "+findHubConductor(G2));
		
		/* Third graph */
		Edge c1 = new Edge(nodes[52],nodes[40]);
		Edge c2 = new Edge(nodes[52],nodes[41]);
		Edge c3 = new Edge(nodes[52],nodes[42]);
		Edge c4 = new Edge(nodes[52],nodes[43]);
		
		Edge c5 = new Edge(nodes[41],nodes[44]);
		Edge c6 = new Edge(nodes[44],nodes[49]);

		Edge c7 = new Edge(nodes[42],nodes[45]);
		Edge c8 = new Edge(nodes[45],nodes[50]);
		
		Edge c9 = new Edge(nodes[43],nodes[46]);
		Edge c10 = new Edge(nodes[46],nodes[51]);
		Edge c11 = new Edge(nodes[51],nodes[39]);

		Edge c12 = new Edge(nodes[40],nodes[47]);
		Edge c13 = new Edge(nodes[40],nodes[48]);

		List<Node> nodeList3 = new ArrayList<Node>();
		for(int i=39; i<=52; i++) nodeList3.add(nodes[i]);
		
		Graph G3 = new Graph(nodeList3);
		System.out.println("G3 is "+Graph.spanningVisit(G3));
		System.out.println("Hub Constructor  is "+findHubConductor(G3));

		/* Fourth graph */
		Edge d1 = new Edge(nodes[53],nodes[54]);
		Edge d2 = new Edge(nodes[59],nodes[58]);
		Edge d3 = new Edge(nodes[57],nodes[56]);
		Edge d4 = new Edge(nodes[56],nodes[55]);
		
		Edge d5 = new Edge(nodes[54],nodes[60]);
		Edge d6 = new Edge(nodes[58],nodes[60]);
		Edge d7 = new Edge(nodes[55],nodes[60]);
		

		List<Node> nodeList4 = new ArrayList<Node>();
		for(int i=53; i<=60; i++) nodeList4.add(nodes[i]);
		
		Graph G4 = new Graph(nodeList4);
		System.out.println("G4 is "+Graph.spanningVisit(G4));
		System.out.println("Hub Constructor  is "+findHubConductor(G4));
		
		/* Fift Graph */
		Edge e1 = new Edge(nodes[61],nodes[62]);
		List<Node> nodeList5 = new ArrayList<Node>();
		for(int i=61; i<=62; i++) nodeList5.add(nodes[i]);
		
		Graph G5 = new Graph(nodeList5);
		System.out.println("G5 is "+Graph.spanningVisit(G5));
		System.out.println("Hub Constructor  is "+findHubConductor(G5));


		/* Sixth graph */
		Edge f1 = new Edge(nodes[70],nodes[64]);
		Edge f2 = new Edge(nodes[64],nodes[65]);
		Edge f3 = new Edge(nodes[65],nodes[66]);
		Edge f4 = new Edge(nodes[66],nodes[69]);
		
		Edge f5 = new Edge(nodes[68],nodes[69]);
		Edge f6 = new Edge(nodes[67],nodes[69]);
		Edge f7 = new Edge(nodes[63],nodes[69]);
		

		List<Node> nodeList6 = new ArrayList<Node>();
		for(int i=63; i<=70; i++) nodeList6.add(nodes[i]);
		
		Graph G6 = new Graph(nodeList6);
		System.out.println("G6 is "+Graph.spanningVisit(G6));
		System.out.println("Hub Constructor  is "+findHubConductor(G6));
		System.out.println("The end..");

	}
}
