package be.ucll.java.ent.controller;

import be.ucll.java.ent.domain.StudentDTO;
import be.ucll.java.ent.model.StudentEntity;
import be.ucll.java.ent.repository.StudentDAO;
import be.ucll.java.ent.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Transactional
public class StudentController {

    @Autowired
    private StudentDAO dao;

    @Autowired
    private StudentRepository studRepo;

    @Autowired
    @Qualifier("messageSource")
    private MessageSource msg;

    // Create methods

    public long createStudent(StudentDTO student) throws IllegalArgumentException {
        // Algemene input controle
        if (student == null) {
            //throw new IllegalArgumentException("Alle data vereist voor het aanmaken van een student ontbreekt");
            throw new IllegalArgumentException("Data vereist voor het aanmaken van een student ontbreekt");
        }

        // Controle op verplichte velden
        if (student.getNaam() == null || student.getNaam().length() == 0)
            throw new IllegalArgumentException("Student aanmaken gefaald. Naam ontbreekt");
        if (student.getVoornaam() == null || student.getVoornaam().length() == 0)
            throw new IllegalArgumentException("Student aanmaken gefaald. Voornaam ontbreekt");
        if (student.getGeboortedatum() == null)
            throw new IllegalArgumentException("Student aanmaken gefaald. Geboortedatum ontbreekt");

        // Waarde te lang
        if (student.getNaam().trim().length() >= 128)
            throw new IllegalArgumentException("Student aanmaken gefaald. Naam langer dan 128 karakters");
        if (student.getVoornaam().trim().length() >= 128)
            throw new IllegalArgumentException("Student aanmaken gefaald. Voornaam langer dan 128 karakters");

        // Datumcontroles
        if (student.getGeboortedatum().after(new Date()))
            throw new IllegalArgumentException("Student aanmaken gefaald. Geboortedatum in de toekomst");

        // Controleer dat er al een student bestaat met dezelfde naam, voornaam en geboortedatum.
        List<StudentDTO> lst = this.getStudents(student.getNaam(), student.getVoornaam());
        for (StudentDTO stud : lst) {
            if (student.getNaam().equalsIgnoreCase(stud.getNaam()) &&
                    student.getVoornaam().equalsIgnoreCase(stud.getVoornaam()) && student.getGeboortedatumstr().equalsIgnoreCase(stud.getGeboortedatumstr())) {
                throw new IllegalArgumentException("Er is al een student in de databank met deze gegevens");
            }
        }

        StudentEntity s = new StudentEntity(0L, student.getNaam(), student.getVoornaam(), student.getGeboortedatum());
        dao.create(s);

        student.setId(s.getId());
        return s.getId();
    }

    // Read / get-one methods

    public StudentDTO getStudentById(long studentId) throws IllegalArgumentException {
        if (studentId <= 0L) throw new IllegalArgumentException("Student ID ontbreekt");

        Optional<StudentEntity> value = dao.get(studentId);
        if (value.isPresent()) {
            return new StudentDTO(value.get().getId(), value.get().getNaam(), value.get().getVoornaam(), value.get().getGeboortedatum());
        } else {
            throw new IllegalArgumentException("Geen student gevonden met ID: " + studentId);
        }
    }

    public StudentDTO getStudentByName(String studentName) throws IllegalArgumentException {
        if (studentName == null) throw new IllegalArgumentException("Ongeldige naam meegegeven");
        if (studentName.trim().length() == 0) throw new IllegalArgumentException("Geen naam meegegeven");

        Optional<StudentEntity> value = dao.getOneByName(studentName);
        if (value.isPresent()) {
            return new StudentDTO(value.get().getId(), value.get().getNaam(), value.get().getVoornaam(), value.get().getGeboortedatum());
        } else {
            throw new IllegalArgumentException("Geen student gevonden met naam: " + studentName);
        }
    }

    // Update / Modify / Change methods

