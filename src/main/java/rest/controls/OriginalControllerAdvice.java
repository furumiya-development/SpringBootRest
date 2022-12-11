package rest.controls;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/** RestControllerおよびControllerクラスでの例外発生を一括で集約するクラス
 * @author none**/
@RestControllerAdvice
public class OriginalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        LastException._LastExcepTitle = ex.getClass().getName();
        LastException._LastExcepPlace = "";
        LastException._LastExcepParam = HttpStatus.INTERNAL_SERVER_ERROR.toString();
        LastException._LastExcepMessage = ex.getMessage();
        var sw = new StringWriter();
        try(var pw = new PrintWriter(sw);) {
            ex.printStackTrace(pw);
            pw.flush();
            LastException._LastExcepTrace = sw.toString();
        }
        //LogWrite();
        HttpHeaders headers = new HttpHeaders();
        return this.handleExceptionInternal(ex, LastException._LastExcepTrace, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    //@Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}