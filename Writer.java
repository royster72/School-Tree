 /**
 * Writer.java
 * Assignment: School Tree Final Project 
 * Purpose: A class used to take a Subject name, Topic name, and notes
 *    then search the existing text document to add the new information
 *    to the existing information in the appropriate place.
 * @version 6/21/16
 * @author Rory Hayashi, Aaron Chen
 */
import java.util.*;
import java.io.*;

public class Writer{
   private String subject;
   private String topic;
   private String text;
   private File info;
   
   private String[] lines;
   
   public Writer(File i, String s, String top, String text){
      subject = s;
      topic = top;
      this.text = text;
      info = i;
      parseLines(text);
   }
   
   private void parseLines(String text) {
	  lines = text.split("\n");
	  System.out.println(Arrays.toString(lines));
   }
   public void setSubject(String sub){
      subject = sub;
   }
   
   public void setTopic(String top){
      topic = top;
   }
   
   public void setText(String t){
      text = t;
   }
   
   public void setFile(File f){
      info = f;
   }
   //goes through the arraylist created from a reader with the text file, then adds in the subject/topic where appropriate
   public ArrayList<Subject> write() throws FileNotFoundException {
      boolean create = true;
      Scanner s = new Scanner(info);
      Reader r = new Reader(s);
      ArrayList<Subject> subjects = r.getList();
      for(int i = 0; i < subjects.size(); i++){
         //checks if the subject name matches
         if(subjects.get(i).getName().equalsIgnoreCase(subject)){
            for(Topic t : subjects.get(i).getList()){
            //goes through the subject's topics to see if any match this writer's topic
               if(t.getName().equalsIgnoreCase(topic)){
                  create = false;
                  t.setInfo(text); 
               }
            }
            //if there wasa subject match, but not topic, add the topic to the end
            if(create){
               subjects.get(i).add(new Topic(topic, text));
               create = false;
            }
         }
      }
      //adds a new subject if it did not match above
      if(create){
         subjects.add(new Subject(subject));
         subjects.get(subjects.size()-1).add(new Topic(topic, text));
      }
      return subjects;
   }
   //rewrites the text file in this writer with the arraylist's information
   public void saveData(ArrayList<Subject> categories) throws FileNotFoundException {
	   
      PrintStream print = new PrintStream(info);
      for(Subject s : categories){
         print.println("<" + s.getName() + ">");
         for(Topic t : s.getList()){
            print.println("*" + t.getName() + "*");
            print.println(t.getInfo());
         }
      }
      print.close();
   }
}
