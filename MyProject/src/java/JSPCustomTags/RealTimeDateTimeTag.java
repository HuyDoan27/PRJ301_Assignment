/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSPCustomTags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class RealTimeDateTimeTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("'Ngày' dd 'tháng' MM 'năm' yyyy, HH:mm:ss");
        String formattedDateTime = formatter.format(currentDate);

        JspWriter out = pageContext.getOut();
        try {
            out.print(formattedDateTime);
        } catch (IOException e) {
            throw new JspException("Error in RealTimeDateTimeTag", e);
        }
        return SKIP_BODY;
    }

}
