package com.microsoft.playwright.spring.boot;

import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.view.swing.BrowserView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static javax.swing.SwingUtilities.invokeLater;

public final class App {

    public static void main(String[] args) {
        // Initialize Chromium.
        var engine = Engine.newInstance(HARDWARE_ACCELERATED);

        // Create a Browser instance.
        var browser = engine.newBrowser();

        invokeLater(() -> {
            var frame = new JFrame("JxBrowser Swing");
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // Shutdown Chromium and release allocated resources.
                    engine.close();
                }
            });
            // Create and embed Swing BrowserView component to display web content.
            frame.add(BrowserView.newInstance(browser));
            frame.setSize(1280, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Load the required web page.
            browser.navigation().loadUrl("https://html5test.teamdev.com/");
        });
    }
}
