import java.net.*;
import java.io.*;
public class FTserver
{
	/*
		this is the beginning of server file 
	*/
	public static void main(String[] args)
	{
		int counter = 0;
		try
		{
			ServerSocket socket = new ServerSocket(1234);
			while(true)
			{
				System.out.println("first mode");
				Socket connectSocket = socket.accept();
				counter++;
				FTthread thread = new FTthread(counter, connectSocket);
				thread.start();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
} 
}
