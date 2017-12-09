package graph;
import java.util.*;

public class Graph implements Cloneable 
{
	private List<Node> vertici=new ArrayList<Node>();
	private List<Edge> archi=new ArrayList<Edge>();
	private int population;
	private boolean isOriented=true;
	private int discoveryLabel=0;
	private int leavingLabel=0;
	
	public static class SingleCicleRecord
	{
		List<Node> list=new ArrayList<Node>();
		int i=0;
		
		public void add(Node v)
		{
			list.add(0,v);
			i++;
		}
		
		public String toString()
		{
			return ""+list.toString();
		}
	}
	
	public Graph clone() throws CloneNotSupportedException 
	{
        try
        {
			return (Graph)super.clone(); // or or another implementation
		}catch(Exception e)
		{
			System.out.println("Clone not supported");
			return null;
		}
    }
	
	public  int getGrado(Node v)
	{
		int count=0;
		if(!isOriented) return v.getAdiacenze().size();
		for(Edge e: v.getAdiacenze())
		{
			if(e.getSecond()==v) count++;
		}
		return count;
	}
	
	public Graph(List<Node> l)
	{
		int i=0;
		int len=l.size();
		for(i=0; i<len; i++)
		{
			Node temp=l.get(i);
			if(temp.getAdiacenze()!=null) vertici.add(temp);
			int leng=temp.getAdiacenze().size();
			for(int j=0; j<leng; j++) 
			{
				Edge a=temp.getAdiacenze().get(j);
				if(!a.isOriented()) isOriented=false;
				if(archi.contains(a)==false) archi.add(a);
			}
		}
		population=i;
	}
	
	public Graph(Node[] l)
	{
		int i=0;
		int len=l.length;
		for(i=0; i<len; i++)
		{
			Node temp=l[i];
			if(temp.getAdiacenze()!=null) vertici.add(temp);
			int leng=temp.getAdiacenze().size();
			for(int j=0; j<leng; j++) 
			{
				Edge a=temp.getAdiacenze().get(j);
				if(!a.isOriented()) isOriented=false;
				if(archi.contains(a)==false) archi.add(a);
			}
		}
		population=i;
	}
	
	public static LinkedList<Node> topologicalSort(Graph G)
	{
		LinkedList<Node> topo=new LinkedList<Node>();
		Map<Node,Integer> count=new HashMap<>();
		LinkedList<Node> ready=new LinkedList<Node>();
		
		for(Node v: G.vertici)
		{
			count.put(v,G.getGrado(v));
			if(count.get(v)==0) ready.add(v);
		}
		while(!ready.isEmpty())
		{
			Node u=ready.remove(0);
			topo.add(u);
			for(Edge e: G.outGoing(u))
			{
				Node v=G.opposite(e,u);	
				count.put(v,count.get(v)-1);
				if(count.get(v)==0) ready.add(v);
			}
			
		}
		return topo;
	}
	
	public Node opposite(Edge e,Node v)
	{
		int i=0,len=v.getAdiacenze().size();
		for(i=0; i<len; i++)
		{
			if(v.getAdiacenze().get(i)==e)
			{
				if(v==e.getFirst()) return e.getSecond();
				return e.getFirst();
			}
		}
		return null;
	}
	
	public int getNextDiscoveryLabel()
	{
		return discoveryLabel+1;
	}
	
	public int getDiscoveryLabel()
	{
		return discoveryLabel;
	}
	
	public int getNextLeavingLabel()
	{
		return leavingLabel+1;
	}
	
	public int getLeavingLabel()
	{
		return leavingLabel;
	}
	
	public Node getFirstNode()
	{
		return vertici.get(0);
	}
	
	
	public void setDiscoveryLabel(Node v, int l)
	{
		discoveryLabel=l;
		v.setDiscoveryLabel(l);
	}
	
