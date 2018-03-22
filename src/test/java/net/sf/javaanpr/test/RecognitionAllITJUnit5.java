package net.sf.javaanpr.test;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class RecognitionAllITJUnit5 {


    static File[] plates() {
        String snapshotDirPath = "src/test/resources/snapshots";
        File snapshotDir = new File(snapshotDirPath);
        assertThat(snapshotDir, notNullValue());
        return snapshotDir.listFiles();
    }

    // https://github.com/howtoprogram/junit5-examples/blob/master/junit5-dynamic-test-example/src/test/java/com/howtoprogram/junit5/TranslationEngineDynamicTest.java
    @TestFactory
    Stream<DynamicTest> spotPlates() {
        List<File> temp = Arrays.asList(plates());
        return temp.stream().map(data -> DynamicTest.dynamicTest("Test plates: " + data, () -> {
            testAllSnapshotsVersionTwo(data);
        }));
    }


    // WORKS, but generate below
    // java.lang.Exception: Method testAllSnapshotsVersionTwo should have no parameters
    @Test
    public void testAllSnapshotsVersionTwo(File file) throws Exception {
        String resultsPath = "src/test/resources/results.properties";
        InputStream resultsStream = new FileInputStream(new File(resultsPath));

        Properties properties = new Properties();
        properties.load(resultsStream);
        resultsStream.close();
        //assertTrue(properties.size() > 0);
        assertThat(properties.size(), greaterThan(0));


        CarSnapshot carSnap = new CarSnapshot(new FileInputStream(file));
        //assertNotNull("carSnap is null", carSnap);
        assertThat(carSnap, notNullValue());
        //assertNotNull("carSnap.image is null", carSnap.getImage());
        assertThat(carSnap.getImage(), notNullValue());
        String snapName = file.getName();
        String plateCorrect = properties.getProperty(snapName);
        //assertNotNull(plateCorrect);
        assertThat(plateCorrect, notNullValue());

        Intelligence intel = new Intelligence();

        String numberPlate = intel.recognize(carSnap, false);

        //assertEquals(numberPlate, plateCorrect);
        assertThat(numberPlate, equalToIgnoringWhiteSpace(plateCorrect));

        carSnap.close();
    }


}
