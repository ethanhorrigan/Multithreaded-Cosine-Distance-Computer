package ie.gmit.sw;

import java.io.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class Parser implements Runnable, ParseInterface {

	// Local Variables
	private String file;
	private BlockingQueue<Shingle> queue;
	private final int shingleSize = 1;
	private Deque<String> buffer = new LinkedList<>();
	private int id;

	public Parser(String file, BlockingQueue<Shingle> queue, int id) {
		super();
		this.file = file;
		this.queue = queue;
		this.id = id;
	}

	@Override
	public void run() {
		parse();
	}

	@Override
	public void parse() {
		long startTime = System.currentTimeMillis();
		try {
			// parse file line by line
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.length() > 0) {
					String lowerCase = line.toLowerCase().replaceAll("[^A-Za-z0-9 ]", "");
					// split words by white space
					String[] words = lowerCase.split("\\s+");
					//System.out.println(lowerCase);
					addWords(words);
					Shingle s = nextShingle();
					this.queue.put(s);
					
				}
			}
			br.close();
			// clear buffer
			flushBuffer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();

		System.out.println("File Parse Execution Time: " + (endTime - startTime) + " milliseconds");
	}

	private void addWords(String[] words) {
		for (String s : words) {
			buffer.add(s);
		}
	}

	private Shingle nextShingle() {
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		while (counter < shingleSize) {
			// check for more words
			if (buffer.peek() != null) {
				// use sb to append remainder words on line to next line
				sb.append(buffer.poll());
				counter++;
			} else {
				// otherwise keep adding
				counter = shingleSize;
			}
		}
		if (sb.length() > 0) {
			//System.out.println("Shingle: "+sb.toString().hashCode()+"");
			return (new Shingle(id, sb.toString()));
		} else {
			return null;
		}
	}

	/**
	 * this method checks if you are at the last word and if you are it creates a
	 * new instance of the class Poison which is used as a marker for the end of
	 * document
	 * 
	 * @throws InterruptedException
	 *             in the case of thread interruption
	 */
	public void flushBuffer() throws InterruptedException {
		while (buffer.size() > 0) {
			Shingle s = nextShingle();
			if (s == null) {
				queue.put(s);
			}
		}
		queue.put(new Poison(id, null));
	}

}