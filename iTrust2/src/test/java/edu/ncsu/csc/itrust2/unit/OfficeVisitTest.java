package edu.ncsu.csc.itrust2.unit;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

import edu.ncsu.csc.itrust2.forms.hcp.OfficeVisitForm;
import edu.ncsu.csc.itrust2.models.enums.AppointmentType;
import edu.ncsu.csc.itrust2.models.enums.HouseholdSmokingStatus;
import edu.ncsu.csc.itrust2.models.enums.LabStatus;
import edu.ncsu.csc.itrust2.models.enums.PatientSmokingStatus;
import edu.ncsu.csc.itrust2.models.enums.Priority;
import edu.ncsu.csc.itrust2.models.enums.Role;
import edu.ncsu.csc.itrust2.models.enums.Specialization;
import edu.ncsu.csc.itrust2.models.enums.Surgeries;
import edu.ncsu.csc.itrust2.models.persistent.BasicHealthMetrics;
import edu.ncsu.csc.itrust2.models.persistent.Diagnosis;
import edu.ncsu.csc.itrust2.models.persistent.Drug;
import edu.ncsu.csc.itrust2.models.persistent.Hospital;
import edu.ncsu.csc.itrust2.models.persistent.ICDCode;
import edu.ncsu.csc.itrust2.models.persistent.LOINC;
import edu.ncsu.csc.itrust2.models.persistent.LabProcedure;
import edu.ncsu.csc.itrust2.models.persistent.OfficeVisit;
import edu.ncsu.csc.itrust2.models.persistent.OpticalHealthMetrics;
import edu.ncsu.csc.itrust2.models.persistent.Prescription;
import edu.ncsu.csc.itrust2.models.persistent.User;

public class OfficeVisitTest {

