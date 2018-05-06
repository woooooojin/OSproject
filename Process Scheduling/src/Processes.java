
public class Processes {
	private int arrivalTime = 0;
	private int burstTime = 0;
	private int waitingTime = 0;
	private int turnaroundTime = 0;
	private double normalizedTime = 0.00000;
	
	public Processes() {
		
	}
	
	public void setAT(int arrivalTime) {
		this.arrivalTime =  arrivalTime;
	}
	
	public int getAT() {
		return arrivalTime;
	}
	
	public void setBT(int burstTime) {
		this.burstTime =  burstTime;
	}
	
	public int getBT() {
		return burstTime;
	}
	
	public void setWT(int waitingTime) {
		this.waitingTime = waitingTime;
	}
	
	public int getWT() {
		return waitingTime;
	}
	
	public void setTT(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}
	
	public int getTT() {
		return turnaroundTime;
	}
	
	public void setNT(double normalizedTime) {
		this.normalizedTime = normalizedTime;
	}
	
	public double getNT() {
		return normalizedTime;
	}

}
