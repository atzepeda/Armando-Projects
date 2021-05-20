package edu.ncsu.csc.itrust2.models.persistent;

import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.criterion.Criterion;

import edu.ncsu.csc.itrust2.forms.hcp.OfficeVisitForm;
import edu.ncsu.csc.itrust2.models.enums.Role;

/**
 * Object persisted in the database that represents the OpticalHealthMetrics of
 * a patient's office visit.
 *
 * @author Keith Goepfert (krgoepfe)
 */

@Entity
@Table ( name = "OpticalHealthMetrics" )
public class OpticalHealthMetrics extends DomainObject<OpticalHealthMetrics> {

    /**
     * Retrieve an OpticalHealthMetrics by its numerical ID.
     *
     * @param id
     *            The ID (as assigned by the DB) of the OpticalHealthMetrics
     * @return The OpticalHealthMetrics, if found, or null if not found.
     */
    public static OpticalHealthMetrics getById ( final Long id ) {
        try {
            return getWhere( eqList( ID, id ) ).get( 0 );
        }
        catch ( final Exception e ) {
            return null;
        }
    }

    /**
     * Retrieve a List of all OpticalHealthMetrics from the database. Can be
     * filtered further once retrieved. Will return the OpticalHealthMetrics
     * sorted by date.
     *
     * @return A List of all OpticalHealthMetrics saved in the database
     */
    @SuppressWarnings ( "unchecked" )
    public static List<OpticalHealthMetrics> getOpticalHealthMetrics () {
        final List<OpticalHealthMetrics> requests = (List<OpticalHealthMetrics>) getAll( OpticalHealthMetrics.class );
        requests.sort( ( x1, x2 ) -> x1.getId().compareTo( x2.getId() ) );
        return requests;
    }

    /**
     * Used so that Hibernate can construct and load objects
     */
    public OpticalHealthMetrics () {
    }

    /**
     * Retrieve a List of OpticalHealthMetrics that meets the given where
     * clause. Clause is expected to be valid SQL.
     *
     * @param where
     *            List of Criterion to and together and search for records by
     * @return The matching list
     */
    @SuppressWarnings ( "unchecked" )
    private static List<OpticalHealthMetrics> getWhere ( final List<Criterion> where ) {
        return (List<OpticalHealthMetrics>) getWhere( OpticalHealthMetrics.class, where );
    }

    /**
     * Retrieves all OpticalHealthMetrics for the Patient provided.
     *
     * @param patientName
     *            Name of the patient
     * @return All of their OpticalHealthMetrics
     */
    public static List<OpticalHealthMetrics> getOpticalHealthMetricsForPatient ( final String patientName ) {
        return getWhere( eqList( "patient", User.getByNameAndRole( patientName, Role.ROLE_PATIENT ) ) );
    }

    /**
     * Retrieves all OpticalHealthMetrics for the HCP provided
     *
     * @param hcpName
     *            Name of the HCP
     * @return All OpticalHealthMetrics involving this HCP
     */
    public static List<OpticalHealthMetrics> getOpticalHealthMetricsForHCP ( final String hcpName ) {
        return getWhere( eqList( "hcp", User.getByNameAndRole( hcpName, Role.ROLE_HCP ) ) );
    }

    /**
     * Retrieves all OpticalHealthMetrics for the HCP _and_ Patient provided.
     * This is the intersection of the requests -- namely, only the ones where
     * both the HCP _and_ Patient are on the request.
     *
     * @param hcpName
     *            Name of the HCP
     * @param patientName
     *            Name of the Patient
     * @return The list of matching OpticalHealthMetrics
     */
    public static List<OpticalHealthMetrics> getOpticalHealthMetricsForHCPAndPatient ( final String hcpName,
            final String patientName ) {

        final Vector<Criterion> criteria = new Vector<Criterion>();
        criteria.add( eq( "hcp", User.getByNameAndRole( hcpName, Role.ROLE_HCP ) ) );
        criteria.add( eq( "patient", User.getByNameAndRole( patientName, Role.ROLE_PATIENT ) ) );
        return getWhere( criteria );
    }

