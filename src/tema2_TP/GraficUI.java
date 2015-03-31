package tema2_TP;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;








import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;




public class GraficUI extends JComponent{

	private JPanel contentPane;
	protected JButton b1,b2,b3;
	private ArrayList<ClientHandler> ch;
	private JTextField textField;
	private JTextField textField2;
	private JTextField textField3;
	private JTextField textField4;
	private JTextField textField5;
	private JTextField textField6;
	private JTextField textField7;
	private Simulator simuleaza;
	private long start=0;
	private long stop=0;
	private JLabel worldTime;
	private Timer sysTime;
	private Timer addClients;
	private int cMin=0;
	private int cMax=0;
	private boolean running=true;
	private JCheckBox closeCheck;
	private boolean closeChck;
	private int initCH;
	private int val1,val2;
	private JLabel avgWait;
	private double threashHold;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Random r=new Random();

	public GraficUI(){
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setSize(1000, 600);
		
		simuleaza=new Simulator();
		ch=simuleaza.getHandlers();
		
		
		//Start Simulation Buttons
		
		b1 = new JButton("Start");
		b1.setBounds(450, 40, 83, 23);
		add(b1);
		
		b1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				start=System.currentTimeMillis();
				
				
				
				//Initial CH
				try{
				String in="";
				in=textField.getText();
				initCH=Integer.parseInt(in);
				simuleaza.addHandlers(initCH);
				}
				catch(Exception e){
					//
				}
				
				try{
				String in;
				in=textField2.getText();
				int val;
				val=Integer.parseInt(in)*1000;
				stop=start+val;
				}catch(Exception e){
					
				}
				
				try{
					String in1,in2;
					in1=textField5.getText();
					in2=textField6.getText();
					val1=Integer.parseInt(in1)*1000;
					val2=Integer.parseInt(in2)*1000;
					closeChck=closeCheck.isSelected();
				}
				catch(Exception e){
					
				}
				
				threashHold=Double.parseDouble(textField7.getText());
				
				cMin=Integer.parseInt(textField3.getText())*100;
				cMax=Integer.parseInt(textField4.getText())*100;
				
				repaint();
				
				sysTime=new Timer(100, taskPerformer);
				addClients=new Timer(cMin,adding);
				
				sysTime.start();
				addClients.start();
			}
			
		});
		
		
		b2=new JButton("Stop");
		b2.setBounds(450,80,83,23);
		add(b2);
		
		b2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				running=false;
				
			}
			
		});
		
		b3=new JButton("Stop Clienti");
		b3.setBounds(600,110,130,23);
		add(b3);
		
		b3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				addClients.stop();
				simuleaza.redist();
				
			}
			
		});
		
		
		worldTime=new JLabel("SimTime");
		worldTime.setFont(new Font("Kalinga", Font.PLAIN, 11));
		worldTime.setBounds(600, 30, 50, 10);
		add(worldTime);
		
		avgWait=new JLabel("0");
		avgWait.setFont(new Font("Kalinga", Font.PLAIN, 11));
		avgWait.setBounds(700, 30, 50, 10);
		add(avgWait);
		
		JLabel dontPass = new JLabel("Open CH if Clients wait >");
		dontPass.setFont(new Font("Kalinga", Font.PLAIN, 11));
		dontPass.setBounds(600, 60, 150, 10);
		add(dontPass);
		
		textField7 = new JTextField();
		textField7.setBounds(600, 80, 50, 20);
		textField7.setColumns(10);
		add(textField7);
		
		
		JLabel initHandlers = new JLabel("Initial CH:");
		initHandlers.setFont(new Font("Kalinga", Font.PLAIN, 11));
		initHandlers.setBounds(100, 30, 55, 10);
		add(initHandlers);
		
		textField = new JTextField();
		textField.setBounds(195, 25, 50, 20);
		textField.setColumns(10);
		add(textField);
		
		JLabel simTime = new JLabel("Simulation Time:");
		simTime.setFont(new Font("Kalinga", Font.PLAIN, 11));
		simTime.setBounds(100, 60, 105, 10);
		add(simTime);
		
		textField2 = new JTextField();
		textField2.setBounds(195, 55, 50, 20);
		textField2.setColumns(10);
		add(textField2);
		
		JLabel ClientTime = new JLabel("Add Clients Interval:");
		ClientTime.setFont(new Font("Kalinga", Font.PLAIN, 11));
		ClientTime.setBounds(100, 90, 130, 10);
		add(ClientTime);
		
		textField3 = new JTextField();
		textField3.setBounds(100, 110, 50, 20);
		textField3.setColumns(10);
		add(textField3);
		
		textField4 = new JTextField();
		textField4.setBounds(160, 110, 50, 20);
		textField4.setColumns(10);
		add(textField4);
		
		JLabel CHTime = new JLabel("CH Service Time:");
		CHTime.setFont(new Font("Kalinga", Font.PLAIN, 11));
		CHTime.setBounds(280, 30, 130, 10);
		add(CHTime);
		
		textField5 = new JTextField();
		textField5.setBounds(280, 50, 50, 20);
		textField5.setColumns(10);
		add(textField5);
		
		textField6 = new JTextField();
		textField6.setBounds(340, 50, 50, 20);
		textField6.setColumns(10);
		add(textField6);
		
		JLabel CHClose = new JLabel("Close CH if no clients?");
		CHClose.setFont(new Font("Kalinga", Font.PLAIN, 11));
		CHClose.setBounds(280,80, 130, 10);
		add(CHClose);
		
		closeCheck = new JCheckBox("Yes"); 
		closeCheck.setSelected(false);
		closeCheck.setBounds(280, 100, 50, 15);
		closeCheck.setBackground(new Color(255,255,255));
		add(closeCheck);
		
	}
	
	
	public static void main(String[] args) {
		JFrame afisare=new JFrame("Queue Sim");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraficUI simWindow=new GraficUI();
					simWindow.setVisible(true);
					afisare.add(simWindow);
					afisare.pack();
					afisare.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					afisare.setLocationRelativeTo(null);
					afisare.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
	}
	
	ActionListener adding = new ActionListener() {
	      public void actionPerformed(ActionEvent event) {
	          //...Perform a task...
	    	 int nr;
	    	 nr=r.nextInt(cMax-cMin)+cMin;
	    	 simuleaza.addClients(1);
	    	addClients.setDelay(nr);
	    
	    	  
	      }
	  };
	
	
	ActionListener taskPerformer = new ActionListener() {
	      public void actionPerformed(ActionEvent event) {
	          //...Perform a task...
	    	  worldTime.setText(String.valueOf((stop-System.currentTimeMillis())/1000.0));
	    	  avgWait.setText(String.valueOf(simuleaza.avgWait()));
	    	  //Oprire simulare
	    	  if((System.currentTimeMillis()>=stop)|(!running)){
	    		  
	    		  worldTime.setText("Ended");
	    		  
	    		  simuleaza.stopIt();
	    		  addClients.stop();
	    		  sysTime.stop();
	    		  
	    		  System.out.println(simuleaza.fileReport());
	    		  
	    		  JTextArea final1=new JTextArea(10,20);
	    		  JScrollPane scroll = new JScrollPane(final1);
	    		  scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    		  scroll.setPreferredSize(null);
	    		  final1.setBounds(100,150,800,400);
	    		  final1.setText("\nSimulation Over\n Starte at: "+start+"\nEnded: "+stop+"\nResults:"+simuleaza.fileReport());
	    		  final1.setEditable(false);
	    		  add(final1);
	    		 // repaint();
	    		  
	    	  }
	    	  
	    	  if(simuleaza.avgWait()>threashHold){
	    		  Random r1=new Random();
	    		  int val;
	    		  val=r1.nextInt(val2-val1)+val1;
	    		  simuleaza.setVal(val);
	    		  simuleaza.addHandlers(1);
	    	  }
	    	  
	    	  simuleaza.redist();
	    	  
	    	  if(closeChck){
	    		  for(int i=initCH;i<ch.size();i++){
	    			  if(ch.get(i).getClienti().size()==0){
	    				  simuleaza.closeHandler(i);
	    			  }
	    		  }
	    	  }
	    	  
	    	  for(int i=0;i<ch.size();i++){
	    		  if(!ch.get(i).iStop()){
	    			  simuleaza.closeHandler(i);
	    		  }
	    	  }
	      }
	  };
	
	public Dimension getPreferredSize(){
		return new Dimension(1000,600);
	}
	
	@Override
	protected void paintComponent(Graphics show1){
		
		show1.setColor(new Color(255,255,255));
		show1.fillRect(0, 0, 1000, 600);
		int k=0;
		int j,i,rows;
		
		//Painting the interface
		
		if(ch.size()>=0){
		for(rows=0;rows<10 & rows<ch.size();rows++){
			
			
			
			show1.setColor(new Color(250,220,150));
			show1.fillRect(100, 150+rows*40,30, 30);
			show1.setColor(new Color(0,0,0));
			show1.drawString("CH", 106, 170+rows*40);
			
			k=ch.get(rows).getClienti().size();
			
			for(i=0, j=0;j<k&i<715;i+=11,j++)
			{
				show1.setColor(ch.get(rows).getClienti().get(j).getColor());
				show1.fillOval(132+i,160+rows*40,10,10);
			}
			if(k>65){
				show1.setColor(new Color(0,0,0));
				show1.drawString((k-65)+" more", 870, 170+rows*40);
			}
		}
		if(ch.size()>10){
			show1.setColor(new Color(0,0,0));
			show1.drawString((ch.size()-10)+" more", 106, 560);
		}
		repaint();
	}
	}	

}
