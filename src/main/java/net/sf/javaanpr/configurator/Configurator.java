/*
 * Copyright 2013 JavaANPR contributors
 * Copyright 2006 Ondrej Martinsky
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package net.sf.javaanpr.configurator;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public final class Configurator {

    private static Configurator configurator;

    /**
     * Default name of the configuration file.
     */
    private String fileName = "config.xml";

    /**
     * Configuration file's comment.
     */
    private String comment = "This is the global configuration file for JavaANPR";

    /**
     * Primary property list containing values from the configuration file.
     */
    private Properties list;

    private Configurator() throws IOException {
        this.list = new Properties();

        this.setIntProperty("photo_adaptivethresholdingradius", 7); // 0 = not adaptive, 7 is recommended

        this.setDoubleProperty("bandgraph_peakfootconstant", 0.55); // 0.75
        this.setDoubleProperty("bandgraph_peakDiffMultiplicationConstant", 0.2);

        this.setIntProperty("carsnapshot_distributormargins", 25);
        this.setIntProperty("carsnapshot_graphrankfilter", 9);

        this.setDoubleProperty("carsnapshotgraph_peakfootconstant", 0.55); // 0.55
        this.setDoubleProperty("carsnapshotgraph_peakDiffMultiplicationConstant", 0.1);

        this.setIntProperty("intelligence_skewdetection", 0);

        // this.setDoubleProperty("char_contrastnormalizationconstant", 0.5); //1.0
        this.setIntProperty("char_normalizeddimensions_x", 8); // 8
        this.setIntProperty("char_normalizeddimensions_y", 13); // 13
        this.setIntProperty("char_resizeMethod", 1); // 0 = linear 1 = average
        this.setIntProperty("char_featuresExtractionMethod", 0); // 0 = map, 1 = edge
        this.setStrProperty("char_neuralNetworkPath", "/neuralnetworks/network_avgres_813_map.xml");
        this.setStrProperty("char_learnAlphabetPath", "/alphabets/alphabet_8x13");
        this.setIntProperty("intelligence_classification_method", 0); // 0 = pattern match, 1 = neural network

        this.setDoubleProperty("plategraph_peakfootconstant", 0.7); // urci sirku detekovanej medzery
        this.setDoubleProperty("plategraph_rel_minpeaksize", 0.86); // 0.85 (mensie cislo seka znaky, vacsie zase
        // nespravne zdruzuje)

        this.setDoubleProperty("platehorizontalgraph_peakfootconstant", 0.05);
        this.setIntProperty("platehorizontalgraph_detectionType", 1); // 0 = magnitude derivate, 1 = edge detection

        this.setDoubleProperty("plateverticalgraph_peakfootconstant", 0.42);

        this.setIntProperty("intelligence_numberOfBands", 3);
        this.setIntProperty("intelligence_numberOfPlates", 3);
        this.setIntProperty("intelligence_numberOfChars", 20);
        this.setIntProperty("intelligence_minimumChars", 5);
        this.setIntProperty("intelligence_maximumChars", 15);
        // plate heuristics
        this.setDoubleProperty("intelligence_maxCharWidthDispersion", 0.5); // in plate
        this.setDoubleProperty("intelligence_minPlateWidthHeightRatio", 0.5);
        this.setDoubleProperty("intelligence_maxPlateWidthHeightRatio", 15.0);
        // char heuristics
        this.setDoubleProperty("intelligence_minCharWidthHeightRatio", 0.1);
        this.setDoubleProperty("intelligence_maxCharWidthHeightRatio", 0.92);
        this.setDoubleProperty("intelligence_maxBrightnessCostDispersion", 0.161);
        this.setDoubleProperty("intelligence_maxContrastCostDispersion", 0.1);
        this.setDoubleProperty("intelligence_maxHueCostDispersion", 0.145);
        this.setDoubleProperty("intelligence_maxSaturationCostDispersion", 0.24); // 0.15
        this.setDoubleProperty("intelligence_maxHeightCostDispersion", 0.2);
        this.setDoubleProperty("intelligence_maxSimilarityCostDispersion", 100);
        // recognition
        this.setIntProperty("intelligence_syntaxanalysis", 2);
        this.setStrProperty("intelligence_syntaxDescriptionFile", "/syntax.xml");

        this.setIntProperty("neural_maxk", 8000); // maximum K - maximalny pocet iteracii
        this.setDoubleProperty("neural_eps", 0.07); // epsilon - pozadovana presnost
        this.setDoubleProperty("neural_lambda", 0.05); // lambda factor - rychlost ucenia, velkost gradientu
        this.setDoubleProperty("neural_micro", 0.5); // micro - momentovy clen pre prekonavanie lokalnych extremov
        // top(log(m recognized units)) = 6
        this.setIntProperty("neural_topology", 20); // topologia strednej vrstvy

        this.setStrProperty("help_file_help", "/help/help.html");
        this.setStrProperty("help_file_about", "/help/about.html");
        this.setStrProperty("reportgeneratorcss", "/reportgenerator/style.css");

        InputStream is = this.getResourceAsStream(this.fileName);
        if (is != null) {
            this.loadConfiguration(is);
            is.close();
        }
        Configurator.configurator = this;
    }

    public static synchronized Configurator getConfigurator() {
        if (configurator == null) {
            try {
                configurator = new Configurator();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return configurator;
    }

    public String getConfigurationFileName() {
        return this.fileName;
    }

    public void setConfigurationFileName(String name) {
        this.fileName = name;
    }

    public String getStrProperty(String name) {
        return this.list.getProperty(name).toString();
    }

    public String getPathProperty(String name) {
        return this.getStrProperty(name).replace('/', File.separatorChar);

    }

    public void setStrProperty(String name, String value) {
        this.list.setProperty(name, value);
    }

    public int getIntProperty(String name) throws NumberFormatException {
        return Integer.decode(this.list.getProperty(name));
    }

    public void setIntProperty(String name, int value) {
        this.list.setProperty(name, String.valueOf(value));
    }

    public double getDoubleProperty(String name) throws NumberFormatException {
        return Double.parseDouble(this.list.getProperty(name));
    }

    public void setDoubleProperty(String name, double value) {
        this.list.setProperty(name, String.valueOf(value));
    }

    public Color getColorProperty(String name) {
        return new Color(Integer.decode(this.list.getProperty(name)));
    }

    public void setColorProperty(String name, Color value) {
        this.list.setProperty(name, String.valueOf(value.getRGB()));
    }

    public void saveConfiguration() throws IOException {
        this.saveConfiguration(this.fileName);
    }

    public void saveConfiguration(String arg_file) throws IOException {
        FileOutputStream os = new FileOutputStream(arg_file);
        this.list.storeToXML(os, this.comment);
        os.close();
    }

    public void loadConfiguration() throws IOException {
        this.loadConfiguration(this.fileName);
    }

    public void loadConfiguration(String arg_file) throws IOException {
        InputStream is = this.getResourceAsStream(arg_file);
        this.loadConfiguration(is);
        is.close();
    }

    public void loadConfiguration(InputStream arg_stream) throws IOException {
        if (arg_stream == null) {
            this.list = null;
            return;
        }
        this.list.loadFromXML(arg_stream);
    }

    public InputStream getResourceAsStream(String filename) {
        String corrected = filename;
        URL f = this.getClass().getResource(corrected);
        if (f != null) {
            return this.getClass().getResourceAsStream(corrected);
        }

        if (filename.startsWith("/")) {
            corrected = filename.substring(1);
        } else if (filename.startsWith("./")) {
            corrected = filename.substring(2);
        } else {
            corrected = "/" + filename;
        }

        f = this.getClass().getResource(corrected);
        if (f != null) {
            return this.getClass().getResourceAsStream(corrected);
        }

        // Should actually load filename. It is here for the GUI. Loading images exactly from specified filesystem path
        File file = new File(filename);
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return fis;
        }
        return null;
    }
}