    /**
     * Handles conversion between an OfficeVisitForm (the form with
     * user-provided data) and a OpticalHealthMetrics object that contains
     * validated information These two classes are closely intertwined to handle
     * validated persistent information and text-based information that is then
     * displayed back to the user.
     *
     * @param ovf
     *            OfficeVisitForm to convert from
     * @throws ParseException
     *             Error in parsing form.
     */
    public OpticalHealthMetrics ( final OfficeVisitForm ovf ) throws ParseException {
        setPatient( User.getByNameAndRole( ovf.getPatient(), Role.ROLE_PATIENT ) );
        setHcp( User.getByNameAndRole( ovf.getHcp(), Role.ROLE_HCP ) );

        setVisualAcuityLeft( ovf.getVisualAcuityLeft() );
        setVisualAcuityRight( ovf.getVisualAcuityRight() );

        setSphereLeft( ovf.getSphereLeft() );
        setSphereRight( ovf.getSphereRight() );

        if ( ( ovf.getCylinderLeft() != null || ovf.getCylinderRight() != null )
                && ( ovf.getAxisLeft() == null || ovf.getAxisRight() == null ) ) {
            throw new IllegalArgumentException(
                    "Optical Health Metrics cannot contain cylinder values without axis values." );
        }

        setCylinderLeft( ovf.getCylinderLeft() );
        setCylinderRight( ovf.getCylinderRight() );
        setAxisLeft( ovf.getAxisLeft() );
        setAxisRight( ovf.getAxisRight() );
    }

    /**
     * ID of the AppointmentRequest
     */
    @Id
    @GeneratedValue ( strategy = GenerationType.AUTO )
    private Long id;

    /**
     * Retrieves the ID of the AppointmentRequest
     */
    @Override
    public Long getId () {
        return id;
    }

    /**
     * Sets the ID of the AppointmentRequest
     *
     * @param id
     *            The new ID of the AppointmentRequest. For Hibernate.
     */
    @SuppressWarnings ( "unused" )
    private void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Visual acuity of the left eye
     */
    @Min ( value = 0, message = "Value must be positive" )
    private Float   visualAcuityLeft;

    /**
     * Visual acuity of the right eye
     */
    @Min ( value = 0, message = "Value must be positive" )
    private Float   visualAcuityRight;

    /**
     * Sphere value of the left eye
     */
    @Min ( value = 0, message = "Value must be positive" )
    private Float   sphereLeft;

    /**
     * Sphere value of the right eye
     */
    @Min ( value = 0, message = "Value must be positive" )
    private Float   sphereRight;

    /**
     * Cylinder value of the left eye
     */
    @Min ( value = 0, message = "Value must be positive" )
    private Float   cylinderLeft;

    /**
     * Cylinder value of the right eye
     */
    @Min ( value = 0, message = "Value must be positive" )
    private Float   cylinderRight;

    /**
     * Axis value of the left eye
     */
    @Min ( value = 0, message = "Value must be positive" )
    private Integer axisLeft;

    /**
     * Axis value of the right eye
     */
    @Min ( value = 0, message = "Value must be positive" )
    private Integer axisRight;

    /**
     * The Patient who is associated with this AppointmentRequest
     */
    @NotNull
    @ManyToOne
    @JoinColumn ( name = "patient_id", columnDefinition = "varchar(100)" )
    private User    patient;

    /**
     * The HCP who is associated with this AppointmentRequest
     */
    @NotNull
    @ManyToOne
    @JoinColumn ( name = "hcp_id", columnDefinition = "varchar(100)" )
    private User    hcp;

    /**
     * Retrieves the User object for the Patient for the AppointmentRequest
     *
     * @return The associated Patient
     */
    public User getPatient () {
        return patient;
    }

    /**
     * Sets the Patient for the AppointmentRequest
     *
     * @param patient
     *            The User object for the Patient on the Request
     */
    public void setPatient ( final User patient ) {
        this.patient = patient;
    }

    /**
     * Gets the User object for the HCP on the request
     *
     * @return The User object for the HCP on the request
     */
    public User getHcp () {
        return hcp;
    }

    /**
     * Sets the User object for the HCP on the AppointmentRequest
     *
     * @param hcp
     *            User object for the HCP on the Request
     */
    public void setHcp ( final User hcp ) {
        this.hcp = hcp;
    }

    /**
     * Gets the visual acuity of the left eye
     *
     * @return the visual acuity of the left eye
     */
    public Float getVisualAcuityLeft () {
        return visualAcuityLeft;
    }

    /**
     * sets the visual acuity of the left eye
     *
     * @param visualAcuityLeft
     *            the new visual acuity of the left eye
     */
    public void setVisualAcuityLeft ( final Float visualAcuityLeft ) {
        this.visualAcuityLeft = visualAcuityLeft;
    }

    /**
     * Gets the visual acuity of the right eye
     *
     * @return the visual acuity of the right eye
     */
    public Float getVisualAcuityRight () {
        return visualAcuityRight;
    }

