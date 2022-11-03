package domainapp.modules.simple.dom.prestador;

import java.util.Comparator;

import java.util.Date;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

//import domainapp.modules.simple.dom.planes.Plan;
import domainapp.modules.simple.dom.afiliado.Afiliado;
import domainapp.modules.simple.dom.plan.Plan;
import domainapp.modules.simple.dom.prestador.Prestador;
import domainapp.modules.simple.types.Prestador.*;
import domainapp.modules.simple.types.afiliado.Apellido;
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


@javax.jdo.annotations.PersistenceCapable(
        schema = "simple",
        identityType=IdentityType.DATASTORE)
/*
@javax.jdo.annotations.Unique(
        name = "Prestador_name_UNQ", members = {"name"}
)
*/
/*
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = Prestador.NAMED_QUERY__FIND_BY_NAME_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.prestador.Prestador " +
                        "WHERE name.indexOf(:name) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Prestador.NAMED_QUERY__FIND_BY_NAME_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.prestador.Prestador " +
                        "WHERE name == :name"
        )
})
*/

@javax.jdo.annotations.DatastoreIdentity(strategy=IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "prestador.Prestador", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)

public class Prestador implements Comparable<Prestador> {


        static final String NAMED_QUERY__FIND_BY_CUIT_LIKE = "Prestador.findByCuitLike";

       // static final String NAMED_QUERY__FIND_BY_CUIT_EXACT = "Prestador.findByCuitExact";



        public static Prestador withName( String razonSocial, int cuit, Categoria categoria,
                                        Date fechaDeInicio,
                                        String domicilio, String localidad, int tel
                                        /*Plan plan*/) {
                val prestador = new Prestador();
               // prestador.setName(name);
                prestador.setRazonSocial(razonSocial);
                prestador.setCuit(cuit);
                prestador.setCategoria(categoria);
                prestador.setFechaDeInicio(fechaDeInicio);
                prestador.setDomicilio(domicilio);
                prestador.setLocalidad(localidad);
                prestador.setTel(tel);
             //   prestador.setPlan(plan);

                return prestador;
        }

        @Inject RepositoryService repositoryService;
        @Inject TitleService titleService;
        @Inject MessageService messageService;



    @RazonSocial
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "prestadores", sequence = "1")
    private String razonSocial;


    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "prestadores", sequence = "2")
    private int cuit;

    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "prestadores", sequence = "3")
    private Categoria categoria;


    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "prestadores", sequence = "4")
    private Date FechaDeInicio;

    @Domicilio
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "prestadores", sequence = "5")
    private String domicilio;

    @Localidad
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "prestadores", sequence = "6")
    private String localidad;


    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "prestadores", sequence = "7")
    private int tel;

   /* @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "prestadores", sequence = "8")
    private Plan plan;*/


    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Borra Prestador por cuit.")
    public void borrar() {
        int identify = this.getCuit();

        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' borrado", title));
        repositoryService.removeAndFlush(this);


    }
    private final static Comparator<Prestador> comparator =
            Comparator.comparing(Prestador::getCuit);

    @Override
    public int compareTo(final Prestador other) {
        return comparator.compare(this, other);
    }

}