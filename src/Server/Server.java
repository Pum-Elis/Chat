package Server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * ��������
 * @author eating
 *
 */

public class Server {
	public void startWork() throws IOException {
		ServerSocket serverSocket=new ServerSocket(4700);
		System.out.println("******���Ƿ����������ڵȴ��ͻ�������******");
		//list�ô�ſͻ���
		List<Socket> list_socket=new ArrayList<Socket>();//List��һ���ӿڣ���ArrayList�����ʵ����
		Socket socket=null;
		int count=0;
		while(true) {
			socket=serverSocket.accept();
			count++;
			System.out.println("�ͻ���"+count+"������~");
			//���ͻ��˼��뵽List��
			list_socket.add(socket);
			//���ӳɹ������̴߳���
			new ServerThread(count,socket,list_socket).start();
			//serverSocket.close();
		}
		//serverSocket.close();
	}
	public static void main(String[] args) throws Exception {
		try {
		      Server server=new Server();
		      server.startWork();
		    }
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * �������̣߳�����Ҫ�����ǣ�
	 *1.�������Կͻ��˵���Ϣ
	 *2.�����յ�����Ϣת�������ͻ���
	 *
	 * @author eating
	 *
	 */

	public class ServerThread extends Thread{
		private Socket socket;
		private List<Socket>  list_socket;
	    private int count;  
	  
	    //���췽��
	    public ServerThread(int count,Socket socket,List<Socket> list_socket) {  
	        this.count = count;  
	        this.socket = socket;
	        this.list_socket=list_socket;
	    }  
	  
	    public void run() {  
	       BufferedReader buff=null;
	       PrintWriter writer=null;
	       try {
			buff=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message=null;
			while(true) {
				message =buff.readLine();
				//���յ���bye�����ͻ��˾��˳������ҽ�byeд��ͻ���
				if(message.equals("bye")) {
					writer=new PrintWriter(socket.getOutputStream());
					writer.println("bye");
					writer.flush();
					continue;
				}
				for(int i=0;i<list_socket.size();i++) {
					writer=new PrintWriter(list_socket.get(i).getOutputStream());
					writer.println("�ͻ���"+count+"˵��"+message);
					writer.flush();
				}
			}
	       } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
	    }  
	  
	}

}
