package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import sample.Main;

import java.net.URL;

public class fxmlloader {
    private Pane view;

    public Pane getpage(String filename){
        try {
            URL fileurl= Main.class.getResource("/sample/"+filename+".fxml");
            if (fileurl==null)
                throw new java.io.FileNotFoundException("FILE KAHAN HAU");
            view=new FXMLLoader().load(fileurl);
        }
        catch (Exception e){
            System.out.println("nahi mila");
        }
        return view;
    }
}
