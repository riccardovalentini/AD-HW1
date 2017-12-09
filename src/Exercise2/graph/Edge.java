package graph;
import java.util.*;

public class Edge
{
	private Node primo;
	private Node secondo;
	private int peso;
	private boolean oriented=false;
	private Label label=Label.UNEXPLORED;
	
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
	
	public Edge(Node f,Node s,int p,boolean or)
	{
		primo=f;
		secondo=s;
		peso=p;
		oriented=or;
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
	

	
	public String toString()
	{
		if(oriented) return primo+"-->"+secondo;
		return primo+"<->"+secondo;
	}
	
	public Node getFirst()
	{
		return primo;
	}
	
	public Node getSecond()
	{
		return secondo;
	}
	 
	public void setLabel(Label l)
	{
		label=l;
	}
	
	public Label getLabel()
	{
		return label;
	}
	
	public int getPeso()
	{
		return peso;
	}
	
	public boolean isOriented()
	{
		return oriented;
	}
}
