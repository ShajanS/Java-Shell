package data;

public interface FileSystem {
    
    public Directory getDirectory(String path) throws InvalidPathException;
    
    public void makeCurrentDirectory(String path) throws InvalidPathException;
    
    public String getCurrentDirectoryPath();
    
    public String getFileContents(String path) throws InvalidPathException;
    
    public void overwriteFile(String path, String newContents)
        throws InvalidPathException;
    
    public void appendToFile(String path, String newContents)
        throws InvalidPathException;
    
    public void addCommandToHistory(String command);
    
    public java.util.ArrayList<String> getCommandHistory();
    
    public void pushToDirStack(String path);
    
    public String popFromDirStack();
}
