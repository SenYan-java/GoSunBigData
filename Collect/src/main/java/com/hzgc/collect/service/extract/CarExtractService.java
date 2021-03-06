package com.hzgc.collect.service.extract;

import com.hzgc.common.util.basic.UuidUtil;
import com.hzgc.jniface.BigCarPictureData;
import com.hzgc.jniface.CarAttribute;
import com.hzgc.jniface.CarPictureData;
import com.hzgc.seemmo.bean.ImageResult;
import com.hzgc.seemmo.bean.carbean.Vehicle;
import com.hzgc.seemmo.service.ImageToData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CarExtractService {
    @Value("${seemmo.url}")
    private String seemmoUrl;

    /**
     * 特征提取
     *
     * @param imageBytes 图片数组（大图）
     * @return CarPictureData
     */
    public BigCarPictureData carExtractByImage(byte[] imageBytes) {
        ImageResult imageResult = ImageToData.getImageResult(seemmoUrl, imageBytes, "0");
        if (imageResult == null) {
            log.info("imageResult is null");
            return null;
        }
        BigCarPictureData bigCarPictureData = new BigCarPictureData();
        List <Vehicle> vehicleList = imageResult.getVehicleList();
        ArrayList <CarPictureData> smallImages = new ArrayList <>();
        if (vehicleList != null && vehicleList.size() > 0) {
            for (Vehicle vehicle : vehicleList) {
                if (vehicle != null) {
                    CarPictureData carPictureData = new CarPictureData();
                    CarAttribute carAttribute = new CarAttribute();
                    carAttribute.setVehicle_object_type(vehicle.getVehicle_object_type());
                    carAttribute.setBelt_maindriver(vehicle.getBelt_maindriver());
                    carAttribute.setBelt_codriver(vehicle.getBelt_codriver());
                    carAttribute.setBrand_name(vehicle.getBrand_name());
                    carAttribute.setCall_code(vehicle.getCall_code());
                    carAttribute.setVehicle_color(vehicle.getVehicle_color());
                    carAttribute.setCrash_code(vehicle.getCrash_code());
                    carAttribute.setDanger_code(vehicle.getDanger_code());
                    carAttribute.setMarker_code(vehicle.getMarker_code());
                    carAttribute.setPlate_schelter_code(vehicle.getPlate_schelter_code());
                    carAttribute.setPlate_flag_code(vehicle.getPlate_flag_code());
                    carAttribute.setPlate_licence(vehicle.getPlate_licence());
                    carAttribute.setPlate_destain_code(vehicle.getPlate_destain_code());
                    carAttribute.setPlate_color_code(vehicle.getPlate_color_code());
                    carAttribute.setPlate_type_code(vehicle.getPlate_type_code());
                    carAttribute.setRack_code(vehicle.getRack_code());
                    carAttribute.setSparetire_code(vehicle.getSparetire_code());
                    carAttribute.setMistake_code(vehicle.getMistake_code());
                    carAttribute.setSunroof_code(vehicle.getSunroof_code());
                    carAttribute.setVehicle_type(vehicle.getVehicle_type());
                    carAttribute.setVehicle_coordinate(vehicle.getVehicle_image());
                    carPictureData.setImageID(UuidUtil.getUuid());
                    carPictureData.setImageData(vehicle.getVehicle_data());
                    carPictureData.setFeature(carAttribute);
                    int[] vehicle_image = vehicle.getVehicle_image();
                    vehicle_image[2] = vehicle_image[0] + vehicle_image[2];
                    vehicle_image[3] = vehicle_image[1] + vehicle_image[3];
                    carPictureData.setImage_coordinate(vehicle.getVehicle_image());
                    smallImages.add(carPictureData);
                }
            }
        }
        bigCarPictureData.setSmallImages(smallImages);
        bigCarPictureData.setTotal(smallImages.size());
        bigCarPictureData.setImageType("car");
        bigCarPictureData.setImageData(imageBytes);
        bigCarPictureData.setImageID(UuidUtil.getUuid());
        return bigCarPictureData;
    }
}
