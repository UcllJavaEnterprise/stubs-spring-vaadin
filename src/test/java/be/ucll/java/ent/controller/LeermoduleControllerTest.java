package be.ucll.java.ent.controller;

import be.ucll.java.ent.domain.LeermoduleDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeermoduleControllerTest {
    @Autowired
    private LeermoduleController ctrl;

    // Testdata
    private LeermoduleDTO testLeermodule;
    private long testLeermoduleID;

    @Before //Before every single test
    public void setUp() throws Exception {
        // Create some testdata
        Calendar myCalendar = new GregorianCalendar(1999, 11, 25);
        Date myDate = myCalendar.getTime();
        testLeermodule = new LeermoduleDTO(0,"MGP12345", "Java Advanced", "2020-21");
        testLeermoduleID = ctrl.createLeermodule(testLeermodule);
    }

    @Test
    public void testCreateLeermoduleOK() {
        LeermoduleDTO lm = new LeermoduleDTO();
        lm.setCode("TestLM");
        lm.setBeschrijving("TestBeschrijving");
        lm.setSchooljaar("2020-2021");
        long tempid = ctrl.createLeermodule(lm);

        LeermoduleDTO tempLM = ctrl.getLeermoduleById(tempid);
        Assert.assertEquals(lm, tempLM);

        ctrl.deleteLeermodule(tempid);
    }

    @Test
    public void getAllLeermodulesOK(){
        List<LeermoduleDTO> lst = ctrl.getAllLeermodules();
        assert(lst.size() > 0);
    }

    @Test
    public void getAllLeermodulesCount(){
        List<LeermoduleDTO> lst = ctrl.getAllLeermodules();
        long cnt = ctrl.countLeermodules();
        Assert.assertEquals(lst.size(), cnt);
    }

    @After // After every single test
    public void tearDown() throws Exception {
        ctrl.deleteLeermodule(testLeermoduleID);
    }

}