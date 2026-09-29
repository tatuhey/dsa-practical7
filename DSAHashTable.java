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
        int index = hashFunction(inKey);

        while(hashArray[index] != null) {
            index = index + stepHash(inKey);
        }
        
        hashArray[index].setAll(inValue, inKey);

        System.out.print("This hash entry has been added: ");
        hashArray[index].toString();
        count++;

        double lf = getLoadFactor();

        if(lf >= 0.6) { // if load factor is more than 0.65, run resize
            resize(getNewSize());
        }

    }

    private int hashFunction(String key) {
        int hashIndex = 0;
        for(int i = 0; i <= key.length() - 1; i++)
            hashIndex = (33 * hashIndex) + key.charAt(i); //bernstein hash function

        return hashIndex % hashArray.length;
    }

    private int stepHash(String key) {
        int value;
        int hashStep;

        value = Integer.parseInt(key);
        hashStep = 5 - (value % 5); // 5 is max_step; must be prime number

        return hashStep;
    }

    private int find(String inKey) {
        int hashIndex = hashFunction(inKey);
        int oriIndex = hashIndex;
        boolean found = false;
        boolean giveUp = false;
        
        while(!found && !giveUp) {
            if(hashArray[hashIndex].getState() == 0)
                giveUp = true;
            else if(hashArray[hashIndex].getKey() == inKey)
                found = true;
            else {
                hashIndex = (hashIndex + 1) % hashArray.length;
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
        hashArray[idx].toString();

        hashArray[idx].delAll();
        count--;
        System.out.println("Hash entry has been deleted");

        double lf = getLoadFactor();

        if(lf <= 0.4) {
            resize(getNewSize());
        }
        
    }

    public double getLoadFactor() {
        return count/actualSize;
    }

    private int getNewSize() {
        return (int) Math.round(count/0.5);
    }

    private void resize(int newSize) {
        int newActualSize = nextPrime(newSize);
        int newCount = 0;

        DSAHashEntry[] newHA = new DSAHashEntry[newActualSize];
        for(int i = 0; i < actualSize; i++) {
            while(hashArray[i] != null) {
                Object val = hashArray[i].getValue();
                String key = hashArray[i].getKey();

                for(int j = 0; j < newActualSize; j++) {
                    newHA[j].setAll(val, key);
                    newCount++;
                }  
            }
        }
        hashArray = newHA;
        count = newCount;

    }
    //endregion

}