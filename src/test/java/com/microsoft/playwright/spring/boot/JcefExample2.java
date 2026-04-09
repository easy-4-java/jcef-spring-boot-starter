package com.microsoft.playwright.spring.boot;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class JcefExample2 {

    private static final boolean OFFSCREEN = false;
    private static final boolean TRANSPARENT = false;

    public static void main(String[] args) throws AWTException {


        CefSettings settings = new CefSettings();
        settings.pack_loading_disabled = true;
        settings.cache_path = System.getProperty("user.dir") + "/cache" + "/context";
        settings.root_cache_path = System.getProperty("user.dir") + "/cache";
        settings.windowless_rendering_enabled = OFFSCREEN;

        // 创建客户端和浏览器
        CefClient client = CefApp.getInstance(settings).createClient();
        CefBrowser browser = client.createBrowser("https://www.baidu.com", false, false);

        // Obtain the component that you want to capture in a screenshot.
        java.awt.Component component = browser.getUIComponent();

        // Determine what area of the entire screen is covered by the component.
        java.awt.Point p = new java.awt.Point(0, 0);
        javax.swing.SwingUtilities.convertPointToScreen(p, component);
        java.awt.Rectangle region = component.getBounds();
        region.x = p.x;
        region.y = p.y;

        // Store the selected area from the screen in a image buffer.
        try {
            java.awt.image.BufferedImage image = new Robot().createScreenCapture( region );
            ImageIO.write(image, "PNG", new File("bitmap2.png"));
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }
       /* // 创建 Swing 窗口并添加浏览器组件
        JFrame frame = new JFrame("Swing-JCEF-Spring");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(browser.getUIComponent(), BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setVisible(true);*/
    }
}