	public void setLeavingLabel(Node v, int l)
	{
		leavingLabel=l;
		v.setLeavingLabel(l);
	}
	
	public List<Edge> outGoing(Node v)
	{
		if(isOriented)
		{
			int len=v.getAdiacenze().size();
			LinkedList<Edge> list=new LinkedList<Edge>();
			for(int i=0; i<len; i++)
			{
				Edge elem=v.getAdiacenze().get(i);
				if(elem.getFirst()==v) list.add(elem);
			}
			return list;
		}else
		{
			return v.getAdiacenze();
		}
	}
	
	public List<Edge> inGoing(Node v)
	{
		if(isOriented)
		{
			int len=v.getAdiacenze().size();
			LinkedList<Edge> list=new LinkedList<Edge>();
			for(int i=0; i<len; i++)
			{
				Edge elem=v.getAdiacenze().get(i);
				if(elem.getSecond()==v) list.add(elem);
			}
			return list;
		}else
		{
			return v.getAdiacenze();
		}
	}
	
	public List<Edge> edges()
	{
		return archi;
	}
	
	public Node getNodeAt(int i)
	{
		return vertici.get(i);
	}
	
	public List<Node> nodes()
	{
		return vertici;
	}

	
	public static int visitDFS(Graph G)
	{
		System.out.println("--------  DFS ---------");
		int l1=G.vertici.size(),l2=G.archi.size();
		for(int i=0; i<l1; i++)
		{
			Node v=G.vertici.get(i);
			v.setLabel(Label.UNEXPLORED);
			G.setLeavingLabel(v,0);
			G.setDiscoveryLabel(v,0);
		}
		for(int i=0; i<l2; i++)
		{
			Edge a=G.archi.get(i);
			a.setLabel(Label.UNEXPLORED);
		}
		int count=0;
		for(int i=0; i<l1; i++)
		{
			Node v=G.vertici.get(i);
			if(v.getLabel()==Label.UNEXPLORED)
			{
				if(G.isOriented) DDFS(G,v);
				else DFS(G,v);
				count++;
			}
		}
		System.out.println("-------- --------- ---------");
		System.out.println("N° of nodes: "+l1);
		resetForVisit(G);
		return count;
	}
	
	
	public boolean areNeighbours(Node g, Node u)
	{
		int i=0;
		int len=g.getAdiacenze().size();
		for(i=0; i<len; i++)
		{
			Edge e=g.getAdiacenze().get(i);
			if(e.getFirst()==u || e.getSecond()==u) return true;
		}
		return false;
	}
	
	public static void visitBFS(Graph G)
	{
		System.out.println("-------- BFS ---------");
		int l1=G.vertici.size(),l2=G.archi.size();
		for(int i=0; i<l1; i++)
		{
			Node v=G.vertici.get(i);
			v.setLabel(Label.UNEXPLORED);
		}
		for(int i=0; i<l2; i++)
		{
			Edge a=G.archi.get(i);
			a.setLabel(Label.UNEXPLORED);
		}
		for(int i=0; i<l1; i++)
		{
			Node v=G.vertici.get(i);
			if(v.getLabel()==Label.UNEXPLORED) BFS(G,v);
		}
		System.out.println("-------- --------- ---------");
	}
	
	private static void DFS(Graph G,Node v)
	{
		if(v.getLabel()!=Label.VISITED) System.out.println(v);
		v.setLabel(Label.VISITED);
		int i=0,len=v.getAdiacenze().size();
		for(i=0; i<len; i++)
		{
			Edge e=v.getAdiacenze().get(i);
			if(e.getLabel()==Label.UNEXPLORED)
			{
				System.out.println("Going on "+e);
				Node u=G.opposite(e,v);
				if(u.getLabel()==Label.UNEXPLORED)
				{
					e.setLabel(Label.DISCOVERY);
					DFS(G,u);
				}else e.setLabel(Label.BACK);
			}
		}
	}


