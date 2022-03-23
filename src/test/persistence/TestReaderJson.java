package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

// This class references code from this CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class TestReaderJson extends TestJson {

    @Test
    void testReaderInvalidFileName() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");
        try {
            Workout testWorkout = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderWorkout() {
        JsonReader reader = new JsonReader("./data/testReader.json");
        try {
            Workout readWorkout = reader.read();

            Exercise readSquat = readWorkout.getExercises().get(0);
            Exercise readBenchPress = readWorkout.getExercises().get(1);
            Exercise readDeadlift = readWorkout.getExercises().get(2);
            Exercise readExercise = readWorkout.getExercises().get(3);

            Set readSquatSet1 = readSquat.getSets().get(0);

            Rep readSquatSet1Rep1 = readSquatSet1.getReps().get(0);
            Rep readSquatSet1Rep2 = readSquatSet1.getReps().get(1);

            checkWorkout("Workout A", 4, readWorkout);
            checkExercise("class model.Squat", "Competition squat", 185, 2, readSquat);
            checkExercise("class model.BenchPress", "Competition bench press", 135, 1, readBenchPress);
            checkExercise("class model.Deadlift", "Competition deadlift", 225, 1, readDeadlift);
            checkExercise("class model.Exercise", "Cable row", 45, 0, readExercise);

            checkSet(1, 2, readSquatSet1);
            checkRep(1, 1, readSquatSet1Rep1);
            checkRep(2, -1, readSquatSet1Rep2);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}