package report;

public class Category {
    private String name;
    private String description;
    public Category(String name, String description){
        this.name = name;
        this.description = description;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }

    @Override
    public String toString() {
        return "Category: " + name + ", description: " + description;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Category other = (Category) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    public void changeCategory(String oldName, String newName, String description){
        if(this.name == oldName){
            this.name = newName;
            this.description = description;
            System.out.println("Category changed successfully.");
        }else{
            System.out.println("Category not found.");
        }
    }
    public static void main(String[] args) {
        Category category = new Category("Category1", "Description1");
        System.out.println(category.toString());
        category.changeCategory("Category3", "Category2", "Description2");
        System.out.println(category.toString());

    }
}


