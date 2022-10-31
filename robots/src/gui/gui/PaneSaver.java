package gui.FrameSaver;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.HashMap;

public class PaneSaver {
    private static String pathToSaves = System.getProperty("user.home") + "/RobotSaves/";

    public PaneSaver(){
        var saves=new File(pathToSaves);
        if(!saves.exists())
            saves.mkdir();
    }

    public void WriteToFile(JDesktopPane desktopPane) {
        for (var frame : desktopPane.getAllFrames()) {
            var file = new File(pathToSaves + frame.getTitle() + ".txt");
            try {
                if (!file.exists())
                    file.createNewFile();

                var outputStream = new FileOutputStream(file);
                var dataOutputStream = new DataOutputStream(new BufferedOutputStream(outputStream));
                dataOutputStream.writeUTF(frame.getTitle());
                dataOutputStream.writeInt(frame.getX());
                dataOutputStream.writeInt(frame.getY());
                dataOutputStream.writeInt(frame.getWidth());
                dataOutputStream.writeInt(frame.getHeight());
                dataOutputStream.writeBoolean(frame.isMaximum());
                dataOutputStream.writeBoolean(frame.isIcon());
                dataOutputStream.close();
                outputStream.close();
            } catch (Exception e) {

            }
        }
    }

    public HashMap<String, gui.FrameSaver.FrameInformation> ReadFrameStates() {
        var frameStates = new HashMap<String, gui.FrameSaver.FrameInformation>();
        var file = new File(pathToSaves);
        if (!file.exists() && file.listFiles()==null && file.listFiles().length==0){
            return frameStates;
        }
        for (var frameFile : file.listFiles()) {
            try {
                var inputStream = new FileInputStream(frameFile);
                var dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
                var title = dataInputStream.readUTF();
                var x = dataInputStream.readInt();
                var y = dataInputStream.readInt();
                var width = dataInputStream.readInt();
                var height = dataInputStream.readInt();
                var isMaximum = dataInputStream.readBoolean();
                var isIcon = dataInputStream.readBoolean();
                frameStates.put(title, new gui.FrameSaver.FrameInformation(x, y, width, height, isMaximum, isIcon));
                dataInputStream.close();
                inputStream.close();
            } catch (Exception e) {
            }

        }
        return frameStates;
    }

    public void LoadSettings(JDesktopPane desktopPane) {
        HashMap<String, gui.FrameSaver.FrameInformation> frameStates = ReadFrameStates();
        for (var frame : desktopPane.getAllFrames()) {
            if (!frameStates.containsKey(frame.getTitle()))
                continue;

            var newFrameState = frameStates.get(frame.getTitle());
            frame.setLocation(new Point(newFrameState.x, newFrameState.y));
            frame.setSize(newFrameState.width, newFrameState.height);
            try {
                frame.setIcon(newFrameState.isIcon);
                frame.setMaximum(newFrameState.isMaximum);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }
    }
}
