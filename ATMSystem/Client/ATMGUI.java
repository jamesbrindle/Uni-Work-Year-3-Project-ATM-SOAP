import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This class is basically the skeleton frame of the ATMGUI, it adds all the objects and lays them out and sets
 * layout settings for each object as well as adding the appropriate action listeners. The client processing
 * side of things is done via the ATMClientProtocol class, which calls an instance of this class with itself
 * as a parameter, in which this class refers back to
 * 
 * @author Jamie Brindle (06352322)
 */
public class ATMGUI extends JFrame implements WindowListener {

    private static final long serialVersionUID = 2L;
    protected Container container;
    protected static JFrame frame;
    protected ATMClientProtocol atmClientProtocol;
	
    // panels
	
    private JPanel numPadPanel, leftButtonsPanel, rightButtonsPanel,
            topPanel;
	
    protected Panel display;
	
    // number pad buttons
	
    protected JButton num1, num2, num3, num4, num5, num6, num7, num8, num9, num0, numEnter,
            numClear, numCancel, numEmpty1, numEmpty2, numEmpty3;
	
    // left buttons
	
    protected JButton left1, left2, left3, left4;
	
    // right buttons
	
    protected JButton right1, right2, right3, right4;
		
    // GridBad constraints
	
    private GridBagConstraints numPadLayout, leftButtonsLayout, rightButtonsLayout;
	
    /**
     * Constructor for the ATMGUI
     * @param atmClientProtocol The calling class of the ATMGUI
     */
    public ATMGUI(ATMClientProtocol atmClientProtocol) {
        this.atmClientProtocol = atmClientProtocol;
        runGUI();	        
    }
	
    /**
     * Builds the frame and its components and sets layout and action listener settings
     */
    public void runGUI() {
		
        frame = new JFrame("Virtual ATM Machine GUI"); // Main frame and its title
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // because the closing operation has been re-defined
        frame.addWindowListener(this);
		
        container = frame.getContentPane();		
		
        container.setLayout(new BorderLayout());
        
        topPanel = new JPanel();
        topPanel.add(new JLabel("Virtual ATM Machine"));
        
        numPadPanel = new JPanel(new GridBagLayout());
        numPadLayout = new GridBagConstraints();
        
        leftButtonsPanel = new JPanel(new GridBagLayout());
        leftButtonsLayout = new GridBagConstraints();
        
        rightButtonsPanel = new JPanel(new GridBagLayout());
        rightButtonsLayout = new GridBagConstraints();
        
        display = new Panel();
        display.setLayout(null); // using standard AWT layout for display panel  
        display.setPreferredSize(new Dimension(450, 350));
        display.setBackground(Color.white);
                                    
        loadNumPad(); // load the virtual ATM machines keypad
        loadLeftButtons(); // load the virtual ATM machine buttons found at the left of the screen
        loadRightButtons(); // load the virtual ATM machines buttons found at the right of the screen
        
        container.add(topPanel, BorderLayout.NORTH);
        container.add(numPadPanel, BorderLayout.SOUTH);
        container.add(display, BorderLayout.CENTER);
        container.add(leftButtonsPanel, BorderLayout.WEST);
        container.add(rightButtonsPanel, BorderLayout.EAST);
        
        // centre frame on screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
         
        frame.setLocation(screenWidth / 4, screenHeight / 4);
        frame.pack();
        frame.setVisible(true);
        
    }
	
