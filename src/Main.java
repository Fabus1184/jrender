import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {
    static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    static volatile ScheduledFuture<?> task;

    static BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    static Graphics2D g;
    static JPanel p;

    static double x = 0;
    static double y = Math.PI * 2 / 3;
    static double z = Math.PI * 4 / 3;

    public static void paint(Rectangle[] rs, int width, int height) {
        if (bi.getWidth() != width || bi.getHeight() != height) {
            bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            g = bi.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D) p.getGraphics()).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.translate(width / 2, height / 2);
        }

        g.clearRect(-width / 2, -height / 2, width, height);

        if (x > Math.PI * 2) x = x % (Math.PI * 2);
        if (y > Math.PI * 2) y = y % (Math.PI * 2);
        if (z > Math.PI * 2) z = z % (Math.PI * 2);

        ArrayList<Vertex[]> drawn = new ArrayList<>();
        for (Rectangle r : rs) r.rotatex(x).rotatey(y).rotatez(z).draw(g, Color.BLUE, Color.YELLOW, drawn);

        x += 0.015;
        y += 0.015;
        z += 0.015;

        p.getGraphics().drawImage(bi, 0, 0, null);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();

        /*Vertex a = new Vertex(100, 100, -100);
        Vertex b = new Vertex(-100, 100, -100);
        Vertex c = new Vertex(0, -100, 0);
        Vertex d = new Vertex(0, 100, 100);

        final Triangle[] ts = {
                new Triangle(a, b, c),
                new Triangle(a, b, d),
                new Triangle(a, c, d),
                new Triangle(b, c, d)
        };*/

        Vertex a = new Vertex(-100, -100, -100);
        Vertex b = new Vertex(100, -100, -100);
        Vertex c = new Vertex(100, 100, -100);
        Vertex d = new Vertex(-100, 100, -100);

        Vertex height = new Vertex(0, 0, 200);

        Vertex as = a.add(height);
        Vertex bs = b.add(height);
        Vertex cs = c.add(height);
        Vertex ds = d.add(height);

        final Rectangle[] rs = {
                new Rectangle(a, b, c, d),
                new Rectangle(ds, cs, bs, as),

                new Rectangle(a, as, bs, b),
                new Rectangle(d, c, cs, ds),

                new Rectangle(d, ds, as, a),
                new Rectangle(c, b, bs, cs)
        };

        p = new JPanel();

        f.add(p);
        f.setSize(800, 600);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);

        task = executor.scheduleAtFixedRate(() -> paint(rs, f.getWidth(), f.getHeight()), 0, 15, TimeUnit.MILLISECONDS);
    }
}
