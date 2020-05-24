package com.alastairappleton.converters;

import com.alastairappleton.bean.CategoryBean;
import com.alastairappleton.entity.Category;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "categoryConverter")
public class CategoryConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext ctx, UIComponent uiComponent, String categoryId) {
            ValueExpression vex =
                    ctx.getApplication().getExpressionFactory()
                            .createValueExpression(ctx.getELContext(),
                                    "#{categoryBean}", CategoryBean.class);

            CategoryBean categories = (CategoryBean) vex.getValue(ctx.getELContext());
            return categories.getCategory(Integer.valueOf(categoryId));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object category) {
            return ((Category) category).getCategoryId().toString();
        }

}
