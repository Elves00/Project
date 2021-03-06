/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author breco
 */
public class PlantFieldTest {

    public PlantFieldTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getWaterState method, of class PlantField.
     */
    @Test
    public void testGetWaterState() {
        System.out.println("getWaterState");
        PlantField instance = new PlantField();
        String[][] expResult = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                expResult[i][j] = "0/10";
            }
        }
        String[][] result = instance.getWaterState();
        assertArrayEquals("Test 1", expResult, result);

        //Watering a differnet plant
        instance.newPlant(new Saffron(), 2, 2);
        instance.water(2, 2);
        expResult[2][2] = "1/3";
        result = instance.getWaterState();
        assertArrayEquals("Test 2", expResult, result);

        //watering dirt
        instance.water(1, 1);
        expResult[1][1] = "1/10";
        result = instance.getWaterState();
        assertArrayEquals("Test 3", expResult, result);

    }

    /**
     * Test of getValueState method, of class PlantField.
     */
    @Test
    public void testGetValueState() {
        System.out.println("getValueState");
        PlantField instance = new PlantField();
        String[][] expResult = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                expResult[i][j] = "$0";
            }
        }
        String[][] result = instance.getValueState();
        assertArrayEquals("Test 1", expResult, result);

        //Value state after plant array money has changed
        instance.getPlant(2, 2).setValue(10);
        expResult[2][2] = "$10";
        result = instance.getValueState();
        assertArrayEquals("Test 2", expResult, result);

    }

    /**
     * Test of getGrowthState method, of class PlantField.
     */
    @Test
    public void testGetGrowthState() {
        System.out.println("getGrowthState");
        PlantField instance = new PlantField();
        String[][] expResult = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                expResult[i][j] = "0/0";
            }
        }
        String[][] result = instance.getGrowthState();
        assertArrayEquals("Test 1", expResult, result);

        //testing with a grown plant
        instance.newPlant(new Saffron(), 1, 2);
        instance.getPlant(1, 2).setGrowth(2);
        expResult[1][2] = "2/2";
        result = instance.getGrowthState();
        assertArrayEquals("Test 2", expResult, result);

    }

    /**
     * Test of getDayState method, of class PlantField.
     */
    @Test
    public void testGetDayState() {
        System.out.println("getDayState");
        PlantField instance = new PlantField();
        String[][] expResult = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                expResult[i][j] = "0/0";
            }
        }
        String[][] result = instance.getDayState();
        assertArrayEquals("Test 1", expResult, result);

        //Testing with a plant
        instance.newPlant(new Cabbage(), 2, 1);
        instance.nextDay();
        expResult[2][1] = "1/4";
        result = instance.getDayState();
        assertArrayEquals("Test 2", expResult, result);

    }

    /**
     * Test of setAllPlants method, of class PlantField.
     */
    @Test
    public void testSetAllPlants_ArrayList() {
        System.out.println("setAllPlants");
        ArrayList<Plant> plants = new ArrayList();
        String[][] expected = new String[3][3];

        for (int i = 0; i < 9; i++) {
            plants.add(new Dirt());
        }

        PlantField instance = new PlantField();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                expected[i][j] = instance.getPlant(i, j).toString();
            }
        }
        instance.setAllPlants(plants);
        String[][] actuals = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                actuals[i][j] = instance.getPlant(i, j).toString();
            }
        }

        Assert.assertArrayEquals("Test 1", expected, actuals);
        instance.newPlant(new Saffron(), 2, 2);

        //Checking it's seting plants in correct place
        plants.remove(8);
        plants.add(new Saffron());
        instance.setAllPlants(plants);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                expected[i][j] = instance.getPlant(i, j).toString();
                actuals[i][j] = instance.getPlant(i, j).toString();
            }
        }

        Assert.assertArrayEquals("Test 2", expected, actuals);

    }

    /**
     * Test of setAllPlants method, of class PlantField.
     */
    @Test
    public void testSetAllPlants_StringArrArr() {
        System.out.println("setAllPlants");
        String[][] plants = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                plants[i][j] = "dirt";
            }
        }
        PlantField instance = new PlantField();
        instance.setAllPlants(plants);
        String[][] actuals = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                actuals[i][j] = instance.getPlant(i, j).toString();
            }
        }

        Assert.assertArrayEquals("Test 1", plants, actuals);

        instance.newPlant(new Truffle(), 0, 0);
        instance.newPlant(new Truffle(), 1, 2);
        instance.newPlant(new Truffle(), 0, 1);
        instance.newPlant(new Truffle(), 0, 2);

        plants[0][0] = "truffle";
        plants[1][2] = "truffle";
        plants[0][1] = "truffle";
        plants[0][2] = "truffle";
        instance.setAllPlants(plants);
        actuals = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                actuals[i][j] = instance.getPlant(i, j).toString();
            }
        }

        Assert.assertArrayEquals("Test 1", plants, actuals);

    }

    /**
     * Test of setAllPlantStatus method, of class PlantField.
     */
    @Test
    public void testSetAllPlantStatus_ArrayList() {
        System.out.println("setAllPlantStatus");
        ArrayList<String> details = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            details.add(new Dirt().toData());
        }

        PlantField instance = new PlantField();
        instance.setAllPlantStatus(details);
        String[][] expected = new String[3][3];
        String[][] actual = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                expected[i][j] = "dirt";
                actual[i][j] = instance.getPlant(i, j).toString();

            }
        }
        Assert.assertArrayEquals(expected, actual);

    }

    /**
     * Test of setAllPlantStatus method, of class PlantField.
     */
    @Test
    public void testSetAllPlantStatus_StringArrArr() {
        System.out.println("setAllPlantStatus");
        String[][] details = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                details[i][j] = new Dirt().toData();
            }
        }
        PlantField instance = new PlantField();
        instance.setAllPlantStatus(details);

    }

    /**
     * Test of newPlant method, of class PlantField.
     */
    @Test
    public void testNewPlant() {
        System.out.println("newPlant");
        Plant plant = new Saffron();
        int x = 0;
        int y = 0;
        PlantField instance = new PlantField();
        instance.newPlant(plant, x, y);

        assertEquals(new Saffron().toData(), instance.getPlant(x, y).toData());

    }

    /**
     * Test of getPlant method, of class PlantField.
     */
    @Test
    public void testGetPlant() {
        System.out.println("getPlant");
        int x = 0;
        int y = 0;
        PlantField instance = new PlantField();
        Plant expResult = new Dirt();
        Plant result = instance.getPlant(x, y);
        assertEquals(expResult.toData(), result.toData());

    }

    /**
     * Test of nextDay method, of class PlantField.
     */
    @Test
    public void testNextDay() {
        System.out.println("nextDay");
        PlantField instance = new PlantField();
        instance.nextDay();
        String[][] actuals = new String[3][3];
        String[][] expected = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                actuals[i][j] = instance.getPlant(i, j).toData();
                expected[i][j] = new Dirt().toData();
            }
        }
        assertArrayEquals(expected, actuals);
    }

    /**
     * Test of pollinate method, of class PlantField.
     */
    @Test
    public void testCheckPolination() {
        System.out.println("checkPolination");
        PlantField instance = new PlantField();
        instance.newPlant(new Saffron(), 0, 0);
        instance.newPlant(new Broccoli(), 1, 0);
        instance.pollinate();

        boolean actual = instance.getPlant(1, 0).isPollinated();

        assertTrue(actual);

        //Checks a plant that doesn't get pollinatied
        actual = instance.getPlant(2, 2).isPollinated();
        assertFalse(actual);

    }

    /**
     * Test of polinateNeighbours method, of class PlantField.
     */
    @Test
    public void testPolinateNeighbours() {
        System.out.println("polinateNeighbours");
        int[] neighbours = null;
        PlantField instance = new PlantField();

        boolean check = false;
        try {
            instance.polinateNeighbours(neighbours);
        } catch (IllegalArgumentException ie) {
            check = true;
        }
        assertTrue(check);

        //Check pollination works on neighbours
        instance.newPlant(new Broccoli(), 0, 1);
        instance.newPlant(new Broccoli(), 2, 1);
        instance.newPlant(new Broccoli(), 1, 0);
        instance.newPlant(new Broccoli(), 1, 2);
        neighbours = instance.getNeighbours(1, 1);
        instance.polinateNeighbours(neighbours);

        assertTrue(instance.getPlant(0, 1).isPollinated());
        assertTrue(instance.getPlant(2, 1).isPollinated());
        assertTrue(instance.getPlant(1, 0).isPollinated());
        assertTrue(instance.getPlant(1, 2).isPollinated());

    }

    /**
     * Test of getNeighbours method, of class PlantField.
     */
    @Test
    public void testGetNeighbours() {
        System.out.println("getNeighbours");
        int x = 0;
        int y = 0;
        PlantField instance = new PlantField();
        int[] expResult = new int[]{-1, 1, -1, 1, 0, 0};
        int[] result = instance.getNeighbours(x, y);
        assertArrayEquals(expResult, result);

    }

    /**
     * Test of water method, of class PlantField.
     */
    @Test
    public void testWater() {
        System.out.println("water");
        int x = 0;
        int y = 0;
        PlantField instance = new PlantField();
        instance.newPlant(new Broccoli(), x, y);
        instance.water(x, y);
        Plant broco = new Broccoli();
        broco.water();
        assertEquals(instance.getPlant(x, y).toData(), broco.toData());

        //Just checking it doesnt break when things are out of bounds
        instance.water(-1, y);
        instance.water(4, y);
        instance.water(x, -1);
        instance.water(x, 4);

    }

    /**
     * Test of pickPlant method, of class PlantField.
     */
    @Test
    public void testPickPlant() {
        System.out.println("pickPlant");
        int x = 0;
        int y = 0;
        PlantField instance = new PlantField();

        int expResult = 0;
        int result = instance.pickPlant(x, y);
        assertEquals(expResult, result);

        instance.newPlant(new Broccoli(), x, y);
        result = instance.pickPlant(x, y);
        assertEquals(expResult, result);

        instance.newPlant(new Broccoli(), 1, 1);
        instance.getPlant(1, 1).setValue(100);
        expResult = 100;
        result = instance.pickPlant(1, 1);
        assertEquals(expResult, result);

        instance.newPlant(new Truffle(), x, y);
        expResult = 0;
        result = instance.pickPlant(x, y);
        assertEquals(expResult, result);

        assertEquals(new Truffle().toData(), instance.getPlant(x, y).toData());

    }

    /**
     * Test of toData method, of class PlantField.
     */
    @Test
    public void testToFile() {
        System.out.println("toFile");
        PlantField instance = new PlantField();
        String[] expResult = new String[6];

        for (int i = 0; i < 3; i++) {
            expResult[i] = "0 0 0 0 0 10 0 10 false false 0 0 0 0 0 10 0 10 false false 0 0 0 0 0 10 0 10 false false ";
            expResult[i + 3] = " dirt dirt dirt";
        }

        String[] result = instance.toFile();

        assertArrayEquals(expResult, result);

        instance.newPlant(new Broccoli(), 0, 0);
        result = instance.toFile();
        expResult[0] = "3 0 0 6 0 3 0 10 false false 0 0 0 0 0 10 0 10 false false 0 0 0 0 0 10 0 10 false false ";
        expResult[3] = " broccoli dirt dirt";

        assertArrayEquals(expResult, result);

    }

    /**
     * Test of getPlantArray method, of class PlantField.
     */
    @Test
    public void testGetPlantArray() {
        System.out.println("getPlantArray");
        PlantField instance = new PlantField();
        Plant[][] expResult = new Plant[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                expResult[i][j] = instance.getPlant(i, j);

            }
        }

        Plant[][] result = instance.getPlantArray();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of setPlantArray method, of class PlantField.
     */
    @Test
    public void testSetPlantArray() {
        System.out.println("setPlantArray");
        Plant[][] plantArray = null;
        PlantField instance = new PlantField();
        instance.setPlantArray(plantArray);

        assertNull(instance.getPlantArray());

        plantArray = new Plant[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                plantArray[i][j] = new Dirt();
            }
        }
        instance.setPlantArray(plantArray);
        assertArrayEquals(plantArray, instance.getPlantArray());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                plantArray[i][j] = new Broccoli();
                instance.newPlant(new Broccoli(), i, j);
            }
        }

        instance.setPlantArray(plantArray);
        assertArrayEquals(plantArray, instance.getPlantArray());

    }

    /**
     * Test of toString method, of class PlantField.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        PlantField instance = new PlantField();
        String expResult = "| dirt       dirt       dirt      |\n"
                + "| dirt       dirt       dirt      |\n"
                + "| dirt       dirt       dirt      |";
        String result = instance.toString();
        assertEquals(expResult, result);

        instance.newPlant(new Broccoli(), 2, 0);
        instance.newPlant(new Truffle(), 1, 1);
        expResult = "| dirt       dirt       dirt      |\n"
                + "| dirt       truffle    dirt      |\n"
                + "| broccoli   dirt       dirt      |";
        result = instance.toString();

        assertEquals(expResult, result);
    }

}