    /**
     * load the virtual ATM machines keypad
     */
    public void loadNumPad() {
			
        numPadLayout.ipady = 8;
        
        numPadLayout.insets = new Insets(4, 0, 0, 0);
		
        num1 = new JButton("1");
        num1.addActionListener(atmClientProtocol);
        numPadLayout.gridx = 0;
        numPadLayout.gridy = 0;		
        numPadPanel.add(num1, numPadLayout);
        
        num2 = new JButton("2");
        num2.addActionListener(atmClientProtocol);
        numPadLayout.gridx = 1;
        numPadLayout.gridy = 0;    	
        numPadPanel.add(num2, numPadLayout);
        
        num3 = new JButton("3");
        num3.addActionListener(atmClientProtocol);
        numPadLayout.gridx = 2;
        numPadLayout.gridy = 0;    	
        numPadPanel.add(num3, numPadLayout);
        
        numPadLayout.insets = new Insets(4, 10, 0, 0);
        
        numClear = new JButton("Clear");
        numClear.addActionListener(atmClientProtocol);
        numPadLayout.fill = GridBagConstraints.HORIZONTAL;
        numPadLayout.gridx = 3;
        numPadLayout.gridy = 0;    	
        numPadPanel.add(numClear, numPadLayout);
        
        numPadLayout.insets = new Insets(0, 0, 0, 0);
        
        num4 = new JButton("4");
        num4.addActionListener(atmClientProtocol);
        numPadLayout.gridx = 0;
        numPadLayout.gridy = 1;    	
        numPadPanel.add(num4, numPadLayout);
        
        num5 = new JButton("5");
        num5.addActionListener(atmClientProtocol);
        numPadLayout.gridx = 1;
        numPadLayout.gridy = 1;    	
        numPadPanel.add(num5, numPadLayout);
        
        num6 = new JButton("6");
        num6.addActionListener(atmClientProtocol);
        numPadLayout.gridx = 2;
        numPadLayout.gridy = 1;    	
        numPadPanel.add(num6, numPadLayout);
        
        numPadLayout.insets = new Insets(0, 10, 0, 0);
        
        numCancel = new JButton("Cancel");
        numCancel.addActionListener(atmClientProtocol);
        numPadLayout.fill = GridBagConstraints.HORIZONTAL;
        numPadLayout.gridx = 3;
        numPadLayout.gridy = 1;    	
        numPadPanel.add(numCancel, numPadLayout);
        
        numPadLayout.insets = new Insets(0, 0, 0, 0);
        
        num7 = new JButton("7");
        num7.addActionListener(atmClientProtocol);
        numPadLayout.gridx = 0;
        numPadLayout.gridy = 2;    	
        numPadPanel.add(num7, numPadLayout);
        
        num8 = new JButton("8");
        num8.addActionListener(atmClientProtocol);
        numPadLayout.gridx = 1;
        numPadLayout.gridy = 2;    	
        numPadPanel.add(num8, numPadLayout);
        
        num9 = new JButton("9");
        num9.addActionListener(atmClientProtocol);
        numPadLayout.gridx = 2;
        numPadLayout.gridy = 2;    	
        numPadPanel.add(num9, numPadLayout);
        
        numPadLayout.insets = new Insets(0, 10, 0, 0);
        
        numEnter = new JButton("Enter");
        numEnter.addActionListener(atmClientProtocol);
        numPadLayout.gridx = 3;
        numPadLayout.gridy = 2;    	
        numPadPanel.add(numEnter, numPadLayout);
        
        numPadLayout.insets = new Insets(0, 0, 0, 0);
        
        numEmpty1 = new JButton("");
        numEmpty1.setEnabled(false);
        numPadLayout.fill = GridBagConstraints.BOTH;
        numPadLayout.gridx = 0;
        numPadLayout.gridy = 3;    	
        numPadPanel.add(numEmpty1, numPadLayout);
        
        num0 = new JButton("0");
        num0.addActionListener(atmClientProtocol);
        numPadLayout.gridx = 1;
        numPadLayout.gridy = 3;    	
        numPadPanel.add(num0, numPadLayout);
        
        numEmpty2 = new JButton("");
        numEmpty2.setEnabled(false);
        numPadLayout.fill = GridBagConstraints.BOTH;
        numPadLayout.gridx = 2;
        numPadLayout.gridy = 3;    	
        numPadPanel.add(numEmpty2, numPadLayout);
        
        numPadLayout.insets = new Insets(0, 10, 0, 0);
        
        numEmpty3 = new JButton("");
        numEmpty3.setEnabled(false);
        numPadLayout.fill = GridBagConstraints.BOTH;
        numPadLayout.gridx = 3;
        numPadLayout.gridy = 3;    	
        numPadPanel.add(numEmpty3, numPadLayout);
        
        numPadLayout.insets = new Insets(0, 0, 0, 0);        
        
    }
    
