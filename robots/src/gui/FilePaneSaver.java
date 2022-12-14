import java.io.*;
import java.util.ArrayList;

//сохраняет данные в файлы
public class FilePaneSaver {
    private static String pathToSaves = System.getProperty("user.home") + "/RobotSaves/";

    public FilePaneSaver(){
        var saves=new File(pathToSaves);
        if(!saves.exists())
            saves.mkdir();
    }

    public void SaveFramesInfo(ArrayList<FrameInfo> frameInfo){
        for (var frame : frameInfo) {
            var file = new File(pathToSaves + frame.title() + ".txt");
            try {
                if (!file.exists())
                    file.createNewFile();

                var outputStream = new FileOutputStream(file);
                var dataOutputStream = new DataOutputStream(new BufferedOutputStream(outputStream));
                dataOutputStream.writeUTF(frame.title());
                dataOutputStream.writeInt(frame.x());
                dataOutputStream.writeInt(frame.y());
                dataOutputStream.writeInt(frame.width());
                dataOutputStream.writeInt(frame.height());
                dataOutputStream.writeBoolean(frame.isMaximum());
                dataOutputStream.writeBoolean(frame.isIcon());
                dataOutputStream.close();
                outputStream.close();
            } catch (Exception e) {


            }
        }
    }

    public ArrayList<FrameInfo> LoadFramesInfo() {
        var frameStates = new ArrayList<FrameInfo>();
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
                frameStates.add(new FrameInfo(title, x, y, width, height, isMaximum, isIcon));
                dataInputStream.close();
                inputStream.close();
            } catch (Exception e) {
            }

        }
        return frameStates;
    }
}

