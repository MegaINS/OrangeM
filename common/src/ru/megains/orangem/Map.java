package ru.megains.orangem;

import ru.megains.orangem.noise.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Map {

    private JFrame frame;
    SpinnerModel octavesSpinner;
    SpinnerModel aSpinner;
    SpinnerModel persistenceSpinner;
    SpinnerModel mnogteSpinner;
    SpinnerModel pribSpinner;


    public static void main(String[] args) {
        new Map().createFrame(1000, 1000, 1, 125);
    }

    public MyMap myMap = null;

    private void createFrame(int width, int height, int cellSize, long seed) {
        frame = new JFrame("Perlin noise 2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        octavesSpinner = new SpinnerNumberModel(1, 0, 10, 1);
        aSpinner = new SpinnerNumberModel(10f, 0f, 2000f, 0.1);
        persistenceSpinner = new SpinnerNumberModel(0.5f, 0, 20, 0.1f);
        mnogteSpinner = new SpinnerNumberModel(255, 1, 1240, 1);

        pribSpinner = new SpinnerNumberModel(128, -1000, 1000, 1);


        JSpinner spinOctaves = new JSpinner(octavesSpinner);
        JSpinner spinA = new JSpinner(aSpinner);
        JSpinner spinPersistence = new JSpinner(persistenceSpinner);
        JSpinner spinMnogte = new JSpinner(mnogteSpinner);
        JSpinner spinPrib = new JSpinner(pribSpinner);


        spinOctaves.addChangeListener(new OctavesAction());
        spinA.addChangeListener(new OctavesAction());
        spinPersistence.addChangeListener(new OctavesAction());
        spinMnogte.addChangeListener(new OctavesAction());
        spinPrib.addChangeListener(new OctavesAction());

        JPanel contents = new JPanel();

        myMap = new MyMap(width, height, cellSize, seed);
        contents.add(spinA);
        contents.add(spinOctaves);
        contents.add(spinPersistence);
        contents.add(spinMnogte);
        contents.add(spinPrib);

        contents.add(myMap);


        frame.add(contents);
        //frame.add(myMap);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    class OctavesAction implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            // octaves = (int) octavesSpinner.getValue();
            z = (int) octavesSpinner.getValue();
            //a = (double) aSpinner.getValue();
            persistence = (double) persistenceSpinner.getValue();
            mnogt = (int) mnogteSpinner.getValue();
            prib = (int) pribSpinner.getValue();
            myMap.updateUI();
        }
    }

    int z = 0;

//    double a = 10;
//    int octaves = 1;
//    double persistence =0.5f;
//    int mnogt = 255;
//    int prib = 128;

    // double a = 5;
    int octaves = 1;
    double persistence = 0.8f;
    int mnogt = 64;
    int prib = 16;

    private class MyMap extends JPanel {
        private int[][] map;
        private int[][] mapT;
        private int widthInCell;
        private int heightInCell;
        private int cellSize;
        private long seed;
        Perlin2D perlin;
        NoiseGeneratorOctaves generatorPerlin;
        NoiseGeneratorOctaves generatorPerlin1;
        NoiseGeneratorOctaves generatorPerlin2;
        NoiseGeneratorOctaves generatorPerlin3;

        MyMap(int width, int height, int cellSize, long seed) {
            setLayout(null);
            setPreferredSize(new Dimension(width, height));

            widthInCell = width / cellSize;
            heightInCell = height / cellSize;
            this.cellSize = cellSize;
            this.seed = seed;
            map = new int[widthInCell][heightInCell];
            mapT = new int[widthInCell][heightInCell];
            perlin = new Perlin2D(seed);
            generatorPerlin = new NoiseGeneratorOctaves(new Random(10), 16);
            generatorPerlin1 = new NoiseGeneratorOctaves(new Random(10), 16);
            generatorPerlin2 = new NoiseGeneratorOctaves(new Random(10), 16);
            generatorPerlin3 = new NoiseGeneratorOctaves(new Random(10), 16);
            int xIn = 0;
            int yIn = 0;
            int zIn = 0;

            a = this.generatorPerlin.generateNoiseOctaves(a1, xIn, zIn, 1000, 1000, 200.0D, 200.0D, 0.5D);
            //  a1  = this.generatorPerlin1.generateNoiseOctaves(a2, xIn, yIn, zIn, 1000, 10, 1000, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
            //  a2  =   generatorPerlin2.generateNoiseOctaves(a3, xIn, yIn, zIn, dX, dY, dZ, 684.412D, 684.412D, 684.412D);


            //  a3 =   generatorPerlin3.generateNoiseOctaves(a3, xIn, yIn, zIn, dX, dY, dZ, 684.412D, 684.412D, 684.412D);
        }

        public  double denormalizeClamp(double p_151238_0_, double p_151238_2_, double p_151238_4_) {
            return p_151238_4_ < 0.0D ? p_151238_0_ : (p_151238_4_ > 1.0D ? p_151238_2_ : p_151238_0_ + (p_151238_2_ - p_151238_0_) * p_151238_4_);
        }

        int dX = 1000;
        int dY = 10;
        int dZ = 1000;
        double[] a = null;
        double[] a1 = null;
        double[] a2 = null;
        double[] a3 = null;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;


//            for (int x = 0; x < widthInCell; x++) {
//                for (int y = 0; y < heightInCell; y++) {
//                    float value = perlin.getNoise(x / a, y / a, octaves, persistence);//- perlin.getNoise(x / (a), y / (a), 1, persistence);
//
//
//                    map[x][y] = (int) (value *mnogt+prib) & 255;
//
////                    if(value<min){
////                        min = value;
////                    }
////                    if(value>max){
////                        max = value;
////                    }
//                }
//            }
//            for (int x = 0; x < widthInCell; x++) {
//                for (int y = 0; y < heightInCell; y++) {
//                    double value = generatorPerlin.noise(x / a, y / a);
//                  //  double value =  perlin.getNoise(x / a, y / a, octaves, persistence);//- perlin.getNoise(x / (a), y / (a), 1, persistence);
//
//                    mapT[x][y] = (int) (value *mnogt+prib);// & 255;
//
//                    if(value<min){
//                        min = value;
//                    }
//                    if(value>max){
//                        max = value;
//                    }
//
//                }
//            }


            System.out.println(min);
            System.out.println(max);
            for (int x = 0; x < widthInCell - 1; ++x) {
                for (int y = 0; y < heightInCell - 1; ++y) {
                    Rectangle2D cell = new Rectangle2D.Float(x * cellSize, y * cellSize,
                            cellSize, cellSize);

                    Color color;

//                    if(mapT[x][y]<128){
//                        if (map[x][y]<64){
//                            color = Color.BLACK;
//                        }else if(map[x][y]<128) {
//                            color = Color.GREEN;
//                        }else if(map[x][y]<192) {
//                            color = Color.ORANGE;
//                        }else {
//                            color = Color.WHITE;
//                        }
//                    }else{
//                        if (map[x][y]<64){
//                            color = Color.BLUE;
//                        }else if(map[x][y]<128) {
//                            color = Color.MAGENTA;
//                        }else if(map[x][y]<192) {
//                            color = Color.CYAN;
//                        }else {
//                            color = Color.GRAY;
//                        }
//                    }

//                    if(a[x]>46){
//                        color = Color.BLACK;
//                    }else{
//                        color = Color.WHITE;
//                    }


                    //g2d.setColor(new Color(mapT[x][y],mapT[x][y],mapT[x][y]));
                    //   int var12 = (x*dX+y)*dY+z;
                    // double var34 = a1[var12] / 512.0D;
                    // double var36 = a2[var12] / 512.0D;
                    //   double var38 = (a3[var12] / 10.0D + 1.0D) / 2.0D;

                    // double var40 = MathHelper.denormalizeClamp(var34, var36, var38);

                    double var40 = a[x * dX + y];
                    if (var40 < min) {
                        min = var40;
                    }
                    if (var40 > max) {
                        max = var40;
                    }





                g2d.setColor(new Color((int)Math.abs(var40/500)&255 ,(int)Math.abs(var40/200)&255,(int)Math.abs(var40/50)&255));
                g2d.fill(cell);
            }
        }
            System.out.println(min);
            System.out.println(max);
    }

}

}