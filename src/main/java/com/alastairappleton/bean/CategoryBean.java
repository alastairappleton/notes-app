package com.alastairappleton.bean;

import com.alastairappleton.entity.Category;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@SessionScoped
public class CategoryBean implements Serializable {

    private Category selectedCategory;
    private List<Category> categories;

    public CategoryBean(){
        categories = new ArrayList<Category>();
        categories.add(new Category(10, "Personal"));
        categories.add(new Category(20, "Work"));
        categories.add(new Category(30, "Other"));
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setcategories(List<Category> categories) {
        this.categories = categories;
    }

    // We have a getcategory method (which is separate from getSelectedategory)...
    public Category getCategory(Integer categoryId) {
        if (categoryId == null){
            throw new IllegalArgumentException("no id provided");
        }
        for (Category category : categories){
            if (categoryId.equals(category.getCategoryId())){
                return category;
            }
        }
        return null;
    }

}
