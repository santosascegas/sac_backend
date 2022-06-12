package com.sac.backend.appointment;

import com.sac.backend.agenda.Agenda;
import com.sac.backend.agenda.AgendaService;
import com.sac.backend.email.EmailService;
import com.sac.backend.interfaces.ServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentService implements ServiceInterface<Appointment> {

    private final AppointmentRepository appointmentRepository;
    private final AgendaService agendaService;
    private final EmailService emailService;

    @Override
    public Appointment create(Appointment obj) {
        Agenda agenda = obj.getAgenda();
        agendaService.makeAgendaUnavailable(agenda.getId());

        emailService.sendClientAppointmentEmail(obj);
        emailService.sendAdminAppointmentEmail(obj);
        emailService.scheduleEmail(obj);

        return appointmentRepository.save(obj);
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public boolean update(Appointment obj) {
        if (appointmentRepository.existsById(obj.getId())) {
            appointmentRepository.save(obj);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Appointment> clientExists(String idDocument) {
        return appointmentRepository.clientExists(idDocument);
    }
}
