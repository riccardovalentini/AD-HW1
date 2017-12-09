import java.util.*;

public class ProductMax
{
	public static double max(double x, double y)
	{
		return x>y?x:y;
	}
	
	public static double max(double x, double y, double z)
	{
		return max(x,max(y,z));
	}
	
	public static double productMax(double[] A)
	{
		int n=A.length;
		double[] M = new double[n+1];
		M[0]=A[0];
		
		double predecessor=0;
		
		for(int j=1; j<n+1; j++)
		{
			M[j]=max(A[j-1],A[j-1]*predecessor,M[j-1]);
			predecessor=max(A[j-1],A[j-1]*predecessor);
		}
		return M[n];
	} 
	
	
	public static void main(String[] args)
	{
		double[] array1 = new double[]{1,1,0.5,3,6,9,0.5,9,9,0,0};
		double[] array2 = new double[]{8,8,0.5,0.5,0.5,0,0,8,7,0,0,1,0.75,0.9,64};
		double[] array3 = new double[]{8,8,0.5,0.5,0.5,0,0,8,7,0,0,1,0.75,0.9,64,1.1,0,1,64,0};
		double[] array4 = new double[]{8,8,0.5,0,0.5,180,0,0.5,0,0,8,7,0,0,1,0.75,0.9,64,1.1,0,1,64,0};
		double[] array5 = new double[]{0.1,0.1,0.1};
		double[] array6 = new double[]{0,0};
		double[] array7 = new double[]{0.75,0.75,0.75,0.75,0.75,0.5,9,0.5,0.75,0,0.75,0.75,0.75};
		double[] array8 = new double[]{0.75,0.75,0.75,0.75,0.75,2,9,0.5,0.75,0,0.75,0.75,0.75};
		double[] array9 = new double[]{0.75,0.75,0.75,0.75,2,0.75,9,0.5,0.75,0,0.75,0.75,0.75};
		double[] array10 = new double[]{0.75,0.75,2,0.75,0.75,0.75,9,0.5,0.75,0,0.75,0.75,0.75};
		double[] array11 = new double[]{0.75,0.75,0.75,0.75,0.75,9,0.75,0.75,2,0.75,0.75,0.75};
		double[] array12 = new double[]{0.75,0.75,0.75,0.75,0.75,9,0.75,0.75,0,0.75,2,0.75,0.75};
		double[] array13 = new double[]{0.75,0.75,0.75,2,0.75,0.75,9,0.75,0.75,0,0.75,0.75,0.75};
		double[] array14 = new double[]{81,0.75,0.75,2,0.75,0.75,9,0.75,0.75,0,0.75,0.75,0.75};
		double[] array15 = new double[]{81,0.75,0.75,0.1,0.75,0.75,9,0.75,0.75,0,0.75,0.75,0.75};
		double[] array16 = new double[]{9,0.75,0.75,0.1,0.75,0.75,81,0.75,0.75,0,0.75,0.75,0.75};
		double[] array17 = new double[]{1,1,0.5,3,6,9,0.001,9,9,0,0};


		System.out.println("Test1; Expected: 6561.0, found: "+productMax(array1));
		System.out.println("Test2; Expected: 64.0, found: "+productMax(array2));
		System.out.println("Test3; Expected: 70.4, found: "+productMax(array3));
		System.out.println("Test4; Expected: 180, found: "+productMax(array4));
		System.out.println("Test5; Expected: 0.1, found: "+productMax(array5));
		System.out.println("Test6; Expected: 0.0, found: "+productMax(array6));
		System.out.println("Test7; Expected: 9.0, found: "+productMax(array7));
		System.out.println("Test8; Expected: 18.0, found: "+productMax(array8));
		System.out.println("Test9; Expected: 13.5, found: "+productMax(array9));
		System.out.println("Test10; Expected: 9.0, found: "+productMax(array10));
		System.out.println("Test11; Expected: 10.125, found: "+productMax(array11));
		System.out.println("Test12; Expected: 9.0, found: "+productMax(array12));
		System.out.println("Test13; Expected: 10.125, found: "+productMax(array13));
		System.out.println("Test14; Expected: 461.32, found: "+productMax(array14));
		System.out.println("Test15; Expected: 81.0, found: "+productMax(array15));
		System.out.println("Test16; Expected: 81.0, found: "+productMax(array16));
		System.out.println("Test17; Expected: 162.0, found: "+productMax(array17));

	}
	
}
