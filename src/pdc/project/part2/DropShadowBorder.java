
package pdc.project.part2;

import javax.swing.border.Border;
import java.awt.*;

public class DropShadowBorder implements Border {
    private int shadowSize = 5;

@Override
public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    Color shadowColorA = new Color(50, 50, 50, 255);
    Color shadowColorB = new Color(50, 50, 50, 0);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setPaint(new GradientPaint(new Point(0, 0), shadowColorA, new Point(shadowSize, shadowSize), shadowColorB));
    g2.fillRoundRect(x + shadowSize, y + shadowSize, width - shadowSize, height - shadowSize, shadowSize, shadowSize);
}


@Override
public Insets getBorderInsets(Component c) {
    return new Insets(shadowSize, shadowSize, shadowSize * 2, shadowSize * 2);
}

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
