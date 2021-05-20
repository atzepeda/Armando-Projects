package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;

import org.junit.Test;

import edu.ncsu.csc.itrust2.forms.patient.AppointmentRequestForm;
import edu.ncsu.csc.itrust2.models.enums.AppointmentType;
import edu.ncsu.csc.itrust2.models.enums.Specialization;
import edu.ncsu.csc.itrust2.models.enums.Status;
import edu.ncsu.csc.itrust2.models.persistent.AppointmentRequest;
import edu.ncsu.csc.itrust2.models.persistent.User;

public class AppointmentRequestTest {

    @Test
    public void testAR () {

        final AppointmentRequest request = new AppointmentRequest();

        request.setComments( "Please I need help here!" );
        request.setDate( Calendar.getInstance() );
        request.setHcp( User.getByName( "hcp" ) );
        request.setPatient( User.getByName( "patient" ) );
        request.setStatus( Status.PENDING );
        request.setType( AppointmentType.GENERAL_CHECKUP );

        final AppointmentRequestForm form = new AppointmentRequestForm( request );

        assertEquals( request.getHcp().getUsername(), form.getHcp() );

        assertEquals( request.getPatient().getUsername(), form.getPatient() );

        assertEquals( request.getType().toString(), form.getType() );
        form.setId( "12" );

        assertEquals( String.valueOf( 12 ), form.getId() );

        request.setType( AppointmentType.OPHTHALMOLOGY_APPOINTMENT);

        final AppointmentRequestForm form2 = new AppointmentRequestForm( request );

        assertEquals( request.getType().toString(), form2.getType() );

        request.setType( AppointmentType.OPHTHALMOLOGY_SURGERY );

        final AppointmentRequestForm form3 = new AppointmentRequestForm( request );

        assertEquals( request.getType().toString(), form3.getType() );

        // Test when the specialization is null
        final User user = User.getByName( "hcp" );
        user.setSpecialization( null );
        request.setHcp( user );
        assertNull( request.getHcp().getSpecialization() );

        // Test when the specialization is not null
        user.setSpecialization( Specialization.OPHTHALMOLOGIST );
        request.setHcp( user );
        assertEquals( request.getHcp().getSpecialization(), Specialization.OPHTHALMOLOGIST );

    }

}
