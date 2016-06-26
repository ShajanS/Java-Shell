package commands;

import java.lang.reflect.InvocationTargetException;

import data.InvalidArgumentException;

public class Echo implements Command{

   public String execute(data.FileSystem fs, String params){
     String result = "\n";
     params = params.trim();
     //If the (trimmed) parameters contain a >, handle them
     //with a separate method that will deal with the files
     if (params.contains(">")){
       try {
        echoToFile(fs, params);
      } catch (InvalidArgumentException e) {
        result = "Error - Invalid path";
      }
     } else{
     //Otherwise, the parameters themselves will be returned (+ a newline)
       result = params + "\n";
     }
     return result;
   }
   
   //Handles echoing to files
   private void echoToFile(data.FileSystem fs, String params)
       throws InvalidArgumentException{
     //Get the index of the first >
     int redirectIndex = params.indexOf('>');
     //Everything up to it is the string to be echoed (trim it as well)
     String strToEcho = params.substring(0, redirectIndex);
     //Store the substring starting with it as well.
     String redirectInfo = params.substring(redirectIndex);
     //If this last string starts with >>, we will append to the file.
     //Also, remove these and trim the substring to get the file path.
     String methodNameToUse;
     if (redirectInfo.startsWith(">>")){
       methodNameToUse = "appendToFile";
       redirectInfo = redirectInfo.substring(2).trim();
     } else {
     //If it starts with >, we will overwrite the file.
     //Also, remove it and trim the substring to get the file path.
       methodNameToUse = "overwriteFile";
       redirectInfo = redirectInfo.substring(1).trim();
     }
     //Call the relevant method on the filesystem using the new contents
     try {
        java.lang.reflect.Method methodToUse =
            fs.getClass().getMethod(methodNameToUse, "".getClass(),
                "".getClass());
        methodToUse.invoke(fs, redirectInfo, strToEcho); 
      } catch (IllegalAccessException | IllegalArgumentException |
          InvocationTargetException | NoSuchMethodException |
          SecurityException e) {
        throw new data.InvalidArgumentException("Could not create file");
      }
     
   }
}
