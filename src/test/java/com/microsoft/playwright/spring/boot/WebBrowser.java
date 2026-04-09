package com.microsoft.playwright.spring.boot;

import javafx.application.Application;

import javafx.scene.Scene;


import javafx.scene.layout.BorderPane;


import javafx.scene.web.WebView;


import javafx.stage.Stage;


public class WebBrowser extends Application {


    @Override
    public void start(Stage primaryStage) {


        WebView webView = new WebView();


        webView.getEngine().load("http://www.example.com");


        BorderPane root = new BorderPane();


        root.setCenter(webView);


        Scene scene = new Scene(root, 800, 600);


        primaryStage.setTitle("Web Browser");


        primaryStage.setScene(scene);


        primaryStage.show();


    }


    public static void main(String[] args) {


        launch(args);


    }


}