package ie.gmit.sw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShingleTaker implements Runnable {


	private ConcurrentMap<String, Integer> subject = new ConcurrentHashMap<String, Integer>();
	private ConcurrentMap<String, Integer> query = new ConcurrentHashMap<String, Integer>();
	
	private BlockingQueue<Shingle> queue;
	private int fileCount;
	private ExecutorService pool = Executors.newFixedThreadPool(3);
	
	double cosineDistance;

	public ShingleTaker(BlockingQueue<Shingle> queue, int fileCount) {
		super();
		this.queue = queue;
		this.fileCount = fileCount;
	}

	public ShingleTaker() {
	}

	/*getting freq*/
	public void run() {
		while (fileCount > 0) {
			try {
				Shingle s = queue.take();
				if (s instanceof Poison) {
					fileCount--;
				} else {
					pool.execute(new Runnable() {
						@Override
						public void run() {

							String shingle = s.getShingle();
							//System.out.print(shingle);
							if(s.getFile() == 1) {
								int count = subject.containsKey(shingle) ? subject.get(shingle) : 0;
								if (!subject.containsKey(shingle)) {
									subject.put(shingle, count + 1);
									System.out.println("Subject Does Not: "+shingle+" "+subject.get(shingle)+"");
									
								}
								else if (subject.containsKey(shingle)) {
									int temp = subject.get(shingle);
									temp++;
									subject.remove(shingle);
									subject.put(shingle, temp);
									System.out.println("Subject Does: "+shingle+" "+subject.get(shingle)+"");
								}
							}
							if(s.getFile() == 2) {
								int count = query.containsKey(shingle) ? query.get(shingle) : 0;
								if (!query.containsKey(shingle)) {
									query.put(shingle, count + 1);
									System.out.println("Query Does Not: "+shingle+" "+query.get(shingle)+"");
								}
								else if (query.containsKey(shingle)) {
									int temp = query.get(shingle);
									temp++;
									query.remove(shingle);
									query.put(shingle, temp);
									System.out.println("Query Does: "+shingle+" "+query.get(shingle)+"");
								}
							}
						}
						
					});
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		calcCosineDistance();
	}//run
	
	public void calcCosineDistance() {
		Map<String, Integer> s = subject;
		Map<String, Integer> q = query;
		
        HashSet<String> intersection = new HashSet<>(s.keySet());
        intersection.retainAll(q.keySet());
        

	       double dotProduct = 0, magnitudeA = 0, magnitudeB = 0;

	       //Calculate dot product
	       for (String item : intersection) {
	           dotProduct += s.get(item) * q.get(item);
	           System.out.print("Dot Product "+dotProduct+"\n");
	       }

	       //Calculate magnitude a
	       for (String k : s.keySet()) {
	           magnitudeA += Math.pow(s.get(k), 2);
	       }

	       //Calculate magnitude b
	       for (String k : q.keySet()) {
	           magnitudeB += Math.pow(q.get(k), 2);
	       }

	       cosineDistance = dotProduct / Math.sqrt(magnitudeA * magnitudeB);
	       System.out.println(cosineDistance);
	}

   }
