import log.Logger;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuBarGenerator {

    private JFrame frame;

    public MenuBarGenerator(JFrame frame){

        this.frame = frame;
    }

    public JMenuBar generateMenuBar(ActionListener exitListener) {
        JMenuBar menuBar = new JMenuBar();
        JMenu exitMenu = generateExitMenu(exitListener);
        JMenu lookAndFeelMenu = generateLookAndFeelMenu(frame);
        JMenu testMenu = generateTestMenu();

        menuBar.add(exitMenu);
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        return menuBar;
    }

    private void exitManager() {
        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");
        int result = JOptionPane.showConfirmDialog(
                null,
                "Вы уверенны что хотите выйти?",
                "Окно подтверждения",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private JMenu generateTestMenu() {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");
        JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> Logger.debug("Новая строка"));

        testMenu.add(addLogMessageItem);
        return testMenu;
    }

    private JMenu generateLookAndFeelMenu(JFrame frame) {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName(), frame);
            frame.invalidate();
        });

        JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
        crossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName(), frame);
            frame.invalidate();
        });

        lookAndFeelMenu.add(systemLookAndFeel);
        lookAndFeelMenu.add(crossplatformLookAndFeel);
        return lookAndFeelMenu;
    }

    private JMenu generateExitMenu(ActionListener exitListener) {
        var exitMenu = new JMenu("Файл");
        var exitItem = new JMenuItem("Выход") {
        };
        exitItem.addActionListener(e -> exitManager());
        exitItem.addActionListener(exitListener);
        exitMenu.add(exitItem);
        return exitMenu;
    }
    private void setLookAndFeel(String className, JFrame frame) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
    }
}
