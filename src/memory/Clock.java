package memory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;
public class Clock {
	public AtomicInteger counter;
	
	public Clock(){
		counter = new AtomicInteger();
	}
	
	public void start(){
		Runnable run = new Runnable() {
		    public void run() {
		    	while(true){
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
	
	
	
}
