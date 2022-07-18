package domainapp.modules.planes.dom;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;

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

import domainapp.modules.simple.types.plan.Name;
import domainapp.modules.simple.types.plan.FechaVencimiento;
import domainapp.modules.simple.types.plan.Monto;
import domainapp.modules.simple.types.plan.FechaCobro;
import domainapp.modules.simple.types.plan.TipoCobertura;


@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "simple.Planes"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Planes {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Plan create(
            @Name final String name,
            @TipoCobertura final String tipoCobertura,
            @Monto final String monto,
            @FechaVencimiento final String fechaVencimiento,
            @FechaCobro final String fechaCobro
           //, @Plan final Plan plan
            ) {
        return repositoryService.persist(Plan.withName(name, tipoCobertura,
                monto, fechaVencimiento, fechaCobro //,plan
        ));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Plan> findByName(
            @Name final String name
            ) {
        return repositoryService.allMatches(
                    Query.named(Plan.class, Plan.NAMED_QUERY__FIND_BY_NAME_LIKE)
                        .withParameter("name", name));
    }


    @Programmatic
    public Plan findByNameExact(final String name) {
        return repositoryService.firstMatch(
                    Query.named(Plan.class, Plan.NAMED_QUERY__FIND_BY_NAME_EXACT)
                        .withParameter("name", name))
                .orElse(null);
    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Plan> listAll() {
        return repositoryService.allInstances(Plan.class);
    }




    @Programmatic
    public void ping() {
        JDOQLTypedQuery<Plan> q = jdoSupportService.newTypesafeQuery(Plan.class);
        final QPlan candidate = QPlan.candidate();
        q.range(0,2);
        q.orderBy(candidate.name.asc());
        q.executeList();
    }


}
