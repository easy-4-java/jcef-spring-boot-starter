package com.microsoft.playwright.spring.boot;

import static com.teamdev.jxbrowser.engine.RenderingMode.OFF_SCREEN;
import static com.teamdev.jxbrowser.print.PaperSize.ISO_A4;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.browser.callback.PrintCallback;
import com.teamdev.jxbrowser.browser.callback.PrintHtmlCallback;
import com.teamdev.jxbrowser.browser.callback.SaveAsPdfCallback;
import com.teamdev.jxbrowser.browser.event.PrintPreviewOpened;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;
import com.teamdev.jxbrowser.frame.Frame;
import com.teamdev.jxbrowser.print.PdfPrinter;
import com.teamdev.jxbrowser.print.PrintJob;
import com.teamdev.jxbrowser.print.event.PrintCompleted;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JxBrowserDemo {
    // 构造一个浏览器实例
    public JxBrowserDemo() {
        // 设置证书秘钥
        System.setProperty("jxbrowser.license.key", "OK6AEKNYF2Y106UFSGGFOFEPC2U1IAJH43X8LQGGMOKI9XH9VSVOLLDOSVU718S6D62LXCLJA7KGAOWHEW5QECL89EOUWTG639USILF1VG80584TDTG5C3MXOVOK0B9X7DQFMC4CEPC04ZR2S");
    }
    // 执行方法
    public void run(String url) {
        EngineOptions engineOptions = EngineOptions.newBuilder(OFF_SCREEN).build();
        // 初始化 Chromium 引擎
        Engine engine = Engine.newInstance(engineOptions);
        // 创建一个浏览器实例
        Browser browser = engine.newBrowser();
        // 等待加载url完成
        browser.navigation().loadUrlAndWait(url);
        // 打印网络页面
        browser.mainFrame().ifPresent(frame -> System.out.println(frame.html()));
        browser.set(PrintCallback.class, (params, tell) -> {
            tell.print();
        });
        // 设置pdf文件导出位置
        browser.set(PrintHtmlCallback.class, (params, tell) -> {
            Path path = Paths.get("/Users/wandl/Downloads/temp3.pdf");
            PdfPrinter<PdfPrinter.HtmlSettings> printer = params.printers().pdfPrinter();
            PrintJob<PdfPrinter.HtmlSettings> printJob = printer.printJob();
            printJob.settings()
                    .paperSize(ISO_A4)
                    .enablePrintingBackgrounds()
                    .pdfFilePath(path)
                    .apply();
            printJob.on(PrintCompleted.class, event -> {
                if (event.isSuccess()) {
                    System.out.println("Printing is completed successfully.");
                } else {
                    System.out.println("Printing has failed.");
                }
            });
            tell.proceed(printer);
        });
        browser.mainFrame().ifPresent(frame -> {
            frame.print();
        });
        try {
            Thread.sleep(100000);
        }catch (InterruptedException e) {
            // do nothing
        }
        // 关闭引擎释放资源
        engine.close();
    }

    public static void main(String[] args) {
        new JxBrowserDemo().run("https://zhpj.bjopenschool.com/growthPrint-new/index.html?schoolCode=2133001269&gradeCode=11&classCode=202401110323&templateId=1873614199084802048&pageId=1873614199617478657&platform=bmp&userId=2814&stuId=1869260292152651776&token=eyJhbGciOiJSUzI1NiJ9.eyJ4eGRtIjoiMjEzMzAwMTI2OSIsInN1YiI6IjEzODY3NDMyMTMwIiwicm9sZSI6NiwiZXhwaXJlIjoxNzY4NDQ4NDI4NDc4LCJ1c2VyY29kZSI6IjI4MTQiLCJ1c2VyTmFtZSI6IueOi-S4veiOjiIsInR5cGUiOiIyIiwiZXhwIjoxNzY4NDQ4NDI4LCJ1c2VySWQiOiIyODE0In0.A_NAZHbbUtQX3yyreQdyCWcU5z0zMo-XsrvNtYE0g7BznCPbgPqcNFqepoHzlMIAbpWtSWpXm90NhiP79EyHGV1MPASq8PUqog6BZdmBQ9MC1rgoHCZruqITtQLuCGWsNkTd5WjVSzf0vBd-y1bHIdouOeOFlKcP91JWx9iaYro");
    }
}

