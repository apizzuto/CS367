import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

///////////////////////////////////////////////////////////////////////////////
//Main Class File:  ImageLoopEditor.java or TextImageLoopEditor.java
//File:             ImageLoopEditor.java
//Semester:         CS 367 Spring 2018
//
//Author:           Alex Pizzuto (pizzuto@wisc.edu)
//CS Login:         pizzuto@cs.wisc.edu
//Lecturer's Name:  Charles Fischer
//
///////////////////////////////////////////////////////////////////////////////


/********************************
 This class implements a GUI-based editor for an Image Loop editor.
 A LinkedLoop<Image> named loop is declared.
 You must complete the code in the methods named pushXXX
 to implement the individual editor commands.
 ********************************/
public class ImageLoopEditor {

	protected static LoopADT<Image> loop = new LinkedLoop<Image>();
	private static String EMPTYMESSAGE = "no images\n"; 
	//make this a private field variable because it is used a bunch


    /**
     * Method to display current context, including the image before and 
     * after our current image. If there are less than three items in 
     * the loop, special formatting is performed.
     * 
     * @return formatted current content string
     */
	private static String currentContext() {
    		String contents = "";
    		try {
	    		Image myImage;
	    		myImage = loop.getCurrent();
	    			
	    		if (loop.size() == 1 ) {
	    			
	    			contents = "-->\t" + myImage.toString() + " <--\n";
	    			contents = contents.replaceAll("\"", "");
	    			
	    		} else if (loop.size() ==2 ) {
	    			
	    			//Current image is pointed to
	    			contents = "-->\t" + myImage.toString() + " <--\n"; 
	    			loop.next();
	    			myImage = loop.getCurrent();
	    			//Other image is displayed beneath the current image
	    			contents = contents + "\t" + myImage.toString() + "\n"; 
	    			loop.previous(); //we don't want to change our place in the loop
	    			contents = contents.replaceAll("\"", "");
	    			
	    		} else {
	    			
	    			loop.previous(); //Start the string with the image previous
	    			myImage = loop.getCurrent();
	    			contents = "\t" + myImage.toString() + "\n"; 
	    			loop.next(); //navigate back to original node
	    			myImage = loop.getCurrent();
	    			contents = contents + "-->\t" + myImage.toString() + " <--\n";
	    			loop.next(); //navigate to the next node
	    			myImage = loop.getCurrent();
	    			contents = contents + "\t" + myImage.toString() + "\n";
	    			loop.previous(); //Navigate back to original place in loop
	    			contents = contents.replaceAll("\"", "");
	    			
	    		}
	    		
    		} catch (EmptyLoopException e) {
    			
    			return EMPTYMESSAGE;
    			
    		}
    		
    		return contents;
    }
	
	private static Boolean goodFileName(String filename) {
		Boolean bool = filename.matches("[\\w./-]+");
		return bool;
	}
	
	
	/**
     * Find's image title containing @param title and navigates to that 
     * image, displaying the content. If not found, display "not found"
     * @param title Name of Image
     * @return current context or appropriate failure message
     */
	static String pushFind(String title){
		try {
			Iterator<Image> itrt = loop.iterator();
			Image currIm;
			while (itrt.hasNext() ) {
				itrt.next();
				currIm = loop.getCurrent();
				if (currIm.getTitle().contains(title) ) {
					return currentContext(); //display current context
				}
				loop.next();
			}
		} catch (EmptyLoopException e) {
			
			return EMPTYMESSAGE;
			
		}
		
		//If loop is exited, we did not  find the title
		return "not found\n"; 
	}
	

    /**
     * If loop is empty, display "no images to save." Otherwise, save all
     * images to a file named @param filename, one image per line starting with
     * the current image. Null titles are allowed and if filename already exists,
     * overwrite file and display message. If unable to write file, display 
     * "unable to save"
     * 
     * @param filename
     * @return Error message, if any
     */
    static String pushSave(String filename){
    		if(!goodFileName(filename)) {
    			return "unable to save\n";
    		}
    		if (loop.isEmpty() ) {
    			return "no images to save\n";
    		} else {
    			Iterator<Image> itrt = loop.iterator();
    			File saveFile = new File("images",filename);
    			String message = "";
    			if (saveFile.exists()) {
    				message += "warning: file already exists, will be overwritten\n";
    			} 
    			PrintStream myPrint;
    			try {
    				myPrint = new PrintStream(saveFile);
    			} catch (FileNotFoundException exc1 ) {
    				return "unable to save\n";
    			}
    			while(itrt.hasNext()) {
    				Image myImage = itrt.next();
    				String title = myImage.getTitle();
    				int duration = myImage.getDuration();
    				String strDuration = Integer.toString(duration);
    				String fileName = myImage.getFile();
    				myPrint.println(fileName + " " + strDuration + " " + title);
    			}
    			
    			myPrint.close();
    			return message;
    		}
    }

