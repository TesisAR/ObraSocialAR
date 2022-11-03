package domainapp.modules.simple.types.credencial;

import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;

import javax.jdo.annotations.Column;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Column(length = Informacion.MAX_LEN, allowsNull = "false")
@Property(maxLength = Informacion.MAX_LEN)
@Parameter(maxLength = Informacion.MAX_LEN)
@ParameterLayout(named = "Informacion")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Informacion {

    int MAX_LEN = 40;
}