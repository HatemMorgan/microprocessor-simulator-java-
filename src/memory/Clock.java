package memory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;
public class Clock {
	public static AtomicInteger counter;
	public static boolean autoClock = true;
	public Clock(){
		counter = new AtomicInteger();
//		this.start();
	}
	
	public void start(){
		Runnable run = new Runnable() {
		    public void run() {
		    	while(autoClock){
					counter.incrementAndGet();
//					System.out.println(counter);
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
		    }
		 };
		 new Thread(run).start();

		
		
	}
	
	
	public void increment(){
		counter.incrementAndGet();
	}
	public void stopClock(){
		autoClock = false;
	}
	public void runClock(){
		autoClock = true;
	}
	
	
	
}
