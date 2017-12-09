package graph;
import java.util.*;

public class Edge
{
	private Node primo;
	private Node secondo;
	private int peso;
	private int occupata=0;
	private boolean oriented=false;
	private Label label=Label.UNEXPLORED;	
	private Label directionLabel=Label.FORWARD;	
	private Edge other=null;

	
	public Edge(Node f, Node s)
	{
		primo=f;
		secondo=s;
		peso=1;
		f.addAdiacenza(this);
		s.addAdiacenza(this);
	}
	
	public Edge(Node f,Node s,int p)
	{
		primo=f;
		secondo=s;
		peso=p;
		f.addAdiacenza(this);
		s.addAdiacenza(this);
	}

	public Edge(Node f,Node s,int p, int flow,boolean or)
	{
		primo=f;
		secondo=s;
		peso=p;
		oriented=or;
		occupata=flow;
		f.addAdiacenza(this);
		s.addAdiacenza(this);
	}
	
	
	public Edge(Node f,Node s,int p,boolean or)
	{
		primo=f;
		secondo=s;
		peso=p;
		oriented=or;
		f.addAdiacenza(this);
		s.addAdiacenza(this);
	}
	
	public Edge(Node f,Node s,int p,boolean or,Label l)
	{
		primo=f;
		secondo=s;
		peso=p;
		oriented=or;
		directionLabel=l;
		f.addAdiacenza(this);
		s.addAdiacenza(this);
	}
	
	public Edge(Node f,Node s,int p, int flow,boolean or,Label l)
	{
		primo=f;
		secondo=s;
		peso=p;
		occupata=flow;
		oriented=or;
		directionLabel=l;
		f.addAdiacenza(this);
		s.addAdiacenza(this);
	}
	
	public Edge(Node f,Node s, boolean or)
	{
		primo=f;
		secondo=s;
		peso=1;
		oriented=or;
		f.addAdiacenza(this);
		s.addAdiacenza(this);
	}
	

	public void setCapacity(int p)
	{
		peso=p;
	}
	
	public Edge getOther()
	{
		if(other==null)
		{
			other=new Edge(secondo,primo,peso,peso,oriented,directionLabel);
			other.setOther(this);
			return other;
		}else return other;
	}
	
	private void setOther(Edge e)
	{
		other = e;
	}
	
	public String toString()
	{
		if(oriented) return primo+"-{"+occupata+"/"+peso+"}->"+secondo;
		return primo+"<-{"+occupata+"/"+peso+"}->"+secondo;
	}
	
	public Node getFirst()
	{
		return primo;
	}
	
	public Node getSecond()
	{
		return secondo;
	}
	
	public void setOriented(boolean b)
	{
		oriented=b;
	}
	 
	public void setDirectionLabel(Label l)
	{
		directionLabel=l;
	}
	
	public Label getDirectionLabel()
	{
		return directionLabel;
	}
	
	public void setLabel(Label l)
	{
		label=l;
	}
	
	public Label getLabel()
	{
		return label;
	}
	
	public int getCapacity()
	{
		return peso;
	}

	public int getFlow()
	{
		return occupata;
	}

	public void setFlow(int f)
	{
		occupata=f;
	}
	
	public boolean isOriented()
	{
		return oriented;
	}
	
	
	public Node destination()
	{
		if(oriented) return secondo;
		else return null;
	}
}
