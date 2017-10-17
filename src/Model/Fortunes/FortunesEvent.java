package Model.Fortunes;

public abstract class FortunesEvent implements Comparable<FortunesEvent> {
    public EventType type;

    public abstract double getFortunesPriority();

    public enum EventType {POINT, CIRCLE}
}
