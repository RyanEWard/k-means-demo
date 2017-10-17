package Model.Fortunes;

import Model.DataPoint;

public class PointEvent extends FortunesEvent {

    private DataPoint p;

    public PointEvent(DataPoint p) {
        super.type = EventType.POINT;
        this.p = p;
    }

    @Override
    public int compareTo(FortunesEvent event) {
        return Double.compare(this.getFortunesPriority(), event.getFortunesPriority());
    }

    @Override
    public double getFortunesPriority() {
        return p.y;
    }
}
