package memory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;
public class Clock {
	public static AtomicInteger counter;
	private boolean finish;
	public Clock(){
		counter = new AtomicInteger();
		finish = false;
		this.start();

	}
	
	
	


	public void setFinish(boolean finish) {
		this.finish = finish;
	}



	public void start(){
		Runnable run = new Runnable() {
		    public void run() {
		    	while(!finish){
					counter.incrementAndGet();
					System.out.println(counter);
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
