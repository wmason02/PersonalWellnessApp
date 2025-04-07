package fitnessapp.physical.foodstuff;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *  Represents a food/drink item
 *  
 * @author David Choo
 * @version 1
 */
public class Food implements Serializable {
    private String name;
    private int calories;
    private LocalDate date;
    private MealType mealType;
    private int quantity;
    private String servingSize;

    public Food(String name, int calories, String servingSize) {
        setName(name);
        setCalories(calories);
        setServingSize(servingSize);
        setQuantity(1);
    }

    public Food(String name, int calories, LocalDate date, MealType mealType, int quantity, String servingSize) {
        this(name, calories, servingSize);
        setDate(date);
        setMealType(mealType);
        setQuantity(quantity);
    }

    /**
     * @param name of this food/drink item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return name of this food/drink item
     */
    public String getName() {
        return name;
    }

    /**
     * @param number of calories for this food/drink item
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }

    /**
     * @return number of calories for this food/drink item
     */
    public int getCalories() {
        return calories * quantity;
    }

    /**
     * @return the date this food/drink item was inputted
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * @param the date this food/drink item was consumed
     */
    public void setDate(LocalDate dayAte) {
        this.date = dayAte;
    }

    /**
     * @return the time or type of meal this food/drink item was inputted
     */
    public MealType getMealType() {
        return this.mealType;
    }

    /**
     * @param the time or type of meal this food/drink item was consumed
     */
    public void setMealType(MealType mealType) {
        if (mealType == null) {
            throw new IllegalArgumentException("Please specify a meal type");
        }
        this.mealType = mealType;
    }

    /**
     * @return the number food/drink item(s) consumed
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * @param the number of time this food/drink item was consumed
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the serving size of this food
     */
    public String getServingSize() {
        return servingSize;
    }

    /**
     * Set this food's serving size
     * @param servingSize the new serving size of the food
     */
    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    /**
     * Create a new Food instance with the given date, meal type and quantity.
     * @param date the date
     * @param mealType the meal type
     * @param quantity the quantity ate
     * @return a new food instance with info combined from this instance
     */
    public Food createEntry(LocalDate date, MealType mealType, int quantity) {
        return new Food(name, calories, date, mealType, quantity, servingSize);
    }

    /**
     * @return the food as a string
     */
    @Override
    public String toString() {
        return getName() + " - " + getServingSize();
    }    
}