package domainapp.webapp.application.services.homepage;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.Nature;

import domainapp.modules.simple.dom.afiliados.Afiliado;
import domainapp.modules.simple.dom.afiliados.Afiliados;
import domainapp.modules.planes.dom.Plan;
import domainapp.modules.planes.dom.Planes;

@DomainObject(
        nature = Nature.VIEW_MODEL,
        logicalTypeName = "simple.HomePageViewModel"
        )
@HomePage
@DomainObjectLayout()
public class HomePageViewModel {

    public String title() {
        return getObjects().size() + "afiliados";

                //getObjects().size() + " objects" + getPlanes().size() + "Planes";
    }

    public List<Afiliado> getObjects() {
        return afiliados.listAll();
    }



    @Inject Afiliados afiliados;

}
