package com.strive.learning.java8.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("unused")
public class C17_Concurrency {
	
	public static void main(String[] args) {
		usingCallableFuture();
	}

	private static void usingCallableFuture() {
		// Random run times for threads
		Random random = new Random();
		
		// A callable (instable of a runnable)
		Callable<Integer> task = () -> {
		    try {
		        String name = Thread.currentThread().getName();
		    	int seconds = random.nextInt(15);
		        System.out.println(String.format("Callable @%s executing for %d seconds", name, seconds));
		        TimeUnit.SECONDS.sleep(seconds);
		        return seconds;
		    } catch (InterruptedException e) {
		        throw new IllegalStateException("Task interrupted", e);
		    }
		};
		
		// Executor with a pool of 10 threads
		int numThreads = 10;
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		System.out.println("Executor has "+numThreads+" threads to run tasks");
		
		// Futures
		List<Future<Integer>> futures = new ArrayList<>();
		for(int f = 0; f < 30; f++) {
			Future<Integer> future = executor.submit(task);
			futures.add(future);
		}
		System.out.println("Number of tasks submitted to executor service = "+futures.size());
		
		// Check whether the tasks are done
		try {
			while(!futures.isEmpty()) {
				System.out.println("Checking on "+futures.size()+" tasks completion ...");
				for(Iterator<Future<Integer>> it = futures.iterator(); it.hasNext(); ) {
					Future<Integer> future = it.next();
					if(future.isDone()) {
						System.out.println("Future "+future+" is done, result = "+future.get());
						it.remove();
					} else {
						System.out.println("Future "+future+" is still running ...");
					}
				}
				
				// Wait a little bit
				System.out.println("Waiting for "+futures.size()+" tasks to complete ...");
				TimeUnit.SECONDS.sleep(3);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		// Stop the executor
		try {
		    System.out.println("Attempt to shutdown executor");
		    executor.shutdown();
		    executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		    System.err.println("Tasks interrupted");
		} finally {
		    if (!executor.isTerminated()) {
		        System.err.println("Cancel non-finished tasks");
		    }
		    executor.shutdownNow();
		    System.out.println("Executor shutdown finished");
		}
	}

	private static void usingExecutorService() {
		// Random run times for threads
		Random random = new Random();
		
		// Supposing we have a runnable
		Runnable runnable = () -> {
		    try {
		        String name = Thread.currentThread().getName();
		        System.out.println("Starting @" + name);
		        TimeUnit.SECONDS.sleep(random.nextInt(5));
		        System.out.println("Completed @" + name);
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		};
		
		// The usual way to execute the runnable is via a thread
		Thread thread = new Thread(runnable);
		thread.start();
		
		// Using an executor service, we can do the same
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
			String name = Thread.currentThread().getName();
	        System.out.println("ExecutorService: starting @" + name);
	        try { 
	        	TimeUnit.SECONDS.sleep(random.nextInt(5));
			} catch (Exception e) {
				e.printStackTrace();
			}
	        System.out.println("ExecutorService: completed @" + name);
		});
		
		// Executor service must be shut down explicitly
		try {
		    System.out.println("Attempt to shutdown executor");
		    executor.shutdown();
		    executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		    System.err.println("Tasks interrupted");
		} finally {
		    if (!executor.isTerminated()) {
		        System.err.println("Cancel non-finished tasks");
		    }
		    executor.shutdownNow();
		    System.out.println("Executor shutdown finished");
		}
	}

	private static void usingExecutorInterface() {
		// Supposing we have a runnable
		class Task implements Runnable {
			public void run() {
				System.out.println("Task is running in "+Thread.currentThread().getName());
			}
		}
		
		// Implement an executor to run a thread a set number of times
		class RepeatedExecutor implements Executor {
			public void execute(Runnable runnable) {
				new Thread(runnable).start();
			}

			public void execute(Runnable runnable, int times) {
				System.out.printf("Shall run %s %d times%n", runnable.toString(), times);
				for (int i = 0; i < times; i++) {
					execute(runnable);
				}
			}
		}
		
		// Task to run a few times
		Runnable runnable = new Task();
		RepeatedExecutor executor = new RepeatedExecutor();
		executor.execute(runnable, 3);
	}

	private static void concurrentCollections() {
		/*
		 * (1) BlockingQueue = This interface extends the Queue interface. 
		 * 		In BlockingQueue, if the queue is empty, it waits (i.e., blocks) for an element to be inserted, and if the queue 
		 * 		is full, it waits for an element to be removed from the queue.
		 * (2) ArrayBlockingQueue = This class provides a fixed-sized array based implementation of the BlockingQueue interface.
		 * (3) LinkedBlockingQueue = This class provides a linked-list-based implementation of the BlockingQueue interface.
		 * (4) DelayQueue = This class implements BlockingQueue and consists of elements that are of type Delayed. 
		 * 		An element can be retrieved from this queue only after its delay period.
		 * (5) PriorityBlockingQueue = Equivalent to java.util.PriorityQueue, but implements the BlockingQueue interface.
		 * (6) SynchronousQueue = This class implements BlockingQueue. In this container, each insert() by a thread waits (blocks) 
		 * 		for a corresponding remove() by another thread and vice versa. 
		 * (7) LinkedBlockingDeque = This class implements BlockingDeque where insert and remove operations could block; uses a 
		 * 		linked-list for implementation.
		 * (8) ConcurrentHashMap = Analogous to Hashtable, but with safe concurrent access and updates.
		 * (9) ConcurrentSkipListMap = Analogous to TreeMap, but provides safe concurrent access and updates.
		 * (10) ConcurrentSkipListSet = Analogous to TreeSet, but provides safe concurrent access and updates.
		 * (11) CopyOnWriteArrayList = Similar to ArrayList, but provides safe concurrent access. When the container is modified, 
		 * 		it creates a fresh copy of the underlying array.
		 * (12) CopyOnWriteArraySet = A Set implementation, but provides safe concurrent access and is implemented using 
		 * 		CopyOnWriteArrayList. When the container is modified, it creates a fresh copy of the underlying array.
		 */
		// Normal modification of a collection while iterating will cause an exception
//		List<String> people = new ArrayList<>(Arrays.asList("Un", "Deux", "Trois"));
//		System.out.println("Regular list = "+people);
//		for(Iterator<String> peopleIt = people.iterator(); peopleIt.hasNext(); ) {
//			String person = peopleIt.next();
//			System.out.println(person);
//			
//			// Throws Exception in thread "main" java.util.ConcurrentModificationException
//			people.add(String.format("_%s_", person.toUpperCase()));
//		}
//		System.out.println("After iterate/modify = "+people);
		
		// But if we use a synchronized collection
		List<String> people = new CopyOnWriteArrayList<>(Arrays.asList("Un", "Deux", "Trois"));
		System.out.println("Regular list = "+people);
		for(Iterator<String> peopleIt = people.iterator(); peopleIt.hasNext(); ) {
			String person = peopleIt.next();
			System.out.println(person);
			
			// Throws Exception in thread "main" java.util.ConcurrentModificationException
			people.add(String.format("_%s_", person.toUpperCase()));
		}
		System.out.println("After iterate/modify = "+people);
	}

	private static void cyclicBarrier() {
		/*
		 * A way of synchronizing threads so that they execute after all others reach a certain point.
		 */
		// Imagine a tennis game of doubles (4 players)
		class MixedDoubleTennisGame extends Thread {
			public void run() {
				System.out.println("All four players ready ... START GAME");
			}
		}
		
		// When a player arrives, we need to wait for others before starting the game
		class Player extends Thread {
			CyclicBarrier waitPoint;

			public Player(CyclicBarrier barrier, String name) {
				this.setName(name);
				this.waitPoint = barrier;
				this.start();
			}

			public void run() {
				System.out.println("Player {" + getName() + "} has arrived ");
				try {
					int arrivalIndex = waitPoint.await(); // await for all four players to arrive
					System.out.println("> Player {" + getName() + "} arrived #"+arrivalIndex);
				} catch (BrokenBarrierException | InterruptedException exception) {
					System.out.println("An exception occurred while waiting for players: " + exception);
				}
			}
		}
		
		System.out.println("Reserving tennis court, waiting for players");
		CyclicBarrier barrier = new CyclicBarrier(4, new MixedDoubleTennisGame());
		new Player(barrier, "G I Joe");
		new Player(barrier, "Dora");
		new Player(barrier, "Tintin");
		new Player(barrier, "Barbie");
	}

	private static void usingAtomicVariables() {
		RunnableAtomic rc = new RunnableAtomic();
		for(int c=0; c<10; c++) {
			Thread t = new Thread(rc);
			t.start();
		}
	}

	private static void threadSynchronization() {
		RunnableCounter rc = new RunnableCounter();
		for(int c=0; c<10; c++) {
			Thread t = new Thread(rc);
			t.start();
		}
	}

	private static void creatingThreads() {
		// Thread
		Thread t = new ThreadCls();
		t.start();
		System.out.println("C17_Concurrency.main(1): thread="+Thread.currentThread().getName());
		
		// Runnable thread
		Thread r = new Thread(new RunnableImpl());
		r.start();
		System.out.println("C17_Concurrency.main(2): thread="+Thread.currentThread().getName());
	}
}

class RunnableAtomic implements Runnable {
	@Override
	public void run() {
		for(int c=0; c<5; c++) { increment(c); }
	}
	
