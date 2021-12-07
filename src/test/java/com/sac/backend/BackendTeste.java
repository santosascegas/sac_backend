package com.sac.backend;

import com.sac.backend.DTO.FaleConoscoDTO;
import com.sac.backend.helpers.EmailSender;
import com.sac.backend.interfaces.AgendamentoRepository;
import com.sac.backend.interfaces.DatasRepository;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    DatasRepository datasRepository;
    
    @Autowired
    EmailSender emailSender;
    
    @Autowired
    AgendamentoService agendamentoService;
    
    @Autowired
    AgendamentoRepository agendamentoRepository;
    
    @Test
    public void salvarDataPasseio() {
        Datas datas = new Datas(1L, "07/12/2021", 0);
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
        datasRepository.save(new Datas(1L, "07/12/2021", 0));
        Optional<Datas> datas = datasService.findById(28L);
        assertNotNull(datas.get(), "O id não foi encontrado.");
    }
    
    @Test
    public void procurarTodasAsDatas() {
        List<Datas> datasList = datasService.findAll();
        for (Datas d: datasList)
            assertTrue(d instanceof Datas, "Registro incorreto.");
    }
    
    @Test
    public void atualizarDatas() {
        double r = Math.random() * 30;
        long rand = (long) (r + 1);
        
        Optional<Datas> atualizacao = datasService.findById(rand);
        atualizacao.ifPresent(value -> assertNotEquals(1, value.getStatus()));
        atualizacao.ifPresent(value -> value.setStatus(1));
        assertAll("Atualização",
                () -> assertNotNull(atualizacao.get(), "Registro não encontrado."),
                () -> assertTrue(datasService.update(atualizacao.get()), "Falhou")
        );
    }
    
    @Test
    public void deletarDatas() {
        double r = Math.random() * 30;
        long rand = (long) (r + 1);
        System.out.println("-->> " + rand);
        Optional<Datas> datas = datasService.findById(12L);
        assertAll("Deletar", () -> assertTrue(datas.isPresent(), "Não presente."),
                () -> assertTrue(datasService.delete(datas.get().getId()),
                        "A data não foi deletada."));
    }
    
    @Test
    public void listarDatasPorStatus() {
        List<Datas> datas = datasService.listarPorStatus();
        System.out.println("LISTA " + datas.size());
        for (Datas d : datas)
            assertSame(0, d.getStatus());
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
        Agendamento agendamento = new Agendamento(1L, "Urubutre",
                "urubutre@gmail.com", "42.000.352-1", "99066293", 1);
        double r = Math.random() * 27;
        long rand = (long) (r + 1);
        Optional<Datas> optional = datasService.findById(rand);
        Datas datas = optional.get();
        if (optional.isPresent()) {
            assertEquals(0, datas.getStatus(), "Data já agendada.");
            datas.setStatus(1);
            datasService.update(datas);
            agendamento.setData(optional.get());
        }
        assertEquals(1, datas.getStatus());
        assertTrue(agendamentoRepository.save(agendamento) instanceof Agendamento);
    }
    
    @Test
    public void encontrarAgendamentoPorId() {
        long rand = (long) (Math.random() * (24 - 15) + 15);
        Optional<Agendamento> agendamento = agendamentoService.findById(rand);
        System.out.println("ID = " + rand + "\nagendamento: " + agendamento.get().getNomeUsuario());
        assertNotNull(agendamento, "Agendamento nao encontrado.");
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
