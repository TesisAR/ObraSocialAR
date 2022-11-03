package domainapp.modules.simple.dom.prestador;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;
import java.util.Date;

//import domainapp.modules.simple.dom.planes.Plan;
import domainapp.modules.simple.dom.afiliado.Afiliado;
import domainapp.modules.simple.dom.credencial.Credencial;
import domainapp.modules.simple.dom.plan.Plan;
import domainapp.modules.simple.dom.viajero.Viajero;
import domainapp.modules.simple.types.Prestador.*;
import domainapp.modules.simple.types.afiliado.*;
import domainapp.modules.simple.types.credencial.Nombre;
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

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "simple.Prestadores"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )

public class Prestadores {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Prestador create(
            @RazonSocial final String razonSocial,
            final int cuit,
            final Categoria categoria,
            final Date fechaDeInicio,
            @Domicilio final String domicilio,
            @Localidad final String localidad,
            final int telefono
          //  final Plan plan

    ) {
        return repositoryService.persist(Prestador.withName
                (razonSocial, cuit, categoria, fechaDeInicio, domicilio,
                        localidad, telefono/*, plan*/));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Prestador> findByCuit(
            final int cuit

    ) {
        return repositoryService.allMatches(
                Query.named(Prestador.class, Prestador.NAMED_QUERY__FIND_BY_CUIT_LIKE)
                        .withParameter("cuit", cuit));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Prestador> listAll() {
        return repositoryService.allInstances(Prestador.class);
    }



}
