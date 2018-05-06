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
		int time = 0;      //���α׷� �ð�
		int currentProcessBurstTime = 0; //�������μ��� ����ð�
		int currentProcess = -1;    //���� ���μ���
		int arrivalProcess = -1;    //������ ���μ���
		
		while(true) {
			System.out.println("Time --------------------> " + time);
			
			//������ ���μ����� �ִ��� Ȯ��
			arrivalProcess = arrival(time);
			if(arrivalProcess != -1) {          //������ ���μ����� ������
				readyQueue.add(arrivalProcess);  //ť�� ����
				arrivalProcess = -1;
				
				System.out.println("Queue State : "+ readyQueue.toString());
			}
			
			//���� ���� �����ϴ� ���μ����� ������ ť���� ������
			if(currentProcess == -1) {
				if(!readyQueue.isEmpty()) {
					currentProcess = readyQueue.poll();
					System.out.println("Queue State : "+ readyQueue.toString());
					p[currentProcess].setWT(time-p[currentProcess].getAT()); //WT = ���α׷� �ð� - AT
				}
			}
			
			//���μ������� �Ҵ�� ����ð��� ���� ���μ����� ������ �ð��� ������
			if(p[currentProcess].getBT() == currentProcessBurstTime) { 
				p[currentProcess].setTT(p[currentProcess].getBT() + p[currentProcess].getWT());
				p[currentProcess].setNT((double)p[currentProcess].getTT() / p[currentProcess].getBT());
				
				//���μ����� ���� ����Ǿ����� Ȯ��
				if((currentProcess+1) == numProcess) {
					System.out.println("All Process Complete! Progracm Exit!!");
					break;
				}else {  //������ ���μ����� ���� ���
					currentProcessBurstTime = 0;
					currentProcess = -1;
					
					//ť���� ���� ���μ����� ������
					if(currentProcess == -1) {
						if(!readyQueue.isEmpty()) {
							currentProcess = readyQueue.poll();
							System.out.println("Queue State : "+ readyQueue.toString());
							p[currentProcess].setWT(time-p[currentProcess].getAT()); //WT = ���α׷� �ð� - AT
						}
					}
				}
				
			}
			
			if(currentProcess != -1)	
				currentProcessBurstTime++; //���� ������
			time++;
		}
	}
	
	//���� ���α׷��ð��� �Ҵ�� AT�ð��� Ȯ��
	private int arrival(int time) {
		for(int index = 0; index < numProcess; index++) {
			if(time == p[index].getAT()) {
				return index;
			}
		}
		return -1;
	}
	
}
