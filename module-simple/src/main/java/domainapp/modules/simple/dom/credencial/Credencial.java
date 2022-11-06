package domainapp.modules.simple.dom.credencial;

import domainapp.modules.simple.types.credencial.Apellido;
import domainapp.modules.simple.types.credencial.Informacion;
import domainapp.modules.simple.types.credencial.Nombre;
import domainapp.modules.simple.types.plan.TipoCobertura;
import java.util.Date;

import lombok.*;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.jaxb.PersistentEntityAdapter;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Comparator;

import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;
@javax.jdo.annotations.PersistenceCapable(
        schema = "simple",
        identityType=IdentityType.DATASTORE)

@javax.jdo.annotations.DatastoreIdentity(strategy=IdGeneratorStrategy.IDENTITY, column="id")
//@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "credencial.Credencialxzc", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Credencial implements Comparable<Credencial> {

    static final String NAMED_QUERY__FIND_BY_NAME_LIKE = "Credencial.findByNameLike";

    static final String NAMED_QUERY__FIND_BY_NAME_EXACT = "Credencial.findByNameExact";


    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @javax.persistence.Column(name = "id", nullable = false)
    private Long id;

    public static Credencial withName(String nombre, String apellido, Date fechaEmision,
                                         Date fechaExpiracion, TipoCobertura tipoCobertura,
                                         String informacion) {
        val credencial = new Credencial();
        credencial.setNombre(nombre);
        credencial.setApellido(apellido);
        credencial.setFechaEmision(fechaEmision);
        credencial.setFechaExpiracion(fechaExpiracion);
        credencial.setTipoCobertura(tipoCobertura);
        credencial.setInformacion(informacion);
        return credencial;
    }

    @Inject RepositoryService repositoryService;
    @Inject TitleService titleService;
    @Inject MessageService messageService;






    @Title
    @Nombre
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "credenciales", sequence = "1")
    private String nombre;

    @Apellido
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "credenciales", sequence = "2")
    private String apellido;


    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "credenciales", sequence = "3")
    private Date fechaEmision;


    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "credenciales", sequence = "4")
    private Date fechaExpiracion;


    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "credenciales", sequence = "5  ")
    private TipoCobertura tipoCobertura;



    @Informacion
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "credenciales", sequence = "6")
    private String informacion;





    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout(associateWith = "credenciales", promptStyle = PromptStyle.INLINE)
    public Credencial updateName(
            @Nombre final String nombre) {
        setNombre(nombre);
        return this;
    }

    public String default0UpdateName() {
        return getNombre();
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
            describedAs = "Borra credencial.")
    public void borrar() {


        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' borrado", title));
        repositoryService.removeAndFlush(this);


/* final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' borrado", title));
        repositoryService.removeAndFlush(this);
        return "Se borró el credencial con Nombre: " + identify;*/

    }
    private final static Comparator<Credencial> comparator =
            Comparator.comparing(Credencial::getNombre);

    @Override
    public int compareTo(final Credencial other) {
        return comparator.compare(this, other);
    }

}


