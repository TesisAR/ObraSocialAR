package domainapp.modules.simple.fixture;

import javax.inject.Inject;

import org.apache.isis.testing.fixtures.applib.personas.BuilderScriptWithResult;


import domainapp.modules.simple.dom.afiliado.Afiliado;
import domainapp.modules.simple.dom.afiliado.Afiliados;
import domainapp.modules.simple.types.afiliado.Name;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class SimpleObjectBuilder extends BuilderScriptWithResult<Afiliado> {

    @Getter @Setter
    private String name;

    @Override
    protected Afiliado buildResult(final ExecutionContext ec) {

        checkParam("name", ec, String.class);

        return wrap(afiliado);//wrap(afiliados).create(name, apellido, dni, edad, fechaNacimiento, lugarNacimiento, telefono, tipo);
    }

    // -- DEPENDENCIES

    @Inject Afiliado afiliado;

}
