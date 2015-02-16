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
	Socket socket;
	private JFileChooser choose;
	private JFrame frameOne;
	private JFrame frameTwo;
	private JPanel mainPanel;
	private JPanel secondaryPanel;
	private JPanel mainPanelTwo;
	private JPanel secondaryPanelTwo;
	private JButton upload;
	private JButton download;
	private JButton selectDownload;
	private JButton cancel;
	private JLabel queryLabel;
	private JLabel instruct;
	private JTextField fileName;
	private final int WINDOW_WIDTH = 375;
	private final int WINDOW_HEIGHT = 100;

	public static void main(String[] args)
	{
		FTclient client = new FTclient();
	}

	public FTclient()
	{
		startFrame();
	}

	/*
		This method initializes and presents the applet.
	*/
	public void startFrame()
	{
		frameOne = new JFrame();
		frameOne.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		frameOne.setTitle("Upload or Download");
		frameOne.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		build();
		frameOne.add(mainPanel);
		frameOne.setVisible(true);
	}

	/*
		This method builds the applet.
	*/
	public void build()
	{
		secondaryPanel = new JPanel();
		secondaryPanel.setLayout(new GridLayout(1,2));
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2,1));
		upload = new JButton("upload");
		upload.addActionListener(new UploadButtonListener());
		download = new JButton("download");
		download.addActionListener(new DownloadButtonListener());
		queryLabel = new JLabel("Do you want to upload or download a text file? ");
		queryLabel.setHorizontalAlignment(CENTER);
		secondaryPanel.add(upload);
		secondaryPanel.add(download);
		mainPanel.add(queryLabel);
		mainPanel.add(secondaryPanel);
	}
	public class UploadButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			frameOne.setVisible(false);
			choose = new JFileChooser();
			choose.setFileFilter(new FileNameExtensionFilter("Text files","txt"));
			choose.addActionListener(new ChooseButtonListener());
			choose.setApproveButtonText("upload");
			choose.setDialogTitle("upload");
			int status = choose.showOpenDialog(null);

		}
	}
	public class ChooseButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			File file = choose.getSelectedFile();
			System.out.println("The name of the file that was selected is"+fileName);
			uploadFile(file);
		}
	}
	public class DownloadButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			frameOne.setVisible(false);
			JFrame frameTwo = new JFrame();
			frameTwo.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
			frameTwo.setTitle("Download");
			frameTwo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			buildTwo();
			frameTwo.add(mainPanelTwo);
			frameTwo.setVisible(true);
		}
	} 
	public void buildTwo()
	{
		secondaryPanelTwo = new JPanel();
		secondaryPanelTwo.setLayout(new GridLayout(1,2));
		mainPanelTwo = new JPanel();
		mainPanelTwo.setLayout(new GridLayout(3,1));
		selectDownload = new JButton("Download");
		selectDownload.addActionListener(new SelectDownloadButtonListener());
		cancel = new JButton("cancel");
		cancel.addActionListener(new CancelButtonListener());
		instruct = new JLabel("Enter The Name of The file that you wish to download.");
		fileName = new JTextField();
		secondaryPanelTwo.add(selectDownload);
		secondaryPanelTwo.add(cancel);
		mainPanelTwo.add(instruct);
		mainPanelTwo.add(fileName);
		mainPanelTwo.add(secondaryPanelTwo);
	}
	public class SelectDownloadButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("download button was clicked");
		}
	}
	public class CancelButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("cancel button was clicked");
		}
	}
	public Socket makeConnection()
	{
		try
		{
			return new Socket("10.0.0.11",1234); //must set socket connection to server ip and port.
		}
		catch(Exception e)
		{
			System.out.println("not open socket.");
			return null;
		}
	}
	public void uploadFile(File file)
	{
		socket = makeConnection();
		try
		{
			Scanner scan = new Scanner(file);
			OutputStream outStream = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(outStream,true);
			while(scan.hasNextLine())
			{
				String line = scan.nextLine();
				System.out.println(line);
				pw.println(line);
			}
		}
		catch(Exception e)
		{
			System.out.println("FILE NOT FOUND");
		}
	}
}
