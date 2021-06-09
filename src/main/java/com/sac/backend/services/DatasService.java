package com.sac.backend.services;

import com.sac.backend.interfaces.DatasRepository;
import com.sac.backend.interfaces.ServiceInterface;
import com.sac.backend.models.DatasModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DatasService implements ServiceInterface<DatasModel> {

    @Autowired
    private DatasRepository datasRepository;

    @Override
    public DatasModel create(DatasModel obj) {
        datasRepository.save(obj);
        return obj;
    }

    public void deleteData(Long id) {
        datasRepository.deleteById(id);
    }

    @Override
    public DatasModel findById(Long id) {
        Optional<DatasModel> _datas = datasRepository.findById(id);
        return _datas.orElse(null);
    }

    @Override
    public List<DatasModel> findAll() {
        List datas = new ArrayList<DatasModel>();
        datasRepository.findAll().forEach(datas::add);

        return datas;
    }

    @Override
    public boolean update(DatasModel obj) {
        if (datasRepository.existsById(obj.getId())) {
            datasRepository.save(obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        // TODO Auto-generated method stub
        return false;
    }

}
