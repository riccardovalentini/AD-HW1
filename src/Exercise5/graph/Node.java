package graph;
import java.util.*;

public class Node
{
	private String etichetta;
	private int id;
	private List<Edge> adiacenze=new LinkedList<Edge>();
	private Label label=Label.UNEXPLORED;
	private int discoveryLabel=0;
	private int leavingLabel=0;
	private int successors = -1;

	
	public Node(String e, int i)
	{
		etichetta=e;
		id=i;
	}
	

	public Node(String e, int i,List<Edge> a)
	{
		etichetta=e;
		id=i;
		adiacenze=a;
	}
	
	
	public void setSuccessorLabel(int x)
	{
		successors=x;
	}
	
	
	public int getSuccessorLabel()
	{
		return successors;
	}
	
	public String getEtichetta()
	{
		return etichetta;
	}
	
	public void addAdiacenza(Edge e)
	{
		adiacenze.add(e);
	}
	
	public void remove(Edge e)
	{
		adiacenze.remove(e);
	}
	
	public void setDiscoveryLabel(int d)
	{
		discoveryLabel=d;
	}
	
	public int getDiscoveryLabel()
	{
		return discoveryLabel;
	}
	
	public int getLeavingLabel()
	{
		return leavingLabel;
	}
	
	public void setLeavingLabel(int d)
	{
		leavingLabel=d;
	}
	
	public int getId()
	{
		return id;
	}
	
	public List<Edge> getAdiacenze()
	{
		return adiacenze;
	}
	
	public String toString()
	{
		return "("+etichetta+")";
	}
	
	public void setLabel(Label l)
	{
		label=l;
	}
	
	public Label getLabel()
	{
		return label;
	}
}
