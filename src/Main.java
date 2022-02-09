import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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

    static double x = 60 / Math.PI * 2;
    static double y = 0;
    static double z = 0;

    public static void paint(Rectangle[] rs, int width, int height) {
        if (bi.getWidth() != width || bi.getHeight() != height) {
            bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            g = bi.createGraphics();
            g.translate(width / 2, height / 2);
        }

        g.clearRect(-width / 2, -height / 2, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (x > Math.PI * 2) x = x % (Math.PI * 2);
        if (y > Math.PI * 2) y = y % (Math.PI * 2);
        if (z > Math.PI * 2) z = z % (Math.PI * 2);

        ArrayList<Vertex[]> drawn = new ArrayList<>();
        for (Rectangle r : rs) r.rotatexyz(x, y, z).draw(g, Color.DARK_GRAY, Color.GREEN, drawn, false);

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

        Vertex a = new Vertex(-150, -150, -150);
        Vertex b = new Vertex(150, -150, -150);
        Vertex c = new Vertex(150, 150, -150);
        Vertex d = new Vertex(-150, 150, -150);

        Vertex height = new Vertex(0, 0, 300);

        Vertex as = a.add(height);
        Vertex bs = b.add(height);
        Vertex cs = c.add(height);
        Vertex ds = d.add(height);

        Texture front = null;
        Texture back = null;
        Texture top = null;
        Texture down = null;
        Texture left = null;
        Texture right = null;
        try {
            front = new Texture(300, 300, "res/front.png");
            back = new Texture(300, 300, "res/back.png");
            top = new Texture(300, 300, "res/top.png");
            down = new Texture(300, 300, "res/down.png");
            left = new Texture(300, 300, "res/left.png");
            right = new Texture(300, 300, "res/right.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Rectangle[] rs = {
                new Rectangle(c, d, a, b, front),
                new Rectangle(ds, cs, bs, as, back),

                new Rectangle(a, as, bs, b, top),
                new Rectangle(d, c, cs, ds, down),

                new Rectangle(d, ds, as, a, left),
                new Rectangle(cs, c, b, bs, right)
        };

        p = new JPanel();

        f.add(p);

        f.setSize(800, 600);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);

        task = executor.scheduleAtFixedRate(() -> paint(rs, f.getWidth(), f.getHeight()), 0, 8, TimeUnit.MILLISECONDS);
    }
}
