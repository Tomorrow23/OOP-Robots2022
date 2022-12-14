import lombok.experimental.UtilityClass;
import javax.swing.*;

//расширяет
@UtilityClass
public class JInternalFrameExtensions {
    //достает
    public static FrameInfo toFrameInfo(JInternalFrame frame) {
        return new FrameVisitor().visit(frame);
    }

    //вставляет
    public static void setFrameInfo(JInternalFrame frame, FrameInfo frameInfo) {
        new FrameVisitor().visit(frame, frameInfo);
    }
}
