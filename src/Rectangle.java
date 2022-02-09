import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Rectangle implements Paintable {
    Vertex a, b, c, d;
    Vertex[] paintList;
    Texture texture;

    public Rectangle(Vertex a, Vertex b, Vertex c, Vertex d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.paintList = new Vertex[]{a, b, c, d, a};
    }

    public Rectangle(Vertex a, Vertex b, Vertex c, Vertex d, Texture t) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.texture = t;

        this.paintList = new Vertex[]{a, b, c, d, a};
    }

    public void setTexture(Texture t) {
        this.texture = t;
    }

    public Vertex normal() {
        Vertex n = this.d.sub(this.a).cross(this.b.sub(this.a));
        return n.div(n.magnitude());
    }

    public Rectangle rotatexyz(double x, double y, double z) {
        return new Rectangle(
                this.a.rotatexyz(x, y, z),
                this.b.rotatexyz(x, y, z),
                this.c.rotatexyz(x, y, z),
                this.d.rotatexyz(x, y, z),
                this.texture
        );
    }

    public Vertex center() {
        return new Vertex(this.a.x + this.c.x / 2, this.a.y + this.c.y, this.a.z + this.c.z);
    }

    @Override
    public void draw(Graphics2D g, Color c, Color d, ArrayList<Vertex[]> drawn, boolean wireframe) {
        if (!wireframe && this.normal().angle(new Vertex(0, 0, -1)) > Math.PI / 2) return;

        if (!wireframe) {
            if (this.texture == null) {
                g.setColor(c);
                g.fillPolygon(
                        new int[]{
                                (int) Math.round(this.a.x), (int) Math.round(this.b.x), (int) Math.round(this.c.x), (int) Math.round(this.d.x)
                        },
                        new int[]{
                                (int) Math.round(this.a.y), (int) Math.round(this.b.y), (int) Math.round(this.c.y), (int) Math.round(this.d.y)
                        },
                        4
                );
            } else {
                int width = this.texture.bi.getWidth();
                int height = this.texture.bi.getHeight();
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        g.setColor(new Color(this.texture.bi.getRGB(x, y)));
                        Vertex v = this.d.add(this.c.sub(this.d).div(width).mul(x)).add(this.a.sub(this.d).div(height).mul(y));
                        g.drawRect(
                                (int) Math.round(v.x),
                                (int) Math.round(v.y),
                                1, 1);
                    }
                }
            }
        } else {
            g.setColor(d);
            for (int i = 0; i < paintList.length - 1; i++) {
                if (drawn.contains(new Vertex[]{this.paintList[i], this.paintList[i + 1]})) return;
                Path2D p = new Path2D.Double();
                p.moveTo(this.paintList[i].x, this.paintList[i].y);
                p.lineTo(this.paintList[i + 1].x, this.paintList[i + 1].y);
                p.closePath();
                p.setWindingRule(Path2D.WIND_EVEN_ODD);
                g.draw(p);
                drawn.add(new Vertex[]{this.paintList[i], this.paintList[i + 1]});
            }
        }
    }
}
