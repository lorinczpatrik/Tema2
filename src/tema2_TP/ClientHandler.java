package tema2_TP;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ClientHandler implements Runnable{
	private volatile boolean running;
	private String myReport="";
	private ArrayList<Client> clienti;
	private int name;
	private long date;
	private int val;
	
	public ClientHandler(int name,long date,int val){
		this.name=name;
		clienti=new ArrayList<Client>();
		running=true;
		this.date=date;
		this.val=val;
		Random r=new Random();
		val=val+r.nextInt(5);
	}
	
	public void run(){
		while(running){
			if(!clienti.isEmpty()){
				try {
					Thread.sleep(clienti.get(0).getTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				myReport=myReport+clienti.get(0).toString();
				clienti.remove(0);
				
			}
			if(System.currentTimeMillis()>date+val){
				running=false;
			}
		}
	}
	
	public boolean iStop(){
		return running;
	}
	
	public void stop(){
		running=false;
	}
	
	public void setClienti(List<Client> clienti){
			this.clienti.addAll(clienti);
		
	}
	
	public ArrayList<Client> getClienti(){
		return clienti;
	}
	
	public double myTime(){
		double sum=0;
		for(int j=0;j<clienti.size();j++){
			for(int i=0;i<j;i++){
				sum=sum+clienti.get(i).getTime();
			}
		}
		sum=(sum*1.0)/clienti.size();
		return sum;
	}
	
	public ArrayList<Client> redist(int i){	
		ArrayList<Client> aux=new ArrayList<Client>();
		int k=clienti.size()-i;
		int size;
		size=clienti.size();
		for(int j=clienti.size()-i;j<size;j++){
			aux.add(clienti.get(k));
			clienti.remove(k);
		}
		
		return aux;
	}
	
	public String writeReport(){
		
		return "\n CH "+name+"\nStarted woking at:"+
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(date))+
				"\n "+myReport;
	}
}
