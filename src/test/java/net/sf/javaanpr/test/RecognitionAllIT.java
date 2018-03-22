package net.sf.javaanpr.test;

import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.internal.matchers.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Modified with Hamcrest library
 */
@RunWith(Parameterized.class)
public class RecognitionAllIT {

    private File file;

    @Parameterized.Parameters(name = "Plate {0}")
    public static File[] plates() {
        String snapshotDirPath = "src/test/resources/snapshots";
        File snapshotDir = new File(snapshotDirPath);
        assertThat(snapshotDir, notNullValue());
        return snapshotDir.listFiles();
    }

    public RecognitionAllIT(File file){
        this.file = file;
    }

    @Test
    public void testAllSnapshotsVersionTwo() throws Exception {
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
