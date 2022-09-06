package domainapp.modules.simple.dom.factura;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;

import domainapp.modules.simple.types.factura.*;
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

import domainapp.modules.simple.dom.factura.Factura;

@DomainService(
        nature = NatureOfService.VIEW,
        logicalTypeName = "simple.Facturas"
)
@javax.annotation.Priority(PriorityPrecedence.EARLY)
@lombok.RequiredArgsConstructor(onConstructor_ = {@Inject} )
public class Facturas {

    final RepositoryService repositoryService;
    final JdoSupportService jdoSupportService;


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public Factura create(
            @FechaFactura final String fechaFactura,
            @FechaVencimiento final String fechaVencimiento,
            @FechaPeriodo final String fechaPeriodo,
            final float monto,
            final int nro
            //final Factura facturas
    ){

        return repositoryService.persist(Factura.withName(
                monto, fechaFactura, fechaPeriodo, fechaVencimiento , nro));
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<Factura> findByNro(
            final int nro
            // final int nro
    ) {
        return repositoryService.allMatches(
                Query.named(Factura.class, Factura.NAMED_QUERY__FIND_BY_NRO_LIKE)
                        .withParameter("nro", nro));
    }


    @Programmatic
    public Factura findByNroExact(final int nro) {
        return repositoryService.firstMatch(
                        Query.named(Factura.class, Factura.NAMED_QUERY__FIND_BY_NRO_EXACT)
                                .withParameter("nro", nro))
                .orElse(null);
    }



    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    public List<Factura> listAll() {
        return repositoryService.allInstances(Factura.class);
    }




    @Programmatic
    public void ping() {
        JDOQLTypedQuery<Factura> q = jdoSupportService.newTypesafeQuery(Factura.class);
        final QFactura candidate = QFactura.candidate();
        q.range(0,2);
        q.orderBy(candidate.nro.asc());
        q.executeList();
    }


}