    @Test
    public void testOfficeVisit () throws NumberFormatException, ParseException {

        OfficeVisit.deleteAll();

        final Hospital hosp = new Hospital( "Dr. Jenkins' Insane Asylum", "123 Main St", "12345", "NC" );
        hosp.save();

        final OfficeVisit visit = new OfficeVisit();

        final BasicHealthMetrics bhm = new BasicHealthMetrics();

        bhm.setDiastolic( 150 );
        bhm.setHcp( User.getByName( "hcp" ) );
        bhm.setPatient( User.getByName( "patient" ) );
        bhm.setHdl( 75 );
        bhm.setLdl( 75 );
        bhm.setHeight( 75f );
        bhm.setWeight( 130f );
        bhm.setTri( 300 );
        bhm.setSystolic( 150 );
        bhm.setHouseSmokingStatus( HouseholdSmokingStatus.NONSMOKING );
        bhm.setPatientSmokingStatus( PatientSmokingStatus.NEVER );

        bhm.save();

        visit.setBasicHealthMetrics( bhm );
        visit.setType( AppointmentType.GENERAL_CHECKUP );
        visit.setHospital( hosp );
        visit.setPatient( User.getByName( "patient" ) );
        visit.setHcp( User.getByName( "hcp" ) );
        visit.setDate( Calendar.getInstance() );

        visit.setSurgery( Surgeries.NONE );

        visit.save();

        final List<Diagnosis> diagnoses = new Vector<Diagnosis>();

        final ICDCode code = new ICDCode();
        code.setCode( "A21" );
        code.setDescription( "Top Quality" );

        code.save();

        final Diagnosis diagnosis = new Diagnosis();

        diagnosis.setCode( code );
        diagnosis.setNote( "This is bad" );
        diagnosis.setVisit( visit );

        diagnoses.add( diagnosis );

        visit.setDiagnoses( diagnoses );

        visit.save();

        final List<LabProcedure> procs = new Vector<LabProcedure>();

        final LOINC loinc = new LOINC();
        loinc.setCode( "000-0" );
        loinc.setCommonName( "Swag analysis" );
        loinc.setComponent( "Swagger" );
        loinc.setProperty( "Coolness" );

        loinc.save();

        final LabProcedure pro = new LabProcedure();

        pro.setLoinc( loinc );
        pro.setPriority( Priority.HIGH );
        pro.setStatus( LabStatus.ASSIGNED );
        pro.setPatient( User.getByName( "patient" ) );
        pro.setAssignedTech( User.getByName( "patient" ) );
        pro.setComments( "Need to see if too cool to attend school :(" );
        pro.setVisit( visit );

        procs.add( pro );

        visit.setLabProcedures( procs );

        visit.save();

        final Drug drug = new Drug();

        drug.setCode( "1234-4321-89" );
        drug.setDescription( "Lithium Compounds" );
        drug.setName( "Li2O8" );
        drug.save();

        final Prescription pres = new Prescription();
        pres.setDosage( 3 );
        pres.setDrug( drug );

        final Calendar end = Calendar.getInstance();
        end.add( Calendar.DAY_OF_WEEK, 10 );
        pres.setEndDate( end );
        pres.setPatient( User.getByName( "patient" ) );
        pres.setStartDate( Calendar.getInstance() );
        pres.setRenewals( 5 );

        pres.save();

        visit.setPrescriptions( Collections.singletonList( pres ) );

        visit.save();

        // Test the visit's persistence
        final OfficeVisit copy = OfficeVisit.getById( visit.getId() );
        assertEquals( visit.getId(), copy.getId() );
        assertEquals( visit.getAppointment(), copy.getAppointment() );
        assertEquals( visit.getBasicHealthMetrics(), copy.getBasicHealthMetrics() );
        assertEquals( visit.getHcp(), copy.getHcp() );
        assertEquals( visit.getHospital().getName(), copy.getHospital().getName() );
        assertEquals( visit.getPatient(), copy.getPatient() );

        // Test the form object
        final OfficeVisitForm form = new OfficeVisitForm( visit );
        form.setPreScheduled( null );
        assertEquals( visit.getId().toString(), form.getId() );
        assertEquals( visit.getHcp().getUsername(), form.getHcp() );
        assertEquals( visit.getHospital().getName(), form.getHospital() );
        assertEquals( visit.getPatient().getUsername(), form.getPatient() );
        assertEquals( visit.getDiagnoses(), form.getDiagnoses() );
        assertEquals( visit.getLabProcedures(), form.getLabProcedures() );

        final OfficeVisit clone = new OfficeVisit( form );
        assertEquals( visit.getId(), clone.getId() );
        assertEquals( visit.getAppointment(), clone.getAppointment() );
        assertEquals( visit.getBasicHealthMetrics().getDiastolic(), clone.getBasicHealthMetrics().getDiastolic() );
        assertEquals( visit.getSurgery(), clone.getSurgery() );
        assertEquals( visit.getHcp(), clone.getHcp() );
        assertEquals( visit.getHospital().getName(), clone.getHospital().getName() );
        assertEquals( visit.getPatient(), clone.getPatient() );

        visit.setPrescriptions( Collections.emptyList() );

        visit.save();

        visit.delete();

    }

