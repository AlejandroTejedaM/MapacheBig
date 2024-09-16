package com.barber.appointment.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.barber.appointment.Model.Citas;

@Repository
public interface CitasRepository extends CrudRepository<Citas, Long>{
    
}
