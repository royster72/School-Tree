 /**
 * Remover.java
 * Assignment: School Tree Final Project 
 * Purpose: A class used to take a Subject name and sometimes
 *    a Topic name, then searches through the information and deletes the
 *    specific topic or subject from the data
 * @version 6/21/16
 * @author Rory Hayashi, Aaron Chen
 */
import java.util.*;
import java.io.*;

public class Remover{
   private String subject;
   private String topic;
   private File info;
   //initializer
   public Remover(File i, String s, String top){
      subject = s.toUpperCase();
      topic = top.toUpperCase();
      info = i;
   }
   //sets subject
   public void setSubject(String sub){
      subject = sub.toUpperCase();
   }
   //sets topic
   public void setTopic(String top){
	   if(top == null) {
		   top = "";
	   }
      topic = top.toUpperCase();
   }
   //sets file
   public void setFile(File f){
      info = f;
   }
   //goes through the array list created by a reader with the file and removes the subject/topic
   public ArrayList<Subject> remove() throws FileNotFoundException {
      boolean removed = false;
      Scanner s = new Scanner(info);
      Reader r = new Reader(s);
      ArrayList<Subject> subjects = r.getList();
      for(int i = 0; i < subjects.size(); i++){
         Subject sub = subjects.get(i);
         if(sub.getName().equalsIgnoreCase(subject)){
            if(this.topic.equals("")){
               subjects.remove(i);
               break;
            }
            ArrayList<Topic> topics = sub.getList();
            for(int t = 0; t < topics.size(); t++){
               if(topics.get(t).getName().equalsIgnoreCase(topic)){
                  removed = true;
                  sub.remove(t);
                  if(sub.getSize() == 0){
                     subjects.remove(sub);
                  }
               }
            }
            if(!removed){
               System.out.println("Topic not found"); //replace later to throw an error of topic not found
            }
         }
      }
      //throw an error if the subject is not found
      return subjects;
   }
   //uses an arraylist and overwrites the file in this remover with the information in the arraylist
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