    @Test
    public void testOfficeVisitWithOphthalmologySurgery () throws NumberFormatException, ParseException {

        OfficeVisit.deleteAll();

        final Hospital hosp = new Hospital( "Dr. Jenkins' Insane Asylum", "123 Main St", "12345", "NC" );
        hosp.save();

        final User ophthalmologist = new User( "Dr.Ophy", "1234567", Role.ROLE_HCP, Specialization.OPHTHALMOLOGIST, 1 );
        ophthalmologist.save();

        final OfficeVisit visit = new OfficeVisit();

        final OpticalHealthMetrics ohm = new OpticalHealthMetrics();

        ohm.setAxisLeft( 1 );
        ohm.setAxisRight( 2 );
        ohm.setCylinderLeft( 3.0f );
        ohm.setCylinderRight( 4.0f );
        ohm.setHcp( User.getByName( "Dr.Ophy" ) );
        ohm.setPatient( User.getByName( "patient" ) );
        ohm.setSphereLeft( 5.0f );
        ohm.setSphereRight( 6.0f );
        ohm.setVisualAcuityLeft( 7.0f );
        ohm.setVisualAcuityRight( 8.0f );
        ohm.save();

        visit.setOpticalHealthMetrics( ohm );
        visit.setType( AppointmentType.OPHTHALMOLOGY_SURGERY );
        visit.setHospital( hosp );
        visit.setPatient( User.getByName( "patient" ) );
        visit.setHcp( User.getByName( "Dr.Ophy" ) );
        visit.setDate( Calendar.getInstance() );

        visit.setSurgery( Surgeries.CATARACT_SURGERY );

        visit.save();

        final List<Diagnosis> diagnoses = new Vector<Diagnosis>();

        final ICDCode code = new ICDCode();
        code.setCode( "A21" );
        code.setDescription( "Top Quality" );

        code.save();

        final Diagnosis diagnosis = new Diagnosis();

        diagnosis.setCode( code );
        diagnosis.setNote( "This is bad" );
        diagnosis.setVisit( visit );

        diagnoses.add( diagnosis );

        visit.setDiagnoses( diagnoses );

        visit.save();

        final List<LabProcedure> procs = new Vector<LabProcedure>();

        final LOINC loinc = new LOINC();
        loinc.setCode( "000-0" );
        loinc.setCommonName( "Swag analysis" );
        loinc.setComponent( "Swagger" );
        loinc.setProperty( "Coolness" );

        loinc.save();

        final LabProcedure pro = new LabProcedure();

        pro.setLoinc( loinc );
        pro.setPriority( Priority.HIGH );
        pro.setStatus( LabStatus.ASSIGNED );
        pro.setPatient( User.getByName( "patient" ) );
        pro.setAssignedTech( User.getByName( "patient" ) );
        pro.setComments( "Need to see if too cool to attend school :(" );
        pro.setVisit( visit );

        procs.add( pro );

        visit.setLabProcedures( procs );

        visit.save();

        final Drug drug = new Drug();

        drug.setCode( "1234-4321-89" );
        drug.setDescription( "Lithium Compounds" );
        drug.setName( "Li2O8" );
        drug.save();

        final Prescription pres = new Prescription();
        pres.setDosage( 3 );
        pres.setDrug( drug );

        final Calendar end = Calendar.getInstance();
        end.add( Calendar.DAY_OF_WEEK, 10 );
        pres.setEndDate( end );
        pres.setPatient( User.getByName( "patient" ) );
        pres.setStartDate( Calendar.getInstance() );
        pres.setRenewals( 5 );

        pres.save();

        visit.setPrescriptions( Collections.singletonList( pres ) );

        visit.save();

        // Test the visit's persistence
        final OfficeVisit copy = OfficeVisit.getById( visit.getId() );
        assertEquals( visit.getId(), copy.getId() );
        assertEquals( visit.getAppointment(), copy.getAppointment() );
        assertEquals( visit.getBasicHealthMetrics(), copy.getBasicHealthMetrics() );
        assertEquals( visit.getHcp(), copy.getHcp() );
        assertEquals( visit.getHospital().getName(), copy.getHospital().getName() );
        assertEquals( visit.getPatient(), copy.getPatient() );

        // Test the form object
        final OfficeVisitForm form = new OfficeVisitForm( visit );
        form.setPreScheduled( null );
        assertEquals( visit.getId().toString(), form.getId() );
        assertEquals( visit.getHcp().getUsername(), form.getHcp() );
        assertEquals( visit.getHospital().getName(), form.getHospital() );
        assertEquals( visit.getPatient().getUsername(), form.getPatient() );
        assertEquals( visit.getDiagnoses(), form.getDiagnoses() );
        assertEquals( visit.getLabProcedures(), form.getLabProcedures() );

        final OfficeVisit clone = new OfficeVisit( form );
        assertEquals( visit.getId(), clone.getId() );
        assertEquals( visit.getAppointment(), clone.getAppointment() );
        assertEquals( visit.getOpticalHealthMetrics().getVisualAcuityRight(),
                clone.getOpticalHealthMetrics().getVisualAcuityRight() );
        assertEquals( visit.getSurgery(), clone.getSurgery() );
        assertEquals( visit.getHcp(), clone.getHcp() );
        assertEquals( visit.getHospital().getName(), clone.getHospital().getName() );
        assertEquals( visit.getPatient(), clone.getPatient() );

        visit.setPrescriptions( Collections.emptyList() );

        visit.save();

        visit.delete();

    }

