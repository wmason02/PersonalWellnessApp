package fitnessapp;

//Importing all for testing purposes
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import fitnessapp.account.Account;
import fitnessapp.mental.MentalHealth;
import fitnessapp.mental.Quality;
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

public class TestingDriver {
    public static void main(String[] args) throws Exception {

    //Person 
    Person person = new Person();
    person.setFullName("Amber C");
    person.setBirthday(LocalDate.of(2003, 3, 17));
    person.setWeight(65);
    person.setGender(Gender.FEMALE);
    person.setHeightCM(177.8f);
    
    //Exercise

    //Person and Exercise

    //Data

    //Serialization
    Account account = new Account("username", "password");
    account.save();

    Person person1 = Account.retrieveAccount("username", "password").getPerson();
    System.out.print(person1.getFullName());

    }
}