	private static void DDFS(Graph G,Node v)
	{
		if(v.getDiscoveryLabel()==0) System.out.println(v);
		G.setDiscoveryLabel(v,G.getNextDiscoveryLabel());
		int len=G.outGoing(v).size();
		for(int i=0; i<len; i++)
		{
			Edge e=G.outGoing(v).get(i);
			Node w;
			if(e.getLabel()==Label.UNEXPLORED)
			{
				System.out.println("Going on "+e);
				w=G.opposite(e,v);
			}else
			{
				e.setLabel(Label.CROSS);
				continue;
			}
			if(w.getDiscoveryLabel()==0)
			{
				e.setLabel(Label.DISCOVERY);
				DDFS(G,w);
			}else if(w.getLeavingLabel()==0) 
			{
				e.setLabel(Label.BACK);
			}else if(v.getDiscoveryLabel()<w.getDiscoveryLabel())
			{
				e.setLabel(Label.FORWARD);
			}else
			{
				e.setLabel(Label.CROSS);
			}
			
		}
		G.setLeavingLabel(v,G.getNextLeavingLabel());
	}
	
	public static void resetForVisit(Graph G)
	{
		G.leavingLabel=0;
		G.discoveryLabel=0;
		int l1=G.vertici.size(),l2=G.archi.size();
		for(int i=0; i<l1; i++)
		{
			Node v=G.vertici.get(i);
			v.setLabel(Label.UNEXPLORED);
			G.setLeavingLabel(v,0);
			G.setDiscoveryLabel(v,0);
		}
		for(int i=0; i<l2; i++)
		{
			Edge a=G.archi.get(i);
			a.setLabel(Label.UNEXPLORED);
		}
	}
	
	public static SingleCicleRecord DDFS(Graph G,Node v,Node v1,int k)
	{
		if(!G.isOriented()) return null;
		G.setDiscoveryLabel(v,G.getNextDiscoveryLabel());
		int len=G.outGoing(v).size();
		SingleCicleRecord record=null;
		for(int i=0; i<len; i++)
		{
			Edge e=G.outGoing(v).get(i);
			Node w;
			if(e.getLabel()==Label.UNEXPLORED)
			{
				w=G.opposite(e,v);
			}else
			{
				e.setLabel(Label.CROSS);
				continue;
			}
			if(w.getDiscoveryLabel()==0)
			{
				e.setLabel(Label.DISCOVERY);
				record=DDFS(G,w,v1,k);
				if(record!=null && record.i<k)
				{
					record.add(w);
					return record;
				}
			}else if(w.getLeavingLabel()==0) 
			{
				e.setLabel(Label.BACK);
				Node x=G.opposite(e,v);
				if(x==v1)
				{
					record=new SingleCicleRecord();
					record.add(x);
					return record;
				}
			}else if(v.getDiscoveryLabel()<w.getDiscoveryLabel())
			{
				e.setLabel(Label.FORWARD);
			}else
			{
				e.setLabel(Label.CROSS);
			}
			
		}
		G.setLeavingLabel(v,G.getNextLeavingLabel());
		return record;
	}
	
	public static LinkedList<Node> findPath(Graph G,Node v,Node z)
	{
		LinkedList<Node> path=null;
		v.setLabel(Label.VISITED);
		System.out.println();
		if(v==z)
		{
			path=new LinkedList<Node>();
			path.add(0,v);
			return path;
		}
		int len=G.outGoing(v).size();
		for(int i=0; i<len; i++)
		{
			LinkedList<Node> aux=null;
			Edge e=G.outGoing(v).get(i);
			if(e.getLabel()==Label.UNEXPLORED)
			{
				e.setLabel(Label.DISCOVERY);
				Node w=G.opposite(e,v);
				aux=findPath(G,w,z);
			}
			if(path==null) path=aux;
			if((path!=null && aux!=null)&& path.size() > aux.size()) path=aux;
		}
		if(path!=null) path.add(0,v);
		return path; 
	}
	
