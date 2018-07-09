package it.si2001.Employee.service;

import it.si2001.Employee.model.MaritalStatus;
import it.si2001.Employee.repository.MaritalStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("maritalStatusService")
public class MaritalStatusServiceImpl implements MaritalStatusService {

    @Autowired
    MaritalStatusRepository maritalStatusRepository;



    @Override
    public List<MaritalStatus> getAllMaritalStatus() {

        return maritalStatusRepository.findAll();
    }
}
