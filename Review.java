
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

import java.io.*;

/**
 * Class that contains helper methods for the Review Lab
 * (method removePunctuation() was added from teacher code)
 **/
public class Review {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
 
  
  private static final String SPACE = " ";
  
  static{
    try {
      Scanner input = new Scanner(new File("cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File("positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
        System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File("negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal( String word )
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }
  
  /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation( String word )
  {
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0)))
    {
      word = word.substring(1);
    }
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1)))
    {
      word = word.substring(0, word.length()-1);
    }
    
    return word;
  }
  
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }

/** Activity 2: totalSentiment()
  * Write the code to total up the sentimentVals of each word in a review.
 */
  public static double totalSentiment(String filename)
  { 
    // read in the file contents into a string using the textToString method with the filename
    String Review = textToString(filename);
    String standardReview = Review;
    // set up a sentimentTotal variable
    double sentimentTotal = 0;
    String str = " ";
    int j = 1;
    // loop through the file contents 
    for (int i = 1; i < standardReview.length(); i++)
    {
        //prevents a length exception on strings
       if (j > Review.length())
       {
           sentimentTotal += sentimentVal(Review);
           return sentimentTotal;
       }
       // tests for spaces to get words that are tested for
       //sentiment value, the sentiment value then adds to a total.
       else if(Review.substring(j-1, j).equals(" "))
       {
           str = Review.substring(0,j-1);
           sentimentTotal += sentimentVal(str);
           Review = Review.substring(j);
           j=1;
       }
       j++;
    }
   



   return sentimentTotal; 
  }


    /** Activity 2 starRating method
     Write the starRating method here which returns the number of stars for the review based on its totalSentiment.
    */
  public static int starRating(String filename)
  {
    // call the totalSentiment method with the fileName

    // determine number of stars between 0 and 4 based on totalSentiment value 
    int stars = 0; // change this!
    // write if statements here
    if (totalSentiment(filename) >= 15)
    {
        stars += 4;
    }
    else if (totalSentiment(filename) >= 5)
    {
        stars += 3;
    }
    else if (totalSentiment(filename) >= -5)
    {
        stars += 2;
    }
    else if (totalSentiment(filename) > -15)
    {
        stars += 1;
    }
    else
    {
        stars = 0;
    }
  
    // return number of stars
    return stars; 
  }
  public static String fakeReview(String fileName)
  {
      String Review = textToString(fileName);
      String finalReview = " ";
      String str = " ";
      String str1 = " ";
      String str2 = " ";
      String str3 = " ";
      int j = 1;
      for (int i = 0; i < Review.length(); i++)
      {
          int star = Review.indexOf("*");
          if(Review.substring(j-1,j).equals("*"))
          {
              while(!Review.substring(j-1,j).equals(" "))
              {
                 j++;
               }
              str1 = randomAdjective();
              str2 = Review.substring(0,star);
              Review = Review.substring(j-1);
              str = str2 + str1.toLowerCase();
              finalReview += str;
              System.out.println(finalReview);
              j = 1;
          }
      j++;
      }
      return finalReview+Review;
  }
 
}

