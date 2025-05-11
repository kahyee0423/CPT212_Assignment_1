import java.util.Scanner;

class Part2 {

    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
       
        // Step 1: Initialization
        // Prompt the user for the number of elements to sort
        System.out.print("Enter number of elements you want to sort: ");
        int n = scanner.nextInt();
        String[] words = new String[n]; // Array to hold the numbers for sorting

        // Prompt the user to enter the word and store them in the words array
        System.out.println("Enter " + n + " word:");
        for (int i = 0; i < n; i++) {
            words[i] = scanner.next();
        }

        // Print the original list before sorting
        System.out.println("\nOriginal list:");
        printWords(words);
        System.out.println("----------------------------------------");

        // Directly sort the original array using radix sort
        radixSort(words);

        // Print the final sorted list
        System.out.println("----------------------------------------");
        System.out.println("Final Sorted list:");
        printWords(words); // Print the sorted original array   
        scanner.close();
    }

    // sorts based on alphabet positions
    public static void radixSort(String[] words) {

        int n = words.length; // Size of the array
        String[][] arrayBoxes = new String[27][n]; // 27 array boxes for a-z and * for padding purpose
        int[] count = new int[27]; // Count elements in each array box

        // Find the length of the longest word from the array
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            String word = words[i];
            if (word.length() > maxLen) {
                maxLen = word.length();
            }
        }

        // Add padding
        words = padLeft(words, maxLen);

        // Step 1: Initialization
        // Show empty array boxes before sorting starts
        System.out.println("1. Initialization");
        showEmptyArrayBoxes("Array");
        System.out.println("----------------------------------------");
        
        // Step 2: Iteration -Sort by alphabet one by one from right to left
        int passNo = 1;
        for (int pos=maxLen-1; pos>=0; pos--) {

            // Indicate which pass is being processed
            System.out.println("Pass Number " + passNo + " (sort by alphabet number " + (pos+1) + " )");
            
            // Reset array box counts for the current pass
            for (int i = 0; i < 27; i++) {
                count[i] = 0; // Clear the count for each digit (array box)
            }

            // Distribute words into the corresponding array boxes based on the current digit place
            for (int i = 0; i < words.length; i++) {
                char currentChar =words[i].charAt(pos);
                int currentCharPosition = -1;

                if(currentChar == 42) {
                     currentCharPosition = 0;
                } else {
                    currentCharPosition = (int) (currentChar - 'a'+1);
                }

                arrayBoxes[currentCharPosition][count[currentCharPosition]] = words[i]; // Place word into the corresponding array box
                count[currentCharPosition]++; // Increment the count for the current array box
            }

             // Print the current array box distribution after the pass
            printArrayBoxes(arrayBoxes, count, "Array");

             // Step 3: Reorder
            // Rebuild the words array based on the array boxes (flatten them into the words array)
            int index = 0;
            for (int i = 0; i < 27; i++) {
                for (int j = 0; j < count[i]; j++) {
                    words[index++] = arrayBoxes[i][j]; // Reorder words back into the original array
                }
            }

            passNo++;

            // Print a separator line after each pass
            System.out.println("----------------------------------------");
        }

        // Remove the stars
       words = removeStars(words);
    }

    // Self defined helper function: Show empty array boxes during initialization
    private static void showEmptyArrayBoxes(String name) {
        System.out.println("Array:");
        for (int i = 0; i < 27; i++) {
            if(i == 0) {
                System.out.print("* : [ ]  ");
            } else {
                System.out.print((char)(96+i) + " : [ ]  ");
            }
            if ( (i+1)%5 == 0) System.out.println(); // Just for formatting (split the output)
        }
        System.out.println("\n");
    }

    // Self defined helper function: Display the contents of array boxes after each pass
    private static void printArrayBoxes(String[][] arrayBoxes, int[] count, String name) {
        System.out.println("Array:");
        for (int i = 0; i < 27; i++) {
            // Print the contents of each array box
            if(i == 0) {
                System.out.print("*: [ ");
            } else {
                System.out.print((char)(96+i) + ": [ ");
            }

            for (int j = 0; j < count[i]; j++) {
                System.out.print(arrayBoxes[i][j] + " ");
            }
            System.out.println("]");
        }
        System.out.println("\n");
    }

    // Self defined helper function: Display the array of words in formatted form
    public static void printWords (String[] words) {
        for (int i = 0; i<words.length; i++) {
            System.out.print(words[i] + " ");
        }
        System.out.println();
    }

    // self-defined helper function - padLeft to help in adding padding
    public static String[] padLeft(String[] words, int maxLen) {
        for (int i=0; i<words.length; i++) {
            words[i] = String.format("%-" + maxLen + "s", words[i]).replace(' ', '*');
        }
        return words;
    }

    // self-defined helper function - removeStars
    public static String[] removeStars(String[] words) {
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("\\*+$", "");
        }
        return words;
    }

}
