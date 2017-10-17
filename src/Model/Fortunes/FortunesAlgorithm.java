package Model.Fortunes;

import Model.DataPoint;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.PriorityQueue;

public class FortunesAlgorithm {

    // Following nomenclature in "A Sweepline Algorithm for Voronoi Diagrams", Steven Fortune, 1987

    private PriorityQueue<FortunesEvent> eventQueue;
    private boolean solved = false;

    public FortunesAlgorithm(List<DataPoint> centroids) {
        this.eventQueue = initializeQueue(centroids);
    }

    private PriorityQueue<FortunesEvent> initializeQueue(List<DataPoint> points) {
        PriorityQueue<FortunesEvent> events = new PriorityQueue<>();
        for (DataPoint p : points) {
            events.add(new PointEvent(p));
        }
        return events;
    }

    public void setPoints(List<DataPoint> centroids) {
        solved = false;
        this.eventQueue = initializeQueue(centroids);
    }

    public void getResult() {
        if (!solved) {
            run();
        }
        throw new NotImplementedException();
    }

    private void run() {
        if (solved) {
            return;
        }

        while (!eventQueue.isEmpty()) {
            FortunesEvent fe = eventQueue.poll();
            switch (fe.type) {
                case POINT:
                    processPointEvent(fe);
                    break;
                case CIRCLE:
                    processCircleEvent(fe);
                    break;
            }
        }

        solved = true;
    }

    private void processPointEvent(FortunesEvent fe) {
        throw new NotImplementedException();
    }

    private void processCircleEvent(FortunesEvent fe) {
        throw new NotImplementedException();
    }
}
