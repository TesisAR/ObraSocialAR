package domainapp.modules.simple.dom.afiliado;

import java.util.Comparator;
import java.util.Date;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

//import domainapp.modules.simple.dom.planes.Plan;
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
/*import org.apache.isis.persistence.jpa.applib.integration.IsisEntityListener;*/

import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

import domainapp.modules.simple.types.afiliado.Name;
import domainapp.modules.simple.types.afiliado.Tipo;
//import domainapp.modules.simple.types.afiliado.Notes;
import domainapp.modules.simple.types.afiliado.Apellido;
import domainapp.modules.simple.types.afiliado.Dni;
import domainapp.modules.simple.types.afiliado.FechaNacimiento;
import domainapp.modules.simple.types.afiliado.Edad;
import domainapp.modules.simple.types.afiliado.LugarNacimiento;
import domainapp.modules.simple.types.afiliado.Telefono;
import domainapp.modules.simple.types.afiliado.FechaInicio;
//import domainapp.modules.simple.types.afiliado.TipoPlan;


@javax.jdo.annotations.PersistenceCapable(
        schema = "simple",
        identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.Unique(
        name = "Afiliado_name_UNQ", members = {"name"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = Afiliado.NAMED_QUERY__FIND_BY_NAME_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.afiliado.Afiliado " +
                        "WHERE name.indexOf(:name) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Afiliado.NAMED_QUERY__FIND_BY_NAME_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.afiliado.Afiliado " +
                        "WHERE name == :name"
        )
})

@javax.jdo.annotations.DatastoreIdentity(strategy=IdGeneratorStrategy.IDENTITY, column="id")
//@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "afiliado.Afiliado", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Afiliado implements Comparable<Afiliado> {

    static final String NAMED_QUERY__FIND_BY_NAME_LIKE = "Afiliado.findByNameLike";
    // static final String NAMED_QUERY__FIND_BY_DNI_EXACT = "Afiliado.findByDniExact";
    static final String NAMED_QUERY__FIND_BY_NAME_EXACT = "Afiliado.findByNameExact";

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @javax.persistence.Column(name = "id", nullable = false)
    private Long id;

    public static Afiliado withName(String name, String apellido, int dni, int edad,
                                    Date fechaNacimiento, String lugarNacimiento, int telefono,
                                    Date fechaInicio, Tipo tipo/*, Plan plan*/) {
        val afiliado = new Afiliado();
        afiliado.setName(name);
        afiliado.setApellido(apellido);
        afiliado.setDni(dni);
        afiliado.setEdad(edad);
        afiliado.setFechaNacimiento(fechaNacimiento);
        afiliado.setLugarNacimiento(lugarNacimiento);
        afiliado.setTelefono(telefono);
        afiliado.setFechaInicio(fechaInicio);
        afiliado.setTipo(tipo);
        return afiliado;
    }

    @Inject RepositoryService repositoryService;
    @Inject TitleService titleService;
    @Inject MessageService messageService;






    @Title
    @Name
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "afiliados", sequence = "1")
    private String name;

    @Apellido
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "afiliados", sequence = "2")
    private String apellido;

    //@Nombre
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "afiliados", sequence = "3")
    private int dni;

    //@Edad
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "afiliados", sequence = "4")
    private int edad;


    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "afiliados", sequence = "5  ")
    private Date fechaNacimiento;



    @LugarNacimiento
    //@javax.persistence.Column(length = Informacion.MAX_LEN, nullable = false)
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "afiliados", sequence = "6")
    private String lugarNacimiento;

    @Telefono
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "afiliados", sequence = "7")
    private int telefono;


    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "afiliados", sequence = "8")
    private Date fechaInicio;

    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "afiliados", sequence = "9")
    private Tipo tipo;



   /* @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "afiliados", sequence = "9")
    private Plan plan;
*/



    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout(associateWith = "afiliados", promptStyle = PromptStyle.INLINE)
    public Afiliado updateName(
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
                return "Characteres '" + prohibitedCharacter + "' prohibidos.";
            }
        }
        return null;
    }


    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Borra afiliado.")
    public void borrar() {
        int identify = this.getDni();

        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' borrado", title));
        repositoryService.removeAndFlush(this);

       /* final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' borrado", title));
        repositoryService.removeAndFlush(this);
        return "Se borró el afiliado con Nombre: " + identify;*/
    }
    private final static Comparator<Afiliado> comparator =
            Comparator.comparing(Afiliado::getName);

    @Override
    public int compareTo(final Afiliado other) {
        return comparator.compare(this, other);
    }

}





