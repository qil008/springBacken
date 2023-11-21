package com.julian.springbacken.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.julian.springbacken.Entity.TrackEntity;
import com.julian.springbacken.location.location;
import com.julian.springbacken.response.ApiResponse;

import java.util.List;

public interface TrackService extends IService<TrackEntity> {
    int putTrack(Long rid, Double lng, Double lat, Double speed, Double asl);
    List<TrackEntity> getTrack(Long rid);
    ApiResponse updateDistanceTraveled(Long rid);
//    calculateDistanceWithElevation(double lat1, double lng1, double alt1,
//                                          double lat2, double lng2, double alt2);
//
//    double calculateTotalDistance(List<TrackEntity> tracks);

}
