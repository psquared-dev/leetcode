import java.util.Arrays;

public class NonConstructibleChange {

    public static int find(int[] arr){
        if(arr.length == 0){
            return 1;
        }

        int[] copy = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copy);

        System.out.println(Arrays.toString(copy));

        int change = 0;

        for (int i : copy) {
            if(change + 1 < i){
                return change + 1;
            }
            change += i;
        }

        return change + 1;
    }

    public static void main(String[] args) {
        int[] arr = {5, 7, 1, 1, 2, 3, 22};
        System.out.println(find(arr));
    }
}
