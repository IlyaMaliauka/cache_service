package benchmarks;

import algorithms.BinarySearch;
import algorithms.InsertionSort;
import org.junit.Before;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class SearchBenchmarks {

    private int[] array = new int[500];
    private int valueToFind;

    @Before
    public void initArray() {
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        Random random = new Random();
        valueToFind = random.nextInt(500);
    }

    @Test
    public void runBenchmarks() throws Exception {
        Options options = new OptionsBuilder()
                .include(this.getClass().getName() + ".*")
                .mode(Mode.AverageTime)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(1)
                .threads(1)
                .measurementIterations(5)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();

        new Runner(options).run();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void iterativeSearchBench() {
        BinarySearch.iterativeBinarySearch(array, valueToFind);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void recursiveSearchBench() {
        BinarySearch.recursiveBinarySearch(array, array[0], array[array.length-1], valueToFind);
    }
}