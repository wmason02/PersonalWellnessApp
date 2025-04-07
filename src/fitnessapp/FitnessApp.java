package fitnessapp;

import fitnessapp.account.Account;
import fitnessapp.fileio.CSVData;
import fitnessapp.mental.MentalHealth;
import fitnessapp.mental.Quality;
import fitnessapp.mental.SleepLogEntry;
import fitnessapp.physical.PhysicalHealth;
import fitnessapp.physical.exercise.Exercise;
import fitnessapp.physical.exercise.Incline;
import fitnessapp.physical.exercise.Intensity;
import fitnessapp.physical.exercise.calisthenics.WeightLiftingExercise;
import fitnessapp.physical.exercise.cardio.CardioExercise;
import fitnessapp.physical.exercise.cardio.CyclingExercise;
import fitnessapp.physical.exercise.cardio.WalkingExercise;
import fitnessapp.physical.foodstuff.Food;
import fitnessapp.physical.foodstuff.MealType;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class FitnessApp {
    public static void main(String[] args) throws Exception {
        Object o = CSVData.getExercisesStats(); // Make sure CSV Data loads first in logs
        Person person = createPerson();
        printPerson(person);

        System.out.println("Saving person");
        Account account = new Account("will", "test");
        account.setPerson(person);
        account.save();

        Person person1 = Account.retrieveAccount("username", "password").getPerson();

        System.out.println("Printing loaded person");
        printPerson(person1);

        Account account2 = Account.retrieveAccount("username", "wrongpassword");
        Account account3 = Account.retrieveAccount("wrongusername", "whatever");
    }

    private static Person createPerson() {
        Person person = new Person();
        person.setFullName("Will M");
        person.setBirthday(LocalDate.of(2003, 3, 17));
        person.setWeight(65);
        person.setGender(Gender.FEMALE);
        person.setHeightCM(177.8f);

        PhysicalHealth physicalHealth = person.getPhysicalHealth();
        
        WalkingExercise exercise = new WalkingExercise();
        exercise.setStartTimestamp(LocalDateTime.of(2022, 3, 8, 13, 39, 0));
        exercise.setDistance(1335.756);
        exercise.setDuration(Duration.ofMinutes(16));
        exercise.setIncline(Incline.NONE);
        exercise.setIntensity(Intensity.LOW);
        physicalHealth.addExercise(exercise);

        WalkingExercise exercise2 = new WalkingExercise();
        exercise2.setStartTimestamp(LocalDateTime.of(2022, 3, 10, 13, 44, 0));
        exercise2.setDistance(1367.942);
        exercise2.setDuration(Duration.ofMinutes(16));
        exercise2.setIncline(Incline.NONE);
        exercise2.setIntensity(Intensity.LOW);
        physicalHealth.addExercise(exercise2);

        CyclingExercise exercise3 = new CyclingExercise();
        exercise3.setStartTimestamp(LocalDateTime.of(2020, 4, 22, 19, 22, 0));
        exercise3.setDistance(3443.996);
        exercise3.setDuration(Duration.ofMinutes(32));
        exercise3.setIncline(Incline.NONE);
        exercise3.setIntensity(Intensity.LOW);
        physicalHealth.addExercise(exercise3);

        WalkingExercise exercise4 = new WalkingExercise();
        exercise4.setStartTimestamp(LocalDateTime.of(2020, 4, 22, 19, 22+31, 0));
        exercise4.setDistance(1335.756);
        exercise4.setDuration(Duration.ofMinutes(16));
        exercise4.setIncline(Incline.NONE);
        exercise4.setIntensity(Intensity.LOW);
        physicalHealth.addExercise(exercise4);

        WeightLiftingExercise exercise5 = new WeightLiftingExercise();
        exercise5.setStartTimestamp(LocalDateTime.of(2020, 4, 22, 20, 0, 0));
        exercise5.setWeight(4.5);
        exercise5.setDuration(Duration.ofMinutes(5));
        exercise5.setIntensity(Intensity.LOW);
        physicalHealth.addExercise(exercise5);

        Food painAuChocolat = new Food("Pain au chocolat", 237, "1 croissant");
        Food creamCheeseBagel = new Food("Bagel with cream cheese", 277 + 102, "1 bagel");
        Food bourbonChicken = new Food("Bourbon Chicken", 695, "1 box");
        Food hotDog = new Food("Hot Dog", 151, "1 hot dog");

        physicalHealth.getFoods().add(painAuChocolat.createEntry(LocalDate.of(2022, 3, 23), MealType.BREAKFAST, 1));
        physicalHealth.getFoods().add(CSVData.getFood("Egg").createEntry(LocalDate.of(2022, 3, 24), MealType.BREAKFAST, 2));
        physicalHealth.getFoods().add(painAuChocolat.createEntry(LocalDate.of(2022, 3, 24), MealType.BREAKFAST, 1));
        physicalHealth.getFoods().add(creamCheeseBagel.createEntry(LocalDate.of(2022, 3, 29), MealType.BREAKFAST, 1));
        physicalHealth.getFoods().add(bourbonChicken.createEntry(LocalDate.of(2022, 3, 29), MealType.DINNER, 1));
        physicalHealth.getFoods().add(hotDog.createEntry(LocalDate.of(2022, 4, 12), MealType.BREAKFAST, 2));
        physicalHealth.getFoods().add(CSVData.getFood("Egg").createEntry(LocalDate.of(2022, 4, 12), MealType.BREAKFAST, 1));

        MentalHealth mentalHealth = person.getMentalHealth();
        mentalHealth.putMood(LocalDate.of(2022, 3, 12), Quality.GOOD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 13), Quality.VERY_BAD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 14), Quality.BAD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 15), Quality.GOOD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 16), Quality.GOOD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 17), Quality.VERY_GOOD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 18), Quality.VERY_GOOD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 19), Quality.GOOD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 20), Quality.BAD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 21), Quality.VERY_BAD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 22), Quality.GOOD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 23), Quality.VERY_GOOD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 24), Quality.GOOD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 25), Quality.GOOD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 26), Quality.VERY_GOOD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 27), Quality.BAD);
        mentalHealth.putMood(LocalDate.of(2022, 3, 28), Quality.BAD);

        SleepLogEntry logEntry = new SleepLogEntry();
        logEntry.setQuality(Quality.BAD);
        logEntry.setStartTimestamp(LocalDateTime.of(2022, 4, 12, 8, 30, 00));
        logEntry.setEndTimestamp(LocalDateTime.of(2022, 4, 12, 11, 30, 00));
        mentalHealth.addSleepLogEntry(logEntry);

        return person;
    }

    private static String durationString(Duration duration) {
        return String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
    }

    private static void printCardioExercise(CardioExercise exercise, Person person) {
        System.out.println("Distance: " + exercise.getDistance() + " m");
        System.out.println("Avg. Speed: " + exercise.getAverageSpeed() + " m/s");
        System.out.println("Duration: " + durationString(exercise.getDuration()));
        System.out.println("Calories: " + exercise.getCaloriesBurnt(person) + " calories");
        System.out.println();
    }

    private static void printSleepEntry(SleepLogEntry entry) {
        System.out.println("Start timestamp: " + entry.getStartTimestamp());
        System.out.println("End timestamp: " + entry.getEndTimestamp());
        System.out.println("Quality: " + entry.getQuality());
    }

    private static void printPerson(Person person) {
        PhysicalHealth physicalHealth = person.getPhysicalHealth();
        for (Exercise e : physicalHealth.getExercises()) {
            if (e instanceof CardioExercise) {
                printCardioExercise((CardioExercise) e, person);
            }
        }
        
        physicalHealth.getExercises().stream()
                                     .map(e -> e.getStartTimestamp().toLocalDate())
                                     .sorted()
                                     .distinct()
                                     .forEach(date -> {
                                        System.out.println("Total calories burnt on " + date + ": " + physicalHealth.getTotalCaloriesBurnt(date) + " calories");
                                     });
        System.out.println("Total calories burnt: " + physicalHealth.getTotalCaloriesBurnt() + " calories");

        physicalHealth.getFoods().stream()
                                 .map(Food::getDate)
                                 .sorted()
                                 .distinct()
                                 .forEach(date -> {
                                    System.out.println("Total calories ate on " + date + ": " + physicalHealth.getTotalCaloriesAte(date) + " calories");
                                 });

        System.out.println("Average mood for March: " + person.getMentalHealth().getAverageMood(Month.MARCH, 2022));
        
        System.out.println();
        person.getMentalHealth().getSleepLogEntries().stream()
                                                     .sorted((a, b) -> a.getStartTimestamp().compareTo(b.getStartTimestamp()))
                                                     .forEach(FitnessApp::printSleepEntry);
    }
}
