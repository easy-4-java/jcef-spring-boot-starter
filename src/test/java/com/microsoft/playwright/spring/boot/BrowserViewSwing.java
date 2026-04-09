package com.microsoft.playwright.spring.boot;



import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.swing.BrowserView;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static javax.swing.SwingUtilities.invokeLater;

/**
 * 带有集成 Browser 组件的最简单应用程序。
 * <p>此示例演示了：
 *
 * <ol>
 *     <li>创建一个 {@link Engine} 实例。
 *     <li>创建一个 {@link Browser} 实例。
 *     <li>通过 {@link BrowserView} 将 Browser 嵌入到 Swing 中。
 *     <li>加载 "https://html5test.teamdev.com" 网站。
 * </ol>
 */
public final class BrowserViewSwing {
    public static void main(String[] args) {
        // 创建和运行 Chromium Engine。
        var engine = Engine.newInstance(
                EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                        .licenseKey("OK6AEKNYF2Y106UFSGGFOFEPC2U1IAJH43X8LQGGMOKI9XH9VSVOLLDOSVU718S6D62LXCLJA7KGAOWHEW5QECL89EOUWTG639USILF1VG80584TDTG5C3MXOVOK0B9X7DQFMC4CEPC04ZR2S")
                        .build());

        var browser = engine.newBrowser();
        // 加载所需的网页。
        browser.navigation().loadUrl("https://html5test.teamdev.com");

        invokeLater(() -> {
            // 创建一个 Swing 组件,
            // 用于渲染在给定 Browser 实例中加载的网页内容。
            var view = BrowserView.newInstance(browser);

            // 创建并显示 Swing 应用程序 Frame。
            var frame = new JFrame("JxBrowser AWT/Swing");
            // 在该应用程序 Frame 即将关闭时关闭其 Engine。
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    engine.close();
                }
            });
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.add(view, BorderLayout.CENTER);
            frame.setSize(800, 600);
            frame.setVisible(true);



        });
    }
}