	public static String spanningVisit(Graph G)
	{
		 LinkedList<Node> list=new LinkedList<Node>();
		 int c=visitDFS(G);
		 int len=G.archi.size();
		 boolean isSpanning=true;
		 for(int i=0; i<len; i++)
		 {
			 Edge e=G.archi.get(i);
			 if(e.getLabel()!=Label.BACK && e.getLabel()!=Label.FORWARD && e.getLabel()!=Label.CROSS) continue;
			 isSpanning=false;
		 }
		 if(!G.isOriented&&isSpanning && c>1) return "a spanning forest with "+c+" connected components";
		 if(!G.isOriented&&isSpanning) return "a spanning tree";
		 if(c>1) return " a graph with "+c+" connected components";
		 return "a graph with one connected component";
	}
	
	private static void BFS(Graph G,Node s)
	{
		LinkedList<Node> L=new LinkedList<Node>();
		if(s.getLabel()!=Label.VISITED)
		s.setLabel(Label.VISITED);
		L.add(s);
		System.out.println(s);
		while(!L.isEmpty())
		{
			int len=L.size();
			for(int i=0; i<len; i++)
			{
				Node v=L.get(0);
				int l2=G.outGoing(v).size();
				for(int j=0; j<l2; j++)
				{
					Edge e=G.outGoing(v).get(j);
					if(e.getLabel()==Label.UNEXPLORED)
					{
						System.out.println("Procedo su "+e);
						e.setLabel(Label.DISCOVERY);
						Node u=G.opposite(e,v);
						if(u.getLabel()!=Label.VISITED)
						{
							System.out.println(u);
							u.setLabel(Label.VISITED);
							L.add(u);
						}
					}else e.setLabel(Label.CROSS);
				}
				L.remove(v);
			}
		}
		
	}
	
	public static Node findMaxCommon(Graph G,Node v)
	{
		v.setLabel(Label.VISITED);
		LinkedList<Node> L=new LinkedList<Node>();
		int n=0,iter=0;
		Node x=null;
		L.add(0,v);
		while(!L.isEmpty())
		{
			int size=L.size();
			System.out.println(L+","+size);
			for(int i=0; i<size; i++)
			{
				Node u=L.get(0);
				int n1=0;
				for(Edge e: G.outGoing(u))
				{
					if(e.getLabel()==Label.UNEXPLORED)
					{
						e.setLabel(Label.DISCOVERY);
						Node v1=G.opposite(e,u);
						if(v1.getLabel()!=Label.VISITED)
						{
							v1.setLabel(Label.VISITED);
							if(iter==1) n1++;
							if(n<n1)
							{
								n=n1;
								x=v1;
							}
							L.add(v1);
						}
					}else e.setLabel(Label.CROSS);
					L.remove(u);
				}
				
			}
			iter++;
			if(iter==3) return x;
		}
		return x;
	}
	
	public boolean isOriented()
	{
		return isOriented;
	}
	
	public static int searchRetweetBst(Graph G, Node a,Node b)
	{
		a.setLabel(Label.VISITED);
		LinkedList<Node> L=new LinkedList<Node>();
		L.add(0,a);
		int countRetweet=0;
		while(!L.isEmpty())
		{
			countRetweet++;
			int size=L.size();
			for(int i=0; i<size; i++)
			{
				Node u=L.get(0);
				for(Edge e: G.outGoing(u))
				{
					if(e.getLabel()==Label.UNEXPLORED)
					{
						e.setLabel(Label.DISCOVERY);
						Node v = G.opposite(e,u);
						if(v.getLabel()==Label.UNEXPLORED)
						{
							v.setLabel(Label.DISCOVERY);
							L.add(v);
							if(v==b) return countRetweet;
						}
					}
				}
				L.remove(u);
			}
		}
		return -1;
		
	}
	
}
