package ie.gmit.sw;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Processor {
	

	private BlockingQueue <Shingle> queue = new ArrayBlockingQueue<>(100);
	ShingleTaker s = new ShingleTaker();
	
	public void process(String subject, String query) throws InterruptedException {
		Thread t1 = new Thread(new Parser(subject, queue, 1));
		Thread t2 = new Thread(new Parser(query, queue, 2));
		
		//Declaring a new Thread and creating a ShingleTaker object 
		Thread t3 = new Thread(new ShingleTaker(queue, 2));

		
		//Starting all threads
		t1.start();
		t2.start();
		t3.start();

		//Threads wait to die until they are all complete
		t1.join();
		t2.join();
		t3.join();
		
		//s.calcCosineDistance();
		
		
	}
}