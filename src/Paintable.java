import java.awt.*;
import java.util.ArrayList;

public interface Paintable {
    void draw(Graphics2D g, Color c, Color d, ArrayList<Vertex[]> alreadyDrawn, boolean wireframe);
}
