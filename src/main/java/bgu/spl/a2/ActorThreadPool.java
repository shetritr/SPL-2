package bgu.spl.a2;


import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
	private AtomicInteger MonitorNumber;
	private VersionMonitor versionMonitor;
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
		MonitorNumber = new AtomicInteger(0);
		versionMonitor = new VersionMonitor();
		threads = new LinkedList<Thread>();
		istopped = false;
		for (int i = 0; i < nthreads; i++) {
			threads.add(new Thread(()->{
				while(!istopped){
					String ActorName = "";
					AtomicBoolean foundActor = new AtomicBoolean(false);
					LinkedList Actor = null;
					synchronized (actors) {
						Iterator<String> ActorsNames = actors.keySet().iterator();
						while (ActorsNames.hasNext() && !foundActor.get()) {
							ActorName = ActorsNames.next();
							Actor = actors.get(ActorName);
							if ((Boolean) Actor.get(3) && !((Queue)Actor.get(0)).isEmpty()) {
								foundActor.set(true);
								Actor.set(3, false);
							}
						}
					}
					if(foundActor.get()){
						((Action<?>)((Queue)Actor.get(0)).poll()).handle(this,ActorName,(PrivateState) Actor.get(1));
						Actor.set(3, true);
						versionMonitor.inc();
					}
					try {
						versionMonitor.await(versionMonitor.getVersion());
					}catch (InterruptedException e){
						break;
					}
				}

			}));
		}
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
					ConcurrentLinkedQueue <Action> actionQueue = new ConcurrentLinkedQueue<Action>();
					actionQueue.add(action);
					tempAction.addFirst(actionQueue);//made a list of the 1)action 2)PrivateState 3)actorId 4)boolean state
					tempAction.add(true);
					actors.put(actorId, tempAction);
				} else {
					((ConcurrentLinkedQueue) actors.get(actorId).getFirst()).add(action);
				}
				//versionMonitor.inc();
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
	public synchronized void start() {
		for (Thread thisThread:threads) {
			thisThread.start();
		}
	}

}
