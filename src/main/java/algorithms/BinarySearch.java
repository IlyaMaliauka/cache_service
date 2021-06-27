package algorithms;

public class BinarySearch {

    public static int iterativeBinarySearch(int[] array, int valueToFind) {
        if (!isSorted(array)) {
            InsertionSort.sort(array);
        }

        int low = 0, high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (valueToFind == array[mid]) {
                return mid;
            } else if (valueToFind < array[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }

    public static int recursiveBinarySearch(int[] array, int low, int high, int valueToFind) {
        if (!isSorted(array)) {
            InsertionSort.sort(array);
        }

        if (low > high) {
            return -1;
        }
        int mid = (low + high) / 2;

        if (valueToFind == array[mid]) {
            return mid;
        } else if (valueToFind < array[mid]) {
            return recursiveBinarySearch(array, low, mid - 1, valueToFind);
        } else {
            return recursiveBinarySearch(array, mid + 1, high, valueToFind);
        }
    }

    private static boolean isSorted(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