    @Test
    public void testOfficeVisitWithOphthalmologyAppointment () throws NumberFormatException, ParseException {

        OfficeVisit.deleteAll();

        final Hospital hosp = new Hospital( "Dr. Jenkins' Insane Asylum", "123 Main St", "12345", "NC" );
        hosp.save();

        final User ophthalmologist = new User( "Dr.Ophy", "1234567", Role.ROLE_HCP, Specialization.OPHTHALMOLOGIST, 1 );
        ophthalmologist.save();
        final OfficeVisit visit = new OfficeVisit();

        final OpticalHealthMetrics ohm = new OpticalHealthMetrics();

        ohm.setAxisLeft( 1 );
        ohm.setAxisRight( 2 );
        ohm.setCylinderLeft( 3.0f );
        ohm.setCylinderRight( 4.0f );
        ohm.setHcp( User.getByName( "Dr.Ophy" ) );
        ohm.setPatient( User.getByName( "patient" ) );
        ohm.setSphereLeft( 5.0f );
        ohm.setSphereRight( 6.0f );
        ohm.setVisualAcuityLeft( 7.0f );
        ohm.setVisualAcuityRight( 8.0f );
        ohm.save();

        visit.setOpticalHealthMetrics( ohm );
        visit.setType( AppointmentType.OPHTHALMOLOGY_APPOINTMENT );
        visit.setHospital( hosp );
        visit.setPatient( User.getByName( "patient" ) );
        visit.setHcp( User.getByName( "Dr.Ophy" ) );
        visit.setDate( Calendar.getInstance() );

        visit.setSurgery( Surgeries.NONE );

        visit.save();

        final List<Diagnosis> diagnoses = new Vector<Diagnosis>();

        final ICDCode code = new ICDCode();
        code.setCode( "A21" );
        code.setDescription( "Top Quality" );

        code.save();

        final Diagnosis diagnosis = new Diagnosis();

        diagnosis.setCode( code );
        diagnosis.setNote( "This is bad" );
        diagnosis.setVisit( visit );

        diagnoses.add( diagnosis );

        visit.setDiagnoses( diagnoses );

        visit.save();

        final List<LabProcedure> procs = new Vector<LabProcedure>();

        final LOINC loinc = new LOINC();
        loinc.setCode( "000-0" );
        loinc.setCommonName( "Swag analysis" );
        loinc.setComponent( "Swagger" );
        loinc.setProperty( "Coolness" );

        loinc.save();

        final LabProcedure pro = new LabProcedure();

        pro.setLoinc( loinc );
        pro.setPriority( Priority.HIGH );
        pro.setStatus( LabStatus.ASSIGNED );
        pro.setPatient( User.getByName( "patient" ) );
        pro.setAssignedTech( User.getByName( "patient" ) );
        pro.setComments( "Need to see if too cool to attend school :(" );
        pro.setVisit( visit );

        procs.add( pro );

        visit.setLabProcedures( procs );

        visit.save();

        final Drug drug = new Drug();

        drug.setCode( "1234-4321-89" );
        drug.setDescription( "Lithium Compounds" );
        drug.setName( "Li2O8" );
        drug.save();

        final Prescription pres = new Prescription();
        pres.setDosage( 3 );
        pres.setDrug( drug );

        final Calendar end = Calendar.getInstance();
        end.add( Calendar.DAY_OF_WEEK, 10 );
        pres.setEndDate( end );
        pres.setPatient( User.getByName( "patient" ) );
        pres.setStartDate( Calendar.getInstance() );
        pres.setRenewals( 5 );

        pres.save();

        visit.setPrescriptions( Collections.singletonList( pres ) );

        visit.save();

        // Test the visit's persistence
        final OfficeVisit copy = OfficeVisit.getById( visit.getId() );
        assertEquals( visit.getId(), copy.getId() );
        assertEquals( visit.getAppointment(), copy.getAppointment() );
        assertEquals( visit.getBasicHealthMetrics(), copy.getBasicHealthMetrics() );
        assertEquals( visit.getHcp(), copy.getHcp() );
        assertEquals( visit.getHospital().getName(), copy.getHospital().getName() );
        assertEquals( visit.getPatient(), copy.getPatient() );

        // Test the form object
        final OfficeVisitForm form = new OfficeVisitForm( visit );
        form.setPreScheduled( null );
        assertEquals( visit.getId().toString(), form.getId() );
        assertEquals( visit.getHcp().getUsername(), form.getHcp() );
        assertEquals( visit.getHospital().getName(), form.getHospital() );
        assertEquals( visit.getPatient().getUsername(), form.getPatient() );
        assertEquals( visit.getDiagnoses(), form.getDiagnoses() );
        assertEquals( visit.getLabProcedures(), form.getLabProcedures() );

        final OfficeVisit clone = new OfficeVisit( form );
        assertEquals( visit.getId(), clone.getId() );
        assertEquals( visit.getAppointment(), clone.getAppointment() );
        assertEquals( visit.getOpticalHealthMetrics().getVisualAcuityRight(),
                clone.getOpticalHealthMetrics().getVisualAcuityRight() );
        assertEquals( visit.getSurgery(), clone.getSurgery() );
        assertEquals( visit.getHcp(), clone.getHcp() );
        assertEquals( visit.getHospital().getName(), clone.getHospital().getName() );
        assertEquals( visit.getPatient(), clone.getPatient() );

        visit.setPrescriptions( Collections.emptyList() );

        visit.save();

        visit.delete();

    }

