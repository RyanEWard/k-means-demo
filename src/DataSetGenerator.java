import java.util.ArrayList;
import java.util.List;
import java.util.Random;

final class DataSetGenerator {

    private static Random rnd = new Random();

    public static List<DataPoint> generateUniformRandomSet(int count, double xUpperBound, double yUpperBound) {
        List<DataPoint> dataSet = new ArrayList<DataPoint>();
        for (int i = 0; i < count; i++) {
            dataSet.add(new DataPoint(rnd.nextDouble() * xUpperBound, rnd.nextDouble() * yUpperBound));
        }
        return dataSet;
    }
}
