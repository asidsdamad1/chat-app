package org.asi.authservice.validate;

import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public abstract class AbstractValidation {

    private String messageKey;
    private List<String> messageKeyCollection;
    private List<String> messageDescCollection;

    public List<String> getMessageDes() {
        if (this.messageDescCollection == null) {
            this.messageDescCollection = new ArrayList<>();
        }
        return messageDescCollection;
    }

    public String buildValidationMessage() {
        if (this.messageDescCollection == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (Iterator<String> iterator = this.messageDescCollection.iterator(); iterator.hasNext();) {
            String msg = (String) iterator.next();
            sb.append(msg);
            sb.append("\r\n");
            count++;
        }
        String result = sb.toString();
        if (count == 1) {
            result = result.replace("\r\n", "");
        }
        return result;
    }

    public boolean isValid() {

        return messageDescCollection == null || messageDescCollection.isEmpty();
    }
}
