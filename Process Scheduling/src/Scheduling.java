import java.util.*;
import java.util.concurrent.TimeUnit;

public class Scheduling {
	protected Queue<Integer> readyQueue= new LinkedList<>();
	
	private Processes[] p;
	private int numProcess = 0;
	
	
	public Scheduling(int num) {
		Scanner in =  new Scanner(System.in);
		
		numProcess = num;
		p = new Processes[numProcess];
		
		System.out.print("Enter ArrivalTime of Processes : "  	);
		for(int i = 0; i < numProcess; i++ ) {
			p[i] = new Processes();
			p[i].setAT(in.nextInt());
		}
		
		System.out.print("Enter currentProcessBurstTime of Processes : ");
		for(int i = 0; i < numProcess; i++ ) {
			p[i].setBT(in.nextInt());
		}
	}
	
	public void FCFS() {
		int time = 0;      //프로그램 시간
		int currentProcessBurstTime = 0; //현재프로세스 수행시간
		int currentProcess = -1;    //현재 프로세스
		int arrivalProcess = -1;    //도착한 프로세스
		
		while(true) {
			System.out.println("Time --------------------> " + time);
			
			//도착한 프로세스가 있는지 확인
			arrivalProcess = arrival(time);
			if(arrivalProcess != -1) {          //도착한 프로세스가 있으면
				readyQueue.add(arrivalProcess);  //큐에 삽입
				arrivalProcess = -1;
				
				System.out.println("Queue State : "+ readyQueue.toString());
			}
			
			//현재 일을 수행하는 프로세스가 없으면 큐에서 꺼내옴
			if(currentProcess == -1) {
				if(!readyQueue.isEmpty()) {
					currentProcess = readyQueue.poll();
					System.out.println("Queue State : "+ readyQueue.toString());
					p[currentProcess].setWT(time-p[currentProcess].getAT()); //WT = 프로그램 시간 - AT
				}
			}
			
			//프로세스에게 할당된 수행시간과 현재 프로세스가 수행한 시간이 같으면
			if(p[currentProcess].getBT() == currentProcessBurstTime) { 
				p[currentProcess].setTT(p[currentProcess].getBT() + p[currentProcess].getWT());
				p[currentProcess].setNT((double)p[currentProcess].getTT() / p[currentProcess].getBT());
				
				//프로세스가 전부 수행되었는지 확인
				if((currentProcess+1) == numProcess) {
					System.out.println("All Process Complete! Progracm Exit!!");
					break;
				}else {  //수행할 프로세스가 남은 경우
					currentProcessBurstTime = 0;
					currentProcess = -1;
					
					//큐에서 다음 프로세스를 꺼내옴
					if(currentProcess == -1) {
						if(!readyQueue.isEmpty()) {
							currentProcess = readyQueue.poll();
							System.out.println("Queue State : "+ readyQueue.toString());
							p[currentProcess].setWT(time-p[currentProcess].getAT()); //WT = 프로그램 시간 - AT
						}
					}
				}
				
			}
			
			if(currentProcess != -1)	
				currentProcessBurstTime++; //일을 수행함
			time++;
		}
	}
	
	//현재 프로그램시간과 할당된 AT시간을 확인
	private int arrival(int time) {
		for(int index = 0; index < numProcess; index++) {
			if(time == p[index].getAT()) {
				return index;
			}
		}
		return -1;
	}
	
}
