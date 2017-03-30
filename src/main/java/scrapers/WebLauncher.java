package scrapers;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Amanda on 06/03/2017.
 */
public class WebLauncher extends Application {

    @Override
    public void start(Stage stage) {
        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        webEngine.load("https://www.bet365.com/?lng=1&cb=1032558600#/HO/");
        stage.setScene(new Scene(webView));
        stage.show();

        webEngine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() == 100 /*percents*/) {
                    try {
                        org.w3c.dom.Document doc = webEngine.getDocument();
                        new XMLSerializer(System.out, new OutputFormat(doc, "UTF-8", true)).serialize(doc);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

}
