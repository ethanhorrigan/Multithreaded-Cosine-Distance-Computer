package ie.gmit.sw;
import java.util.Scanner;

public class Menu {

	private Scanner scanner = new Scanner(System.in);
	public String directory;
	public String queryFile;

	public Menu(String directory, String queryFile) {
		super();
		this.directory = directory;
		this.queryFile = queryFile;
	}

	public Menu() {
		super();
	}

	public void display() throws InterruptedException {

		System.out.print("Enter Subject Directory: \n> ");
		directory = scanner.nextLine();

		System.out.print("Enter Query File: \n> ");
		queryFile = scanner.nextLine();

		new Processor().process(directory, queryFile);

	}

}
