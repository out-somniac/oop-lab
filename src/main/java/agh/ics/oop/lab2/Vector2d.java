package agh.ics.oop.lab2;

public class Vector2d {
    private final int x, y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", this.x, this.y);
    }
    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }
    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }
    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }
    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }
    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y+other.y);
    }
    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    // Poniższa metoda equals używa ciekawego feature Java 14, a mianowicie pattern variable
    // Żeby poprawić czytelność kodu i uniknąć castowania linijka w warunku od razu castuje other na typ Vector2d
    // I w scope tego warunku daje nam dostęp do zmiennej that
    @Override
    public boolean equals(Object other) {
        if(other instanceof Vector2d that) {
            return this.x == that.x && this.y == that.y;
        }
        else {
            return false;
        }
    }

    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }
}
