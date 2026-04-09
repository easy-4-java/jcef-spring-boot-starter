package com.microsoft.playwright.spring.boot;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;

public class Main2 {
    public static void main(String[] args) {
        // 初始化JCEF
        CefApp cefApp = CefApp.getInstance();
// 创建客户端实例
        CefClient client = cefApp.createClient();
        // 创建浏览器实例
        CefBrowser browser = client.createBrowser("https://www.example.com", false, false);
    }
}
