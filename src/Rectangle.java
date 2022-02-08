import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Rectangle implements Paintable {
    Vertex a, b, c, d;
    Vertex[] paintList;

    public Rectangle(Vertex a, Vertex b, Vertex c, Vertex d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;

        this.paintList = new Vertex[]{a, b, c, d, a};
    }

    public Vertex normal() {
        Vertex n = this.d.sub(this.a).cross(this.b.sub(this.a));
        return n.div(n.magnitude());
    }

    public Rectangle rotatex(double theta) {
        return new Rectangle(
                this.a.rotatex(theta),
                this.b.rotatex(theta),
                this.c.rotatex(theta),
                this.d.rotatex(theta)
        );
    }

    public Rectangle rotatey(double theta) {
        return new Rectangle(
                this.a.rotatey(theta),
                this.b.rotatey(theta),
                this.c.rotatey(theta),
                this.d.rotatey(theta)
        );
    }

    public Rectangle rotatez(double theta) {
        return new Rectangle(
                this.a.rotatez(theta),
                this.b.rotatez(theta),
                this.c.rotatez(theta),
                this.d.rotatez(theta)
        );
    }

    @Override
    public void draw(Graphics2D g, Color c, Color d, ArrayList<Vertex[]> drawn) {
        if (this.normal().angle(new Vertex(0, 0, -1)) > Math.PI / 2 || c == Color.MAGENTA) return;

        g.setColor(c);
        g.fillPolygon(
                new int[]{
                        (int) this.a.x, (int) this.b.x, (int) this.c.x, (int) this.d.x
                },
                new int[]{
                        (int) this.a.y, (int) this.b.y, (int) this.c.y, (int) this.d.y
                },
                4
        );

        g.setColor(d);

        for (int i = 0; i < paintList.length - 1; i++) {
            if (drawn.contains(new Vertex[]{this.paintList[i], this.paintList[i + 1]})) return;
            Path2D p = new Path2D.Double();
            p.moveTo(this.paintList[i].x, this.paintList[i].y);
            p.lineTo(this.paintList[i + 1].x, this.paintList[i + 1].y);
            p.closePath();
            g.draw(p);
            drawn.add(new Vertex[]{this.paintList[i], this.paintList[i + 1]});
        }
    }
}
