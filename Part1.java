import java.util.Scanner;

public class Part1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Initialization
        // Prompt the user for the number of elements to sort
        System.out.print("Enter number of elements you want to sort: ");
        int n = scanner.nextInt();
        int[] numbers = new int[n]; // Array to hold the numbers for sorting

        // Prompt the user to enter positive integers and store them in the numbers array
        System.out.println("Enter " + n + " positive integers:");
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
            if (numbers[i] < 0) {
                // If a negative number is entered, print a message and terminate the program
                System.out.println("Only positive integers are allowed.");
                scanner.close();
                return;
            }
        }

        // Print the original list before sorting
        System.out.println("\nOriginal list:");
        printNumbers(numbers);
        System.out.println("----------------------------------------");

        // Directly sort the original array using radix sort
        radixSort(numbers);

        // Print the final sorted list
        System.out.println("----------------------------------------");
        System.out.println("Final Sorted list:");
        printNumbers(numbers); // Print the sorted original array
        scanner.close();
    }

    // sorts based on digit positions
    public static void radixSort(int[] numbers) {
        int n = numbers.length;
        int[][] arrayBoxes = new int[10][n]; // 10 array boxes for digits 0-9
        int[] count = new int[10];        // Count elements in each array box

        // Determine the maximum number of digits across all numbers
        int maxDigits = 0;
        for (int i = 0; i < n; i++) {
            int digits = String.valueOf(numbers[i]).length();
            if (digits > maxDigits) {
                maxDigits = digits; // Store the largest number of digits
            }
        }

        // Step 1: Initialization
        // Show empty array boxes before sorting starts
        System.out.println("1. Initialization");
        showEmptyArrayBoxes("Array");
        System.out.println("----------------------------------------");

        // Step 2: Iteration (sorting by each digit place)
        for (int digitPlace = 0; digitPlace < maxDigits; digitPlace++) {
            // Indicate which pass (digit place) is being processed
            System.out.println((digitPlace + 2) + ". Pass (sort by digit at index " + digitPlace + ")");
            
            // Reset array box counts for the current pass
            for (int i = 0; i < 10; i++) {
                count[i] = 0; // Clear the count for each digit (array box)
            }

            // Distribute numbers into the corresponding array boxes based on the current digit place
            for (int i = 0; i < n; i++) {
                int digit = (numbers[i] / (int) Math.pow(10, digitPlace)) % 10; // Extract the current digit
                arrayBoxes[digit][count[digit]] = numbers[i]; // Place number into the corresponding array box
                count[digit]++; // Increment the count for the current array box
            }

            // Print the current array box distribution after the pass
            printArrayBoxes(arrayBoxes, count, "Array", maxDigits);

            // Step 3: Reorder
            // Rebuild the numbers array based on the array boxes (flatten them into the numbers array)
            int index = 0;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < count[i]; j++) {
                    numbers[index++] = arrayBoxes[i][j]; // Reorder numbers back into the original array
                }
            }

            // Print a separator line after each pass
            System.out.println("----------------------------------------");
        }
    }

    // Self defined helper function: Show empty array boxes during initialization
    private static void showEmptyArrayBoxes(String name) {
        System.out.println("Array:");
        for (int i = 0; i < 10; i++) {
            // Display empty array boxes for each digit (0-9)
            System.out.print(i + ": [ ]  ");
            if (i == 4) System.out.println(); // Just for formatting (split the output)
        }
        System.out.println("\n");
    }

    // Self defined helper function: Display the contents of array boxes after each pass
    private static void printArrayBoxes(int[][] arrayBoxes, int[] count, String name, int maxDigits) {
        System.out.println("Array:");
        for (int i = 0; i < 10; i++) {
            // Print the contents of each array box
            System.out.print(i + ": [");
            for (int j = 0; j < count[i]; j++) {
                // Print each number inside the array box with leading zeros for formatting
                System.out.print(String.format("%0" + maxDigits + "d", arrayBoxes[i][j]) + " ");
            }
            System.out.print("]  ");
            if (i == 4) System.out.println(); // For formatting (split output into two lines)
        }
        System.out.println("\n");
    }

    // Self defined helper function: Display the array of numbers in formatted form
    private static void printNumbers(int[] numbers) {
        int maxDigits = 0;
        for (int i = 0; i < numbers.length; i++) {
            // Find the maximum number of digits in any number
            int digits = String.valueOf(numbers[i]).length();
            if (digits > maxDigits) {
                maxDigits = digits; // Update the maxDigits if a larger value is found
            }
        }

        // Print the numbers with leading zeros to ensure they have the same width
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(String.format("%0" + maxDigits + "d", numbers[i]) + " ");
        }        
        System.out.println();
    }
}
