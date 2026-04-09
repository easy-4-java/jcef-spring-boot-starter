package com.microsoft.playwright.spring.boot;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static com.teamdev.jxbrowser.print.PaperSize.ISO_A4;

import com.microsoft.playwright.spring.boot.enums.PDPageSize;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.DisplayMode;
import com.teamdev.jxbrowser.browser.callback.PrintCallback;
import com.teamdev.jxbrowser.browser.callback.PrintHtmlCallback;
import com.teamdev.jxbrowser.browser.callback.SaveAsPdfCallback;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.frame.Frame;
import com.teamdev.jxbrowser.navigation.Navigation;
import com.teamdev.jxbrowser.print.PdfPrinter;
import com.teamdev.jxbrowser.print.PrintJob;
import com.teamdev.jxbrowser.print.event.PrintCompleted;
import com.teamdev.jxbrowser.ui.Size;
import com.teamdev.jxbrowser.view.swing.BrowserView;
import com.teamdev.jxbrowser.view.swing.graphics.BitmapImage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.util.StopWatch;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.*;

// https://teamdev.cn/jxbrowser/docs/guides/content/

public final class App {

    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch(UUID.randomUUID().toString());

        stopWatch.start("JxBrowser 加载 Engine ");

        // 初始化 Chromium 引擎
        try (Engine engine = Engine.newInstance(
                EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                        .licenseKey("OK6AEKNYF2Y106UFSGGFOFEPC2U1IAJH43X8LQGGMOKI9XH9VSVOLLDOSVU718S6D62LXCLJA7KGAOWHEW5QECL89EOUWTG639USILF1VG80584TDTG5C3MXOVOK0B9X7DQFMC4CEPC04ZR2S")
                        .build())) {
            stopWatch.stop();

            stopWatch.start("JxBrowser 创建 Browser ");

            engine.on(PrintCompleted.class, event -> {
                System.out.println("Print completed");
            });

            // 创建一个浏览器实例
            Browser browser = engine.newBrowser();
            browser.settings().hideScrollbars();
            // Resize browser to the required dimension
            browser.resize((int) 1240, (int) 1768);

            //browser.devTools().show();
            stopWatch.stop();

            stopWatch.start("JxBrowser 加载网页 。。 ");
            // 等待加载url完成
            browser.navigation().loadUrlAndWait("https://zhpj.bjopenschool.com/growthPrint-new/index.html?schoolCode=2133001269&gradeCode=11&classCode=202401110323&templateId=1873614199084802048&pageId=1873614199617478657&platform=bmp&userId=2814&stuId=1869260292152651776&token=eyJhbGciOiJSUzI1NiJ9.eyJ4eGRtIjoiMjEzMzAwMTI2OSIsInN1YiI6IjEzODY3NDMyMTMwIiwicm9sZSI6NiwiZXhwaXJlIjoxNzY4NDQ4NDI4NDc4LCJ1c2VyY29kZSI6IjI4MTQiLCJ1c2VyTmFtZSI6IueOi-S4veiOjiIsInR5cGUiOiIyIiwiZXhwIjoxNzY4NDQ4NDI4LCJ1c2VySWQiOiIyODE0In0.A_NAZHbbUtQX3yyreQdyCWcU5z0zMo-XsrvNtYE0g7BznCPbgPqcNFqepoHzlMIAbpWtSWpXm90NhiP79EyHGV1MPASq8PUqog6BZdmBQ9MC1rgoHCZruqITtQLuCGWsNkTd5WjVSzf0vBd-y1bHIdouOeOFlKcP91JWx9iaYro", Duration.ofSeconds(5));
            stopWatch.stop();

            stopWatch.start("JxBrowser 保存网页为图片 ");

            try {

                Thread.sleep(4000);

                // 获取当前加载的网页的位图。
                // 其大小将与当前 Browser 的大小相等。
                var bitmap = browser.bitmap();

                // Convert the bitmap to java.awt.image.BufferedImage
                var bufferedImage = BitmapImage.toToolkit(bitmap);

                // Save the image to a PNG file
                ImageIO.write(bufferedImage, "PNG", new File("bitmap.png"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            stopWatch.stop();
            //navigation.reload();
            //browser.mainFrame().ifPresent(Frame::print);
            System.out.println(stopWatch.prettyPrint());
            /*
            SwingUtilities.invokeLater(() -> {

                var frame = new JFrame("JxBrowser Swing - Instance "  );
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // Shutdown Chromium and release allocated resources.
                        engine.close();
                    }
                });

                // Create and embed Swing BrowserView component to display web content.
                BrowserView view =  BrowserView.newInstance(browser);
                frame.add(view);
                frame.setSize( 1920, 1780);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);


           });*/
        }
    }
}
