package com.javaweb.service.impl;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.converter.BuildingSearchBuilderConverter;
import com.javaweb.entity.AssignmentBuildingEntity;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.repository.AssignmentBuildingRepository;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BuildingService implements IBuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuildingDTOConverter buildingDTOConverter;

    @Autowired
    private BuildingSearchBuilderConverter buildingSearchBuilderConverter;

    @Autowired
    private AssignmentBuildingRepository assignmentBuildingRepository;



    @Override
    public ResponseDTO listStaffs (Long buildingId){
        BuildingEntity building= buildingRepository.findById(buildingId).get();
        List<UserEntity>staffs= userRepository.findByStatusAndRoles_Code(1,"STAFF");
        List<UserEntity> staffAssignment = userRepository.findUsersByBuilding(building);
//        List<UserEntity>staffAssignment= building.getUserEntities();
        List<StaffResponseDTO>staffResponseDTOS=new ArrayList<>();
        ResponseDTO responseDTO= new ResponseDTO();
        for(UserEntity it: staffs){
            StaffResponseDTO staffResponseDTO= new StaffResponseDTO();
            staffResponseDTO.setFullName(it.getFullName());
            staffResponseDTO.setStaffId(it.getId());
            if(staffAssignment.contains(it)){
                staffResponseDTO.setChecked("checked");
            }
            else{
                staffResponseDTO.setChecked("");
            }
            staffResponseDTOS.add(staffResponseDTO);
        }
        responseDTO.setData(staffResponseDTOS);
        responseDTO.setMessage("success");
        return  responseDTO;

    }

    @Override
    public List<BuildingSearchResponse> findAll(BuildingSearchRequest buildingSearchRequest) {
//        BuildingSearchBuilder buildingSearchBuilder=buildingSearchBuilderConverter.toBuildingSearchBuilder(params, typeCode);
        List<BuildingEntity> buildingEntities = buildingRepository.findAllBuilding(buildingSearchRequest);
        List<BuildingSearchResponse> result = new ArrayList<BuildingSearchResponse>();
        for (BuildingEntity item : buildingEntities) {
            BuildingSearchResponse building = buildingDTOConverter.toBuildingResponce(item);
            result.add(building);
        }
        return result;
    }

    @Override
    public void deleteBuildings (List<Long>ids){
        for(Long id:ids){

            buildingRepository.deleteRentAreaByBuildingId(id);
            buildingRepository.deleteAssignmentByBuildingId(id);
            buildingRepository.deleteById(id);


        }

    }

    @Override
    public void addOrUpdateBuilding(BuildingDTO buildingDTO ){
        //update building

        if(buildingDTO.getId()!=null){
            BuildingEntity existingBuilding = buildingRepository.findById(buildingDTO.getId()).get();
            BuildingEntity updatedBuilding = buildingDTOConverter.toBuildingEntity(buildingDTO);
            if(updatedBuilding.getName()!=null){
                existingBuilding.setName(updatedBuilding.getName());
            }
            if(updatedBuilding.getFloorarea()!=null){
                existingBuilding.setFloorarea(updatedBuilding.getFloorarea());

            }
            if(updatedBuilding.getNumberofbasement() != 0){
                existingBuilding.setNumberofbasement(updatedBuilding.getNumberofbasement());
            }
            if(updatedBuilding.getDirection() != null){
                existingBuilding.setDirection(updatedBuilding.getDirection());
            }
            if(updatedBuilding.getLevel() != null){
                existingBuilding.setLevel(updatedBuilding.getLevel());
            }
            if(updatedBuilding.getRentprice() != null){
                existingBuilding.setRentprice(updatedBuilding.getRentprice());
            }
            if(updatedBuilding.getManagername() != null){
                existingBuilding.setManagername(updatedBuilding.getManagername());
            }
            if(updatedBuilding.getManagerphone() != null){
                existingBuilding.setManagerphone(updatedBuilding.getManagerphone());
            }
            if(updatedBuilding.getType() != null){
                existingBuilding.setType(updatedBuilding.getType());
            }

            buildingRepository.save(existingBuilding);
        }
        //adding new building
        else {
            BuildingEntity newBuilding= buildingDTOConverter.toBuildingEntity(buildingDTO);
            buildingRepository.save(newBuilding);
            System.out.println("oke here");
        }



    }
    public BuildingDTO findBuildingById(Long id){
        BuildingEntity buildingEntity = buildingRepository.findById(id).get();
        BuildingDTO buildingDTO = buildingDTOConverter.toBuildingDTO(buildingEntity);


        return buildingDTO;
    }



}
