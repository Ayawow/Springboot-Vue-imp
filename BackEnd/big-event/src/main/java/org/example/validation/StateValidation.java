package org.example.validation;

import org.example.anno.State;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State/*给那个注解提供校验规则*/,String/*校验的数据类型*/> {
    /**
     * @param value 将来要校验的数据
     * @param context context in which the constraint is evaluated
     *
     * @return 如果返回false，则校验不通过，如果返回true，则校验通过
     */

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
            //提供校验规则
        if(value == null){
            return false;
        }
        if (value.equals("已发布") || value.equals("草稿")){
            return true;
        }

        return false;
    }
}
