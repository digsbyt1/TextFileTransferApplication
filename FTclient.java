import javax.swing.JEditorPane;
import javax.swing.filechooser.*;
import java.util.Scanner;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
public class FTclient implements WindowConstants, SwingConstants
{
	/*This is the beginning of client file*/
	private Socket socket;
	private InputStream stream;
	private JFileChooser choose;
	private JFrame frameOne;
	private JPanel panelOne;
	private JPanel panelTwo;
	private JPanel panelThree;
	private JPanel panelFour;
	private JButton buttonOne;
	private JButton buttonTwo;
	private JLabel queryLabel;
	private JLabel instruct;
	private JTextField fileName;
	private final int WINDOW_WIDTH = 375;
	private final int WINDOW_HEIGHT = 100;
	private File file;	
	private BufferedReader reader;
	private OutputStream oStream;
	private PrintWriter remote;
	private PrintWriter local;
	private String string;
	private String[] listOfFiles;
	private JMenu menu;
	private	JList <String>list;
	private Scanner scan;
	private int time;
	private int num;
	private int status;
	private JScrollPane pane;
	public static void main(String[] args)
	{
		FTclient client = new FTclient();
	}
	/*
		This Constructor creates a connection with the server
		and creates input and output streams that will
		be used throughout the class.
	*/
	public FTclient()
	{
		try
		{
			socket = new Socket("10.0.0.11",1234);
			oStream = socket.getOutputStream();
			remote = new PrintWriter(oStream,true);
			stream = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(stream));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		startFrame();
	}

	/*
		The startFrame method creates the main GUI of the program.
		There are two buttons associated with the GUI,
		upload and download. Selecting upload will allow
		the user to upload a file to the server. Selecting
		download will allow the user to download a file
		from the server.
	*/
	public void startFrame()
	{
		frameOne = new JFrame();
		frameOne.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		frameOne.setTitle("Upload or Download");
		frameOne.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		build();
		frameOne.add(panelOne);
		frameOne.setVisible(true);
	}

