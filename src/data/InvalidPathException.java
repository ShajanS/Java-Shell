package data;

// A class to handle the case where a user gives an invalid path
@SuppressWarnings("serial")
public class InvalidPathException extends Exception{
	
	public InvalidPathException(){
		super();
	}
	
	public InvalidPathException(String message){
		super(message);
	}
	
}
