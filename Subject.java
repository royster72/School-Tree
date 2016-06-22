 /**
 * Subject.java
 * Assignment: School Tree Final Project 
 * Purpose: A class used to store an ArrayList of different topics
 *    under a subject name
 * @version 6/21/16
 * @author Rory Hayashi, Aaron Chen
 */
import java.util.*;

public class Subject{
   private String name;
   private ArrayList<Topic> topics = new ArrayList<Topic>();
   
   public Subject(String n){
      name = n;
   }
   
   public void add(Topic t){
      topics.add(t);
   }
   
   public String getName() {
	   return name;
   }
   public int getSize(){
      return topics.size();
   }
   
   public void setInfo(int i, String s){
      topics.get(i).setInfo(s);
   }
   
   public ArrayList<Topic> getList(){
      return topics;
   }
   
   public void remove(Subject s) {
	   topics.remove(s);
   }
   
   public void remove(int i) {
	   topics.remove(i);
   }
   
   public String toString(){
      return name;
   }
}