    @Test
    public void testOfficeVisitForm () throws NumberFormatException, ParseException {

        final Hospital hosp = new Hospital( "Dr. Jenkins' Insane Asylum", "123 Main St", "12345", "NC" );
        hosp.save();

        final OfficeVisitForm visit = new OfficeVisitForm();
        visit.setPreScheduled( null );
        visit.setDate( "4/16/2048" );
        visit.setTime( "9:50 AM" );
        visit.setHcp( "hcp" );
        visit.setPatient( "patient" );
        visit.setNotes( "Test office visit" );
        visit.setType( AppointmentType.GENERAL_CHECKUP.toString() );
        visit.setHospital( "Dr. Jenkins' Insane Asylum" );
        visit.setDiastolic( 150 );
        visit.setHdl( 75 );
        visit.setLdl( 75 );
        visit.setHeight( 75f );
        visit.setWeight( 130f );
        visit.setTri( 300 );
        visit.setSystolic( 150 );
        visit.setHouseSmokingStatus( HouseholdSmokingStatus.NONSMOKING );
        visit.setPatientSmokingStatus( PatientSmokingStatus.NEVER );
        final LabProcedure proc = new LabProcedure();
        proc.setAssignedTech( User.getByRole( Role.ROLE_LABTECH ).get( 0 ) );
        proc.setComments( "Commment" );
        proc.setLoinc( LOINC.getAll().get( 0 ) );
        proc.setPatient( User.getByName( visit.getPatient() ) );
        proc.setStatus( LabStatus.ASSIGNED );
        proc.setPriority( Priority.CRITICAL );
        final ArrayList<LabProcedure> procs = new ArrayList<LabProcedure>();
        procs.add( proc );
        visit.setLabProcedures( procs );

        final OfficeVisit ov = new OfficeVisit( visit );

        assertEquals( visit.getHcp(), ov.getHcp().getUsername() );
        assertEquals( visit.getPatient(), ov.getPatient().getUsername() );
    }

}
