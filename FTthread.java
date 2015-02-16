import java.io.*;
import java.net.*;
public class FTthread extends Thread
{
	/*this is the beginning of thread file*/
	private int threadNumber;
	private Socket connectSocket;
	public FTthread(int threadNumber, Socket connectSocket)
	{
		System.out.println("secondMode");
		this.threadNumber = threadNumber;
		this.connectSocket = connectSocket;
	} 
	public void run()
	{
		try
		{
			InputStream inStream = connectSocket.getInputStream();
				System.out.println(1);
				BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
				String line = null;
				while((line = reader.readLine()) !=null)
				{
					System.out.println(line);
				}
				reader.close();
				connectSocket.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
			
	}
}