    /**
     * Load image from @param filename in the order they are given
     * and set current image to be the first image read in. If @param filename
     * does not exist, cannot be read from, or is not in images folder,
     * return appropriate error message
     * 
     * @param filename
     * @return Error message, if any
     */
    static String pushLoad(String filename){
    		//Make sure the filename is a good filehandle
    		if(!goodFileName(filename)) {
    			//Ensures only allowed characters are used
			return "unable to load\n";
		}
    		File readFile = new File(filename);
    		if(!readFile.exists() ) {
    			//If file DNE, return "unable to load"
    			return "unable to load\n";
    		}
    		
    		try {
    			
    			Scanner myScan = new Scanner(readFile);
    			int count = 0;
    			String message = "";
    			while (myScan.hasNext() ) {
    				
    				//Read in line by line
    				String myLine = myScan.nextLine();
    				//Split line into filename, duration, title
    				String[] info = myLine.split("\\s+");
    				String title = "";
    				if (info.length == 3) {
    					title = info[2];
    				} else if (info.length > 3 ) {
    					//Make title all of the words intended for the title
    					title = info[2];
    					for (int i = 3; i < info.length; i++) {
    						title = title + " " + info[i];
    					}
    				} else {
    					title = "";
    				}
    				
    				//Now we make sure the filename is a good filename
    				File imFile = new File("images/",info[0]);
    				if(!imFile.exists() ) {
    					message+= "Warning: " + info[0] + " is not in images folder\n";
    					count++;
    				} else {
	    				
	    				//Create the image
	    				Image myImage = new Image(info[0]);
	    				
	    				//Make sure that duration is a positive integer
	    				try {
	    					int duration = Integer.parseInt(info[1]);
	    					myImage.setDuration(duration);
	    					if (duration < 0 ) {
	    	    					//Time must be positive
	    	    					return "New duration must be postive\n";
	    					}
	    				} catch (NumberFormatException e) {
	    					//Ensure that input is an integer
	    					myScan.close();
	    					return "Input was not an integer\n";
	    				}
	    				
	    				myImage.setTitle(title);
	    				//Move to the next image so that adding before
	    				//adds after our original image
	    				if (count==0) {
	    					loop.add(myImage); 
	    				} else {
	    					loop.next();
	    					loop.add(myImage);
	    				}
	    				count++;
    				}
    		
    			}
    			myScan.close();
    			//Make the first image read in the current image
    			loop.next(); 
    			return message;
    			
    		} catch (FileNotFoundException exc1  ) {
    			return "unable to load\n"; 
    			//Catch-all just in case something made it past previous tests
    			
    		}
    }

    /**
     * Add the new image immediately after current image and make it the
     * new current image, and display current context. If @param filename is 
     * not in images folder, display warning
     * 
     * @param filename image's filename to be added
     * @return current contents or warning
     */
    static String pushAddImage(String filename){
    		if(!goodFileName(filename)) {
			return "Filename is improperly formatted\n";
		}
    		File imFile = new File("images",filename);
    		if (!imFile.exists() ) {
    			return "Warning: " + filename + " is not in images folder\n";
    		} else {
    			Image myImage = new Image(filename);
	    		if (loop.isEmpty() ) {
	    			myImage.setDuration(5);
	    			myImage.setTitle("");
	    			loop.add(myImage);
	    			return currentContext();
	    		} else {
	    			loop.next();
	    			loop.add(myImage);
	    			return currentContext();
	    		}
    		}
    }

