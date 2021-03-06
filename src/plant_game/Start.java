/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author breco
 */
public class Start {

    public static void main(String[] args) {
        try {

            View view = new View();
            Model model = new Model();
            model.addObserver(view); // Build connection between the view and the model.
            model.getModelSave().addObserver(view);
            model.getModelRun().addObserver(view);
            Controller controller = new Controller(model, view);

        } catch (IOException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