	private void increment(int iteration) {
		long previous = CounterAtomic.count.getAndIncrement();
		System.out.println("previous="+previous+", after="+CounterAtomic.count+" {in "+
				Thread.currentThread().getName()+", #"+(iteration+1)+"}");
	}
}

class CounterAtomic {
	public static AtomicLong count = new AtomicLong(0);
	/*
	 * Other atomic variables include:
	 * - AtomicInteger (extends Number)
	 * - AtomicBoolean
	 * - AtomicIntegerArray and AtomicLongArray
	 * - AtomicReference<V>
	 * - AtomicReferenceArray<V>
	 */
}

@SuppressWarnings("unused")
class RunnableCounter implements Runnable {
	@Override
	public void run() {
		for(int c=0; c<5; c++) { increment(c); }
	}

	private void incrementNotThreadSafe(int iteration) {
		Counter.count++;
		System.out.println("Count="+Counter.count+" {in "+Thread.currentThread().getName()+", #"+(iteration+1)+"}");
	}

	private void incrementSyncBlock(int iteration) {
		synchronized(this) {
			Counter.count++;
			System.out.println("Count="+Counter.count+" {in "+Thread.currentThread().getName()+", #"+(iteration+1)+"}");
		}
	}

	private synchronized void increment(int iteration) {
		Counter.count++;
		System.out.println("Count="+Counter.count+" {in "+Thread.currentThread().getName()+", #"+(iteration+1)+"}");
	}
}

class Counter {
	public static long count = 0;
}

class RunnableImpl implements Runnable {
	@Override
	public void run() {
		System.out.println("RunnableImpl.run(): thread="+Thread.currentThread().getName());
	}
}

class ThreadCls extends Thread {
	@Override
	public void run() {
		try {
			System.out.println("ThreadCls.run(): thread="+this.getName());
			sleep(5000);
		} catch(InterruptedException ex) {
			System.err.println("Safe to ignore this error: "+ex.getMessage());
		}
	}
}