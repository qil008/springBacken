package com.julian.springbacken.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.julian.springbacken.Entity.RideEntity;
import com.julian.springbacken.Entity.TrackEntity;
import com.julian.springbacken.Entity.UserEntity;
import com.julian.springbacken.location.location;
import com.julian.springbacken.mapper.TrackMapper;
import com.julian.springbacken.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TrackServiceImpl extends ServiceImpl<TrackMapper, TrackEntity> implements TrackService {

    @Autowired
    private RideService rideService;

    public int putTrack(Long rid, Double lng, Double lat, Double speed, Double asl){
        TrackEntity track = new TrackEntity();
        track.setRid(rid);
        track.setLng(lng);
        track.setLat(lat);
        track.setSpeed(speed);
        track.setAsl(asl);
        try{
            save(track);
            return 0;
        } catch(Exception e) {
            log.info(e.getMessage());
            return 1;
        }
    }
    public List<TrackEntity> getTrack(Long rid){
        return lambdaQuery().eq(TrackEntity::getRid, rid)
                .orderByAsc(TrackEntity::getCreateTime).list();
    }

    public static double calculateDistanceWithElevation(double lat1, double lng1, double alt1,
                                                        double lat2, double lng2, double alt2) {
        final int R = 6371; // 地球半径，单位千米

        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);
        double altDistance = alt2 - alt1; // 海拔差异

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double horizontalDistance = R * c; // 水平距离

        return Math.sqrt(horizontalDistance * horizontalDistance + altDistance * altDistance);
    }

    public static double calculateTotalDistanceWithElevation(List<TrackEntity> tracks) {
        double totalDistance = 0;
        for (int i = 0; i < tracks.size() - 1; i++) {
            TrackEntity start = tracks.get(i);
            TrackEntity end = tracks.get(i + 1);
            totalDistance += calculateDistanceWithElevation(
                    start.getLat(), start.getLng(), start.getAsl(),
                    end.getLat(), end.getLng(), end.getAsl()
            );
        }
        return totalDistance;
    }


    public ApiResponse updateDistanceTraveled(Long rid){
        List<TrackEntity> tracks = getTrack(rid);
        Double distance = calculateTotalDistanceWithElevation(tracks);
        try{
            rideService.updateRide(rid,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    distance,
                    null);
            return new ApiResponse("0", "Success");
        } catch (Exception e){
            log.info(e.getMessage());
            return new ApiResponse("500", "Server error" + e.getMessage());
        }
    }
}
