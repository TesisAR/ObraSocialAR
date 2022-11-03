package domainapp.webapp.application.services.homepage;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;

<<<<<<< HEAD
import domainapp.modules.simple.dom.afiliados.SimpleObject;
import domainapp.modules.simple.dom.afiliados.SimpleObjects;
=======
import domainapp.modules.simple.dom.afiliado.Afiliado;
import domainapp.modules.simple.dom.afiliado.Afiliados;
/*import domainapp.modules.simple.dom.planes.Plan;
import domainapp.modules.simple.dom.planes.Planes;
import domainapp.modules.simple.dom.facturas.Factura;
import domainapp.modules.simple.dom.facturas.Facturas;*/

>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759

@DomainObject(
        nature = Nature.VIEW_MODEL,
        logicalTypeName = "simple.HomePageViewModel"
        )
@HomePage
@DomainObjectLayout()
public class HomePageViewModel {

    public String title() {
<<<<<<< HEAD
        return getObjects().size() + " objects";
    }

    public List<SimpleObject> getObjects() {
        return simpleObjects.listAll();
    }

    @Inject SimpleObjects simpleObjects;
=======
        return getObjects().size() + " afiliados";
    }

    public List<Afiliado> getObjects() {
        return afiliados.listAll();
    }

    @Inject Afiliados afiliados;
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759
}
