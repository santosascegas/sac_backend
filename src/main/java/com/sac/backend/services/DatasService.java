package com.sac.backend.services;

import com.sac.backend.interfaces.DatasRepository;
import com.sac.backend.interfaces.ServiceInterface;
import com.sac.backend.models.DatasModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Maurício Freire
 * Date 03/06/2021 at 18:15
 * Created on IntelliJ IDEA
 */

@Service
public class DatasService implements ServiceInterface<DatasModel> {

    @Autowired
    private DatasRepository datasRepository;

    @Override
    public DatasModel create(DatasModel obj) {
        datasRepository.save(obj);
        return obj;
    }

    @Override
    public DatasModel findByData(Date date) {
        Optional<DatasModel> _datas = datasRepository.findById(date);
        return _datas.orElse(null);
    }

    @Override
    public List<DatasModel> findAll() {
        return datasRepository.findAll();
    }

    @Override
    public boolean update(DatasModel obj) {
        if (datasRepository.existsById(obj.getData())) {
            datasRepository.save(obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    public List<DatasModel> findByDisponibilidade() {
        return datasRepository.findByDisponibilidade();
    }

    public List<DatasModel> findBySemana() {
        return datasRepository.findBySemana();
    }
}
