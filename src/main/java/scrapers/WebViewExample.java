package scrapers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Created by Amanda on 03/03/2017.
 */

    public class WebViewExample extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    /*public void start(Stage primaryStage) {
        WebView webview = new WebView();
        final WebEngine webengine = webview.getEngine();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        webengine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            Document doc = webengine.getDocument();
                            try {
                                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
                                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                                transformer.transform(new DOMSource(doc),
                                        new StreamResult(new OutputStreamWriter(System.out, "UTF-8")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });

        webengine.load("http://www.boylesports.com/betting");
        primaryStage.setScene(new Scene(webview, 800, 800));
        primaryStage.show();

    }*/

    @Override
    public void start(final Stage stage) throws Exception {
        stage.setTitle("JavaFX: WebView");

        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();

        webEngine.setJavaScriptEnabled(true);
        String url = ("http://www.boylesports.com/betting");
        webEngine.load(url);
        Thread.sleep(10000);
        Thread.sleep(10000);
        Thread.sleep(10000);

        StackPane sp = new StackPane();
        sp.getChildren().add(webView);

        Scene root = new Scene(sp);

        stage.setScene(root);
        stage.show();
        System.out.println(webEngine.getDocument());
    }


}