    /**
     * Sets the visual acuity of the right eye
     *
     * @param visualAcuityRight
     *            the new visual acuity of the right eye
     */
    public void setVisualAcuityRight ( final Float visualAcuityRight ) {
        this.visualAcuityRight = visualAcuityRight;
    }

    /**
     * Gets the sphere value of the left eye
     *
     * @return the sphere value of the left eye
     */
    public Float getSphereLeft () {
        return sphereLeft;
    }

    /**
     * Sets the sphere value of the left eye
     *
     * @param sphereLeft
     *            the new sphere value of the left eye
     */
    public void setSphereLeft ( final Float sphereLeft ) {
        this.sphereLeft = sphereLeft;
    }

    /**
     * Gets the sphere value of the right eye
     *
     * @return the sphere value of the right eye
     */
    public Float getSphereRight () {
        return sphereRight;
    }

    /**
     * Sets the sphere value of the right eye
     *
     * @param sphereRight
     *            the new sphere value of the right eye
     */
    public void setSphereRight ( final Float sphereRight ) {
        this.sphereRight = sphereRight;
    }

    /**
     * Gets the cylinder value of the left eye
     *
     * @return the cylinder value of the left eye
     */
    public Float getCylinderLeft () {
        return cylinderLeft;
    }

    /**
     * Sets the cylinder value of the left eye
     *
     * @param cylinderLeft
     *            the new cylinder value of the left eye
     */
    public void setCylinderLeft ( final Float cylinderLeft ) {
        this.cylinderLeft = cylinderLeft;
    }

    /**
     * Gets the cylinder value of the right eye
     *
     * @return the cylinder value of the right eye
     */
    public Float getCylinderRight () {
        return cylinderRight;
    }

    /**
     * Sets the cylinder value of the right eye
     *
     * @param cylinderRight
     *            the new cylinder value of the right eye
     */
    public void setCylinderRight ( final Float cylinderRight ) {
        this.cylinderRight = cylinderRight;
    }

    /**
     * Gets the axis value of the left eye
     *
     * @return the axis value of the left eye
     */
    public Integer getAxisLeft () {
        return axisLeft;
    }

    /**
     * Sets the axis value of the left eye
     *
     * @param axisLeft
     *            the new axis value of the left eye
     */
    public void setAxisLeft ( final Integer axisLeft ) {
        this.axisLeft = axisLeft;
    }

    /**
     * Gets the axis value of the right eye
     *
     * @return the axis value of the right eye
     */
    public Integer getAxisRight () {
        return axisRight;
    }

    /**
     * Sets the axis value of the right eye
     *
     * @param axisRight
     *            the new axis value of the right eye
     */
    public void setAxisRight ( final Integer axisRight ) {
        this.axisRight = axisRight;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( visualAcuityLeft == null ) ? 0 : visualAcuityLeft.hashCode() );
        result = prime * result + ( ( hcp == null ) ? 0 : hcp.hashCode() );
        result = prime * result + ( ( visualAcuityRight == null ) ? 0 : visualAcuityRight.hashCode() );
        result = prime * result + ( ( sphereLeft == null ) ? 0 : sphereLeft.hashCode() );
        result = prime * result + ( ( sphereRight == null ) ? 0 : sphereRight.hashCode() );
        result = prime * result + ( ( cylinderLeft == null ) ? 0 : cylinderLeft.hashCode() );
        result = prime * result + ( ( axisLeft == null ) ? 0 : axisLeft.hashCode() );
        result = prime * result + ( ( patient == null ) ? 0 : patient.hashCode() );
        result = prime * result + ( ( axisRight == null ) ? 0 : axisRight.hashCode() );
        return result;
    }

    @Override
    public boolean equals ( final Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final OpticalHealthMetrics other = (OpticalHealthMetrics) obj;

        if ( hcp == null ) {
            if ( other.hcp != null ) {
                return false;
            }
        }
        else if ( !hcp.equals( other.hcp ) ) {
            return false;
        }
        else if ( !visualAcuityLeft.equals( other.visualAcuityLeft ) ) {
            return false;
        }
        else if ( !visualAcuityRight.equals( other.visualAcuityRight ) ) {
            return false;
        }
        else if ( !sphereLeft.equals( other.sphereLeft ) ) {
            return false;
        }
        else if ( !sphereRight.equals( other.sphereRight ) ) {
            return false;
        }
        else if ( !axisLeft.equals( other.axisLeft ) ) {
            return false;
        }
        else if ( !axisRight.equals( other.axisRight ) ) {
            return false;
        }

        return false;
    }

}
