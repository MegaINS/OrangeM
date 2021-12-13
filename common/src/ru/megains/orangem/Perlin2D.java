package ru.megains.orangem;

import java.util.Random;
 
public class Perlin2D {
 
    private byte[] permutationTable;
 
    public Perlin2D(long seed) {
    Random random = new Random(seed);
    permutationTable = new byte[1024];
    random.nextBytes(permutationTable);
    }
 
    public float getNoise(double fx, double fy, int octaves, double persistence) {
        float max = 0; 
        float result = 0;
        double amplitude =1;
        while (octaves-- > 0)
        {
            max += amplitude;
            result += getNoise(fx,  fy) * amplitude;
            amplitude *= persistence;
            fx *= 2;
            fy *= 2;
        }
 
        return result/max;
    }
 
    public double getNoise(double x, double y) {
    int left = (int)x;
    int top = (int)y;

        double localX = x - left;
        double localY = y - top;
 
        // извлекаем градиентные векторы для всех вершин квадрата:
        Vector topLeftGradient     = getPseudoRandomGradientVector(left,   top  );
        Vector topRightGradient    = getPseudoRandomGradientVector(left+1, top  );
        Vector bottomLeftGradient  = getPseudoRandomGradientVector(left,   top+1);
        Vector bottomRightGradient = getPseudoRandomGradientVector(left+1, top+1);
 
     // вектора от вершин квадрата до точки внутри квадрата:
        Vector distanceToTopLeft     = new Vector(localX,   localY);
        Vector distanceToTopRight    = new Vector(localX-1, localY);
        Vector distanceToBottomLeft  = new Vector(localX,   localY-1);
        Vector distanceToBottomRight = new Vector(localX-1, localY-1);
 
    // считаем скалярные произведения между которыми будем интерполировать
        double tx1 = dot(distanceToTopLeft,     topLeftGradient);
        double tx2 = dot(distanceToTopRight,    topRightGradient);
        double bx1 = dot(distanceToBottomLeft,  bottomLeftGradient);
        double bx2 = dot(distanceToBottomRight, bottomRightGradient);
 
        // интерполяция:
        double tx = lerp(tx1, tx2, qunticCurve(localX));
        double bx = lerp(bx1, bx2, qunticCurve(localX));
        double tb = lerp(tx, bx, qunticCurve(localY));
 
    return tb;
    }
 
    private double lerp(double a, double b, double t) {
    return a + (b - a) * t;
    }
 
    private double dot(Vector a, Vector b) {
    return a.x * b.x + a.y * b.y;
    }
 
    private double qunticCurve(double t) {
    return t * t * t * (t * (t * 6 - 15) + 10);
    }
 
    private Vector getPseudoRandomGradientVector(int x, int y) {
    // псевдо-случайное число от 0 до 3 которое всегда неизменно при данных x и y
        int v = (int)(((x * 1836311903L) ^ (y * 2971215073L) + 4807526976L) & 1023L);
        v = permutationTable[v] & 3;
 
        switch (v) {
            case 0:  return new Vector(1, 0);
            case 1:  return new Vector(-1, 0);
            case 2:  return new Vector(0, 1);
            default: return new Vector(0,-1);
        }
    } 
 
 
 
    private static class Vector {

        double x;
        double y;
 
    Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
 
    }
 
}