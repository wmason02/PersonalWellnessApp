package fitnessapp.physical;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import fitnessapp.Person;
import fitnessapp.TimestampedPeriod;
import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.foodstuff.Food;

/**
 * Represents the physical health of a person.
 * Has many menthods to calculate the calories burned and ate.
 *  
 * @author David Choo
 * @author Nick Kurth
 * @version 1
 */
public class PhysicalHealth implements Serializable {
    private Person person;
    private List<Exercise> exercises = new ArrayList<>();
    private List<Food> foods = new ArrayList<>();

    public PhysicalHealth(Person person) {
        this.person = person;
    }

    /**
     * Check if the TimestampedPeriod collides with any periods stored
     * @param period the period to check
     * @return true if the period overlaps with another period, otherwise false
     */
    public boolean isOverlapping(TimestampedPeriod period) {
        return exercises.stream().anyMatch(period::overlaps);
    }

    /**
     * Add a new exercise
     * This will prevent exercises that overlap with previously added
     * exercises from being added
     * @param exercise the exercise to add
     * @return true if the exercise was added, otherwise false
     */
    public boolean addExercise(Exercise exercise) {
        if (isOverlapping(exercise)) {
            return false;
        }
        return exercises.add(exercise);
    }

    /**
     * @return all the exercises
     */
    public List<Exercise> getExercises() {
        return exercises;
    }

    /**
     * @return a set of all days in which an exercise occured
     */
    public Set<LocalDate> getExerciseDays() {
        return exercises.stream()
                        .map(Exercise::getStartTimestamp)
                        .map(LocalDateTime::toLocalDate)
                        .collect(Collectors.toSet());
    }

    /**
     * Get the exercises started on a date
     * @param date the date
     * @return a list of exercises started on the date
     */
    public List<Exercise> getExercises(LocalDate date) {
        return exercises.stream()
                        .filter(exercise -> exercise.getStartTimestamp().toLocalDate().equals(date))
                        .collect(Collectors.toList());
    }

    /**
     * @return the total calories burnt in total
     */
    public int getTotalCaloriesBurnt() {
        return exercises.stream()
                        .map(exercise -> exercise.getCaloriesBurnt(person)) // Can't use a method reference since it's abstract
                        .reduce(Integer::sum)
                        .orElse(0);
    }

    /**
     * @param date the date
     * @return the total calories burnt in total
     */
    public int getTotalCaloriesBurnt(LocalDate date) {
        return getExercises(date).stream()
                                 .map(exercise -> exercise.getCaloriesBurnt(person))
                                 .reduce(Integer::sum)
                                 .orElse(0);
    }

    /**
     * @return the total time spent exercising
     */
    public Duration getTotalExerciseDuration() {
        return getExercises().stream()
                             .map(Exercise::getDuration)
                             .reduce(Duration.ZERO, Duration::plus);
    }

    /**
     * Calculate the total time exercising on a day
     * @param date the day
     * @return the total time spent exercising on a day
     */
    public Duration getTotalExerciseDuration(LocalDate date) {
        return getExercises(date).stream()
                                 .map(Exercise::getDuration)
                                 .reduce(Duration.ZERO, Duration::plus);
    }

    /**
     * @return a list of all the foods consumed
     */
    public List<Food> getFoods() {
        return foods;
    }

    /**
     * @return a set of all days on which a food was consumed
     */
    public Set<LocalDate> getFoodDays() {
        return foods.stream()
                    .map(Food::getDate)
                    .collect(Collectors.toSet());
    }

    /**
     * Find all the foods consumed on a date
     * @param date the date
     * @return a list of all foods consumed on the date
     */
    public List<Food> getFoods(LocalDate date) {
        return foods.stream()
                    .filter(food -> food.getDate().equals(date))
                    .collect(Collectors.toList());
    }

    /**
     * @return the total calories consumed
     */
    public int getTotalCaloriesAte() {
        return getFoods().stream()
                         .mapToInt(entry -> entry.getQuantity() * entry.getCalories())
                         .sum();
    }

    /**
     * Calcualtae the total calories consumed on a date
     * @param date the date
     * @return the total calories consumed on a date
     */
    public int getTotalCaloriesAte(LocalDate date) {
        return getFoods(date).stream()
                             .mapToInt(entry -> entry.getQuantity() * entry.getCalories())
                             .sum();
    }

    /**
     * Get a summery for each day
     * @param executor the executor service to use
     * @return a CompleteableFuture for a list of DaySummary's
     */
    public CompletableFuture<List<DaySummary>> summarizeByDay(ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            Set<LocalDate> allDates = getExerciseDays();
            allDates.addAll(getFoodDays());
            List<DaySummary> summaries = allDates.stream().map(date -> {
                int caloriesBurnt = getTotalCaloriesBurnt(date);
                int caloriesConsumed = getTotalCaloriesAte(date);
                int numFoodsConsumed = getFoods(date).size();
                Duration totalExerciseDuration = getTotalExerciseDuration(date);
                
                return new DaySummary(date, caloriesBurnt, caloriesConsumed, numFoodsConsumed, totalExerciseDuration);
            }).collect(Collectors.toList());
            return summaries;
        }, executor);
    }
}
