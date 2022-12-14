import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

public class FrameVisitor {
    //посещает и преобразует в информацию
    public FrameInfo visit(JInternalFrame frame) {
        return new FrameInfo(
                frame.getTitle(),
                frame.getX(),
                frame.getY(),
                frame.getWidth(),
                frame.getHeight(),
                frame.isMaximum(),
                frame.isIcon());
    }

    //посещает и записывает информацию
    public void visit(JInternalFrame frame, FrameInfo frameInfo) {
        frame.setLocation(new Point(frameInfo.x(), frameInfo.y()));
        frame.setSize(frameInfo.width(), frameInfo.height());
        try {
            frame.setIcon(frameInfo.isIcon());
            frame.setMaximum(frameInfo.isMaximum());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
}
