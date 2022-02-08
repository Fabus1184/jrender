import java.util.Arrays;

public class Vertex {
    double x, y, z;

    public Vertex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString(){
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public Vertex cross(Vertex v){
        return new Vertex(
                this.y * v.z - this.z * v.y,
                this.z * v.x - this.x * v.z,
                this.x * v.y - this.y * v.x
        );
    }

    public double magnitude(){
        return Math.sqrt(Arrays.stream(new double[]{x, y, z}).map(i -> i * i).sum());
    }

    public double angle(Vertex v){
        return Math.acos(this.dot(v) / (this.magnitude() * v.magnitude()));
    }

    public double dot(Vertex v){
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    public Vertex add(Vertex v) {
        return new Vertex(
                this.x + v.x,
                this.y + v.y,
                this.z + v.z
        );
    }

    public Vertex sub(Vertex v) {
        return new Vertex(
                this.x - v.x,
                this.y - v.y,
                this.z - v.z
        );
    }

    public Vertex div(double d){
        return new Vertex(
          this.x / d,
          this.y / d,
          this.z / d
        );
    }

    public Vertex mmul(Matrix m) {
        if (Math.sqrt(m.values.length) != 3) return null;
        return new Vertex(
                this.x * m.values[0] + this.y * m.values[1] + this.z * m.values[2],
                this.x * m.values[3] + this.y * m.values[4] + this.z * m.values[5],
                this.x * m.values[6] + this.y * m.values[7] + this.z * m.values[8]
        );
    }

    public Vertex rotatex(double theta) {
        theta = theta % (Math.PI * 2);
        return this.mmul(new Matrix(new double[]{
                1, 0, 0,
                0, Math.cos(theta), -Math.sin(theta),
                0, Math.sin(theta), Math.cos(theta)
        }));
    }

    public Vertex rotatey(double theta) {
        theta = theta % (Math.PI * 2);
        return this.mmul(new Matrix(new double[]{
                Math.cos(theta), 0, Math.sin(theta),
                0, 1, 0,
                -Math.sin(theta), 0, Math.cos(theta)
        }));
    }

    public Vertex rotatez(double theta) {
        theta = theta % (Math.PI * 2);
        return this.mmul(new Matrix(new double[]{
                Math.cos(theta), -Math.sin(theta), 0,
                Math.sin(theta), Math.cos(theta), 0,
                0, 0, 1
        }));
    }
}


