package reorderBuffer;

import instructionSetArchitecture.AddImmediateInstruction;
import instructionSetArchitecture.AddInstruction;
import instructionSetArchitecture.InstructionSetArchitecture;
import instructionSetArchitecture.LoadInstruction;
import instructionSetArchitecture.MulInstruction;
import instructionSetArchitecture.StoreInstruction;
import registers.RegisterEnum;
import reservationStations.Operation;

public class ROB {
	private ROBEntry head;
	private ROBEntry tail;
	private ROBEntry first;
	private ROBEntry last;
	private int size;
	private int robentryNumberOfInOrderCommittedISA;
	// make sure to insert instruction inorder when issuing
	private int robLastinstructionNumEntry ;

	public ROB(int size) {
		this.size = size;
		init();
		this.robentryNumberOfInOrderCommittedISA = 1 ;
		this.robLastinstructionNumEntry = 1;
	}

	private void init() {
		ROBEntry current = null;
		for (int i = 1; i <= size; i++) {
			// first entry in the ROB linked list data structure
			if (i == 1) {
				ROBEntry r = new ROBEntry(i, null, null, null, false);
				head = r;
				tail = r;
				first = r;
				current = head;
				continue;
			}

			if (i == size) {
				// last entry in the ROB linkedlist data structure
				ROBEntry r = new ROBEntry(i, null, null, null, false);
				last = r;
				// linking the before last with last one
				current.next = last;

				// link the last entry with the first one to act as a circular
				// linked list
				last.next = first;

				continue;
			}

			// middle entry in the ROB linkedlist data structure
			ROBEntry r = new ROBEntry(i, null, null, null, false);
			current.next = r;
			current = current.next;

		}

	}

	public int insertToROB(InstructionSetArchitecture type) {
		if (isFull()) {
			System.out.println("ROB is Full you cannot insert a new Entry");
			return 0;
		}

		tail.setType(type);
		tail.setDestination(type.getDestinationRegister());
		int robNum = tail.getEntryNumber();
		tail = tail.next;
		return robNum;

	}

	public synchronized void writeResultTOROB(int entryNumber, Short result) {
		ROBEntry current = first;
		boolean found = false;
		while (current.next != first) {
			if (current.getEntryNumber() == entryNumber) {
				current.setValue(result);
				current.setReady(true);
				found = true;
				break;
			} else {
				current = current.next;
			}
		}
		// if not found check the last entry of ROB
		if (!found) {
			if (current.getEntryNumber() == entryNumber) {
				current.setValue(result);
				current.setReady(true);
				found = true;
			} else { // wrong entry number
				System.out.println("Wrong entry number");
			}
		}
	}

	public synchronized void commit() {
		// check if the head is pointing to this entry inorder to maintain
		// inorder committing
		if (head.isReady()) {

			System.out.println("entry with number #" + head.getEntryNumber()
					+ " is committed and removed from the ROB");

			ROBEntry current = head;
			head = head.next;

			current.setDestination(null);
			current.setReady(false);
			current.setType(null);
			current.setValue(null);

		}
	}

	// check if the head and tail are pointing to the same entry
	// and also check that head.next has type != null to make sure that we are
	// not at the begining
	public boolean isFull() {
		return head.equals(tail) && head.next.getType() != null;
	}

	public void printROB() {
		ROBEntry current = first;
		while (current.next != first) {
			System.out.println(current.toString());
			current = current.next;
		}
		// print the last link in the
		System.out.println(current.toString());

	}

	public synchronized boolean IsReady(int robNum) {
		if (robNum > size) {
			System.out.println("Invalid ROB number");
			return false;
		}
		ROBEntry current = first;
		while (current.getEntryNumber() != robNum) {
			current = current.next;
		}

		return current.isReady();

	}

	public synchronized Short getValue(int robNum) {
		if (robNum > size) {
			System.out.println("Invalid ROB number");
			return null;
		}
		ROBEntry current = first;
		while (current.getEntryNumber() != robNum) {
			current = current.next;
		}

		return current.getValue();

	}

	public synchronized void setROBEntryReady(int robNum) {
		if (robNum > size) {
			System.out.println("Invalid ROB number");
			return;
		}
		ROBEntry current = first;
		while (current.getEntryNumber() != robNum) {
			current = current.next;
		}

		current.setReady(true);
	}

	public synchronized int getROBEntryNumber(
			InstructionSetArchitecture instruction) {

		if (first.getType().equals(instruction)) {
			return first.getEntryNumber();
		}

		ROBEntry current = first.next;
		while (current != first) {
			if (current.getType().equals(instruction))
				break;

			current = current.next;

		}

		return current.getEntryNumber();

	}
	
	
	
	public int getSize() {
		return size;
	}

	public int getHeadROBEntryNum(){
			return head.getEntryNumber();
	}

	public int getRobentryNumberOfInOrderCommittedISA() {
		return robentryNumberOfInOrderCommittedISA;
	}

	public void setRobentryNumberOfInOrderCommittedISA(
			int robentryNumberOfInOrderCommittedISA) {
		this.robentryNumberOfInOrderCommittedISA = robentryNumberOfInOrderCommittedISA;
	}
	
	

	public int getRobLastinstructionNumEntry() {
		return robLastinstructionNumEntry;
	}

	public void setRobLastinstructionNumEntry(int robLastinstructionNumEntry) {
		this.robLastinstructionNumEntry = robLastinstructionNumEntry;
	}

	public static void main(String[] args) {
		ROB r = new ROB(4);
		r.insertToROB(new AddInstruction(RegisterEnum.R2, 1, RegisterEnum.R3,
				RegisterEnum.R4));
		r.insertToROB(new MulInstruction(RegisterEnum.R6, 2, RegisterEnum.R1,
				RegisterEnum.R4));
		r.insertToROB(new LoadInstruction(RegisterEnum.R1, 3, RegisterEnum.R4,
				(short) 120));
		r.insertToROB(new StoreInstruction(RegisterEnum.R2, 4, RegisterEnum.R5,
				(short) 299));
		r.insertToROB(new AddImmediateInstruction(RegisterEnum.R7, 5,
				RegisterEnum.R3, (short) 2000)); // testing to insert a new
													// entry when the ROB is
													// FUll

		r.writeResultTOROB(3, (short) 24); // testing entring a new entry
		r.commit(); // testing committing an entry that the head is not
					// pointing to it
		r.commit(); // testing committing an entry that is not ready

		r.writeResultTOROB(1, (short) 200);

		// getting value of the written RobEntry
		System.out.println("-------------->" + r.IsReady(1));
		System.out.println("-------------->" + r.getValue(1));
		// get rob entry number by passing instruction
		System.out.println("-------------->"
				+ r.getROBEntryNumber(new AddInstruction(RegisterEnum.R2, 1,
						RegisterEnum.R3, RegisterEnum.R4)));
		r.printROB();
		System.out
				.println("----------------------------------------------------------------------");

		r.commit(); // testing to commit an entry that is ready

		// testing committing all ROB entries
		r.writeResultTOROB(2, (short) 1000);
		r.writeResultTOROB(4, (short) 500);
		r.commit();
		r.commit();
		r.commit();

		// r.insertToROB(ROBEntryType.INT, Register.R6); // testing if the new
		// entry will be in the first ROB entry

		System.out.println("------------------------------------------");
		r.printROB();
	}

}
