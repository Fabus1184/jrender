import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.*;

public class Main {
    static double x = 0;
    static double y = Math.PI * 2 / 3;
    static double z = Math.PI * 4 / 3;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private volatile ScheduledFuture<?> task;

    public static void paint(Graphics2D g, Rectangle[] rs, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(width / 2, height / 2);
        if (x > Math.PI * 2) x = x % (Math.PI * 2);
        if (y > Math.PI * 2) y = y % (Math.PI * 2);
        if (z > Math.PI * 2) z = z % (Math.PI * 2);

        for (Rectangle r : rs) r.rotatex(x).rotatey(y).rotatez(z).draw(g2d, Color.BLUE);

        x += 0.02;
        y += 0.02;
        z += 0.02;

        g.drawImage(bi, 0, 0, null);
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

        JPanel p = new JPanel();
        f.add(p);
        f.setSize(800, 600);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);

        new Thread(() -> {
            while (true) {
                paint((Graphics2D) p.getGraphics(), rs, f.getWidth(), f.getHeight());
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
