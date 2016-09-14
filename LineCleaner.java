
/**
 * Clean a line. Trim a line by . , and ;
 * 
 * @author Neil
 * @version 1.0
 */
 public class LineCleaner {
     
   //Trim a string by dots, commas and spaces.
   static public String clean(String string)
   {
       String newStr = string.replaceAll("[.]+", " . ");
       newStr = newStr.replaceAll(",", " , ");
       newStr = newStr.replaceAll("\\s+", " ");
       if (newStr != null && newStr.length() > 0 && newStr.charAt(newStr.length() -1) == ' ')
       {
           newStr = newStr.substring(0,newStr.length()-1);
       }
       return newStr;

   }
   
   



 }