    /**
     * load the virtual ATM machine buttons found at the left of the screen
     */
    public void loadLeftButtons() {
        leftButtonsLayout.insets = new Insets(160, 0, 15, 4);
		
        leftButtonsLayout.ipady = 20;
        leftButtonsLayout.ipadx = 15;
        
        left1 = new JButton();
        left1.addActionListener(atmClientProtocol);
        leftButtonsLayout.gridx = 0;
        leftButtonsLayout.gridy = 0;		
        leftButtonsPanel.add(left1, leftButtonsLayout);
        
        leftButtonsLayout.insets = new Insets(0, 0, 15, 4);
        
        left2 = new JButton();
        left2.addActionListener(atmClientProtocol);
        leftButtonsLayout.gridx = 0;
        leftButtonsLayout.gridy = 1;		
        leftButtonsPanel.add(left2, leftButtonsLayout);
        
        left3 = new JButton();
        left3.addActionListener(atmClientProtocol);
        leftButtonsLayout.gridx = 0;
        leftButtonsLayout.gridy = 2;		
        leftButtonsPanel.add(left3, leftButtonsLayout);
        
        left4 = new JButton();
        left4.addActionListener(atmClientProtocol);
        leftButtonsLayout.gridx = 0;
        leftButtonsLayout.gridy = 3;		
        leftButtonsPanel.add(left4, leftButtonsLayout);
    }
	
    /**
     * load the virtual ATM machine buttons found at the right of the screen
     */
    public void loadRightButtons() {
        rightButtonsLayout.insets = new Insets(160, 4, 15, 0);
		
        rightButtonsLayout.ipady = 20;
        rightButtonsLayout.ipadx = 15;
        
        right1 = new JButton();
        right1.addActionListener(atmClientProtocol);
        rightButtonsLayout.gridx = 0;
        rightButtonsLayout.gridy = 0;		
        rightButtonsPanel.add(right1, rightButtonsLayout);
        
        rightButtonsLayout.insets = new Insets(0, 4, 15, 0);
        
        right2 = new JButton();
        right2.addActionListener(atmClientProtocol);
        rightButtonsLayout.gridx = 0;
        rightButtonsLayout.gridy = 1;		
        rightButtonsPanel.add(right2, rightButtonsLayout);
        
        right3 = new JButton();
        right3.addActionListener(atmClientProtocol);
        rightButtonsLayout.gridx = 0;
        rightButtonsLayout.gridy = 2;		
        rightButtonsPanel.add(right3, rightButtonsLayout);
        
        right4 = new JButton();
        right4.addActionListener(atmClientProtocol);
        rightButtonsLayout.gridx = 0;
        rightButtonsLayout.gridy = 3;		
        rightButtonsPanel.add(right4, rightButtonsLayout);
		
    }
	
    /**
     * Implements and overrides window closing event
     * @param e The window calling the event
     */
    public void windowClosing(WindowEvent e) {
        atmClientProtocol.processExit();
        frame.dispose();		
    }

    /**
     * Implements and overrides window closed event
     * @param e The window calling the event
     */
    public void windowClosed(WindowEvent e) {}

    /**
     * Implements and overrides window Opened event
     * @param e The window calling the event
     */
    public void windowOpened(WindowEvent e) {}

    /**
     * Implements and overrides window iconified event
     * @param e The window calling the event
     */
    public void windowIconified(WindowEvent e) {}

    /**
     * Implements and overrides window deiconified event
     * @param e The window calling the event
     */
    public void windowDeiconified(WindowEvent e) {}

    /**
     * Implements and overrides window activated event
     * @param e The window calling the event
     */
    public void windowActivated(WindowEvent e) {}

    /**
     * Implements and overrides window deactivated event
     * @param e The window calling the event
     */
    public void windowDeactivated(WindowEvent e) {}

}
