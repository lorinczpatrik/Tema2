package tema2_TP;

import java.util.ArrayList;
import java.util.Random;

public class Simulator {
	
	private ArrayList<ClientHandler> handlers;
	private ArrayList<Thread> executors;
	private int nr_op=0;
	private String fullReport="";
	private int val=100000;
	
	public Simulator(){
		nr_op=0;
		handlers=new ArrayList<ClientHandler>();
		executors=new ArrayList<Thread>();
	}
	
	public ArrayList<ClientHandler> getHandlers(){
		return handlers;
	}
	
	public void stopIt(){
		int i=nr_op;
		for(int j=0;j<i;j++){
			closeHandler(0);
		}
	}
	
	public void setVal(int val){
		this.val=val;
	}
	
	
	public void addClients(int i){
		for(int j=0;j<nr_op;j++){
			handlers.get(j).setClienti(generateClients(i/nr_op));
		}
		for(int j=0;j<i%nr_op;j++){
			handlers.get(j).setClienti(generateClients(1));
		}
	}
	
	public void closeHandler(int i){
		handlers.get(i).stop();
		ArrayList<Client> remaining=new ArrayList<Client>();
		int size;
		size=handlers.get(i).getClienti().size();
		if(size>0){
		remaining=handlers.get(i).redist(handlers.get(i).getClienti().size()-1);
		}
		
		executors.remove(i);
		fullReport=fullReport+handlers.get(i).writeReport();
		handlers.remove(i);
		
		nr_op=nr_op-1;
		if(nr_op!=0){
			
			handlers.get(0).setClienti(remaining);
			redist();
		}
		else{
			//exceptie
		}
	}
	
	public void redist(){
		if(nr_op>1){
			
		int avgsize=0;
		for(int j=0;j<nr_op;j++){
				avgsize=avgsize+(handlers.get(j).getClienti().size());
			
		}
		if(avgsize%nr_op==0){
			avgsize=avgsize/nr_op;
		}
		else{
			avgsize=avgsize/nr_op+1;
		}
		
		ArrayList<Client> surplus=new ArrayList<Client>();
		ArrayList<Client> aux=new ArrayList<Client>();

		
		for(int j=0;j<nr_op;j++){
			if(handlers.get(j).getClienti().size()>(avgsize)){
				surplus.addAll(handlers.get(j).redist(handlers.get(j).getClienti().size()-(avgsize)));
			}
		}
		
		for(int j=0;j<nr_op;j++){
			while(handlers.get(j).getClienti().size()<avgsize){
				if(surplus.size()>0){
					aux.add(surplus.get(0));
					handlers.get(j).setClienti(aux);
					surplus.remove(0);
					aux.remove(0);
				}
				else
					break;
			}
		}
		
		
		
		}
	}
	
	public void addHandlers(int i){
		ClientHandler c1;
		Thread t1;
		
		
		//System.out.println(val1+val2);
		for(int j=0;j<i;j++){
			nr_op=nr_op+1;
			
			c1=new ClientHandler(nr_op,System.currentTimeMillis(),val);
			t1=new Thread(c1);
			t1.start();
			
			handlers.add(c1);
			executors.add(t1);
		}
	}
	
	public double avgWait(){
		
		double sum=0;;
		
		for(int i=0;i<handlers.size();i++){
			sum=sum+handlers.get(i).myTime();
		}
		sum=sum/handlers.size();
		return sum;
	}
	
	public ArrayList<Client> generateClients(int nr){
		
		ArrayList<Client> lista=new ArrayList<Client>();
		Client c1;
		Random r2=new Random();
		int timetaken;
		long arrival;
		
		for(int i=0;i<nr;i++){

		timetaken=r2.nextInt(10)+10;
		timetaken=timetaken*100;
		arrival=System.currentTimeMillis();
		c1=new Client(timetaken,arrival);
		lista.add(c1);
		
		}
	
	return lista;
	}
	
	public String fileReport(){
		
		
		
		return "Simulare incheiata"+fullReport;
	}
	
}
