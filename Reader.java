 /**
 * Reader.java
 * Assignment: School Tree Final Project 
 * Purpose: A class used to take a text document of information
 *    created by this program and convert that into an ArrayList of subjeccts
 * @version 6/21/16
 * @author Rory Hayashi, Aaron Chen
 */
import java.util.*;

public class Reader{
   private Scanner s;
   public ArrayList<Subject> categories = new ArrayList<Subject>();
   //initializes the reader and creates the arratlist
   public Reader(Scanner input){
      s = input;
      while(s.hasNext()){
         String temp = s.nextLine();
         if(temp.substring(0,1).equals("<") && temp.substring(temp.length()-1).equals(">")){
            Subject s = new Subject(temp.substring(1,temp.length()-1));
            categories.add(s);
         } else if(temp.substring(0,1).equals("*") && temp.substring(temp.length()-1).equals("*")){
            Topic t = new Topic(temp.substring(1,temp.length()-1), "");
            categories.get(categories.size()-1).add(t);
         } else {
            Subject l = categories.get(categories.size()-1);
            l.setInfo(l.getSize()-1,temp);
         }
      }
   }
   //a getter for the arraylist
   public ArrayList<Subject> getList(){
      return categories;
   }
}


