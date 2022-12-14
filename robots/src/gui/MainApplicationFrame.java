import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;
import log.Logger;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();

    private final PaneSaver saver = new PaneSaver(new FilePaneSaver(), new FramesLoader());
    
    MainApplicationFrame() {
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        MenuBarGenerator menuGenerator = new MenuBarGenerator(this);
        setJMenuBar(menuGenerator.generateMenuBar(e -> saver.WriteToFile(desktopPane)));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        saver.loadSettings(desktopPane);
    }
    
    private LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    
    private void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
