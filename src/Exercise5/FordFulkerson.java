import graph.*;
import java.util.*;


public class FordFulkerson
{
	static int MAX = 10000;
	
	public static class Tris
	{
		Graph g;
		Node s;
		Node t;
		
		public Tris(Graph G, Node S, Node T)
		{
			g=G;
			s=S;
			t=T;
		}
		
		public Graph getG()
		{
			return g;
		}
		
		public Node getS()
		{
			return s;
		}
		
		public Node getT()
		{
			return t;
		}
	}
	
	public static Graph fordFulkerson(Graph G,Node s, Node t) throws CloneNotSupportedException 
	{
		System.out.println("Executing Ford Fulkerson!");
		List<Edge> edges = G.edges();
		for(Edge e:edges) e.getOther();
		Graph Gf = new Graph(G.nodes());
		
		List<Edge> path = hasPath(Gf,s,t,"FF");
		while(path!=null)
		{
			int b=augment(path);
			System.out.println("B:"+b+","+path);
			path=hasPath(Gf,s,t,"FF");
		}
		return Gf;
	}
	

	
	public static List<Edge> findPath(Graph G, Node s,Node t)
	{
		List<Edge> path = new ArrayList<Edge>();
		for(Edge e: G.outGoing(s))
		{
			if(e.getLabel()==Label.VISITED) continue;
			if(e.getCapacity()-e.getFlow()==0) continue;
			e.setLabel(Label.VISITED);
			Node dest = G.opposite(e,s);
			if(dest==t)
			{
				path.add(0,e);
				return path;
			}
			List<Edge> partialPath = findPath(G,dest,t);
			if(partialPath!=null)
			{
				partialPath.add(0,e);
				return partialPath;
				
			}
		}
		return null; 		
	}


	
	public static List<Edge> hasPath(Graph G,Node s, Node t,String algorithm)
	{
		List<Edge> path;
		path = findPath(G,s,t);
		Graph.resetForVisit(G);
		return path;
	}
	
	public static int augment(List<Edge> path)
	{
		int b=bottleneck(path);
		for(Edge e: path)
		{
			e.setFlow(e.getFlow()+b);
			Edge other = e.getOther();
			other.setFlow(other.getFlow()-b);
		}
		return b;
	}
	

	
	public static int bottleneck(List<Edge> path)
	{
		int min = path.get(0).getCapacity()-path.get(0).getFlow();
		for(Edge e: path)
		{
			int hp=	e.getCapacity()-e.getFlow();
			if(hp<min) min=hp;
		}
		return min;
	}


	public static int performChallenges(String[]r,int[]cr,String[]c,int[]cMaxR,int[]p) throws CloneNotSupportedException
	{
		List<Node> nodes = new ArrayList<Node>();
		List<Node> rNodes = new ArrayList<Node>();
		List<Node> cNodes = new ArrayList<Node>();
		List<Edge> edges = new ArrayList<Edge>();
		List<Edge> sEdges = new LinkedList<Edge>();
		int m = r.length;
		int n = c.length;
		Node s=new Node("s",0),t=new Node("t",m+n+1);
		for(int i=0; i<m;i++)
		{
			Node node=new Node(r[i],i+1);
			nodes.add(0,node);
			rNodes.add(0,node);
		}
		for(int i=0; i<n; i++)
		{
			Node node=new Node(c[i],cMaxR[i]);
			nodes.add(0,node);
			cNodes.add(0,node);
		}
		nodes.add(0,t);
		nodes.add(0,s);
		for(int i=0; i<m; i++)
		{
			Edge e=new Edge(s,rNodes.get(m-1-i),cr[i],true);
			edges.add(e);
			sEdges.add(e);
		}	
		Collections.reverse(cNodes);
		Collections.reverse(rNodes);		
		List<Edge> cEdges = new ArrayList<Edge>();
		for(int i=0; i<n; i++)
		{
			Edge e=new Edge(cNodes.get(i),t,p[i],true);
			edges.add(0,e);
			cEdges.add(0,e);
			int crMax=cNodes.get(i).getId();
			for(int j=0; j<crMax; j++)
			{
				edges.add(0,new Edge(rNodes.get(j),cNodes.get(i),MAX,true));
			}
		}
		Graph g = new Graph(nodes);
		Graph res= fordFulkerson(g,s,t);
		for(Edge C: cEdges)
		{
			List<Edge> path = hasPath(res,s,C.getFirst(),"dd");
			System.out.println(path);
			if(path!=null) C.setDirectionLabel(Label.IMPOSSIBLE);
		}
		int max=0, win=0,price=0;
		for(Edge C: cEdges)
		{
			if(C.getDirectionLabel()==Label.IMPOSSIBLE) continue;
			System.out.println(C);
			if(max<C.getFirst().getId()) max=C.getFirst().getId();
			win+=C.getCapacity();
		}
		for(Edge R: sEdges)
		{
			System.out.println(R);
			if(R.getSecond().getId()<=max)
			{
				price+=R.getCapacity(); 
			}
		}
		return win-price;
		
	}
	
