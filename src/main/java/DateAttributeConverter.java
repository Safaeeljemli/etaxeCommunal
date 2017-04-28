
import controller.util.DateUtil;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("dateAttributeConverter")
public class DateAttributeConverter implements Converter {

    @Override
    public LocalDate getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("*****************getAsObject*******************");
        return DateUtil.convrtString(value,"dd/MM/yyyy").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        System.out.println("***************** DateAttributeConverter getAsString*******************");
        return String.valueOf(value);
    }

   

}