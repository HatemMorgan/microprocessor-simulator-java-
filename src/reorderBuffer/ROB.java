package reorderBuffer;

public class ROB {
	private ROBEntry head;
	private ROBEntry tail;
	private ROBEntry first;
	private int size ;

	
	public ROB (int size){
		this.size = size;
		init();
	}
	
	private void init(){
		ROBEntry current = null;
		for(int i=1;i<=size;i++){
			// first entry in the ROB linked list data structure
			if(i==1){
				ROBEntry r = new ROBEntry(i,null,null, null, false);
				head = r;
				tail = r;
				first = r;
				current = head;
				continue;
			}
			
			if(i== size){
				// last entry in the ROB linkedlist data structure
				ROBEntry r = new ROBEntry(i,null,null, null, false);
				
				// linking the before last with last one
				current.next = r;
				
				// link the last entry with the first one to act as a circular linked list
				current = current.next;
				current.next = first;
			
				continue;
			}
			
			// middle entry in the ROB linkedlist data structure
			ROBEntry r = new ROBEntry(i,null,null, null, false);
			current.next = r;
			current = current.next;
			
		}
		
	}
	
	
	private void insertToROB(ROBEntryType type, ROBEntryDestination destination){
		tail.setType(type);
		tail.setDestination(destination);
		tail= tail.next;
	}
	
	private void writeResultTOROB(int entryNumber,String result){
		ROBEntry current = first ;
		boolean found = false ;
		while (current.next != first){
			if(current.getEntryNumber() == entryNumber){
				current.setValue(result);
				current.setReady(true);
				found =true;
				break;
			}else{
				current = current.next;
			}
		}
		// if not found check the last entry of ROB
		if(!found){
			if(current.getEntryNumber()== entryNumber){
				current.setValue(result);
				current.setReady(true);
				found =true;
			}else{ // wrong entry number 
				System.out.println("Wrong entry number");
			}
		}
	}
	
	
	// TODO commiting and testing
	
	
	private void printROB(){
		ROBEntry current = first ;
		while (current.next != first){
			System.out.println(current.toString());
			current = current.next;
		}
		// print the last link in the 
		System.out.println(current.toString());
	}
	
	
	public static void main(String[] args) {
		ROB r = new ROB(10);
		r.printROB();
	}
	
}
