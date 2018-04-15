package Client;

import java.io.*;
import java.net.*;

/**
 * 客户端:
 * 创建客户端并启动接收消息和发送消息的线程
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
		System.out.println("******欢迎您进入聊天器******");
		new SendMsg(socket).start();
		new ReadMsg(socket).start();
	}
	 public static void main(String[] args) throws UnknownHostException, IOException {  
	        Client client=new Client("127.0.0.1");
	        client.startWork();
	  }
	 
	 
	 /**
	  * 接收服务器信息的线程
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
					//由Socket对象得到输入流，并构造相应的BufferedReader对象
					buff =new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String message=null;
					while(true) {
						message=buff.readLine();
						System.out.println(message);
						//当收到“bye”的消息，则退出线程
						if(message.equals("bye")) {
							System.out.println("bye");
							System.out.println("******结束聊天******");
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
	  * 发送消息线程
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
	 			//由Socket对象得到输入流，并构造相应的BufferedReader对象
				buff=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//由Socket对象得到输出流，并构造PrintWriter对象
				writer=new PrintWriter(socket.getOutputStream());
				//由系统标准输入设备构造BufferedReader对象
				BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
				//在标准输出上打印从客户端读入的字符串
				//System.out.println(buff.readLine());
	 			//String message =sin.readLine();
	 			while(true) {
	 				String message =sin.readLine();
	 				//System.out.println(message);
	 				if(message.equals("bye")) {
	 					break;
	 				}
	 				
	 				//向服务器端发送消息
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
