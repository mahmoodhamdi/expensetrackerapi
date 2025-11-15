package alashwah.expensetrackerapi.expense.entity;

import alashwah.expensetrackerapi.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_expenses")
@EntityListeners(AuditingEntityListener.class)
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{validation.expense.name.required}")
    @Size(max = 100, message = "{validation.expense.name.size}")
    @Column(nullable = false)
    private String expenseName;

    @NotBlank(message = "{validation.expense.description.required}")
    @Size(max = 255, message = "{validation.expense.description.size}")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "{validation.expense.amount.required}")
    @Positive(message = "{validation.expense.amount.positive}")
    @Column(nullable = false)
    private Double expenseAmount;

    @NotBlank(message = "{validation.expense.category.required}")
    @Size(max = 50, message = "{validation.expense.category.size}")
    @Column(nullable = false)
    private String category;

    @NotNull(message = "{validation.expense.date.required}")
    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}