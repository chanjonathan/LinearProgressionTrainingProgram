package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

// This class references code from this CPSC210/JsonSerializationDemo
// Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class TestWriterJson extends TestJson {

    @Test
    void testWriterInvalidFile() {
        try {
            Workout workout = new Workout("Workout A");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterGeneralWorkout() {
        try {
            Workout workout = new Workout("Workout A");
            Exercise benchPress = new BenchPress("3 count paused bench press", 125);
            Exercise deadlift = new Deadlift("Stiff leg deadlift", 185);
            Exercise squat = new Squat("Pin squat", 200);
            Exercise exercise = new Exercise("Cable row", 45);

            Set benchSet1 = new Set(benchPress.getDescription(), 1);
            Set benchSet2 = new Set(benchPress.getDescription(), 2);

            Rep benchSet1Rep1 = new Rep(benchSet1.getExerciseDescription(), benchSet1.getNumber(), 1);
            Rep benchSet1Rep2 = new Rep(benchSet1.getExerciseDescription(), benchSet1.getNumber(), 2);
            Rep benchSet2Rep1 = new Rep(benchSet2.getExerciseDescription(), benchSet2.getNumber(), 1);
            Rep benchSet2Rep2 = new Rep(benchSet2.getExerciseDescription(), benchSet1.getNumber(), 2);

            benchSet1Rep1.complete();
            benchSet1Rep2.miss();
            benchSet2Rep1.complete();
            benchSet2Rep2.miss();


            Set deadliftSet1 = new Set(deadlift.getDescription(), 1);
            Set deadliftSet2 = new Set(deadlift.getDescription(), 2);

            Rep deadliftSet1Rep1 = new Rep(deadliftSet1.getExerciseDescription(), deadliftSet1.getNumber(), 1);
            Rep deadliftSet1Rep2 = new Rep(deadliftSet1.getExerciseDescription(), deadliftSet1.getNumber(), 2);
            Rep deadliftSet2Rep1 = new Rep(deadliftSet2.getExerciseDescription(), deadliftSet2.getNumber(), 1);
            Rep deadliftSet2Rep2 = new Rep(deadliftSet2.getExerciseDescription(), deadliftSet2.getNumber(), 2);

            deadliftSet2Rep1.complete();
            deadliftSet2Rep2.miss();

            workout.loadExercise(benchPress);
            workout.loadExercise(deadlift);
            workout.loadExercise(squat);
            workout.loadExercise(exercise);

            benchPress.loadSet(benchSet1);
            benchPress.loadSet(benchSet2);

            benchSet1.loadRep(benchSet1Rep1);
            benchSet1.loadRep(benchSet1Rep2);
            benchSet2.loadRep(benchSet2Rep1);
            benchSet2.loadRep(benchSet2Rep2);

            deadlift.loadSet(deadliftSet1);
            deadlift.loadSet(deadliftSet2);

            deadliftSet1.loadRep(deadliftSet1Rep1);
            deadliftSet1.loadRep(deadliftSet1Rep2);
            deadliftSet2.loadRep(deadliftSet2Rep1);
            deadliftSet2.loadRep(deadliftSet2Rep2);

            JsonWriter writer = new JsonWriter("./data/testWriter.json");
            writer.open();
            writer.write(workout);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriter.json");
            Workout readWorkout = reader.read();

            Exercise readBenchPress = readWorkout.getExercises().get(0);
            Exercise readDeadlift = readWorkout.getExercises().get(1);
            Exercise readSquat = readWorkout.getExercises().get(2);
            Exercise readExercise = readWorkout.getExercises().get(3);

            Set readBenchSet1 = readBenchPress.getSets().get(0);
            Set readBenchSet2 = readBenchPress.getSets().get(1);

            Rep readBenchSet1Rep1 = readBenchSet1.getReps().get(0);
            Rep readBenchSet1Rep2 = readBenchSet1.getReps().get(1);

            Rep readBenchSet2Rep1 = readBenchSet2.getReps().get(0);
            Rep readBenchSet2Rep2 = readBenchSet2.getReps().get(1);

            Set readDeadliftSet1 = readDeadlift.getSets().get(0);
            Set readDeadliftSet2 = readDeadlift.getSets().get(1);

            Rep readDeadliftSet1Rep1 = readDeadliftSet1.getReps().get(0);
            Rep readDeadliftSet1Rep2 = readDeadliftSet1.getReps().get(1);

            Rep readDeadliftSet2Rep1 = readDeadliftSet2.getReps().get(0);
            Rep readDeadliftSet2Rep2 = readDeadliftSet2.getReps().get(1);


            checkWorkout("Workout A", 4, readWorkout);

            checkExercise("class model.BenchPress", "3 count paused bench press", 125, 2, readBenchPress);
            checkExercise("class model.Deadlift", "Stiff leg deadlift", 185, 2, readDeadlift);
            checkExercise("class model.Squat", "Pin squat", 200, 0, readSquat);
            checkExercise("class model.Exercise", "Cable row", 45, 0, readExercise);


            checkSet(1, 2, readBenchSet1);
            checkSet(2, 2, readBenchSet2);

            checkRep(1, 1, readBenchSet1Rep1);
            checkRep(2, -1, readBenchSet1Rep2);
            checkRep(1, 1, readBenchSet2Rep1);
            checkRep(2, -1, readBenchSet2Rep2);

            checkSet(1, 2, readDeadliftSet1);
            checkSet(2, 2, readDeadliftSet2);

            checkRep(1, 0, readDeadliftSet1Rep1);
            checkRep(2, 0, readDeadliftSet1Rep2);
            checkRep(1, 1, readDeadliftSet2Rep1);
            checkRep(2, -1, readDeadliftSet2Rep2);

        } catch (IOException e) {
            fail("Exception not expected");
        }
    }
}