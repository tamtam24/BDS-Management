package com.javaweb.repository;

import com.javaweb.entity.RentAreaEntity;
import com.javaweb.repository.custom.RentAreaReepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentAreaRepository extends JpaRepository<RentAreaEntity,Long>, RentAreaReepositoryCustom {

}
