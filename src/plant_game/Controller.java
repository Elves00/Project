/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author breco
 */
public class Controller extends JPanel implements ActionListener, MouseListener {

    private PlantGameModel plantGameModel;

    public PlantGameModel getPlantGameModel() {
        return this.plantGameModel;
    }

    private CardLayout card;

    //Main field
    private boolean planting;
    private int plantToPlant;
    private boolean watering;
    private boolean picking;

    //CARDS
    private PlantGameStart plantGameStart;
    //CARDS
    private PlantGameMain plantGameMain;

    public Controller(PlantGameModel plantGameModel, PlantGameMain plantGameMain) {
        this.plantGameModel = plantGameModel;
        this.plantGameMain = plantGameMain;
        this.plantGameMain.addActionListener(this); //adds the panel as a listener for all actions within the plantGamePanel
        this.plantGameMain.addMouseListener(this);
    }

    public Controller(PlantGameModel plantGameModel) throws IOException {

        //Sets up a cardlayout to be used by this controller
        card = new CardLayout();
        this.setLayout(card);

        //Starting conditions player is not planting watering or picking
        this.planting = false;
        this.plantToPlant = -1;
        this.watering = false;
        this.picking = false;

        //Set up the game model
        this.plantGameModel = plantGameModel;
//      plantGameModel = new PlantGameModel();

        //Creates the main plant panel
        plantGameMain = new PlantGameMain(plantGameModel);
        //Creates the start up panel for loading game options
        plantGameStart = new PlantGameStart(plantGameModel);

        //Add action listeners for buttons in plant game start
        //Save slot buttons
        plantGameStart.getOne().addActionListener(this);
        plantGameStart.getTwo().addActionListener(this);
        plantGameStart.getThree().addActionListener(this);
        plantGameStart.getFour().addActionListener(this);
        plantGameStart.getFive().addActionListener(this);

        //Game startup options
        plantGameStart.getNewGame().addActionListener(this);
        plantGameStart.getPreviousGame().addActionListener(this);
        plantGameStart.getLoadGame().addActionListener(this);

        //New Game buttons
        plantGameStart.getUsername().addActionListener(this);
        plantGameStart.getSubmit().addActionListener(this);

        
        
        this.plantGameMain.addActionListener(this); //adds the panel as a listener for all actions within the plantGamePanel
        this.plantGameMain.addMouseListener(this);
//        //Main plant game button lisiteners
//        plantGameMain.getPlant().addActionListener(this);
//        plantGameMain.getWater().addActionListener(this);
//        plantGameMain.getPick().addActionListener(this);
//        plantGameMain.getInformation().addActionListener(this);
//        plantGameMain.getNextDay().addActionListener(this);
//        plantGameMain.getPlantBack().addActionListener(this);
//        plantGameMain.getWaterBack().addActionListener(this);
//        plantGameMain.getPickBack().addActionListener(this);
//        plantGameMain.getUnlockBack().addActionListener(this);
//        plantGameMain.getSave().addActionListener(this);
//        plantGameMain.getSaveBack().addActionListener(this);
//        plantGameMain.getUnlockShop().addActionListener(this);
//        plantGameMain.getInfoBack().addActionListener(this);
//
////        plantGameMain.getUnlockBack().addActionListener(this);
//        //Unlock listeners
//        for (int i = 0; i < plantGameMain.getUnlockSlot().length; i++) {
//
//            plantGameMain.getUnlockSlot()[i].addActionListener(this);
//        }
//
//        //Slave slot listiners
//        for (int i = 0; i < 5; i++) {
//            plantGameMain.getSaveSlot()[i].addActionListener(this);
//        }
//
//        //Listeners for the field panels
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                plantGameMain.getFieldLabels()[i][j].addMouseListener(this);
//            }
//        }
//        //Action listners for planting options
//        for (int i = 0; i < plantGameMain.getPlantingButtons().length; i++) {
//            plantGameMain.getPlantingButtons()[i].addActionListener(this);
//        }
//
//        //Action listeners for info panel buttons
//        for (int i = 0; i < 8; i++) {
//            plantGameMain.getInfoSlot()[i].addActionListener(this);
//        }
//
//        //Cardlayout views
        this.add("A", plantGameStart);
        this.add("B", plantGameMain);
        //Start game
        this.plantGameModel.alternatStart();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceA = e.getSource();

        //Creates a new game shifting the view to the jtext field for creating a new player
        if (sourceA == plantGameStart.getNewGame()) {
            try {
                this.plantGameModel.newGame();
            } catch (MoneyException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Gets text from the text field and creates a new player using the inputed text
        if (sourceA == plantGameStart.getSubmit()) {
            try {
                this.plantGameModel.newGame(plantGameStart.getUsername().getText());

                this.plantGameModel.initialView();
                this.card.show(this, "B");
            } catch (MoneyException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //If pressed opens the load game view showing 5 save slots to load from
        if (sourceA == plantGameStart.getLoadGame()) {
            this.plantGameModel.loadGameView();
        }

        //Large block of buttons used to load a save slot
        if (sourceA == plantGameStart.getOne()) {
            try {
                this.plantGameModel.loadGame(0);

                this.plantGameModel.initialView();
                this.card.show(this, "B");
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == plantGameStart.getTwo()) {
            try {
                this.plantGameModel.loadGame(1);

                this.plantGameModel.initialView();
                this.card.show(this, "B");
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == plantGameStart.getThree()) {
            try {
                this.plantGameModel.loadGame(2);

                this.plantGameModel.initialView();
                this.card.show(this, "B");
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == plantGameStart.getFour()) {
            try {
                this.plantGameModel.loadGame(3);
                this.plantGameModel.initialView();
                this.card.show(this, "B");
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sourceA == plantGameStart.getFive()) {
            try {
                this.plantGameModel.loadGame(4);
                this.plantGameModel.initialView();
                this.card.show(this, "B");
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //loads the previous saved game.
        if (sourceA == plantGameStart.getPreviousGame()) {
            try {
                this.plantGameModel.previousGame();
                this.plantGameModel.initialView();
                this.card.show(this, "B");
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*
        Planting section has the buttons that manage planting
         */
        //Player is planting a plant in the field
        if (sourceA == plantGameMain.getPlant()) {

            //Swtich view to the selection of plants available to plant
            this.plantGameMain.getCard().show(this.plantGameMain.getButtonPanel(), "b");

        }
        //Checks which plant has been selected to plant in the field.
        for (int i = 0; i < plantGameModel.getShop().size(); i++) {
            if (sourceA == plantGameMain.getPlantingButtons()[i]) {
                //Sets the plant to be planted and sets the planting condition to true.
                //This means if a user presses in the field it will plant this plant.
                plantToPlant = i + 1;
                planting = true;

            }
        }

        //Returns the user to the main card and disables planting
        if (sourceA == plantGameMain.getPlantBack()) {
            //Swtich view to the selection of plants available to plant
            this.plantGameMain.getCard().show(this.plantGameMain.getButtonPanel(), "a");
            planting = false;
            System.out.println("Pressing back");
        }

        //Watering
        if (sourceA == plantGameMain.getWater()) {
            //sets water condition to true.
            this.plantGameMain.getCard().show(this.plantGameMain.getButtonPanel(), "c");
            watering = true;
        }
        //Returns the user to the main card and disables watering
        if (sourceA == plantGameMain.getWaterBack()) {
            this.plantGameMain.getCard().show(this.plantGameMain.getButtonPanel(), "a");
            watering = false;

        }
        //picking
        if (sourceA == plantGameMain.getPick()) {
            this.plantGameModel.pickView();
            this.plantGameMain.getCard().show(this.plantGameMain.getButtonPanel(), "d");
            //sets picking condition to true.
            picking = true;
        }
        //Returns the user to the main card and disables picking
        if (sourceA == plantGameMain.getPickBack()) {
            this.plantGameMain.getCard().show(this.plantGameMain.getButtonPanel(), "a");
            picking = false;
            this.plantGameModel.initialView();
        }
        //Progress the game to the next day.
        if (sourceA == plantGameMain.getNextDay()) {
            try {
                this.plantGameModel.nextDay();
            } catch (MoneyException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (sourceA == plantGameMain.getSave()) {
            this.plantGameModel.saveView();
            this.plantGameMain.getCard().show(this.plantGameMain.getButtonPanel(), "e");

        }
        for (int i = 0; i < 5; i++) {
            if (sourceA == plantGameMain.getSaveSlot()[i]) {
                try {
                    this.plantGameModel.save(i + 1);

                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //Returns the user to the main card from the save view
        if (sourceA == plantGameMain.getSaveBack()) {
            this.plantGameMain.getCard().show(this.plantGameMain.getButtonPanel(), "a");

        }
        //UNLOCKS
        if (sourceA == plantGameMain.getUnlockShop()) {
            plantGameModel.unlockView();
            this.plantGameMain.getCard().show(this.plantGameMain.getButtonPanel(), "f");

        }
        //Unlock buttons
        for (int i = 0; i < this.plantGameModel.getUnlocks().size(); i++) {
            if (sourceA == plantGameMain.getUnlockSlot()[i]) {

                this.plantGameModel.unlock(i + 1);
                //Re adds action listiener as it gets removed in plantGameMain during unlock
                this.plantGameMain.getPlantingButtons()[this.plantGameModel.getShop().size() - 1].addActionListener(this);
            }
        }
        //Returns the user to the main card from the unlock view
        if (sourceA == plantGameMain.getUnlockBack()) {
            this.plantGameMain.getCard().show(this.plantGameMain.getButtonPanel(), "a");

        }
        //Information
        if (sourceA == plantGameMain.getInformation()) {
            this.plantGameMain.getCardField().show(this.plantGameMain.getFieldCard(), "b");
            this.plantGameMain.getCard().show(this.plantGameMain.getButtonPanel(), "g");
        }
        //Send back to main menu
        if (sourceA == plantGameMain.getInfoBack()) {
            this.plantGameMain.getCardField().show(this.plantGameMain.getFieldCard(), "a");
            this.plantGameMain.getCard().show(this.plantGameMain.getButtonPanel(), "a");
        }
        //Display information
        for (int i = 0; i < 8; i++) {
            if (sourceA == plantGameMain.getInfoSlot()[i]) {

                this.plantGameModel.getInfo(i);
            }
        }

    }

//MOUSE INPUTE
    @Override
    public void mouseClicked(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object sourceA = e.getSource();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (sourceA == this.plantGameMain.getFieldLabels()[i][j]) {
                    System.out.println("YOU PRESSED THE FIELD PLANT IS:" + this.plantGameModel.getPlayer().getField().getPlantArray()[i][j].toString());
                    System.out.println("Water" + this.plantGameModel.getPlayer().getField().getPlantArray()[i][j].getWaterCount() + "/" + this.plantGameModel.getPlayer().getField().getPlantArray()[i][j].getWaterLimit());
                    System.out.println("Value" + this.plantGameModel.getPlayer().getField().getPlantArray()[i][j].getValue());
                    System.out.println("X clicked is:" + i);
                    System.out.println("X clicked is:" + j);
                    if (planting) {
                        try {
                            //Plant a plant at row i collumn j
                            this.plantGameModel.plantAPlant(plantToPlant, i + 1, j + 1);

                        } catch (InstantiationException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        //Switch the panel view back to the main view from planting view
//                        this.plantGameMain.getCard().show(this.plantGameMain.getSouthPanel(), "a");
//                        //Switch the panel view back to the main view from planting view
//                        this.plantGameMain.getCard().show(this.plantGameMain.getSouthPanel(), "a");

                    }
                    if (watering) {
                        //Sets up the watering view highlighting wattered plants
                        this.plantGameModel.waterView();
                        //Water the plant at row i collumn j
                        this.plantGameModel.water(i, j);

                    }
                    if (picking) {
                        this.plantGameModel.pick(i, j);
                    }

                }
            }
        }

//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Plant Game");
        PlantGameModel pg = new PlantGameModel();
        // kill all threads when frame closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Controller(pg));
        frame.pack();
        //position the frame in the middle of the screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenDimension = tk.getScreenSize();
        Dimension frameDimension = frame.getSize();
        frame.setLocation((screenDimension.width - frameDimension.width) / 2,
                (screenDimension.height - frameDimension.height) / 2);
        //Frame starts showing
        frame.setVisible(true);
        // now display something while the main thread is still alive

    }
}