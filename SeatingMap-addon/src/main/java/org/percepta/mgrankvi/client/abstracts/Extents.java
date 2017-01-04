package org.percepta.mgrankvi.client.abstracts;

import org.percepta.mgrankvi.client.geometry.Line;

import java.util.List;

/**
 * Created by Mikael on 04/01/17.
 */
public class Extents {

    private Double minX, maxX, minY, maxY;

    public Extents(List<Line> lines) {
        minX = minY = Double.MAX_VALUE;
        maxX = maxY = Double.MIN_VALUE;
        for(Line line: lines) {
            if(line.start.getX() < minX) minX = line.start.getX();
            if(line.start.getX() > maxX) maxX = line.start.getX();
            if(line.start.getY() < minY) minY = line.start.getY();
            if(line.start.getY() > maxY) maxY = line.start.getY();

            if(line.end.getX() < minX) minX = line.end.getX();
            if(line.end.getX() > maxX) maxX = line.end.getX();
            if(line.end.getY() < minY) minY = line.end.getY();
            if(line.end.getY() > maxY) maxY = line.end.getY();
        }
    }

    public Double getMinX() {
        return minX;
    }

    public Double getMaxX() {
        return maxX;
    }

    public Double getMinY() {
        return minY;
    }

    public Double getMaxY() {
        return maxY;
    }
}
