package bgu.spl.a2;


import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * represents an actor thread pool - to understand what this class does please
 * refer to your assignment.
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */
public class ActorThreadPool {
	private HashMap<String,LinkedList> actors;
	private int MonitorNumber;
	private List <Thread> threads;
	boolean istopped;

	/**
	 * creates a {@link ActorThreadPool} which has nthreads. Note, threads
	 * should not get started until calling to the {@link #start()} method.
	 *
	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 *
	 * @param nthreads
	 *            the number of threads that should be started by this thread
	 *            pool
	 */
	public ActorThreadPool(int nthreads) {
		actors = new HashMap<String,LinkedList>();
		MonitorNumber = -1;
		for (int i = 0; i < nthreads; i++) {
			threads.add(new Thread());
		}
		istopped = false;
	}

	/**
	 * submits an action into an actor to be executed by a thread belongs to
	 * this thread pool
	 *
	 * @param action
	 *            the action to execute
	 * @param actorId
	 *            corresponding actor's id
	 * @param actorState
	 *            actor's private state (actor's information)
	 */
	public void submit(Action<?> action, String actorId, PrivateState actorState) {
		if(!istopped) {
			synchronized (actors) {
				if (!actors.containsKey(actorId)) {
					LinkedList tempAction = new LinkedList();
					tempAction.addFirst(actorId);
					tempAction.addFirst(actorState);
					tempAction.addFirst(new ConcurrentLinkedQueue<Action>().add(action));//made a list of the 1)action 2)PrivateState 3)actorId
					actors.put(actorId, tempAction);
				} else {
					((ConcurrentLinkedQueue) actors.get(actorId).getFirst()).add(action);
				}
			}
		}
	}

	/**
	 * closes the thread pool - this method interrupts all the threads and waits
	 * for them to stop - it is returns *only* when there are no live threads in
	 * the queue.
	 *
	 * after calling this method - one should not use the queue anymore.
	 *
	 * @throws InterruptedException
	 *             if the thread that shut down the threads is interrupted
	 */
	public synchronized void shutdown() throws InterruptedException {
		istopped = true;
		for (Thread tempforshutdown:threads) {
			tempforshutdown.interrupt();
			}
		for (Thread tempforshutdown:threads ) {
			tempforshutdown.join();
		}

	}

	/**
	 * start the threads belongs to this thread pool
	 */
	public void start() {

	}

}
