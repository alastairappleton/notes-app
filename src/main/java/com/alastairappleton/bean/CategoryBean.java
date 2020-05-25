package com.alastairappleton.bean;

import com.alastairappleton.entity.Category;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@ViewScoped
public class CategoryBean implements Serializable {

    private Category selectedCategory;
    private List<Category> categories;

    public CategoryBean(){
    }

    @PostConstruct
    public void init() {
        categories = new ArrayList<Category>();
        Category c = new Category();
        c.setCategoryId(10);
        c.setCategoryName("Home");
        categories.add(c);
        c = new Category();
        c.setCategoryId(20);
        c.setCategoryName("Away");
        categories.add(c);
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
