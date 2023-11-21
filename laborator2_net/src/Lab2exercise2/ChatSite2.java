package Lab2exercise2;
import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class ChatSite2 {
	public static void main(String args[]) throws IOException {
		JFrame frame = new JFrame("Chat App 2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640,640);
		
		JTextArea heading = new JTextArea();
		heading.setBounds(10,10,600,50);
		heading.setText("Chat Site 2");
		heading.setEditable(false);
		frame.add(heading);
		
		JTextField inputField = new JTextField(" <Write Here> ");
		inputField.setBounds(10, 410, 500, 30);
		frame.add(inputField);

		JTextArea outputArea = new JTextArea();
		outputArea.setBounds(10,100,600,300);
		outputArea.setEditable(false);
		frame.add(outputArea);
		
		JButton button = new JButton("Send");
		button.setBounds(530, 410, 80, 30);
		button.setBackground(Color.GREEN);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Sender2( outputArea,inputField).start();
			}
		});
		frame.add(button);
		
		frame.setLayout(null);
		frame.setVisible(true);
		
		new Receiver2("Serve",outputArea).start();
	}
}

class Sender2 extends Thread{
	
	private JTextField inputField;
	JTextArea outputArea;
	
	public Sender2(JTextArea outputArea, JTextField inputField) {
		this.outputArea=outputArea;
		this.inputField = inputField;

	}

	public void run() {
		try {
			DatagramSocket socket = new DatagramSocket();
			byte[] buf = new byte[256];
			InetAddress adress = InetAddress.getByName("localhost"); 
			
			String send = null;
			send = inputField.getText();
			inputField.setText("");
			buf = send.getBytes();
			outputArea.append("\n (Me)>"+send);
			
			DatagramPacket packet = new DatagramPacket(buf, buf.length, adress, 4445);
			socket.send(packet);
			socket.close();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}

class Receiver2 extends Thread{
	
	private DatagramSocket socket = null;

	JTextArea outputArea;
	public Receiver2(String name,JTextArea outputArea) throws IOException {
		super(name);
		socket = new DatagramSocket(4446);
		this.outputArea = outputArea;
	}
	
	public void run() {
		while(true) {
			try {
			byte[] buf = new byte[256];
			
			DatagramPacket packet= new DatagramPacket(buf, buf.length);
			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			socket.receive(packet);
		
			String received = new String(packet.getData());
			outputArea.append("\n ("+ port+")>>"+received);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}