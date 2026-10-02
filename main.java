import java.io.*;
import java.util.*;


public class main {
    public static void main(String[] args) {
        int sel = 100000;
       
        Scanner sc = new Scanner(System.in);
        while(sel != 0) {
            System.out.println("Select menu:");
            System.out.println("1. Manual\n2. RandomNames7000.csv\n0. exit");
            
            try {
                sel = sc.nextInt();
                switch(sel) {
                    case 1:
                        // manual stuf
                        manualHashTbl(sc);
                        break;
                    case 2:
                        // randomnames7000csv
                        autoSelection(sc);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Wrong selection");
                }
            }  catch (InputMismatchException e) {
                sel = 100000;
                sc.nextLine();
                System.out.println(e + ". Please input selection properly.");
                
            } catch (NoSuchElementException e2) {
                System.out.println(e2);
            }
        
        }

        sc.close();
    }

    public static void manualHashTbl(Scanner sc) {
        int size = 0;
        String filename;

        try {
            System.out.print("Insert size of heap: ");
            size = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(e + ". Please input size of heap properly.");
        }

        System.out.println("Insert the file name: <filename.csv>");
        filename = sc.nextLine();

        DSAHashTable tbl = new DSAHashTable(size);
        readFile(filename, tbl);

        submenu(sc, tbl, filename);
    }

    public static void autoSelection(Scanner sc) {
        DSAHashTable hashTbl = new DSAHashTable(7000);

        readFile("RandomNames7000.csv", hashTbl);

        submenu(sc, hashTbl, "RandomNames7000.csv");
    }

    public static void submenu(Scanner sc, DSAHashTable hashTbl, String filname) {
        int sel = 1000000;
        String key = "";
        Object val = null;

        while(sel != 0) {
            System.out.println("Current file being worked on: " + filname);
            System.out.println("Select menu:");
            System.out.println("1. Add entry\n2. Find entry\n3. Remove entry\n4. Load factor\n5. Export\n0. exit");

            try {
                sel = sc.nextInt();
                switch(sel) {
                    case 1:
                        System.out.println("Add an entry");
                        sc.nextLine(); // to clean up terminal

                        System.out.print("Input key: ");
                        key = sc.nextLine();    

                        System.out.print("Input value: ");
                        val = sc.nextLine();
                        try {
                            hashTbl.put(key, val);
                        } catch (NumberFormatException e) {
                            System.out.println("Please insert a proper key and value. " + e.getMessage());
                        }
                        
                        break;
                    case 2:
                        System.out.println("Find an entry");
                        sc.nextLine(); // to clean up terminal

                        System.out.print("Input key: ");
                        key = sc.nextLine();

                        try {
                            System.out.println("Value: " + hashTbl.get(key));
                        } catch (NumberFormatException e) {
                            System.out.println("Please insert a proper key. " + e.getMessage());
                        }
                        
                        break;
                    case 3:
                        System.out.println("Remove an entry");
                        sc.nextLine(); // to clean up terminal

                        System.out.print("Input key: ");
                        key = sc.nextLine();

                        try {
                            hashTbl.remove(key);
                        } catch (NumberFormatException e) {
                            System.out.println("Please insert a proper key. " + e.getMessage());
                        }
                        
                        break;
                    case 4:
                        System.out.print("Load factor: ");
                        System.out.println(hashTbl.getLoadFactor());
                        System.out.print("Count = ");
                        System.out.println(hashTbl.countVal());
                        System.out.print("Actual size = ");
                        System.out.println(hashTbl.actualSizeVal());
                        break;
                    case 5:
                        System.out.println("Exporting hash table as a file");
                        sc.nextLine();

                        System.out.print("Insert filename: ");
                        String name = sc.nextLine();

                        hashTbl.export(name);
                        
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Wrong selection");
                }
                
            } catch (InputMismatchException e) {
                sel = 100000;
                sc.nextLine();
                System.out.println(e + ". Please input selection properly.");
                
            } catch (NoSuchElementException e2) {
                System.out.println(e2);
            }
        
        }
    }
   
    // taken from past PDI assignment
    public static void readFile(String pFilename, DSAHashTable tbl) {
        FileInputStream fileStream = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        int lineNum;
        String line;
        try {
            fileStream = new FileInputStream(pFilename);
            rdr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(rdr);
            lineNum = 0; 
            line = bufRdr.readLine();
            while(line != null)
            {
                lineNum++;
                //writeLog("readFile runs successfully");
                processLine(line, tbl);
                line = bufRdr.readLine();
            }
                fileStream.close();
                
        }
        catch(IOException e) {
            if(fileStream != null) {
                try {
                    fileStream.close();
                }
                catch(IOException e2){}
                }
                System.out.println("Error during reading the file. " + e.getMessage());
                //writeLog("Error during reading the file. " + e.getMessage());
        }
    }

    public static void processLine(String row, DSAHashTable tbl) {
        String[] splitLine;

        splitLine = row.split(",");

        if(splitLine.length >= 2){
            try {
                String key = splitLine[0];
                Object value = splitLine[1];

                tbl.put(key, value);            

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Array out of bound" + e.getMessage());
            }
        } else
            System.out.println("Invalid CSV row: " + row);

    }

    
}