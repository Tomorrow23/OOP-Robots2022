package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.*;

import gui.FrameSaver.PaneSaver;
import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();

    private final gui.FrameSaver.PaneSaver saver = new PaneSaver();
    
    MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        
        gui.LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        gui.GameWindow gameWindow = new gui.GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        gui.MenuBarGenerator menuGenerator = new gui.MenuBarGenerator(this);
        setJMenuBar(menuGenerator.generateMenuBar(e -> saver.WriteToFile(desktopPane)));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        saver.LoadSettings(desktopPane);
    }
    
    private gui.LogWindow createLogWindow()
    {
        gui.LogWindow logWindow = new gui.LogWindow(Logger.getDefaultLogSource());
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