	public static void printArray(int[] array)
	{
		String s="[ ";
		for(int i=0; i<array.length; i++) s+=array[i]+" ";
		s+="]";
		System.out.println(s);
	}

	
	public static void main(String[] args) throws CloneNotSupportedException 
	{
		Node s=new Node("s",0),n1=new Node("n1",1),n2=new Node("n2",2),
			 n3=new Node("n3",3),n4=new Node("n4",4),n5=new Node("n5",5),t=new Node("t",6);
		Node[] nodes1 = new Node[]{s,n1,n2,n3,n4,n5,t};
		Edge e1 = new Edge(s,n1,10,true);
		Edge e2 = new Edge(s,n2,10,true);
		Edge e3 = new Edge(n1,n2,2,true);
		Edge e4 = new Edge(n1,n3,4,true);
		Edge e5 = new Edge(n2,n4,9,true);
		Edge e6 = new Edge(n1,n4,8,true);
		Edge e7 = new Edge(n4,n3,6,true);
		Edge e8 = new Edge(n3,t,10,true);
		Edge e9 = new Edge(n4,t,10,true);
		
		Graph g = new Graph(nodes1);
		fordFulkerson(g,s,t);
		//Graph.visitDFS(g);
		
		String[] r1 = new String[]{"r1","r2","r3","r4"};
		int[] cr1 = new int[]{2,6,8,11};
		String[] c1 = new String[]{"c1","c2"};
		int[] cMaxR1 = new int[]{2,4};
		int[] p1 = new int[]{9,16};
		int res1=performChallenges(r1,cr1,c1,cMaxR1,p1);
		System.out.println("Max revenue: "+res1);

		String[] r2 = new String[]{"r1","r2","r3","r4","r5","r6"};
		int[] cr2 = new int[]{1,2,3,4,5,6};
		String[] c2 = new String[]{"c1","c2","c3","c4"};
		int[] cMaxR2 = new int[]{1,2,4,6};
		int[] p2 = new int[]{2,5,12,21};
		int res2=performChallenges(r2,cr2,c2,cMaxR2,p2);
		System.out.println("Max revenue: "+res2);
		
		String[] r3 = new String[]{"r1","r2","r3","r4","r5","r6"};
		int[] cr3 = new int[]{5,10,12,12,12,20};
		String[] c3 = new String[]{"c1","c2","c3"};
		int[] cMaxR3 = new int[]{6,2,3};
		int[] p3 = new int[]{80,15,29};
		int res3=performChallenges(r3,cr3,c3,cMaxR3,p3);
		System.out.println("Max revenue: "+res3);
		
		String[] r4 = new String[]{"r1","r2","r3","r4"};
		int[] cr4 = new int[]{4,5,6,7};
		String[] c4 = new String[]{"c1","c2"};
		int[] cMaxR4 = new int[]{1,4};
		int[] p4 = new int[]{3,150};
		int res4=performChallenges(r4,cr4,c4,cMaxR4,p4);
		System.out.println("Max revenue: "+res4);

		String[] r5 = new String[]{"r1","r2","r3","r4"};
		int[] cr5 = new int[]{10,20,30,200};
		String[] c5 = new String[]{"c1","c2","c3"};
		int[] cMaxR5 = new int[]{4,3,1};
		int[] p5 = new int[]{150,80,20};
		int res5=performChallenges(r5,cr5,c5,cMaxR5,p5);
		System.out.println("Max revenue: "+res5);


		String[] r6 = new String[]{"r1","r2","r3","r4","r5"};
		int[] cr6 = new int[]{5,10,12,20,70};
		String[] c6 = new String[]{"c2","c1","c5","c4","c3"};
		int[] cMaxR6 = new int[]{2,1,5,4,3};
		int[] p6 = new int[]{10,5,25,20,15};
		int res6=performChallenges(r6,cr6,c6,cMaxR6,p6);
		System.out.println("Max revenue: "+res6);
		
		String[] r7 = new String[]{"r1","r2","r3"};
		int[] cr7 = new int[]{5,25,25};
		String[] c7 = new String[]{"c1","c2"};
		int[] cMaxR7 = new int[]{1,3};
		int[] p7 = new int[]{15,30};
		int res7=performChallenges(r7,cr7,c7,cMaxR7,p7);
		System.out.println("Max revenue: "+res7);

		String[] r8 = new String[]{"r1","r2","r3"};
		int[] cr8 = new int[]{20,50,19};
		String[] c8 = new String[]{"c1","c2","c3"};
		int[] cMaxR8 = new int[]{1,2,3};
		int[] p8 = new int[]{20,20,50};
		int res8=performChallenges(r8,cr8,c8,cMaxR8,p8);
		System.out.println("Max revenue: "+res8);

		String[] r9 = new String[]{"r1","r2","r3","r4"};
		int[] cr9 = new int[]{20,30,40,50};
		String[] c9 = new String[]{"c1","c2","c3"};
		int[] cMaxR9 = new int[]{1,3,4};
		int[] p9 = new int[]{10,90,30};
		int res9=performChallenges(r9,cr9,c9,cMaxR9,p9);
		System.out.println("Max revenue: "+res9);

		String[] r10 = new String[]{"r1","r2","r3","r4"};
		int[] cr10 = new int[]{30,1,1,200};
		String[] c10 = new String[]{"c1","c2","c3","c4"};
		int[] cMaxR10 = new int[]{1,2,3,4};
		int[] p10 = new int[]{1,2,30,22};
		int res10=performChallenges(r10,cr10,c10,cMaxR10,p10);
		System.out.println("Max revenue: "+res10);
	}
}
