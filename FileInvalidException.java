package assignment_3;

public class FileInvalidException extends Exception {
	
	public FileInvalidException(){
		super("File is not valid");
	}
	
	public FileInvalidException(String s) {
		super(s);
	}
	
	public String getMessage() {
		return super.getMessage();
	}
	
}
