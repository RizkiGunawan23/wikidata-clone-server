package com.polban.wikidata.validation;

import java.lang.reflect.Field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneNotEmptyValidator implements ConstraintValidator<AtLeastOneNotEmpty, Object> {
    private String[] fields;
    private String fieldName;

    @Override
    public void initialize(AtLeastOneNotEmpty constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        try {
            boolean hasAtLeastOne = false;

            for (String fieldName : fields) {
                Field field = value.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(value);

                if (fieldValue != null && !fieldValue.toString().trim().isEmpty()) {
                    hasAtLeastOne = true;
                    break;
                }
            }

            if (!hasAtLeastOne) {
                // Disable default constraint violation
                context.disableDefaultConstraintViolation();

                // Create custom field name untuk response yang lebih baik
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(this.fieldName)
                        .addConstraintViolation();
            }

            return hasAtLeastOne;

        } catch (Exception e) {
            return false;
        }
    }
}
