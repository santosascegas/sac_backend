package com.sac.backend;

import com.sac.backend.models.Datas;
import com.sac.backend.services.DatasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Maurício Freire
 * Date 30/10/2021 at 15:54
 * Created on IntelliJ IDEA
 */

@SpringBootTest
public class BackendTeste {
    
    @Autowired
    DatasService datasService;
    
    @Test
    public void salvarDataPasseio() {
        Datas datas = new Datas(1L, "30/10/2021", 0);
        assertAll(() -> assertNotNull(datas.getId(), "O id não deve ser nulo."),
                () -> assertNotNull(datas.getData(), "A data não deve ser nula."),
                () -> assertTrue(datas.getStatus() >= 0 && datas.getStatus() <= 1,
                        "O status deve ser 0 ou 1."),
                () -> assertNotNull(datasService.create(datas),
                        "Erro ao salvar a data.")
        );
    }
    
    @Test
    public void procurarData() {
        datasService.create(new Datas(1L, "01/11/2021", 0));
        Optional<Datas> datas = datasService.findById(1L);
        
        assertNotNull(datas.get().getId(), "O id não foi encontrado.");
    }
    
    @Test
    public void procurarTodasAsDatas() {
        List<Datas> datasList = datasService.findAll();
        for (Datas d: datasList)
            assertTrue(d instanceof Datas, "Registro incorreto.");
    }
    
    @Test
    public void atualizarDatas() {
        Datas datas = new Datas(1L, "01/11/2021", 0);
        datasService.create(datas);
        Optional<Datas> atualizacao = datasService.findById(1L);
        atualizacao.ifPresent(value -> value.setStatus(1));
        assertAll("Atualização",
                () -> assertNotNull(atualizacao.get(), "Registro não encontrado."),
                () -> assertTrue(datasService.update(atualizacao.get()), "Falhou")
        );
    }
    
    @Test
    public void deletarDatas() {
        datasService.create(new Datas(1L, "26/10/2021", 0));
        Optional<Datas> datas = datasService.findById(1L);
        assertAll("Deletar", () -> assertTrue(datas.isPresent(), "Não presente."),
                () -> assertTrue(datasService.delete(datas.get().getId()),
                        "A data não foi deletada."));
    }
    
    @Test
    public void listarDatasPorStatus() {
        Datas data1 = new Datas(1L, "26/10/2021", 0);
        Datas data2 = new Datas(2L, "27/10/2021", 1);
        Datas data3 = new Datas(3L, "28/10/2021", 0);
        datasService.create(data1);
        datasService.create(data2);
        datasService.create(data3);
        List<Datas> base = Arrays.asList(data1, data3);
        List<Datas> datas = datasService.listarPorStatus();
        for (int i = 0; i < 2; i++)
            assertSame(base.get(i).getId(), datas.get(i).getId());
    }
}
