package domainapp.modules.simple.dom.viajero;

import java.util.Date;
import domainapp.modules.simple.types.viajero.EstadoViajero;
import domainapp.modules.simple.types.viajero.FechaViaje;
import domainapp.modules.simple.types.viajero.Lugar;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import java.util.List;
//import domainapp.modules.simple.types.afiliado.TipoPlan;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "simple.Viajeros"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Viajeros {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Viajero create(
            final Date fechaViaje,
            @Lugar final String lugar,
            final EstadoViajero estadoViajero
    ) {
        return repositoryService.persist(Viajero.withName
                (fechaViaje, lugar, estadoViajero));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Viajero> findByLugar(
            @Lugar final String lugar

    ) {
        return repositoryService.allMatches(
                Query.named(Viajero.class, Viajero.NAMED_QUERY__FIND_BY_LUGAR_LIKE)
                        .withParameter("lugar", lugar));
    }



/*    @Programmatic
    public Viajero findByLugarExact(final String lugar) {
        return repositoryService.firstMatch(
                        Query.named(Viajero.class, Viajero.NAMED_QUERY__FIND_BY_LUGAR_EXACT)
                                .withParameter("name", name))
                .orElse(null);
    }*/

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Viajero> listAll() {
        return repositoryService.allInstances(Viajero.class);
    }
/*
    public void ping() {
        JDOQLTypedQuery<Credencialxzc> q = jdoSupportService.newTypesafeQuery(Credencialxzc.class);

    }


   @Programmatic
    public void ping() {
        JDOQLTypedQuery<Credencialxzc> q = jdoSupportService.newTypesafeQuery(Credencialxzc.class);
        final QAfiliado candidate = QAfiliado.candidate();
        q.range(0,2);
        q.orderBy(candidate.name.asc());
        q.executeList();
    }*/


}
