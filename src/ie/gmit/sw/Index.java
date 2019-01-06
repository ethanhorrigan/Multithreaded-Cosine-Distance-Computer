package ie.gmit.sw;

public class Index {

	private int frequency = 0;
	private int fileName;

	public Index(int frequency, int fileName) {
		super();
		this.frequency += frequency;
		this.fileName = fileName;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getFileName() {
		return fileName;
	}

	public void setFileName(int fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "" + frequency;
	}
	
}
