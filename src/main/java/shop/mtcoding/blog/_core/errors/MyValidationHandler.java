package shop.mtcoding.blog._core.errors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import shop.mtcoding.blog._core.errors.exception.Exception400;

@Aspect // AOP 등록
@Component // IoC 등록
public class MyValidationHandler {

    // Advice (부가 로직 hello 메서드)
    // Advice가 수행될 위치 == PointCut
    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void hello(JoinPoint jp){
        Object[] args = jp.getArgs(); // 파라메터(매개변수)
        System.out.println("크기 : "+args.length);

        for(Object arg : args){
            // System.out.println("매개변수 : "+arg);
            
            // 유효성 검사
            if (arg instanceof Errors){
                Errors errors = (Errors) arg; // arg 에러라면 

                if (errors.hasErrors()){
                    for (FieldError error : errors.getFieldErrors()){
                        System.out.println(error.getField());
                        System.out.println(error.getDefaultMessage());

                        // 에러 메세지 날림
                        throw new Exception400(error.getDefaultMessage()+":"+error.getField());
                    }
                }
                
            }
        }

        System.out.println("MyValidationHandler : hello_________________");
    }



}