    /**
     * Add the new image immediately before current image and make it the
     * new current image, and display current context. If @param filename is 
     * not in images folder, display warning
     * 
     * @param filename image's filename to be added
     * @return current contents or warning
     */
    static String pushInsertImage(String filename){
    		if(!goodFileName(filename)) {
			return "Filename is improperly formatted\n";
		}
    		File imFile = new File("images",filename);
    		if (!imFile.exists() ) {
    			return "Warning: " + filename + " is not in images folder\n";
    		} else {
    			Image myImage = new Image(filename);
	    		if (loop.isEmpty() ) {
	    			myImage.setDuration(5);
	    			myImage.setTitle("");
	    			loop.add(myImage);
	    			return currentContext();
	    		} else {
	    			loop.add(myImage);
	    			return currentContext();
	    		}
    		}
    }

    /**
     * Jump @param count images in the loop (forward if >0, 
     * backwards if <0) and display current context. If empty,
     * tell user
     * 
     * @param count number of images to traverse through
     * @return current context or warning
     */
    static String pushJump(String count){
		if (loop.isEmpty() ) {
			//warn user if loop is empty
			return EMPTYMESSAGE;
		}
		
    		try {
    			int steps = Integer.parseInt(count);
    			//Use helper method to move to new Image
    			jumper(steps);
    			//Display current context
    			return currentContext();
    		} catch (NumberFormatException e) {
    			//Make sure input is an integer
    			return "invalid jump count\n";
    		}
    		
    }
    
   /**
    * Jumps by count number of images in the loop.
    * Functionality of pushJump is needed in other parts of program,
    * so this is written as a helper method for the rest of the class
    * 
    * @param count number of spaces to jump forwards in the loop
    */
    private static void jumper(int count) {
		if (count < 0 ) {
			//move backwards if count is negative
			for (int i = 0; i > count; i-- ) {
				loop.previous();
			}
		} else {
			//otherwise, move forwards
			for (int i = 0; i < count; i++ ) {
				loop.next();
				}
			//if count == 0, spot does not change
		}
    }

    /**
     * Update the duration for current image to the given time
     * and display current context. If empty, return warning
     * 
     * @param time new duration for image
     * @return current context or warning
     */   
    static String pushUpdate(String time){
    		try {
    			
    			int duration = Integer.parseInt(time);
    			if (duration < 0 ) {
    				//Time must be positive
    				return "New duration must be postive\n";
    			} else {
    				//Change duration and display current context
    				loop.getCurrent().setDuration(duration);
    				return currentContext();
    			}
		} catch (EmptyLoopException exc1) {
			//If loop is empty, warn user
			return EMPTYMESSAGE;
		} catch (NumberFormatException e) {
			//Ensure that input is an integer
			return "invalid duration\n";
		}
    }

    /**
     * Edit the title for current image to the given title. 
     * If empty, tell user
     * 
     * @param title New name for current image
     * @return Current context or warning
     */
    static String pushEdit(String title){
	    	try {
	    		//Change title and display current context
			loop.getCurrent().setTitle(title);
			return currentContext();
		} catch (EmptyLoopException exc1) {
			//If loop is empty, warn user
			return EMPTYMESSAGE;
		} 
    }

    /**
     * Display all of images in the loop, starting with the current image.
     * If loop empty, warn user. In the specifications it asks for a differently
     * formatted string than the default toString() method gives, so we go
     * with the specifications.
     * 
     * @return all of the images or warning
     */
    static String pushDisplay(){
    		try {
    			if(loop.isEmpty() ) {
    				return EMPTYMESSAGE;
    			}
    			String loopInfo = "";
    			Iterator<Image> itrt = loop.iterator();
    			Image currIm;
    			while(itrt.hasNext() ) {
    				//Concatenate all titles into one formatted string
    				itrt.next();
    				currIm = loop.getCurrent();
    				String title = "";
    				if (currIm.getTitle().length() > 0 ) {
    					title = currIm.getTitle() + " ";
    				}
    				String duration = Integer.toString(currIm.getDuration());
    				String filename = currIm.getFile();
    				String imString = title + "[" + duration + "," 
    						+ filename + "]";
    				loopInfo = loopInfo + imString + "\n"; 
    				loop.next();
    				loopInfo = loopInfo.replaceAll("\"", "");
    			}
    			return loopInfo;
    			
    		} catch (EmptyLoopException e) {
    			
    			return EMPTYMESSAGE;
    		}
    }

    /**
     * Display current image as a photograph in a window
     * with the image's specified duration. If empty, warn user
     * 
     * @return warning message, if any
     */
    static String pushShow(){
    		try {
    			//Display image as a photograph
    			loop.getCurrent().displayImage();
    			String message = "";
    			return message;
    		} catch (EmptyLoopException exc1 ) {
    			//If loop is empty, warn user
    			return EMPTYMESSAGE;
    		}
    }


