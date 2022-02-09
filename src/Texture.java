import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {
    public BufferedImage bi;
    public AffineTransform t = new AffineTransform();

    public Texture(int width, int height, String path) throws IOException {
        bi = ImageIO.read(new File(path));
        t.scale((float) width / bi.getWidth(), (float) height / bi.getHeight());
    }

}
