package ui;

public class Point {
    private double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getXstr() {
        return String.valueOf(this.x);
    }

    public void setXstr(String x) {
        this.x = Double.parseDouble(x);
    }

    public String getYstr() {
        return String.valueOf(this.y);
    }

    public void setYstr(String y) {
        this.y = Double.parseDouble(y);
    }

}
