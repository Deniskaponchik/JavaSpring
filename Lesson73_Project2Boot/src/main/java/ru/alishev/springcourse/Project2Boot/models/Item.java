package ru.alishev.springcourse.Project2Boot.models;
/* hibernate НЕ РАБОТАЕТ
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Size;
import org.hibernate.validator.constraints.Min;
import org.hibernate.validator.constraints.Email;
 */
/* jakarta НЕ РАБОТАЕТ
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Email;
*/
//Алишев. Требуется ИМЕННО 6 версия hibernate.validator
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;        //В пред. проектах было:: import javax.validation.constraints.Min;
import jakarta.validation.constraints.Size;       //В пред. проектах было: import javax.validation.constraints.Size;
//import jakarta.validation.constraints.NotEmpty; //В пред. проектах было:: import javax.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="Item")  //import javax.persistence.Table;
public class Item {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="item_name")
    //@NotEmpty(message = "ItemName should not be empty")
    @NotNull(message = "Title should not be empty")
    //@Size(min = 2, max = 30, message = "ItemName should be between 2 and 30 chars")
    private String itemName;

    //@Transient    //НЕ ХРАНИМ В БД. Hibernate его не будет замечать
    //private int perimeter   //Удобно хранить вычисление на основании других полей. Проще и быстрее вычислить внутри JAVA

    @ManyToOne
    @JoinColumn(name="person_id", referencedColumnName = "id")
    private Person owner;

    public Item() {    }
    public Item(String itemName) {        this.itemName = itemName;    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String ItemName) {        this.itemName = itemName;    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", owner=" + owner +
                '}';
    }
}



























