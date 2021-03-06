import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;

public class Triangle {
    Vertex v1, v2, v3;
    Vertex[] paintList;

    public Triangle(Vertex v1, Vertex v2, Vertex v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;

        this.paintList = new Vertex[]{
                v1, v2, v3, v1
        };
    }

    public Triangle rotatexyz(double x, double y, double z) {
        return new Triangle(
                this.v1.rotatexyz(x, y, z),
                this.v2.rotatexyz(x, y, z),
                this.v3.rotatexyz(x, y, z)
        );
    }

    public void draw(Graphics2D g, Color c, Color d, ArrayList<Vertex[]> drawn, boolean wireframe) {
        Path2D p = new Path2D.Double();
        p.moveTo(this.paintList[0].x, this.paintList[0].y);
        Arrays.stream(this.paintList).toList().forEach(v -> p.lineTo(v.x, v.y));
        p.closePath();
        g.setColor(c);
        g.draw(p);

    }
}
