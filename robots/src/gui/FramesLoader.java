import lombok.experimental.ExtensionMethod;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

@ExtensionMethod(JInternalFrameExtensions.class)
public class FramesLoader {

    //достает

    public ArrayList<FrameInfo> ExtractFrameInfo(JDesktopPane desktopPane) {
        var frames = new ArrayList<FrameInfo>();
        for (var frame : desktopPane.getAllFrames()) {
            frames.add(frame.toFrameInfo());
        }
        return frames;
    }

    //устанавливет значение во фреймы
    public void SaveFramesInfo(JDesktopPane desktopPane, ArrayList<FrameInfo> framesInfo) {
        HashMap<String, FrameInfo> frameStates = new HashMap<String, FrameInfo>();

        for (var frame : framesInfo) {
            frameStates.put(frame.title(), frame);
        }

        for (var frame : desktopPane.getAllFrames()) {
            if (!frameStates.containsKey(frame.getTitle()))
                continue;

            frame.setFrameInfo(frameStates.get(frame.getTitle()));
        }
    }
}