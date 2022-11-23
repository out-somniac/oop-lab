package agh.ics.oop.maps;

import java.util.Comparator;
import java.util.TreeSet;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.interfaces.IMoveObserver;

public class MapBoundary implements IMoveObserver {
    private final TreeSet<Vector2d> xOrder = new TreeSet<>(Comparator.comparingInt(p -> p.x));
    private final TreeSet<Vector2d> yOrder = new TreeSet<>(Comparator.comparingInt(p -> p.y));

    @Override
    public void updatePosition(Vector2d new_position, Vector2d old_position) {
        removePosition(old_position);
        addPosition(new_position);
    }

    public void addPosition(Vector2d position) {
        this.xOrder.add(position);
        this.yOrder.add(position);
    }

    private void removePosition(Vector2d position) {
        this.xOrder.remove(position);
        this.yOrder.remove(position);
    }

    public Vector2d getUpperRight() {
        Vector2d upper = this.yOrder.last();
        Vector2d right = this.xOrder.last();
        return upper.upperRight(right);
    }

    public Vector2d getLowerLeft() {
        Vector2d lower = this.yOrder.first();
        Vector2d left = this.xOrder.first();
        return lower.lowerLeft(left);
    }
}
