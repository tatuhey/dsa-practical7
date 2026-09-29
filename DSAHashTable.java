import java.io.*;
import java.util.*;

public class DSAHashTable {
    private int count = 0;
    private int actualSize = 0;
    private DSAHashEntry[] hashArray;
    
    //region constructor
    //taken from https://www.geeksforgeeks.org/dsa/hash-table-data-structure/ aswell
    public DSAHashTable(int size) {
        actualSize = nextPrime(size);

        hashArray = new DSAHashEntry[actualSize];
        for(int i = 0; i < actualSize; i++) {
            hashArray[i] = new DSAHashEntry();
        }
    }

    private int nextPrime(int digit) {
        int primeTest;
        boolean isPrime = false;

        if(digit % 2 == 0)
            primeTest = digit - 1;
        else
            primeTest = digit;

        do { 
            primeTest = primeTest + 2;
            int i = 3;
            isPrime = true;

            double rootPrime = Math.sqrt(primeTest);
            do { 
                if(primeTest % i == 0)
                    isPrime = false;
                else
                    i = i + 2;
            } while (i <= rootPrime && (isPrime));
        } while (!isPrime);

        return primeTest;
    }
    //endregion

    //region accessor
    public int countVal() {
        return count;
    }

    public int actualSizeVal() {
        return actualSize;
    }
    //endregion

    //region mutator
    public void put(String inKey, Object inValue) {
        int length = hashArray.length;
        int index = hashFunction(inKey, length);

        while(hashArray[index].getState() == 1) { // was hasharray [idx] != null
            index = (index + stepHash(inKey)) % hashArray.length; // was just idx + stephash
        }
        
        hashArray[index].setAll(inValue, inKey);

        System.out.println("This hash entry has been added. " + hashArray[index].toString());
        count++;

        double lf = getLoadFactor();

        if(lf >= 0.6) { // if load factor is more than 0.65, run resize
            resize(getNewSize());
        }

    }

    private int hashFunction(String key, int arraySize) {
        int hashIndex = 0;
        for(int i = 0; i <= key.length() - 1; i++)
            hashIndex = (33 * hashIndex) + key.charAt(i); //bernstein hash function

        return hashIndex % arraySize;
    }

    private int stepHash(String key) {
        int value;
        int hashStep;

        value = Integer.parseInt(key);
        hashStep = 5 - (value % 5); // 5 is max_step; must be prime number

        return hashStep;
    }

    private int find(String inKey) {
        int length = hashArray.length;
        int hashIndex = hashFunction(inKey, length);
        int oriIndex = hashIndex;
        boolean found = false;
        boolean giveUp = false;
        
        while(!found && !giveUp) {
            if(hashArray[hashIndex].getState() == 0)
                giveUp = true;
            else if(hashArray[hashIndex].getKey().equals(inKey)) // not using == for string
                found = true;
            else {
                hashIndex = (hashIndex + stepHash(inKey)) % hashArray.length;
                if(hashIndex == oriIndex)
                    giveUp = true;
            }
        }

        if(!found)
            throw new NoSuchElementException("Key " + inKey + " is not found");

        return hashIndex;
    }

    public Object get(String inKey) {
        int idx = find(inKey);

        while(hashArray[idx].getValue() == null)
            idx = idx + stepHash(inKey);

        return hashArray[idx].getValue();
    }

    public void remove(String inKey) {
        int idx = find(inKey);

        while(hashArray[idx].getValue() == null)
            idx = idx + stepHash(inKey);

        System.out.print("This hash entry will be deleted: ");
        System.out.println(hashArray[idx].toString());

        hashArray[idx].delAll();
        count--;
        System.out.println("Hash entry has been deleted");

        double lf = getLoadFactor();

        if(lf <= 0.4) {
            resize(getNewSize());
        }
        
    }

    public double getLoadFactor() {
        return (double) count/actualSize;
    }

    private int getNewSize() {
        return (int) Math.round(count/0.5);
    }

    private void resize(int newSize) {
        int newActualSize = nextPrime(newSize);
        int newCount = 0;

        DSAHashEntry[] newHA = new DSAHashEntry[newActualSize];

        for(int i = 0; i < newActualSize; i++)
            newHA[i] = new DSAHashEntry(); // initialising all arrays

        for(int i = 0; i < actualSize; i++) {
            if(hashArray[i].getState() == 1) {

                String key = hashArray[i].getKey();
                Object value = hashArray[i].getValue();

                int index = hashFunction(key, newActualSize);
                newCount++;

                while(newHA[index].getState() == 1) {
                    index = (index + stepHash(key)) % newActualSize;
                }

                newHA[index].setAll(value, key);
        
                }
            }
        hashArray = newHA;
        actualSize = newActualSize;
        count = newCount;

    }

    // taken from https://stackoverflow.com/questions/32684139/how-do-i-write-an-array-to-csv-in-java
    public void export(String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for(int i = 0; i < hashArray.length; i++) {
                if(hashArray[i].getState() == 1)
                    writer.write(hashArray[i].getKey() + "," + hashArray[i].getValue() + "\n");
            }
            
            writer.close();
        } catch(IOException e) {
            System.out.println("Error during exporting the file. " + e.getMessage());
        }
    }

    //endregion

}