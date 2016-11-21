# microprocessor-simulator-java
## Getting Started


The entire memory needs a clock in order to work

### Clock Setup
``` 
Clock c = new Clock();
c.start();
``` 

## Data Memory
### Creation
``` 
DataMemory memory = new DataMemory(int cacheLevels, int mainMemoryAccessTimeInCycles, Clock clock, writeHitPolicy hitPolicy, writeMissPolicy missPolicy);
```
### Load
``` 
memory.load(int byteAddress);
```
Returns String value


### Store
``` 
memory.store(String value, int byteAddress);
```

## Instruction Memory
### Creation
``` 
InstructionMemory memory = new InstructionMemory(int cacheLevels, int mainMemoryAccessTimeInCycles, Clock clock, writeHitPolicy hitPolicy, writeMissPolicy missPolicy);
``` 


### Store Instruction
- gets an array of instructions to place into memory and the start address of the program
- places the instructions sequentially in the instruction memory

``` 
  memory.placeInstructionsInMemory(int startAddress, String[] instructions) {

``````


### Read Instruction
- gets number of instructions to read and the start address of the program
- returns an array of strings containing the instructions

```
  memory.readInstructions(int instructionsToFetch, int startAddress);

```
Returns Array of Strings (Instructions)
