package fitnessapp.fileio;

import fitnessapp.fileio.data.BikeStats;
import fitnessapp.fileio.data.CalisthenicsStats;
import fitnessapp.fileio.data.ExerciseStats;
import fitnessapp.fileio.data.SwimmingStats;
import fitnessapp.fileio.data.WalkingStats;
import fitnessapp.fileio.data.WeightLiftingStats;
import fitnessapp.physical.exercise.Activity;
import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.exercise.calisthenics.CalisthenicsExercise;
import fitnessapp.physical.exercise.calisthenics.WeightLiftingExercise;
import fitnessapp.physical.exercise.cardio.CyclingExercise;
import fitnessapp.physical.exercise.cardio.SwimmingExercise;
import fitnessapp.physical.exercise.cardio.WalkingExercise;
import fitnessapp.physical.foodstuff.Food;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

/**
 *  Overall compiler of the CSV datasets and they're accompanying class
 *  Organizes CSV file and destination class; ie. who uses what
 */
public class CSVData implements CSVReadingInterface {
    private static HashMap<Class<?>, String> csvs = new HashMap<>();
    static {
        csvs.put(CyclingExercise.class, "dataFiles/MET Values - BikingMET.csv");
        csvs.put(WalkingExercise.class, "dataFiles/MET Values - WalkingMET.csv");
        csvs.put(SwimmingExercise.class, "dataFiles/MET Values - SwimmingMET.csv");
        csvs.put(CalisthenicsExercise.class, "dataFiles/MET Values - CalisthenicsMET.csv");
        csvs.put(WeightLiftingExercise.class, "dataFiles/MET Values - WeightsMET.csv");
        csvs.put(Food.class, "dataFiles/Foods and Drinks - FoodAndDrink.csv");
    }

    private static List<ExerciseStats> exercisesStats = new ArrayList<>();
    static {
        for (Entry<Class<?>, String> entry : csvs.entrySet()) {
            String csvPath = entry.getValue();
            if (!Exercise.class.isAssignableFrom(entry.getKey())) {
                continue; // Not an exercise csv
            }
            try {
                for (String[] points : CSVReadingInterface.read(csvPath)) {
                    Activity activity = Activity.valueOf(points[0].toUpperCase());
                    switch (activity) {
                        case BICYCLING:
                            exercisesStats.add(new BikeStats(points[1], points[2], Double.parseDouble(points[3])));
                            break;
                        case WALKING:
                            exercisesStats.add(new WalkingStats(points[1], points[2], Double.parseDouble(points[3])));
                            break;
                        case SWIMMING:
                            exercisesStats.add(new SwimmingStats(points[1], points[2], Double.parseDouble(points[3])));
                            break;
                        case CALISTHENICS:
                            exercisesStats.add(new CalisthenicsStats(points[1], Double.parseDouble(points[2])));
                            break;
                        case WEIGHTS:
                            exercisesStats.add(new WeightLiftingStats(points[1], Double.parseDouble(points[2])));
                            break;
                    }
                }
            } catch (IOException exception) {
                logger.log(Level.SEVERE, "Unable to read Activity CSV: " + csvPath, exception);
            } catch (EnumConstantNotPresentException exception) {
                logger.log(Level.SEVERE, "Activity CSV: " + csvPath + " uses an unknown enum constant: " + exception.constantName(), exception);
            }
        }
        logger.log(Level.INFO, "Activity CSVS were interpretted successfully");
    }

    /**
     * @return list of exercises after conversion from CSV
     */
    public static List<ExerciseStats> getExercisesStats() {
        return exercisesStats;
    }

    private static Map<String, Food> foods = new HashMap<>();
    static {
        try {
            for (String[] points : CSVReadingInterface.read(csvs.get(Food.class))) {
                String name = points[0];
                foods.put(name, new Food(name, Integer.parseInt(points[2]), points[1]));
            }
            logger.log(Level.INFO, "Food and Drink CSV was interpretted successfully");
        } catch (IOException exception) {
            logger.log(Level.SEVERE, "Unable to read Food and Drink CSV", exception);
        } catch (EnumConstantNotPresentException exception) {
            logger.log(Level.SEVERE, "Food and Drink CSV uses an unknown enum constant: " + exception.constantName(), exception);
        }
    }

    /**
     * @return a map of foods/drink names to Foods
     */
    public static Map<String, Food> getFoods() {
        return foods;
    }

    /**
     * Get a food by a name
     * @param name the name of the food
     * @return the food if found otherwise null
     */
    public static Food getFood(String name) {
        return foods.get(name);
    }
}