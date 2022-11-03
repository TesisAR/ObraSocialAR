package domainapp.modules.simple.fixture;

import javax.inject.Inject;

import org.apache.isis.testing.fixtures.applib.personas.BuilderScriptWithResult;

<<<<<<< HEAD
import domainapp.modules.simple.dom.afiliados.SimpleObject;
import domainapp.modules.simple.dom.afiliados.SimpleObjects;
=======

import domainapp.modules.simple.dom.afiliado.Afiliado;
import domainapp.modules.simple.dom.afiliado.Afiliados;
import domainapp.modules.simple.types.afiliado.Name;

>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
<<<<<<< HEAD
public class SimpleObjectBuilder extends BuilderScriptWithResult<SimpleObject> {
=======
public class SimpleObjectBuilder extends BuilderScriptWithResult<Afiliado> {
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759

    @Getter @Setter
    private String name;

    @Override
<<<<<<< HEAD
    protected SimpleObject buildResult(final ExecutionContext ec) {

        checkParam("name", ec, String.class);

        return wrap(simpleObjects).create(name);
=======
    protected Afiliado buildResult(final ExecutionContext ec) {

        checkParam("name", ec, String.class);

        return wrap(afiliado);//wrap(afiliados).create(name, apellido, dni, edad, fechaNacimiento, lugarNacimiento, telefono, tipo);
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759
    }

    // -- DEPENDENCIES

<<<<<<< HEAD
    @Inject SimpleObjects simpleObjects;
=======
    @Inject Afiliado afiliado;
>>>>>>> 1dd8206bec68dfc87ae023a152f9dd2feae3f759

}
