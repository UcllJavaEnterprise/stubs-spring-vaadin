package be.ucll.java.ent.controller;

import be.ucll.java.ent.domain.InschrijvingDTO;
import be.ucll.java.ent.domain.LeermoduleDTO;
import be.ucll.java.ent.domain.StudentDTO;
import be.ucll.java.ent.model.InschrijvingEntity;
import be.ucll.java.ent.model.LeermoduleEntity;
import be.ucll.java.ent.model.StudentEntity;
import be.ucll.java.ent.repository.InschrijvingDAO;
import be.ucll.java.ent.repository.LeermoduleDAO;
import be.ucll.java.ent.repository.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Transactional
public class InschrijvingController {

    @Autowired
    private StudentDAO sdao;

    @Autowired
    private LeermoduleDAO lmdao;

    @Autowired
    private InschrijvingDAO idao;

    // Create methods

    public void createInschrijving(StudentDTO s, LeermoduleDTO lm, boolean vrijgesteld) throws IllegalArgumentException {
        if (s == null) throw new IllegalArgumentException("Inschrijving zonder student niet mogelijk");
        if (lm == null) throw new IllegalArgumentException("Inschrijving zonder leermodule niet mogelijk");
        if (s.getId() == 0) throw new IllegalArgumentException("Inschrijving zonder student ID niet mogelijk");
        if (lm.getId() == 0) throw new IllegalArgumentException("Inschrijving zonder leermodule ID niet mogelijk");

        StudentEntity se = null;
        Optional<StudentEntity> opts = sdao.get(s.getId());
        if (opts.isPresent()) {
            se = opts.get();
        } else {
            throw new IllegalArgumentException("Student die wordt ingeschreven bestaat niet");
        }

        LeermoduleEntity lme = null;
        Optional<LeermoduleEntity> optlm = lmdao.get(lm.getId());
        if (optlm.isPresent()) {
            lme = optlm.get();
        } else {
            throw new IllegalArgumentException("Leermodule die wordt gerefereerd bestaat niet");
        }

        List<InschrijvingDTO> inschr = getInschrijvingenVoorStudentId(s.getId());
        if (inschr != null && inschr.size() > 0) {
            for (InschrijvingDTO i : inschr) {
                if (i.getCode().equals(lm.getCode())) {
                    throw new IllegalArgumentException("Student is reeds ingeschreven voor " + lm.getBeschrijving());
                }
            }
        }

        InschrijvingEntity ie = new InschrijvingEntity(se, lme, vrijgesteld);
        idao.create(ie);
    }

    // Read method

    public InschrijvingDTO getInschrijving(long student_id, long leermodule_id) throws IllegalArgumentException {
        if (student_id <= 0) throw new RuntimeException("Inschrijving verwijderen gefaaald. Student ID ontbreekt");
        if (leermodule_id <= 0)
            throw new RuntimeException("Inschrijving verwijderen gefaaald. Leermodule ID ontbreekt");

        Optional<InschrijvingEntity> opt = idao.get(student_id, leermodule_id);
        if (opt.isPresent()) {
            return new InschrijvingDTO(opt.get().getLeermodule().getCode(), opt.get().getLeermodule().getBeschrijving(), opt.get().isVrijgesteld());
        } else {
            throw new IllegalArgumentException("Geen inschrijving gevonden voor deze combinatie student / leermodule");
        }
    }

    // Delete / Remove methods

    public void deleteInschrijving(StudentDTO s, LeermoduleDTO lm) throws IllegalArgumentException {
        if (s == null) throw new IllegalArgumentException("Uitschrijven niet mogelijk. Student ontbreekt");
        if (s.getId() == 0) throw new IllegalArgumentException("Uitschrijven niet mogelijk. Student ID ontbreekt");
        if (lm == null) throw new IllegalArgumentException("Uitschrijven niet mogelijk. Leermodule ontbreekt");
        if (lm.getId() == 0) throw new IllegalArgumentException("Uitschrijven niet mogelijk. Leermodule ID ontbreekt");

        InschrijvingDTO i = getInschrijving(s.getId(), lm.getId());
        if (i != null) {
            idao.delete(s.getId(), lm.getId());
        } else {
            throw new IllegalArgumentException("Verwijderen inschrijving gefaald");
        }
    }

    // Search methods

    public List<InschrijvingDTO> getInschrijvingenVoorStudentId(long studentID) throws IllegalArgumentException {
        if (studentID <= 0L) throw new IllegalArgumentException("Ongeldig ID");

        Optional<StudentEntity> opts = sdao.get(studentID);
        if (!opts.isPresent()) {
            throw new IllegalArgumentException("Student bestaat niet");
        }

        List<InschrijvingEntity> lstEnt = idao.getInschrijvingenVoorStudentId(studentID);
        List<InschrijvingDTO> lstDTO = new ArrayList<>();

        LeermoduleEntity lme;
        for (InschrijvingEntity ie : lstEnt) {
            lme = ie.getLeermodule();

            InschrijvingDTO dto = new InschrijvingDTO(lme.getCode(), lme.getBeschrijving(), ie.isVrijgesteld());
            lstDTO.add(dto);
        }

        return lstDTO;
    }

    public List<String> getAllInschrijvingen() {
        return idao.getAllInschrijvingen();
    }

}