    public void updateStudent(StudentDTO student) throws IllegalArgumentException {
        if (student == null) throw new IllegalArgumentException("Student wijzigen gefaald. Inputdata ontbreekt");
        if (student.getId() <= 0) throw new IllegalArgumentException("Student wijzigen gefaald. Student ID ontbreekt");
        if (student.getNaam() == null || student.getNaam().trim().equals(""))
            throw new IllegalArgumentException("Student wijzigen gefaald. Inputdata ontbreekt");
        if (student.getVoornaam() == null || student.getVoornaam().trim().equals(""))
            throw new IllegalArgumentException("Student wijzigen gefaald. Inputdata ontbreekt");
        if (student.getNaam().trim().length() >= 128)
            throw new IllegalArgumentException("Student wijzigen gefaald. Naam langer dan 128 karakters");
        if (student.getVoornaam().trim().length() >= 128)
            throw new IllegalArgumentException("Student wijzigen gefaald. Naam langer dan 128 karakters");
        if (student.getGeboortedatum() == null)
            throw new IllegalArgumentException("Student wijzigen gefaald. Geboortedatum ontbreekt");
        if (student.getGeboortedatum().after(new Date()))
            throw new IllegalArgumentException("Student wijzigen gefaald. Geboortedatum in de toekomst");

        // TODO Controleer dat deze update geen duplicaten veroorzaakt.

        dao.update(new StudentEntity(student.getId(), student.getNaam(), student.getVoornaam(), student.getGeboortedatum()));
    }

    // Delete methods

    public void deleteStudent(long studentId) throws IllegalArgumentException {
        if (studentId <= 0L) throw new IllegalArgumentException("Ongeldig ID");

        // First check if this student exists
        // If not the thrown IllegalArgumentException exits out of this method
        getStudentById(studentId);

        // TODO Check of er inschrijvingen zijn voor student. Dan best niet verwijderen.

        // If all is good effectively delete
        dao.delete(studentId);
    }

    // Search methods

    public List<StudentDTO> getStudentsByName(String naam) throws IllegalArgumentException {
        if (naam == null) throw new IllegalArgumentException("Student opzoeken op naam gefaald. Inputdata ontbreekt");
        if (naam.trim().length() == 0)
            throw new IllegalArgumentException("Student opzoeken op naam gefaald. Naam leeg");

        return (queryListToStudentDTOList(studRepo.findAllByNaamContainsIgnoreCaseOrderByNaam(naam)));
    }

    public List<StudentDTO> getStudents(String naam, String voornaam) throws IllegalArgumentException {
        if (naam == null && voornaam == null)
            throw new IllegalArgumentException("Student opzoeken op naam + voornaam gefaald. Inputdata ontbreekt");
        if (voornaam == null && naam != null && naam.trim().length() == 0)
            throw new IllegalArgumentException("Student opzoeken op naam + voornaam gefaald. Naam leeg");
        if (naam == null && voornaam != null && voornaam.trim().length() == 0)
            throw new IllegalArgumentException("Student opzoeken op naam + voornaam gefaald. Voornaam leeg");

        List<StudentDTO> lst = queryListToStudentDTOList(dao.getStudents(naam, voornaam));
        return lst;
    }

    public List<StudentDTO> getAllStudents() {
        return queryListToStudentDTOList(dao.getAll());
    }

    public long countStudents() {
        return dao.countAll();
    }

    // private methods

    private List<StudentDTO> queryListToStudentDTOList(List<StudentEntity> lst) {
        Stream<StudentDTO> stream = lst.stream()
                .map(rec -> {
                    StudentDTO dto = new StudentDTO();
                    dto.setId(rec.getId());
                    dto.setNaam(rec.getNaam());
                    dto.setVoornaam(rec.getVoornaam());
                    dto.setGeboortedatum(rec.getGeboortedatum());
                    return dto;
                });
        return stream.collect(Collectors.toList());
    }
}



