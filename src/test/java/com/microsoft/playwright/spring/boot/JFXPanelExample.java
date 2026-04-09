package com.microsoft.playwright.spring.boot;


import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import java.awt.BorderLayout;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static javafx.application.Platform.runLater;

/**
 * 这个示例演示了如何将 JavaFX 的 BrowserView 嵌入到
 * 显示在 Swing/AWT Frame 内的 JFXPanel 中。
 */
public final class JFXPanelExample {

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(JFXPanelExample::initAndShowGUI);
    }

    private static void initAndShowGUI() {
        var frame = new JFrame("JFXPanel");

        // 将 JFXPanel 嵌入到 Swing Frame 中。
        var fxPanel = new JFXPanel();
        frame.add(fxPanel, BorderLayout.CENTER);
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 在 JavaFX UI 线程中初始化 JFXPanel。
        runLater(() -> initFX(fxPanel));
    }

    private static void initFX(JFXPanel fxPanel) {
        var engine = Engine.newInstance(HARDWARE_ACCELERATED);

        var browser = engine.newBrowser();
        browser.navigation().loadUrl("https://html5test.teamdev.com");
        // 创建 JavaFX BrowserView 并将其插入到 JFXPanel 中。
        var view = BrowserView.newInstance(browser);
        var pane = new BorderPane(view);
        fxPanel.setScene(new Scene(pane, 600, 600));
    }
}
