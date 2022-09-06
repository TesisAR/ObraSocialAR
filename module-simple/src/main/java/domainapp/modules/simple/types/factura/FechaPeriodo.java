package domainapp.modules.simple.types.factura;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.jdo.annotations.Column;

import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;

@Column(length = FechaPeriodo.MAX_LEN, allowsNull = "false")
@Property(maxLength = FechaPeriodo.MAX_LEN)
@Parameter(maxLength = FechaPeriodo.MAX_LEN)
@ParameterLayout(named = "FechaPeriodo")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FechaPeriodo {

    int MAX_LEN = 40;
}