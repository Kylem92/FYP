package scrapers;

/**
 * Created by Amanda on 06/03/2017.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Render extends Application {


    public static void main(String[] args) throws InterruptedException {

        launch(args);
        compile();
        System.out.println("stop");
    }

    public static void compile() throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                new Render().start(new Stage()); //EDIT: copy/paste mistake
            }
        };

        Platform.runLater(t);
        t.join(); // never get here!
    }

    public void start(final Stage primaryStage) {
        final WebView webView = new WebView();
        final WebEngine engine = webView.getEngine();
        //final WebEngine engine = new WebEngine();

        //engine.documentProperty().addListener( new ChangeListener<Document>() { @Override public void changed(ObservableValue<? extends Document> prop, Document oldDoc, Document newDoc) {
        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                if (newState == Worker.State.SUCCEEDED) {
                    System.out.println(engine.getDocument());

                    primaryStage.close();
                    //Platform.setImplicitExit(true);

                    Platform.exit();  // platform will not exit ... it will exit if I use Application.launch(), but throws an error: java.lang.IllegalStateException: Attempt to call defer when toolkit not running
                    System.out.println("after exit");
                }
            }
        });

        engine.load("https://www.bet365.com");
        System.out.println(engine.getDocument());
    }
}