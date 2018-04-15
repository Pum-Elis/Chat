package Server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 服务器类
 * @author eating
 *
 */

public class Server {
	public void startWork() throws IOException {
		ServerSocket serverSocket=new ServerSocket(4700);
		System.out.println("******我是服务器，正在等待客户端连接******");
		//list用存放客户端
		List<Socket> list_socket=new ArrayList<Socket>();//List是一个接口，用ArrayList类进行实例化
		Socket socket=null;
		int count=0;
		while(true) {
			socket=serverSocket.accept();
			count++;
			System.out.println("客户端"+count+"已连接~");
			//将客户端加入到List中
			list_socket.add(socket);
			//连接成功后开启线程处理
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
	 * 服务器线程，其主要作用是：
	 *1.接收来自客户端的信息
	 *2.将接收到的信息转发给各客户端
	 *
	 * @author eating
	 *
	 */

	public class ServerThread extends Thread{
		private Socket socket;
		private List<Socket>  list_socket;
	    private int count;  
	  
	    //构造方法
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
				//接收到“bye”，客户端就退出，并且将bye写入客户端
				if(message.equals("bye")) {
					writer=new PrintWriter(socket.getOutputStream());
					writer.println("bye");
					writer.flush();
					continue;
				}
				for(int i=0;i<list_socket.size();i++) {
					writer=new PrintWriter(list_socket.get(i).getOutputStream());
					writer.println("客户端"+count+"说："+message);
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
