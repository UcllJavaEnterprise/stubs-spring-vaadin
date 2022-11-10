package be.ucll.java.ent.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InschrijvingControllerTest {

    @Autowired
    private InschrijvingController ctrl;

    @Before //Before every single test
    public void setup() {

    }

    @Test
    public void getInschrijvingen4StudentUnexistingID() {
        Exception expectedEx = Assert.assertThrows(IllegalArgumentException.class, () ->
                ctrl.getInschrijvingenVoorStudentId(999999999999L)
        );
        Assert.assertTrue(expectedEx.getMessage().contains("Student bestaat niet"));
    }

    @Test
    public void getAllInschrijvingen() {
        List<String> lst = ctrl.getAllInschrijvingen();
    }

    @After // After every single test
    public void teardown() {

    }

}
