# DSA Hash Table Implementation

This project is a custom Hash Table implementation in Java. It features a dynamically resizing hash table that uses double hashing for collision resolution. The project includes an interactive Command Line Interface (CLI) to manipulate the data structure, allowing users to add, find, and remove entries, as well as load from and export to CSV files.

## Features

*   **Double Hashing:** Utilizes a primary Bernstein hash function and a secondary step hash function to effectively manage and resolve collisions.
*   **Dynamic Resizing:** Automatically resizes the hash table based on the load factor to maintain optimal performance.
    *   Expands when the load factor reaches $\ge 0.6$.
    *   Shrinks when the load factor drops to $\le 0.4$.
    *   Always resizes to the next prime number to minimize collisions.
*   **State Management:** Hash entries maintain a state (`free`, `used`, or `previously-used`/tombstone) to ensure accurate probing during insertions and deletions.
*   **CSV Import/Export:** Automatically reads initial data from a CSV file (`RandomNames7000.csv`) upon startup and allows exporting the current state of the hash table back to a CSV file.
*   **Interactive CLI:** Provides a user-friendly terminal menu for interacting with the hash table.

## File Structure

*   `DSAHashEntry.java`: Defines the data object for each slot in the hash table. It stores the key, value, and the state of the entry.
*   `DSAHashTable.java`: Contains the core logic for the Hash Table, including the hashing algorithms, put/get/remove operations, load factor calculations, and resizing logic.
*   `main.java`: The main driver class that handles the interactive CLI, user input, and file I/O operations.

## Prerequisites

*   Java Development Kit (JDK) 8 or higher.
*   A file named `RandomNames7000.csv` in the root directory (the program expects this file to preload data upon running). Ensure it is formatted as `key,value` on each line.

## How to Compile and Run

1. Open your terminal or command prompt.
2. Navigate to the directory containing the source files.
3. Compile the Java files using the following command:
   ```bash
   javac main.java DSAHashTable.java DSAHashEntry.java
   ```
4. Run the program:
   ```bash
   java main
   ```

## Usage

Once the program is running, you will be presented with a menu:

1. **Add entry:** Prompts you to input a key and a value to insert into the hash table.
2. **Find entry:** Prompts you for a key and returns the corresponding value if it exists.
3. **Remove entry:** Prompts you for a key and removes that entry from the hash table.
4. **Load factor:** Displays the current load factor, the total number of items, and the actual allocated size of the hash table array.
5. **Export:** Prompts you for a filename and exports the current active entries in the hash table to a CSV file.
0. **exit:** Closes the application.