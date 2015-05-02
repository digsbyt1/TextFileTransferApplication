import java.io.*;
import java.net.*;
import java.util.Scanner;
public class FTthread extends Thread
{
 	private int threadNumber;
 	private Socket connectSocket;
 	private File file;
 	private BufferedReader reader;
 	private InputStream inStream;
  	private OutputStream oStream;
  	private PrintWriter write;
	private PrintWriter write2;
	private Scanner scan;
	private String string;
  	private File[] fileList;
	private int i;
	private boolean bool;
	/*
		The FTthread constructor creates
		input and ouput stream with the associated
		socket. It also creates the appropriate reader 
		and writer to be used in this class.
		@param threadNumber The thread number of the server.
		@param connectSocket is the socket that connects
		the client to the server.
	*/
  	public FTthread(int threadNumber, Socket connectSocket)
  	{
    		try
		{
			this.threadNumber = threadNumber;
    			this.connectSocket = connectSocket;
			inStream = connectSocket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inStream));
			oStream = connectSocket.getOutputStream();
			write = new PrintWriter(oStream,true);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong. Here is the error: "+e);
		}
  	}
	/*
		The run method calls the checkUploadOrDownload
		method.
	*/
  	public void run() 
	{
    		checkUploadOrDownload();
  	}
	/*
		The checkUploadOrDownload method reads
		from the client and determines whether 
		the client wants to upload or download
		a file.
	*/
  	public void checkUploadOrDownload()
  	{
    		try 
		{
      			if ((string =reader.readLine()).compareTo("download") == 0)
      			{
        			Thread.sleep(50);	
				download();
      			}
      			else if(string.compareTo("upload")==0)
        		{
				Thread.sleep(50);
				upload();
			}
			else
			{
				respond(3);
			}
    		}
    		catch (Exception e)
    		{
      			System.out.println("The error the program encountered was: " + e);
    		}
  	}
	/*
		The upload method accepts a file from
		the client and saves it to the server.
	*/
	public void upload()
  	{
    		respond(1);
    		try
    		{
    	     		i = Integer.parseInt(reader.readLine());
      			file = null;
      			if (!isThere(string = reader.readLine()))
      			{
				respond(1);
        			file = new File(string);
        			write2 = new PrintWriter(file);
				for(int j = 0; j<i;j++)
				{
        				Thread.sleep(50);
					string = reader.readLine();
          				write2.println(string);
          			}
				write2.close();	
			}
			else
      			{
        			respond(2);
      			}
			checkUploadOrDownload();
		}
    		catch (Exception e)
    		{
      			System.out.println(e);
    		}
  	}
	/*
		The download method sends the requested file
		to the client.
	*/
  	public void download() 
	{
    		respond(1);
   		try
    		{
      			file = new File("");//Place location of directory of downloadable files here
      			fileList = file.listFiles();
			write.println(fileList.length);
			for(int i = 0; i<fileList.length;i++)
			{
				write.println(fileList[i].getName());
			}
			if((string = reader.readLine()).compareTo("(cancel)")==0)
			{
				checkUploadOrDownload();
			}
			else
			{
				file = new File(string);
				scan = new Scanner(file);
				i = 0;
				while(scan.hasNextLine())
				{	
					string = scan.nextLine();
					i++;
				}
				write.println(i);
				scan = new Scanner(file);
				while (scan.hasNextLine())
        			{
					Thread.sleep(50);
          				write.println(scan.nextLine());
        			}
				checkUploadOrDownload();
			}
        	}
    		catch (Exception e)
    		{
      			System.out.println("The error that was produced in the program was: " +e);
    		}
  	}
	/*
		The respond method sends a 
		response to the client to 
		inform to continue or not to
		continue with its request.
		@param i An int the determines
		the response that is sent to 
		the client.
	*/
  	public void respond(int i)
  	{
    		try 
		{
        		if(i == 1)
			{
				write.println("ok");
			}
			else if(i == 2)
			{
				write.println("error");
			}
			else
			{
				connectSocket.close();
				
				inStream.close();
				oStream.close();
				
				
				while(true)
				{	
				}
			}
    		}
    		catch (Exception e)
    		{
      			System.out.println("The error that the program encountered was:" + e);
    		}
  	}
	/*
		The isThere method checks the server
		to see if the file requested to be 
		uploaded is already on the server.
		@param string The name of the file.
	*/
	public boolean isThere(String string)
	{
		bool = false;
		file = new File("");//Place location of directory of downloadable files here
		fileList = file.listFiles();
		for(int i = 0; i<fileList.length; i++)
		{
			if(fileList[i].getName().compareTo(string) == 0)
			{
				bool = true;
			}
		}
		return bool;
	}
}
