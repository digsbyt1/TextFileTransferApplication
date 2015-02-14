import java.awt.*;
import javax.swing.*;
public class FTclient implements WindowConstants, SwingConstants
{
	/*This is the beginning of client file*/
	private JFrame frameOne;
	private JPanel mainPanel;
	private JPanel secondaryPanel;
	private JButton upload;
	private JButton download;
	private JLabel queryLabel;
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
		secondaryPanel.setLayout(new GridLayout(1,1));
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2,1));
		upload = new JButton("upload");
		download = new JButton("download");
		queryLabel = new JLabel("Do you want to upload or download a text file? ");
		queryLabel.setHorizontalAlignment(CENTER);
		secondaryPanel.add(upload);
		secondaryPanel.add(download);
		mainPanel.add(queryLabel);
		mainPanel.add(secondaryPanel);
	}
}
