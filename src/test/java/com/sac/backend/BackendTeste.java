package com.sac.backend;

import com.sac.backend.DTO.FaleConoscoDTO;
import com.sac.backend.helpers.EmailSender;
import com.sac.backend.interfaces.AgendamentoRepository;
import com.sac.backend.models.Agendamento;
import com.sac.backend.models.Datas;
import com.sac.backend.services.AgendamentoService;
import com.sac.backend.services.DatasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    
    @Autowired
    EmailSender emailSender;
    
    @Autowired
    AgendamentoService agendamentoService;
    
    @Autowired
    AgendamentoRepository agendamentoRepository;
    
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
        for (int i = 0; i < datas.size(); i++)
            assertSame(base.get(i).getId(), datas.get(i).getId());
    }
    
    @Test
    public void enviarEmailConfirmacao() {
        FaleConoscoDTO faleConosco = new FaleConoscoDTO();
        faleConosco.setEmail("mauriciofreire@unisantos.br");
        assertTrue(emailSender.enviarEmailFaleConosco(faleConosco),
                "Falha ao enviar o email.");
    }
    
    @Test
    public void salvarAgendamentoPasseio() {
        Agendamento agendamento = new Agendamento(1L, "João Silva", "jsilva@gmail.com",
                "55.184.332-5", "99121473", 0);
        Optional<Datas> optional = datasService.findById(16L);
        Datas datas = optional.get();
        if (optional.isPresent()) {
            assertEquals(0, datas.getStatus());
            datas.setStatus(1);
            datasService.update(datas);
            agendamento.setData(optional.get());
        }
        assertEquals(1, datas.getStatus());
        assertTrue(agendamentoService.create(agendamento) instanceof Agendamento);
    }
    
    @Test
    public void encontrarAgendamentoPorId() {
        agendamentoRepository.save(new Agendamento(1L, "João Silva",
                "jsilva@gmail.com", "55.184.332-5", "99121473", 0));
        Optional<Agendamento> agendamento = agendamentoService.findById(1L);
        assertNotNull(agendamento.get().getId(), "Agendamento nao encontrado.");
    }
    
    @Test
    public void encontrarTodosAgendamentos() {
        Agendamento[] agendamentosArray = { new Agendamento(),
                new Agendamento(), new Agendamento() };
        String nome[] = { "Ana", "João", "Mario" };
        for (int id = 1; id < agendamentosArray.length; id++) {
            agendamentosArray[id].setId((long) id);
            agendamentosArray[id].setNomeUsuario(nome[id]);
            agendamentosArray[id].setEmailUsuario(nome[id].concat("@gmail.com"));
            agendamentosArray[id].setDocumento("11.222.333-" + id);
            agendamentosArray[id].setTelefone("999666321");
            agendamentosArray[id].setAtestado(0);
            agendamentoRepository.save(agendamentosArray[id]);
        }
        List<Agendamento> agendamentos = agendamentoService.findAll();
        for (Agendamento agendamento : agendamentos)
            assertTrue(agendamento instanceof Agendamento);
    }
    
    @Test
    public void atualizarAgendamento() {
        agendamentoRepository.save(new Agendamento(1L, "João Silva",
                "jsilva@gmail.com", "55.184.332-5", "99121473", 0));
        Optional<Agendamento> agendamento = agendamentoService.findById(1L);
        if (agendamento.isPresent())
            assertTrue(agendamentoService.delete(agendamento.get().getId()),
                    "Registro não disponível para exclusão.");
        else assertNotNull(agendamento, "Registro não encontrado.");
    }
    
    @Test
    public void excluirAgendamento() {
        agendamentoRepository.save(new Agendamento(1L, "João Silva",
                "jsilva@gmail.com","55.184.332-5", "99121473", 0));
        Optional<Agendamento> agendamento = agendamentoService.findById(1L);
        if (agendamento.isPresent())
            assertTrue(agendamentoService.delete(agendamento.get().getId()),
                    "Registro nao disponivel para exclusão.");
        else assertNotNull(agendamento, "Registro não encontrado.");
    }
}
