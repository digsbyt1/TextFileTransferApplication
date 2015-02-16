import java.io.*;
import java.net.*;
public class FTthread extends Thread
{
	/*this is the beginning of thread file*/
	private int threadNumber;
	private Socket connectSocket;
	private File file;
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
				BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
				String line = null;
				int count = 0;
				PrintWriter write = null;
				file = null;
				while((line = reader.readLine()) !=null)
				{
					count++;
					if(count == 1)
					{
						file = new File(line);
						write = new PrintWriter(file);	
					}
					else
					{
						write.println(line);
						System.out.println(line);
					}
				}
				write.close();
				reader.close();
				connectSocket.close();
				System.out.println("everything closed");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
			
	}
}
