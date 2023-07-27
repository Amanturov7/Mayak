package kg.center.mayak.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

    private String recipient;
    private String message;
    private String msgBody;
    private String title;
    private String phone;
    private String fullName;
//    private String attachmentName;
//    private String attachmentPath;

}