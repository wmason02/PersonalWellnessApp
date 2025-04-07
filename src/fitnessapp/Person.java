package fitnessapp;
import fitnessapp.mental.MentalHealth;
import fitnessapp.physical.PhysicalHealth;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 *  Represents a person within our program
 *  Has a composition relationship with PhysicalHealth and MentalHealth
 */
public class Person implements Serializable {

    private String fullName;
    private LocalDate birthday;
    
    /**
     * Height in centimeters
     */
    private double height;
    /**
     * Weight in kilograms
     */
    private double weight;

    /**
     * Target weight in kilograms
     */
    private double targetWeight;

    private Gender gender;

    private final PhysicalHealth physicalHealth = new PhysicalHealth(this);
    private final MentalHealth mentalHealth = new MentalHealth();

    /**
     * Set the person's full name
     * @param fullName the person's new full name
     */
    public void setFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name can't be blank");
        } 
        this.fullName = fullName;
    }

    /**
     * @return The person's full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Set the person's birthday
     * @param date the date of the new birthday
     */
    public void setBirthday(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Must specify a birthday");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birthday can't be in the future");
        }
        this.birthday = date;
    }

    /**
     * @return the person's birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * @return the person's age in years today
     */
    public int getAge() {
        LocalDate today = LocalDate.now();
        return Period.between(birthday, today).getYears();
    }

    /**
     * Set the person's height in centimeters
     */
    public void setHeightCM(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height can't be negative or zero");
        }
        this.height = height;
    }

    /**
     * @return the person's height in centimeters
     */
    public double getHeightCM() {
        return height;
    }

    /**
     * @return the person's height in inches
     */
    public double getHeightIN() {
        return height / 2.54f;
    }

    /**
     * Set the person's weight in kilograms
     * @param weight the person's weight in kilograms
     */
    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight can't be negative or zero");
        }
        this.weight = weight;
    }

    /**
     * @return the person's weight in kilograms
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Set the person's target weight in kilograms
     * @param targetWeight the person's target weight in kilograms
     */
    public void setTargetWeight(double targetWeight) {
        if (targetWeight <= 0) {
            throw new IllegalArgumentException("Target weight can't be negative or zero");
        }
        this.targetWeight = targetWeight;
    }
    
    /**
     * @return the person's target weight
     */
    public double getTargetWeight() {
        return targetWeight;
    }

    /**
     * Set the person's gender
     * @param gender the person's new gender
     */
    public void setGender(Gender gender) {
        if (gender == null) {
            throw new IllegalArgumentException("Must specify a gender");
        }
        this.gender = gender;
    }

    /**
     * @return the person's gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * @return the person's physical health
     */
    public PhysicalHealth getPhysicalHealth() {
        return physicalHealth;
    }

    /**
     * @return the person's mental health
     */
    public MentalHealth getMentalHealth() {
        return mentalHealth;
    }

    /**
     * @return the person's BMI based on their height and weight (kg/m^2)
     */
    public double calcBMI() {
        return weight / Math.pow(height/100, 2);
    }
}
