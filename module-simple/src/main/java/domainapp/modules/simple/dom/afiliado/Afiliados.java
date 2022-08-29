package domainapp.modules.simple.dom.afiliados;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;

//import domainapp.modules.simple.dom.planes.Plan;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.PriorityPrecedence;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.PromptStyle;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import domainapp.modules.simple.types.afiliado.Name;
import domainapp.modules.simple.types.afiliado.Apellido;
import domainapp.modules.simple.types.afiliado.Dni;
import domainapp.modules.simple.types.afiliado.FechaNacimiento;
import domainapp.modules.simple.types.afiliado.Edad;
import domainapp.modules.simple.types.afiliado.LugarNacimiento;
import domainapp.modules.simple.types.afiliado.Telefono;
import domainapp.modules.simple.types.afiliado.FechaInicio;
//import domainapp.modules.simple.types.afiliado.TipoPlan;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "simple.Afiliados"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Afiliados {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;
    //final SimpleObjectRepository simpleObjectRepository;

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Afiliado create(
            @Name final String name,
            @Apellido final String apellido,
            final int dni,
            final int edad,
            @FechaNacimiento final String fechaNacimiento,
            @LugarNacimiento final String lugarNacimiento,
            final int telefono,
            @FechaInicio final String fechaInicio
          //  final Plan plan
    ) {
        return repositoryService.persist(Afiliado.withName
                (name, apellido, dni, edad, fechaNacimiento,
                 lugarNacimiento, telefono, fechaInicio));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Afiliado> findByName(
            @Name final String name
            // final int dni
    ) {
        return repositoryService.allMatches(
                Query.named(Afiliado.class, Afiliado.NAMED_QUERY__FIND_BY_NAME_LIKE)
                        .withParameter("name", name));
    }



    @Programmatic
    public Afiliado findByNameExact(final String name) {
        return repositoryService.firstMatch(
                        Query.named(Afiliado.class, Afiliado.NAMED_QUERY__FIND_BY_NAME_EXACT)
                                .withParameter("name", name))
                .orElse(null);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Afiliado> listAll() {
        return repositoryService.allInstances(Afiliado.class);
    }


    @Programmatic
    public void ping() {
        JDOQLTypedQuery<Afiliado> q = jdoSupportService.newTypesafeQuery(Afiliado.class);
        final QAfiliado candidate = QAfiliado.candidate();
        q.range(0,2);
        q.orderBy(candidate.dni.asc());
        q.executeList();
    }


}
