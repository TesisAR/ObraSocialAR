package domainapp.modules.simple.dom.plan;

import java.util.Comparator;
import java.util.Date;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import domainapp.modules.simple.types.plan.*;
import lombok.*;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.PromptStyle;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;


@javax.jdo.annotations.PersistenceCapable(
    schema = "simple",
    identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.Unique(
        name = "Plan_nombrePlan_UNQ", members = {"nombrePlan"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = Plan.NAMED_QUERY__FIND_BY_NOMBRE_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.plan.Plan " +
                        "WHERE nombrePlan.indexOf(:nombrePlan) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Plan.NAMED_QUERY__FIND_BY_NOMBRE_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.plan.Plan " +
                        "WHERE nombrePlan == :nombrePlan"
        )
})
@javax.jdo.annotations.DatastoreIdentity(strategy=IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "plan.Plan", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Plan implements Comparable<Plan> {

    static final String NAMED_QUERY__FIND_BY_NOMBRE_LIKE = "Plan.findByNombreLike";
    static final String NAMED_QUERY__FIND_BY_NOMBRE_EXACT = "Plan.findByNombreExact";

    @Inject RepositoryService repositoryService;
    @Inject TitleService titleService;
    @Inject MessageService messageService;

    public static Plan withName(String nombrePlan, TipoCobertura tipoCobertura,
                                float monto, Date fechaVencimiento,
                                Date fechaCobro) {
        val plan = new Plan();
        plan.setNombrePlan(nombrePlan);
        plan.setTipoCobertura(tipoCobertura);
        plan.setMonto(monto);
        plan.setFechaVencimiento(fechaVencimiento);
        plan.setFechaCobro(fechaCobro);
        return plan;
    }


    @Title
    @NombrePlan
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "planes", sequence = "1")
    private String nombrePlan;


  //  @TipoCobertura
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "planes", sequence = "2")
    private TipoCobertura tipoCobertura;

    //@Monto
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "planes", sequence = "3")
    private float monto;


    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "planes", sequence = "4")
    private Date fechaVencimiento;


    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "planes", sequence = "5")
    private Date fechaCobro;


    /*@Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = "planes", sequence = "6")
    private Plan plan;*/


    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout(associateWith = "planes", promptStyle = PromptStyle.INLINE)
    public Plan updateName(
            @NombrePlan final String nombrePlan) {
        setNombrePlan(nombrePlan);
        return this;
    }
    public String default0UpdateName() {
        return getNombrePlan();
    }
    public String validate0UpdateName(String newNombrePlan) {
        for (char prohibitedCharacter : "&%$!".toCharArray()) {
            if( newNombrePlan.contains(""+prohibitedCharacter)) {
                return "Character '" + prohibitedCharacter + "' is not allowed.";
            }
        }
        return null;
    }


    /*@Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Deletes this object from the persistent datastore")
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.removeAndFlush(this);
    }*/
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Borra Plan.")
    public String borrar() {
        String nombre = this.getNombrePlan();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' borrado", title));
        repositoryService.removeAndFlush(this);
        return "Se borró el plan llamado " + nombre;
    }



    private final static Comparator<Plan> comparator =
            Comparator.comparing(Plan::getNombrePlan);

    @Override
    public int compareTo(final Plan other) {
        return comparator.compare(this, other);
    }

}
