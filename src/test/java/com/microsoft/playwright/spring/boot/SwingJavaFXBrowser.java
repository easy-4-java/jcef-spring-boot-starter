package com.microsoft.playwright.spring.boot;

import com.microsoft.playwright.spring.boot.enums.PDPageSize;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;


import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;


import javafx.scene.web.WebEngine;


import javafx.scene.web.WebView;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class SwingJavaFXBrowser {


    public static void main(String[] args) {


        JFrame frame = new JFrame("Swing JavaFX Browser");

        // 获取屏幕大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFXPanel jfxPanel = new JFXPanel();
        frame.add(jfxPanel);
        frame.setSize(screenSize.width, screenSize.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Platform.runLater(() -> {


            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();

            //webEngine.load("https://html5test.com/");
            //webEngine.load("http://www.example.com");
            webEngine.load("");

            BorderPane root = new BorderPane();
            root.setCenter(webView);
            Scene scene = new Scene(root);

            jfxPanel.setScene(scene);

            // 方式1. 监听webEngine加载进度，可能页面加载不完整

            webEngine.getLoadWorker().stateProperty()

                    .addListener((ov, oldState, newState) -> {

                        if (newState == Worker.State.SUCCEEDED) {
                            /*try {
                                Robot pixelGrabber = new Robot();

                                float width = PDPageSize.A4.getRectangle().getWidth();
                                float height = PDPageSize.A4.getRectangle().getHeight();
                                BufferedImage bi = pixelGrabber.createScreenCapture(new Rectangle(0, 0, (int) width, (int) height));

                                WritableImage screen = SwingFXUtils.toFXImage(bi, new WritableImage(bi.getWidth(), bi.getHeight()));

                                // browser is javafx.scene.web.WebView
                                File file = new File("screenshot_fx.png");
                                try {
                                    ImageIO.write(SwingFXUtils.fromFXImage(screen, null), "png", file);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } catch (AWTException ex) {
                                ex.printStackTrace();
                            }*/

                            snapshot(webView);

                        }

                    });



        });


    }


    public static void snapshot(WebView view) {

        WritableImage image = view.snapshot(null, null);

        try {

            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png",

                    new File(System.currentTimeMillis() + ".png"));

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

}
