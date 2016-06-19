package commands;

public class Echo implements Command{

   public String execute(driver.JShell shell, String params){
     String result = "\n";
     params = params.trim();
     //If the (trimmed) parameters start with a >, handle them
     //with a separate method that will deal with the files
     if (params.startsWith(">")){
       //TODO: Implement echoToFile, file class
       //echoToFile(shell, params);
     } else{
     //Otherwise, the parameters themselves will be returned (+ a newline)
       result = params + "\n";
     }
     return result;
   }
}
