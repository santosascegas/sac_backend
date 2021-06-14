package com.sac.backend.services;

import com.sac.backend.interfaces.DatasRepository;
import com.sac.backend.interfaces.ServiceInterface;
import com.sac.backend.models.Datas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DatasService implements ServiceInterface<Datas> {

    @Autowired
    private DatasRepository datasRepository;

    @Override
    public Datas create(Datas obj) {
        datasRepository.save(obj);
        return obj;
    }

    public void deleteData(Long id) {
        datasRepository.deleteById(id);
    }

    @Override
    public Datas findById(Long id) {
        Optional<Datas> _datas = datasRepository.findById(id);
        return _datas.orElse(null);
    }

    @Override
    public List<Datas> findAll() {
        List<Datas> datas = new ArrayList<Datas>();
        datasRepository.findAll().forEach(datas::add);

        return datas;
    }

    @Override
    public boolean update(Datas obj) {
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
