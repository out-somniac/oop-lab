package agh.ics.oop.maps;

import java.util.Comparator;
import java.util.TreeSet;

import agh.ics.oop.core.Vector2d;
import agh.ics.oop.interfaces.IMoveObserver;

public class MapBoundary implements IMoveObserver {
    private final TreeSet<Vector2d> xOrder = new TreeSet<>((v1, v2) -> {
        if (v1.x == v2.x) {
            return Integer.compare(v1.y, v2.y);
        } else {
            return Integer.compare(v1.x, v2.x);
        }
    });
    private final TreeSet<Vector2d> yOrder = new TreeSet<>((v1, v2) -> {
        if (v1.y == v2.y) {
            return Integer.compare(v1.x, v2.x);
        } else {
            return Integer.compare(v1.y, v2.y);
        }
    });

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
