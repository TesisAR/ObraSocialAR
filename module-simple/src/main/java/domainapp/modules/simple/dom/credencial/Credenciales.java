package domainapp.modules.simple.dom.credencial;


import domainapp.modules.simple.types.credencial.Apellido;
import domainapp.modules.simple.types.credencial.FechaEmision;
import domainapp.modules.simple.types.credencial.FechaExpiracion;
import domainapp.modules.simple.types.credencial.Informacion;
import domainapp.modules.simple.types.credencial.Nombre;
import domainapp.modules.simple.types.plan.TipoCobertura;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.Query;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.JdoSupportService;

import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;
import java.util.Date;
import java.util.List;


@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "simple.Credenciales"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Credenciales {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;
    //final SimpleObjectRepository simpleObjectRepository;

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Credencial create(
            @Nombre final String nombre,
            @Apellido final String apellido,
            final Date fechaEmision,
            final Date fechaExpiracion,
            final TipoCobertura tipoCobertura,
            @Informacion final String informacion
    ) {
        return repositoryService.persist(Credencial.withName
                (nombre, apellido, fechaEmision, fechaExpiracion, tipoCobertura,
                        informacion));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Credencial> findByName(
            @Nombre final String nombre
            // final int dni
    ) {
        return repositoryService.allMatches(
                Query.named(Credencial.class, Credencial.NAMED_QUERY__FIND_BY_NAME_LIKE)
                        .withParameter("nombre", nombre));
    }



    @Programmatic
    public Credencial findByNameExact(final String nombre) {
        return repositoryService.firstMatch(
                        Query.named(Credencial.class, Credencial.NAMED_QUERY__FIND_BY_NAME_EXACT)
                                .withParameter("nombre", nombre))
                .orElse(null);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Credencial> listAll() {
        return repositoryService.allInstances(Credencial.class);
    }



   @Programmatic
    public void ping() {
        JDOQLTypedQuery<Credencial> q = jdoSupportService.newTypesafeQuery(Credencial.class);
        final QCredencial candidate = QCredencial.candidate();
        q.range(0,2);
        q.orderBy(candidate.nombre.asc());
        q.executeList();
    }

}
