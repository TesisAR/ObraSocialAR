package domainapp.modules.simple.dom.factura;


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

//import static io.swagger.jackson.JAXBAnnotationsHelper.setName;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

import domainapp.modules.simple.types.factura.FechaFactura;
import domainapp.modules.simple.types.factura.FechaPeriodo;
import domainapp.modules.simple.types.factura.FechaVencimiento;


@javax.jdo.annotations.PersistenceCapable(
        schema = "simple",// schema = "factura",
        identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.Unique(
        name = "Factura_nro_UNQ", members = {"nro"}
)
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = Factura.NAMED_QUERY__FIND_BY_NRO_LIKE,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.factura.Factura " +
                        "WHERE nro.indexOf(:nro) >= 0"
        ),
        @javax.jdo.annotations.Query(
                name = Factura.NAMED_QUERY__FIND_BY_NRO_EXACT,
                value = "SELECT " +
                        "FROM domainapp.modules.simple.dom.factura.Factura " +
                        "WHERE nro == :nro"
        )
})
@javax.jdo.annotations.DatastoreIdentity(strategy=IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@DomainObject(logicalTypeName = "factura.Factura", entityChangePublishing = Publishing.ENABLED)
@DomainObjectLayout()
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@XmlJavaTypeAdapter(PersistentEntityAdapter.class)
@ToString(onlyExplicitlyIncluded = true)
public class Factura implements Comparable<Factura> {

    static final String NAMED_QUERY__FIND_BY_NRO_LIKE = "Factura.findByNroLike";
    static final String NAMED_QUERY__FIND_BY_NRO_EXACT = "Factura.findByNroExact";

    @Inject RepositoryService repositoryService;
    @Inject TitleService titleService;
    @Inject MessageService messageService;


    public static Factura withName(float monto, String fechaFactura, String fechaPeriodo, String fechaVencimiento, int nro) {
        val factura = new Factura();
      //  factura.setFactura(factura);
        factura.setMonto(monto);
        factura.setFechaFactura(fechaFactura);
        factura.setFechaPeriodo(fechaPeriodo);
        factura.setFechaVencimiento(fechaVencimiento);
        factura.setNro(nro); //NUMERO DE FACTURA
        return factura;
    }








   /* @Title
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "facturas", sequence = "1")
    private Factura factura;*/


    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "facturas", sequence = "2")
    private float monto;

    @FechaFactura
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "facturas", sequence = "3")
    private String fechaFactura;

    @FechaPeriodo
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "facturas", sequence = "4")
    private String fechaPeriodo;

    @FechaVencimiento
    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "facturas", sequence = "5  ")
    private String fechaVencimiento;



    @Getter @Setter @ToString.Include
    @PropertyLayout(fieldSetId = "facturas", sequence = "6")
    private int nro;




    @Action(semantics = IDEMPOTENT, commandPublishing = Publishing.ENABLED, executionPublishing = Publishing.ENABLED)
    @ActionLayout(associateWith = "facturas", promptStyle = PromptStyle.INLINE)
    public Factura updateNro(
            final int nro) {
        setNro(nro);
        return this;
    }

    public int default0UpdateNro() {
        return getNro();
    }



    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    @ActionLayout(
            position = ActionLayout.Position.PANEL,
            describedAs = "Borra factura.")
    public String borrar() {
        int numero = this.getNro();
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' borrado", title));
        repositoryService.removeAndFlush(this);
        return "Se borró la factura " + numero;
    }
    private final static Comparator<Factura> comparator =
            Comparator.comparing(Factura::getNro);

    @Override
    public int compareTo(final Factura other) {
        return comparator.compare(this, other);
    }


}


