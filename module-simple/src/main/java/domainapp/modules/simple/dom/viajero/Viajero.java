package domainapp.modules.simple.dom.viajero;

import domainapp.modules.simple.types.viajero.Lugar;
import domainapp.modules.simple.types.viajero.FechaViaje;
import domainapp.modules.simple.types.viajero.EstadoViajero;

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
/*@javax.jdo.annotations.Unique(
        name = "Viajero_name_UNQ", members = {"name"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = Viajero.NAMED_QUERY__FIND_BY_NAME_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.viajero.Viajero " +
                        "WHERE name.indexOf(:name) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Viajero.NAMED_QUERY__FIND_BY_NAME_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.viajero.Viajero " +
                        "WHERE name == :name"
        )
})*/

@javax.jdo.annotations.DatastoreIdentity(strategy=IdGeneratorStrategy.IDENTITY, column="id")
//@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "viajero.Viajero", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Viajero implements Comparable<Viajero> {
    public static final String NAMED_QUERY__FIND_BY_LUGAR_LIKE = "Viajer.findByLugarLike";

   /* @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @javax.persistence.Column(name = "id", nullable = false)
    private Long id;*/

    public static Viajero withName(Date fechaViaje, String lugar, EstadoViajero estadoViajero)
                                    {
        val viajero = new Viajero();
        viajero.setFechaViaje(fechaViaje);
        viajero.setLugar(lugar);
        viajero.setEstadoViajero(estadoViajero);

        return viajero;
    }

    @Inject RepositoryService repositoryService;
    @Inject TitleService titleService;
    @Inject MessageService messageService;






    @Title
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "viajeros", sequence = "1")
    private Date fechaViaje;

    @Lugar
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "viajeros", sequence = "2")
    private String lugar;


    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "viajeros", sequence = "3")
    private EstadoViajero estadoViajero;

    //Viajero
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "viajeros", sequence = "4")
    private Viajero viajero;




    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout(associateWith = "viajeros", promptStyle = PromptStyle.INLINE)
    public Viajero updateLugar(
            @Lugar final String lugar) {
        setLugar(lugar);
        return this;
    }

  public String default0UpdateLugar() {
        return getLugar();
    }

  /*  public String validate0UpdateLugar(String newLugar) {
        for (char prohibitedCharacter : "&%$!".toCharArray()) {
            if( newLugar.contains(""+prohibitedCharacter)) {
                return "Characteres '" + prohibitedCharacter + "' prohibidos.";
            }
        }
        return null;
    }*/


    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Borra viajero.")
    public void borrar() {
        //string identify = this.getLugar();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' borrado", title));
        repositoryService.removeAndFlush(this);

       /* final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' borrado", title));
        repositoryService.removeAndFlush(this);
        return "Se borró el viajero con Nombre: " + identify;*/
    }
    private final static Comparator<Viajero> comparator =
            Comparator.comparing(Viajero::getLugar);

    @Override
    public int compareTo(final Viajero other) {
        return comparator.compare(this, other);
    }

}


