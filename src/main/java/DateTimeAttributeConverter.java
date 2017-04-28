
import controller.util.DateUtil;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("dateTimeAttributeConverter")
public class DateTimeAttributeConverter implements Converter {

    @Override
    public LocalDateTime getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("*****************getAsObject*******************");
        return DateUtil.convrtString(value,"dd/MM/yyyy HH:mm").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        System.out.println("***************** DateAttributeConverter getAsString*******************");
        return String.valueOf(value);
    }

   

}