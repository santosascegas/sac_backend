package com.sac.backend.appointment;


import com.sac.backend.agenda.Agenda;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String doctorsNote;
    private String phone;
    private String idDocument;

    @OneToOne
    private Agenda agenda;
}