	/*
		The build method creates the components of the main GUI 
		and adds the components to the main GUI.
	*/
	public void build()
	{
		frameOne.addWindowListener(new ThisWindowListener());//The windowListener will handle the closing of the main GUI
		panelTwo = new JPanel();
		panelTwo.setLayout(new GridLayout(1,2));
		panelOne = new JPanel();
		panelOne.setLayout(new GridLayout(2,1));
		buttonOne = new JButton("upload");
		buttonOne.addActionListener(new UploadButtonListener());
		buttonTwo = new JButton("download");
		buttonTwo.addActionListener(new DownloadButtonListener());
		queryLabel = new JLabel("Do you want to upload or download a text file? ");
		queryLabel.setHorizontalAlignment(CENTER);
		panelTwo.add(buttonOne);
		panelTwo.add(buttonTwo);
		panelOne.add(queryLabel);
		panelOne.add(panelTwo);
	}
	/*
		The ThisWindowListener class handles
		the makes sure that all streams,sockets,readers, 
		and writers are closed before gracefully closing
		the program. 
	*/
	public class ThisWindowListener implements WindowListener
	{
		/*
			The windowClosing method closes all streams, sockets, readers, and 
			writers before closing the program.
			@param e A WindowEvent that is triggered when there 
			is a change in the state of the window.
		*/
		public void windowClosing(WindowEvent e)
		{
			remote.println("closing");
			try
			{
				socket.close();
				stream.close();
				oStream.close();
				reader.close();
				remote.close();
			}
			catch(Exception d)
			{
				System.out.println(d);
			}
			System.exit(0);
			
		}
		/*
			The windowDeactivated method does not yet do anything.	
			@param e A WindowEvent that is triggered when there
			is a change in the stat of the window.
		*/
		public void windowDeactivated(WindowEvent e)
		{}
		/*	
		 	The windowDeactivated method does not yet do anything.   
                        @param e A WindowEvent that is triggered when there
                        is a change in the stat of the window.
		*/
		public void windowActivated(WindowEvent e)
		{}
		/*
			The windowActivated method does not yet do anything.   
                        @param e A WindowEvent that is triggered when there
                        is a change in the stat of the window.
		*/
		public void windowClosed(WindowEvent e)
		{}
		/*
			The windowClosed method does not yet do anything.   
                        @param e A WindowEvent that is triggered when there
                        is a change in the stat of the window.
		*/
		public void windowDeiconified(WindowEvent e)
		{}
		/*
			The windowDeiconified method does not yet do anything.   
                        @param e A WindowEvent that is triggered when there
                        is a change in the stat of the window.
		*/
		public void windowIconified(WindowEvent e)
		{}
		/*
			The windowIconified method does not yet do anything.   
                        @param e A WindowEvent that is triggered when there
                        is a change in the stat of the window.
		*/
		public void windowOpened(WindowEvent e)
		{}
	}
	/*
		The UploadButtonListener class creates the upload 
		filechooser window.
	*/
	public class UploadButtonListener implements ActionListener
	{
		/*
			The actionPerformed method creates and show 
			a fileChooser. The user will be allowed to 
			choose a file to upload to the server.
			@param e An ActionEvent that is triggered 
			When upload is selected.
		*/
		public void actionPerformed(ActionEvent e)
		{
			frameOne.setVisible(false);
			choose = new JFileChooser();
			choose.setFileFilter(new FileNameExtensionFilter("Text files","txt"));
			choose.setApproveButtonText("upload");
			choose.setDialogTitle("upload File");
			status = choose.showOpenDialog(choose);
			if(status==JFileChooser.APPROVE_OPTION)
			{	
				file = choose.getSelectedFile();
				buildSaveFileName(file.getName());
			}
			else
			{
				startFrame();
			}

		}
	}
	/*
		The buildSaveFileName method creates
		a GUI. The GUI will give the user
		The option to change the name of the file
		before uploading the file to the server.
		@param name The name(String) of the original file. 
		
	*/
	public void buildSaveFileName(String name)
	{
		frameOne = new JFrame();
		frameOne.addWindowListener(new ThisWindowListener());
		frameOne.setSize(550,120);
		frameOne.setTitle("save and upload");
		frameOne.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		panelTwo = new JPanel();
		panelTwo.setLayout(new GridLayout(3,1));
		buttonOne = new JButton("save");//reuse button for save.
		buttonOne.addActionListener(new SaveButtonListener());
		instruct = new JLabel();
		instruct.setText("What would you like to name the file? i.e. Example.txt(you must place a .txt at the end).");
		fileName = new JTextField(20);
		fileName.setText(name);
		fileName.select(0,name.lastIndexOf("."));
		panelOne = new JPanel();
		panelThree  = new JPanel();
		panelFour = new JPanel();
		panelOne.add(buttonOne);
		panelThree.add(instruct);
		panelFour.add(fileName);
		panelTwo.add(panelThree);
		panelTwo.add(panelFour);
		panelTwo.add(panelOne); 
		frameOne.add(panelTwo);
		frameOne.setVisible(true);
		
		
	}
	/*
		The SaveButtonListener class is initiated 
		when the user selects the save button inside of the
		save and upload window.
	*/
	public class SaveButtonListener implements ActionListener
	{
		/*
			The actionPerformed method hides the
			save and upload GUI and calls
			The uploadFile method.
			@param e An ActionEvent that is triggered
                        When save is selected.
		*/
		public void actionPerformed(ActionEvent e)
		{
			frameOne.setVisible(false);
			uploadFile(file);
		}
	}
	/*
		The DownloadButtonListener class
		is initiated when the download button
		is selected.
	*/
	public class DownloadButtonListener implements ActionListener
	{
		/*
			The actioPerformed method hides 
			the upload/download GUI
			and calls the getList method.
			@param e An ActionEvent that is triggered 
			when download is selected.
		*/
		public void actionPerformed(ActionEvent e)
		{
			frameOne.setVisible(false);
			getList();
		}
	}
	/*
		The getList method sends a request for all 
		files on the server.
	*/
	public void getList()
	{
		try
		{
			remote.println("download");
			string = reader.readLine();
			num = 0;
			time = Integer.parseInt(reader.readLine());
			listOfFiles = new String[time];
			while(num < time)
			{
				string = reader.readLine();
				listOfFiles[num] = string;
				num++;
			}
			buildMenu();
		}
		catch(Exception d)
		{
			System.out.println("something went wrong: "+d);
		}
	}
	/*
		The buildMenu method creates
		a GUI for the user. The user
		will be able see all files on 
		the server and will be able 
		to select a file to download
		from the server.
	*/
	public void buildMenu()
	{
		frameOne = new JFrame();
		frameOne.addWindowListener(new ThisWindowListener());
		frameOne.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		frameOne.setTitle("Download Window");
		list = new JList<String>(listOfFiles);
		buttonOne = new JButton("download");
		buttonOne.addActionListener(new DownloadingListener());
		buttonTwo = new JButton("cancel");
		buttonTwo.addActionListener(new CancelButtonListener());
		panelOne = new JPanel();
		panelTwo = new JPanel();		
		panelTwo.add(buttonOne);
		panelTwo.add(buttonTwo);
		panelOne.setLayout(new BorderLayout());
		list.setVisibleRowCount(3);
		list.setLayoutOrientation(JList.VERTICAL);
		instruct = new JLabel("Select what file you wan't to download");
		pane  = new JScrollPane(list);
		instruct.setHorizontalAlignment(SwingConstants.CENTER);
		pane.setViewportView(list);
		pane.setVerticalScrollBar(pane.createVerticalScrollBar());	
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		pane.setPreferredSize(new Dimension(200,100));
		panelTwo.setPreferredSize(new Dimension(200,100));
		panelOne.add(instruct, BorderLayout.NORTH);
		panelOne.add(pane,BorderLayout.CENTER);
		panelOne.add(panelTwo,BorderLayout.SOUTH);
		frameOne.add(panelOne);
		frameOne.pack();
		frameOne.setVisible(true);
	}
	/*
		The DownloadingListener class is
		initiated when the dowload button is
		selected from the Download Window.
	*/
	public class DownloadingListener implements ActionListener
	{
		/*
			The actionPerformed method requests a
			file from the server to be dowloaded
			and saves the file in the current directory.
			@param e An ActionEvent that is triggered 
			when the download button of the Download
			window is selected. 
		*/
		public void actionPerformed(ActionEvent e)
		{
			frameOne.setVisible(false);
			try
			{
				remote.println(list.getSelectedValue());	
				file = new File(list.getSelectedValue());
				local = new PrintWriter(file);
				num = Integer.parseInt( reader.readLine());
				for(int j = 0; j < num; j++)
				{
					local.println(reader.readLine());
				}
				local.close();
				JOptionPane.showMessageDialog(null,"Download complete.");		
			}
			catch(Exception d)
			{
				System.out.println(d);
			}
			startFrame();
			
		}
	}
	/*
		The CancelButtonListener is initiated 
		when the cancel button of the download
		Window is selected.
	*/
	public class CancelButtonListener implements ActionListener
	{
		/*
			The actionPerformed method hides the
			download window and creates a new
			upload/download window.
			@param e An ActionEvent that 
			is triggered when the cancel
			button of the download window
			is selected.
		*/
		public void actionPerformed(ActionEvent e)
		{
			remote.println("(cancel)");
			frameOne.setVisible(false);
			startFrame();
		}
	}
	/*
		The uploadFile method uploads
		a file to the server.
		@param file The file to be uploaded.
	*/
	public void uploadFile(File file)
	{
		try
		{
			scan = new Scanner(file);
			remote.println("upload");
			if(reader.readLine().compareTo("error")==0)
			{
				JOptionPane.showMessageDialog(null,"was not able to recieve a response.");
				startFrame();
			}
			else
			{
				num = 0;
				while(scan.hasNext())
				{
					num++;
					scan.nextLine();
				}
				remote.println(num);
				remote.println(fileName.getText());
				string = reader.readLine();
				if(string.compareTo("ok")==0)
				{
					scan = new Scanner(file);
					while(scan.hasNextLine())
					{
						String line = scan.nextLine();
						remote.println(line);
					}
					JOptionPane.showMessageDialog(null,"Upload complete.");
				}
				else
				{
					JOptionPane.showMessageDialog(null,"the name that you entered is already on file please choose another name.");
				}
			}
			startFrame();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
