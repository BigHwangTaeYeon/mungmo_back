package mungMo.memberService.com.aop;

import jakarta.servlet.UnavailableException;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import mungMo.memberService.com.exception.FileUploadException;
import mungMo.memberService.com.exception.ForbiddenException;
import mungMo.memberService.com.exception.UnauthorizedException;
import mungMo.memberService.com.exception.ValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@Aspect
@Slf4j
public class ResponseMessageAOP {

    @Around("execution(public * mungMo.memberService.domain.*.*.*Controller.*(..))")
    public ResponseEntity<?> allController(ProceedingJoinPoint jp) throws Throwable {
        try {
            Object proceed = jp.proceed();

            System.out.println("test aop : " + jp.getArgs().getClass().getSimpleName());

            return ResponseEntity.ok(proceed);
        }
        catch(FileUploadException | NullPointerException | IllegalArgumentException e){
            System.out.println("exception : " + e.getMessage());
            // 그 외 에러의 경우 400 메세지
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mungMo.memberService.com.config.ResponseMessage.valueOfCode("InvalidParams").getMessage());
        }
        catch(UnauthorizedException e){
            System.out.println("exception : " + e.getMessage());
            // 그 외 에러의 경우 401 메세지
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mungMo.memberService.com.config.ResponseMessage.valueOfCode("Unauthorized").getMessage());
        }
        catch(UnavailableException e){
            System.out.println("exception : " + e.getMessage());
            // 그 외 에러의 경우 401 메세지
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mungMo.memberService.com.config.ResponseMessage.valueOfCode("Unavailable").getMessage());
        }
        catch(ForbiddenException e){
            System.out.println("exception : " + e.getMessage());
            // 그 외 에러의 경우 403 메세지
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mungMo.memberService.com.config.ResponseMessage.valueOfCode("Forbidden").getMessage());
        }
        catch(ValidationException e){
            System.out.println("exception : " + e.getMessage());
            // 그 외 에러의 경우 403 메세지
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mungMo.memberService.com.config.ResponseMessage.valueOfCode("Validation").getMessage());
        }
        catch(NotFoundException e){
            System.out.println("exception : " + e.getMessage());
            // 그 외 에러의 경우 404 메세지
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mungMo.memberService.com.config.ResponseMessage.valueOfCode("NotFound").getMessage());
        }
        catch(HttpClientErrorException.Conflict e){
            System.out.println("exception : " + e.getMessage());
            // 그 외 에러의 경우 409 메세지
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mungMo.memberService.com.config.ResponseMessage.valueOfCode("Conflict").getMessage());
        }
        catch(Exception e){
            System.out.println("exception : " + e.getMessage());
            // 그 외 에러의 경우 500 메세지
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mungMo.memberService.com.config.ResponseMessage.valueOfCode("InternalServerError").getMessage());
        }
    }

}
