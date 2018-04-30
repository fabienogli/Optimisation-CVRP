package Util;

import java.util.Objects;

public class Coordonnee {

    private int x;
    private int y;

    public Coordonnee(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordonnee() {
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordonnee that = (Coordonnee) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Util.Coordonnee{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static double distance(Coordonnee a, Coordonnee b) {
        return Math.sqrt(Math.pow(b.getY() - a.getY(), 2) + Math.pow(b.getX() - a.getY(), 2));
    }
}