    /**
     * Test the loop, starting with current image, by displaying
     * each image as a photograph in a window with the image's title
     * and for specified duration
     * 
     * @return warning message, if any
     */
    static String pushTest(){
    		if (loop.isEmpty() ) {
    			//if loop is empty, warn user
    			return EMPTYMESSAGE;
    		} else {
    			//Iterate through the loop, displaying one image at a time
    			//To get around displaying all images at once and not trying
    			//to call wait in this static method, we just make a list of
    			//the images, and iterate through the loop one at a time
    			Iterator<Image> itrt = loop.iterator();
    			ArrayList<Image> imageList = new ArrayList<Image>(); 
    			while (itrt.hasNext() ) {
    				itrt.next();
    				try {
    					imageList.add(loop.getCurrent());
    				} catch (EmptyLoopException e) {
    					return EMPTYMESSAGE;
    				}
    				loop.next();
    			}
    			Image.displayImageList(imageList);
    			//Inform user when test has completed
    			return "";
    		}
    }


    /**
     * Remove current image, warning user if loop is empty
     * 
     * @return Current context or warning
     */
    static String pushRemove(){
    		try {
    			if (loop.size() == 1 ) {
    				//Display "no images" if loop becomes empty after removal
    				loop.removeCurrent();
    				return EMPTYMESSAGE;
    			} else {
    				loop.removeCurrent(); 
    				//Makes current node what was the next node
    				return currentContext();
    			}	
    		} catch (EmptyLoopException exc1) {
    			//This ensures "no images" is output if loop was empty
    			return EMPTYMESSAGE;
    		}
	    		
    }


    /**
     * Go forward to next image in the loop and display current context.
     * If loop is empty, warn user. 
     * 
     * @return current context or warning
     */
    static String pushForward(){
	    	if (loop.isEmpty() ) {
	    		//Warn user if loop is empty
	    		return EMPTYMESSAGE;
	    } else {
	    		jumper(1); //moves forwards one image
	    		return currentContext(); //displays Current Context
	    }
    }


    /**
     * Go backwards to previous image and display current context. 
     * If loop is empty, warn user. 
     * 
     * @return current context or warning
     */
    static String pushBack(){
        if (loop.isEmpty() ) {
        		//Warn user if loop is empty
        		return EMPTYMESSAGE;
        } else {
        		jumper(-1); //moves back one image
        		return currentContext(); //displays Current Context
        }
    }


  static String pushHelp(){
	  String cmds = "";
	  cmds +="Available commands:\n" +
		"\tSave image loop into filename\n" +
		"\tLoad image loop from filename\n" +
		"\tAdd Image at filename after current image\n" +
		"\tInsert Image at filename before current image\n" +
		"\tFind image matching title\n" +
		"\tUpdate display time of current image\n" +
		"\tEdit title of current image\n" +
		"\tJump count images\n" +
		"\tDisplay loop\n" +
		"\tShow current image in a window\n" +
		"\tTest image loop by showing all images\n" +
		"\tRemove current image from loop\n" +
		"\tMove current image forward one step\n" +
		"\tMove current image back one step\n" +
		"\tHelp on available commands\n" +
		"\tQuit and close this GUI\n" ;
   return cmds;
  }

  static String pushQuit(){
        System.exit(0);
	      return "quit";
  }


