package com.cwk.gps.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LEAVES
 * @Version 1.0
 * @Date: 2021年10月08日  10时55分37秒
 * @Description: 每个点的集合【一段轨迹】
 */
public class TrackSegment {

    private List<TrackPoint> points;

    public TrackSegment() {
        this.points = new ArrayList();
    }

    public TrackSegment(List<TrackPoint> points) {
        this.points = points;
    }

    public void addTrackPoint(TrackPoint point) {
        this.points.add(point);
    }

    public void removeTrackPoint(int index) {
        this.points.remove(index);
    }

    public void clearTrackPoints() {
        this.points.clear();
    }

    public List<TrackPoint> getPoints() {
        return this.points;
    }

    public void setPoints(List<TrackPoint> points) {
        this.points = points;
    }
}
