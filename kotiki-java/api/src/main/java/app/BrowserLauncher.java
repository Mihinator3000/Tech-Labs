package app;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class BrowserLauncher{
    @EventListener(ApplicationReadyEvent.class)
    public void launchBrowser() throws URISyntaxException, IOException {
        System.setProperty("java.awt.headless", "false");
        Desktop.getDesktop().browse(new URI("http://localhost:9090/api/cat/get/all"));
    }
}