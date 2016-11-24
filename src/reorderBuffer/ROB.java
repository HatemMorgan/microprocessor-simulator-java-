package reorderBuffer;

public class ROB {
	private ROBEntry head;
	private ROBEntry tail;
	private ROBEntry first;
	private ROBEntry last;
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
				last = r;
				// linking the before last with last one
				current.next = last;
				
				// link the last entry with the first one to act as a circular linked list
				last.next = first;
			
				continue;
			}
			
			// middle entry in the ROB linkedlist data structure
			ROBEntry r = new ROBEntry(i,null,null, null, false);
			current.next = r;
			current = current.next;
			
		}
		
	}
	
	
	private void insertToROB(ROBEntryType type, ROBEntryDestination destination){
		if(!isFull()){
		tail.setType(type);
		tail.setDestination(destination);
		tail= tail.next;
		}else{
			System.out.println("ROB is Full you cannot insert a new Entry");
		}
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
	
	
	private void commit(int entryNumber){
		// check if the head is pointing to this entry inorder to maintain inorder committing 
		if(head.getEntryNumber() == entryNumber && head.isReady()){
			ROBEntry current = head.next;
			
			if(head.equals(first)){
				head = new ROBEntry(head.getEntryNumber(),null,null,null,false);
				head.next = current ;
				first = head;		// emptying the first entry
				head = current;    // moving head to the next entry
				last.next = first; // connecting last to new first to maintain that the linked list is circular
				tail = tail.next; // moving tail to next empty element
				return;

			}
				ROBEntry prevHead = first ;
				while(!prevHead.next.equals(head)){
					prevHead = prevHead.next;
				}
			
				head = new ROBEntry(head.getEntryNumber(),null,null,null,false);
				head.next = current ;
				prevHead.next = head ; // connecting the previous entry with the new empty entry
				head = current; // moving head to next entry
				tail = tail.next;
			
			
		
			
			System.out.println("entry with number #"+entryNumber+" is committed and removed from the ROB");
		}else{
			if (head.getEntryNumber() != entryNumber) {
			System.out.println("Cannot commit because the head is not pointing to this entry = #"+entryNumber+" .");
			return;
			}
			
			if(!head.isReady()){
				System.out.println("Connot commit because the ROB entry the head is pointing to it is not ready(instruction is not in commit stage) ");
			}
			
		}
	}
	// check if the head and tail are pointing to the same entry
	// and also check that head.next has type != null to make sure that we are not at the begining
	private boolean isFull(){
		return head.equals(tail)&&head.next.getType()!=null ;
	}
	
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
		ROB r = new ROB(4);
		r.insertToROB(ROBEntryType.LD, ROBEntryDestination.R1);
		r.insertToROB(ROBEntryType.SD, ROBEntryDestination.R2);
		r.insertToROB(ROBEntryType.LD, ROBEntryDestination.R3);
		r.insertToROB(ROBEntryType.INT, ROBEntryDestination.R4);
		r.insertToROB(ROBEntryType.INT, ROBEntryDestination.R5); // testing to insert a new entry when the ROB is FUll
		
		r.writeResultTOROB(3, "24"); // testing entring a new entry
		r.commit(3); // testing committing an entry that the head is not pointing to it
		r.commit(1); // testing committing an entry that is not ready
		
		r.writeResultTOROB(1,"200");
		r.commit(1); // testing to commit an entry that is ready
		
		// testing committing all ROB entries
		r.writeResultTOROB(2, "1000");
		r.writeResultTOROB(4, "500");
		r.commit(2);
		r.commit(3);
		r.commit(4);
		
		r.insertToROB(ROBEntryType.INT, ROBEntryDestination.R6); // testing if the new entry will be in the first ROB entry
		
		System.out.println("------------------------------------------");
		r.printROB();
	}
	
}
