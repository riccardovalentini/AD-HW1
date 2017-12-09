import java.util.*;

public class Scheduler
{
	
	static class Interval
	{
		int begin;
		int end;
		String jobTag;
		
		public Interval(String j,int b,int e)
		{
			jobTag=j;
			begin=b;
			end=e;
		}
		
		public String toString()
		{
			return "["+jobTag+"] of length "+(end-begin)+" | ";
		}
		
		public int size()
		{
			return end-begin;
		}
	}
	
	static class Machine
	{
		List<Interval> M;
		int left;
		int length=0;
		
		public Machine()
		{
			M=new ArrayList<Interval>();
			left=0;
		}
		
		public void add(Interval j)
		{
			M.add(0,j);
			length+=j.size();
		}
		
		public void setLeft(int l)
		{
			left=l;
		}
		
		public String toString()
		{
			int size=M.size();
			String s="";
			for(int i=size-1; i>=0; i--)
			{
				s+=M.get(i).toString();
			}
			return s;
		}
	}
	
	static class Job
	{
		String tag;
		int duration;
		int right;
		
		public Job(String t,int d)
		{
			tag=t;
			duration=d;
		}
		
		public void setRight(int r)
		{
			right=r;
		}
		
		public String toString()
		{
			return tag+" of time "+duration+"";
		}
	}
	
	
	public static int max(int x, int y)
	{
		return x>y?x:y;
	}
	
	public static int min(int x, int y)
	{
		return x<y?x:y;
	}
	
	public static int divide(int x,int y)
	{
		return x%y>0? (x/y)+1 : x/y;
	}
	
	
		public static List<Machine> doTheSchedule(Job[] jobs, int m)
	{
		int n=jobs.length;
		int maxJob=0,sumOfJobs=0,avg=0;
		for(int i=0; i<n; i++)
		{
			sumOfJobs+=jobs[i].duration;
			if(jobs[i].duration > maxJob) maxJob=jobs[i].duration;
		}
		avg=divide(sumOfJobs,m);
		
		/* COMPUTING GREEDY CMAX */
		int Cmax=max(avg,maxJob);
		for(int i=0; i<n; i++) jobs[i].setRight(Cmax);
		
		/* Pre-For */
		Machine M = new Machine();
		ArrayList<Machine> schedule = new ArrayList<Machine>();
		
		for(int i=0; i<n; i++)
		{
			// our job
			Job j=jobs[i];
			boolean concluded=false;
			int reservedTime = 0;
			
			Interval inter = null;
			while(j.duration > 0)
			{
				if(j.duration < Cmax-M.left) reservedTime = j.duration;
				else reservedTime = Cmax-M.left;
				inter = new Interval(j.tag,M.left,M.left+reservedTime);
				M.setLeft(M.left+reservedTime);
				M.add(inter);
				if(M.left==Cmax || (M.left > 0 && i==n-1) )
				{
					schedule.add(M);
					M=new Machine();
				}		
				j.duration = j.duration-reservedTime;
				if(j.duration == 0) continue; 
			}
		}
		return schedule;
		
	}
	
	

	
	public static Job[] copy(Job[] array)
	{
		Job[] res = new Job[array.length];
		for(int i=0; i<res.length; i++)
		{
			Job j = new Job(array[i].tag,array[i].duration);
			res[i]=j;
		}
		return res;
	}
	
	public static void printSchedule(String tag,Job[]array,int machines)
	{
		System.out.println("###################### Schedule "+tag+" ######################");
		System.out.println("The schedule requires "+machines+" machines.");
		System.out.println("The jobs are the following:");
		Job[] acopy = copy(array);
		for(int i=0; i<acopy.length; i++)
		{
			System.out.println("["+acopy[i].toString()+"]");
		}
		ArrayList<Machine> s = (ArrayList<Machine>)doTheSchedule(acopy,machines);
		boolean allRight=true;
		for(int i=0; i<acopy.length; i++)
		{
			if(acopy[i].duration > 0) allRight=false;
		}
		System.out.println("-------------------------------------------------------------");
		if(allRight) System.out.println("All jobs are been scheduled! ");
		else
		{
			System.out.println("Something wrong happened!");
			return;
		}
		System.out.println("The resulting schedule is the following:");
		int n=s.size();
		int max=0;
		for(int i=0; i<n; i++)
		{
			Machine m=s.get(i);
			System.out.println("M"+(i+1)+" has "+m+"");
			if(m.length > max) max=m.length; 
		} 
		System.out.println("Cmax is "+max);
		System.out.println("###############################################################");

	}
	
	public static void main(String[] args)
	{
		Job j1 = new Job("j1",1);
		Job j2 = new Job("j2",2);
		Job j3 = new Job("j3",3);
		Job j4 = new Job("j4",4);
		Job j5 = new Job("j5",5);
		Job j6 = new Job("j6",6);
		Job j7 = new Job("j7",7);
		Job j8 = new Job("j8",8);
		Job j9 = new Job("j9",9);
		Job j10 = new Job("j10",10);

		
		Job[] jobs1=new Job[]{j5,j7,j8,j10};



		printSchedule("1",jobs1,2);
		printSchedule("2",jobs1,3);
		printSchedule("3",jobs1,4);



	}
}