	/********************************
	  The following method actually implements our GUI.
		Major components are buttons and text fields.
		The components are defined and placed (in terms of pixels).
		You should not change any of this unless you really know what you
		are doing.
		Each button has an "action listener."
		When you push a button, the action listener calls a
		pushXXX method that YOU must define.
	*********************************/
public static void runGUI() {

    //f is the JFrame that will be our GUI
	 JFrame f=new JFrame("Image Loop");
    // We define the buttons and text areas that will fill the GUI
    // The locations of each component are set, in terms of pixels
    final JTextField tf1=new JTextField("");
    JTextArea ta = new JTextArea();
    ta.setTabSize(4);
    ta.setBounds(50,450,370,300);
    JButton b1=new JButton("Save");
    b1.setBounds(50,25,110,30);
    tf1.setBounds(170,25, 160,30);
    tf1.setText("filename");
    JButton b2=new JButton("Load");
    b2.setBounds(50,60,110,30);
    final JTextField tf2=new JTextField("");
    tf2.setBounds(170,60, 160,30);
    tf2.setText("filename");
    JButton b3=new JButton("Add after");
    b3.setBounds(50,95,110,30);
    final JTextField tf3=new JTextField("");
    tf3.setBounds(170,95, 150,30);
    tf3.setText("filename of image");
    JButton b4=new JButton("Insert before");
    b4.setBounds(50,130,110,30);
    final JTextField tf4=new JTextField("");
    tf4.setBounds(170,130, 150,30);
    tf4.setText("filename of image");
    JButton b5=new JButton("Find");
    b5.setBounds(50,165,110,30);
    final JTextField tf5=new JTextField("");
    tf5.setBounds(170,165, 150,30);
    tf5.setText("title");
    JButton b6=new JButton("Update");
    b6.setBounds(50,200,110,30);
    final JTextField tf6=new JTextField("");
    tf6.setBounds(170,200, 150,30);
    tf6.setText("time");
    JButton b7=new JButton("Edit");
    b7.setBounds(50,235,110,30);
    final JTextField tf7=new JTextField("");
    tf7.setBounds(170,235, 150,30);
    tf7.setText("title");
    JButton b8=new JButton("Jump");
    b8.setBounds(50,270,110,30);
    final JTextField tf8=new JTextField("");
    tf8.setBounds(170,270, 150,30);
    tf8.setText("count");
    JButton b9=new JButton("Display loop");
    b9.setBounds(50,305,110,30);
    JButton b10=new JButton("Show image");
    b10.setBounds(170,305,110,30);
    JButton b11=new JButton("Test loop");
    b11.setBounds(50,340,110,30);
    JButton b12=new JButton("Remove image");
    b12.setBounds(170,340,120,30);
    JButton b13=new JButton("Move forward");
    b13.setBounds(50,375,110,30);
    JButton b14=new JButton("Move backward");
    b14.setBounds(170,375,110,30);
    JButton b15=new JButton("Quit");
    b15.setBounds(50,410,110,30);
    JButton b16=new JButton("Help");
    b16.setBounds(170,410,110,30);


//  The effect of pushing a GUI button is defined in an ActionListener
//  The actionPerformed method is executed when the associated button is pushed

    b1.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushSave(tf1.getText()));
        }
    });

    b2.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
           ta.setText(pushLoad(tf2.getText()));
        }
    });

    b3.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushAddImage(tf3.getText()));
        }
    });

    b4.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushInsertImage(tf4.getText()));
        }
    });

    b5.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushFind(tf5.getText()));
        }
    });

    b6.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushUpdate(tf6.getText()));
        }
    });

    b7.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushEdit(tf7.getText()));
        }
    });

    b8.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushJump(tf8.getText()));
        }
    });


    b9.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushDisplay());
        }
    });


    b10.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){

     ta.setText(pushShow());
         }
    });


    b11.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushTest());
        }
    });

    b12.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushRemove());
        }
    });


    b13.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushForward());
        }
    });

    b14.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushBack());
        }
    });


    b15.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushQuit());
        }
    });


    b16.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
            ta.setText(pushHelp());
        }
    });

    //Buttons and text frames are added to the JFrame to build the GUI

    f.add(b1);f.add(tf1); f.add(ta);
    f.add(b1);f.add(tf1); f.add(ta);
    f.add(b1);f.add(tf1); f.add(ta);
    f.add(b2);f.add(tf2);
    f.add(b3);f.add(tf3);
    f.add(b4);f.add(tf4);
    f.add(b5);f.add(tf5);
    f.add(b6);f.add(tf6);
    f.add(b7);f.add(tf7);
    f.add(b8);f.add(tf8);
    f.add(b9);//f.add(tf9);
    f.add(b10);//f.add(tf10);
    f.add(b11);//f.add(tf10);
    f.add(b12);//f.add(tf10);
    f.add(b13);//f.add(tf10);
    f.add(b14);//f.add(tf10);
    f.add(b15);//f.add(tf10);
    f.add(b16);//f.add(tf10);
    f.setBounds(50,50,500,800);
    f.setLayout(null);
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // The GUI is now up and running!

}


	public static void main(String[] args) {
		runGUI();
		
  }
}