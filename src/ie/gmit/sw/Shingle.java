package ie.gmit.sw;

public class Shingle {

	private int file;
	private String shingle;
	
	public Shingle(int file, String shingle) {
		super();
		this.file = file;
		this.shingle = shingle;
	}

	public int getFile() {
		return file;
	}

	public void setFile(int file) {
		this.file = file;
	}

	public String getShingle() {
		return shingle;
	}

	public void setShingle(String shingle) {
		this.shingle = shingle;
	}
	
}
