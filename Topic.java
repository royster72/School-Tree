 /**
 * Topic.java
 * Assignment: School Tree Final Project 
 * Purpose: A class used to store notes under a certain name that is the topic
 * @version 6/21/16
 * @author Rory Hayashi, Aaron Chen
 */
public class Topic{
	   private String name;
	   private String info;
	   
	   public Topic(String n, String i){
	      name = n;
	      info = i;
	   }
	   
	   public String getInfo(){
	      return info;
	   }
	   
	   public String getName(){
	      return name;
	   }
	   
	   public void setInfo(String i){
	      info = i;
	   }
	   
	   public String toString(){
	      return getName();
	   }
	}
