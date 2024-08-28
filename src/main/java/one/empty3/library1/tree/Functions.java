package one.empty3.library1.tree;

public class Functions {
    public static double search(String call, double... vec) {
        if (call.equals("sum")) {
            return sum(vec);
        }
        return 0;
    }

    public static double sum(double... args) {
        double sum = 0;
        for (int i = 0; i < args.length; i++) {
            sum += args[i];
        }

        return sum;
    }
}
