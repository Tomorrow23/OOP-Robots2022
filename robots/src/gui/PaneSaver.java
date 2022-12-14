import javax.swing.*;

public class PaneSaver {

    FramesLoader framesLoader;
    FilePaneSaver filePaneSaver;

    public PaneSaver(FilePaneSaver filePaneSaver,FramesLoader framesLoader){
        this.filePaneSaver=filePaneSaver;
        this.framesLoader=framesLoader;
    }

    public void WriteToFile(JDesktopPane desktopPane) {
        var framesInfo=framesLoader.ExtractFrameInfo(desktopPane);
        filePaneSaver.SaveFramesInfo(framesInfo);
    }

    public void loadSettings(JDesktopPane desktopPane) {
        var framesInfo=filePaneSaver.LoadFramesInfo();
        framesLoader.SaveFramesInfo(desktopPane, framesInfo);
    }
}