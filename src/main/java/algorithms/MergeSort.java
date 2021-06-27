package algorithms;

public class MergeSort {

    public static void sort(int[] array, int value) {
        if (value < 2) {
            return;
        }
        int mid = value / 2;
        int[] l = new int[mid];
        int[] r = new int[value - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = array[i];
        }
        for (int i = mid; i < value; i++) {
            r[i - mid] = array[i];
        }
        sort(l, mid);
        sort(r, value - mid);

        merge(array, l, r, mid, value - mid);
    }

    public static void merge(
            int[] a, int[] l, int[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }
}
