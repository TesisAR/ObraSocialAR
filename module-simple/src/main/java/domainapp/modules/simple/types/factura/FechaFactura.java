package domainapp.modules.simple.types.factura;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.jdo.annotations.Column;

import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;

@Column(length = FechaFactura.MAX_LEN, allowsNull = "false")
@Property(maxLength = FechaFactura.MAX_LEN)
@Parameter(maxLength = FechaFactura.MAX_LEN)
@ParameterLayout(named = "FechaFactura")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FechaFactura {

    int MAX_LEN = 40;
}