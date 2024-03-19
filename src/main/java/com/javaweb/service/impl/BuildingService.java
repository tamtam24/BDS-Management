package com.javaweb.service.impl;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.converter.BuildingSearchBuilderConverter;
import com.javaweb.entity.AssignmentBuildingEntity;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
//import com.javaweb.repository.AssignmentBuildingRepository;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

//    @Autowired
//    private AssignmentBuildingRepository assignmentBuildingRepository;

    @Autowired
    private RentAreaRepository rentAreaRepository;



    @Override
    public ResponseDTO listStaffs (Long buildingId){
        BuildingEntity building= buildingRepository.findById(buildingId).get();
        List<UserEntity>staffs= userRepository.findByStatusAndRoles_Code(1,"STAFF");
//        List<UserEntity> staffAssignment = userRepository.findUsersByBuilding(building);
        List<UserEntity>staffAssignment= building.getUserEntities();
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

//            buildingRepository.deleteRentAreaByBuildingId(id);
//            buildingRepository.deleteAssignmentByBuildingId(id);
            buildingRepository.deleteById(id);


        }

    }

    @Override
    @Transactional
    public void addOrUpdateBuilding(BuildingDTO buildingDTO ){
        //update building

        if(buildingDTO.getId()!=null){
            BuildingEntity existingBuilding = buildingRepository.findById(buildingDTO.getId()).get();
            BuildingEntity updatedBuilding = buildingDTOConverter.toBuildingEntity(buildingDTO);

            if(updatedBuilding.getName()!=null){
                existingBuilding.setName(updatedBuilding.getName());
            }
            if(updatedBuilding.getStreet()!=null){
                existingBuilding.setStreet(updatedBuilding.getStreet());
            }

            if(updatedBuilding.getDistrict()!=null){
                existingBuilding.setDistrict(updatedBuilding.getDistrict());
            }

            if(updatedBuilding.getWard()!=null){
                existingBuilding.setWard(updatedBuilding.getWard());
            }

            if(updatedBuilding.getStructure()!=null){
                existingBuilding.setStructure(updatedBuilding.getStructure());
            }

            if(updatedBuilding.getNumberofbasement() != 0){
                existingBuilding.setNumberofbasement(updatedBuilding.getNumberofbasement());
            }
            if(updatedBuilding.getFloorarea()!=null){
                existingBuilding.setFloorarea(updatedBuilding.getFloorarea());
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
            if(updatedBuilding.getRentpricedescription() != null){
                existingBuilding.setRentpricedescription(updatedBuilding.getRentpricedescription());
            }

            if (updatedBuilding.getServicefee() != null) {
                existingBuilding.setServicefee(updatedBuilding.getServicefee());
            }
            if (updatedBuilding.getCarfee() != null) {
                existingBuilding.setCarfee(updatedBuilding.getCarfee());
            }
            if (updatedBuilding.getMotofee() != null) {
                existingBuilding.setMotofee(updatedBuilding.getMotofee());
            }
            if (updatedBuilding.getOvertimefee() != null) {
                existingBuilding.setOvertimefee(updatedBuilding.getOvertimefee());
            }
            if (updatedBuilding.getElectricityfee() != null) {
                existingBuilding.setElectricityfee(updatedBuilding.getElectricityfee());
            }
            if (updatedBuilding.getDeposit() != null) {
                existingBuilding.setDeposit(updatedBuilding.getDeposit());
            }
            if (updatedBuilding.getPayment() != null) {
                existingBuilding.setPayment(updatedBuilding.getPayment());
            }
            if (updatedBuilding.getRenttime() != null) {
                existingBuilding.setRenttime(updatedBuilding.getRenttime());
            }
            if (updatedBuilding.getDecorationtime() != null) {
                existingBuilding.setDecorationtime(updatedBuilding.getDecorationtime());
            }
            if (updatedBuilding.getBrokeragefee() != null) {
                existingBuilding.setBrokeragefee(updatedBuilding.getBrokeragefee());
            }
            if (updatedBuilding.getNote() != null) {
                existingBuilding.setNote(updatedBuilding.getNote());
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
            if (updatedBuilding.getItems()!=null) {
                rentAreaRepository.deleteByBuildingId(existingBuilding.getId());
                existingBuilding.setItems(updatedBuilding.getItems());

                for (RentAreaEntity rentAreaEntity : existingBuilding.getItems()) {
                    rentAreaEntity.setBuilding(existingBuilding);
                    rentAreaRepository.save(rentAreaEntity);
                    System.out.println("sua rentArea oke");

                }

                buildingRepository.save(existingBuilding);
            }
        }
        //adding new building
        else {
            BuildingEntity newBuilding= buildingDTOConverter.toBuildingEntity(buildingDTO);
            buildingRepository.save(newBuilding);
            if (newBuilding.getItems()!=null) {
                for (RentAreaEntity rentAreaEntity : newBuilding.getItems()) {
                    rentAreaEntity.setBuilding(newBuilding);
                    rentAreaRepository.save(rentAreaEntity);
                    System.out.println("luu rentArea oke");
                }
            }
            System.out.println("oke here");
        }



    }
    public BuildingDTO findBuildingById(Long id){
        BuildingEntity buildingEntity = buildingRepository.findById(id).get();
        BuildingDTO buildingDTO = buildingDTOConverter.toBuildingDTO_forUpdateBuilding(buildingEntity);


        return buildingDTO;
    }



}
