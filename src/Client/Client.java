package Client;

import java.io.*;
import java.net.*;

/**
 * �ͻ���:
 * �����ͻ��˲�����������Ϣ�ͷ�����Ϣ���߳�
 * 
 * @author eating
 *
 */

public class Client {
	private String ip;
	
	public Client(String ip) {
		this.ip=ip;
	}
	
	public void startWork() throws UnknownHostException, IOException {
		Socket socket=new Socket(ip,4700);
		System.out.println("******��ӭ������������******");
		new SendMsg(socket).start();
		new ReadMsg(socket).start();
	}
	 public static void main(String[] args) throws UnknownHostException, IOException {  
	        Client client=new Client("127.0.0.1");
	        client.startWork();
	  }
	 
	 
	 /**
	  * ���շ�������Ϣ���߳�
	  * @author eating
	  *
	  */
	 public class ReadMsg extends Thread{
			private Socket socket;
			
			public ReadMsg(Socket socket) {
				this.socket=socket;
			}
			
			public void run() {
				BufferedReader buff=null;
				try {
					//��Socket����õ�����������������Ӧ��BufferedReader����
					buff =new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String message=null;
					while(true) {
						message=buff.readLine();
						System.out.println(message);
						//���յ���bye������Ϣ�����˳��߳�
						if(message.equals("bye")) {
							System.out.println("bye");
							System.out.println("******��������******");
							break;
						}
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {
						buff.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		}
	 
	 /**
	  * ������Ϣ�߳�
	  * @author eating
	  *
	  */                

	 public class SendMsg extends Thread{
	 	private Socket socket;
	 	
	 	public SendMsg(Socket socket) {
	 		this.socket=socket;
	 	}
	 	
	 	public void run() {
	 		BufferedReader buff =null;
	 		PrintWriter writer=null;
	 		try {
	 			//��Socket����õ�����������������Ӧ��BufferedReader����
				buff=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//��Socket����õ��������������PrintWriter����
				writer=new PrintWriter(socket.getOutputStream());
				//��ϵͳ��׼�����豸����BufferedReader����
				BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
				//�ڱ�׼����ϴ�ӡ�ӿͻ��˶�����ַ���
				//System.out.println(buff.readLine());
	 			//String message =sin.readLine();
	 			while(true) {
	 				String message =sin.readLine();
	 				//System.out.println(message);
	 				if(message.equals("bye")) {
	 					break;
	 				}
	 				
	 				//��������˷�����Ϣ
	 				writer.println(message);
	 				writer.flush();
	 			}
	 		} catch (IOException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}finally {
	 			if (buff!=null) {
	 				try {
						buff.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	 			}
	 			if(writer!=null) {
	 				writer.close();
	 			}
	 		}
	 		
	 	}

	 }

}
