 /**
 * DisplayList.java
 * Assignment: School Tree Final Project 
 * Purpose: This is used to create the GUI for our project
 *    by taking a text document, pulling out all the information, and formatting it
 * @version 6/21/16
 * @author Rory Hayashi, Aaron Chen
 */
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;


public class DisplayList extends JPanel implements TreeSelectionListener, ActionListener {
	
	private JTextArea textBox;
    private JTree tree;
    private static JMenuBar menuBar;
    private JMenuItem menuItem;
    private JMenu menu;
    private static Reader reader = null;
    private Topic currentTopic;
    private static String directory = "C:/Users/ros_adchen/Desktop/words.txt";
    private static File file;
    
    private DefaultMutableTreeNode top = null;
    
    public DisplayList() {
    	
    	//Set the splitPane to fill up the entire window instead of staying in one size.
    	super(new GridLayout(1,0)); 
    	if(file != null) {
        //Create the menuBar
        menuBar = new JMenuBar();
        
        //Create the menu
        menu = new JMenu("Options");
        
        menuBar.add(menu);
        
        //Adds multiple menu items to the menu
        menuItem = new JMenuItem("Add");
        menuItem.setToolTipText("Add additional notes.");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem("Remove");
        menuItem.setToolTipText("Removes selected notes.");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem("Clear");
        menuItem.setToolTipText("Clears all subjects including the topics inside of them and the notes.");
        menuItem.addActionListener(this);
        menuItem = new JMenuItem("Save");
        menuItem.setToolTipText("Saves all edited notes.");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem("Help");
        menuItem.setToolTipText("Learn more about how to use School Tree.");
        menuItem.addActionListener(this);
        menu.addSeparator();
        menu.add(menuItem);
        
        
        createTree();

        //Create the scroll pane and add the tree to it. 
        JScrollPane treeView = new JScrollPane(tree);
        //Create the HTML viewing pane.
        textBox = new JTextArea();
        textBox.setEditable(true);
        
        //Creates a new instance of the ScrollPane
        new JScrollPane(textBox);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(textBox);
        
        Dimension minimumSize = new Dimension(100, 50);
        textBox.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        
        splitPane.setDividerLocation(100); 
        splitPane.setPreferredSize(new Dimension(500, 300));
        //Add the split pane to this panel.
        add(splitPane);
    	}
    }
    
   
    private void createTree() {
        //Create the nodes.
        top = new DefaultMutableTreeNode("Subjects");
        createNodes(top);
        
        //Create a tree that allows one selection at a time.
       	tree = new JTree(top);
       	tree.getSelectionModel().setSelectionMode
       	(TreeSelectionModel.SINGLE_TREE_SELECTION);
      	//Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
        
    }
    
    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand().equals("Help")) {
			JOptionPane.showMessageDialog(null, "Hello. Welcome to Homework Helper.\nThe folders on your left display the subjects. \nIf you click on a folder it will display all the topics in that subject. \nDouble-clicking on the topics will present the notes in them. \nOn the right panel, your notes will be displayed and you are free to edit them.\nAfter editing, remember to save your work via the menu bar!", "Homework Helper", JOptionPane.DEFAULT_OPTION, null);
    	}
    	if(e.getActionCommand().equals("Add")) {
			new addFrame();
    	}
    	if(e.getActionCommand().equals("Remove")) {
    		new removeFrame();
    	}
    	if(e.getActionCommand().equals("Save")) {
    		saveInformation();
    	}
    }
    
    private void saveInformation() {
    	String edited = textBox.getText();
		Subject currentSubject = null;
		for(Subject s : reader.getList()) {
			for(Topic t : s.getList()) {
				if(t.getName().equalsIgnoreCase(currentTopic.getName())) {
					currentTopic = t;
					currentSubject = s;
				}
			}
		}
		if(currentTopic == null || currentSubject == null) {
			JOptionPane.showMessageDialog(null, "No new data detected.", "Error", JOptionPane.ERROR_MESSAGE, null);
		}
		if(currentSubject != null && currentTopic != null) {
			Writer writer = new Writer(file, currentSubject.getName(), currentTopic.getName(), edited);
			//System.out.println(currentSubject.getName() + "   " + currentTopic.getName());
			try {
				reader.categories = writer.write();
				writer.saveData(reader.categories);
				JOptionPane.showMessageDialog(null, "Data successfully saved.", "Homework Helper", JOptionPane.DEFAULT_OPTION, null);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Save was unsuccessful.", "Error", JOptionPane.ERROR_MESSAGE, null);
				e1.printStackTrace();
			}
		}
    }
    @SuppressWarnings("serial")
	class addFrame extends JFrame {
    	JEditorPane notesBox;
    	JTextField subjectBox;
    	JTextField topicBox;
    	JLabel subjectLabel;
    	JLabel topicLabel;
    	JLabel notesLabel;
    	public addFrame() {
    		this.setTitle("Add...");
    		this.setSize(300, 200);
    		this.setResizable(false);
    		this.setVisible(true);
    		
    		JPanel panel = new JPanel();
    		subjectBox = new JTextField();
    		topicBox = new JTextField();
    		notesBox = new JEditorPane();
    		subjectBox.setBounds(55, 10, 100, 20);
    		topicBox.setBounds(55, 35, 100, 20);
    		notesBox.setBounds(55, 60, getWidth() - 75, 100);
    		subjectLabel = new JLabel("Subject");
    		subjectLabel.setText("Subject: ");
    		subjectLabel.setBounds(10, 10, 50, 20);
    		subjectLabel.setVisible(true);
    		topicLabel = new JLabel("Topic");
    		topicLabel.setText("Topic: ");
    		topicLabel.setBounds(10,35,40,20);
    		topicLabel.setVisible(true);
    		notesLabel = new JLabel("Notes");
    		notesLabel.setText("Notes: ");
    		notesLabel.setBounds(10, 60, 40,20);
    		notesLabel.setVisible(true);
    		panel.add(notesLabel);
    		panel.add(topicLabel);
    		panel.add(subjectLabel);
    		panel.add(notesBox);
    		panel.add(topicBox);
    		panel.add(subjectBox);
    		panel.setLayout(null);
    		JButton confirm = new JButton();
    		confirm.setText("Add");
    		confirm.setBounds(getWidth() - 120, 10, 100, 45);
    		
    		confirm.addActionListener(new ActionListener() {
    			
    			public void actionPerformed(ActionEvent e) {
    				
    				if(notesBox.getText().isEmpty() || topicBox.getText().isEmpty() || subjectBox.getText().isEmpty()) {
    					JOptionPane.showMessageDialog(null, "No new data detected.", "Error", JOptionPane.ERROR_MESSAGE, null);
    					
    				}else{
    					
    					Writer writer = new Writer(file, subjectBox.getText(), topicBox.getText(), notesBox.getText());
    					try{
    						
    						DefaultTreeModel model = (DefaultTreeModel)tree.getModel();

    						DefaultMutableTreeNode root = (DefaultMutableTreeNode)top; // Subjects parent folder

    						Topic t = new Topic(topicBox.getText(), notesBox.getText());
    						DefaultMutableTreeNode topicNode = new DefaultMutableTreeNode(t);
    						Subject s = new Subject(subjectBox.getText());
    						s.add(t);
    						DefaultMutableTreeNode subjectNode = new DefaultMutableTreeNode(s);
    						subjectNode.add(topicNode);
    						
    						
    						for(int i = 0; i < root.getChildCount(); i++) { // loops through all the children of the root
    							String childName = root.getChildAt(i).toString();
    							
    							if(s.getName().equalsIgnoreCase(childName)) { // if the subject is found
    								DefaultMutableTreeNode subject = (DefaultMutableTreeNode) root.getChildAt(i); // creates a subject based on the index of the loop
    								subject.add(topicNode); // adds the topic to the subject

    								break;
    							}else{
    								if(root.getLastChild().toString().equalsIgnoreCase(s.getName())) {  
    									DefaultMutableTreeNode subject = (DefaultMutableTreeNode) root.getLastChild(); // creates a subject based on the last child
    									subject.add(topicNode);
    									break;
    								}
    							}
    						}
    						
    						if(notHere(root, subjectBox.getText())) {
    							root.add(subjectNode);
    						}
    						
    						if(root.getChildCount() == 0) { // if their are no children
    							root.add(subjectNode); // adds the subject node
    						}
    						model.reload(); // reloads the JTree model allowing it to auto-update

    						// saves the data onto text file
    						reader.categories = writer.write();
    						writer.saveData(reader.categories);
    					}catch(Exception exception) {
    						exception.printStackTrace();
    					}

						addFrame.this.dispose(); // disposes of the addFrame
    				}
    			}
    		});

    		panel.add(confirm);
    		add(panel);
    	}
    	private boolean notHere(DefaultMutableTreeNode root, String check) {
    		for(int i = 0; i < root.getChildCount(); i++) {
				String childName = root.getChildAt(i).toString();
				if(childName.equalsIgnoreCase(check)) {
					return false;
				}
			}
    		return true;
    	}
    }
    
    @SuppressWarnings("serial")
	class removeFrame extends JFrame {
    	JComboBox<Topic> topicList;
    	JLabel subjectLabel, topicLabel;
    	JComboBox<Subject> subjectList;
    	public removeFrame() {
    		this.setTitle("Remove...");
    		this.setSize(300, 100);
    		this.setResizable(false);
    		this.setVisible(true);

    		JPanel panel = new JPanel();

    		panel.setLayout(null);
    		topicList = new JComboBox<Topic>();
    		subjectLabel = new JLabel();
    		topicLabel = new JLabel();
    		subjectLabel.setBounds(20, 15, 50, 10);
    		topicLabel.setBounds(20, 40, 50, 10);
    		subjectLabel.setText("Subject: ");
    		topicLabel.setText("Topic: ");
    		topicList.setBounds(65, 35, 100, 20);
    		topicList.setEnabled(false);
    		panel.add(topicLabel);
    		panel.add(subjectLabel);
    		panel.add(topicList);
    		subjectList = new JComboBox<Subject>();
    		for(Subject s : reader.getList()) {
    			subjectList.addItem(s);
    		}
    		if(subjectList.getItemCount() <= 0) {
    			this.dispose();
    			JOptionPane.showMessageDialog(null, "No data detected.", "Error", JOptionPane.ERROR_MESSAGE, null);	
    		}
    		
    		subjectList.setSelectedIndex(0);
    		Subject firstSubj = (Subject)subjectList.getSelectedItem();
    		if(firstSubj.getList().size() > 0) {
    			topicList.setEnabled(true);
    			topicList.removeAllItems(); // removes all previous items in the list
				topicList.addItem(new Topic("ALL", ""));
				for(Topic t : firstSubj.getList()) {
					topicList.addItem(t);
				}
    		}
    		subjectList.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					JComboBox<Subject> combo = (JComboBox<Subject>)e.getSource();
					Subject s = (Subject)combo.getSelectedItem();
					if(s.getList().size() <= 0) {
						topicList.setEnabled(false);
					}else{
						topicList.setEnabled(true);
						
						topicList.removeAllItems(); // removes all previous items in the list
						topicList.addItem(new Topic("ALL", null));
						for(Topic t : s.getList()) {
							topicList.addItem(t);
						}
					}
    			}
    		});
    	    subjectList.setEditable(false);
    	    subjectList.setBounds(65, 10, 100, 20);
    	    subjectList.setVisible(true);
    		panel.add(subjectList);
    		
    		JButton confirm = new JButton();
    		confirm.setText("Remove");
    		confirm.setBounds(this.getWidth() - 120, 10, 100, 45);
    		confirm.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				String topic = topicList.getSelectedItem().toString();
    				if(topicList.getSelectedItem().toString().equalsIgnoreCase("ALL")) {
    					topic = "";
    				}

    				Remover remover = new Remover(file, subjectList.getSelectedItem().toString(), topic);
    				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
    				DefaultMutableTreeNode root = (DefaultMutableTreeNode)top; // Subjects parent folder
    				Subject selectedSubject = (Subject)subjectList.getSelectedItem();
    				Topic selectedTopic = (Topic)topicList.getSelectedItem();
    				
    				if(topicList.getSelectedItem().toString().equalsIgnoreCase("ALL")) {
    					try{
    					for(int i = 0; i < root.getChildCount(); i++) { // loops through all the children of the root
    						DefaultMutableTreeNode s = (DefaultMutableTreeNode)root.getChildAt(i);
    						if(subjectList.getSelectedItem().toString().equalsIgnoreCase(s.toString())) { // if one of the children's name is the same as what the user wants
    							root.remove(s); // removes the subject
    							
    						}
    					}
						model.reload();

						ArrayList<Subject> subjects = remover.remove(); // removes the specified subject and returns a new arraylist of data
						remover.saveData(subjects); // saves the newly created arraylist
    					}catch(Exception exception) {
    						exception.printStackTrace();
    					}
    				}else{
    					try{
    						for(int i = 0; i < root.getChildCount(); i++) { // loops through all the children of the root
    							String childName = root.getChildAt(i).toString();
    							if(childName.equalsIgnoreCase(subjectList.getSelectedItem().toString())) { // if a child has the same name as the specified subject
    								DefaultMutableTreeNode subject = (DefaultMutableTreeNode)root.getChildAt(i); // get the subject
    								if(subject.getChildCount() > 0) { // if their are topics in the subject
    									for(int b = 0; b < subject.getChildCount(); b++) { // loops through all the children of the subject
    										DefaultMutableTreeNode t = (DefaultMutableTreeNode)subject.getChildAt(b); 
    										if(t.toString().equalsIgnoreCase(topicList.getSelectedItem().toString())) {
    											model.removeNodeFromParent(t);
    											selectedSubject.getList().remove(selectedTopic);
    										}
    									}
    								}else{
    									root.remove(subject);
    								}
    							}
    						}

    						model.reload();

    						ArrayList<Subject> subjects = remover.remove();
    						remover.saveData(subjects);
    					}catch(FileNotFoundException exception) {
    						exception.printStackTrace();
    					}

    				}

					removeFrame.this.dispose();
    			}
    		});
    		panel.add(confirm);
    		add(panel);
    		
    	}
    }
    
    /** Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e) {
    	DefaultMutableTreeNode node = (DefaultMutableTreeNode)
    			tree.getLastSelectedPathComponent();

    	if (node == null) return;
    	if(node.getUserObject() instanceof Topic) {
    		Topic nodeInfo = (Topic)node.getUserObject();
    		currentTopic = nodeInfo;
    		if (node.isLeaf()) {
    			displayNotes(nodeInfo.getInfo());
    		}
    	}
    	
    }
    
    /** Sets the textbox to the notes. **/
    private void displayNotes(String s) {
    	
    	String[] lines;
    	lines = s.split("\n");
  	    // System.out.println(Arrays.toString(lines));
    	String oldText = "";
    	
    	for(String t : lines) {
    		oldText += t += "\n";
    	}
    	textBox.setText(oldText);
    	
    	//textBox.setText(s);
    	//System.out.println(totalText);
    }
    
    /** Returns whether the string is too long or not. **/
    private boolean exceedsLength(String s) {
    	if(s.length() > 7) {
    		return true;
    	}
    	return false;
    }
    
    /** Adds all subjects and topics to treeView**/
    private void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode subject = null;
        for(Subject s : reader.getList()) {
        	subject = new DefaultMutableTreeNode(s);
        	top.add(subject);
        	for(Topic t : s.getList()) {
        		subject.add(new DefaultMutableTreeNode(t));
        	}
        }
        /** original example
        book = new DefaultMutableTreeNode(new BookInfo
            ("test",
            "html"));
        category.add(book);
         **/
    }
        
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void initiateInterface(Reader r) {
    	reader = r;
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Couldn't use system look and feel.");
            }
        //Create and set up the window.
        JFrame frame = new JFrame("School Tree");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new DisplayList());
        frame.setJMenuBar(menuBar);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    @SuppressWarnings("resource")
	public static void main(String[] args) {

    	Scanner scan = new Scanner(System.in);
    	System.out.println("Please select a file.");
    	directory = scan.next();
    	file = new File(directory);
    	//Schedule a job for the event dispatch thread:
    	//creating and showing this application's GUI.
    	try {
    		if(file.exists()) {
			scan = new Scanner(file);
    		}else{
    			System.out.println("File does not exist.");
    			main(args);
    		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(scan != null) {
    		initiateInterface(new Reader(scan));
    	}
    }

}