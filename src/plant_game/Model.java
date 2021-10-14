/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author breco
 */
public class Model extends Observable {

    private Player player;
    private PlantSelection shop;
    private UnlockShop unlocks;
    private OrderedList<Score> highscores;
 
    private Modelsave modelSave;
    private ModelRun modelRun;
    private DBManager manager;
    Data data;

    /**
     * Sets up the database for the plant Game.
     *
     */
    public Model() {
        //Information search terms for use in update
        manager = new DBManager();
        manager.constructDatabse();
        modelSave = new Modelsave(manager);
        modelRun = new ModelRun(manager);
    }

    /**
     * Determines the visibility of the load game buttons and previous game
     * button based on data within the data table.
     *
     * This is effectivly a middle man method.
     *
     */
    public void start() {
        data = manager.start();
        //passes data to the view
        setChanged();
        notifyObservers(data);

    }

    /**
     * Retrieves information stored within the information table and passes it
     * to the view.
     *
     *
     * @param selection
     */
    public void getInfo(int selection) {
        data = modelSave.getInfo(selection,data);
    }

    /**
     * Creates a new game with a player containing the inputted name.
     *
     * @param name
     * @throws MoneyException
     * @throws FileNotFoundException
     */
    protected void newGame(String name) throws MoneyException, FileNotFoundException {
        setPlayer(new Player(name));
        data = modelSave.newGame(name, data, getPlayer());

    }

    /**
     * Saves the current game and notifies the data class of changes.
     *
     * Save game to current game and set main menu to true telling the data
     * class to preform main menu actions. afterwards create a new data class as
     * the options present to the player all require a fresh data object.
     */
    public void mainMenu() {
        //save to current game to allowing returning
        this.save(0);
        //restart the data
        this.data = manager.start();
        data.setMainMenu(true);
        setChanged();
        notifyObservers(data);
        //main menu complete revert to false
        data.setMainMenu(false);

    }

    /**
     * Creates a new game using a user generated name to establish player and
     * new files.
     *
     * Creates a new player using the file managers newPlayer option and
     * provides a helpful start up message for a new player
     *
     * @throws MoneyException
     * @throws FileNotFoundException
     */
    protected void newGame() throws MoneyException, FileNotFoundException {

        setShop(new PlantSelection());
        setUnlocks(new UnlockShop());

        this.modelSave.newGame(data, getShop(), getUnlocks());

    }

    /**
     * Loads a game from the current game file.
     *
     * Calls load game for slot zero.
     *
     */
    protected void previousGame() {

        data = modelSave.previousGame(data);
    }

    /**
     * Notify the observe to display the options for loading game
     */
    protected void loadGameView() {

        data = modelSave.loadGameView(data);

    }

    /**
     * Loads a selected plant game from the database.
     *
     * Will load a plant game from the data base.
     *
     * @param selection
     */
    protected void loadGame(int selection) {

        data = modelSave.loadGame(selection, data);
        player = modelSave.getPlayer();
        unlocks = modelSave.getUnlocks();
        shop = modelSave.getShop();

    }

    public void save(int selection) {

        data = modelSave.save(selection, data, player);

    }

    public void saveView() {

        data = modelSave.saveView(data);

    }

    public void plantAPlantView() {
        shopUpdate();
    }

    public void plantAPlant(int selection, int x, int y) throws InstantiationException, IllegalAccessException {

        modelRun.setPlayer(player);
        modelRun.setShop(shop);
        data = getModelRun().plantAPlant(selection, x, y, data);
        player = getModelRun().getPlayer();

    }

    public void unlockView() {

        modelRun.setUnlocks(unlocks);
        data = modelRun.unlockView(data);

    }

    public void shopUpdate() {

        modelRun.setShop(shop);
        modelRun.setPlayer(player);

        data = modelRun.shopUpdate(data);

        shop = modelRun.getShop();
        player = modelRun.getPlayer();

    }

    /**
     * Unlocks a plant from the unlock shop for the current game.
     *
     *
     * @param selection
     */
    public void unlock(int selection) {

        modelRun.setPlayer(player);
        modelRun.setUnlocks(unlocks);
        modelRun.setShop(shop);

        data = modelRun.unlock(selection, data);

        unlocks = modelRun.getUnlocks();
        player = modelRun.getPlayer();
        shop = modelRun.getShop();

    }

    public void initialView() {
        //Update the field
        modelRun.setPlayer(player);
        data = modelRun.fieldUpdate(data);

    }

    public void nextDay() throws MoneyException, FileNotFoundException {

        modelRun.setPlayer(player);
        modelRun.setShop(shop);
        modelRun.setUnlocks(unlocks);

        data = modelRun.nextDay(data);

        player = modelRun.getPlayer();
        shop = modelRun.getShop();
        unlocks = modelRun.getUnlocks();

    }

    public void waterView() {
        //Update the field
        modelRun.setPlayer(player);
        data = modelRun.waterView(data);

    }

    public void water(int x, int y) {
        modelRun.setPlayer(player);
        data = modelRun.water(x, y, data);
        player = modelRun.getPlayer();
    }

    /**
     * Notify the view that the player intends to pick a plant.
     *
     * Once the view has been notified set field pick back to false.
     */
    public void pickView() {
        modelRun.setPlayer(player);
        data = modelRun.pickView(data);

    }

    public void pick(int i, int j) {

        modelRun.setPlayer(player);
        data = modelRun.pick(i, j, data);
        player = modelRun.getPlayer();

    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return the shop
     */
    public PlantSelection getShop() {
        return shop;
    }

    /**
     * @param shop the shop to set
     */
    public void setShop(PlantSelection shop) {
        this.shop = shop;
    }

    /**
     * @param shop the shop to set
     */
    public void setShop(String shop) {
        this.shop = new PlantSelection(shop);
    }

    /**
     * @return the unlocks
     */
    public UnlockShop getUnlocks() {
        return unlocks;
    }

    /**
     * @param unlocks the unlocks to set
     */
    public void setUnlocks(UnlockShop unlocks) {
        this.unlocks = unlocks;

    }

    /**
     * @return the high scores
     */
    public OrderedList<Score> getHighscores() {
        return highscores;
    }

    /**
     * @param highscores the highscores to set
     */
    public void setHighscores(OrderedList<Score> highscores) {
        this.highscores = highscores;
    }

    /**
     * @return the modelSave
     */
    public Modelsave getModelSave() {
        return modelSave;
    }

    /**
     * @return the modelRun
     */
    public ModelRun getModelRun() {
        return modelRun;
    }
}
