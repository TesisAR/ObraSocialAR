package domainapp.modules.planes.dom;

import java.util.Comparator;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

import domainapp.modules.simple.types.plan.Name;
import domainapp.modules.simple.types.plan.FechaVencimiento;
import domainapp.modules.simple.types.plan.Monto;
import domainapp.modules.simple.types.plan.FechaCobro;
import domainapp.modules.simple.types.plan.TipoCobertura;



@javax.jdo.annotations.PersistenceCapable(
    schema = "Afiliados",
    identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.Unique(
        name = "Plan_name_UNQ", members = {"name"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = Plan.NAMED_QUERY__FIND_BY_NAME_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.planes.dom.Plan " +
                        "WHERE name.indexOf(:name) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Plan.NAMED_QUERY__FIND_BY_NAME_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.planes.dom.Plan " +
                        "WHERE name == :name"
        )
})
@javax.jdo.annotations.DatastoreIdentity(strategy=IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "simple.Plan", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Plan implements Comparable<Plan> {

    static final String NAMED_QUERY__FIND_BY_NAME_LIKE = "Plan.findByNameLike";
    static final String NAMED_QUERY__FIND_BY_NAME_EXACT = "Plan.findByNameExact";

    public static Plan withName(String name, String tipoCobertura,
                                String monto, String fechaVencimiento,
                                String fechaCobro //,String pplan
                                  ) {
        val plan = new Plan();
        plan.setName(name);
        plan.setTipoCobertura(tipoCobertura);
        plan.setMonto(monto);
        plan.setFechaVencimiento(fechaVencimiento);
        plan.setFechaCobro(fechaCobro);
       //pplan.setPplan(pplan);
        return plan;
    }

    @Inject RepositoryService repositoryService;
    @Inject TitleService titleService;
    @Inject MessageService messageService;



    @Title
    @Name
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "name", sequence = "1")
    private String name;


    @TipoCobertura
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "name", sequence = "2")
    private String tipoCobertura;

    @Monto
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "name", sequence = "3")
    private String monto;

    @FechaVencimiento
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "name", sequence = "4")
    private String fechaVencimiento;

    @FechaCobro
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "name", sequence = "5")
    private String fechaCobro;


  /* @Pplan
    @Getter @Setter
    @Property(commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @PropertyLayout(fieldSetId = "name", sequence = "6")
    private TipoPlan tipoPlan;*/


    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout(associateWith = "name", promptStyle = PromptStyle.INLINE)
    public Plan updateName(
            @Name final String name) {
        setName(name);
        return this;
    }
    public String default0UpdateName() {
        return getName();
    }
    public String validate0UpdateName(String newName) {
        for (char prohibitedCharacter : "&%$!".toCharArray()) {
            if( newName.contains(""+prohibitedCharacter)) {
                return "Character '" + prohibitedCharacter + "' is not allowed.";
            }
        }
        return null;
    }


    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Deletes this object from the persistent datastore")
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.removeAndFlush(this);
    }



    private final static Comparator<Plan> comparator =
            Comparator.comparing(Plan::getName);

    @Override
    public int compareTo(final Plan other) {
        return comparator.compare(this, other);
    }

}
