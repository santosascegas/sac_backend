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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    
    static Integer N = 30;
    
    @Test
    public void salvarDataPasseio() {
        Datas datas = new Datas(1L, "08/12/2021", 0);
        assertAll(() -> assertNotNull(datas.getId(), "O id não deve ser nulo."),
                () -> assertNotNull(datas.getData(), "A data não deve ser nula."),
                () -> assertTrue(datas.getStatus() >= 0 && datas.getStatus() <= 1,
                        "O status deve ser 0 ou 1."),
                () -> assertNotNull(datasService.create(datas),
                        "Erro ao salvar a data.")
        );
    }
    
    @ParameterizedTest
    @MethodSource(value = "aleatorio")
    public void procurarData(long id) {
        Optional<Datas> datas = datasService.findById(id);
        assertTrue(datas.isPresent(), "Nenhuma data encontrada.");
    }
    
    @Test
    public void procurarTodasAsDatas() {
        List<Datas> datasList = datasService.findAll();
        for (Datas d: datasList)
            assertAll("Datas", () -> assertNotNull(d.getData(), "Data nula."),
                    () -> assertTrue(d instanceof Datas, "Registro incompatível."));
    }
    
    @ParameterizedTest
    @MethodSource(value = "aleatorio")
    public void atualizarDatas(long id) {
        Optional<Datas> atualizacao = datasService.findById(id);
        atualizacao.ifPresent(value -> assertNotEquals(1, value.getStatus()));
        atualizacao.ifPresent(value -> value.setStatus(1));
        assertAll("Atualização de Data",
                () -> assertTrue(atualizacao.isPresent(), "Registro não encontrado."),
                () -> assertTrue(datasService.update(atualizacao.get()), "Falhou")
        );
    }
    
    @ParameterizedTest
    @MethodSource(value = "aleatorio")
    public void deletarDatas(long id) {
        Optional<Datas> datas = datasService.findById(id);
        assertAll("Deletar", () -> assertTrue(datas.isPresent(), "Não presente."),
                () -> assertTrue(datasService.delete(datas.get().getId()),
                        "A data não foi deletada."));
    }
    
    @Test
    public void listarDatasPorStatus() {
        List<Datas> datas = datasService.listarPorStatus();
        for (Datas d : datas)
            assertSame(0, d.getStatus(), "Status inesperado!");
    }
    
    @Test
    public void enviarEmailConfirmacao() {
        FaleConoscoDTO faleConosco = new FaleConoscoDTO();
        faleConosco.setEmail("mauriciofreire@unisantos.br");
        assertTrue(emailSender.enviarEmailFaleConosco(faleConosco),
                "Falha ao enviar o email.");
    }
    
    @ParameterizedTest
    @MethodSource(value = "aleatorio")
    public void salvarAgendamentoPasseio(long id) {
        Agendamento agendamento = new Agendamento(1L, "Max Verstappen",
                "supermax@gmail.com", "33.333.333-3", "993233033", 1);
        Optional<Datas> optional = datasService.findById(id);
        Datas datas = optional.get();
        if (!optional.isPresent())
            assertThrows(NullPointerException.class,
                    () -> System.out.println("Nenhum registro encontrado."));

        assertEquals(0, datas.getStatus(), "Data já agendada.");
        datas.setStatus(1);
        datasService.update(datas);
        agendamento.setData(optional.get());
        
        assertEquals(1, datas.getStatus(), "Status da data não atualizado!");
        assertAll("Verificar criação da data",
                () -> assertNotNull(agendamento.getNomeUsuario()),
                () -> assertNotNull(agendamento.getEmailUsuario()),
                () -> assertNotNull(agendamento.getData()),
                () -> assertNotNull(agendamento.getDocumento()),
                () -> assertNotNull(agendamento.getTelefone()),
                () -> assertNotNull(agendamento.getAtestado()),
                () -> assertTrue(agendamentoService.create(agendamento) instanceof Agendamento));
    }
    
    @ParameterizedTest
    @MethodSource(value = "aleatorio")
    public void encontrarAgendamentoPorId(long id) {
        Optional<Agendamento> agendamento = agendamentoService.findById(id);
        agendamento.ifPresent( usuario -> System.out.println(usuario.getNomeUsuario()));
        agendamento.ifPresentOrElse(value ->
                        assertNotNull(agendamento), new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Nenhum agendamento foi " +
                                "encontradado com ID = " + id + "!");
                    }
                });
    }
    
    @Test
    public void encontrarTodosAgendamentos() {
        List<Agendamento> agendamentos = agendamentoService.findAll();
        for (Agendamento agendamento : agendamentos) {
            assertAll("FindAll", () -> assertNotNull(agendamento),
                    () -> assertNotNull(agendamento.getData(),
                            agendamento.getId() + " sem data."),
                    () -> assertTrue(agendamento instanceof Agendamento));
        }
    }
    
    @ParameterizedTest
    @MethodSource(value = "aleatorio")
    public void atualizarAgendamento(long id) {
        Optional<Agendamento> agendamento = agendamentoService.findById(id);
        assertTrue(agendamento.isPresent(), "Registro não encontrado!");
        assertTrue(agendamentoService.update(agendamento.get()));
    }
    
    @ParameterizedTest
    @MethodSource(value = "aleatorio")
    public void excluirAgendamento(long id) {
        Optional<Agendamento> agendamento = agendamentoService.findById(id);
        assertNotNull(agendamento.get(), "Registro não encontrado!");
        assertTrue(agendamentoService.delete(agendamento.get().getId()));
    }
    
    public static Stream<Long> aleatorio() {
        long rand = (long) (Math.random() * N + 1);
        return Stream.of(rand);
    }
}
