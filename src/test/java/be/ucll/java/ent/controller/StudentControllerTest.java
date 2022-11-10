package be.ucll.java.ent.controller;

import be.ucll.java.ent.domain.StudentDTO;
import com.helger.commons.state.IEnabledIndicator;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentControllerTest {

    @Autowired
    private StudentController ctrl;

    // Test data
    private StudentDTO testStudent;
    private long testStudentID;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before //Before every single test
    public void setup() {
        Calendar myCalendar = new GregorianCalendar(1999, 11, 25);
        Date myDate = myCalendar.getTime();
        testStudent = new StudentDTO(0, "TestNaam", "TestVoornaam", myDate);
        testStudentID = ctrl.createStudent(testStudent);
    }

    // 1. Alle READ / Opzoeken testen

    @Test // Test een student ID 0
    public void getStudentById0() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Student ID ontbreekt");

        ctrl.getStudentById(0);
    }

    @Test // Test een student ID met een negatief getal
    public void getStudentByIdNegGetal() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Student ID ontbreekt");

        ctrl.getStudentById(-1);
    }

    @Test
    public void getStudentByIdOK() {
        StudentDTO s = ctrl.getStudentById(testStudentID);
        assertEquals(s.toString(), testStudent.toString());
    }

    @Test // Test dat student Naam niet null is
    public void getStudentByNameNull() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Inputdata ontbreekt");

        ctrl.getStudentsByName(null);
    }

    @Test // Test dat student Naam niet leeg is
    public void getStudentByNameLeeg() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Naam leeg");

        ctrl.getStudentsByName("");
    }

    @Test // Test dat student Naam gelijk aan 'TestNaam' 1 student terug geeft => OK test
    public void getStudentByNameOK() {
        List<StudentDTO> lst = ctrl.getStudentsByName("TestNaam");
        assertEquals(1, lst.size());
    }

    @Test // Test dat student Naam gelijk aan 'TestNaam' 1 student terug geeft => OK test
    public void getStudentByNameOKCasInsensitive() {
        List<StudentDTO> lst = ctrl.getStudentsByName("testnaam");
        assertEquals(1, lst.size());
    }

    @Test // Test dat student Naam gelijk aan 'TestNaam' 1 student terug geeft => OK test
    public void getStudentByNameOKPartial() {
        List<StudentDTO> lst = ctrl.getStudentsByName("test");
        assertEquals(1, lst.size());
    }

    @Test
    public void getStudentsOK() {
        List<StudentDTO> lst = ctrl.getStudents("TestNaam", "TestVoornaam");
        assertEquals(1, lst.size());
    }

    @Test
    public void getStudentsPartialLowercase() {
        List<StudentDTO> lst = ctrl.getStudents("test", "test");
        assertEquals(1, lst.size());
    }

    @Test
    public void getStudentsNaamNull() {
        List<StudentDTO> lst = ctrl.getStudents(null, "TestVoornaam");
        assertEquals(1, lst.size());
    }

    @Test
    public void getStudentsNaamLeeg() {
        List<StudentDTO> lst = ctrl.getStudents("", "TestVoornaam");
        assertEquals(1, lst.size());
    }

    @Test
    public void getStudentsNaamWhitespace() {
        List<StudentDTO> lst = ctrl.getStudents(" ", "estvoorna");
        assertEquals(1, lst.size());
    }

    // 2. Alle UPDATE / wijzigen testen

    @Test
    public void updateStudentNull() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Inputdata ontbreekt");

        StudentDTO student = null;
        ctrl.updateStudent(student);
    }

    @Test // Test dat student ID niet 0 is
    public void updateStudent0() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Student ID ontbreekt");

        StudentDTO student = new StudentDTO();
        student.setId(0);
        student.setNaam("TestNaam");
        student.setVoornaam("TestVoornaam");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.updateStudent(student);
    }

    @Test // Test dat student ID gen negatief getal is
    public void updateStudentNegGetal() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Student ID ontbreekt");

        StudentDTO student = new StudentDTO();
        student.setId(-1);
        student.setNaam("TestNaam");
        student.setVoornaam("TestVoornaam");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.updateStudent(student);
    }

    @Test // Test dat student Naam niet null is
    public void updateStudentNaamNull() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setId(testStudentID);
        student.setNaam(null);
        student.setVoornaam("TestVoornaam");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.updateStudent(student);
    }

    @Test // Test dat student Naam niet leeg is
    public void updateStudentNaamEmpty() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setId(testStudentID);
        student.setNaam("");
        student.setVoornaam("TestVoornaam");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.updateStudent(student);
    }

    @Test // Test dat student Naam niet te lang is
    public void updateStudentNaamTeLang() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("128");

        StudentDTO student = new StudentDTO();
        student.setId(testStudentID);
        student.setNaam("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        student.setVoornaam("TestVoornaam");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.updateStudent(student);
    }

    @Test // Test dat student Voornaam niet null is
    public void updateStudentVoornaamNull() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setId(testStudentID);
        student.setNaam("TestNaam");
        student.setVoornaam(null);
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.updateStudent(student);
    }

    @Test // Test dat student Voornaam niet leeg is
    public void updateStudentVoornaamEmpty() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setId(testStudentID);
        student.setNaam("TestNaam");
        student.setVoornaam("");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.updateStudent(student);
    }

    @Test // Test dat student Voornaam niet te lang is
    public void updateStudentVoornaamTeLang() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setId(testStudentID);
        student.setNaam("TestNaam");
        student.setVoornaam("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.updateStudent(student);
    }

    @Test // Test dat student Geboortedatum niet null is
    public void updateStudentGeboortedatumNull() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setId(testStudentID);
        student.setNaam("TestNaam");
        student.setVoornaam("TestVoornaam");
        student.setGeboortedatum(null);
        ctrl.updateStudent(student);
    }

    @Test // Test dat student Geboortedatum niet in de toekomst ligt
    public void updateStudentGeboortedatum() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setId(testStudentID);
        student.setNaam("TestNaam");
        student.setVoornaam("TestVoornaam");
        student.setGeboortedatum(new GregorianCalendar(2100, 1, 1).getTime());
        ctrl.updateStudent(student);
    }

    @Test // Test dat student OK update
    public void updateStudentOK() {

        StudentDTO student = new StudentDTO();
        student.setId(testStudentID);
        student.setNaam("TestNaam");
        student.setVoornaam("TestVoornaam");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.updateStudent(student);

        StudentDTO tempStudent = ctrl.getStudentById(testStudentID);
        assertEquals(student.toString(), tempStudent.toString());
    }

    // 3. Alle Create / Aanmaken testen

    @Test
    public void createStudentNull() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Data vereist voor het aanmaken van een student ontbreekt");

        StudentDTO student = null;
        ctrl.createStudent(student);
    }

    @Test // Test dat student Naam niet null is
    public void createStudentNaamNull() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setNaam(null);
        student.setVoornaam("TestVoornaam");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.createStudent(student);
    }

    @Test // Test dat student Naam niet leeg is
    public void createStudentNaamEmpty() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setNaam("");
        student.setVoornaam("TestVoornaam");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.createStudent(student);
    }

    @Test // Test dat student Naam niet te lang is
    public void createStudentNaamTeLang() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setNaam("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        student.setVoornaam("TestVoornaam");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.createStudent(student);
    }

    @Test // Test dat student Voornaam niet null is
    public void createStudentVoornaamNull() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setNaam("TestNaam");
        student.setVoornaam(null);
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.createStudent(student);
    }

    @Test // Test dat student Voornaam niet leeg is
    public void createStudentVoornaamEmpty() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setNaam("TestNaam");
        student.setVoornaam("");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.createStudent(student);
    }

    @Test // Test dat student Voornaam niet te lang is
    public void createStudentVoornaamTeLang() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setNaam("TestNaam");
        student.setVoornaam("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.createStudent(student);
    }

    @Test // Test dat student Geboortedatum niet null is
    public void createStudentGeboortedatumNull() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setNaam("TestNaam");
        student.setVoornaam("TestVoornaam");
        student.setGeboortedatum(null);
        ctrl.createStudent(student);
    }

    @Test // Test dat student Geboortedatum niet in de toekomst ligt
    public void createStudentGeboortedatum() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setNaam("TestNaam");
        student.setVoornaam("TestVoornaam");
        student.setGeboortedatum(new GregorianCalendar(2100, 1, 1).getTime());
        ctrl.createStudent(student);
    }

    @Test // Test dat student niet reeds aanwezig is
    public void createStudentDuplicate() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setNaam("testnaam");
        student.setVoornaam("testvoornaam");
        student.setGeboortedatum(new GregorianCalendar(1999, 11, 25).getTime());
        ctrl.createStudent(student);
    }

    @Test // OK Test
    public void createStudentOK() {
        StudentDTO student = new StudentDTO();
        student.setNaam("Jan");
        student.setVoornaam("Janssens");
        student.setGeboortedatum(new GregorianCalendar(1980, 12, 31).getTime());
        long tempid = ctrl.createStudent(student);

        StudentDTO tempStudent = ctrl.getStudentById(tempid);
        assertEquals(student.toString(), tempStudent.toString());

        ctrl.deleteStudent(tempid);
    }

    // 4. Alle Delete / Verwijderen testen

    @Test // Test dat student ID niet 0 is
    public void deleteStudent0() {
        expectedEx.expect(IllegalArgumentException.class);

        ctrl.deleteStudent(0);
    }

    @Test // Test delete StudentOK
    public void deleteStudentOK() {
        expectedEx.expect(IllegalArgumentException.class);

        StudentDTO student = new StudentDTO();
        student.setNaam("Piet");
        student.setVoornaam("Peeters");
        student.setGeboortedatum(new GregorianCalendar(1990, 1, 1).getTime());
        long tempid = ctrl.createStudent(student);

        ctrl.deleteStudent(tempid);

        StudentDTO s = ctrl.getStudentById(tempid);
    }

    @After // After every single test
    public void teardown() {
        ctrl.deleteStudent(testStudentID);
    }

}
