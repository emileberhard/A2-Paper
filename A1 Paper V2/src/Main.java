import java.util.Scanner;

public class Main {
	
	final static double ratio = Math.pow(2.0, (-5.0/4.0))/Math.pow(2.0, (-3.0/4.0));
	final static double a2Area = Math.pow(2.0, (-5.0/4.0))*Math.pow(2.0, (-3.0/4.0));
	
	public static void main(String[] args) {
		// Set-up
		Scanner s = new Scanner(System.in);
		double tapeLength = 0;
		int smallestSize = s.nextInt();
		double[] ihopKlistringsTejp = new double[smallestSize - 1];
		double[] papers = new double[smallestSize - 1];
		PappersBunt[] pappersBuntar = new PappersBunt[smallestSize - 1];

		// Scanna in
		for(int i = 0; i < papers.length; i++) {
			papers[i] = s.nextDouble();
		}

		// Klistra ihop mindre pappersbuntar till så stora papper som möjligt och kom ihåg hur mkt tejp som används
		for(int i = papers.length - 1; i > 0; i--) {
			PappersBunt p = new PappersBunt(i, papers[i]);
			p = tejpaIhop(p);
			tapeLength += p.tejp;
			ihopKlistringsTejp[i] += p.tejp;
			if(p.size < i) {
				papers[p.size] += p.n;
				papers[i] = 0;
			}
		}
		
		System.out.println(papers[0]);
		
		// Ta bort 1 från udda nummer
		for(int i = 0; i < papers.length; i++) {
			if(papers[i] != 1) {
				papers[i] = papers[i] - (papers[i]%2);
			}
		}

		// Kolla om total area = A1
		double totalArea = 0;	
		for(int i = 0; i < papers.length; i++) {
			totalArea += ((a2Area/Math.pow(2, i))*papers[i]);
		}
		if(totalArea < a2Area*2) {
			System.out.println("impossible");
		}else {
			// Kalkylering	
			double currentArea = 0;
			for(int i = 0; i < papers.length; i++) {
				double tape = Math.pow(2.0, (-3.0/4.0))*Math.pow(ratio, i);
				
				if(papers[i] == 1) // Potentiellt problem
					tapeLength += tape;
				else
					tapeLength += tape*(papers[i]/2);
				currentArea += (a2Area/Math.pow(2, i))*papers[i];
				
				if(currentArea == a2Area*2)
					break;
				
				if(currentArea > a2Area*2) {
					int pageExcess = (int)Math.round(((currentArea - (a2Area*2))/(a2Area/Math.pow(2, i)))/2);
					tapeLength -= tape*pageExcess;
					break;
				}
			}
			//
			// Output
			System.out.println(tapeLength);
		}
	}
	
	
	
	
	
	
	
	

	//tejpar ihop mindre papper till ett/två så stort/stora papper som möjligt
	static PappersBunt tejpaIhop(PappersBunt p) {

		if(p.n > 1 && p.size > 0) {
			p.n -= p.n%2;
			p.tejp += (p.n/2)*Math.pow(2.0, (-3.0/4.0))*Math.pow(ratio, p.size);
			p.n /= 2;
			p.size--;
		}
		
		if(p.n < 2.0 || p.size == 0) {
			return p;
		}
			
		else
			return tejpaIhop(p);
	}
}
