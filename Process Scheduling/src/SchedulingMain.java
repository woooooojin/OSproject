import java.util.*;

public class SchedulingMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		
		System.out.print("Enter num Of Processes : ");
		
		Scheduling schedul = new Scheduling(in.nextInt());
		
		schedul.FCFS();
		

		
	}

}
