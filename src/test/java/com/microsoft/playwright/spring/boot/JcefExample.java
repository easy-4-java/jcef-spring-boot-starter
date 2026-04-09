package com.microsoft.playwright.spring.boot;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.impl.progress.ConsoleProgressHandler;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 这是一个带有地址栏和 JCEF 的简单应用程序。
 */
public final class JcefExample {

    private static  String url = "https://teamdev.com";
    private static final boolean OFFSCREEN = false;
    private static final boolean TRANSPARENT = false;

    public static void main(String[] args) throws Exception {

        // 创建一个新的 CefAppBuilder 实例
        CefAppBuilder builder = new CefAppBuilder();

        // 配置 builder 实例‚
        builder.setInstallDir(new File("jcef-bundle"));
        builder.setProgressHandler(new ConsoleProgressHandler());
        builder.addJcefArgs("--disable-gpu");
        builder.addJcefArgs("--disable-web-security");
        builder.addJcefArgs("--disable-site-isolation-trials");
        builder.addJcefArgs("--add-exports java.base/java.lang=ALL-UNNAMED");
        builder.addJcefArgs("--add-exports java.desktop/sun.awt=ALL-UNNAMED");
        builder.addJcefArgs("--add-exports java.desktop/sun.java2d=ALL-UNNAMED");
        if (System.getProperty("os.name").contains("Mac")) {
            builder.addJcefArgs("--add-opens java.desktop/sun.awt=ALL-UNNAMED");
            builder.addJcefArgs("--add-opens java.desktop/sun.lwawt=ALL-UNNAMED");
            builder.addJcefArgs("--add-opens java.desktop/sun.lwawt.macosx=ALL-UNNAMED");
        }

        CefSettings settings = new CefSettings();
        settings.cache_path = System.getProperty("user.dir") + "/cache" + "/context";
        settings.root_cache_path = System.getProperty("user.dir") + "/cache";
        settings.windowless_rendering_enabled = OFFSCREEN;


        builder.getCefSettings().cache_path = settings.cache_path;
        builder.getCefSettings().root_cache_path = settings.root_cache_path;
        builder.getCefSettings().windowless_rendering_enabled = settings.windowless_rendering_enabled;

        // 设置应用处理器
        builder.setAppHandler(new MavenCefAppHandlerAdapter(){});
        CefApp cefApp =  builder.build();

        url = "https://zhpj.bjopenschool.com/growthPrint-new/index.html?schoolCode=2133001269&gradeCode=11&classCode=202401110323&templateId=1873614199084802048&pageId=1873614199617478657&platform=bmp&userId=2814&stuId=1869260292152651776&token=eyJhbGciOiJSUzI1NiJ9.eyJ4eGRtIjoiMjEzMzAwMTI2OSIsInN1YiI6IjEzODY3NDMyMTMwIiwicm9sZSI6NiwiZXhwaXJlIjoxNzY4NDQ4NDI4NDc4LCJ1c2VyY29kZSI6IjI4MTQiLCJ1c2VyTmFtZSI6IueOi-S4veiOjiIsInR5cGUiOiIyIiwiZXhwIjoxNzY4NDQ4NDI4LCJ1c2VySWQiOiIyODE0In0.A_NAZHbbUtQX3yyreQdyCWcU5z0zMo-XsrvNtYE0g7BznCPbgPqcNFqepoHzlMIAbpWtSWpXm90NhiP79EyHGV1MPASq8PUqog6BZdmBQ9MC1rgoHCZruqITtQLuCGWsNkTd5WjVSzf0vBd-y1bHIdouOeOFlKcP91JWx9iaYro";

        // 创建客户端实例
        CefClient client = cefApp.createClient();
        // 创建浏览器实例
        client.addMessageRouter(CefMessageRouter.create());
        CefBrowser browser = client.createBrowser(url, OFFSCREEN, TRANSPARENT);

        JTextField address = new JTextField(url);
        address.addActionListener(e -> browser.loadURL(address.getText()));

        JFrame frame = new JFrame("JCEF");
        frame.add(address, BorderLayout.NORTH);
        frame.add(browser.getUIComponent(), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(1280, 900);
        frame.setVisible(true);
    }
}